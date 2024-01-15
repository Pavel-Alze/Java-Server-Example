package test.connectdb.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.connectdb.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    //расширение интерфейса JPA Repository уже имеет некоторые методы, можешь юзать их, но почитай, какие есть
    //либо создавай сам на примере того, что есть у меня, но тут тоже есть тонкости
    //можешь либо сам почитать, либо опять же спросить у меня

    @Transactional
    @Query(nativeQuery = true, value = "select * from users where login_id = ?1")
    Optional<User> findByLogin(int login_id);
}
