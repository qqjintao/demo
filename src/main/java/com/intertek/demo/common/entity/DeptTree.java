package com.intertek.demo.common.entity;

import com.intertek.demo.system.entity.Dept;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jacksy.qin
 * @date 2019/9/23 15:22
 */
@Data
public class DeptTree<T> implements Serializable {

    private static final long serialVersionUID = -5265882269864474517L;

    private String id;
    private String icon;
    private String href;
    private String name;
    private Map<String, Object> state;
    private boolean checked = false;
    private Map<String, Object> attributes;
    private List<DeptTree<T>> children;
    private String parentId;
    private boolean hasParent = false;
    private boolean hasChild = false;

    private Dept data;

    public void initChildren(){
        this.children = new ArrayList<>();
    }

}
