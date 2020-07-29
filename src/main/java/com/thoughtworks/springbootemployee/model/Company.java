package com.thoughtworks.springbootemployee.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private int companyId;
    private String companyName;
    private int employeesNumber;
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Company() {
    }

    public Company(int companyId, String companyName, int employeesNumber) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        Company company = (Company) o;
        return getCompanyId() == company.getCompanyId() &&
                getEmployeesNumber() == company.getEmployeesNumber() &&
                getCompanyName().equals(company.getCompanyName()) &&
                getEmployees().equals(company.getEmployees());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyId(), getCompanyName(), getEmployeesNumber(), getEmployees());
    }
}
