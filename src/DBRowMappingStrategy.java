/**
 * Trabalho Final - POO
 * Equipe:
 * 
 * Back-End:  Hélio Potelicki & Luis Felipe Zaguini
 * Front-End: Murilo Eger & Pedro Roweder
 *
 */

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class DBRowMappingStrategy extends ColumnPositionMappingStrategy<DBRow> {
    static final String[] HEADER = new String[]{
    	"Tipo de material",
    	"Meio de divulgação",
    	"Entidade",
    	"Tipo de organização",
    	"Autor",
    	"Título",
    	"Palavras-chave ou descritores",
    	"Ano da produção",
    	"Ano da publicação",
    	"Edição",
    	"Local de publicação",
    	"Editora",
    	"Número de páginas/minutos",
    	"Disponível em",
    	"ISBN",
    	"ISSN"
    };
    
    @Override
    public String[] generateHeader(DBRow bean) throws CsvRequiredFieldEmptyException {
    	return HEADER;
	}

	public static String[] getHeader() {
		return HEADER;
	}
}