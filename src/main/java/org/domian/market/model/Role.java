package org.domian.market.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(schema = "testshema")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<Person> users;

    public Role() {
    }

    public Role(int id) {
        this.role = role;
    }

    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
