package test.connectdb.services;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import test.connectdb.AppError;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Service
public class PassService {
    private static final Logger log = Logger.getLogger(LoginService.class);
    //Функция принимает пароль и возвращает хэш и соль, соль тоже нужно запомнить, чтобы потом проверить правильность пароля
    public String[] getHash(String pass) throws NoSuchAlgorithmException, InvalidKeySpecException, AppError {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String passhash[] = {Base64.encodeBase64String(hash), Base64.encodeBase64String(salt)};
            return passhash;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal serer error");
        }
    }
    //Вернет тру, если хэши паролей совпадает. Принимает пароль пришедший от пользователя, заново хэширует его при помощи соли из бд, и проверяет совпадение хэшей
    public boolean isRightPass(String pass, String passhashfromBD, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException, AppError {
        try {
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), Base64.decodeBase64(salt), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String passhash = Base64.encodeBase64String(hash);
            if (passhash.equals(passhashfromBD)) return true;
            else return false;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal serer error");
        }
    }
    //Если пароль короткий вернет фолс
    public boolean isHardPass(String pass) throws AppError {
        try {
            if (pass.length() < 8) {
                return false;
            }
            return true;
        }catch (Exception e){
            log.error(e);
            throw new AppError(500,"Internal serer error");
        }
    }
}