package Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    private String matricula;

    private String senha;

    private String nome;

    private String sobrenome;
}
