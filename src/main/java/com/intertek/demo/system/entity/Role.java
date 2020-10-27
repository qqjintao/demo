package com.intertek.demo.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.intertek.demo.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jacksy.qin
 * @date 2019/9/23 14:06
 */
@Data
@TableName("t_role")
@Excel("角色信息表")
public class Role implements Serializable {

    private static final long serialVersionUID = -9075955524458741705L;

    /**
     * 角色ID
     */
    @TableId(value = "ROLE_ID", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    @ExcelField(value = "角色名称")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("REMARK")
    @ExcelField(value = "角色描述")
    @Size(max = 50, message = "{noMoreThan}")
    private String remark;

    /**
     * 创建者id
     */
    @TableField("CREATE_ID")
    @ExcelField(value = "创建者id")
    private Integer createId;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 修改者id
     */
    @TableField("MODIFY_ID")
    @ExcelField(value = "修改者id")
    private Integer modifyId;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    /**
     * 角色对应的菜单（按钮） id
     * transient：不需序列化设置
     */
    private transient String menuIds;
}
