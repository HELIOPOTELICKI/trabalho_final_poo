/**
 * Trabalho Final - POO
 * Equipe:
 * 
 * Back-End: Hélio Potelicki & Luis Felipe Zaguini
 * Front-End: Murilo Eger & Pedro Roweder
 *
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.configuration2.ex.ConfigurationException;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

public class TrabalhoPOO {
	private Settings settings;

	public static void main(String[] args) {
		// Front-end TODO: Remove those return statements
		// and replace them with gracefully retry operations
		// Dev-tip: isolate them in different functions inside this class
		// and call them. Examples:
		// app.loadSettings();
		// app.loadTemporaryFile();
		// app.saveTemporaryFile();
		// app.exportToDatabase();
		// app.displayMessage("Exported successfully to database");
		
		TrabalhoPOO app = new TrabalhoPOO();

		try {
			app.settings = new Settings();
		} catch (ConfigurationException e) {
			// Front-end TODO: Let the user try again
			System.out.println("Falha ao iniciar configuracao");
			System.out.println(e);
			return;
		}

		String csvFile = app.settings.getCSVFile();

		if (csvFile == null) {
			// Front-end TODO: Show file picker 
			Scanner scanner = new Scanner(System.in);
			System.out.printf("Digite o caminho do arquivo: ");
			String file = scanner.nextLine().trim();
			scanner.close();

			String fileName = new File(file).getName().toString();

			try {
				// Front-end TODO: Add a CopyOption here if the user wants to replace what already exists
				Files.copy(new File(file).toPath(), new File(fileName).toPath());
			} catch(FileAlreadyExistsException e) {
				// Front-end TODO: Display an option to skip the import or retry, replacing what they have
				e.printStackTrace();
				return;
			} catch(NoSuchFileException e) {
				// Front-end TODO: Display the file picker again, allowing the user to select a file
				e.printStackTrace();
				return;
			} catch (IOException e) {
				// Front-end TODO: Display error message and let the user try again
				e.printStackTrace();
				return;
			}
		
			app.settings.setCSVFile(fileName);
		}

		LocalCSVReader reader;
		
		try {
			reader = new LocalCSVReader(app.settings.getCSVFile());
		} catch (CsvValidationException e) {
			// Front-end TODO: Display error message and let the user try again
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// Front-end TODO: Display error message and let the user try again
			e.printStackTrace();
			return;
		}
		
		// Front-end TODO: Show table that allows users to modify data
		// After that, write to temporary document using reader.write();
		try {
			reader.write();
		} catch (CsvDataTypeMismatchException e) {
			// Front-end TODO: Display error message and let the user try again
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			// Front-end TODO: Display error message and let the user try again
			e.printStackTrace();
		} catch (IOException e) {
			// Front-end TODO: Display error message and let the user try again
			e.printStackTrace();
		}
		
		ArrayList<DBRow> items = reader.getItems();
		
		// Front-end TODO: Run the lines below only when the user clicks the "Migrate" button
		Connection connection;
		try {
			connection = DriverManager.getConnection(
				"jdbc:mysql://" + app.settings.getDBHost() + "/" + app.settings.getDBName() + "?allowMultiQueries=true", 
				app.settings.getDBUser(), 
				app.settings.getDBPassword()
			);
		} catch (SQLException e) {
			// Front-end TODO: Display error message and let the user modify the database credentials
			// before trying again if they want
			e.printStackTrace();
			return;
		}
		
		DatabaseService databaseService = new DatabaseService(connection, items); 
		
		try {
			databaseService.write();
			
			// Front-end TODO: Display the success alert and move the user to the start screen so that
			// they might choose a different file to import
			System.out.println("Operação finalizada com sucesso!");
		} catch (NoContentException e) {
			// Front-end TODO: Set the below value as the active row on the table saying what went wrong
			System.out.println(e.getIndex() + ": Campo " + e.getMessage() + " está vazio.");
		} catch (SQLException e) {
			// Front-end TODO: Display an alert saying that the query failed to execute
			System.out.println("Falha na operação: " + e.getMessage());
		} catch (RecordInsertionException e) {
			// Front-end TODO: Set the below value as the active row on the table saying what went wrong
			System.out.println(e.getIndex() + ": Falha ao inserir registro " + e.getMessage());
		}
	}
}


 // C:\Users\Potelicky\Desktop\BANCO DE DADOS TEÇÁ.csv