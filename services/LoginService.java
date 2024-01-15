package test.connectdb.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.PostLoginDTO;
import test.connectdb.models.Login;
import test.connectdb.repositories.LoginRepository;
import test.connectdb.services.DAO.LoginDAO;
import test.connectdb.utils.Mapper;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements LoginDAO<Login,Integer,PostLoginDTO> {

    @Autowired
    Mapper mapper;

    private static final Logger log = Logger.getLogger(LoginService.class);

    @Autowired
    LoginRepository loginRepository;
    @Autowired
    UserService userService;

    @Autowired
    PassService passService;

    @Override
    public int create(PostLoginDTO loginDTO) throws AppError {
        try {
            if(loginRepository.findByLogin(loginDTO.getLogin()).isEmpty()) {
                if(passService.isHardPass(loginDTO.getPassword())) {
                    Login login = new Login();
                    loginRepository.save(mapper.LoginDtoToEntity(loginDTO, login));
                    log.info("Login created." + login.toString());
                    int user_id;
                    user_id = userService.create(login.getId());
                    return user_id;
                }else{
                    log.info("Low password");
                    throw new AppError(409,"Low password");
                }
            }else {
                log.info("User with this login already exists");
                throw new AppError(409,"User with this login already exists");
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal serer error");
        }
    }

    @Override
    public void update(Login login) {
        loginRepository.save(login);
    }

    @Override
    public List<Login> getAll() {
        return loginRepository.findAll();
    }

    @Override
    public Optional<Login> getByPk(Integer integer) {
        return loginRepository.findById(integer);
    }

    @Override
    public Optional<Login> getByLogin(String str) throws AppError {
        try {
            Optional<Login> login = loginRepository.findByLogin(str);
            if(login.isEmpty()){
                log.info("User with login: " + str + " does not exist");
                throw new AppError(404,"User with login: " + str + " does not exist");
            }else {
                return login;
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e) {
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public void delete(Integer integer) {
        if(!(getByPk(integer).isEmpty())){
            loginRepository.deleteById(integer);
        }
    }

    @Override
    public Optional<Login> auth(PostLoginDTO loginDTO) throws AppError {
        try {
            Optional<Login> templog = getByLogin(loginDTO.getLogin());
            if(passService.isRightPass(loginDTO.getPassword(),templog.get().getPassword(),templog.get().getSalt())){
                return templog;
            }else{
                log.info("Incorrect password or login");
                throw new AppError(400,"Incorrect password or login");
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
}
