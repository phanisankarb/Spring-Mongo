package com.example.demo.web;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.UnauthorizedException;
import com.example.demo.model.EmployeeModel;
import com.example.demo.service.EmployeeService;
import com.example.demo.util.GateWayResponse;

@RestController
public class EmployeeWeb {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(path = "/saveEmployee", method = RequestMethod.POST,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public GateWayResponse<?> customerInfoStepOne(HttpServletRequest request, @RequestBody EmployeeModel employeeModel) throws UnauthorizedException {
		System.out.println("ok..");
		employeeService.saveEmployeeDetails(employeeModel);
		return new GateWayResponse<String>(true, HttpStatus.OK, "Success");
	}
	
	@RequestMapping(path = "/getEmployee/{employeeId}", method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public GateWayResponse<?> customerInfoStepOne(HttpServletRequest request, @PathVariable("employeeId") Long employeeId) throws UnauthorizedException {
		EmployeeModel employeeModel = employeeService.getEmployeeId(employeeId);
		return new GateWayResponse<EmployeeModel>(true, HttpStatus.OK, employeeModel);
	}
}
