package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.*;
import com.ihrm.system.dao.*;
import com.ihrm.system.dao.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionPointDao permissionPointDao;
    @Autowired
    private PermissionApiDao permissionApiDao;
    /**
     * 添加权限
     */
    public void save(Map<String,Object> map) throws Exception {
        //填充其他参数
        String id = idWorker.nextId()+"";
        //1.通过map构造权限对象
        Permission permission = BeanMapUtils.mapToBean(map,Permission.class);
        permission.setId(id);

        //2.根据类型构造不同的资源对象
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PY_MENU :
                PermissionMenu permissionMenu =  BeanMapUtils.mapToBean(map,PermissionMenu.class);
                permissionMenu.setId(id);
                permissionMenuDao.save(permissionMenu);
                break;

            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map,PermissionPoint.class);
                permissionPoint.setId(id);
                permissionPointDao.save(permissionPoint);
                break;

            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map,PermissionApi.class);
                permissionApi.setId(id);
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);

        }
        //3.保存
        permissionDao.save(permission);

    }
    /**
     * 更新权限
     */
    public void update(Map<String,Object> map) throws Exception {
        Permission permission = BeanMapUtils.mapToBean(map,Permission.class);
        //1.根据传递的id查权限
        Permission perm = permissionDao.findById(permission.getId()).get();
        perm.setName(permission.getName());
        perm.setCode(permission.getCode());
        perm.setEnVisible(permission.getEnVisible());
        perm.setDescription(permission.getDescription());
        //2.根据类型构造不同的资源
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PY_MENU :
                PermissionMenu permissionMenu =  BeanMapUtils.mapToBean(map,PermissionMenu.class);
                permissionMenu.setId(permission.getId());
                permissionMenuDao.save(permissionMenu);
                break;

            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map,PermissionPoint.class);
                permissionPoint.setId(permission.getId());
                permissionPointDao.save(permissionPoint);
                break;

            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map,PermissionApi.class);
                permissionApi.setId(permission.getId());
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);

        }

        //3.保存
        permissionDao.save(permission);
    }
    /**
     * 根据ID查询角色
     * 1.查询权限
     * 2.根据权限类型查询资源
     * 3.构造map集合
     */
    public Map<String,Object> findById(String id) throws CommonException {
        Permission perm = permissionDao.findById(id).get();
        int type = perm.getType();

        Object object = null;
        if(type==PermissionConstants.PY_MENU){
            object = permissionMenuDao.findById(id).get();
        }else if(type==PermissionConstants.PY_POINT) {
            object = permissionPointDao.findById(id).get();
        }else if(type==PermissionConstants.PY_API) {
            object = permissionApiDao.findById(id).get();
        }else {
            throw new CommonException(ResultCode.FAIL);
        }
        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("name",perm.getName());
        map.put("type",perm.getType());
        map.put("code",perm.getCode());
        map.put("description",perm.getDescription());
        map.put("pid",perm.getPid());
        map.put("enVisible",perm.getEnVisible());
        return map;
    }

    /**
     * 1.删除权限
     * 2.删除权限对应的资源
     */
    public void deleteById(String id) throws CommonException {
        Permission permission = permissionDao.findById(id).get();
        permissionDao.delete(permission);
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PY_MENU :
                permissionMenuDao.deleteById(id);
                break;

            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(id);
                break;

            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);

        }
        permissionDao.deleteById(id);
    }

    /*
    * 查询权限列表
    * type:0:1+2 1:菜单 2：按钮 3.api
    * enVisible:0：查询所有Saas企业的最高权限 1：查询企业权限
    * */
    public List<Permission> findAll(Map<String,Object> map) {
        //1.查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            /*
             * 动态拼接查询条件
             * */
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                // 根据请求的pid是否为空请求查询条件
                if(!ObjectUtils.isEmpty(map.get("pid"))){
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class),(String)map.get("pid")));
                }
                // 根据请求的enVisible是否为空请求查询条件
                if(!ObjectUtils.isEmpty(map.get("enVisible"))){
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class),(String)map.get("enVisible")));
                }
                // 根据请求的hashDept是否为空请求查询条件

                if(!ObjectUtils.isEmpty(map.get("type"))){
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                   String ty = (String) map.get("type");
                   if("0".equals(ty)){
                       in.value(1).value(2);
                   }else {
                       in.value(Integer.parseInt(ty));
                   }

                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(spec);
    }
}
