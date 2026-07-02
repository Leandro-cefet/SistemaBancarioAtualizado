package banco.model;

import banco.interfaces.Operavel;

public abstract class ContaBancaria implements Operavel {

    private Long id;
    private String numeroConta;
    private Cliente titular;
    private double saldo;

    public ContaBancaria() {

    }

    public ContaBancaria(Long id,
                         String numeroConta,
                         Cliente titular,
                         double saldo) {

        this.id = id;
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = saldo;
    }

    @Override
    public void depositar(double valor) {

        if (valor > 0) {

            saldo += valor;
        }

    }

    @Override
    public boolean sacar(double valor) {

        if (valor <= 0) {

            return false;
        }

        if (valor > saldo) {

            return false;
        }

        saldo -= valor;

        return true;
    }

    @Override
    public double consultarSaldo() {

        return saldo;
    }

    public abstract String gerarExtrato();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {

        this.numeroConta = numeroConta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {

        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {

        this.saldo = saldo;
    }

}