package com.intertek.demo.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.intertek.demo.generator.entity.Column;
import com.intertek.demo.generator.entity.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jacksy.qin
 * @date 2019/9/26 16:17
 */
public interface GeneratorMapper {

    List<String> getDatabases(@Param("databaseType") String databaseType);

    IPage<Table> getTables(Page page, @Param("tableName") String tableName, @Param("databaseType") String databaseType, @Param("schemaName") String schemaName);

    List<Column> getColumns(@Param("databaseType") String databaseType, @Param("schemaName") String schemaName, @Param("tableName") String tableName);
}
