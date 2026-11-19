package local.redes;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

// @author GUILHERME CORREIA LIRA DE SOUZA
public class Servidor {

    public static void main(String[] args) {

        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");

            Registry registro = LocateRegistry.createRegistry(1099);

            ServicoUrnaImpl servico = new ServicoUrnaImpl();

            registro.rebind("ServicoUrna", servico);

            System.out.println("==================================");
            System.out.println(" CENTRAL DE APURACAO DE VOTOS");
            System.out.println("==================================");
            System.out.println("Servidor iniciado com sucesso!");
            System.out.println("Porta RMI: 1099");
            System.out.println("Aguardando dados das urnas...");
            System.out.println();

            while (true) {
                Thread.sleep(5000);

                System.out.println("===== APURACAO ATUALIZADA =====");

                Map<Candidato, Integer> apuracao =
                        servico.obterApuracao();

                for (Map.Entry<Candidato, Integer> resultado
                        : apuracao.entrySet()) {

                    System.out.println(
                            resultado.getKey().getNome()
                                    + " - "
                                    + resultado.getValue()
                                    + " votos"
                    );
                }

                System.out.println("===============================");
                System.out.println();
            }

        } catch (Exception e) {
            System.err.println(
                    "Erro ao iniciar ou executar o servidor."
            );

            System.err.println(
                    "Verifique se a porta 1099 está disponível."
            );
        }
    }
}
