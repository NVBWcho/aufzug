package com.example.aufzug.controller;


import com.example.aufzug.entities.*;
import com.example.aufzug.service.AufzugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")

@RestController
@RequestMapping("/")


public class AufzugController {


    @Autowired
    private AufzugService aufzugService;


    @GetMapping("/defectsByDate")
    public ResponseEntity<List<DefectByDateSummary>> getDefectsByDate(){
        List<DefectByDateSummary> defectByDateSummaries=aufzugService.getAllDefects();
        return  new ResponseEntity<>(defectByDateSummaries, HttpStatus.OK);
    }

    @GetMapping("/resolutionsByDate")

    public ResponseEntity<List<ResolvedByDateSummary>> getResolutions(){
        List<ResolvedByDateSummary> resolvedByDateSummaries=aufzugService.getAllResolutions();
        return  new ResponseEntity<>(resolvedByDateSummaries,HttpStatus.OK);
    }


    @GetMapping("/currentActive")


    public ResponseEntity<Integer> getCurrentActiveFraction(){
        Integer currentActive= aufzugService.getCurrentActiveFraction();
        return new ResponseEntity<>(currentActive,HttpStatus.OK);
    }

    @GetMapping("/repairsWithinTwoHours")

    public ResponseEntity<Integer> getRepairsWithinTwoHours(){
        Integer repairsWithin=aufzugService.getRepairTimesWithinTwoHours();
        return  new ResponseEntity<>(repairsWithin,HttpStatus.OK);
    }

    @GetMapping("/steplessAccess/{dhid}")

    public ResponseEntity<String> getSteplessAccess(@PathVariable String dhid){

        Optional<String> response=aufzugService.getSteplessAccess(dhid);
        if(response.isPresent()){
            return new ResponseEntity<>(response.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Information",HttpStatus.OK);
        }
    }

    @GetMapping("/activityRatesAll")
    public ResponseEntity<List<SPNVObject>> getActiveRatesAtAStops(){

        return  new ResponseEntity<>(aufzugService.getStopsWithActiveRates(),HttpStatus.OK
        );
    }

    @GetMapping("/lastUpdated/{bfrkId}")
    public ResponseEntity<Optional<Date>> getLastUpdated(@PathVariable Integer bfrkId){

        return  new ResponseEntity<>(aufzugService.lastUpdated(bfrkId),HttpStatus.OK);

    }



    @PostMapping("/wasActiveAt")

    public ResponseEntity<Boolean> isActiveAtTimeStamp(@RequestBody IsActiveDao isActiveDao){


        Optional<Boolean>  requestedState=aufzugService.IsActiveAtTimepoint(isActiveDao.getFastaId(), isActiveDao.getTimestamp());
        if(requestedState.isPresent()){
            return new ResponseEntity<>(requestedState.get(),HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/defectivesAt")

    public ResponseEntity<List<Long>> getDefectivesAtPoint(@RequestBody DefectivesAtDao defectivesAtDao){
        Optional<List<Long>> allDefectives=aufzugService.getDefectiveIdsAtTimePoint(defectivesAtDao.getTimestamp());
        if(allDefectives.isPresent()){
            return  new ResponseEntity<>(allDefectives.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
