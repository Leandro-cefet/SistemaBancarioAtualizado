package banco.dao;

import banco.model.Cliente;
import banco.model.ContaCorrente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaCorrenteDAO {

    private final Connection conexao;

    public ContaCorrenteDAO() {

        conexao = ConexaoDB.getConexao();

    }

    public boolean salvar(ContaCorrente conta) {

        String sql = "INSERT INTO contas_correntes (numero_conta, saldo, limite_cheque, cliente_id) VALUES (?, ?, ?, ?)";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, conta.getNumeroConta());
                stmt.setDouble(2, conta.getSaldo());
                stmt.setDouble(3, conta.getLimiteChequeEspecial());
                stmt.setLong(4, conta.getTitular().getId());
                
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {


            return false;

        }

    }

    public ContaCorrente buscarPorNumero(String numeroConta) {

        String sql = "SELECT * FROM contas_correntes WHERE numero_conta = ?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, numeroConta);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        
                        ClienteDAO clienteDAO = new ClienteDAO();
                        
                        Cliente cliente = clienteDAO.buscarPorId(rs.getLong("cliente_id"));
                        
                        ContaCorrente conta = new ContaCorrente();
                        
                        conta.setId(rs.getLong("id"));
                        conta.setNumeroConta(rs.getString("numero_conta"));
                        conta.setSaldo(rs.getDouble("saldo"));
                        conta.setLimiteChequeEspecial(rs.getDouble("limite_cheque"));
                        conta.setLimiteDisponivel(rs.getDouble("limite_cheque"));
                        conta.setTitular(cliente);
                        
                        rs.close();
                        stmt.close();
                        
                        return conta;
                        
                    }
                }
            }

        } catch (SQLException e) {
        }

        return null;

    }

    public List<ContaCorrente> listarTodas() {

        List<ContaCorrente> lista = new ArrayList<>();

        String sql = "SELECT * FROM contas_correntes ORDER BY numero_conta";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                
                ClienteDAO clienteDAO = new ClienteDAO();
                
                while (rs.next()) {
                    
                    Cliente cliente = clienteDAO.buscarPorId(rs.getLong("cliente_id"));
                    
                    ContaCorrente conta = new ContaCorrente();
                    
                    conta.setId(rs.getLong("id"));
                    conta.setNumeroConta(rs.getString("numero_conta"));
                    conta.setSaldo(rs.getDouble("saldo"));
                    conta.setLimiteChequeEspecial(rs.getDouble("limite_cheque"));
                    conta.setLimiteDisponivel(rs.getDouble("limite_cheque"));
                    conta.setTitular(cliente);
                    
                    lista.add(conta);
                    
                }
                
            }

        } catch (SQLException e) {
        }

        return lista;

    }

    public boolean atualizar(ContaCorrente conta) {

        String sql = "UPDATE contas_correntes SET numero_conta=?, saldo=?, limite_cheque=?, cliente_id=? WHERE id=?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, conta.getNumeroConta());
                stmt.setDouble(2, conta.getSaldo());
                stmt.setDouble(3, conta.getLimiteChequeEspecial());
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

        String sql = "DELETE FROM contas_correntes WHERE id=?";

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