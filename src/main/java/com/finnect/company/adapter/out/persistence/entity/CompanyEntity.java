package com.finnect.company.adapter.out.persistence.entity;

import com.finnect.cell.adapter.out.persistence.DataRowEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long companyId;


    @OneToOne(fetch = FetchType.EAGER)
    @Getter
    private DataRowEntity dataRowEntity;

}
