package kr.side.dstar.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    @Query("SELECT s FROM Scrap s ORDER BY s.id DESC")
    List<Scrap> findAllDesc();
}
