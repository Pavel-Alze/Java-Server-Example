package test.connectdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.connectdb.models.RideAnalitic;

@Repository
public interface RideAnaliticRepository extends JpaRepository<RideAnalitic,Integer> {
}
