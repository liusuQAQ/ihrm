package com.ihrm.system.controller;


import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/system")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    // 添加
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> map) throws Exception {
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }

    //修改
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Map<String,Object> map) throws Exception {
        map.put("id",id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    //删除
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) throws CommonException {
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    //查询
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) throws CommonException {
        System.out.println("controller");
        Map map = permissionService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(map);
        return result;
    }

    //查询全部
    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public Result findAll(@RequestParam Map<String,Object> map){
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS,list);
    }
}
