package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeIntergrationTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MockMvc mockMvc;

    @AfterAll
    public void after() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_employee_when_add_employee_given_employee() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        companyRepository.save(company);
        String jsonEmployee = "{\n" +
                "            \"name\": \"Change\",\n" +
                "            \"age\": 18,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 55,\n" +
                "            \"companyId\": " + company.getId() + "\n" +
                "        }";
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(jsonEmployee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Change"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(55))
                .andExpect(jsonPath("$.companyId").value(1));

        //when//then
    }

    @Test
    void should_return_employees_when_get_employees_given_none() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        companyRepository.save(company);
        Employee employee = new Employee(1, "tom", 12, "male", 1111, company.getId());
        employeeRepository.save(employee);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("tom"))
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1111))
                .andExpect(jsonPath("$[0].companyId").value(1));

        //when//then
    }
}
