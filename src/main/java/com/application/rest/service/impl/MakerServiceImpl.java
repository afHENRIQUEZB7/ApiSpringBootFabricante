package com.application.rest.service.impl;

import com.application.rest.entities.Maker;
import com.application.rest.persistence.IMakerDAO;
import com.application.rest.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MakerServiceImpl implements IMakerService {

    @Autowired
    private IMakerDAO iMakerDAO;

    @Override
    public List<Maker> findAll() {
        return iMakerDAO.findAll();
    }

    @Override
    public Optional<Maker> findById(Long id) {
        return iMakerDAO.findById(id);
    }

    @Override
    public void save(Maker maker) {
        iMakerDAO.save(maker);
    }

    @Override
    public void deleteById(Long id) {
        iMakerDAO.deleteById(id);
    }
}
