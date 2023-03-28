package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Category implements ValueObject, Comparable<Category> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String categoryId;

    @Embedded
    private Designation name;

    @OneToMany(mappedBy="category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<Product>();

    protected Category() {
    }

    public Category(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        Category that = (Category) o;

        return Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }


    public String getCategoryId() {
        return categoryId;
    }

    public static Category valueOf(final String category){
        return new Category(category);
    }

    @Override
    public String toString() {
        return categoryId;
    }


    @Override
    public int compareTo(Category o) {
        return 0;
    }
}
