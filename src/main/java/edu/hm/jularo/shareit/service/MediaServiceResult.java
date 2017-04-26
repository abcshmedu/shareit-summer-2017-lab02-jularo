package edu.hm.jularo.shareit.service;

import javax.ws.rs.core.Response;

public enum MediaServiceResult {

    FOUND(Response.Status.OK.getStatusCode(), "Medium wurde geladen."),
    UPDATED(Response.Status.OK.getStatusCode(), "Änderungen wurden gespeichert."),
    FOUND_LIST(Response.Status.OK.getStatusCode(), "Medien wurden geladen."),
    CREATED(Response.Status.CREATED.getStatusCode(), "Medium wurde hinzugefügt - Läuft bei uns!"),

    ALREADY_IN_LIST(Response.Status.CONFLICT.getStatusCode(), "Medium ist bereits vorhanden."),
    MEDIUM_NOT_IN_LIST(Response.Status.NOT_FOUND.getStatusCode(), "Medium wurde nicht gefunden."),
    NOT_ACCEPTABLE(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Änderungen wurden nicht akzeptiert."),
    NOT_VALID(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Medium wurde nicht akzeptiert.");

    private int code;
    private String detail;

    MediaServiceResult(int code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }

}
