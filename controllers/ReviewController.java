package test.connectdb.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.connectdb.AppError;
import test.connectdb.dto.PostReviewDTO;
import test.connectdb.dto.ResListDTO;
import test.connectdb.dto.ResReviewDTO;
import test.connectdb.dto.TokensDTO;
import test.connectdb.models.Review;
import test.connectdb.services.*;
import test.connectdb.utils.Mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private static final Logger logger = Logger.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final TokenService tokenService;


    @Autowired
    Mapper mapper;

    public ReviewController(ReviewService reviewService, TokenService tokenService) {
        this.reviewService = reviewService;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<?> createReview(@RequestHeader String accessToken, @RequestHeader String refreshToken, @RequestBody PostReviewDTO postReviewDTO){
        try{
            TokensDTO tokensDTO = new TokensDTO();
            if(!tokenService.isValidToken(accessToken)){
                return new ResponseEntity<>("Bad Token", HttpStatusCode.valueOf(403));
            }
            if(!tokenService.expirationDate(accessToken)){
                if(!tokenService.isLiquser_idReshresh(accessToken,refreshToken)){
                    return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(401));
                }
                accessToken = tokenService.creteToken(tokenService.getUserId(accessToken));
                refreshToken = tokenService.createRefreshToken(accessToken);
            }
            tokensDTO.setAccessToken(accessToken);
            tokensDTO.setRefreshToken(refreshToken);
            reviewService.create(postReviewDTO);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(201));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/user_reviews")
    public ResponseEntity<?> getReviewListByUser(@RequestHeader String accessToken,@RequestHeader String refreshToken){
        try{
            ResListDTO resListDTO = new ResListDTO();
            if(!tokenService.isValidToken(accessToken)){
                return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(403));
            }
            if(!tokenService.expirationDate(accessToken)){
                if(!tokenService.isLiquser_idReshresh(accessToken,refreshToken)){
                    return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(401));
                }
                accessToken = tokenService.creteToken(tokenService.getUserId(accessToken));
                refreshToken = tokenService.createRefreshToken(accessToken);
            }
            resListDTO.setAccessToken(accessToken);
            resListDTO.setRefreshToken(refreshToken);
            List<Review> reviewlist = reviewService.getByUser(tokenService.getUserId(accessToken));
            resListDTO.setList(Arrays.asList(reviewlist.toArray()));
            return new ResponseEntity<>(resListDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/{review_id}")
    public ResponseEntity<?> getReview(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int review_id){
        try{
            ResReviewDTO resReviewDTO = new ResReviewDTO();
            if(!tokenService.isValidToken(accessToken)){
                return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(403));
            }
            if(!tokenService.expirationDate(accessToken)){
                if(!tokenService.isLiquser_idReshresh(accessToken,refreshToken)){
                    return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(401));
                }
                accessToken = tokenService.creteToken(tokenService.getUserId(accessToken));
                refreshToken = tokenService.createRefreshToken(accessToken);
            }
            resReviewDTO.setAccessToken(accessToken);
            resReviewDTO.setRefreshToken(refreshToken);
            Optional<Review> review = reviewService.getByPk(review_id);
            resReviewDTO = mapper.ReviewEntityToDto(review.get(),resReviewDTO);
            return new ResponseEntity<>(resReviewDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/resort_reviews/{resort_id}")
    public ResponseEntity<?> getReviewListByResort(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int resort_id){
        try{
            ResListDTO resListDTO = new ResListDTO();
            if(!tokenService.isValidToken(accessToken)){
                return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(403));
            }
            if(!tokenService.expirationDate(accessToken)){
                if(!tokenService.isLiquser_idReshresh(accessToken,refreshToken)){
                    return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(401));
                }
                accessToken = tokenService.creteToken(tokenService.getUserId(accessToken));
                refreshToken = tokenService.createRefreshToken(accessToken);
            }
            resListDTO.setAccessToken(accessToken);
            resListDTO.setRefreshToken(refreshToken);
            List<Review> reviewlist = reviewService.getByResort(resort_id);
            resListDTO.setList(Arrays.asList(reviewlist.toArray()));
            return new ResponseEntity<>(resListDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @DeleteMapping(value = "/{review_id}")
    public ResponseEntity<?> deleteReview(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int review_id){
        try{
            TokensDTO tokensDTO = new TokensDTO();
            if(!tokenService.isValidToken(accessToken)){
                return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(403));
            }
            if(!tokenService.expirationDate(accessToken)){
                if(!tokenService.isLiquser_idReshresh(accessToken,refreshToken)){
                    return new ResponseEntity<>("Bad Token",HttpStatusCode.valueOf(401));
                }
                accessToken = tokenService.creteToken(tokenService.getUserId(accessToken));
                refreshToken = tokenService.createRefreshToken(accessToken);
            }
            tokensDTO.setAccessToken(accessToken);
            tokensDTO.setRefreshToken(refreshToken);
            reviewService.delete(review_id);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
}
