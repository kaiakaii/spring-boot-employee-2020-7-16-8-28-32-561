package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Resource
    private CompanyService companyService;

    @GetMapping
    public List<Company> getCompanies(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "0") int pageSize) {
        return companyService.findAll();
    }


    @GetMapping(path = "/{id}")
    public Company getCompany() {
        return new Company(1, "HUAWEI", 200);
    }

    @GetMapping(path = "/{id}/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            employees.add(new Employee(i, "Tom" + i, 18 + i, "male", i * 1000));
        }
        return employees;
    }

    //TODO
//    @PostMapping
//    public Company createCompany(@RequestBody Company company) {
//        Company company1 = new Company(company.getId(), company.getCompanyName(), company.getEmployeesNumber());
//        company1.setEmployees(company.getEmployees());
//        return company1;
//    }

    @PutMapping("/{id}")
    public String updateCompany(@PathVariable(name = "id") int id) {
        return "update successful id = " + id;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String deleteCompany(@PathVariable(name = "id") int id) {
        return "delete successful id = " + id;
    }


}
