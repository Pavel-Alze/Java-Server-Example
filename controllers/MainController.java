package test.connectdb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.connectdb.AppError;
import test.connectdb.dto.*;
import test.connectdb.models.*;
import test.connectdb.services.*;
import test.connectdb.utils.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
@RestController
@RequestMapping(value = "/user")
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);
    private final LoginService loginService;
    private final TokenService tokenService;
    private final UserService userService;


    public MainController(LoginService loginService, TokenService tokenService, UserService userService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Autowired
    Mapper mapper;
    @PostMapping(value = "/registration/login")
    public ResponseEntity<?> create_login(@RequestBody PostLoginDTO loginDTO){
        try{
            ResIdDTO resIdDTO = new ResIdDTO();
            int id;
            id = loginService.create(loginDTO);
            resIdDTO.setId(id);
            return new ResponseEntity<>(resIdDTO,HttpStatusCode.valueOf(201));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }
    }

    @PostMapping(value = "/form_filling")
    public ResponseEntity<?> create_user(@RequestHeader String accessToken,@RequestHeader String refreshToken,@RequestBody PostUserDTO postUserDTO){
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
            userService.update(tokenService.getUserId(accessToken),postUserDTO);
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(201));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/authorisation")
    public ResponseEntity<?> auth(@RequestBody PostLoginDTO loginDTO){
        try{
            Optional<Login> log = loginService.auth(loginDTO);
            Optional<User> user = userService.getByLogin(log.get().getId());
            String accessToken = tokenService.creteToken(user.get().getId()); //в функцию передать id пользователя
            String refreshToken = tokenService.createRefreshToken(accessToken);
            TokensDTO tokensDTO = new TokensDTO();
            tokensDTO.setAccessToken(accessToken);
            tokensDTO.setRefreshToken(refreshToken);

            logger.info("User success log in with: " + loginDTO.toString());
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(500));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<?> findUser(@RequestHeader String accessToken,@RequestHeader String refreshToken){
        try {
            ResUserDTO resUserDTO = new ResUserDTO();
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
            resUserDTO.setRefreshToken(refreshToken);
            resUserDTO.setAccessToken(accessToken);
            resUserDTO = mapper.UserEntityToDto(userService.getByPk(tokenService.getUserId(accessToken)).get(),resUserDTO);
            return new ResponseEntity<>(resUserDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

}
