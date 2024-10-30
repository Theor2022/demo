package cooweb.controllers;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import cooweb.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cooweb.services.UsuarioServices;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping
    public ResponseEntity <List<Usuario>> getUsuarios(){
        List<Usuario> usuarios = usuarioServices.getUsuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();}
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("{id}")
        public ResponseEntity<Void>eliminar(@PathVariable Long id){
        usuarioServices.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        // Verificar si el correo electrónico ya está en uso
        if (usuarioServices.existePorEmail(usuario.getEmail())) {
            return ResponseEntity.status(409).body("El email ya está en uso.");
        }

        // Hashear la contraseña
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);

        // Registrar el usuario
        Usuario registrado = (Usuario) usuarioServices.registrar(usuario);

        // Verificar si el usuario fue registrado exitosamente
        if (registrado != null) {
            return ResponseEntity.ok().body("Usuario registrado con éxito");
        } else {
            return ResponseEntity.status(400).body("Error al registrar el usuario");
        }
    }
}
