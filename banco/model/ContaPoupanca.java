package banco.model;

public class ContaPoupanca extends ContaBancaria {

    private double taxaRendimentoMensal;

    public ContaPoupanca() {

    }

    public ContaPoupanca(Long id,
                         String numeroConta,
                         Cliente titular,
                         double saldo,
                         double taxaRendimentoMensal) {

        super(id, numeroConta, titular, saldo);

        this.taxaRendimentoMensal = taxaRendimentoMensal;
    }

    public double calcularRendimento() {

        return getSaldo() * (taxaRendimentoMensal / 100);

    }

    public void aplicarRendimento() {

        double rendimento = calcularRendimento();

        setSaldo(getSaldo() + rendimento);

    }

    @Override
    public String gerarExtrato() {

        String texto = "";

        texto += "CONTA POUPANÇA\n";

        texto += "Titular: " + getTitular().getNome() + "\n";

        texto += "Conta: " + getNumeroConta() + "\n";

        texto += "Saldo: R$ " + String.format("%.2f", getSaldo()) + "\n";

        texto += "Taxa: " + taxaRendimentoMensal + "%";

        return texto;

    }

    public double getTaxaRendimentoMensal() {
        return taxaRendimentoMensal;
    }

    public void setTaxaRendimentoMensal(double taxaRendimentoMensal) {

        this.taxaRendimentoMensal = taxaRendimentoMensal;
    }

}