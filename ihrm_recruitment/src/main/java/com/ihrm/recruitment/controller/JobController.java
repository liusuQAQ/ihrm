package com.ihrm.recruitment.controller;


import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.recruitment.Job;
import com.ihrm.domain.system.Role;
import com.ihrm.recruitment.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping(value = "/recruitment")
public class JobController extends BaseController {
    @Autowired
    private JobService jobService;

    // 添加
    @RequestMapping(value = "/job",method = RequestMethod.POST)
    public Result save(@RequestBody Job job){
        System.out.println(job.getTimeOfEnd());
        System.out.println(job.getTimeOfStart());
        jobService.add(job);

        return new Result(ResultCode.SUCCESS);
    }

    //修改
    @RequestMapping(value = "/job/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Job job){
        job.setId(id);
        jobService.update(job);
        return new Result(ResultCode.SUCCESS);
    }

    //删除
    @RequestMapping(value = "/job/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        jobService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    //查询
    @RequestMapping(value = "/job/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Job job = jobService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(job);
        return result;
    }

    //查询全部
//    @RequestMapping(value = "/job",method = RequestMethod.GET)
//    public Result findAll(){
//        List<Job> jobList =  jobService.findAll();
//        Result result = new Result(ResultCode.SUCCESS);
//        result.setData(jobList);
//        return result;
//    }
//
    @RequestMapping(value = "/job", method = RequestMethod.GET)
    public Result findByPage(int page, int pagesize) throws Exception {

        Page<Job> searchPage = jobService.findByPage(companyId, page, pagesize);
        PageResult<Job> pr = new PageResult(searchPage.getTotalElements(),searchPage.getContent());
        return new Result(ResultCode.SUCCESS,pr);
    }

}
