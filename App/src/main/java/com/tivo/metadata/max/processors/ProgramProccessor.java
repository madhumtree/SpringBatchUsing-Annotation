package com.tivo.metadata.max.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.tivo.metadata.max.model.Employee;
import com.tivo.metadata.max.model.Program;

@Component
public class ProgramProccessor implements ItemProcessor<List<Object>, String>{
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

		System.out.println("in processor"+item);
		
		
	
		for (Object obj:item) {
			JSONObject programs = new JSONObject();
			Map<String,Object> ob=(Map<String, Object>)obj;
		String id=(String)ob.get("provider_program_id");
	
		System.out.println("id in porgram processor ====="+id);
		
	//	System.out.println("id=-----"+id);
		
		String provider=(String)ob.get("provider");
		programs.put("provider",id);
		programs.put("provider_program_id",provider);
		jsonObject.put(id,programs );
		
	}
		System.out.println("jsonObject="+jsonObject.toString());
		return jsonObject.toString();
	}
	
	
	
	     
}
