package com.tivo.metadata.max.config;
 
import org.springframework.batch.item.ItemProcessor;

import com.tivo.metadata.max.model.Employee;
 
public class DBLogProcessor implements ItemProcessor<Employee, Employee>
{
	
	
    public Employee process(Employee employee) throws Exception
    {
        System.out.println("writing employee : " + employee);
        return employee;
    }
}