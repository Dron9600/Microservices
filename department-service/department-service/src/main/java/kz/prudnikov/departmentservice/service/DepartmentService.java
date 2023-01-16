package kz.prudnikov.departmentservice.service;

import kz.prudnikov.departmentservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentByCode(String Code);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto updateDepartment(DepartmentDto departmentDto);

    void deleteDepartment(Long id);
}
