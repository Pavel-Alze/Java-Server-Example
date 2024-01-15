package test.connectdb.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.ResResortDTO;
import test.connectdb.models.Blacklist;
import test.connectdb.models.Likelist;
import test.connectdb.repositories.BlackRepository;
import test.connectdb.repositories.LikeRepository;
import test.connectdb.services.DAO.MarkDAO;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService implements MarkDAO<Likelist> {
    private static final Logger log = Logger.getLogger(LikeService.class);

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BlackRepository blackRepository;

    @Autowired
    private ResortService resortService;

    @Override
    public List<Likelist> getListByUser(Integer id) throws AppError {
        try {
            return likeRepository.find_likelist(id);
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }


    @Override
    public Optional<Likelist> get(Integer user_id, Integer resort_id) throws AppError {
        try {
            Optional<Likelist> like = likeRepository.find_oneLike(user_id,resort_id);
            if(like.isEmpty()){
                log.info("Like resort with user_id: " + user_id + " & resort_id: " + resort_id + " not found");
                throw new AppError(404,"Like resort with User ID: " + user_id + " and Resort ID: " + resort_id + " not found. Please check the correctness of the data");
            }else {
                return like;
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
    public void create(Integer user_id, Integer resort_id) throws AppError {
        try {
            if (likeRepository.find_oneLike(user_id, resort_id).isEmpty()) {
                Optional<Blacklist> blacklist =  blackRepository.find_oneBlack(user_id, resort_id);
                if (blacklist.isPresent()) {
                    blackRepository.delete(blacklist.get());
                }
                ResResortDTO resort = resortService.getByPk(resort_id);
                Likelist likelist = new Likelist();
                likelist.setResort_id(resort_id);
                likelist.setTitle(resort.getTitle());
                likelist.setUser_id(user_id);
                likeRepository.save(likelist);
                log.info("Like created with user_id: " + user_id + " & resort_id: " + resort_id);
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
            Optional<Likelist> likelist = likeRepository.find_oneLike(user_id,resort_id);
            if (likelist.isEmpty()) {
                log.info("Like resort with User ID: " + user_id + " & Resort ID: " + resort_id + " not found");
                throw new AppError(404,"Like resort with User ID: " + user_id + " and Resort ID: " + resort_id + " not found. Please check the correctness of the data");
            } else {
                likeRepository.delete(likelist.get());
                log.info("Like resort deleted with user_id: " + user_id + " & resort_id: " + resort_id );
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
