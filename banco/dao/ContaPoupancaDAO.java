package banco.dao;

import banco.model.Cliente;
import banco.model.ContaPoupanca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaPoupancaDAO {

    private final Connection conexao;

    public ContaPoupancaDAO() {

        conexao = ConexaoDB.getConexao();

    }

    public boolean salvar(ContaPoupanca conta) {

        String sql = "INSERT INTO contas_poupanca (numero_conta, saldo, taxa_rendimento, cliente_id) VALUES (?, ?, ?, ?)";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, conta.getNumeroConta());
                stmt.setDouble(2, conta.getSaldo());
                stmt.setDouble(3, conta.getTaxaRendimentoMensal());
                stmt.setLong(4, conta.getTitular().getId());
                
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {


            return false;

        }

    }

    public ContaPoupanca buscarPorNumero(String numeroConta) {

        String sql = "SELECT * FROM contas_poupanca WHERE numero_conta = ?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, numeroConta);
                
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    
                    ClienteDAO clienteDAO = new ClienteDAO();
                    
                    Cliente cliente = clienteDAO.buscarPorId(rs.getLong("cliente_id"));
                    
                    ContaPoupanca conta = new ContaPoupanca();
                    
                    conta.setId(rs.getLong("id"));
                    conta.setNumeroConta(rs.getString("numero_conta"));
                    conta.setSaldo(rs.getDouble("saldo"));
                    conta.setTaxaRendimentoMensal(rs.getDouble("taxa_rendimento"));
                    conta.setTitular(cliente);
                    
                    rs.close();
                    stmt.close();
                    
                    return conta;
                    
                }
                
                rs.close();
            }

        } catch (SQLException e) {
        }

        return null;

    }

    public List<ContaPoupanca> listarTodas() {

        List<ContaPoupanca> lista = new ArrayList<>();

        String sql = "SELECT * FROM contas_poupanca ORDER BY numero_conta";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                
                ClienteDAO clienteDAO = new ClienteDAO();
                
                while (rs.next()) {
                    
                    Cliente cliente = clienteDAO.buscarPorId(rs.getLong("cliente_id"));
                    
                    ContaPoupanca conta = new ContaPoupanca();
                    
                    conta.setId(rs.getLong("id"));
                    conta.setNumeroConta(rs.getString("numero_conta"));
                    conta.setSaldo(rs.getDouble("saldo"));
                    conta.setTaxaRendimentoMensal(rs.getDouble("taxa_rendimento"));
                    conta.setTitular(cliente);
                    
                    lista.add(conta);
                    
                }
                
            }

        } catch (SQLException e) {
        }

        return lista;

    }

    public boolean atualizar(ContaPoupanca conta) {

        String sql = "UPDATE contas_poupanca SET numero_conta=?, saldo=?, taxa_rendimento=?, cliente_id=? WHERE id=?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, conta.getNumeroConta());
                stmt.setDouble(2, conta.getSaldo());
                stmt.setDouble(3, conta.getTaxaRendimentoMensal());
                stmt.setLong(4, conta.getTitular().getId());
                stmt.setLong(5, conta.getId());
                
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {


            return false;

        }

    }

    public boolean excluir(Long id) {

        String sql = "DELETE FROM contas_poupanca WHERE id=?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, id);
                
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {


            return false;

        }

    }

}