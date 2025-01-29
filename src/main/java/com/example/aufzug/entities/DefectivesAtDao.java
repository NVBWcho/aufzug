package com.example.aufzug.entities;

public class DefectivesAtDao {

    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public DefectivesAtDao(String timestamp) {
        this.timestamp = timestamp;
    }

    public DefectivesAtDao() {
    }
}
