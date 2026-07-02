package banco.service;

import banco.dao.UsuarioDAO;
import banco.model.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {

        usuarioDAO = new UsuarioDAO();

    }

    public boolean salvar(Usuario usuario) {

        Usuario usuarioExistente =
                usuarioDAO.buscarPorLogin(usuario.getLogin());

        if (usuarioExistente != null) {

            return false;

        }

        usuario.setSenha(gerarHash(usuario.getSenha()));

        return usuarioDAO.salvar(usuario);

    }

    public Usuario autenticar(String login, String senha) {

        Usuario usuario =
                usuarioDAO.buscarPorLogin(login);

        if (usuario == null) {

            return null;

        }

        String senhaHash = gerarHash(senha);

        if (usuario.getSenha().equals(senhaHash)) {

            return usuario;

        }

        return null;

    }

    public boolean atualizar(Usuario usuario) {

        usuario.setSenha(gerarHash(usuario.getSenha()));

        return usuarioDAO.atualizar(usuario);

    }

    public boolean excluir(Long id) {

        List<Usuario> usuarios =
                usuarioDAO.listarTodos();

        for (Usuario usuario : usuarios) {

            if (usuario.getId().equals(id)
                    && usuario.getPerfil().equalsIgnoreCase("ADMIN")) {

                return false;

            }

        }

        return usuarioDAO.excluir(id);

    }

    public List<Usuario> listarTodos() {

        return usuarioDAO.listarTodos();

    }

    private String gerarHash(String senha) {

        try {

            MessageDigest md =
                    MessageDigest.getInstance("SHA-256");

            byte[] bytes =
                    md.digest(senha.getBytes(StandardCharsets.UTF_8));

            StringBuilder hash = new StringBuilder();

            for (byte b : bytes) {

                hash.append(String.format("%02x", b));

            }

            return hash.toString();

        }

        catch (NoSuchAlgorithmException e) {
        }

        return null;

    }

}