package test.connectdb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.PostUserDTO;
import test.connectdb.models.User;
import test.connectdb.repositories.UserRepository;
import test.connectdb.services.DAO.UserDAO;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import test.connectdb.utils.Mapper;


@Service
public class UserService implements UserDAO<User,Integer,PostUserDTO> {

    private static final Logger log = Logger.getLogger(UserService.class);

    //как я уже сказал, тела методов я ещё не прописывал, можешь сдлеать это сам
    //для обращения к бд используй объявленный репозиторий ниже
    //так же можешь создавать свои методы, но не забудь их объявиться в интерфейсе в папке DAO
    //если где надо будет сделать обращение к бд, а не сам алгоритм обработки
    //можешь попытаться сам, либо оставляй мне комменты в нужном месте и указанием
    //того, что тебе нужно
    @Autowired
    Mapper mapper;
    @Autowired
    UserRepository userRepository;

    @Override
    public int create(Integer login_id) throws AppError {
        try {
            User user = new User();
            user.setLogin_id(login_id);
            userRepository.save(user);
            log.info("User created." + user.toString());
            return user.getId();
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public void update(Integer user_id, PostUserDTO postUserDTO) throws AppError {
        try {
            User user = getByPk(user_id).get();
            userRepository.save(mapper.UserDtoToEntity(postUserDTO,user));
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getByPk(Integer integer) throws AppError {
        try {
            Optional<User> user = userRepository.findById(integer);
            if (user.isEmpty()) {
                log.info("User with ID: " + integer + " not found");
                throw new AppError(404, "User with ID: " + integer + " not found. Please check the correctness of the data");
            }
            return user;
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }

    @Override
    public Optional<User> getByLogin(Integer integer) throws AppError {
        try {
            Optional<User> user = userRepository.findByLogin(integer);
            if(user.isEmpty()){
                log.info("User with login ID: " + integer + " not found");
                throw new AppError(404, "User not found. Please check the correctness of the data");
            }else {
                return user;
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public void delete(Integer integer) throws AppError {
        if(!(getByPk(integer).isEmpty())){
            userRepository.deleteById(integer);
        }
    }
}
