package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company findById(int companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Company> findAll(int page, int pageSize) {
        if (page < 1 || pageSize < 0) {
            return null;
        }
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }

    public boolean deleteById(int companyId) {
        return false;
    }
}
