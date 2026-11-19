package local.redes;


import java.rmi.Remote;
import java.rmi.RemoteException;

// @author GUILHERME CORREIA LIRA DE SOUZA
public interface ServicoUrna extends Remote {

    void computarVotos(Candidato candidato, int quantidade)
        throws RemoteException;
}
