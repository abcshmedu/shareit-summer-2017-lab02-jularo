package edu.hm.jularo.shareit.models;

public class Disc extends Medium{

    private final String barcode;
    private final String director;
    private final int fsk;

    public Disc(){
        super("");
        barcode = "";
        director = "";
        fsk = -1;  //TODO wieso -1 ?
    }

    public Disc(String barcode, String director, int fsk, String title){
        super(title);

        if (barcode == null || director == null ) {
            throw new IllegalArgumentException("director and barcode must not be null");
        }

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
        return "Disc{" + super.toString() + ", barcode='" + barcode + '\'' + ", director='" + director + '\'' + ", fsk=" + fsk + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)){
            return false;
        }

        Disc disc = (Disc) object;

        return  fsk == disc.getFsk()
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
}
