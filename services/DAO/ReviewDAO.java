package test.connectdb.services.DAO;

import test.connectdb.AppError;

import java.util.List;
import java.util.Optional;

public interface ReviewDAO<T,I,K,J,L> {
    void create(L l) throws AppError;

    void update(T t) throws AppError;

    Optional<T> getByPk(I i) throws AppError;

    List<T> getByUser(K k) throws AppError;

    List<T> getByResort(J j) throws AppError;

    List<T> getByUserResort(K k,J j) throws AppError;

    void delete(I i) throws AppError;
}