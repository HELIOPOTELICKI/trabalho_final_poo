import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

	public static void main(String[] args)
			throws IOException, CsvValidationException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, SQLException {
		TrabalhoPOO app = new TrabalhoPOO();

		try {
			app.settings = new Settings();
		} catch (ConfigurationException e) {
			System.out.println("Falha ao iniciar configuracao");
			System.out.println(e);
			return;
		}

		String csvFile = app.settings.getCSVFile();

		if (csvFile == null) {

			// TODO implementar WindowBuilder
			Scanner scanner = new Scanner(System.in);
			System.out.printf("Digite o caminho do arquivo: ");
			String file = scanner.nextLine().trim();
			scanner.close();

			String fileName = new File(file).getName().toString();

			Files.copy(new File(file).toPath(), new File(fileName).toPath());
		
			app.settings.setCSVFile(fileName);
		}

		LocalCSVReader reader = new LocalCSVReader(app.settings.getCSVFile());
		
		// user modifies data...
//		reader.write();
		
		ArrayList<DBRow> items = reader.getItems();
		
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/trabalho_final_poo?allowMultiQueries=true", 
				"root", 
				""    );
		
		DatabaseService databaseService = new DatabaseService(connection, items); 
		
		try {
			databaseService.write();
		} catch (NoContentException e) {
			System.out.println(e.getIndex() + ": Campo " + e.getMessage() + " está vazio.");
		} catch (SQLException e) {
			System.out.println("Deu merda no banco");
		} catch (RecordInsertionException e) {
			System.out.println(e.getIndex() + ": Falha ao inserir registro " + e.getMessage());
		}
	}
}


 // C:\Users\Potelicky\Desktop\BANCO DE DADOS TEÇÁ.csv