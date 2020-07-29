package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {
    private static List<Company> companies;

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
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        given(companyRepository.findAll()).willReturn(companies);
        CompanyService companyService = new CompanyService(companyRepository);

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
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        when(companyRepository.save(company)).thenReturn(company);
        //when
        Company actualCompany = companyService.addCompany(company);
        //then
        assertNotNull(actualCompany);
        assertEquals(company, actualCompany);
    }
//    @Test
//    void should_return_company_when_find_company_by_id_given_company_id() {
//        //given
//        int companyId = 1;
//        CompanyRepository companyRepository = mock(CompanyRepository.class);
//        CompanyService companyService = new CompanyService(companyRepository);
//        Company expectCompany = companies.get(0);
//        given(companyRepository.findById(companyId)).willReturn(expectCompany);
//        //when
//        Company company = companyService.findById(companyId);
//        //then
//        assertNotNull(company);
//        assertEquals(expectCompany, company);
//    }

//    @Test
//    void should_return_employees_when_get_employees_by_company_id_given_company_id() {
//        //given
//        int companyId = 1;
//        CompanyRepository companyRepository = mock(CompanyRepository.class);
//        CompanyService companyService = new CompanyService(companyRepository);
//        Company expectCompany = companies.get(0);
//        given(companyRepository.findById(companyId)).willReturn(expectCompany);
//        given(companyRepository.getEmployeesByCompanyId(companyId)).willReturn(expectCompany.getEmployees());
//        //when
//        List<Employee> employees = companyService.getEmployeesByCompanyId(companyId);
//        //then
//        assertNotNull(employees);
//        assertEquals(expectCompany.getEmployees().size(), employees.size());
//        for (int i = 0; i < employees.size(); i++) {
//            assertEquals(expectCompany.getEmployees().get(i), employees.get(i));
//        }
//
//    }
//    @Test
//    void should_return_companies_when_get_companies_with_paging_given_page_and_page_size() {
//        //given
//        int page = 1;
//        int pageSize = 5;
//        CompanyRepository companyRepository = mock(CompanyRepository.class);
//        CompanyService companyService = new CompanyService(companyRepository);
//        given(companyRepository.findAllWithPaging(page,pageSize)).willReturn(companies.stream()
//                .skip((page-1)*pageSize)
//                .limit(pageSize)
//                .collect(Collectors.toList());
//
//        //when
//        List<Company> pagingCompanies = companyService.findAllWithPaging(page,pageSize);
//        //then
//        assertNotNull(employees);
//
//
//    }


}
