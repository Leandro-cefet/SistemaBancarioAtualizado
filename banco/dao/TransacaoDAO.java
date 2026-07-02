package banco.dao;

import banco.model.Transacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    private final Connection conexao;

    public TransacaoDAO() {

        conexao = ConexaoDB.getConexao();

    }

    public boolean salvar(Transacao transacao) {

        String sql = "INSERT INTO transacoes (conta_id, tipo_conta, descricao, valor, data_hora) VALUES (?, ?, ?, ?, ?)";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, transacao.getContaId());
                stmt.setString(2, transacao.getTipoConta());
                stmt.setString(3, transacao.getDescricao());
                stmt.setDouble(4, transacao.getValor());
                stmt.setTimestamp(5, Timestamp.valueOf(transacao.getDataHora()));
                
                stmt.executeUpdate();
            }

            return true;

        }

        catch (SQLException e) {


            return false;

        }

    }

    public List<Transacao> listarPorConta(Long contaId, String tipoConta) {

        List<Transacao> lista = new ArrayList<>();

        String sql = "SELECT * FROM transacoes WHERE conta_id = ? AND tipo_conta = ? ORDER BY data_hora";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, contaId);
                
                stmt.setString(2, tipoConta);
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    
                    Transacao transacao = new Transacao();
                    
                    transacao.setId(rs.getLong("id"));
                    transacao.setContaId(rs.getLong("conta_id"));
                    transacao.setTipoConta(rs.getString("tipo_conta"));
                    transacao.setDescricao(rs.getString("descricao"));
                    transacao.setValor(rs.getDouble("valor"));
                    transacao.setDataHora(
                            rs.getTimestamp("data_hora").toLocalDateTime());
                    
                    lista.add(transacao);
                    
                }
                
                rs.close();
            }

        }

        catch (SQLException e) {
        }

        return lista;

    }

}