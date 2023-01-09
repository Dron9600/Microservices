package kz.prudnikov.departmentservice.service.impl;

import kz.prudnikov.departmentservice.dto.DepartmentDto;
import kz.prudnikov.departmentservice.entity.Department;
import kz.prudnikov.departmentservice.exception.ResourceNotFoundException;
import kz.prudnikov.departmentservice.mapper.AutoDepartmentMapper;
import kz.prudnikov.departmentservice.repository.DepartmentRepository;
import kz.prudnikov.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Department department = AutoDepartmentMapper.MAPPER.mapToDepartment(departmentDto);
        Department savedDep = departmentRepository.save(department);
        DepartmentDto savedDepDto = AutoDepartmentMapper.MAPPER.mapToDepartmentDto(savedDep);
        return savedDepDto;
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department  = departmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", id)
        );
        DepartmentDto getDtoById = AutoDepartmentMapper.MAPPER.mapToDepartmentDto(department);
        return  getDtoById;
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(AutoDepartmentMapper.MAPPER::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(DepartmentDto departmentDto) {
        Department department  = departmentRepository.findById(departmentDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", departmentDto.getId())
        );
        department.setDepartmentCode(departmentDto.getDepartmentCode());
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        Department updateDep = departmentRepository.save(department);
        return AutoDepartmentMapper.MAPPER.mapToDepartmentDto(updateDep);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Department", "id", id)
        );
        departmentRepository.deleteById(id);
    }
}
