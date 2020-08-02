package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.ExceptionMessage;
import com.thoughtworks.springbootemployee.exception.NotFoundIDException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee) throws NotFoundIDException {
        if (Objects.isNull(employee)) {
            throw new NotFoundIDException(ExceptionMessage.NOT_FOUND_ID);
        }
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Integer employeeId) throws NotFoundIDException {
        return employeeRepository.findById(employeeId).orElseThrow(()->new NotFoundIDException(ExceptionMessage.NOT_FOUND_ID));
    }

    public boolean deleteById(Integer employeeId) throws NotFoundIDException {
        if (Objects.isNull(employeeId) && Objects.isNull(findEmployeeById(employeeId))) {
            throw new NotFoundIDException(ExceptionMessage.NOT_FOUND_ID);
        }
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee updateEmployeeById(int employeeId, Employee updateEmployee) throws NotFoundIDException {
        Employee employee = findEmployeeById(employeeId);
        if (Objects.isNull(employee)) {
            throw new NotFoundIDException(ExceptionMessage.NOT_FOUND_ID);
        }
        updateEmployee.setId(employeeId);
        return employeeRepository.save(updateEmployee);
    }

    public List<Employee> findAll(int page, int pageSize) throws NotFoundIDException {
        if (page < 1 || pageSize < 0) {
            throw new NotFoundIDException(ExceptionMessage.NOT_FOUND_ID);
        }
        return employeeRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }
}
