package com.ihrm.company.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private IdWorker idWorker;

    /*
    * 保存企业
    * 1.配置idwork到工程
    * 2.注入idwork
    * 3.通过idwork生成id
    * 4.保存企业
    *
    * */

    public void add(Company company){
        // 基本属性的设置
        String id = idWorker.nextId()+"";
        company.setId(id);
        // 默认状态
        company.setAuditState("1"); //0:未审核 1：已审核
        company.setState(1); //0:未激活 1：已激活
        companyDao.save(company);

    }

    /*
    * 更新企业
    * */
    public void update(Company company){

    }

    /*
    * 删除
    * */

    public void deleteById(String id){
        companyDao.deleteById(id);
    }
    /*
    *
    * 通过id查询
    * */
    public Company findById(String id){
        return companyDao.findById(id).get();
    }

    /*
    *
    * 查询企业列表
    * */
    public List<Company> findAll(){
        return companyDao.findAll();
    }



}
