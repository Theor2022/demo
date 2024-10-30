package cooweb.services;

import cooweb.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioServices {

    List<Usuario> getUsuarios();

    void eliminar(Long id);

    Object registrar(Usuario usuario);

    boolean verificarCredenciales(Usuario usuario);

    boolean existePorEmail(String email);

}
