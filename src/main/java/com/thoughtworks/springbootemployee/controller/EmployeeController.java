package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees(@RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "0") int pageSize,
                                       @RequestParam(required = false, defaultValue = "") String gender) {
        if (Objects.nonNull(gender)) {
            return employeeService.findAllByGender(gender);
        }
        return employeeService.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable(name = "id") int id) {
        return employeeService.findEmployeeById(id);
    }


    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable(name = "id") Integer id, @RequestBody Employee employee) {
        return employeeService.updateEmployeeById(id, employee);

    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable(name = "id") int id) {
        if (employeeService.deleteById(id)) {
            return "delete success";
        }
        return "delete fail";
    }

}
