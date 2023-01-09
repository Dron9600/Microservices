package kz.prudnikov.departmentservice.service;

import kz.prudnikov.departmentservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentById(Long id);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto updateDepartment(DepartmentDto departmentDto);

    void deleteDepartment(Long id);
}
