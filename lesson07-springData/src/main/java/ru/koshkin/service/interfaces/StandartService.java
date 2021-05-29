package ru.koshkin.service.interfaces;

import ru.koshkin.persist.Entities.Persistable;

import java.util.List;
import java.util.Optional;

public interface StandartService<T extends Persistable> {
    Optional<T> getById(Long id);

    List<T> getAll();

    void saveOrUpdate(T entity);

    void delete(T entity);

    void deleteById(Long id);

}

