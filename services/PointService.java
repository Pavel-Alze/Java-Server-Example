package test.connectdb.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.PointDTO;
import test.connectdb.models.Point;
import test.connectdb.repositories.PointRepository;
import test.connectdb.services.DAO.PointDAO;
import test.connectdb.utils.Mapper;

import java.util.List;

@Service
public class PointService implements PointDAO<Point,Integer, PointDTO> {

    private static final Logger log = Logger.getLogger(PointService.class);

    @Autowired
    PointRepository pointRepository;

    @Override
    public List<Point> getByRoute(Integer integer) throws AppError {
        try{
            List<Point> pointList = pointRepository.find_all(integer);
            return pointList;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public void create(Point point) throws AppError {
        try{
            pointRepository.save(point);
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public void deleteAll(Integer integer) throws AppError {
        try{
            pointRepository.delete_all(integer);
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

}
