package project.tutorial.spring.domain.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import project.tutorial.spring.domain.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
