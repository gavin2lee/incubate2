package com.gl.extrade.common.dto;

import java.io.Serializable;

import com.gl.extrade.common.util.ReflectionUtil;

public abstract class DTO implements Serializable{

    

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ReflectionUtil.toString(this.getClass(), this);
    }

}
