package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.Employee;

public class ResponseEmployee {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    private Integer companyId;
    public static ResponseEmployee toResponseEmployee(Employee employee){
        ResponseEmployee responseEmployee = new ResponseEmployee();
        responseEmployee.setId(employee.getId());
        responseEmployee.setAge(employee.getAge());
        responseEmployee.setName(employee.getName());
        responseEmployee.setGender(employee.getGender());
        responseEmployee.setSalary(employee.getSalary());
        responseEmployee.setCompanyId(employee.getCompanyId());
        return responseEmployee;

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
