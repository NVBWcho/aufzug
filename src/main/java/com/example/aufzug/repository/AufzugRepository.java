package com.example.aufzug.repository;


import com.example.aufzug.UnpackingException;
import com.example.aufzug.entities.DefectByDateSummary;
import com.example.aufzug.entities.ResolvedByDateSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.awt.desktop.QuitEvent;
import java.math.BigDecimal;
import java.util.*;

@Repository

public class AufzugRepository {

    @PersistenceContext
    EntityManager entityManager;

    public void testConnection(){
        ObjectMapper objectMapper=new ObjectMapper();



        String queryStr= """
                WITH temp_cte AS (
                    SELECT created_on_ts,date(created_on_ts) AS date_column FROM defects)

                SELECT date_column, COUNT(*) AS occurrences FROM temp_cte GROUP BY date_column ORDER BY date_column;""";

        try{
            Query query=entityManager.createNativeQuery(queryStr);
            List<Object> allDates=query.getResultList();
            Object [] row=(Object[]) allDates.get(0);

            DefectByDateSummary  defectByDateSummary=new DefectByDateSummary();
            defectByDateSummary.setCreatedOn((Date) row[0]);
            defectByDateSummary.setOccurances((Long) row[1]);
            System.out.println(defectByDateSummary.getCreatedOn().toString()+" "+defectByDateSummary.getOccurances());
            System.out.println(row[0]+" "+row[1] );

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<DefectByDateSummary> getDefectsSummary(){

        String queryStr= """
                WITH temp_cte AS (
                    SELECT created_on_ts,date(created_on_ts) AS date_column FROM defects
                    where created_on_ts>=NOW() - INTERVAL '7 days'
                    )

                SELECT date_column, COUNT(*) AS occurrences FROM temp_cte GROUP BY date_column ORDER BY date_column;""";

        try{

            Query query=entityManager.createNativeQuery(queryStr);
            List<Object> allResults=query.getResultList();
            List<DefectByDateSummary> allDefects=new ArrayList<>();
            for(Object o: allResults){
                try{
                    DefectByDateSummary defectByDateSummary=unpackRowToDefect((Object[]) o);
                    allDefects.add(defectByDateSummary);

                }catch (UnpackingException u){
                    System.out.println("data error");
                }


            }
            return  allDefects;


        }catch (Exception e){
            System.out.println("SQL Error");
            return  null;

        }




    }


    public List<ResolvedByDateSummary> getResolutionSummary(){


        String queryStr= """
                WITH temp_cte AS (
                    SELECT resolved_on_ts,date(resolved_on_ts) AS date_column FROM defects where resolution_status='yes'
                    AND created_on_ts>=NOW() - INTERVAL '7 days'
                    )

                SELECT date_column, COUNT(*) AS occurrences FROM temp_cte GROUP BY date_column ORDER BY date_column;""";

        try{

            Query query=entityManager.createNativeQuery(queryStr);
            List<Object> allResults=query.getResultList();
            List<ResolvedByDateSummary> allResolutions=new ArrayList<>();
            for(Object o: allResults){
                try{
                    ResolvedByDateSummary resolvedByDateSummary=unpackResolve((Object[]) o);
                    allResolutions.add(resolvedByDateSummary);

                }catch (UnpackingException u){
                    System.out.println("data error");
                }


            }
            return  allResolutions;


        }catch (Exception e){
            System.out.println("SQL Error");
            return  null;

        }



    }

    public Optional<Boolean> isActiveAtTimePoint(Integer fastaId, String timestamp){

        String queryStr= """
                select * from defects where fasta_id= ?1 and created_on_ts<CAST( ?2 AS TIMESTAMP) AND\s

                ( resolved_on_ts>=CAST( ?3 AS TIMESTAMP) OR resolution_status!='yes')""";
        Query query=entityManager.createNativeQuery(queryStr);
        query.setParameter(1,fastaId);
        query.setParameter(2,timestamp);
        query.setParameter(3,timestamp);

        try{
            List<Object> result=query.getResultList();
            System.out.println(result.size());
            if(result.size()>0){
                return Optional.of(false);
            }else{
                return  Optional.of(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  Optional.empty();
        }

    }

    public Optional<List<Long>> AllInactivAtTimepoint(String timestamp){
        String queryStr= """
                select fasta_id from defects where created_on_ts<CAST( ?1 AS TIMESTAMP) AND\s

                ( resolved_on_ts>=CAST( ?2 AS TIMESTAMP) OR resolution_status!='yes')""";
        Query query=entityManager.createNativeQuery(queryStr);

        query.setParameter(1,timestamp);
        query.setParameter(2,timestamp);

        try{

            List<Long> result=query.getResultList();
            if(result.size()>0){
                List<Long> defectFastIds=new ArrayList<>();
                for(Long  o:result){


                    System.out.println(o);
                    defectFastIds.add( o);
                }
                return Optional.of(defectFastIds);

            }else {
                Optional<List<Long>>emptyResult=Optional.empty();
                return emptyResult;

            }

        }catch (Exception e){
            e.printStackTrace();
            Optional<List<Long>>emptyResult=Optional.empty();
            return emptyResult;
        }



    }

    public Integer getFrctionOfActiveLifts(){

        String queryStr="select COUNT(*) from aufzuge where current_state='ACTIVE' ";
        Query query=entityManager.createNativeQuery(queryStr);
        try{
            List<Object> result=query.getResultList();
            Long currentActive=(Long) result.get(0);
            int fraction= (int) Math.floor ((float) (100*currentActive)/332);
            System.out.println(currentActive);
            System.out.println(fraction);
            return (Integer) fraction;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    public List<Integer> getDistributionOfRepairTimes(){
        String queryStr= """
                select
                     EXTRACT(EPOCH FROM (resolved_on_ts - created_on_ts)) AS difference
                 from defects where resolution_status='yes' ;
                """;
        Query query=entityManager.createNativeQuery(queryStr);
        try{
            List<Object> vals=query.getResultList();
            List<Integer> integerVals=new ArrayList<>();
            for(Object o: vals){
                BigDecimal decimal=(BigDecimal) o;

                integerVals.add(((BigDecimal) o).intValue());
            }
            //System.out.println(integerVals.get(1));
            return integerVals;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Optional<List<Map<String,Object>>> getActiveRateForAllStops(){

        String queryStr= """
                SELECT\s
                    s.dhid,
                	
                    COUNT(CASE WHEN a.current_state = 'ACTIVE' THEN 1 END) AS num_active_lifts,
                    COUNT(a.fasta_id) AS total_lifts,
                    CASE\s
                        WHEN COUNT(a.fasta_id) > 0 THEN\s
                            CAST(COUNT(CASE WHEN a.current_state = 'ACTIVE' THEN 1 END) AS float) / COUNT(a.fasta_id)
                        ELSE\s
                            0.0\s
                    END AS ratio_active_to_total
                FROM\s
                    dbstations s
                JOIN\s
                    aufzuge a ON s.dbstationnumber = a.db_station_number
                                
                GROUP BY\s
                    s.dhid;
                                
                """;

        Query query=entityManager.createNativeQuery(queryStr);

        try{
            List<Object> results=query.getResultList();
            List<Map<String,Object>> activityRates=new ArrayList<>();
            for(Object o:results){
                Object[] row=(Object[]) o;
                Map<String,Object> result=new HashMap<>();
                Double value=(Double) row[3];
                result.put("dhid",row[0]);
                result.put("fraction",value);
                activityRates.add(result);



            }
            return  Optional.of(activityRates);

        }catch (Exception e){
            e.printStackTrace();
            return  Optional.empty();
        }
    }

    public Optional<Date> getLastUpdated(Integer bfrkId){
        String queryStr="select last_updated_ts from aufzuge where bfrk_id="+Integer.toString(bfrkId);
        Query query=entityManager.createNativeQuery(queryStr);


        try{
            System.out.println(query.getFirstResult());
            List<Object> o=query.getResultList();
            Date row=(Date) o.get(0);
            System.out.println(row);

            return Optional.of(row);
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<String> getStairlessAccess(String dhid){
        String queryStr="select steplessaccess from dbstations where dhid="+"'"+dhid+"'";
        Query query=entityManager.createNativeQuery(queryStr);
        try{
            List<Object> o=query.getResultList();
            String response=(String) o.get(0);
            System.out.println(response);
            return  Optional.of(response);
        }catch (Exception e){
            e.printStackTrace();
            return  Optional.empty();
        }
    }

    public static DefectByDateSummary unpackRowToDefect(Object[] datarow) throws UnpackingException {

        DefectByDateSummary  defectByDateSummary=new DefectByDateSummary();
        defectByDateSummary.setCreatedOn((Date) datarow[0]);
        defectByDateSummary.setOccurances((Long) datarow[1]);
        return  defectByDateSummary;
    }

    public static ResolvedByDateSummary unpackResolve(Object [] datarow) throws  UnpackingException{

        ResolvedByDateSummary resolvedByDateSummary=new ResolvedByDateSummary();
        resolvedByDateSummary.setResolveOn((Date) datarow[0]);
        resolvedByDateSummary.setOccurances((Long) datarow[1]);
        return resolvedByDateSummary;


    }
}
