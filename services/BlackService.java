package test.connectdb.services;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.ResResortDTO;
import test.connectdb.models.Blacklist;
import test.connectdb.models.Likelist;
import test.connectdb.models.Resort;
import test.connectdb.repositories.BlackRepository;
import test.connectdb.repositories.LikeRepository;
import test.connectdb.services.DAO.MarkDAO;

import java.util.List;
import java.util.Optional;

@Service
public class BlackService implements MarkDAO<Blacklist> {
    private static final Logger log = Logger.getLogger(BlackService.class);

    @Autowired
    private BlackRepository blackRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    private  ResortService resortService;
    @Override
    public List<Blacklist> getListByUser(Integer id) throws AppError {
        try {
            return blackRepository.find_blacklist(id);
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public Optional<Blacklist> get(Integer user_id, Integer resort_id) throws AppError {
        try {
            Optional<Blacklist> black = blackRepository.find_oneBlack(user_id,resort_id);
            if(black.isEmpty()){
                log.info("Black resort with user_id: " + user_id + " & resort_id: " + resort_id + " not found");
                throw new AppError(404,"Black resort with User ID: " + user_id + " and Resort ID: " + resort_id + " not found. Please check the correctness of the data");
            }else {
                return black;
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }
        catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public void create(Integer user_id, Integer resort_id) throws AppError {
        try {
            if (blackRepository.find_oneBlack(user_id, resort_id).isEmpty()) {
                Optional<Likelist> likelist = likeRepository.find_oneLike(user_id,resort_id);
                if(likelist.isPresent()){
                    likeRepository.delete(likelist.get());
                }
                ResResortDTO resort = resortService.getByPk(resort_id);
                Blacklist blacklist = new Blacklist();
                blacklist.setResort_id(resort_id);
                blacklist.setTitle(resort.getTitle());
                blacklist.setUser_id(user_id);
                blackRepository.save(blacklist);
                log.info("Black created with user_id: " + user_id + " & resort_id: " + resort_id );
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }
        catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public void update(Integer user_id, Integer resort_id) throws AppError {
    }

    @Override
    public void delete(Integer user_id, Integer resort_id) throws AppError {
        try {
            Optional<Blacklist> blacklist = blackRepository.find_oneBlack(user_id,resort_id);
            if(blacklist.isEmpty()){
                log.info("Black resort with User ID: " + user_id + " & Resort ID: " + resort_id + " not found");
                throw new AppError(404,"Black resort with User ID: " + user_id + " and Resort ID: " + resort_id + " not found. Please check the correctness of the data");
            }else {
                blackRepository.delete(blacklist.get());
                log.info("Black resort deleted with user_id: " + user_id + " & resort_id: " + resort_id );
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
