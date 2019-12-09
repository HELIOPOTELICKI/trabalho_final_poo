/**
 * Trabalho Final - POO
 * Equipe: Hélio Potelicki & Luis Felipe Zaguini 
 */

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import javax.swing.JTable;
import javax.swing.JButton;

public class EditPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private Settings settings;
	private LocalCSVReader reader;
	private Connection connection;
	private JTable table;
	
	public EditPage withSettings(Settings settings) {
		this.settings = settings;
		return this;
	}
	
	public EditPage withCSVReader(LocalCSVReader reader) {
		this.reader = reader;
		return this;
	}
	
	public EditPage withConnection(Connection connection) {
		this.connection = connection;
		return this;
	}
	
	private void addLines(DefaultTableModel model) {
		ArrayList<DBRow> items = reader.getItems();
		
		for(DBRow item: items) {
			String[] row = {
					item.getTipoMaterial(),
					item.getMeioDivulgacao(),
					item.getEntidade(),
					item.getTipoOrganizacao(),
					item.getAutor(),
					item.getTitulo(),
					item.getPalavrasChaveDescritores(),
					item.getAnoProducao(),
					item.getAnoPublicacao(),
					item.getEdicao(),
					item.getLocalPublicacao(),
					item.getEditora(),
					item.getPaginasMinutos(),
					item.getDisponivelEm(),
					item.getISBN(),
					item.getISSN()
			};
			
			model.addRow(row);
		}
	}
	
	
	private void writeChanges(boolean displayMessage) throws Exception {
		try {
			reader.write();
			
			if(displayMessage) {
				JOptionPane.showMessageDialog(null, "Rascunho salvo.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
			JOptionPane.showMessageDialog(null, "Falha ao gravar dados. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			throw e;
		}
	}
	
	private void importChanges() {
		DatabaseService databaseService = new DatabaseService(connection, reader.getItems()); 
		
		try {
			databaseService.write();
			JOptionPane.showMessageDialog(null, "Importação realizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			settings.clear();
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} catch (NoContentException e) {
			table.setRowSelectionInterval(e.getIndex(), e.getIndex());
			JOptionPane.showMessageDialog(null, "Campo " + e.getMessage() + " está vazio", "Erro", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Falha na importação. Tente novamente! Erro:" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (RecordInsertionException e) {
			table.setRowSelectionInterval(e.getIndex(), e.getIndex());
			JOptionPane.showMessageDialog(null, "Falha na importação. Tente novamente! Erro:" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public void display() {
		setTitle("Normalização de dados");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 994, 610);
		getContentPane().setLayout(null);
		
		DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tipo de material"); //0
        model.addColumn("Meio de divulgação"); //1
        model.addColumn("Entidade");			//2
        model.addColumn("Tipo de organização");//3
        model.addColumn("Autor");	//4
        model.addColumn("Título");//5
        model.addColumn("Palavras-chave ou descritores"); //6
        model.addColumn("Ano da produção");//7
        model.addColumn("Ano da publicação");//8
        model.addColumn("Edição");//9
        model.addColumn("Local de publicação"); //10
        model.addColumn("Editora"); //11
        model.addColumn("Num de pg/min");//12
        model.addColumn("Disponível em");//13
        model.addColumn("ISBN");//14
        model.addColumn("ISSN");//15

        table = new Tabela(model, reader.getItems());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(125);
        table.getColumnModel().getColumn(1).setPreferredWidth(125);
        table.getColumnModel().getColumn(2).setPreferredWidth(75);
        table.getColumnModel().getColumn(3).setPreferredWidth(125);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(300);
        table.getColumnModel().getColumn(6).setPreferredWidth(300);
        table.getColumnModel().getColumn(7).setPreferredWidth(125);
        table.getColumnModel().getColumn(8).setPreferredWidth(125);
        table.getColumnModel().getColumn(9).setPreferredWidth(100);
        table.getColumnModel().getColumn(10).setPreferredWidth(150);
        table.getColumnModel().getColumn(11).setPreferredWidth(150);
        table.getColumnModel().getColumn(12).setPreferredWidth(150);
        table.getColumnModel().getColumn(13).setPreferredWidth(300);
        table.getColumnModel().getColumn(14).setPreferredWidth(125);
        table.getColumnModel().getColumn(15).setPreferredWidth(125);
		table.setBounds(0, 0, 978, 515);
		
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(0, 0, 978, 515);
		getContentPane().add(sp);
		
		this.addLines(model);
		
		JButton btnSalvarRascunho = new JButton("Salvar Rascunho");
		btnSalvarRascunho.setBounds(10, 526, 165, 34);
		getContentPane().add(btnSalvarRascunho);
		
		EditPage editPage = this;
		
		btnSalvarRascunho.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					editPage.writeChanges(true);
				} catch(Exception e) {}
			}
		});
		
		JButton btnImportar = new JButton("Importar");
		btnImportar.setBounds(803, 526, 165, 34);
		getContentPane().add(btnImportar);
		
		btnImportar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					editPage.writeChanges(false);
					editPage.importChanges();
				} catch(Exception e) {}
			}
		});
		
		setVisible(true);
	}
}
