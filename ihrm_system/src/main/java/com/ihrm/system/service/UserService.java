package com.ihrm.system.service;

import ch.qos.logback.core.status.StatusUtil;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;



    /*
    * 根据mobile查询用户
    * */

    public User findByMobile(String mobile){
        return userDao.findByMobile(mobile);
    }

    public void add(User user){
        // 基本属性的设置
        String id = idWorker.nextId()+"";
        user.setId(id);
        user.setPassword("123456");
        user.setEnableState(1);
        userDao.save(user);

    }

    public void update(User user){

        User user1 = userDao.findById(user.getId()).get();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setDepartmentId(user.getDepartmentId());
        user1.setDepartmentName(user.getDepartmentName());
        userDao.save(user1);

    }

    public void deleteById(String id){
        userDao.deleteById(id);
    }

    public Page findAll(Map<String, Object> map , int page ,int size){
        //1. 需要查询条件
        Specification<User> spec = new Specification<User>() {
            /*
            * 动态拼接查询条件
            * */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                // 根据请求的companyId是否为空请求查询条件
                if(!ObjectUtils.isEmpty(map.get("companyId"))){
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class),(String)map.get("companyId")));
                }
                // 根据请求的departmentId是否为空请求查询条件
                if(!ObjectUtils.isEmpty(map.get("departmentId"))){
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class),(String)map.get("departmentId")));
                }
                // 根据请求的hashDept是否为空请求查询条件
                if(!ObjectUtils.isEmpty(map.get("hashDept"))){
                    if(map.get("hashDept").equals(0)){
                        list.add(criteriaBuilder.isNull(root.get("departmentId")));
                    }else {
                        list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                    }
                }



                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //2. 需要分页
        Page<User> pageUser =  userDao.findAll(spec, PageRequest.of(page-1,size));

        return pageUser;
    }

    public User findById(String id){
        return userDao.findById(id).get();

    }

    /**
     * 分配角色
     */
    public void assignRoles(String userId,List<String> roleIds) {
        //1.根据id查询用户
        User user = userDao.findById(userId).get();
        //2.设置用户的角色集合
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        //设置用户和角色集合的关系
        user.setRoles(roles);
        //3.更新用户
        userDao.save(user);
    }


}
