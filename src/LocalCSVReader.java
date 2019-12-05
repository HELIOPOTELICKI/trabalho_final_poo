import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

public class LocalCSVReader {
	private String fileName;
	private ArrayList<DBRow> items = new ArrayList<DBRow>();

	public ArrayList<DBRow> getItems() {
		return items;
	}

	public LocalCSVReader(String fileName) throws IOException, CsvValidationException {
		this.fileName = fileName;
		InputStreamReader file = new InputStreamReader(new FileInputStream(fileName), "UTF8");
		CsvToBean<DBRow> csvToBean = new CsvToBeanBuilder<DBRow>(file)
				.withSeparator(';')
				.withType(DBRow.class).withIgnoreLeadingWhiteSpace(true).withSkipLines(1).build();

		Iterator<DBRow> csvDbRowIterator = csvToBean.iterator();

		while (csvDbRowIterator.hasNext()) {
			items.add(csvDbRowIterator.next());
		}
	}

	public void write() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		Writer writer = Files.newBufferedWriter(Paths.get(this.fileName), StandardOpenOption.TRUNCATE_EXISTING);
		DBRowMappingStrategy mappingStrategy = new DBRowMappingStrategy();
		mappingStrategy.setColumnMapping(DBRowMappingStrategy.HEADER);
		mappingStrategy.setType(DBRow.class);

		StatefulBeanToCsv<DBRow> beanToCsv = new StatefulBeanToCsvBuilder<DBRow>(writer)
				.withMappingStrategy(mappingStrategy).withApplyQuotesToAll(false).withSeparator(';').build();

		beanToCsv.write(items);

		writer.close();
	}
}
