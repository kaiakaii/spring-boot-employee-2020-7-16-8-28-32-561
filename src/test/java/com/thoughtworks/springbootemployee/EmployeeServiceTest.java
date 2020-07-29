package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee(1, "test", 18, "female", 900)));
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
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(new Employee(employeeId, "test", 18, "female", 900)));
        //when
        boolean isDelete = employeeService.deleteById(employeeId);
        //then
        assertTrue(isDelete);
    }

    @Test
    void should_return_male_employees_when_find_employee_by_gender_given_male() {
        //given
        String gender = "male";
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        when(employeeRepository.findAllByGender(gender)).thenReturn(Arrays.asList(
                new Employee(1, "test", 18, "male", 900),
                new Employee(2, "test", 18, "male", 900)));
        //when
        List<Employee> actualEmployees = employeeService.findAllByGender(gender);
        //then
        assertNotNull(actualEmployees);
        assertEquals(2, actualEmployees.size());
    }
    @Test
    void should_return_update_employee_when_update_employee_by_id_given_employee_id() {
        //given
        int employeeId = 1;
        Employee updateEmployee = new Employee(employeeId, "test", 18, "male", 900);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        when(employeeRepository.save(updateEmployee)).thenReturn(updateEmployee);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(updateEmployee));
        //when
        Employee employee = employeeService.updateEmployeeById(employeeId, updateEmployee);
        //then
        assertNotNull(employee);
        assertEquals(updateEmployee, employee);
    }

    @Test
    void should_return_employees_when_get_employee_by_paging_given_page_and_page_size() {
        //given
        int page = 1;
        int pageSize = 5;
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        when(employeeRepository.findAll(PageRequest.of(page, pageSize).first())).thenReturn(new PageImpl<>(Arrays.asList(
                new Employee(1, "test", 18, "male", 900),
                new Employee(2, "test", 18, "male", 900))));
        //when
        List<Employee> employeeList = employeeService.findAll(page, pageSize);
        //then
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());
    }
}
