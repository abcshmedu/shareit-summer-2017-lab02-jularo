package edu.hm.shareit.models;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public class Copy {
    private final Medium medium;
    private final String owner;

    public Copy(String owner, Medium medium) {
        if (medium==null){
            throw new IllegalArgumentException("Medium is null");
        }
        this.medium = medium;
        this.owner = owner!=null?owner:"";
    }

    public Medium getMedium() {
        return medium;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Copy{" +
                medium.toString() +
                ", owner='" + owner + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Copy copy = (Copy) o;

        return medium.equals(copy.medium) && owner.equals(copy.owner);
    }

    @Override
    public int hashCode() {
        int result = medium.hashCode();
        result = 31 * result + owner.hashCode();
        return result;
    }
}
