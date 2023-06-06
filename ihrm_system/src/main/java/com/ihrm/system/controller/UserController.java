package com.ihrm.system.controller;


import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.reponse.ProfileResult;
import com.ihrm.domain.system.reponse.UserResult;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/system")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PermissionService permissionService;

    // 添加
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result save(@RequestBody User user){
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        userService.add(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 分配角色
     */
    @RequestMapping(value = "/user/assignRoles", method = RequestMethod.PUT)
    public Result save(@RequestBody Map<String,Object> map) {
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //3.调用service完成角色分配
        userService.assignRoles(userId,roleIds);
        return new Result(ResultCode.SUCCESS);
    }



    //修改
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    //删除
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据ID查询user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        // 添加 roleIds (用户已经具有的角色id数组)
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS, userResult);
    }

    //查询全部
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findAll(int page , int size, @RequestParam Map map){
        map.put("companyId",companyId);
        Page<User> pageUser =  userService.findAll(map,page,size);
        PageResult<User> pageResult = new PageResult(pageUser.getTotalElements(),pageUser.getContent());

        return new Result(ResultCode.SUCCESS,pageResult);
    }
    /*
    * 1.查询用户
    * 2.比较密码
    * 3.生成jwt信息
    *
    * */

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login( @RequestBody Map<String,String> loginMap){
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        System.out.println(mobile);
        User user = userService.findByMobile(mobile);
        System.out.println(user.getId());
        if(user == null || !user.getPassword().equals(password)){
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else {
            //登录成功
            Map<String,Object> map = new HashMap<>();
            System.out.println(user.getId());
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS,token);

        }
    }

    /*
    * 用户登录成功之后获取用户信息
    * 1.获取用户id
    * 2.查询用户
    * 3.构建返回值对象
    *
    *
    * */
    @RequestMapping(value = "/profile",method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {

        String authorization = request.getHeader("Authorization");
        if(ObjectUtils.isEmpty(authorization)){
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }

        String token = authorization.replace("Bearer","");
        // 解析token
        Claims claims = jwtUtils.parseJwt(token);

        String userId = claims.getId();
        User user = userService.findById(userId);

        ProfileResult result = null;

        if("user".equals(user.getLevel())) {
            result = new ProfileResult(user);
        }else {
            Map map = new HashMap();
            if("coAdmin".equals(user.getLevel())) {
                map.put("enVisible","1");
            }
            List<Permission> list = permissionService.findAll(map);
            result = new ProfileResult(user,list);
        }

        return new Result(ResultCode.SUCCESS,result);

    }
}
