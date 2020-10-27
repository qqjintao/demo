package com.intertek.demo.generator.controller;

import com.intertek.demo.common.entity.ItsConstant;
import com.intertek.demo.common.utils.ItsUtil;
import com.intertek.demo.generator.entity.GeneratorConfig;
import com.intertek.demo.generator.service.IGeneratorConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jacksy.qin
 * @date 2019/9/26 16:17
 */
@Controller("generatorViews")
@RequestMapping(ItsConstant.VIEW_PREFIX + "generator")
public class ViewController {

    @Autowired
    private IGeneratorConfigService generatorConfigService;

    @GetMapping("generator")
    @RequiresPermissions("generator:view")
    public String generator() {
        return ItsUtil.view("generator/generator");
    }

    @GetMapping("configure")
    @RequiresPermissions("generator:configure:view")
    public String generatorConfigure(Model model) {
        GeneratorConfig generatorConfig = generatorConfigService.findGeneratorConfig();
        model.addAttribute("config", generatorConfig);
        return ItsUtil.view("generator/configure");
    }
}
