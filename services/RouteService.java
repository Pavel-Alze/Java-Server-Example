package test.connectdb.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.PointDTO;
import test.connectdb.dto.PostRouteDTO;
import test.connectdb.dto.RouteDTO;
import test.connectdb.models.Point;
import test.connectdb.models.Route;
import test.connectdb.repositories.RouteRepository;
import test.connectdb.services.DAO.RouteDAO;
import test.connectdb.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService implements RouteDAO<Route,Integer,RouteDTO, PostRouteDTO> {

    private static final Logger log = Logger.getLogger(RouteService.class);

    @Autowired
    PointService pointService;
    @Autowired
    Mapper mapper;
    @Autowired
    RouteRepository routeRepository;

    @Override
    public List<RouteDTO> get_all(Integer integer) throws AppError {
        try {
            List<RouteDTO> routeDTOList = new ArrayList<>();
            List<Route> routeList = routeRepository.findByResort(integer);
            if(!routeList.isEmpty()) {
                for (Route route : routeList) {
                    routeDTOList.add(get(route));
                }
            }
            return routeDTOList;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public RouteDTO get(Route route) throws AppError {
        try {
            RouteDTO routeDTO = new RouteDTO();
            List<Point> pointList = new ArrayList<>();
            pointList = pointService.getByRoute(route.getId());
            routeDTO = mapper.RouteEntityToDto(route,routeDTO,pointList);
            return routeDTO;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    @Override
    public RouteDTO getById(Integer integer) throws AppError {
        try {
            Optional<Route> route = routeRepository.findById(integer);
            if(route.isEmpty()){
                log.info("Route with ID: " + integer + " not found");
                throw new AppError(404,"Route with ID: " + integer + " not found. Please check the correctness of the data");
            }else {
                RouteDTO routeDTO = new RouteDTO();
                List<Point> pointList = new ArrayList<>();
                pointList = pointService.getByRoute(integer);
                routeDTO = mapper.RouteEntityToDto(route.get(), routeDTO, pointList);
                return routeDTO;
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
    public void create(PostRouteDTO postRouteDTO) throws AppError {
        try {
            Route route = new Route();
            route = mapper.RouteDtoToEntity(postRouteDTO,route);
            routeRepository.save(route);
            for(PointDTO pointDTO : postRouteDTO.getRoute()){
                Point point = new Point();
                point = mapper.PointDtoToEntity(pointDTO,point,route.getId());
                pointService.create(point);
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
    public void update(Integer integer, PostRouteDTO postRouteDTO) throws AppError {
        try {
            RouteDTO routeDTO = getById(integer);
            Optional<Route> route = routeRepository.findById(integer);
            boolean diff = false;
            if(routeDTO.getRoute().size()==postRouteDTO.getRoute().size()){
                for (int i = 0; i < routeDTO.getRoute().size(); i++){
                    if(!routeDTO.getRoute().get(i).toString().equals(postRouteDTO.getRoute().get(i).toString())){
                        diff = true;
                        break;
                    }
                }
            }else{
                diff = true;
            }
            if(diff) {
                pointService.deleteAll(routeDTO.getId());
                for (PointDTO pointDTO : postRouteDTO.getRoute()) {
                    Point point = new Point();
                    point = mapper.PointDtoToEntity(pointDTO, point, routeDTO.getId());
                    pointService.create(point);
                }
            }
            route.get().setName(postRouteDTO.getName());
            routeRepository.save(route.get());
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
    public void deleteAll(Integer integer) throws AppError {
        try {
            List<Route> routeList = routeRepository.findByResort(integer);
            if(!routeList.isEmpty()) {
                for (Route route : routeList) {
                    pointService.deleteAll(route.getId());
                    routeRepository.delete(route);
                }
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
        try {
            Optional<Route> route = routeRepository.findById(integer);
            if (route.isEmpty()) {
                log.info("Route not found");
                throw new AppError(404, "Route not found. Please check the correctness of the data");
            }
            pointService.deleteAll(route.get().getId());
            routeRepository.delete(route.get());
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
}
