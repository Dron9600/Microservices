package kz.prudnikov.departmentservice.controller;

import kz.prudnikov.departmentservice.dto.DepartmentDto;
import kz.prudnikov.departmentservice.entity.Department;
import kz.prudnikov.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/departments")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        DepartmentDto departmentDto = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(departmentDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        List<DepartmentDto> getAllDep = departmentService.getAllDepartments();
        return new ResponseEntity<>(getAllDep, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id,
                                                          @RequestBody DepartmentDto departmentDto){
        departmentDto.setId(id);
        DepartmentDto updatedDep = departmentService.updateDepartment(departmentDto);
        return new ResponseEntity<>(updatedDep, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "Deleted successfully";
    }
}
