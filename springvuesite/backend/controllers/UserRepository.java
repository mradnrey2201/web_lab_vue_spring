package springvuesite.backend.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springvuesite.backend.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(@Param("username") String username);
}