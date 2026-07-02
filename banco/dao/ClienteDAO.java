package banco.dao;

import banco.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection conexao;

    public ClienteDAO() {

        conexao = ConexaoDB.getConexao();

    }

    public boolean salvar(Cliente cliente) {

        String sql = "INSERT INTO clientes (nome, cpf, telefone) VALUES (?, ?, ?)";

        try {

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());

            stmt.executeUpdate();

            stmt.close();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    public Cliente buscarPorCpf(String cpf) {

        String sql = "SELECT * FROM clientes WHERE cpf = ?";

        try {

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));

                rs.close();
                stmt.close();

                return cliente;

            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    public Cliente buscarPorId(Long id) {

        String sql = "SELECT * FROM clientes WHERE id = ?";

        try {

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));

                rs.close();
                stmt.close();

                return cliente;

            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    public List<Cliente> listarTodos() {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM clientes ORDER BY nome";

        try {

            PreparedStatement stmt = conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));

                lista.add(cliente);

            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lista;

    }

    public boolean atualizar(Cliente cliente) {

        String sql = "UPDATE clientes SET nome=?, cpf=?, telefone=? WHERE id=?";

        try {

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setLong(4, cliente.getId());

            stmt.executeUpdate();

            stmt.close();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    public boolean excluir(Long id) {

        String sql = "DELETE FROM clientes WHERE id=?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, id);
                
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

}