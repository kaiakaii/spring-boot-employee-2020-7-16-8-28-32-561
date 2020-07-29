package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping
    public List<Employee> getEmployeesPage(@RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "0") int pageSize,
                                           @RequestParam(required = false, defaultValue = "") String gender) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            employees.add(new Employee(i, "Tom" + i, 18 + i, "male", i * 1000));
        }
        if (page != 0) {
            return employees.stream().limit(3).collect(Collectors.toList());
        } else if (!gender.equals("")) {
            return employees.stream().limit(1).collect(Collectors.toList());
        } else {
            return employees;
        }
    }


    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employee;
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable(name = "id") int id) {
        return new Employee(id, "Tom", 18, "male", 1000);
    }


    @PutMapping("/{id}")
    public String putEmployee(@PathVariable(name = "id") int id) {
        return "put id: " + id;

    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable(name = "id") int id) {
        return "delete id: " + id;

    }

}
