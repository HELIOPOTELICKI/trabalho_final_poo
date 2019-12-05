import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class DBRowMappingStrategy extends ColumnPositionMappingStrategy<DBRow> {
    static final String[] HEADER = new String[]{
    	"Tipo de material",
    	"Meio de divulga��o",
    	"Entidade",
    	"Tipo de organiza��o",
    	"Autor",
    	"T�tulo",
    	"Palavras-chave ou descritores",
    	"Ano da produ��o",
    	"Ano da publica��o",
    	"Edi��o",
    	"Local de publica��o",
    	"Editora",
    	"N�mero de p�ginas/minutos",
    	"Dispon�vel em",
    	"ISBN",
    	"ISSN"
    };
    
    @Override
    public String[] generateHeader(DBRow bean) throws CsvRequiredFieldEmptyException {
    	// TODO Auto-generated method stub
    	return HEADER;
	}

	public static String[] getHeader() {
		return HEADER;
	}
}