package br.com.ada.usuarios.repository;

import br.com.ada.usuarios.model.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, String> {
}
