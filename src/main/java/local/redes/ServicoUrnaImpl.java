package local.redes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

// @author GUILHERME CORREIA LIRA DE SOUZA
public class ServicoUrnaImpl extends UnicastRemoteObject
        implements ServicoUrna {

    private final Map<Candidato, Integer> votos;

    public ServicoUrnaImpl() throws RemoteException {
        super();

        votos = new LinkedHashMap<>();

        for (Candidato candidato : CadCandidato.listarCandidatos()) {
            votos.put(candidato, 0);
        }
    }

    @Override
    public synchronized void computarVotos(Candidato candidato, int quantidade) throws RemoteException {

        if (candidato == null || !votos.containsKey(candidato)) {
            throw new RemoteException("Candidato não encontrado.");
        }

        if (quantidade <= 0) {
            throw new RemoteException(
                    "A quantidade de votos deve ser maior que zero."
            );
        }

        votos.merge(candidato, quantidade, Integer::sum);
    }

    public synchronized Map<Candidato, Integer> obterApuracao() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(votos));
    }
}
