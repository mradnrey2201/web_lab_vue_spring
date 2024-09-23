package springvuesite.backend.controllers;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springvuesite.backend.models.Point;

import java.util.Collection;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    Collection<Point> findAllByUsername(@Param("username") String username);
    @Transactional
    void removeAllByUsername(@Param("username") String username);
}
