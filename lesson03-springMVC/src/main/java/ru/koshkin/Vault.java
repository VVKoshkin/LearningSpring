package ru.koshkin;


import java.util.List;

// интерфейс корзины
public interface Vault {

    String getList();

    List<Storable> getAll();

    Storable getById(Integer id);

    Vault put(Storable element) throws ReflectiveOperationException;

    Vault delete(Integer id);
}
