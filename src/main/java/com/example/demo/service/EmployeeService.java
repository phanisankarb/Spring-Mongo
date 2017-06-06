package com.example.demo.service;

import com.example.demo.model.EmployeeModel;

public interface EmployeeService {
	//public
	EmployeeModel getEmployeeId(Long employeeId);
	void saveEmployeeDetails(EmployeeModel employeeModel);
	
}
