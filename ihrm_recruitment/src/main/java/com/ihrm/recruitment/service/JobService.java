package com.ihrm.recruitment.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.recruitment.Job;
import com.ihrm.domain.system.Role;
import com.ihrm.recruitment.dao.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobService extends BaseService {

    @Autowired
    private JobDao jobDao;
    @Autowired
    private IdWorker idWorker;

    /*
     * 保存岗位
     * 1.配置idwork到工程
     * 2.注入idwork
     * 3.通过idwork生成id
     * 4.保存岗位
     *
     * */

    public void add(Job job){
        // 基本属性的设置
        String id = idWorker.nextId()+"";
        job.setId(id);
        // 默认状态
        job.setTimeOfStart(new Date());
        job.setTimeOfEnd(new Date());
        job.setCompanyId("1");
        job.setCompanyName("hitwh");
        jobDao.save(job);

    }

    /*
     * 更新岗位
     * */
    public void update(Job job){
        Job job1 = jobDao.findById(job.getId()).get();
//        修改
        job1.setName(job.getName());
        job1.setTimeOfStart(job.getTimeOfStart());
        job1.setTimeOfEnd(job.getTimeOfEnd());
        job1.setDescription(job.getDescription());
        job1.setEnableState(job.getEnableState());
        job1.setDepartmentId(job.getDepartmentId());
        job1.setDepartmentName(job.getDepartmentName());

        jobDao.save(job1);


    }

    /*
     * 删除岗位
     * */

    public void deleteById(String id){
        jobDao.deleteById(id);
    }
    /*
     *
     * 通过id查询
     * */
    public Job findById(String id){
        return jobDao.findById(id).get();
    }

    /*
     *
     * 查询岗位列表
     * */
    public List<Job> findAll(){
        return jobDao.findAll();
    }

    public Page<Job> findByPage(String companyId, int page, int size) {
        return jobDao.findAll(getSpec(companyId), PageRequest.of(page-1, size));
    }
}
