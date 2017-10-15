package com.gl.extrade.spec.dto;

import com.gl.extrade.common.dto.DTO;

public class Request extends DTO{
    /**
     * 
     */
    private static final long serialVersionUID = 3056641837437284690L;
    private String systemId;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    
}
