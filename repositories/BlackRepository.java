package test.connectdb.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.connectdb.models.Blacklist;
import test.connectdb.models.Likelist;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlackRepository extends JpaRepository<Blacklist, Integer> {
    @Transactional
    @Query(nativeQuery = true, value = "select * from blacklist_resorts where user_id = ?1")
    List<Blacklist> find_blacklist(int user_id);

    @Transactional
    @Query(nativeQuery = true, value = "select * from blacklist_resorts where user_id = ?1 and resort_id = ?2")
    Optional<Blacklist> find_oneBlack(int user_id, int resort_id);

}
