package com.nirikshan.service;

import com.nirikshan.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class WorkService {
    private final RiskEngine riskEngine;
    private List<Work> works = new ArrayList<>(List.of(
        new Work("MPLADS-001","Nandyal","Nandyal","Community Hall Renovation",1000000,930000,42,180,240,"ONGOING"),
        new Work("MPLADS-002","Kurnool","Kurnool","Government School Building",2500000,1200000,65,300,250,"ONGOING"),
        new Work("MPLADS-003","Anantapur","Anantapur","Road Improvement",1800000,600000,72,180,140,"ONGOING"),
        new Work("MPLADS-004","Kadapa","YSR Kadapa","Drinking Water Facility",900000,860000,88,150,145,"NEAR_COMPLETE"),
        new Work("MPLADS-005","Chittoor","Chittoor","Public Library",1500000,350000,25,240,220,"ONGOING"),
        new Work("MPLADS-006","Nellore","SPSR Nellore","Drainage Improvement",1300000,1250000,35,200,260,"DELAYED")
    ));

    public WorkService(RiskEngine riskEngine) { this.riskEngine = riskEngine; }
    public synchronized List<Work> all() { return List.copyOf(works); }
    public synchronized Work get(String id) { return works.stream().filter(w -> w.id().equalsIgnoreCase(id)).findFirst().orElse(null); }
    public synchronized List<RiskResult> risks() { return works.stream().map(riskEngine::assess).sorted(Comparator.comparingInt(RiskResult::score).reversed()).toList(); }
    public synchronized RiskResult risk(String id) { Work w = get(id); return w == null ? null : riskEngine.assess(w); }

    public synchronized Map<String,Object> importCsv(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("CSV file is empty");
        List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)).lines().toList();
        if (lines.size() < 2) throw new IllegalArgumentException("CSV must contain a header and at least one data row");
        String expected = "id,constituency,district,workName,sanctionedAmount,expenditure,physicalProgress,plannedDays,elapsedDays,status";
        if (!lines.get(0).trim().equalsIgnoreCase(expected)) throw new IllegalArgumentException("Invalid header. Download the NIRIKSHAN CSV template.");
        List<Work> imported = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        for (int i=1;i<lines.size();i++) {
            String line=lines.get(i).trim(); if(line.isEmpty()) continue;
            List<String> p=parse(line); if(p.size()!=10) throw new IllegalArgumentException("Row "+(i+1)+" must contain 10 columns");
            try {
                String id=p.get(0).trim(); if(id.isBlank() || !ids.add(id)) throw new IllegalArgumentException("Duplicate/empty ID at row "+(i+1));
                double sanctioned=Double.parseDouble(p.get(4)); double expenditure=Double.parseDouble(p.get(5)); double progress=Double.parseDouble(p.get(6));
                int planned=Integer.parseInt(p.get(7)); int elapsed=Integer.parseInt(p.get(8));
                if(sanctioned<0 || expenditure<0 || progress<0 || progress>100 || planned<=0 || elapsed<0) throw new IllegalArgumentException("Invalid numeric value at row "+(i+1));
                imported.add(new Work(id,p.get(1),p.get(2),p.get(3),sanctioned,expenditure,progress,planned,elapsed,p.get(9)));
            } catch(NumberFormatException e){ throw new IllegalArgumentException("Invalid number at row "+(i+1)); }
        }
        if(imported.isEmpty()) throw new IllegalArgumentException("No valid data rows found");
        works = imported;
        return Map.of("imported", imported.size(), "message", "CSV validated and imported successfully");
    }

    private List<String> parse(String line){
        List<String> out=new ArrayList<>(); StringBuilder b=new StringBuilder(); boolean q=false;
        for(char c:line.toCharArray()){ if(c=='\"'){q=!q;} else if(c==','&&!q){out.add(b.toString().trim());b.setLength(0);} else b.append(c); }
        out.add(b.toString().trim()); return out;
    }
}
