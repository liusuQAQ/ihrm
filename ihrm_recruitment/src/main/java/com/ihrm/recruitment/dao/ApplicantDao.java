package com.ihrm.recruitment.dao;


import com.ihrm.domain.recruitment.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplicantDao extends JpaRepository<Applicant,String>, JpaSpecificationExecutor<Applicant> {

}
