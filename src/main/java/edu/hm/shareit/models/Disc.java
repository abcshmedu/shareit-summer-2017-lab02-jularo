package edu.hm.shareit.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */

@Entity
@Table(name="DISCS")
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Disc extends Medium implements Serializable {

    @Id
    @Column(nullable = false)
    private final String barcode;

    @Column(nullable = false)
    private final String director;

    @Column(nullable = false, length = 2)
    private final int fsk;


    /**
     * private default constructor is needed for reflection (Jackson)
     */
    private Disc() {
        super("");
        barcode = "";
        director = "";
        fsk = -1;
    }

    public Disc(String title, String barcode, String director, int fsk) {
        super(title);
        this.barcode =  barcode!=null?barcode:"";
        this.director  = director!=null?director:"";
        this.fsk = fsk;
    }

    @Override
    public String toString() {
        return "Disc{" +
                super.toString() +
                ", barcode='" + barcode + '\'' +
                ", director='" + director + '\'' +
                ", fsk=" + fsk +
                '}';
    }

    public String getBarcode() {
        return barcode;
    }

    public String getDirector() {
        return director;
    }

    public int getFsk() {
        return fsk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Disc disc = (Disc) o;

        if (getFsk() != disc.getFsk()) {
            return false;
        }
        if (!getBarcode().equals(disc.getBarcode())) {
            return false;
        }
        return getDirector().equals(disc.getDirector());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getBarcode() != null ? getBarcode().hashCode() : 0);
        result = 31 * result + (getDirector() != null ? getDirector().hashCode() : 0);
        result = 31 * result + getFsk();
        return result;
    }
}
