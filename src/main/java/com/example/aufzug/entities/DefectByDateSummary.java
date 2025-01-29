package com.example.aufzug.entities;

import java.util.Date;

public class DefectByDateSummary {
    @Override
    public String toString() {
        return "DefectByDateSummary{" +
                "createdOn=" + createdOn +
                ", occurances=" + occurances +
                '}';
    }

    private Date createdOn;

    private Long occurances;

    public DefectByDateSummary() {
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getOccurances() {
        return occurances;
    }

    public void setOccurances(Long occurances) {
        this.occurances = occurances;
    }
}
