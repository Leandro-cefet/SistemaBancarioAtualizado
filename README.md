# Sistema Bancário em Java

## 1. Nome do Projeto e Descrição do Sistema

### Nome do Projeto

Sistema Bancário em Java

### Descrição

O Sistema Bancário em Java é uma aplicação desktop desenvolvida para simular as principais operações realizadas em uma instituição bancária. O sistema foi desenvolvido utilizando os conceitos de Programação Orientada a Objetos (POO), linguagem Java, interface gráfica com Java Swing e banco de dados PostgreSQL.

O objetivo do sistema é permitir o gerenciamento de usuários, clientes, contas bancárias e operações financeiras, proporcionando uma aplicação organizada em camadas (Model, DAO, Service e Interface Gráfica).

O sistema oferece as seguintes funcionalidades:

- Login de usuários;
- Cadastro de usuários;
- Cadastro de clientes;
- Cadastro de contas correntes;
- Cadastro de contas poupança;
- Depósitos;
- Saques;
- Transferências entre contas;
- Aplicação de rendimento em contas poupança;
- Consulta de saldo;
- Emissão de extrato bancário;
- Relatório de contas cadastradas.

Todo o acesso ao banco de dados é realizado através da camada DAO, enquanto a camada Service é responsável pelas regras de negócio da aplicação.

---

# 2. Tecnologias Utilizadas

- Linguagem Java
- Java Swing (Interface gráfica)
- PostgreSQL
- JDBC (Java Database Connectivity)
- Apache NetBeans IDE
- Git
- GitHub

---

# 3. Como Compilar e Executar o Projeto

## Pré-requisitos

- Java JDK 21 (ou versão utilizada no projeto)
- PostgreSQL instalado
- Apache NetBeans IDE
- Driver JDBC do PostgreSQL

## Passo 1

Criar o banco de dados PostgreSQL.

Exemplo:

```sql
CREATE DATABASE banco_java;
```

## Passo 2

Executar o script SQL de criação das tabelas.

## Passo 3

Configurar o arquivo de conexão com o banco de dados.

Exemplo:

```properties
url=jdbc:postgresql://localhost:5432/banco_java
usuario=postgres
senha=123456
```

## Passo 4

Abrir o projeto no Apache NetBeans.

## Passo 5

Limpar e construir o projeto.

```
Run → Clean and Build Project
```

## Passo 6

Executar a classe principal.

```
banco.app.SistemaBanco
```

A aplicação abrirá a tela de Login.

---

# 4. Descrição dos Pacotes e Classes

## Pacote banco.app

### SistemaBanco

Classe principal da aplicação. Possui o método main(), responsável por iniciar o sistema e abrir a tela de Login.

---

## Pacote banco.model

### Usuario

Representa os usuários que possuem acesso ao sistema.

### Cliente

Representa os clientes cadastrados no banco.

### ContaBancaria

Classe abstrata que contém os atributos e métodos comuns às contas bancárias.

### ContaCorrente

Especialização da ContaBancaria.

Possui limite de cheque especial.

### ContaPoupanca

Especialização da ContaBancaria.

Possui taxa de rendimento mensal.

### Transacao

Representa todas as movimentações realizadas nas contas.

---

## Pacote banco.dao

### ConexaoDB

Responsável por estabelecer a conexão com o PostgreSQL.

### UsuarioDAO

Realiza as operações de banco de dados relacionadas aos usuários.

### ClienteDAO

Realiza o cadastro, consulta, atualização e exclusão de clientes.

### ContaCorrenteDAO

Responsável pelas operações das contas correntes.

### ContaPoupancaDAO

Responsável pelas operações das contas poupança.

### TransacaoDAO

Responsável pelo armazenamento e consulta das movimentações bancárias.

---

## Pacote banco.service

### UsuarioService

Implementa as regras de autenticação dos usuários e criptografia da senha.

### BancoService

Centraliza toda a lógica de negócio do sistema, como:

- Cadastro de clientes;
- Cadastro de contas;
- Depósitos;
- Saques;
- Transferências;
- Aplicação de rendimento;
- Consulta de saldo;
- Emissão de extrato;
- Relatórios.

---

## Pacote banco.gui

### TelaLogin

Tela responsável pela autenticação do usuário.

### TelaMenuPrincipal

Tela principal de navegação do sistema.

### TelaCadastroUsuario

Permite cadastrar, alterar, excluir e listar usuários.

### TelaCadastroCliente

Permite cadastrar, alterar, excluir e listar clientes.

### TelaCadastroContaCorrente

Permite cadastrar contas correntes.

### TelaCadastroContaPoupanca

Permite cadastrar contas poupança.

### TelaOperacoes

Realiza depósitos, saques, transferências e aplicação de rendimento.

### TelaExtrato

Exibe o histórico de movimentações da conta.

### TelaRelatorio

Apresenta um relatório contendo todas as contas cadastradas e seus respectivos saldos.

---

## Pacote banco.util

### CriptografiaUtil

Responsável por gerar o hash SHA-256 utilizado para armazenar as senhas dos usuários.

---

# 5. Diagrama Textual da Hierarquia das Classes

```
SistemaBanco
│
├── TelaLogin
│
├── TelaMenuPrincipal
│
├── Cadastro
│   ├── TelaCadastroUsuario
│   ├── TelaCadastroCliente
│   ├── TelaCadastroContaCorrente
│   └── TelaCadastroContaPoupanca
│
├── Operações
│   ├── TelaOperacoes
│   ├── TelaExtrato
│   └── TelaRelatorio
│
├── Service
│   ├── UsuarioService
│   └── BancoService
│
├── DAO
│   ├── UsuarioDAO
│   ├── ClienteDAO
│   ├── ContaCorrenteDAO
│   ├── ContaPoupancaDAO
│   ├── TransacaoDAO
│   └── ConexaoDB
│
└── Model
    ├── Usuario
    ├── Cliente
    ├── ContaBancaria
    │    ├── ContaCorrente
    │    └── ContaPoupanca
    └── Transacao
```

---

# Estrutura dos Pacotes

```
src
│
└── banco
    │
    ├── app
    │     └── SistemaBanco.java
    │
    ├── dao
    │
    ├── model
    │
    ├── service
    │
    ├── gui
    │
    └── util
```

---

# Funcionalidades do Sistema

- Login de usuários
- Cadastro de usuários
- Cadastro de clientes
- Cadastro de contas correntes
- Cadastro de contas poupança
- Depósito
- Saque
- Transferência entre contas
- Aplicação de rendimento
- Consulta de saldo
- Extrato bancário
- Relatório de contas

---

# Autor

Aluno: Leandro otávio de Almeida

Turma: 2 período de Informática
