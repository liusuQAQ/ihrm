package com.ihrm.company.controller;

/*
* 1.解决跨域
* 2.声明restController
* 3.设置父路径
* */

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/company")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    // 添加
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department){
        department.setCompanyId(companyId);
        System.out.println(department.getPid());
        departmentService.add(department);
        return new Result(ResultCode.SUCCESS);
    }

    //修改
    @RequestMapping(value = "/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Department department){
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    //删除
    @RequestMapping(value = "/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    //查询
    @RequestMapping(value = "/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Department department = departmentService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(department);
        return result;
    }

    //查询全部
    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll(){
        List<Department> companyList =  departmentService.findAll(companyId);
        Company company = companyService.findById(companyId);
        DeptListResult deptListResult = new DeptListResult(company,companyList);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(deptListResult);
        return result;
    }

}
