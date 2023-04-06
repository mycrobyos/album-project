package br.com.ada.usuarios.repository;

import br.com.ada.usuarios.model.entity.Phone;
import br.com.ada.usuarios.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query(value = "SELECT p FROM Phone p WHERE p.usuario.id = :id")
    List<Phone> findPhones(String id);
}
