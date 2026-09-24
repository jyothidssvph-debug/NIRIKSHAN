package com.nirikshan.controller;

import com.nirikshan.model.*;
import com.nirikshan.service.WorkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class WorkController {
    private final WorkService service;
    public WorkController(WorkService service){this.service=service;}
    @GetMapping("/works") public List<Work> works(){return service.all();}
    @GetMapping("/risks") public List<RiskResult> risks(){return service.risks();}
    @GetMapping("/works/{id}") public ResponseEntity<Work> work(@PathVariable String id){Work w=service.get(id);return w==null?ResponseEntity.notFound().build():ResponseEntity.ok(w);}
    @GetMapping("/works/{id}/risk") public ResponseEntity<RiskResult> risk(@PathVariable String id){RiskResult r=service.risk(id);return r==null?ResponseEntity.notFound().build():ResponseEntity.ok(r);}
    @GetMapping("/summary") public Map<String,Object> summary(){List<RiskResult> r=service.risks();return Map.of("totalWorks",r.size(),"critical",r.stream().filter(x->x.level().equals("CRITICAL")).count(),"high",r.stream().filter(x->x.level().equals("HIGH")).count(),"medium",r.stream().filter(x->x.level().equals("MEDIUM")).count(),"low",r.stream().filter(x->x.level().equals("LOW")).count());}
    @PostMapping("/import") public ResponseEntity<?> importCsv(@RequestParam("file") MultipartFile file){try{return ResponseEntity.ok(service.importCsv(file));}catch(Exception e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}}
}
