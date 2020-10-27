package com.intertek.demo.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intertek.demo.common.entity.DeptTree;
import com.intertek.demo.common.entity.ItsConstant;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.common.utils.SortUtil;
import com.intertek.demo.common.utils.TreeUtil;
import com.intertek.demo.system.entity.Dept;
import com.intertek.demo.system.mapper.DeptMapper;
import com.intertek.demo.system.service.IDeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 15:22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Override
    public List<DeptTree<Dept>> findDepts() {
        List<Dept> depts = this.baseMapper.selectList(new QueryWrapper<>());
        List<DeptTree<Dept>> trees = this.convertDepts(depts);
        return TreeUtil.buildDeptTree(trees);
    }

    @Override
    public List<DeptTree<Dept>> findDepts(Dept dept) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getDeptName())) {
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        }
        queryWrapper.lambda().orderByAsc(Dept::getOrderNum);

        List<Dept> depts = this.baseMapper.selectList(queryWrapper);
        List<DeptTree<Dept>> trees =  this.convertDepts(depts);
        return TreeUtil.buildDeptTree(trees);
    }

    @Override
    public List<Dept> findDepts(Dept dept, QueryRequest request) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getDeptName())) {
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        }
        SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", ItsConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void createDept(Dept dept) {
        Long parentId = dept.getParentId();
        if (parentId == null) {
            dept.setParentId(0L);
        }
        dept.setCreateTime(new Date());
        this.save(dept);
    }

    @Override
    @Transactional
    public void updateDept(Dept dept) {
        dept.setModifyTime(new Date());
        this.baseMapper.updateById(dept);
    }

    @Override
    @Transactional
    public void deleteDepts(String[] deptIds) {
       this.delete(Arrays.asList(deptIds));
    }

    private List<DeptTree<Dept>> convertDepts(List<Dept> depts){
        List<DeptTree<Dept>> trees = new ArrayList<>();
        depts.forEach(dept -> {
            DeptTree<Dept> tree = new DeptTree<>();
            tree.setId(String.valueOf(dept.getDeptId()));
            tree.setParentId(String.valueOf(dept.getParentId()));
            tree.setName(dept.getDeptName());
            tree.setData(dept);
            trees.add(tree);
        });
        return trees;
    }

    private void delete(List<String> deptIds) {
        removeByIds(deptIds);

        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dept::getParentId, deptIds);
        List<Dept> depts = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(depts)) {
            List<String> deptIdList = new ArrayList<>();
            depts.forEach(d -> deptIdList.add(String.valueOf(d.getDeptId())));
            this.delete(deptIdList);
        }
    }
}
