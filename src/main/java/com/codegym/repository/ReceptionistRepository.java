package com.codegym.repository;

import com.codegym.model.Receptionist;

import java.util.ArrayList;
import java.util.List;

public class ReceptionistRepository implements GeneralRepository<Receptionist> {
    private static ArrayList<Receptionist> receptionistsList;

    static {
        receptionistsList = new ArrayList<>();
        receptionistsList.add(new Receptionist(1, "Quynh bup be",21,"ha giang","anh Canh","1.jpg"));
        receptionistsList.add(new Receptionist(2, "Lan kave",31,"ha tay","lam tho may","1.jpg"));
        receptionistsList.add(new Receptionist(3, "My soi",24,"ha noi","anh Canh","1.jpg"));

    }

    @Override
    public List findAll() {
        return receptionistsList;
    }

    @Override
    public void addReceptionist(Receptionist receptionist) {
        receptionistsList.add(receptionist);
    }

    @Override
    public Receptionist findById(int id) {
        for (int i = 0; i < receptionistsList.size(); i++) {
            if (receptionistsList.get(i).getId() == id) {
                return receptionistsList.get(i);
            }
        }
        return null;
    }

    @Override
    public void updateReceptionist(int id, Receptionist receptionist) {
        int index= -1;
        for (int i =0; i< receptionistsList.size();i++){
            if(receptionistsList.get(i).getId()==id){
                index = i;
                break;
            }
        }
        receptionistsList.set(index,receptionist);
    }

    @Override
    public void removeReceptionist(int id) {
        for (int i =0;i<receptionistsList.size();i++){
            if (receptionistsList.get(i).getId() == id){
                receptionistsList.remove(i);
                return;
            }
        }
    }

    @Override
    public List<Receptionist> findByName(String name) {
        List<Receptionist> receptionists = new ArrayList<>();
        for (Receptionist receptionist : receptionistsList) {
            if (receptionist.getName().contains(name)) {
                receptionists.add(receptionist);
            }
        }
        return receptionists;
    }
}
