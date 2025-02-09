package org.domian.market.service;

import org.domian.market.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDao extends JpaRepository<Card, Integer> {
}
