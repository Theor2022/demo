package cooweb.repository;

import cooweb.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    List<Usuario> findAll();
    Optional<Usuario> findByEmail(String email);

}
