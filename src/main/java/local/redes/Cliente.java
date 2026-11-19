
package local.redes;

import java.awt.Color;
import java.awt.Font;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

// @author GUILHERME CORREIA LIRA DE SOUZA
public class Cliente extends JFrame {

    private static final long serialVersionUID = 1L;

    private final ServicoUrna servico;
    private final JComboBox<Candidato> comboCandidatos;
    private final JTextField campoVotos;
    private final JLabel mensagemStatus;

    public Cliente(ServicoUrna servico) {
        this.servico = servico;

        setTitle("Urna Eletrônica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel painel = new JPanel(null);
        painel.setBackground(new Color(240, 240, 240));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Urna Java");
        titulo.setFont(new Font("Arial", Font.PLAIN, 52));
        titulo.setBounds(20, 25, 400, 65);
        painel.add(titulo);

        JLabel labelCandidato = new JLabel("Selecione o Candidato");
        labelCandidato.setFont(new Font("Arial", Font.PLAIN, 14));
        labelCandidato.setBounds(20, 125, 250, 25);
        painel.add(labelCandidato);

        JLabel labelVotos = new JLabel("Quantidade de votos");
        labelVotos.setFont(new Font("Arial", Font.PLAIN, 14));
        labelVotos.setBounds(335, 125, 150, 25);
        painel.add(labelVotos);

        comboCandidatos = new JComboBox<>(
                CadCandidato.listarCandidatos()
                        .toArray(new Candidato[0])
        );

        comboCandidatos.setBounds(20, 155, 295, 30);
        comboCandidatos.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(comboCandidatos);

        campoVotos = new JTextField();
        campoVotos.setBounds(335, 155, 130, 30);
        campoVotos.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(campoVotos);

        JButton botaoEnviar = new JButton("Enviar ao Servidor Central");
        botaoEnviar.setBounds(245, 260, 220, 45);
        botaoEnviar.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(botaoEnviar);

        botaoEnviar.addActionListener(e -> computarVotos());

        mensagemStatus = new JLabel("Aguardando envio de dados...");
        mensagemStatus.setOpaque(true);
        mensagemStatus.setBackground(new Color(205, 205, 205));
        mensagemStatus.setBorder(new EmptyBorder(3, 8, 3, 8));
        mensagemStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        mensagemStatus.setBounds(20, 315, 445, 28);
        painel.add(mensagemStatus);

        setContentPane(painel);
        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    private void computarVotos() {

        Candidato candidato =
                (Candidato) comboCandidatos.getSelectedItem();

        try {
            int quantidade = Integer.parseInt(
                    campoVotos.getText().trim()
            );

            if (candidato == null || quantidade <= 0) {
                mensagemStatus.setText(
                        "Informe uma quantidade válida de votos."
                );
                return;
            }

            servico.computarVotos(candidato, quantidade);

            mensagemStatus.setText(
                    "Dados enviados com sucesso!"
            );

            campoVotos.setText("");

        } catch (NumberFormatException e) {

            mensagemStatus.setText(
                    "Informe uma quantidade válida de votos."
            );

        } catch (Exception e) {

            mensagemStatus.setText(
                    "Erro ao enviar dados ao servidor."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível enviar os votos.\n"
                            + "Verifique a conexão com o servidor central.",
                    "Erro de comunicação",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                Registry registro = LocateRegistry.getRegistry(
                        "127.0.0.1", 1099
                );

                ServicoUrna servico = (ServicoUrna)
                        registro.lookup("ServicoUrna");

                Cliente cliente = new Cliente(servico);
                cliente.setVisible(true);

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Não foi possível conectar ao servidor.\n"
                                + "Verifique se o servidor está iniciado.\n"
                                + "Endereço: 127.0.0.1\n"
                                + "Porta: 1099",
                        "Erro de conexão",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
