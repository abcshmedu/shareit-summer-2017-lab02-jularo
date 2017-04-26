package edu.hm.jularo.shareit.models;

/**
 * This is the class that represents a medium.
 *
 * @author Juliane Seidl
 * @author Carolin Direnberger
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
    Medium(String title) {
        if (title == null) {
            throw new IllegalArgumentException("title must not be null");
        }
        this.title = title;
    }

    String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Medium{title='" + title + '\'' + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Medium medium = (Medium) object;
        return title.equals(medium.getTitle());
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
