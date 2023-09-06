package com.lihicouponsystem.mapper.company;

import com.lihicouponsystem.entities.Company;
import com.lihicouponsystem.web.dto.CompanyDto;

public interface CompanyMapper {
    Company map(CompanyDto companyDto);

    CompanyDto map(Company company);
}
