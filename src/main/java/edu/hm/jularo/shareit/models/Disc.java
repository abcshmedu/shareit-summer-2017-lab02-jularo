package edu.hm.jularo.shareit.models;

public class Disc extends Medium{

    private final String barcode;
    private final String director;
    private final int fsk;

    public Disc(){
        super(null);
        barcode = null;
        director = null;
        fsk = -1;
    }

    public Disc(String barcode, String director, int fsk, String title){
        super(title);
        this.barcode = barcode;
        this.director = director;
        this.fsk = fsk;
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
    public String toString() {
        return "Disc{" +
                super.toString() +
                ", barcode='" + barcode + '\'' +
                ", director='" + director + '\'' +
                ", fsk=" + fsk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Disc disc = (Disc) o;

        if (getFsk() != disc.getFsk()) return false;
        if (getBarcode() != null ? !getBarcode().equals(disc.getBarcode()) : disc.getBarcode() != null) return false;
        return getDirector() != null ? getDirector().equals(disc.getDirector()) : disc.getDirector() == null;

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
