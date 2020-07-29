package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
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

public class CompanyServiceTest {
    private static List<Company> companies;

    @BeforeAll
    static void init() {
        companies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            companies.add(new Company(i + 1, String.format("company-%s", i + 1), 200));
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
    void should_return_company_when_find_company_by_id_given_company_id() {
        //given
        int companyId = 1;
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyService companyService = new CompanyService(companyRepository);
        Company expectCompany = companies.get(0);
        given(companyRepository.findById(companyId)).willReturn(expectCompany);
        //when
        Company company = companyService.findById(companyId);
        //then
        assertNotNull(company);
        assertEquals(expectCompany, company);
    }
}
