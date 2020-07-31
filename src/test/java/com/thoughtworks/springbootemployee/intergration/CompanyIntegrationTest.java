package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyIntegrationTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MockMvc mockMvc;


    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_new_company_when_add_company_given_company() throws Exception {
        //given
        String jsonCompany = "{\n" +
                "    \"companyName\":\"test5\",\n" +
                "    \"employeesNumber\":200\n" +
                "}";
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(jsonCompany))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("test5"))
                .andExpect(jsonPath("$.employeesNumber").value(200));
        //when
        //then
    }

    @Test
    void should_return_companies_when_get_companies_given_none() throws Exception {
        //given
        List<Company> companies = companyRepository.findAll();
        companyRepository.save(new Company(null, "test", 100));
        companyRepository.save(new Company(null, "test1", 100));
        companyRepository.save(new Company(null, "test3", 100));
        List<Company> companies1 = companyRepository.findAll();
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("test"))
                .andExpect(jsonPath("$[0].employeesNumber").value(100));

        //when//then
    }

    @Test
    void should_return_company_when_get_company_by_id_given_company_id() throws Exception {
        //given
        Company company = new Company(null, "test", 100);
        companyRepository.save(company);
        Employee employee = new Employee(null, "tom", 12, "male", 1111, company.getId());
        employeeRepository.save(employee);

        mockMvc.perform(get("/companies/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("test"))
                .andExpect(jsonPath("$.employeesNumber").value(100));

        //when//then
    }

    @Test
    void should_return_companies_when_get_companies_by_page_given_page_and_page_size() throws Exception {
        //given
        companyRepository.save(new Company(null, "test", 100));
        companyRepository.save(new Company(null, "test1", 100));
        companyRepository.save(new Company(null, "test3", 100));
        companyRepository.save(new Company(null, "test", 100));
        companyRepository.save(new Company(null, "test1", 100));
        companyRepository.save(new Company(null, "test3", 100));

        mockMvc.perform(get("/companies").contentType(MediaType.APPLICATION_JSON)
                .param("page", "1").param("pageSize", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("test"))
                .andExpect(jsonPath("$[0].employeesNumber").value(100));

        //when//then
    }

    @Test
    void should_return_none_when_delete_company_by_gender_given_company_id() throws Exception {
        //given
        Company company = new Company(null, "test", 100);
        companyRepository.save(company);
        Employee employee = new Employee(null, "tom", 12, "male", 1111, company.getId());
        employeeRepository.save(employee);


        mockMvc.perform(delete("/companies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //when//then
    }

    @Test
    void should_return_updated_company_when_update_company_by_gender_given_company_and_id() throws Exception {
        //given
        Company company = new Company(null, "test", 100);
        companyRepository.save(company);
        String jsonCompany = "{\n" +
                "    \"companyName\":\"test5\",\n" +
                "    \"employeesNumber\":200\n" +
                "}";

        mockMvc.perform(put("/companies/1").contentType(MediaType.APPLICATION_JSON).content(jsonCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("test5"))
                .andExpect(jsonPath("$.employeesNumber").value(200));

        //when//then
    }

    @Test
    void should_return_employees_when_find_employees_by_company_id_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        companyRepository.save(company);
        employeeRepository.save(new Employee(1, "tom", 12, "male", 1111, company.getId()));

        mockMvc.perform(get("/companies/{id}/employees", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("tom2"))
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[0].gender").value("female"))
                .andExpect(jsonPath("$[0].salary").value(1111));

        //when//then
    }
}
