package com.codegym.service;

import java.util.List;

public interface GeneralService<T> {
    List<T> findAll();
    void addReceptionist(T t);
    T findById(int id);
    void updateReceptionist(int id, T t);
    void removeReceptionist(int id);
    List<T> findByName(String name);


}
