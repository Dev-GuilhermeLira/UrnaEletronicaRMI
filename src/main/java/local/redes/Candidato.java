
package local.redes;

import java.io.Serializable;
import java.util.Objects;

// @author GUILHERME CORREIA LIRA DE SOUZA
public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int numero;
    private final String nome;

    public Candidato(int numero, String nome) {
        if (numero <= 0) {
            throw new IllegalArgumentException(
                    "O número do candidato deve ser positivo."
            );
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do candidato é obrigatório."
            );
        }

        this.numero = numero;
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return numero + " - " + nome;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Candidato outro)) {
            return false;
        }

        return numero == outro.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
