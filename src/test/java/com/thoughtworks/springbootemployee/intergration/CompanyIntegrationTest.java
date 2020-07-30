package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @AfterAll
    public void after() {
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
        companyRepository.save(new Company(1, "test", 100));
        companyRepository.save(new Company(2, "test1", 100));
        companyRepository.save(new Company(3, "test3", 100));

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("test"))
                .andExpect(jsonPath("$[0].employeesNumber").value(100));

        //when//then
    }
}
