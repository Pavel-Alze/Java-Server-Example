package test.connectdb.services.DAO;

import test.connectdb.AppError;

import java.util.List;
import java.util.Optional;

public interface ResortDAO<T,I,K,F> {
    void create(F f) throws AppError;

    void update(I i, F f) throws AppError;

    List<T> getAll(I i) throws AppError;

    K getByPk(I i) throws AppError;

    void delete(I i) throws AppError;
}
