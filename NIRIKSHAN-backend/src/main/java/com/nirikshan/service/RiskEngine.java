package com.nirikshan.service;

import com.nirikshan.model.RiskResult;
import com.nirikshan.model.Work;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RiskEngine {
    public RiskResult assess(Work w) {
        int score = 0;
        List<String> reasons = new ArrayList<>();
        double spendPct = w.sanctionedAmount() <= 0 ? 0 : w.expenditure() / w.sanctionedAmount() * 100;

        // NIRIKSHAN rule set: transparent, deterministic, human-review prioritization.
        if (spendPct >= 90 && w.physicalProgress() < 60) { score += 45; reasons.add("R1: Expenditure ≥90% while physical progress is <60%"); }
        if (spendPct >= 75 && w.physicalProgress() < 70) { score += 30; reasons.add("R2: Expenditure ≥75% while physical progress is <70%"); }
        if (w.elapsedDays() > w.plannedDays() && w.physicalProgress() < 80) { score += 30; reasons.add("R3: Timeline exceeded while physical progress is <80%"); }
        if (w.physicalProgress() < 40) { score += 15; reasons.add("R4: Physical progress is <40%"); }
        if (w.expenditure() > w.sanctionedAmount()) { score += 25; reasons.add("R5: Expenditure exceeds sanctioned amount"); }

        score = Math.min(score, 100);
        String level = score >= 70 ? "CRITICAL" : score >= 45 ? "HIGH" : score >= 20 ? "MEDIUM" : "LOW";
        if (reasons.isEmpty()) reasons.add("No major rule-based anomaly detected");
        return new RiskResult(w.id(), score, level, reasons);
    }
}
