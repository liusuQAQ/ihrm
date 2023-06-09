package com.ihrm.recruitment.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.recruitment.Applicant;
import com.ihrm.domain.recruitment.Job;
import com.ihrm.domain.system.Role;
import com.ihrm.recruitment.service.ApplicantService;
import com.ihrm.recruitment.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping(value = "/recruitment")
public class ApplicantController extends BaseController {
    @Autowired
    private ApplicantService applicantService;

    // 添加
    @RequestMapping(value = "/applicant",method = RequestMethod.POST)
    public Result save(@RequestBody Applicant applicant){
        applicantService.add(applicant);
        return new Result(ResultCode.SUCCESS);
    }

    //修改
    @RequestMapping(value = "/applicant/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Applicant applicant){
        applicant.setId(id);
        applicantService.update(applicant);
        return new Result(ResultCode.SUCCESS);
    }

    //删除
    @RequestMapping(value = "/applicant/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        applicantService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    //查询
    @RequestMapping(value = "/applicant/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Applicant applicant = applicantService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(applicant);
        return result;
    }

//    //查询全部
//    @RequestMapping(value = "/applicant",method = RequestMethod.GET)
//    public Result findAll(){
//        List<Applicant> applicantList =  applicantService.findAll();
//        Result result = new Result(ResultCode.SUCCESS);
//        result.setData(applicantList);
//        return result;
//
////        Page<Role> searchPage = roleService.findByPage(companyId, page, pagesize);
////        PageResult<Role> pr = new PageResult(searchPage.getTotalElements(),searchPage.getContent());
////        return new Result(ResultCode.SUCCESS,pr);
//    }

    @RequestMapping(value = "/applicant", method = RequestMethod.GET)
    public Result findByPage(int page, int pagesize) throws Exception {

        Page<Applicant> searchPage = applicantService.findByPage(companyId, page, pagesize);
        PageResult<Applicant> pr = new PageResult(searchPage.getTotalElements(),searchPage.getContent());
        return new Result(ResultCode.SUCCESS,pr);
    }
    @RequestMapping(value = "/applicant/state", method = RequestMethod.GET)
    public Result findByState(int page, int pagesize, int state) throws Exception {

        Page<Applicant> searchPage = applicantService.findByState(companyId, page, pagesize,state);
        PageResult<Applicant> pr = new PageResult(searchPage.getTotalElements(),searchPage.getContent());
        return new Result(ResultCode.SUCCESS,pr);

    }

}
