package banco.model;

import java.time.LocalDateTime;

public class Transacao {

    private Long id;
    private Long contaId;
    private String tipoConta;
    private String descricao;
    private double valor;
    private LocalDateTime dataHora;

    public Transacao() {

    }

    public Transacao(Long id,
                     Long contaId,
                     String tipoConta,
                     String descricao,
                     double valor,
                     LocalDateTime dataHora) {

        this.id = id;
        this.contaId = contaId;
        this.tipoConta = tipoConta;
        this.descricao = descricao;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {

        this.contaId = contaId;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {

        this.tipoConta = tipoConta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {

        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {

        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {

        this.dataHora = dataHora;
    }

}