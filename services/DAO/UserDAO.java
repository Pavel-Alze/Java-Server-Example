package test.connectdb.services.DAO;

import test.connectdb.AppError;

import java.util.List;
import java.util.Optional;

public interface UserDAO<T,I,K> {
    int create(I i) throws AppError;

    void update(I i,K k) throws AppError;

    List<T> getAll();

    Optional<T> getByPk(I i) throws AppError;

    Optional<T> getByLogin(I i) throws AppError;

    void delete(I i) throws AppError;

}
