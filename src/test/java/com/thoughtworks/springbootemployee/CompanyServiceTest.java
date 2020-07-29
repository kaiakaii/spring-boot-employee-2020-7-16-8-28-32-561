package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyServiceTest {
    private List<Company> companies;

    @BeforeAll
    public void init(){
        companies = new ArrayList<>();
        for (int i=0;i<10;i++){
            companies.add(new Company(i+1, String.format("company-%s", i+1),200));
        }
    }
    @Test
    void should_return_companies_when_find_all_companies_given_company_service() {
        //given
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        given(companyRepository.findAll()).willReturn(companies);
        CompanyService companyService = new CompanyService(companyRepository);

        //when
        List<Company> companies = companyService.findAll();
        //then
        assertNotNull(companies);
        assertEquals(10,companies.size());
    }
}
