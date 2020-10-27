package com.intertek.demo.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Barry.Huang
 * @date 10/15/2019 1:44 PM
 */
@Data
public class TreeEntity<T> implements Serializable {

    private static final long serialVersionUID = 1746029226906939421L;

    private String id;
    private String icon;
    private String href;
    private String title;
    private Map<String, Object> state;
    private boolean checked = false;
    private Map<String, Object> attributes;
    private List<TreeEntity<T>> children = new ArrayList<>();
    private String parentId;
    private boolean hasParent = false;
    private boolean hasChild = false;

    private Object data;

}
