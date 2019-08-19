package com.tivo.metadata.max.tasklets;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TruncateXumoAssociationTasklet implements Tasklet {

	
	@Autowired 
	JdbcTemplate jdbctemplate;
	
	/*
	 * String sql;
	 * 
	 * public TruncateMaxAssociationTasklet(String sql){
	 * 
	 * this.sql=sql; }
	 * 
	 * public String getSql() { return sql; }
	 */





	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		//jdbctemplate.execute("CREATE table dbo.sample(name varchar(10))");
		String id=chunkContext.getStepContext().getStepExecution()
			      .getJobParameters().getString("processingId");
        
		jdbctemplate.execute("IF OBJECT_ID('MAX_ASSOCIATION_PROGRAM_xumo') IS NOT NULL " + 
         		"                    TRUNCATE TABLE dbo.MAX_ASSOCIATION_PROGRAM_xumo   ELSE " + 
         		"                    	BEGIN " + 
         		"                        	CREATE TABLE dbo.MAX_ASSOCIATION_PROGRAM_xumo ( " + 
         		"                        		PROVIDER nvarchar(50) NOT NULL, " + 
         		"								PROVIDER_PROGRAM_ID nvarchar(100) NOT NULL, " + 
         		"								STATION_ID nvarchar(100) NOT NULL, " + 
         		"								AIR_DATE_TIME datetime NOT NULL " + 
         		"							); " + 
         		"						END ");
		
		jdbctemplate.execute("IF OBJECT_ID('MAX_ASSOCIATION_schedule_xumo') IS NOT NULL " + 
         		"                    TRUNCATE TABLE dbo.MAX_ASSOCIATION_schedule_xumo   ELSE " + 
         		"                    	BEGIN " + 
         		"                        	CREATE TABLE dbo.MAX_ASSOCIATION_schedule_xumo ( " + 
         		"                        		PROVIDER nvarchar(50) NOT NULL, " + 
         		"								PROVIDER_PROGRAM_ID nvarchar(100) NOT NULL, " + 
         		"								STATION_ID nvarchar(100) NOT NULL, " + 
         		"								AIR_DATE_TIME datetime NOT NULL " + 
         		"							); " + 
         		"						END ");
				
		return RepeatStatus.FINISHED;
	}

}
