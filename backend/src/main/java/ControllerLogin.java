import com.mendozanews.apinews.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
// @CrossOrigin(value = "http://localhost:8080")
public class ControllerLogin {
    @Autowired 
    private  UsuarioServicio usuarioServicio;



    @PostMapping("/entrar")
    public ResponseEntity<?> authenticate (@RequestBody LoginRequest loginRequest ){
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            // Lógica de autenticación utilizando el servicio de usuario
            boolean isAuthenticated = usuarioServicio.authenticate(email, password);

            if (isAuthenticated) {
                return ResponseEntity.ok("Autenticación exitosa.");
            } else {
                return ResponseEntity.status(401).body("Credenciales inválidas");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en la autenticación");
        }
       
    }

    static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}

