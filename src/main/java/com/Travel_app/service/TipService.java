package com.Travel_app.service;

import com.Travel_app.db.model.Tip;
import com.Travel_app.db.repository.TipRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class TipService {
    @Autowired
    TipRepository tips;

    public Object findAll() {
        return this.tips.findAll();
    }

    public List<Tip> findTipsByKeyword(String keyword) {
        return this.tips.findAllByKeyword(keyword);
    }

    public Object getById(Long id) {
        Optional<Tip> tip=tips.findById(id);
        return tip.isEmpty()? null:tip.get();
    }

    public void addTip(Tip tip) {
        this.tips.save(tip);
    }

    public void updateTip(Long id, Tip tip) {
        Tip t = tips.findById(id).get();
        t.setTitle(tip.getTitle());
        t.setContent(tip.getContent());
        t.setCategory(tip.getCategory());
        this.tips.save(t);
    }

    public void deleteTip(Long id) {
        Tip tip = tips.findById(id).get();
        this.tips.delete(tip);
    }

    public Object findAllByCategory(String category) {
        return this.tips.findAllByCategory(category);
    }
}
