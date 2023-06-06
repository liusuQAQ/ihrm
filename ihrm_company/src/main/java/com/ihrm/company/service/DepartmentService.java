package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
public class DepartmentService extends BaseService {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private IdWorker idWorker;

    // 添加部门
    public void add(Department department){
        // 基本属性的设置
        String id = idWorker.nextId()+"";
        department.setId(id);
        Date data = new Date();
        department.setCreateTime(data);
        departmentDao.save(department);
    }



    // 删除部门
    public void deleteById(String id){
        departmentDao.deleteById(id);
    }

    // 更改部门
    public void update(Department department){
        Department dept = departmentDao.findById(department.getId()).get();
        //修改属性
        dept.setCode(department.getCode());

        departmentDao.save(dept);
    }

    //根据id查询部门
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }

    //查询全部部门
    public List<Department> findAll(String companyId){

        return departmentDao.findAll(getSpec(companyId));
    }
}
