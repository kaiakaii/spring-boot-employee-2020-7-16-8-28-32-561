package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CompanyRepository {
    private static List<Company> companies = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            Company company = new Company(i + 1, String.format("company-%s", i + 1), 200);
            companies.add(company);
            List<Employee> employees = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                employees.add(new Employee(j, "Tom" + j, 18, j % 2 == 0 ? "male" : "female", j * 1000));
            }
            company.setEmployees(employees);
        }
    }

    public List<Company> findAll() {
        return companies;
    }

    public Company findById(int companyId) {
        return companies.stream().filter(company -> company.getCompanyId() == companyId).findFirst().orElse(null);
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        Company company = findById(companyId);
        if (Objects.nonNull(companies)) {
            return company.getEmployees();
        }
        return null;
    }
}
