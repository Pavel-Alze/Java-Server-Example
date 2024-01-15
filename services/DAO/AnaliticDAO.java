package test.connectdb.services.DAO;

import test.connectdb.AppError;

public interface AnaliticDAO<T,I>{
    void create(T t, I i) throws AppError;
}
