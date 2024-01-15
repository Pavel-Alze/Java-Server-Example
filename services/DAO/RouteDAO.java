package test.connectdb.services.DAO;

import test.connectdb.AppError;

import java.util.List;
import java.util.Optional;

public interface RouteDAO<T,I,K,F> {
    public List<K> get_all(I i) throws AppError;
    public K get(T t) throws AppError;
    public K getById(I i) throws AppError;
    public void create(F f) throws AppError;
    public void update(I i, F f) throws AppError;
    public void deleteAll(I i) throws AppError;
    public void delete(I i) throws AppError;
}
