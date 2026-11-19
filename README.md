# 🗳️ Urna Eletrônica — Java RMI

Projeto acadêmico desenvolvido na disciplina **Java Redes**, com o objetivo de aplicar os conceitos de comunicação entre aplicações distribuídas utilizando **Java RMI (Remote Method Invocation)**.

A aplicação simula um sistema de apuração de votos, composto por um cliente com interface gráfica e um servidor central responsável pelo recebimento, armazenamento e contabilização dos votos.

## 🎯 Objetivo do Projeto

Desenvolver uma aplicação distribuída utilizando Java RMI, permitindo que uma ou mais urnas eletrônicas enviem informações de votação para um servidor central.

O projeto demonstra na prática conceitos de:

- Arquitetura cliente-servidor.
- Comunicação remota entre aplicações Java.
- Interfaces remotas.
- Serialização de objetos.
- Tratamento de exceções na comunicação de rede.
- Concorrência e sincronização de operações.
- Desenvolvimento de interfaces gráficas com Java Swing.

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Aplicação |
|---|---|
| Java 17+ | Linguagem de programação |
| Java RMI | Comunicação entre cliente e servidor |
| Java Swing | Interface gráfica da urna |
| IntelliJ IDEA | Ambiente de desenvolvimento |
| Collections Framework | Armazenamento e gerenciamento dos votos |

**Bibliotecas:** foram utilizadas APIs nativas do Java, sem dependência de frameworks externos.

## 🏗️ Arquitetura do Projeto

A aplicação utiliza a arquitetura cliente-servidor, com comunicação por meio de Java RMI.

O cliente representa a urna eletrônica, enquanto o servidor centraliza o recebimento e a contabilização dos votos.

```text
                  CLIENTE
              (Urna Eletrônica)
                     |
                     |
              Java RMI / Remote
                     |
                     |
              SERVIDOR CENTRAL
               (Porta 1099)
                     |
                     |
              Serviço de Apuração
                     |
                     |
               Mapa de Votos
                     |
                     |
           Atualização a cada 5 segundos
```

### Fluxo de funcionamento

1. O servidor é iniciado e cria o RMI Registry na porta 1099.
2. O serviço remoto é disponibilizado no registro com o nome `ServicoUrna`.
3. O cliente localiza o serviço remoto por meio do RMI Registry.
4. O usuário seleciona um candidato e informa a quantidade de votos.
5. O cliente envia os dados ao servidor utilizando uma chamada remota.
6. O servidor valida as informações e acumula os votos do candidato.
7. A apuração é apresentada automaticamente no console do servidor a cada 5 segundos.

## 📂 Estrutura do Projeto

```text
local.redes/
│
├── Candidato.java
│
├── CadastroCandidatos.java
│
├── ServicoUrna.java
│
├── ServicoUrnaImpl.java
│
├── Servidor.java
│
└── Cliente.java
```

## 📌 Responsabilidade das Classes

### 1. Candidato.java

Representa o modelo de dados de um candidato.

Responsabilidades:
- Armazenar o número e o nome do candidato.
- Implementar `Serializable`, permitindo a transmissão do objeto pela comunicação RMI.
- Disponibilizar métodos de acesso aos atributos.
- Implementar `equals()` e `hashCode()` para identificação dos candidatos pelo número.

### 2. CadastroCandidatos.java

Responsável pelo cadastro inicial dos candidatos utilizando dados *hardcoded*.

Candidatos cadastrados:

| Número | Candidato |
|---|---|
| 10 | João Márcio |
| 22 | Maria Rita |
| 40 | Vitor Bello |
| 50 | Augusto Patto |

A classe disponibiliza a lista de candidatos para utilização na aplicação.

### 3. ServicoUrna.java

Interface remota que define o contrato de comunicação entre cliente e servidor.

Implementa a interface `Remote` e declara o método:

```java
void computarVotos(Candidato candidato, int quantidade)
        throws RemoteException;
```

### 4. ServicoUrnaImpl.java

Implementação da interface remota `ServicoUrna`.

Responsabilidades:
- Estender `UnicastRemoteObject`.
- Inicializar a apuração dos candidatos com zero votos.
- Receber as solicitações remotas.
- Validar candidatos e quantidades de votos.
- Acumular os votos utilizando `Map` e `merge()`.
- Proteger as operações compartilhadas utilizando `synchronized`.
- Disponibilizar os resultados da apuração.

### 5. Servidor.java

Responsável pela inicialização e gerenciamento do servidor RMI.

Responsabilidades:
- Configurar o endereço de comunicação `127.0.0.1`.
- Criar o RMI Registry na porta `1099`.
- Registrar o serviço remoto com o nome `ServicoUrna`.
- Manter o servidor em execução.
- Atualizar e apresentar a apuração dos votos no console a cada 5 segundos.

### 6. Cliente.java

Responsável pela interface gráfica da urna eletrônica, desenvolvida utilizando Java Swing.

Funcionalidades:
- Exibir os candidatos disponíveis.
- Permitir a seleção de um candidato.
- Permitir a entrada manual da quantidade de votos.
- Enviar os dados ao servidor central por meio de Java RMI.
- Apresentar mensagens de sucesso ou erro em português.

## ▶️ Como Executar o Projeto

### Pré-requisitos

- JDK 17 ou superior.
- IntelliJ IDEA.
- Projeto configurado com o package `local.redes`.

### Passo 1 — Iniciar o servidor

Execute a classe:

```text
Servidor.java
```

O servidor criará automaticamente o RMI Registry na porta `1099`, sem necessidade de iniciar o `rmiregistry` manualmente.

Aguarde a mensagem de inicialização no console.

### Passo 2 — Iniciar o cliente

Com o servidor em execução, execute:

```text
Cliente.java
```

O cliente localizará o serviço remoto registrado no servidor e abrirá a interface gráfica da urna.

### Passo 3 — Enviar votos

1. Selecione um candidato na lista.
2. Informe a quantidade de votos.
3. Clique em **Enviar ao Servidor Central**.
4. Aguarde a confirmação do envio.
5. Consulte a apuração atualizada no console do servidor.

**Importante:** o servidor deve permanecer em execução durante a utilização do cliente.

## 🧪 Exemplo de Funcionamento

Considere o envio de duas solicitações para o mesmo candidato:

| Operação | Quantidade |
|---|---:|
| Primeiro envio | 500 votos |
| Segundo envio | 400 votos |
| **Total acumulado** | **900 votos** |

Resultado esperado no console do servidor:

```text
===== APURACAO ATUALIZADA =====
João Márcio - 900 votos
Maria Rita - 0 votos
Vitor Bello - 0 votos
Augusto Patto - 0 votos
===============================
```

A apuração é atualizada automaticamente a cada 5 segundos.

## 🧠 Conceitos Aplicados

Durante o desenvolvimento foram trabalhados os seguintes conceitos:

- **Java RMI:** invocação de métodos em objetos remotos.
- **Remote:** identificação de interfaces com operações remotas.
- **UnicastRemoteObject:** exportação de objetos para acesso remoto.
- **Serializable:** serialização de objetos transmitidos pela rede.
- **RemoteException:** tratamento de falhas na comunicação remota.
- **Map:** associação entre candidatos e quantidades de votos.
- **Collections:** manipulação de estruturas de dados.
- **Synchronized:** controle de acesso concorrente aos dados compartilhados.
- **Java Swing:** construção de interfaces gráficas.
- **Thread.sleep():** controle do intervalo de atualização da apuração.

## 📚 Considerações Finais

O desenvolvimento deste projeto permitiu aplicar os fundamentos de programação distribuída utilizando Java RMI, demonstrando como diferentes aplicações Java podem se comunicar por meio de chamadas remotas.

A separação entre cliente e servidor possibilitou centralizar a contabilização dos votos, enquanto a interface gráfica tornou possível realizar os envios de maneira simples e intuitiva.

O projeto foi desenvolvido como atividade prática da disciplina **Java Redes**, com foco na compreensão e aplicação dos conceitos de comunicação distribuída e arquitetura cliente-servidor.

---

**Disciplina:** Java Redes  
**Projeto:** Urna Eletrônica com Java RMI  
**Linguagem:** Java  
**IDE:** IntelliJ IDEA  

**Autor:** Seu Nome Completo