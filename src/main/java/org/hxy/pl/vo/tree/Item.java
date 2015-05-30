package org.hxy.pl.vo.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能: 节点的信息
 */
public class Item {
    /**
     * 节点的名字
     */
    private String text;

    /**
     * 图标
     */
    private String icon;

    /**
     * 选中图标
     */
    private String selectedIcon;
    /**
     * 超链接
     */
    private String href;

    /**
     * 前景色
     */
    private String color;

    /**
     * 背景色
     */
    private String  backColor;
    /**
     * 子节点的信息
     */
    private List<Item> nodes = new ArrayList<Item>();

    /**
     * 标签
     */

    private ArrayList<String> tags = new ArrayList<String>();

    /**
     * 节点状态
     */
    private State state;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public List<Item> getNodes() {
        return nodes;
    }

    public void setNodes(List<Item> nodes) {
        this.nodes = nodes;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(String selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
