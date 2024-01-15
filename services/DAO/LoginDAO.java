package test.connectdb.services.DAO;

import test.connectdb.AppError;

import java.util.List;
import java.util.Optional;

public interface LoginDAO<T,I,K> {
    int create(K k) throws AppError;

    void update(T t);

    List<T> getAll();

    Optional<T> getByPk(I i);

    Optional<T> getByLogin(String str) throws AppError;

    void delete(I i);

    Optional<T> auth(K k) throws AppError;
}
