package com.distribuido.sistema.db;

import java.util.List;
import java.util.Optional;

import com.distribuido.sistema.exceptions.UsuarioNaoEncontradoException;
import com.distribuido.sistema.model.Usuario;

public class AlunosDTO {
    
    private static List<Usuario> alunosMatriculados = List.of(
        new Usuario("1234", "12345678", "Lucas", "Severo"),
        new Usuario("339", "!@34asd339", "Anderson", "Andrade"),
        new Usuario("3451", "12335778a", "João", "Silva"));

    public static List<Usuario> obterAlunosMatriculados() {
        return alunosMatriculados;
    }

    public static Usuario obterAlunoPorMatricula(String matricula) {
        Optional<Usuario> usuario = alunosMatriculados
            .stream()
            .filter(aluno -> aluno.getMatricula().equals(matricula))
            .findAny();

        return usuario.orElseThrow(UsuarioNaoEncontradoException::new);
    }

}
