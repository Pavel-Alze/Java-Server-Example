package test.connectdb.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.*;
import test.connectdb.models.*;
import test.connectdb.services.PassService;
import test.connectdb.services.PointService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class Mapper {

    @Autowired
    PassService passService;
    private static final Logger log = Logger.getLogger(Mapper.class);
    public Login LoginDtoToEntity(PostLoginDTO loginDTO, Login loginEntity) throws AppError {
        try {
            loginEntity.setLogin(loginDTO.getLogin());
            String[] pass_salt = passService.getHash(loginDTO.getPassword());
            loginEntity.setPassword(pass_salt[0]);
            loginEntity.setSalt(pass_salt[1]);
            return loginEntity;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    public User UserDtoToEntity(PostUserDTO userDTO, User userEntity) throws AppError {
        try {
            userEntity.setName(userDTO.getName());
            userEntity.setSurname(userDTO.getSurname());
            userEntity.setAge(userDTO.getAge());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setPhone(userDTO.getPhone());
            return userEntity;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    public ResUserDTO UserEntityToDto(User userEntity, ResUserDTO userDTO) throws AppError {
        try {
            userDTO.setId(userEntity.getId());
            userDTO.setAge(userEntity.getAge());
            userDTO.setEmail(userEntity.getEmail());
            userDTO.setName(userEntity.getName());
            userDTO.setPhone(userEntity.getPhone());
            userDTO.setSurname(userEntity.getSurname());
            userDTO.setLogin_id(userEntity.getLogin_id());
            return userDTO;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    public ResResortDTO ResortEntityToDto(Resort resortEntity, ResResortDTO resortDTO, List<RouteDTO> routeDTOList) throws AppError {
        try {
            resortDTO.setId(resortEntity.getId());
            resortDTO.setTitle(resortEntity.getTitle());
            resortDTO.setDescription(resortEntity.getDescription());
            resortDTO.setPointlan(resortEntity.getPointlan());
            resortDTO.setPointlon(resortEntity.getPointlon());
            resortDTO.setRoutes(routeDTOList);
            return resortDTO;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    public Resort ResortDtoToEntity(PostResortDTO postResortDTO, Resort resortEntity) throws AppError {
        try {
            resortEntity.setTitle(postResortDTO.getTitle());
            resortEntity.setDescription(postResortDTO.getDescription());
            resortEntity.setPointlan(postResortDTO.getPointlan());
            resortEntity.setPointlon(postResortDTO.getPointlon());
            return resortEntity;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    public Review ReviewDtoToEntity(PostReviewDTO reviewDTO, Review reviewEntity) throws AppError {
        try {
            reviewEntity.setTitle(reviewDTO.getTitle());
            reviewEntity.setDescription(reviewDTO.getDescription());
            reviewEntity.setResort_id(reviewDTO.getResort_id());
            reviewEntity.setUser_id(reviewDTO.getUser_id());
            return reviewEntity;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    public ResReviewDTO ReviewEntityToDto(Review reviewEntity, ResReviewDTO reviewDTO) throws AppError {
        try {
            reviewDTO.setId(reviewEntity.getId());
            reviewDTO.setTitle(reviewEntity.getTitle());
            reviewDTO.setDescription(reviewEntity.getDescription());
            reviewDTO.setResort_id(reviewEntity.getResort_id());
            reviewDTO.setUser_id(reviewEntity.getUser_id());
            return reviewDTO;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    public List<PointDTO> PointListEntityToDto(List<Point> pointEntityList,List<PointDTO> pointDTOList) throws AppError {
        try {
            for (Point point : pointEntityList) {
                PointDTO pointDTO = new PointDTO();
                pointDTO.setPointlan(point.getPointlan());
                pointDTO.setPointlon(point.getPointlon());
                pointDTOList.add(pointDTO);
            }
            return pointDTOList;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    public RouteDTO RouteEntityToDto(Route routeEntity, RouteDTO routeDTO, List<Point> pointList) throws AppError {
        try {
            List<PointDTO> pointDTOList = new ArrayList<>();
            pointDTOList = PointListEntityToDto(pointList, pointDTOList);
            routeDTO.setId(routeEntity.getId());
            routeDTO.setName(routeEntity.getName());
            routeDTO.setResort_id(routeEntity.getResort_id());
            routeDTO.setRoute(Arrays.asList(pointDTOList.toArray()));
            return routeDTO;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    public Route RouteDtoToEntity(PostRouteDTO postRouteDTO, Route routeEntity) throws AppError {
        try {
            routeEntity.setName(postRouteDTO.getName());
            routeEntity.setResort_id(postRouteDTO.getResort_id());
            return routeEntity;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    public Point PointDtoToEntity(PointDTO pointDTO, Point pointEntity, Integer route_id) throws AppError {
        try {
            pointEntity.setPointlon(pointDTO.getPointlon());
            pointEntity.setPointlan(pointDTO.getPointlan());
            pointEntity.setRoute_id(route_id);
            return pointEntity;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    public ResRouteDTO RouteToDtoRes(RouteDTO routeDTO, ResRouteDTO resRouteDTO) throws AppError {
        try {
            resRouteDTO.setId(routeDTO.getId());
            resRouteDTO.setName(routeDTO.getName());
            resRouteDTO.setResort_id(routeDTO.getResort_id());
            resRouteDTO.setRoute(routeDTO.getRoute());
            return resRouteDTO;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    public RideAnalitic AnaliticDtoToEntity(AnaliticDTO analiticDTO, RideAnalitic rideAnalitic,Integer user_id) throws AppError{
        try {
            enum diff {EASY,MEDIUM,HARD}
            rideAnalitic.setTime(analiticDTO.getTime());
            rideAnalitic.setRoute_id(analiticDTO.getRoute_id());
            rideAnalitic.setUser_id(user_id);
            if (analiticDTO.getDifficulty().equals(diff.EASY.toString()) | analiticDTO.getDifficulty().equals(diff.MEDIUM.toString()) | analiticDTO.getDifficulty().equals(diff.HARD.toString())){
                rideAnalitic.setDifficulty(analiticDTO.getDifficulty());
            } else {
                throw new AppError(422,"Bad data");
            }
            return rideAnalitic;
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

}
