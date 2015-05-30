package org.hxy.pl.vo.tree;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Administrator on 15-5-30.
 */
public class State {
    @JsonProperty("checked")
    private boolean checked;
    @JsonProperty("disabled")
    private boolean disabled;
    @JsonProperty("expanded")
    private boolean expanded = false;
    @JsonProperty("selected")
    private boolean selected;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
