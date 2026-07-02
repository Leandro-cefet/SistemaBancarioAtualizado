package banco.model;

public class ContaCorrente extends ContaBancaria {

    private double limiteChequeEspecial;
    private double limiteDisponivel;

    public ContaCorrente() {

    }

    public ContaCorrente(Long id,
                         String numeroConta,
                         Cliente titular,
                         double saldo,
                         double limiteChequeEspecial) {

        super(id, numeroConta, titular, saldo);

        this.limiteChequeEspecial = limiteChequeEspecial;
        this.limiteDisponivel = limiteChequeEspecial;
    }

    @Override
    public boolean sacar(double valor) {

        if (valor <= 0) {

            return false;
        }

        if (valor <= getSaldo()) {

            setSaldo(getSaldo() - valor);

            return true;
        }

        double restante = valor - getSaldo();

        if (restante > limiteDisponivel) {

            return false;
        }

        limiteDisponivel -= restante;

        setSaldo(0);

        return true;
    }

    @Override
    public void depositar(double valor) {

        if (valor <= 0) {

            return;
        }

        double utilizado = limiteChequeEspecial - limiteDisponivel;

        if (utilizado > 0) {

            if (valor >= utilizado) {

                limiteDisponivel = limiteChequeEspecial;

                valor -= utilizado;

            } else {

                limiteDisponivel += valor;

                valor = 0;
            }

        }

        setSaldo(getSaldo() + valor);

    }

    @Override
    public String gerarExtrato() {

        String texto = "";

        texto += "CONTA CORRENTE\n";

        texto += "Titular: " + getTitular().getNome() + "\n";

        texto += "Conta: " + getNumeroConta() + "\n";

        texto += "Saldo: R$ " + String.format("%.2f", getSaldo()) + "\n";

        texto += "Cheque Especial: R$ " + String.format("%.2f", limiteDisponivel);

        return texto;

    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {

        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public double getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(double limiteDisponivel) {

        this.limiteDisponivel = limiteDisponivel;
    }

}