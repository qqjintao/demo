package com.intertek.demo.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.entity.DeptTree;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.common.exception.ItsException;
import com.intertek.demo.system.entity.Dept;
import com.intertek.demo.system.service.IDeptService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/23 16:16
 */
@Slf4j
@RestController
@RequestMapping("dept")
public class DeptController{

    @Autowired
    private IDeptService deptService;

    @GetMapping("select/tree")
    @ControllerEndPoint(exceptionMessage = "获取部门树失败")
    public List<DeptTree<Dept>> getDeptTree() throws ItsException {
        return this.deptService.findDepts();
    }

    @GetMapping("getTree")
    @ControllerEndPoint(exceptionMessage = "获取部门树失败")
    public List<DeptTree<Dept>> getDeptTreeRegist() throws ItsException {
        List<DeptTree<Dept>> data=this.deptService.findDepts();
        List<DeptTree<Dept>> out =new ArrayList<>();
        for (DeptTree tree:data){
            if(tree.getName().equals("Supplier")||tree.getName().equals("Buyer")){
                out.add(tree);
            }
        }
        return out;
    }

    @GetMapping("tree")
    @ControllerEndPoint(exceptionMessage = "获取部门树失败")
    public ItsResponse getDeptTree(Dept dept) throws ItsException {
        List<DeptTree<Dept>> depts = this.deptService.findDepts(dept);
        return new ItsResponse().success().data(depts);
    }

    @PostMapping
    @RequiresPermissions("dept:add")
    @ControllerEndPoint(operation = "新增部门", exceptionMessage = "新增部门失败")
    public ItsResponse addDept(@Valid Dept dept) {
        this.deptService.createDept(dept);
        return new ItsResponse().success();
    }

    @GetMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    @ControllerEndPoint(operation = "删除部门", exceptionMessage = "删除部门失败")
    public ItsResponse deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws ItsException {
        String[] ids = deptIds.split(StringPool.COMMA);
        this.deptService.deleteDepts(ids);
        return new ItsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("dept:update")
    @ControllerEndPoint(operation = "修改部门", exceptionMessage = "修改部门失败")
    public ItsResponse updateDept(@Valid Dept dept) throws ItsException {
        this.deptService.updateDept(dept);
        return new ItsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("dept:export")
    @ControllerEndPoint(exceptionMessage = "导出Excel失败")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws ItsException {
        List<Dept> depts = this.deptService.findDepts(dept, request);
        ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
    }
}
