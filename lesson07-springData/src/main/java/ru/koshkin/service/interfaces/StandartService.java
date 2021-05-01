package ru.koshkin.service.interfaces;

import ru.koshkin.persist.Entities.Persistable;

import java.util.List;

public interface StandartService<T extends Persistable> {
    T getById(Long id);

    List<T> getAll();

    void saveOrUpdate(T entity);

    void delete(T entity);

    void deleteById(Long id);

}

