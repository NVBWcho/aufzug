package com.example.aufzug.entities;

public class IsActiveDao {

    private Integer fastaId;
    private String timestamp;

    public Integer getFastaId() {
        return fastaId;
    }

    public IsActiveDao() {
    }

    public IsActiveDao(Integer fastaId, String timestamp) {
        this.fastaId = fastaId;
        this.timestamp = timestamp;
    }

    public void setFastaId(Integer fastaId) {
        this.fastaId = fastaId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
