package test.connectdb.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.connectdb.models.Review;
import test.connectdb.models.RideCount;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideCountRepository extends JpaRepository<RideCount,Integer> {
    @Transactional
    @Query(nativeQuery = true, value = "select * from ride_count where user_id = ?1")
    Optional<RideCount> findByUser(int user_id);
}
