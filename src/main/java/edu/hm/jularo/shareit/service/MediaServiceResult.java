package edu.hm.jularo.shareit.service;

import javax.ws.rs.core.Response;

/**
 * Klasse enthält die Responsecodes.
 *
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public enum MediaServiceResult {

    FOUND(Response.Status.OK.getStatusCode(), "Medium wurde geladen."),
    UPDATED(Response.Status.OK.getStatusCode(), "Änderungen wurden gespeichert."),
    FOUND_LIST(Response.Status.OK.getStatusCode(), "Medien wurden geladen."),
    CREATED(Response.Status.CREATED.getStatusCode(), "Medium wurde hinzugefügt - Läuft bei uns!"),
    EMPTY_LIST(Response.Status.OK.getStatusCode(), "Medienliste ist leer."),

    ALREADY_IN_LIST(Response.Status.CONFLICT.getStatusCode(), "Medium ist bereits vorhanden."),
    MEDIUM_NOT_IN_LIST(Response.Status.NOT_FOUND.getStatusCode(), "Medium wurde nicht gefunden."),
    NOT_ACCEPTABLE(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Änderungen wurden nicht akzeptiert."),
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
