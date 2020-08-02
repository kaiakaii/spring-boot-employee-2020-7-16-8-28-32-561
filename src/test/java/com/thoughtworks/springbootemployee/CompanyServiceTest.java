package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    private static List<Company> companies;
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;
    @BeforeAll
    static void init() {
        companies = new ArrayList<>();
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

    @Test
    void should_return_companies_when_find_all_companies_given_company_service() {
        //given
        given(companyRepository.findAll()).willReturn(companies);
        //when
        List<Company> actualCompanies = companyService.findAll();
        //then
        assertNotNull(actualCompanies);
        assertEquals(10, actualCompanies.size());
    }

    @Test
    void should_return_employees_when_add_employee_given_employee() {
        //given
        Company company = new Company(1, "test", 100);
        when(companyRepository.save(company)).thenReturn(company);
        //when
        Company actualCompany = companyService.addCompany(company);
        //then
        assertNotNull(actualCompany);
        assertEquals(company, actualCompany);
    }

    @Test
    void should_return_company_when_find_company_by_id_given_company_id() {
        //given
        int companyId = 1;
        Company expectCompany = companies.get(0);
        given(companyRepository.findById(companyId)).willReturn(Optional.of(expectCompany));
        //when
        Company company = companyService.findById(companyId);
        //then
        assertNotNull(company);
        assertEquals(expectCompany, company);
    }

    @Test
    void should_return_employees_when_get_employees_by_company_id_given_company_id() {
        //given
        int companyId = 1;
        Company expectCompany = companies.get(0);
        given(companyRepository.findById(companyId)).willReturn(Optional.of(expectCompany));
        //when
        List<Employee> employees = companyService.getEmployeesByCompanyId(companyId);
        //then
        assertNotNull(employees);
        assertEquals(expectCompany.getEmployees().size(), employees.size());
        for (int i = 0; i < employees.size(); i++) {
            assertEquals(expectCompany.getEmployees().get(i), employees.get(i));
        }

    }
    @Test
    void should_return_companies_when_get_companies_with_paging_given_page_and_page_size() {
        //given
        int page = 1;
        int pageSize = 5;
        when(companyRepository.findAll(PageRequest.of(page, pageSize).first())).thenReturn(new PageImpl<>(Arrays.asList(
                new Company(1, "test", 100),
                new Company(2, "test2", 100))));
        //when
        List<Company> companyList = companyService.findAll(page, pageSize);
        //then
        assertNotNull(companyList);
        assertEquals(2, companyList.size());


    }

    @Test
    void should_return_true_when_delete_company_given_company_id() {
        //given
        int companyId = 1;
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(
                new Company(1, "test", 100)));
        //when
        boolean isDelete = companyService.deleteById(companyId);
        //then
        assertTrue(isDelete);
    }

    @Test
    void should_return_update_company_when_update_company_by_id_given_company_id() {
        //given
        int companyId = 1;
        Company updateCompany = new Company(1, "test", 100);
        when(companyRepository.save(updateCompany)).thenReturn(updateCompany);
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(updateCompany));
        //when
        Company company = companyService.updateCompanyById(companyId, updateCompany);
        //then
        assertNotNull(company);
        assertEquals(updateCompany, company);
    }

}
