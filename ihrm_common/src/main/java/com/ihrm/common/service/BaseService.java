package com.ihrm.common.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class BaseService<T> {
    protected Specification<T> getSpec(String companyId){
        Specification<T> spect = new Specification<T>() {
            @Override
            /*
             * 构造查询条件
             * root：包含所有的对象属性
             * cq：一般不用
             * cb：构造查询条件
             * */
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 根据企业ID进行查询
                return criteriaBuilder.equal(root.get("companyId").as(String.class),companyId);
            }
        };
        return spect;
    }

    protected Specification<T> getSpec1(int state){
        Specification<T> spect = new Specification<T>() {
            @Override
            /*
             * 构造查询条件
             * root：包含所有的对象属性
             * cq：一般不用
             * cb：构造查询条件
             * */
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 根据企业ID进行查询
                return criteriaBuilder.equal(root.get("progressState").as(int.class),state);
            }
        };
        return spect;
    }

}
