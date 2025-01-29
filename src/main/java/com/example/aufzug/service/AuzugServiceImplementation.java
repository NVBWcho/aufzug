package com.example.aufzug.service;

import com.example.aufzug.entities.DefectByDateSummary;
import com.example.aufzug.entities.ResolvedByDateSummary;
import com.example.aufzug.entities.SPNVObject;
import com.example.aufzug.repository.AufzugRepository;
import com.example.aufzug.repository.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AuzugServiceImplementation  implements AufzugService{

    @Autowired
    private AufzugRepository aufzugRepository;
    @Override
    public List<DefectByDateSummary> getAllDefects() {

        return aufzugRepository.getDefectsSummary();
    }

    @Override
    public List<ResolvedByDateSummary> getAllResolutions() {
        return aufzugRepository.getResolutionSummary();
    }

    @Override
    public Optional<Boolean> IsActiveAtTimepoint(Integer fastaId, String timestamp) {
        return aufzugRepository.isActiveAtTimePoint(fastaId,timestamp);
    }

    @Override
    public Optional<List<Long>> getDefectiveIdsAtTimePoint(String timestamp) {
        return aufzugRepository.AllInactivAtTimepoint(timestamp);
    }

    @Override
    public Optional<Date> lastUpdated(Integer bfrkId) {
        return aufzugRepository.getLastUpdated(bfrkId);
    }

    @Override
    public void checkActiveRateByDhid() {
         aufzugRepository.getActiveRateForAllStops();
    }

    @Override
    public Integer getCurrentActiveFraction() {
        return aufzugRepository.getFrctionOfActiveLifts();
    }

    @Override
    public Integer getRepairTimesWithinTwoHours() {
        List<Integer> allVals=aufzugRepository.getDistributionOfRepairTimes();
        long withinTwoHours=    allVals.stream().filter(num -> num <= 43200).count();
        int fraction= (int) Math.floor ((float) (100*withinTwoHours)/allVals.size());
        return  (Integer) fraction;


    }

    @Override
    public Optional<String> getSteplessAccess(String dhid) {
        return aufzugRepository.getStairlessAccess(dhid);
    }

    @Override
    public Optional<List<Map<String,Object>>> getActiveRatesForAllStops() {
        return  aufzugRepository.getActiveRateForAllStops();
    }

    @Override
    public List<SPNVObject> getStopsWithActiveRates() {

        JsonReader jsonReader=new JsonReader();
        List<SPNVObject> allStops=jsonReader.getSPNVList();
        Optional<List<Map<String,Object>>> currentActiveRates=aufzugRepository.getActiveRateForAllStops();

        List<SPNVObject> stationsInDatabse=new ArrayList<>();

        for(SPNVObject station:allStops){
            Optional<SPNVObject> foundStaion=fetchActiveRate(station,currentActiveRates.get());
            if(foundStaion.isPresent()){
                stationsInDatabse.add(foundStaion.get());
            }
        }


        return stationsInDatabse;
    }

    public static Optional<SPNVObject> fetchActiveRate(SPNVObject station, List<Map<String,Object>> currentRates){
        Map<String,Object> searchResult=currentRates.stream().filter(o->o.get("dhid").equals(station.getDhid())).findFirst().orElse(null);
        if(searchResult==null){
            return  Optional.of(station);
        }else {
            station.setActiveFraction((Double) searchResult.get("fraction"));
            return Optional.of(station);

        }


    }
}
