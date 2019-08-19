package com.tivo.metadata.max.tasklets;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InsertXumoAssociationTasklet  implements Tasklet {
	@Autowired 
	JdbcTemplate jdbctemplate;
	
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		String id=chunkContext.getStepContext().getStepExecution().getJobParameters().getString("processingId");

String sql1="INSERT INTO dbo.MAX_ASSOCIATION_PROGRAM_xumo (" + 
		"                           PROVIDER, PROGRAM_ID" + 
		"                   )   SELECT " + 
		"                                         program.provider," + 
		"                                         program.provider_program_id" + 
		"                                         FROM  ACESDR.dbo.program AS program  JOIN" + 
		"										 ACESDR.dbo.schedule AS schedule" + 
		"										 ON  schedule.provider='xumo' " + 
		"										 AND schedule.provider_program_id=program.provider_program_id" + 
		"										 WHERE  " + 
		"										 program.properties IS NOT NULL AND schedule.properties IS NOT NULL ";
		
String sql2=" INSERT INTO dbo.MAX_ASSOCIATION_SCHEDULE_xumo (" + 
		"                           PROVIDER, PROGRAM_ID, STATION_ID, START_TIME, END_TIME " + 
		"                   ) " + 
		"SELECT " + 
		"                                         program.provider," + 
		"                                         program.provider_program_id," + 
		"                                         JSON_VALUE(schedule.properties, '$.payload.affiliate_station') AS station_id," + 
		"										 JSON_VALUE(schedule.properties, '$.payload.start_time') AS start_time," + 
		"										 JSON_VALUE(schedule.properties, '$.payload.end_time') AS end_time" + 
		"                                         FROM ACESDR.dbo.schedule AS schedule JOIN" + 
		"										 ACESDR.dbo.program AS program  " + 
		"										 ON  schedule.provider='xumo' " + 
		"										 AND schedule.provider_program_id=program.provider_program_id" + 
		"										 WHERE  " + 
		"										 program.properties IS NOT NULL AND schedule.properties IS NOT NULL" + 
		"										 ORDER BY start_time, end_time;  ";

		jdbctemplate.update(sql1);
		jdbctemplate.update(sql2);

		return RepeatStatus.FINISHED;
	}

}
