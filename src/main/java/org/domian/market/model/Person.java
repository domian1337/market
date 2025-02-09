package org.domian.market.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(schema = "testshema")
public class Person implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false, length = 64)
    private String email;

    @Column(nullable = false)
    private int age;

    private int balance;

    private String tpassword;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "persons_roles",
            joinColumns = @JoinColumn (name = "person_id"),
            inverseJoinColumns = @JoinColumn (name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "persons_cards",
            joinColumns = @JoinColumn (name = "person_id"),
            inverseJoinColumns = @JoinColumn (name = "article"))
    private Set<Card> cards = new HashSet<>();

    public Person(String name, String surname, String email, int age,
                  int balance, Gender gender, String password, Status status, Set<Role> roles, Set<Card> cards) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.balance = balance;
        this.gender = gender;
        this.password = password;
        this.status = status;
        this.roles = roles;
        this.cards = cards;
    }

    public Person() {
    }

    public void addRole(Role role) {
        roles.add(role);
        role.setUsers(Collections.singleton(this));
    }

    public void addCardToShop(Card card) {
        cards.add(card);
        card.setPersons(Collections.singleton(this));
    }

    public void removeCardFromShop(Card card) {
        cards.remove(card);
        card.getPersons().remove(this);
    }

    public Set<Card> getAllCards() {
        return cards;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles.isEmpty()) {
            System.out.println("roles is empty");
        }
        if (roles == null) {
            System.out.println("roles is null");
        }
        return roles;
    }

    @Override
    public String toString() {
        return "username = " + name + ", surname = " + surname + ", " +
                "email = " + email + ", age = " + age + ", roles = " + roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        return password != null ? password.equals(person.password) : person.password == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}