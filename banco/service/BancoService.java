package banco.service;

import banco.dao.ClienteDAO;
import banco.dao.ContaCorrenteDAO;
import banco.dao.ContaPoupancaDAO;
import banco.dao.TransacaoDAO;

import banco.model.Cliente;
import banco.model.ContaCorrente;
import banco.model.ContaPoupanca;
import banco.model.Transacao;

import java.time.LocalDateTime;
import java.util.List;

public class BancoService {

    private final ClienteDAO clienteDAO;
    private final ContaCorrenteDAO contaCorrenteDAO;
    private final ContaPoupancaDAO contaPoupancaDAO;
    private final TransacaoDAO transacaoDAO;

    public BancoService() {

        clienteDAO = new ClienteDAO();
        contaCorrenteDAO = new ContaCorrenteDAO();
        contaPoupancaDAO = new ContaPoupancaDAO();
        transacaoDAO = new TransacaoDAO();

    }

    // ============================
    // CLIENTES
    // ============================

    public boolean cadastrarCliente(Cliente cliente) {

        Cliente clienteExistente =
                clienteDAO.buscarPorCpf(cliente.getCpf());

        if (clienteExistente != null) {

            return false;

        }

        return clienteDAO.salvar(cliente);

    }

    public List<Cliente> listarClientes() {

        return clienteDAO.listarTodos();

    }

    // ============================
    // CONTAS
    // ============================

    public boolean cadastrarContaCorrente(ContaCorrente conta) {

        ContaCorrente contaExistente =
                contaCorrenteDAO.buscarPorNumero(conta.getNumeroConta());

        if (contaExistente != null) {

            return false;

        }

        return contaCorrenteDAO.salvar(conta);

    }

    public boolean cadastrarContaPoupanca(ContaPoupanca conta) {

        ContaPoupanca contaExistente =
                contaPoupancaDAO.buscarPorNumero(conta.getNumeroConta());

        if (contaExistente != null) {

            return false;

        }

        return contaPoupancaDAO.salvar(conta);

    }

    // ============================
    // DEPÓSITO
    // ============================

    public boolean depositarContaCorrente(String numeroConta,
                                          double valor) {

        ContaCorrente conta =
                contaCorrenteDAO.buscarPorNumero(numeroConta);

        if (conta == null) {

            return false;

        }

        conta.depositar(valor);

        contaCorrenteDAO.atualizar(conta);

        registrarTransacao(
                conta.getId(),
                "CORRENTE",
                "Depósito",
                valor);

        return true;

    }

    public boolean depositarContaPoupanca(String numeroConta,
                                          double valor) {

        ContaPoupanca conta =
                contaPoupancaDAO.buscarPorNumero(numeroConta);

        if (conta == null) {

            return false;

        }

        conta.depositar(valor);

        contaPoupancaDAO.atualizar(conta);

        registrarTransacao(
                conta.getId(),
                "POUPANCA",
                "Depósito",
                valor);

        return true;

    }

    // ============================
    // SAQUE
    // ============================

    public boolean sacarContaCorrente(String numeroConta,
                                      double valor) {

        ContaCorrente conta =
                contaCorrenteDAO.buscarPorNumero(numeroConta);

        if (conta == null) {

            return false;

        }

        if (!conta.sacar(valor)) {

            return false;

        }

        contaCorrenteDAO.atualizar(conta);

        registrarTransacao(
                conta.getId(),
                "CORRENTE",
                "Saque",
                valor);

        return true;

    }

    public boolean sacarContaPoupanca(String numeroConta,
                                      double valor) {

        ContaPoupanca conta =
                contaPoupancaDAO.buscarPorNumero(numeroConta);

        if (conta == null) {

            return false;

        }

        if (!conta.sacar(valor)) {

            return false;

        }

        contaPoupancaDAO.atualizar(conta);

        registrarTransacao(
                conta.getId(),
                "POUPANCA",
                "Saque",
                valor);

        return true;

    }

    // ============================
    // RENDIMENTO
    // ============================

    public boolean aplicarRendimento(String numeroConta) {

        ContaPoupanca conta =
                contaPoupancaDAO.buscarPorNumero(numeroConta);

        if (conta == null) {

            return false;

        }

        double rendimento = conta.calcularRendimento();

        conta.aplicarRendimento();

        contaPoupancaDAO.atualizar(conta);

        registrarTransacao(
                conta.getId(),
                "POUPANCA",
                "Rendimento",
                rendimento);

        return true;

    }

    // ============================
    // CONSULTAS
    // ============================

    public ContaCorrente buscarContaCorrente(String numeroConta) {

        return contaCorrenteDAO.buscarPorNumero(numeroConta);

    }

    public ContaPoupanca buscarContaPoupanca(String numeroConta) {

        return contaPoupancaDAO.buscarPorNumero(numeroConta);

    }

    // ============================
    // EXTRATO
    // ============================

    public List<Transacao> listarTransacoes(Long contaId,
                                            String tipoConta) {

        return transacaoDAO.listarPorConta(contaId, tipoConta);

    }

    // ============================
    // MÉTODO AUXILIAR
    // ============================

    private void registrarTransacao(Long contaId,
                                    String tipoConta,
                                    String descricao,
                                    double valor) {

        Transacao transacao = new Transacao();

        transacao.setContaId(contaId);
        transacao.setTipoConta(tipoConta);
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setDataHora(LocalDateTime.now());

        transacaoDAO.salvar(transacao);

    }
    
    public boolean transferirCorrenteParaCorrente(String contaOrigem,
                                              String contaDestino,
                                              double valor) {

    ContaCorrente origem =
            contaCorrenteDAO.buscarPorNumero(contaOrigem);

    ContaCorrente destino =
            contaCorrenteDAO.buscarPorNumero(contaDestino);

    if (origem == null || destino == null) {

        return false;

    }

    if (!origem.sacar(valor)) {

        return false;

    }

    destino.depositar(valor);

    contaCorrenteDAO.atualizar(origem);
    contaCorrenteDAO.atualizar(destino);

    registrarTransacao(
            origem.getId(),
            "CORRENTE",
            "Transferência enviada",
            valor);

    registrarTransacao(
            destino.getId(),
            "CORRENTE",
            "Transferência recebida",
            valor);

    return true;

}
    
    public boolean transferirCorrenteParaPoupanca(String contaOrigem,
                                              String contaDestino,
                                              double valor) {

    ContaCorrente origem =
            contaCorrenteDAO.buscarPorNumero(contaOrigem);

    ContaPoupanca destino =
            contaPoupancaDAO.buscarPorNumero(contaDestino);

    if (origem == null || destino == null) {

        return false;

    }

    if (!origem.sacar(valor)) {

        return false;

    }

    destino.depositar(valor);

    contaCorrenteDAO.atualizar(origem);
    contaPoupancaDAO.atualizar(destino);

    registrarTransacao(
            origem.getId(),
            "CORRENTE",
            "Transferência enviada",
            valor);

    registrarTransacao(
            destino.getId(),
            "POUPANCA",
            "Transferência recebida",
            valor);

    return true;

}
    
    public boolean transferirPoupancaParaCorrente(String contaOrigem,
                                              String contaDestino,
                                              double valor) {

    ContaPoupanca origem =
            contaPoupancaDAO.buscarPorNumero(contaOrigem);

    ContaCorrente destino =
            contaCorrenteDAO.buscarPorNumero(contaDestino);

    if (origem == null || destino == null) {

        return false;

    }

    if (!origem.sacar(valor)) {

        return false;

    }

    destino.depositar(valor);

    contaPoupancaDAO.atualizar(origem);
    contaCorrenteDAO.atualizar(destino);

    registrarTransacao(
            origem.getId(),
            "POUPANCA",
            "Transferência enviada",
            valor);

    registrarTransacao(
            destino.getId(),
            "CORRENTE",
            "Transferência recebida",
            valor);

    return true;

}
    
    public boolean transferirPoupancaParaPoupanca(String contaOrigem,
                                              String contaDestino,
                                              double valor) {

    ContaPoupanca origem =
            contaPoupancaDAO.buscarPorNumero(contaOrigem);

    ContaPoupanca destino =
            contaPoupancaDAO.buscarPorNumero(contaDestino);

    if (origem == null || destino == null) {

        return false;

    }

    if (!origem.sacar(valor)) {

        return false;

    }

    destino.depositar(valor);

    contaPoupancaDAO.atualizar(origem);
    contaPoupancaDAO.atualizar(destino);

    registrarTransacao(
            origem.getId(),
            "POUPANCA",
            "Transferência enviada",
            valor);

    registrarTransacao(
            destino.getId(),
            "POUPANCA",
            "Transferência recebida",
            valor);

    return true;

}
    

}