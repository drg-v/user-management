package user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import user.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByName(String name);
}
