package com.ihrm.recruitment.dao;


import com.ihrm.domain.recruitment.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobDao extends JpaRepository<Job,String>, JpaSpecificationExecutor<Job> {
}
