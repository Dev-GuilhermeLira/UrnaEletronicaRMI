package local.redes;

import java.util.ArrayList;
import java.util.List;

// @author GUILHERME CORREIA LIRA DE SOUZA
public class CadCandidato {

    public static List<Candidato> listarCandidatos() {

        List<Candidato> candidatos = new ArrayList<>();

        candidatos.add(new Candidato(10, "João Márcio"));
        candidatos.add(new Candidato(20, "Maria Rita"));
        candidatos.add(new Candidato(30, "Vitor Bello"));
        candidatos.add(new Candidato(40, "Augusto Patto"));

        return candidatos;
    }
}