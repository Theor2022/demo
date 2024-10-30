package cooweb.services;

import cooweb.repository.UsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import cooweb.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServicesImp implements UsuarioServices {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @SuppressWarnings("unchecked")
    @Override
    public List<Usuario> getUsuarios() {
        String query = "SELECT u FROM Usuario u"; // Mejora en la consulta
        return entityManager.createQuery(query, Usuario.class).getResultList(); // Usa el tipo de retorno
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        if (usuario != null) { // Verificar si el usuario existe
            entityManager.remove(usuario);
        } else {
            // Aquí puedes lanzar una excepción o registrar un mensaje si lo deseas
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    @Override
    public Object registrar(Usuario usuario) {
        return entityManager.merge(usuario);

    }

    @Override
    public boolean verificarCredenciales(Usuario usuario) {

        // Buscar al usuario solo por email
        String query = "FROM Usuario u WHERE u.email = :email";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        // Si el usuario no existe, retornar falso
        if (lista.isEmpty()) {
            return false;
        }
        // Recuperar el hash de la contraseña almacenada
        String passHasheada = lista.get(0).getPassword();

        // Crear la instancia de Argon2
         Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);

        return argon2.verify(passHasheada, usuario.getPassword());
    }
    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

}
