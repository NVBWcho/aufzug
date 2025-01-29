package com.example.aufzug.entities;

public class SPNVObject {

    private String dhid;
    private String kreisschluessel;

    private String oevart;

    private String gemeinde;

    private String ortsteil;

    private String name;

    private Double lon;

    private Double lat;

    public SPNVObject() {
    }

    public SPNVObject(String dhid, String kreisschluessel, String oevart, String gemeinde, String ortsteil, String name, Double lon, Double lat, String objektart) {
        this.dhid = dhid;
        this.kreisschluessel = kreisschluessel;
        this.oevart = oevart;
        this.gemeinde = gemeinde;
        this.ortsteil = ortsteil;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.objektart = objektart;
    }

    private String objektart;

    private Double activeFraction;

    public String getDhid() {
        return dhid;
    }

    public void setDhid(String dhid) {
        this.dhid = dhid;
    }

    public String getKreisschluessel() {
        return kreisschluessel;
    }

    public void setKreisschluessel(String kreisschluessel) {
        this.kreisschluessel = kreisschluessel;
    }

    public String getOevart() {
        return oevart;
    }

    public void setOevart(String oevart) {
        this.oevart = oevart;
    }

    public String getGemeinde() {
        return gemeinde;
    }

    public void setGemeinde(String gemeinde) {
        this.gemeinde = gemeinde;
    }

    public String getOrtsteil() {
        return ortsteil;
    }

    public void setOrtsteil(String ortsteil) {
        this.ortsteil = ortsteil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getObjektart() {
        return objektart;
    }

    public void setObjektart(String objektart) {
        this.objektart = objektart;
    }

    public Double getActiveFraction() {
        return activeFraction;
    }

    public void setActiveFraction(Double activeFraction) {
        this.activeFraction = activeFraction;
    }

    @Override
    public String toString() {
        return "SPNVObject{" +
                "dhid='" + dhid + '\'' +
                ", kreisschluessel='" + kreisschluessel + '\'' +
                ", oevart='" + oevart + '\'' +
                ", gemeinde='" + gemeinde + '\'' +
                ", ortsteil='" + ortsteil + '\'' +
                ", name='" + name + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", objektart='" + objektart + '\'' +
                ", activeFraction=" + activeFraction +
                '}';
    }
}
