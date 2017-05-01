package edu.hm.jularo.shareit.service;

import javax.ws.rs.core.Response;

/**
 * Klasse enthält die Responsecodes.
 *
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public enum MediaServiceResult {

    UPDATED(Response.Status.OK.getStatusCode(), "Änderungen wurden gespeichert."),
    CREATED(Response.Status.CREATED.getStatusCode(), "Medium wurde hinzugefügt - Läuft bei uns!"),

    ALREADY_IN_LIST(Response.Status.CONFLICT.getStatusCode(), "MedienID ist bereits vorhanden."),
    NOT_VALID(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Medium wurde nicht akzeptiert.");

    private final int code;
    private final String detail;

    /**
     * Konstruktor für MediaServiceResult.
     *
     * @param code   Status als Code
     * @param detail Status als Text
     */
    MediaServiceResult(int code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    /**
     * Einfacher Getter für den Code des Status.
     *
     * @return Status als Code
     */
    public int getCode() {
        return code;
    }

    /**
     * Einfacher Getter für den Text des Status.
     *
     * @return Status als Text
     */
    public String getDetail() {
        return detail;
    }

}
