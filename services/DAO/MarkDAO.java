package test.connectdb.services.DAO;

import test.connectdb.AppError;

import java.util.List;
import java.util.Optional;

public interface MarkDAO<T>{
    List<T> getListByUser(Integer user_id) throws AppError;

    Optional<T> get(Integer user_id, Integer resort_id) throws AppError;

    void create(Integer user_id, Integer resort_id) throws AppError;

    void update(Integer user_id, Integer resort_id) throws AppError;

    void delete(Integer user_id, Integer resort_id) throws AppError;
}
