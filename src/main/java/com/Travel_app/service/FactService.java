package com.Travel_app.service;

import com.Travel_app.db.model.Fact;
import com.Travel_app.db.repository.FactRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class FactService {
    @Autowired
    FactRepository facts;

    public List<Fact> findAll() {
        return this.facts.findAll();
    }

    public Object getById(Long id) {
        Optional<Fact> fact=facts.findById(id);
        return fact.isEmpty()? null:fact.get();
    }

    public void addFact(Fact fact) {
        this.facts.save(fact);
    }

    public void updateFact(Long id, Fact fact) {
        Optional<Fact> fact1=facts.findById(id);
        Fact f = fact1.get();
        f.setTitle(fact.getTitle());
        f.setContent(fact.getContent());
        this.facts.save(fact);
    }

    public void deleteFact(Long id) {
        Fact fact = facts.findById(id).get();
        this.facts.delete(fact);
    }
}
