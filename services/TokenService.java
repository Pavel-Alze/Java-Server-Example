package test.connectdb.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;
import test.connectdb.models.AccessTokenModel;
import test.connectdb.models.RefreshTokenModel;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final Logger log = Logger.getLogger(TokenService.class);

    //как я уже сказал, тела методов я ещё не прописывал, можешь сдлеать это сам
    //для обращения к бд используй объявленный репозиторий ниже
    //так же можешь создавать свои методы, но не забудь их объявиться в интерфейсе в папке DAO
    //если где надо будет сделать обращение к бд, а не сам алгоритм обработки
    //можешь попытаться сам, либо оставляй мне комменты в нужном месте и указанием
    //того, что тебе нужно

    private static final String SECRET = "Ihavethekeyyjzdfvtujytcrf;e";
    private static final String SECRET_REFRESH = "AhSHIIIITHereWeGoAGAIn";

    //Возвращает рефрештокен
    public String createRefreshToken(String token) throws AppError {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            //блок для шифрования
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(SECRET_REFRESH.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            //Зашиваем дату в токен
            Date dataCreateToken = new Date();
            RefreshTokenModel payload = new RefreshTokenModel(dataCreateToken.getTime());

            //Делаем JSON
            String jsonPayload = objectMapper.writeValueAsString(payload);

            //Кодируем полезную нагрузку
            String payloadHash = Base64.encodeBase64String(jsonPayload.getBytes());

            //Шифруем сигнатуру
            String signatura = Base64.encodeBase64String(sha256_HMAC.doFinal(token.getBytes()));

            return payloadHash + "." + signatura;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    //Проверяет годен ли рефреш токен и его ликвидность. Возвращает тру если все оккей, в любом другом случае фолс
    public boolean isLiquser_idReshresh(String token, String refresh) throws AppError {
        try {
            //Берем полезную нагрузку
            int i = refresh.indexOf(".");
            String payload = refresh.substring(0, i);
            String decode = new String(Base64.decodeBase64(payload.getBytes()));

            //Из JSON  в объект
            ObjectMapper objectMapper = new ObjectMapper();
            RefreshTokenModel jsonRefreshToken = objectMapper.readValue(decode, RefreshTokenModel.class);
            //проверяем дату
            long diff = new Date().getTime() - jsonRefreshToken.getData();
            long TTL = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (TTL > 30) return false;

            //Блок дешифрования, проверяем сигнатуру
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(SECRET_REFRESH.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String signatura = refresh.substring(i + 1);
            String reSignatura = Base64.encodeBase64String(sha256_HMAC.doFinal(token.getBytes()));
            if (signatura.equals(reSignatura)) return true;
            else return false;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

    //Возвращает токен доступа
    public String creteToken(int user) throws AppError {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //Блок для шифрования
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            //Создаем хедер
            Date dataCreateToken = new Date();
            String header = "{ \"alg\": \"HS256\", \"typ\": \"JWT\"}";
            String headerHash = Base64.encodeBase64String(header.getBytes());
            //String payload = "{ \"user\": \""+user+"\", \"Data\": \""+dataCreateToken+"\" }";

            AccessTokenModel payload = new AccessTokenModel(user, dataCreateToken.getTime());

            //Создаем JSON
            String jsonPayload = objectMapper.writeValueAsString(payload);
            //кодируем
            String payloadHash = Base64.encodeBase64String(jsonPayload.getBytes());
            //Шифруем сигнатуру
            String unSignature = headerHash + "." + payloadHash;
            String signatura = Base64.encodeBase64String(sha256_HMAC.doFinal(unSignature.getBytes()));
            return unSignature + "." + signatura;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    //Вернет тру, если наш сервер выписал токен
    public boolean isValidToken(String token) throws AppError {
        try {
            int i = token.lastIndexOf(".");
            String unsignatura = token.substring(0, i);
            String signatura = token.substring(i + 1);

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String reSignatura = Base64.encodeBase64String(sha256_HMAC.doFinal(unsignatura.getBytes()));
            if (signatura.equals(reSignatura)) return true;
            else return false;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    //Возвращает JSON полезной нагрузки токена
    public String encodeToken(String token) throws AppError {
        try {
            int first_i = token.indexOf(".");
            int last_i = token.lastIndexOf(".");
            String payload = token.substring(first_i + 1, last_i);
            String decode = new String(Base64.decodeBase64(payload.getBytes()));
            return decode;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }
    //Вернет тру, если токен доступа еще годен
    public boolean expirationDate(String token) throws AppError {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = encodeToken(token);
            AccessTokenModel payload = objectMapper.readValue(jsonPayload, AccessTokenModel.class);
            long diff = new Date().getTime() - payload.getDate();
            long TTL = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (TTL > 1) return false;
            else return true;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500, "Internal server error");
        }
    }
    //Вернет юзера из токена
    public int getUserId(String token)throws AppError {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = encodeToken(token);
            AccessTokenModel payload = objectMapper.readValue(jsonPayload, AccessTokenModel.class);
            return payload.getUser();
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal server error");
        }
    }

}
