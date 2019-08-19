package com.tivo.metadata.max.RowMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tivo.metadata.max.model.Program;

public class ProgramRowMapper  implements  RowMapper<List<Object>>{

	@Override
	public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Program obj=new Program();
		System.out.println("this is program row mapper"); 
		ObjectMapper mapper = new ObjectMapper();
		List<Object> lst=new ArrayList<Object>();
		try {
			lst = mapper.readValue(rs.getString("jsonString"), new TypeReference<List<Object>>(){});
			System.out.println("this is program row mapper="+lst); 

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//obj.setAggregationId(rs.getString("provider"));
		//List<Object> lst=(List<Object>)rs.getString("jsonString");
		return lst;
	}

}
