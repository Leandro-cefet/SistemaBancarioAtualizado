package banco.dao;

import banco.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final Connection conexao;

    public UsuarioDAO() {

        conexao = ConexaoDB.getConexao();

    }

    public boolean salvar(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nome, login, senha, perfil) VALUES (?, ?, ?, ?)";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getLogin());
                stmt.setString(3, usuario.getSenha());
                stmt.setString(4, usuario.getPerfil());
                
                stmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {


            return false;

        }

    }

    public Usuario buscarPorLogin(String login) {

        String sql = "SELECT * FROM usuarios WHERE login = ?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, login);
                
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    
                    Usuario usuario = new Usuario();
                    
                    usuario.setId(rs.getLong("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    
                    rs.close();
                    stmt.close();
                    
                    return usuario;
                    
                }
                
                rs.close();
            }

        }

        catch (SQLException e) {
        }

        return null;

    }

    public List<Usuario> listarTodos() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios ORDER BY nome";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    
                    Usuario usuario = new Usuario();
                    
                    usuario.setId(rs.getLong("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    
                    lista.add(usuario);
                    
                }
                
            }

        }

        catch (SQLException e) {
        }

        return lista;

    }

    public boolean atualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET nome=?, login=?, senha=?, perfil=? WHERE id=?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getLogin());
                stmt.setString(3, usuario.getSenha());
                stmt.setString(4, usuario.getPerfil());
                stmt.setLong(5, usuario.getId());
                
                stmt.executeUpdate();
            }

            return true;

        }

        catch (SQLException e) {


            return false;

        }

    }

    public boolean excluir(Long id) {

        String sql = "DELETE FROM usuarios WHERE id=?";

        try {

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, id);
                
                stmt.executeUpdate();
            }

            return true;

        }

        catch (SQLException e) {


            return false;

        }

    }

}