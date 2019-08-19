package com.tivo.metadata.max.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tivo.metadata.max.model.Program;
@Component
public class ScheduleProcessor implements ItemProcessor<List<Object>, String>{

	private StepExecution stepExecution;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}  
	
	List<Program> programlist=new ArrayList<Program>();
	@Override
	public String process(List<Object> item) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();

		

	String station_id="";
	JSONArray array = new JSONArray();
		for (Object obj:item) {
			JSONObject programs	  = new JSONObject();
			
			 System.out.println("obj-----"+obj.toString());
			Map<String,Object> ob=(Map<String, Object>)obj;
		 station_id=(String)ob.get("STATION_ID");
		String provider=(String)ob.get("PROVIDER");
		String start_time=(String)ob.get("STATION_ID");
		String end_time=(String)ob.get("STATION_ID");

		
		programs.put("STATION_ID",  station_id);
		
		
		programs.put("END_TIME", end_time);
		programs.put("START_TIME", start_time);
		programs.put("PROVIDER", provider);

		
		array.put(programs);
		
	//	System.out.println("id=-----"+id);
		
		
		
		
	}
		jsonObject.put(station_id,array );
		System.out.println("jsonObject="+jsonObject.toString());
		return jsonObject.toString();
	}
	

}
