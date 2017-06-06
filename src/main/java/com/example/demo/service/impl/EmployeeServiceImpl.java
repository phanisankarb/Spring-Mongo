package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.entity.EmployeeEntity;
import com.example.demo.model.EmployeeModel;
import com.example.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeModel getEmployeeId(Long employeeId){
		
		EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId);
		EmployeeModel employeeModel = new EmployeeModel();
		
		if(employeeEntity != null){
			
			employeeModel.setCallName(employeeEntity.getCallName());
			employeeModel.setDepartment(employeeEntity.getDepartment());
			employeeModel.setDesignation(employeeEntity.getDesignation());
			employeeModel.setDob(employeeEntity.getDob());
			employeeModel.setEmployeeId(employeeEntity.getEmployeeId());
			employeeModel.setFirstName(employeeEntity.getFirstName());
			employeeModel.setLastName(employeeEntity.getLastName());
			employeeModel.setMiddleName(employeeEntity.getMiddleName());
		}
		
		return employeeModel;
	}
	
	@Override
	public void saveEmployeeDetails(EmployeeModel employeeModel) {
		// TODO Auto-generated method stub
		
		EmployeeEntity employeeEntity = new EmployeeEntity();
		
		employeeEntity.setCallName(employeeModel.getCallName());
		employeeEntity.setDepartment(employeeModel.getDepartment());
		employeeEntity.setDesignation(employeeModel.getDesignation());
		employeeEntity.setDob(employeeModel.getDob());
		employeeEntity.setEmployeeId(employeeModel.getEmployeeId());
		employeeEntity.setFirstName(employeeModel.getFirstName());
		employeeEntity.setLastName(employeeModel.getLastName());
		employeeEntity.setMiddleName(employeeModel.getMiddleName());
		
		employeeRepository.save(employeeEntity);
	}
}
