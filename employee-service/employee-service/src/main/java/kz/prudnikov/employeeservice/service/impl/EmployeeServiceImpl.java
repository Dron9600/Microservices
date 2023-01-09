package kz.prudnikov.employeeservice.service.impl;

import kz.prudnikov.employeeservice.dto.APIResponseDto;
import kz.prudnikov.employeeservice.dto.DepartmentDto;
import kz.prudnikov.employeeservice.dto.EmployeeDto;
import kz.prudnikov.employeeservice.entity.Employee;
import kz.prudnikov.employeeservice.exception.EmailAlreadyExistException;
import kz.prudnikov.employeeservice.exception.ResourceNotFoundException;
import kz.prudnikov.employeeservice.mapper.AutoEmployeeMapper;
import kz.prudnikov.employeeservice.repository.EmployeeRepository;
import kz.prudnikov.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    private RestTemplate restTemplate;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeDto.getEmail());
        if(optionalEmployee.isPresent()) {
            throw new EmailAlreadyExistException("Email already exist");
        }

        Employee employee = AutoEmployeeMapper.MAPPER.mapToEmployee(employeeDto);
        Employee savedDep = employeeRepository.save(employee);
        EmployeeDto savedDepDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(savedDep);
        return savedDepDto;
    }

    @Override
    public APIResponseDto getEmployeeById(Long id) {
//        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//        EmployeeDto getDtoById = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(optionalEmployee.get());
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
        );

        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(" http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
                DepartmentDto.class);
        DepartmentDto departmentDto = responseEntity.getBody();

        EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);


        return  apiResponseDto;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(AutoEmployeeMapper.MAPPER::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeDto.getId())
        );
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        Employee updateDep = employeeRepository.save(employee);
        return AutoEmployeeMapper.MAPPER.mapToEmployeeDto(updateDep);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
        );
        employeeRepository.deleteById(id);
    }
}
