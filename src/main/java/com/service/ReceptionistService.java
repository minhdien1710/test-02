package com.service;

import com.model.Receptionist;
import com.repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReceptionistService implements GeneralService<Receptionist> {
    @Autowired
    ReceptionistRepository receptionistRepository;

    @Override
    public List<Receptionist> findAll() {
        return receptionistRepository.findAll();
    }

    @Override
    public void addReceptionist(Receptionist receptionist) {
        receptionistRepository.addReceptionist(receptionist);
    }

    @Override
    public Receptionist findById(int id) {
        return receptionistRepository.findById(id);
    }

    @Override
    public void updateReceptionist(int id, Receptionist receptionist) {
        receptionistRepository.updateReceptionist(id,receptionist);
    }

    @Override
    public void removeReceptionist(int id) {
        receptionistRepository.removeReceptionist(id);

    }

    @Override
    public List<Receptionist> findByName(String name) {
        return receptionistRepository.findByName(name);
    }
}
