package br.com.ada.users.repository;

import br.com.ada.users.model.entity.Phone;
import br.com.ada.users.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT p FROM Phone p WHERE p.user.id = :id")
    List<Phone> findPhones(String id);
}
