package com.ihrm.recruitment.service;


import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.recruitment.Applicant;
import com.ihrm.domain.recruitment.Job;
import com.ihrm.recruitment.dao.ApplicantDao;
import com.ihrm.recruitment.dao.ApplicantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
public class ApplicantService extends BaseService {
    @Autowired
    private ApplicantDao applicantDao;
    @Autowired
    private IdWorker idWorker;

    /*
     * 保存简历
     * 1.配置idwork到工程
     * 2.注入idwork
     * 3.通过idwork生成id
     * 4.保存简历
     *
     * */

    public void add(Applicant applicant){
        // 基本属性的设置
        String id = idWorker.nextId()+"";
        applicant.setId(id);
        // 默认状态
        //ProgressState: 0：简历初筛 1：笔试 2：专业面试 3：主管面试
        applicant.setProgressState(0);
        //PassState：0：未开始 1：通过 2：未通过
        applicant.setPassState(0);

        applicant.setCompanyId("1");
        applicant.setCompanyName("hitwh");

        applicantDao.save(applicant);

    }

    /*
     * 更新简历
     * */
    public void update(Applicant applicant){
        Applicant applicant1 = applicantDao.findById(applicant.getId()).get();
//        修改
        applicant1.setProgressState(applicant.getProgressState());
        applicant1.setPassState(applicant.getPassState());
        applicant1.setWrittenTestTime(applicant.getWrittenTestTime());
        applicant1.setInterviewTime(applicant.getInterviewTime());
        applicant1.setInterviewerId(applicant.getInterviewerId());
        applicant1.setOffer(applicant.getOffer());

        applicantDao.save(applicant1);


    }

    /*
     * 删除简历
     * */

    public void deleteById(String id){
        applicantDao.deleteById(id);
    }
    /*
     *
     * 通过id查询
     * */
    public Applicant findById(String id){
        return applicantDao.findById(id).get();
    }

    /*
     *
     * 查询企业列表
     * */
    public List<Applicant> findAll(){
        return applicantDao.findAll();
    }

    public Page<Applicant> findByPage(String companyId, int page, int size) {

        return applicantDao.findAll(getSpec(companyId), PageRequest.of(page-1, size));
    }

    public Page<Applicant> findByState(String companyId, int page, int size, int state) {

        return applicantDao.findAll(getSpec1(state), PageRequest.of(page-1, size));
    }
}
