package test.connectdb.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.PostReviewDTO;
import test.connectdb.models.Review;
import test.connectdb.repositories.ReviewRepository;
import test.connectdb.services.DAO.ReviewDAO;
import test.connectdb.utils.Mapper;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements ReviewDAO<Review,Integer,Integer,Integer,PostReviewDTO> {

    private static final Logger log = Logger.getLogger(ReviewService.class);

    @Autowired
    Mapper mapper;
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public void create(PostReviewDTO postReviewDTO) throws AppError {
        try {
            Review review = new Review();
            reviewRepository.save(mapper.ReviewDtoToEntity(postReviewDTO,review));
            log.info("Review created." + review.toString());
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }

    @Override
    public void update(Review review) throws AppError {
        try {
            if(reviewRepository.findById(review.getId()).isEmpty()){
                log.info("Review with ID: " + review.getId() + " not found");
                throw new AppError(404, "Review with ID: " + review.getId() + " not found. Please check the correctness of the data");
            }else{
                reviewRepository.save(review);
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }

    @Override
    public Optional<Review> getByPk(Integer integer) throws AppError {
        try {
            Optional<Review> review = reviewRepository.findById(integer);
            if(review.isEmpty()){
                log.info("Review with ID: " + integer + " not found");
                throw new AppError(404, "Review with ID: " + integer + " not found. Please check the correctness of the data");
            }else{
                return review;
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }

    @Override
    public List<Review> getByUser(Integer integer) throws AppError {
        try {
            List<Review> review = reviewRepository.findByUser(integer);
            return review;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }

    @Override
    public List<Review> getByResort(Integer integer) throws AppError {
        try {
            List<Review> review = reviewRepository.findByResort(integer);
            return review;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }

    @Override
    public List<Review> getByUserResort(Integer user_id, Integer resort_id) throws AppError {
        try {
            List<Review> review = reviewRepository.findByUserResort(user_id,resort_id);
            if(review.isEmpty()){
                log.info("Review with User ID: " + user_id + " and Resort ID: " + resort_id + " not found");
                throw new AppError(404, "Review with User ID: " + user_id + " and Resort ID: " + resort_id + " not found. Please check the correctness of the data");
            }else{
                return review;
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }

    @Override
    public void delete(Integer integer) throws AppError {
        try {
            if(reviewRepository.findById(integer).isEmpty()){
                log.info("Review with ID: " + integer + " not found");
                throw new AppError(404, "Review with ID: " + integer + " not found. Please check the correctness of the data");
            }else{
                reviewRepository.deleteById(integer);
                log.info("Review deleted with ID: " + integer );
            }
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }
}
