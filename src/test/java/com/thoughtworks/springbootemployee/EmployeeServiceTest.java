package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    private static List<Employee> employees = new ArrayList<>();

    @BeforeAll
    static void init() {
        for (int j = 0; j < 10; j++) {
            employees.add(new Employee(j, "Tom" + j, 18, j % 2 == 0 ? "male" : "female", j * 1000));
        }
    }

    @Test
    void should_return_employees_when_find_all_given_no_parameters() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        when(employeeRepository.findAll()).thenReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.findAll();
        //then
        assertNotNull(actualEmployees);
        assertEquals(employees.size(), actualEmployees.size());
    }

    @Test
    void should_return_employees_when_add_employee_given_employee() {
        //given
        Employee employee = new Employee(1, "test", 18, "female", 900);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        when(employeeRepository.save(employee)).thenReturn(employee);
        //when
        Employee actualEmployee = employeeService.addEmployee(employee);
        //then
        assertNotNull(actualEmployee);
        assertEquals(employee, actualEmployee);
    }
    @Test
    void should_return_employee_when_find_employees_by_id_given_employee_id() {
        //given
        int employeeId = 1;
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        when(employeeRepository.getOne(1)).thenReturn(new Employee(1, "test", 18, "female", 900));
        //when
        Employee actualEmployee = employeeService.findEmployeeById(employeeId);
        //then
        assertNotNull(actualEmployee);
        assertEquals(1, actualEmployee.getId());
    }

    @Test
    void should_return_true_when_delete_employee_given_employee_id() {
        //given
        int employeeId = 1;
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        //when
        boolean isDelete = employeeService.deleteById(employeeId);
        //then
        assertTrue(isDelete);
    }
}
