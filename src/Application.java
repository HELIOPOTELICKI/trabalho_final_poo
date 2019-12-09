/**
 * Trabalho Final - POO
 * Equipe: Hélio Potelicki & Luis Felipe Zaguini 
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.opencsv.exceptions.CsvValidationException;

public class Application {
	private Settings settings;
	private Connection connection;
	private DatabaseWizard dbWizard;
	
	public void loadDatabase(String host, String dbName, String dbUser, String dbPassword) throws SQLException {
		this.connection = DriverManager.getConnection(
				"jdbc:mysql://" + host + "/" + dbName + "?allowMultiQueries=true", 
				dbUser, dbPassword
			);
	}
	
	public void onConnectionGranted(String host, String dbName, String dbUser, String dbPassword) {
		settings.setDBHost(host);
		settings.setDBName(dbName);
		settings.setDBUser(dbUser);
		settings.setDBPassword(dbPassword);
		
		if(dbWizard != null) {
			dbWizard.setVisible(false);
			dbWizard.dispose();
		}
		
		this.openCSVFile();
	}
	
	private void loadFile(String filePath, boolean replaceFile) {
		String fileName = new File(filePath).getName().toString();
		
		try {
			if(replaceFile) {
				Files.copy(
						new File(filePath).toPath(),
						new File(fileName).toPath()
						);
			} else {
				Files.copy(
						new File(filePath).toPath(),
						new File(fileName).toPath(),
						StandardCopyOption.REPLACE_EXISTING
				);
			}
		} catch(FileAlreadyExistsException e) {
			int shouldReplace = JOptionPane.showConfirmDialog(null, "O arquivo já existe. Deseja substituí-lo?", "Conflito", JOptionPane.QUESTION_MESSAGE);
			
			if(shouldReplace == JOptionPane.OK_OPTION) {
				this.loadFile(filePath, true);
			} else {
				JOptionPane.showMessageDialog(null, "Arquivo já existente. Abordando!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido. Tente novamente!", "Erro desconhecido", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
	}
	
	private void openCSVFile() {
		String csvFile = settings.getCSVFile();
		
		if(csvFile == null) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Selecione o arquivo CSV para importação");
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			
			fileChooser.setFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() {
					return "Arquivos CSV (*.csv)";
				}
				
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
			   }
			});
			
			int result = fileChooser.showOpenDialog(null);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				
				this.loadFile(filePath, false);
				settings.setCSVFile(new File(filePath).getName().toString());
			} else {
				return;
			}
		}
		
		LocalCSVReader reader = null;
		
		try {
			reader = new LocalCSVReader(settings.getCSVFile());
		} catch (CsvValidationException e) {
			JOptionPane.showMessageDialog(null, "Falha ao carregar arquivo CSV. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
			settings.setCSVFile(null);
			e.printStackTrace();
			this.openCSVFile();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro desconhecido. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			this.openCSVFile();
		}
		
		EditPage editPage = new EditPage()
			.withSettings(settings)
			.withCSVReader(reader)
			.withConnection(connection);
		
		editPage.display();

	}
	
	public Application(Settings settings) {
		this.settings = settings;
		
		String host = settings.getDBHost();
		String dbName = settings.getDBName();
		String dbUser = settings.getDBUser();
		String dbPassword = settings.getDBPassword();
		
		try {
			this.loadDatabase(host, dbName, dbUser, dbPassword);
		} catch(Exception e) {
			if(host != null && dbName != null && dbUser != null && dbPassword != null) {
				JOptionPane.showMessageDialog(null, "Falha ao conectar no banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			dbWizard = new DatabaseWizard(this, host, dbName, dbUser, dbPassword);
		}
		
		if(this.connection != null) {
			this.onConnectionGranted(host, dbName, dbUser, dbPassword);
		}
	}
}
