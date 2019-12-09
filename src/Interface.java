/**
 * Trabalho Final - POO
 * Equipe: Hélio Potelicki & Luis Felipe Zaguini 
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import org.apache.commons.configuration2.ex.ConfigurationException;
import javax.swing.JOptionPane;

public class Interface extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Settings settings;
				
				try {
					settings = new Settings();
				} catch(ConfigurationException e) {
					JOptionPane.showMessageDialog(null, "Falha ao carregar configurações", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				new Application(settings);
			}
		});
	}
}
