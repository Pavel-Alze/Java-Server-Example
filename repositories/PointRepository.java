package test.connectdb.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.connectdb.models.Point;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point,Integer> {
    @Transactional
    @Query(nativeQuery = true, value = "select * from points where route_id = ?1")
    List<Point> find_all(int route_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from points where route_id = ?1")
    void delete_all(int route_id);
}
