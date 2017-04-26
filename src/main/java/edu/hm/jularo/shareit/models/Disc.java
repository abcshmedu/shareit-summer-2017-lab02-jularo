package edu.hm.jularo.shareit.models;

/**
 * Model für Discs.
 *
 * @author Juliane Seidl
 * @author Carolin Direnberger
 */
public class Disc extends Medium {

    private final String barcode;
    private final String director;
    private final int fsk;

    /**
     * Konstruktor.
     */
    public Disc() {
        super("");
        barcode = "";
        director = "";
        fsk = -1;
    }

    /**
     * Konstruktor.
     *
     * @param barcode  Der Barcode der Disk
     * @param director Der Director der Disk
     * @param fsk      Die Altersfreigabe der Disk
     * @param title    Der Titel der Disk
     */
    public Disc(String barcode, String director, int fsk, String title) {
        super(title);

        if (barcode == null || director == null) {
            throw new IllegalArgumentException("director and barcode must not be null");
        }

        this.barcode = barcode;
        this.director = director;
        this.fsk = fsk;
    }

    /**
     * Einfacher Getter für den Barcode.
     *
     * @return barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Einfacher Getter für den Director.
     *
     * @return director
     */
    private String getDirector() {
        return director;
    }

    /**
     * Einfacher Getter für die Altersfreigabe.
     *
     * @return fsk
     */
    private int getFsk() {
        return fsk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append(" \"title\":\"" + getTitle() + "\",\n");
        builder.append(" \"barcode\":\"" + barcode + "\",\n");
        builder.append(" \"director\":\"" + director + "\",\n");
        builder.append(" \"fsk\":\"" + fsk + "\"\n");
        builder.append("}\n");

        return builder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        Disc disc = (Disc) object;

        return fsk == disc.getFsk()
                && barcode.equals(disc.getBarcode())
                && director.equals(disc.getDirector());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + barcode.hashCode();
        result = 31 * result + director.hashCode();
        result = 31 * result + fsk;
        return result;
    }

    /**
     * Prüft, ob der Titel und der Director der Disc gefüllt sind.
     *
     * @return true wenn valid
     */
    public boolean isValid() {
        return !director.isEmpty() && !getTitle().isEmpty();
    }
}
