package kz.prudnikov.employeeservice.service;

import kz.prudnikov.employeeservice.dto.APIResponseDto;
import kz.prudnikov.employeeservice.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    void deleteEmployee(Long id);
}
