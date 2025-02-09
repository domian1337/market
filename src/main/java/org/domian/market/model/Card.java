package org.domian.market.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(schema = "testshema")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int article;

    private int cost;

    private String title;

    private String description;

    private String imageUrl;

    private int discount;

    @ManyToMany(mappedBy = "cards")
    private Set<Person> persons;

    public Card() {}

    public Card(String title, String description, String imageUrl, int cost, int discount) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.cost = cost;
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return article == card.article && cost == card.cost && Double.compare(discount, card.discount) == 0 && Objects.equals(title, card.title) && Objects.equals(description, card.description) && Objects.equals(persons, card.persons);
    }

    @Override
    public int hashCode() {
        int result = cost;
        result = 31 * result + title.hashCode();
        return 31 * result + (description != null ? description.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "Card{" +
                "articul=" + article +
                ", cost=" + cost +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", discount=" + discount +
                ", persons=" + persons +
                '}';
    }
}