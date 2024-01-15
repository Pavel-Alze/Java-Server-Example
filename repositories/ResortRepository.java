package test.connectdb.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.connectdb.models.Resort;

import java.util.List;

@Repository
public interface ResortRepository extends JpaRepository<Resort,Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from resorts where id not in (select resort_id from blacklist_resorts where user_id = ?1);")
    public List<Resort> find_all(int user_id);
}
