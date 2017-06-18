package edu.hm.shareit.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */


//@Entity
//@Table(name="Medien")
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public abstract class Medium  implements Serializable {

    @Column(nullable = false)
    private final String title;

    public Medium(String title) {
        this.title = title!=null?title:"";
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "title='" + title + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medium medium = (Medium) o;

        return getTitle().equals(medium.getTitle());
    }

    @Override
    public int hashCode() {
        return getTitle().hashCode() ;
    }
}
