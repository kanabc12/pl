package org.hxy.pl.vo;

import org.apache.commons.lang3.builder.*;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 15-4-4.
 */
public class BaseVO implements Serializable, Cloneable, Comparable<Object> {

    private static final long serialVersionUID = -6322424843449582306L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }


    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    public int compareTo(Object o) {
        return CompareToBuilder.reflectionCompare(this, o);
    }


    public Map<String, Object> converMap(){

        return null;
    }
}
