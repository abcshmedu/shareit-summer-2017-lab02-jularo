package edu.hm.jularo.shareit.service;

import javax.ws.rs.core.Response;

public enum MediaServiceResult {

    OK(Response.Status.OK.getStatusCode(), "200 - läuft bei uns!"),
    BAD_REQUEST(Response.Status.BAD_REQUEST.getStatusCode(), "The server cannot or will not process the request due to an apparent client error."),
    NOT_FOUND(Response.Status.NOT_FOUND.getStatusCode(), "The requested resource could not be found.");


    private int code;
    private String detail;

    MediaServiceResult(int code, String detail){
        this.code = code;
        this.detail = detail;
    }

    public int getCode(){
        return code;
    }

    public String getDetail(){
        return detail;
    }

}
