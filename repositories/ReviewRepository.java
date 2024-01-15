package test.connectdb.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.connectdb.models.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

    @Transactional
    @Query(nativeQuery = true, value = "select * from reviews where user_id = ?1 and resort_id = ?2")
    List<Review> findByUserResort(int user_id, int resort_id);

    @Transactional
    @Query(nativeQuery = true, value = "select * from reviews where user_id = ?1")
    List<Review> findByUser(int user_id);

    @Transactional
    @Query(nativeQuery = true, value = "select * from reviews where resort_id = ?1")
    List<Review> findByResort(int resort_id);
}
