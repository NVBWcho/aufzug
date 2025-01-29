package com.example.aufzug.entities;

import java.util.Date;

public class ResolvedByDateSummary {

    private Date resolveOn;

    private Long occurances;

    public Date getResolveOn() {
        return resolveOn;
    }

    public void setResolveOn(Date resolveOn) {
        this.resolveOn = resolveOn;
    }

    public Long getOccurances() {
        return occurances;
    }

    public void setOccurances(Long occurances) {
        this.occurances = occurances;
    }

    @Override
    public String toString() {
        return "ResolvedByDateSummary{" +
                "resolveOn=" + resolveOn +
                ", occurances=" + occurances +
                '}';
    }
}
