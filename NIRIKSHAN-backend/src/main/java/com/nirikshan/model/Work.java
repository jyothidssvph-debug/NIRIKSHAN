package com.nirikshan.model;

public record Work(
        String id, String constituency, String district, String workName,
        double sanctionedAmount, double expenditure, double physicalProgress,
        int plannedDays, int elapsedDays, String status
) {}
