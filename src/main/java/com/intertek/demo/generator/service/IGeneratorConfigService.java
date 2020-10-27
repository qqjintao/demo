package com.intertek.demo.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intertek.demo.generator.entity.GeneratorConfig;

/**
 * @author jacksy.qin
 * @date 2019/9/26 16:17
 */
public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfig findGeneratorConfig();

    /**
     * 修改
     *
     * @param generatorConfig generatorConfig
     */
    void updateGeneratorConfig(GeneratorConfig generatorConfig);

}
