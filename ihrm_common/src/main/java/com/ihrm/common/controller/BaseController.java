package com.ihrm.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    public HttpServletRequest httpServletRequest;
    public HttpServletResponse httpServletResponse;
    protected String companyId;
    protected String companyName;

    @ModelAttribute
    public void setResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.httpServletRequest = request;
        this.httpServletResponse = response;
        this.companyId = "1";
        this.companyName = "hitwh";
    }
}
