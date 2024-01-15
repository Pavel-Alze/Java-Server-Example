package test.connectdb.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.dto.PostResortDTO;
import test.connectdb.dto.ResResortDTO;
import test.connectdb.dto.RouteDTO;
import test.connectdb.models.Resort;
import test.connectdb.repositories.ResortRepository;
import test.connectdb.services.DAO.ResortDAO;
import test.connectdb.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResortService implements ResortDAO<Resort,Integer,ResResortDTO,PostResortDTO> {

    private static final Logger log = Logger.getLogger(ResortService.class);

    @Autowired
    RouteService routeService;
    @Autowired
    ResortRepository resortRepository;
    @Autowired
    Mapper mapper;
    @Override
    public void create(PostResortDTO postResortDTO) throws AppError {
        try {
            Resort resort = new Resort();
            resort = mapper.ResortDtoToEntity(postResortDTO, resort);
            resortRepository.save(resort);
            log.info("Resort created."+ resort.toString());
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public void update(Integer integer, PostResortDTO resortDTO) throws AppError {
        try {
            Optional<Resort> resort = resortRepository.findById(integer);
            if (resort.isEmpty()) {
                log.info("Resort with ID: " + integer + " not found");
                throw new AppError(404, "Resort with ID: " + integer + " not found. Please check the correctness of the data");
            }
            resort.get().setTitle(resortDTO.getTitle());
            resort.get().setDescription(resortDTO.getDescription());
            resort.get().setPointlan(resortDTO.getPointlan());
            resort.get().setPointlon(resortDTO.getPointlon());
            resortRepository.save(resort.get());
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public List<Resort> getAll(Integer user_id) throws AppError {
        try {
            return resortRepository.find_all(user_id);
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    @Override
    public ResResortDTO getByPk(Integer integer) throws AppError {
        try {
            ResResortDTO resortDTO = new ResResortDTO();
            List<RouteDTO> routeDTOList = new ArrayList<>();
        Optional<Resort> resort = resortRepository.findById(integer);
        if(resort.isEmpty()){
            log.info("Resort with ID: " + integer + " not found");
            throw new AppError(404,"Resort with ID: " + integer + " not found. Please check the correctness of the data");
        }
        routeDTOList = routeService.get_all(integer);
        resortDTO = mapper.ResortEntityToDto(resort.get(),resortDTO,routeDTOList);
        return resortDTO;
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
            if (resortRepository.findById(integer).isEmpty()) {
                log.info("Resort with ID: " + integer + " not found");
                throw new AppError(404, "Resort with ID: " + integer + " not found. Please check the correctness of the data");
            }
            routeService.deleteAll(integer);
            resortRepository.deleteById(integer);
        }catch (AppError e){
            log.error(e);
            throw e;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
}
