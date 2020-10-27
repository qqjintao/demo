package com.intertek.demo.common.utils;

import com.intertek.demo.common.entity.DeptTree;
import com.intertek.demo.common.entity.MenuTree;
import com.intertek.demo.common.entity.TreeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jacksy.qin
 * @date 2019/9/23 15:22
 */
public class TreeUtil {

    protected TreeUtil() {

    }

    public static <T> MenuTree<T> buildMenuTree(List<MenuTree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<MenuTree<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || "0".equals(pid)) {
                topNodes.add(children);
                return;
            }
            for (MenuTree<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChilds().add(children);
                    children.setHasParent(true);
                    parent.setHasChild(true);
                    return;
                }
            }
        });

        MenuTree<T> root = new MenuTree<>();
        root.setId("0");
        root.setParentId("");
        root.setHasParent(false);
        root.setHasChild(true);
        root.setChecked(true);
        root.setChilds(topNodes);
        Map<String, Object> state = new HashMap<>(16);
        root.setState(state);
        return root;
    }

    public static <T> List<DeptTree<T>> buildDeptTree(List<DeptTree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<DeptTree<T>> result = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || "0".equals(pid)) {
                result.add(children);
                return;
            }
            for (DeptTree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(children);
                    children.setHasParent(true);
                    n.setHasChild(true);
                    return;
                }
            }
        });

        return result;
    }

    /**
     *树形结构 构建树
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> List<TreeEntity<T>> buildCategoryTree(List<TreeEntity<T>> entity) {
        if (entity == null) {
            return null;
        }
        List<TreeEntity<T>> result = new ArrayList<>();
        entity.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || "0".equals(pid)||"null".equalsIgnoreCase(pid)) {
                result.add(children);
                return;
            }
            for (TreeEntity<T> n : entity) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    n.getChildren().add(children);
                    children.setHasParent(true);
                    n.setHasChild(true);
                    return;
                }
            }
        });

        return result;
    }

    public static <T> List<MenuTree<T>> buildList(List<MenuTree<T>> nodes, String idParam) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        List<MenuTree<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);
                return;
            }
            nodes.forEach(parent -> {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChilds().add(children);
                    children.setHasParent(true);
                    parent.setHasChild(true);
                }
            });
        });
        return topNodes;
    }
}