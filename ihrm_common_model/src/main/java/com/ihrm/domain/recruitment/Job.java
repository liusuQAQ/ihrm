package com.ihrm.domain.recruitment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "re_job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Serializable {
    @Id
    private String id;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date timeOfStart;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date timeOfEnd;
    /**
     * 展示状态
     */
    private int enableState;
    /**
     * 描述
     */
    private String description;
    /**
     * 企业id
     */
    private String companyId;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 部门id
     */
    private String departmentId;
    /**
     * 部门名称
     */
    private String departmentName;


}
