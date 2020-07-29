package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Resource
    private CompanyService companyService;

    @GetMapping
    public List<Company> getCompanies(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            return companyService.findAll(page, pageSize);
        }
        return companyService.findAll();
    }


    @GetMapping(path = "/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyService.findById(id);
    }

    @GetMapping(path = "/{id}/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            employees.add(new Employee(i, "Tom" + i, 18 + i, "male", i * 1000));
        }
        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PutMapping("/{id}")
    public String updateCompany(@PathVariable(name = "id") int id) {
        return "update successful id = " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable(name = "id") int id) {
        if (companyService.deleteById(id)) {
            return "delete success";
        }
        return "delete fail";
    }


}
