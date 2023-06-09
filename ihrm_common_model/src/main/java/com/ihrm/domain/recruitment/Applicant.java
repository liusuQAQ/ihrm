package com.ihrm.domain.recruitment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "re_applicant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Applicant implements Serializable {
    @Id
    private String id;

    private String name;

    private String mobile;

    private String email;

    private int gender;

    private String jobId;

    private String jobName;

    private String curriculumVitae;

    private int progressState;

    private int passState;

    private Date writtenTestTime;

    private String interviewerId;
    private Date interviewTime;

    private int offer;
    /**
     * 企业id
     */
    private String companyId;
    /**
     * 企业名称
     */
    private String companyName;

    private String comments;

}
