package test.connectdb.services.DAO;

import test.connectdb.AppError;

import java.util.List;
import java.util.Optional;

public interface PointDAO<T,I,K>{
    public List<T> getByRoute(I i) throws AppError;
    public void create(T t) throws AppError;
    public void deleteAll(I i) throws AppError;

}
