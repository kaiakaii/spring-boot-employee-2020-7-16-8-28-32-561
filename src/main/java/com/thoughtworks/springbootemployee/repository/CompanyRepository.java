package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private static List<Company> companies = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            companies.add(new Company(i + 1, String.format("company-%s", i + 1), 200));
        }
    }

    public List<Company> findAll() {
        return companies;
    }

    public Company findById(int companyId) {
        return null;
    }
}
