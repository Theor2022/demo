package cooweb.controllers;

import cooweb.models.Usuario;
import cooweb.services.UsuarioServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioServicesImp usuarioServicesImp;

    @PostMapping("/api/login")
    public String login (@RequestBody Usuario usuario) {

        if (usuarioServicesImp.verificarCredenciales(usuario)) {

            return "OK";
        }
        return "FAIL";
    }
}
