package org.domian.market.service;

import lombok.extern.log4j.Log4j;
import org.domian.market.model.*;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class PersonService implements UserDetailsService {

    private final CardDao cardDao;
    private final PersonDao personDao;
    private final RoleDao roleDao;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public PersonService(PersonDao personDao, RoleDao roleDao, CardDao cardDao) {
        this.personDao = personDao;
        this.roleDao = roleDao;
        this.cardDao = cardDao;
    }

    @Transactional
    public void registrationUser(Person person) {
        Optional<Person> personOptional = personDao.findByEmail(person.getEmail());
        if (personOptional.isPresent()) {
            throw new IllegalArgumentException("Пользователь с этой почтой уже существует");
        }
        person.setGender(Gender.NOT);
        Role role = getRoleById(1);
        person.addRole(role);
        person.setStatus(Status.ACTIVE);
        person.setTpassword(person.getPassword());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personDao.save(person);
    }

    public List<Person> getAllPersons() {
        return personDao.findAll();
    }

    public Person getPersonByEmail(String email) {
        return personDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email не существует"));
    }

    public Person getPersonById(int id) {
        return personDao.findById(id).
                orElseThrow(() -> new IllegalIdentifierException("id не существует"));
    }

    @Transactional
    public void savePerson(Person person) {
        Optional<Person> personOptional = personDao.findByEmail(person.getEmail());
        if (personOptional.isPresent()) {
            throw new IllegalArgumentException("Пользователь с этой почтой уже существует");
        }
        Set<Role> roles = person.getRoles().stream()
                .map(role -> getRoleById(role.getId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        person.setRoles(roles);
        person.setTpassword(person.getPassword());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personDao.save(person);
    }

    @Transactional
    public void editPerson(int id, String name, String surname, String email, int age,
                           int balance, Gender gender, String password, Status status, Set<Role> roles) {
        Person person = personDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с id" + id + "не существует"));
        if (name != null && !name.isEmpty() && !Objects.equals(name, person.getName())) {
            person.setName(name);
        }
        if (surname != null && !surname.isEmpty() && !Objects.equals(surname, person.getSurname())) {
            person.setSurname(surname);
        }
        if (email != null && !email.isEmpty() && !Objects.equals(email, person.getEmail())) {
            Optional<Person> personOptional = personDao.findByEmail(email);
            if (personOptional.isPresent()) {
                throw new IllegalArgumentException("Пользователь с этой почтой уже существует");
            }
            person.setEmail(email);
        }
        if (password != null && !password.isEmpty() && !Objects.equals(password, person.getPassword())) {
            person.setTpassword(password);
            person.setPassword(passwordEncoder.encode(password));
        }
        if (age != 0 && age != person.getAge()) {
            person.setAge(age);
        }
        if (balance != 0 && balance != person.getBalance()) {
            person.setBalance(balance);
        }
        if (gender != null && !Objects.equals(gender, person.getGender())) {
            person.setGender(gender);
        }
        if (status != null && !Objects.equals(status, person.getStatus())) {
            person.setStatus(status);
        }
        for (Role role : roles) {
            person.addRole(role);
        }
        personDao.save(person);
    }

    @Transactional
    public void editProfile(int id, Person person) {
        Person dest = getPersonById(id);
        dest.setName(person.getName());
        dest.setSurname(person.getSurname());
        dest.setEmail(person.getEmail());
        dest.setGender(person.getGender());
        dest.setAge(person.getAge());
        personDao.save(dest);
    }

    public void deletePerson(int id) {
        boolean exists = personDao.existsById(id);
        if (!exists) {
            new IllegalArgumentException("Пользователя с id" + id + "не существует");
        }
        personDao.deleteById(id);
    }

    public void addCard(Card card) {
        cardDao.save(card);
    }

    public void deleteCard(int id) {
        cardDao.deleteById(id);
    }

    public List<Card> getAllCards() {
        return cardDao.findAll();
    }

    public Card getCardById(int id) {
        return cardDao.findById(id).orElseThrow(() -> new UsernameNotFoundException("Товара не существует"));
    }

    public void addCardToShop(int id, int article) {
        Person person = getPersonById(id);
        Card card = getCardById(article);
        person.addCardToShop(card);
        personDao.save(person);
    }

    public void deleteCardFromShop(int id, int article) {
        Card card = getCardById(article);
        Person person = getPersonById(id);
        person.removeCardFromShop(card);
        personDao.save(person);
    }

    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    public Role getRoleById(int id) {
        System.out.println("роль: " + id);
        return roleDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Роль не существует"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getPersonByEmail(username);
    }
}