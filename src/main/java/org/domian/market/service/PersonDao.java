package org.domian.market.service;

import org.domian.market.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface PersonDao extends JpaRepository<Person, Integer> {

    @Query("select p from Person p where p.email = ?1")
    Optional<Person> findByEmail(String email);
}
