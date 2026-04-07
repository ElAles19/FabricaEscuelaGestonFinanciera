// import statements

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody Usuario usuario) {
        // registration logic
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // login logic
        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

    // Removed /obtenerUsuario and /listarUsuarios endpoints
}