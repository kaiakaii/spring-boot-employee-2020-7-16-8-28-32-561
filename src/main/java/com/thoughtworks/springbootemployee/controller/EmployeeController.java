package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees(@RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "0") int pageSize,
                                       @RequestParam(required = false, defaultValue = "") String gender) {
        return employeeService.findAll();
    }


    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable(name = "id") int id) {
        return employeeService.findEmployeeById(id);
    }


    @PutMapping("/{id}")
    public String putEmployee(@PathVariable(name = "id") int id) {
        return "put id: " + id;

    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable(name = "id") int id) {
        if (employeeService.deleteById(id)) {
            return "delete success";
        }
        return "delete fail";
    }

}
