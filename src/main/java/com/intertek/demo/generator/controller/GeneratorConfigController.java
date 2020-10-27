package com.intertek.demo.generator.controller;

import com.intertek.demo.common.annotation.ControllerEndPoint;
import com.intertek.demo.common.controller.BaseController;
import com.intertek.demo.common.entity.ItsResponse;
import com.intertek.demo.common.exception.ItsException;
import com.intertek.demo.generator.entity.GeneratorConfig;
import com.intertek.demo.generator.service.IGeneratorConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author jacksy.qin
 * @date 2019/9/26 16:17
 */
@Slf4j
@RestController
@RequestMapping("generatorConfig")
public class GeneratorConfigController extends BaseController {

    @Autowired
    private IGeneratorConfigService generatorConfigService;

    @GetMapping
    @RequiresPermissions("generator:configure:view")
    public ItsResponse getGeneratorConfig() {
        return new ItsResponse().success().data(generatorConfigService.findGeneratorConfig());
    }

    @PostMapping("update")
    @RequiresPermissions("generator:configure:update")
    @ControllerEndPoint(operation = "修改GeneratorConfig", exceptionMessage = "修改GeneratorConfig失败")
    public ItsResponse updateGeneratorConfig(@Valid GeneratorConfig generatorConfig) {
        if (StringUtils.isBlank(generatorConfig.getId())) {
            throw new ItsException("配置id不能为空");
        }
        this.generatorConfigService.updateGeneratorConfig(generatorConfig);
        return new ItsResponse().success();
    }
}
