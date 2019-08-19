package com.tivo.metadata.max.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.tivo.metadata.max.model.Program;

@Component
public class ProgramWriter implements	ItemWriter<Object>, StepExecutionListener  
	{

		@Override
		public void beforeStep(StepExecution stepExecution) {
			
		}

		@Override
		public ExitStatus afterStep(StepExecution stepExecution) {
		
			return null;
		}

		

		@Override
		public void write(List<? extends Object> items) throws Exception {
			// TODO Auto-generated method stub
			String fileName = "file.txt";
			System.out.println("writer in items writer"+items.size()+"item value"+items);

			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			 
			File file = new File("D:\\Rovi Source Code\\Spring Boot CSV to Database\\App\\src\\main\\resources\\file.txt");
			  FileWriter fileWriter = new FileWriter(file,true);
			  fileWriter.write(items.toString());
			  fileWriter.flush();
			  fileWriter.close();
		}



	
}
