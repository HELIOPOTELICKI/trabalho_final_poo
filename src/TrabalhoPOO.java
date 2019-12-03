import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import org.apache.commons.configuration2.ex.ConfigurationException;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

public class TrabalhoPOO {
	private Settings settings;

	public static void main(String[] args) throws IOException, CsvValidationException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		TrabalhoPOO app = new TrabalhoPOO();
		
		try {
			app.settings = new Settings();
		} catch (ConfigurationException e) {
			System.out.println("Falha ao iniciar configuracao");
			return;
		}
		
		String csvFile = app.settings.getCSVFile();
		
		if(csvFile == null) {
			// TODO: implementar WindowBuilder
			Scanner scanner = new Scanner(System.in);
			System.out.printf("Digite o caminho do arquivo: ");
			String file = scanner.nextLine();
			scanner.close();
			
			String fileName = new File(file).getName().toString();
			
			Files.copy(new File(file).toPath(), new File(fileName).toPath());
			
			app.settings.setCSVFile(fileName);
		}

		LocalCSVReader reader = new LocalCSVReader(app.settings.getCSVFile());
				
		reader.write();
	}
}
