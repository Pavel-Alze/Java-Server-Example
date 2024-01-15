package test.connectdb.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.connectdb.AppError;
import test.connectdb.dto.PostRouteDTO;
import test.connectdb.dto.ResRouteDTO;
import test.connectdb.dto.RouteDTO;
import test.connectdb.dto.TokensDTO;
import test.connectdb.services.*;
import test.connectdb.utils.Mapper;

@RestController
@RequestMapping(value = "/route")
public class RouteController {

    private static final Logger logger = Logger.getLogger(RouteController.class);

    private final TokenService tokenService;
    private final RouteService routeService;


    @Autowired
    Mapper mapper;

    public RouteController(TokenService tokenService, RouteService routeService) {
        this.tokenService = tokenService;
        this.routeService = routeService;
    }

    @GetMapping(value = "/{route_id}")
    public ResponseEntity<?> findRoute(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int route_id){
        try{
            ResRouteDTO resRouteDTO = new ResRouteDTO();
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
            RouteDTO routeDTO = routeService.getById(route_id);
            mapper.RouteToDtoRes(routeDTO,resRouteDTO);
            resRouteDTO.setAccessToken(accessToken);
            resRouteDTO.setRefreshToken(refreshToken);
            return new ResponseEntity<>(resRouteDTO,HttpStatusCode.valueOf(200));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/new")
    public ResponseEntity<?> createRoute(@RequestHeader String accessToken, @RequestHeader String refreshToken, @RequestBody PostRouteDTO postRouteDTO){
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
            routeService.create(postRouteDTO);
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

    @PatchMapping(value = "/{route_id}")
    public ResponseEntity<?> updateRoute(@RequestHeader String accessToken, @RequestHeader String refreshToken, @PathVariable int route_id, @RequestBody PostRouteDTO postRouteDTO){
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
            routeService.update(route_id,postRouteDTO);
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
    @DeleteMapping(value = "/{route_id}")
    public ResponseEntity<?> deleteRoute(@RequestHeader String accessToken,@RequestHeader String refreshToken, @PathVariable int route_id){
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
            routeService.delete(route_id);
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
}
