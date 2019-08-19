package com.tivo.metadata.max.config;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.tivo.metadata.max.model.Program;

@Component
public class ProgramWriter implements
	ItemWriter<Program>, StepExecutionListener  
	{

		@Override
		public void beforeStep(StepExecution stepExecution) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ExitStatus afterStep(StepExecution stepExecution) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void write(List<? extends Program> items) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("writer in items writer"+items.size());
		}



	
}
