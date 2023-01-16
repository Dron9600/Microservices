package kz.prudnikov.employeeservice.service;

import kz.prudnikov.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DEPARTMENT-SERVER")
public interface APIClient {
    @GetMapping("api/departments/{code}")
    DepartmentDto getDepartment(@PathVariable String code);
}
