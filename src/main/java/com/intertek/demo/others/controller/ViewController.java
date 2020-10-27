package com.intertek.demo.others.controller;

import com.intertek.demo.common.entity.ItsConstant;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.utils.ItsUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jacksy.qin
 * @date 2019/9/26 10:42
 */
@Controller("othersView")
@RequestMapping(ItsConstant.VIEW_PREFIX + "others")
public class ViewController {

    @GetMapping("febs/form")
    @RequiresPermissions("febs:form:view")
    public String febsForm(){
        return ItsUtil.view("others/febs/form");
    }


    @GetMapping("febs/form/group")
    @RequiresPermissions("febs:formgroup:view")
    public String febsFormGroup() {
        return ItsUtil.view("others/febs/formGroup");
    }

    @GetMapping("febs/tools")
    @RequiresPermissions("febs:tools:view")
    public String febsTools() {
        return ItsUtil.view("others/febs/tools");
    }

    @GetMapping("febs/icon")
//    @RequiresPermissions("febs:icons:view")
    public String febsIcon() {
        return ItsUtil.view("others/febs/icon");
    }

    @GetMapping("febs/others")
    @RequiresPermissions("others:febs:others")
    public String febsOthers() {
        return ItsUtil.view("others/febs/others");
    }

    @GetMapping("eximport")
    @RequiresPermissions("others:eximport:view")
    public String eximport(){return ItsUtil.view("others/eximport/eximport"); }

    @GetMapping("eximport/result")
    public String eximportResult(){return ItsUtil.view("others/eximport/eximportResult"); }

    @GetMapping("apex/line")
    @RequiresPermissions("apex:line:view")
    public String apexLine() {
        return ItsUtil.view("others/apex/line");
    }

    @GetMapping("apex/area")
    @RequiresPermissions("apex:area:view")
    public String apexArea() {
        return ItsUtil.view("others/apex/area");
    }

    @GetMapping("apex/column")
    @RequiresPermissions("apex:column:view")
    public String apexColumn() {
        return ItsUtil.view("others/apex/column");
    }

    @GetMapping("apex/radar")
    @RequiresPermissions("apex:radar:view")
    public String apexRadar() {
        return ItsUtil.view("others/apex/radar");
    }

    @GetMapping("apex/bar")
    @RequiresPermissions("apex:bar:view")
    public String apexBar() {
        return ItsUtil.view("others/apex/bar");
    }

    @GetMapping("apex/mix")
    @RequiresPermissions("apex:mix:view")
    public String apexMix() {
        return ItsUtil.view("others/apex/mix");
    }

}
