package test.connectdb.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.connectdb.AppError;
import test.connectdb.dto.PostResortDTO;
import test.connectdb.dto.ResListDTO;
import test.connectdb.dto.ResResortDTO;
import test.connectdb.dto.TokensDTO;
import test.connectdb.models.Blacklist;
import test.connectdb.models.Likelist;
import test.connectdb.models.Resort;
import test.connectdb.services.*;
import test.connectdb.utils.Mapper;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/resort")
public class ResortController {

    private static final Logger logger = Logger.getLogger(ResortController.class);

    private final LikeService likeService;
    private final BlackService blackService;
    private final ResortService resortService;
    private final TokenService tokenService;


    @Autowired
    Mapper mapper;

    public ResortController(LikeService likeService, BlackService blackService, ResortService resortService, TokenService tokenService) {
        this.likeService = likeService;
        this.blackService = blackService;
        this.resortService = resortService;
        this.tokenService = tokenService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> findResortList(@RequestHeader String accessToken,@RequestHeader String refreshToken){
        try {
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
            List<Resort> resortList = resortService.getAll(tokenService.getUserId(accessToken));
            resListDTO.setList(Arrays.asList(resortList.toArray()));
            return new ResponseEntity<>(resListDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/{resort_id}")
    public ResponseEntity<?> findResort(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int resort_id){
        try{
            ResResortDTO resResortDTO = new ResResortDTO();
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
            resResortDTO = resortService.getByPk(resort_id);
            resResortDTO.setAccessToken(accessToken);
            resResortDTO.setRefreshToken(refreshToken);
            return new ResponseEntity<>(resResortDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
    @PostMapping(value = "/new")
    public ResponseEntity<?> createResort(@RequestHeader String accessToken, @RequestHeader String refreshToken, @RequestBody PostResortDTO postResortDTO ){
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
            resortService.create(postResortDTO);
            tokensDTO.setAccessToken(accessToken);
            tokensDTO.setRefreshToken(refreshToken);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(201));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PatchMapping(value = "/{resort_id}")
    public ResponseEntity<?> updateResort(@RequestHeader String accessToken, @RequestHeader String refreshToken, @PathVariable int resort_id, @RequestBody PostResortDTO postResortDTO){
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
            resortService.update(resort_id, postResortDTO);
            tokensDTO.setAccessToken(accessToken);
            tokensDTO.setRefreshToken(refreshToken);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(201));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
    @DeleteMapping(value = "/{resort_id}")
    public ResponseEntity<?> deleteResort(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int resort_id){
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
            resortService.delete(resort_id);
            tokensDTO.setAccessToken(accessToken);
            tokensDTO.setRefreshToken(refreshToken);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/like/{resort_id}")
    public ResponseEntity<?> createLike(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int resort_id){
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
            likeService.create(tokenService.getUserId(accessToken), resort_id);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/likelist")
    public ResponseEntity<?> getLikeList(@RequestHeader String accessToken,@RequestHeader String refreshToken) {
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
            List<Likelist> likelist = likeService.getListByUser(tokenService.getUserId(accessToken));
            resListDTO.setList(Arrays.asList(likelist.toArray()));
            return new ResponseEntity<>(resListDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @DeleteMapping(value = "/like/{resort_id}")
    public ResponseEntity<?> deleteLike(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int resort_id){
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
            likeService.delete(tokenService.getUserId(accessToken), resort_id);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/blacklist/{resort_id}")
    public ResponseEntity<?> createBlacklist(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int resort_id){
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
            blackService.create(tokenService.getUserId(accessToken), resort_id);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/blacklist")
    public ResponseEntity<?> getBlacklist(@RequestHeader String accessToken,@RequestHeader String refreshToken) {
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
            List<Blacklist> blacklist = blackService.getListByUser(tokenService.getUserId(accessToken));
            resListDTO.setList(Arrays.asList(blacklist.toArray()));
            return new ResponseEntity<>(resListDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }


    @DeleteMapping(value = "/blacklist/{resort_id}")
    public ResponseEntity<?> deleteBlacklist(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int resort_id){
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
            blackService.delete(tokenService.getUserId(accessToken), resort_id);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
}
