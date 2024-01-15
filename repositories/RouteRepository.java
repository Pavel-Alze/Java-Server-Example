package test.connectdb.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.connectdb.models.Route;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route,Integer> {
    @Transactional
    @Query(nativeQuery = true, value = "select * from routes where resort_id = ?1")
    List<Route> findByResort(int resort_id);
}
