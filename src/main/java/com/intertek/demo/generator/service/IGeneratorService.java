package com.intertek.demo.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.intertek.demo.common.entity.QueryRequest;
import com.intertek.demo.generator.entity.Column;
import com.intertek.demo.generator.entity.Table;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/26 16:17
 */
public interface IGeneratorService {

    List<String> getDatabases(String databaseType);

    IPage<Table> getTables(String tableName, QueryRequest request, String databaseType, String schemaName);

    List<Column> getColumns(String databaseType, String schemaName, String tableName);
}
