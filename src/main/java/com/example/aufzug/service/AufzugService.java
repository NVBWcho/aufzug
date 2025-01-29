package com.example.aufzug.service;

import com.example.aufzug.entities.DefectByDateSummary;
import com.example.aufzug.entities.ResolvedByDateSummary;
import com.example.aufzug.entities.SPNVObject;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AufzugService {

    List<DefectByDateSummary> getAllDefects();

    List<ResolvedByDateSummary> getAllResolutions();

    Optional<Boolean> IsActiveAtTimepoint(Integer fastaId,String timestamp);

    Optional<List<Long>> getDefectiveIdsAtTimePoint(String timestamp);


    Optional<Date> lastUpdated(Integer bfrkId);

    void checkActiveRateByDhid();

    Integer getCurrentActiveFraction();

    Integer getRepairTimesWithinTwoHours();

    Optional<String> getSteplessAccess(String dhid);

    Optional<List<Map<String,Object>>> getActiveRatesForAllStops();


    List<SPNVObject> getStopsWithActiveRates();









}
