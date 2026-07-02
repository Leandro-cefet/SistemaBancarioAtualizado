package banco.app;

import banco.gui.TelaLogin;

import javax.swing.SwingUtilities;

public class SistemaBanco {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(TelaLogin::new);

    }

}