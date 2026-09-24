package com.nirikshan.model;

import java.util.List;

public record RiskResult(String workId, int score, String level, List<String> reasons) {}
