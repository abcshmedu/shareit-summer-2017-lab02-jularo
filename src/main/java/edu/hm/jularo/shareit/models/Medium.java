package edu.hm.jularo.shareit.models;

/**
 * This is the class that represents a medium.
 *
 * @author Juliane Seidl
 * @author Carolin Dierenberger
 */
public abstract class Medium {
    /**
     * This is the title for the medium.
     */
    private final String title;

    /**
     * This is the constructor for a medium.
     *
     * @param title The medium's title.
     */
    public Medium(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Medium{title='" + title + '\'' +'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medium medium = (Medium) o;

        return getTitle() != null ? getTitle().equals(medium.getTitle()) : medium.getTitle() == null;
    }

    @Override
    public int hashCode() {
        return getTitle() != null ? getTitle().hashCode() : 0;
    }
}
