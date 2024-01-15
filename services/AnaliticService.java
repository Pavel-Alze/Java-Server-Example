package test.connectdb.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.AnaliticDTO;
import test.connectdb.dto.RouteDTO;
import test.connectdb.models.RideAnalitic;
import test.connectdb.models.RideCount;
import test.connectdb.models.Route;
import test.connectdb.repositories.RideAnaliticRepository;
import test.connectdb.repositories.RideCountRepository;
import test.connectdb.repositories.RouteRepository;
import test.connectdb.services.DAO.AnaliticDAO;
import test.connectdb.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnaliticService implements AnaliticDAO<AnaliticDTO,Integer> {

    private static final Logger log = Logger.getLogger(AnaliticService.class);
    @Autowired
    Mapper mapper;
    @Autowired
    RideAnaliticRepository rideAnaliticRepository;
    @Autowired
    RideCountRepository rideCountRepository;
    @Override
    public void create(AnaliticDTO analiticDTO, Integer integer) throws AppError {
        try {
            RideAnalitic rideAnalitic = new RideAnalitic();
            rideAnalitic = mapper.AnaliticDtoToEntity(analiticDTO,rideAnalitic,integer);
            rideAnaliticRepository.save(rideAnalitic);
            Optional<RideCount> count= rideCountRepository.findByUser(integer);
            if(count.isEmpty()){
                RideCount rideCount = new RideCount();
                rideCount.setUser_id(integer);
                rideCount.setRoute_id(analiticDTO.getRoute_id());
                rideCount.setCount(1);
                rideCountRepository.save(rideCount);
            }else {
                count.get().setCount(count.get().getCount()+1);
                rideCountRepository.save(count.get());
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
