package com.ihrm.company.controller;


import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // 添加
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Result save(@RequestBody Company company){
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);
    }

    //修改
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Company company){
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }

    //删除
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    //查询
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Company company = companyService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(company);
        return result;
    }

    //查询全部
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result findAll(){
        List<Company> companyList =  companyService.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(companyList);
        return result;
    }




}
