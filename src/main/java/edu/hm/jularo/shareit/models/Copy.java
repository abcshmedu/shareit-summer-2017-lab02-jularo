package edu.hm.jularo.shareit.models;

/**
 * Klasse für Ausleiher und Medium.
 *
 * @author Juliane Seidl
 * @author Carolin Direnberger
 */
class Copy {
    private Medium medium;
    private String owner;

    /**
     * Konstruktor.
     *
     * @param owner  Der Besitzer des Mediums
     * @param medium Das Medium.
     */
    Copy(String owner, Medium medium) {

        if (owner == null || medium == null) {
            throw new IllegalArgumentException("owner and medium must not be null");
        }

        this.owner = owner;
        this.medium = medium;
    }

    /**
     * Einfacher Getter für das Medium.
     *
     * @return medium
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     * Einfacher Getter für den Besitzer.
     *
     * @return owner
     */
    public String getOwner() {
        return owner;
    }
}
