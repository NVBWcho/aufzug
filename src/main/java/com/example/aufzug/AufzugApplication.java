package com.example.aufzug;

import com.example.aufzug.entities.DefectByDateSummary;
import com.example.aufzug.entities.ResolvedByDateSummary;
import com.example.aufzug.entities.SPNVObject;
import com.example.aufzug.repository.AufzugRepository;
import com.example.aufzug.repository.JsonReader;
import com.example.aufzug.service.AufzugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AufzugApplication implements CommandLineRunner {


	//private AufzugRepository aufzugRepository;

	@Autowired
	private AufzugService aufzugService;

	public static void main(String[] args) {
		SpringApplication.run(AufzugApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*List<DefectByDateSummary> defectByDateSummaries=aufzugService.getAllDefects();

		for( DefectByDateSummary d:defectByDateSummaries){
			System.out.println(d);
		}*/

		//System.out.println(aufzugService.IsActiveAtTimepoint(10554989,"2024-03-25 20:40:00"));

		//System.out.println(aufzugService.getDefectiveIdsAtTimePoint("2024-03-25 20:20:00").get());
		//System.out.println(aufzugService.lastUpdated(816523).get());

		System.out.println(aufzugService.getSteplessAccess("de:08211:35110"));
		//System.out.println(aufzugService.getActiveRatesForAllStops());

		List<SPNVObject> allStations=aufzugService.getStopsWithActiveRates();

		for(SPNVObject spnvObject:allStations) {
			System.out.println(spnvObject);
		}


		/*JsonReader jsonReader=new JsonReader();
		List<SPNVObject> stations=jsonReader.getSPNVList();
		for(SPNVObject spnvObject:stations){
			System.out.println(spnvObject);
		}*/

		//aufzugService.getCurrentActiveFraction();
		//System.out.println(aufzugService.getRepairTimesWithinTwoHours());

		//aufzugService.checkActiveRateByDhid();


		/*List<ResolvedByDateSummary> resolvedByDateSummaries=aufzugService.getAllResolutions();
		for(ResolvedByDateSummary r:resolvedByDateSummaries){
			System.out.println(r);
		}*/

	}
}
