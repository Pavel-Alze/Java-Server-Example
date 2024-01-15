package test.connectdb.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.connectdb.AppError;
import test.connectdb.dto.AnaliticDTO;
import test.connectdb.dto.TokensDTO;
import test.connectdb.services.AnaliticService;
import test.connectdb.services.TokenService;

@RestController
@RequestMapping(value = "/analytics")
public class AnaliticsController {

    private final TokenService tokenService;
    private final AnaliticService analiticService;

    private static final Logger logger = Logger.getLogger(ResortController.class);

    public AnaliticsController(TokenService tokenService, AnaliticService analiticService) {
        this.tokenService = tokenService;
        this.analiticService = analiticService;
    }

    @PutMapping(value = "/end-of-trace")
    public ResponseEntity<?> end_of_trace(@RequestHeader String accessToken, @RequestHeader String refreshToken, @RequestBody AnaliticDTO analiticDTO){
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
            analiticService.create(analiticDTO,tokenService.getUserId(accessToken));
            return new ResponseEntity<>(tokensDTO,HttpStatusCode.valueOf(201));
        }catch (AppError error){
            return new ResponseEntity<>(error.getMessage(),HttpStatusCode.valueOf(error.getStatusCode()));
        }catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }
}
