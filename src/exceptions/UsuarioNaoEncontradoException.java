package exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    
    public UsuarioNaoEncontradoException() {
        super("Login ou senha inválidos, tente novamente");
    }

}
