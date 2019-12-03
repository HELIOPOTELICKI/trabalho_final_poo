import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class DBRowMappingStrategy extends ColumnPositionMappingStrategy<DBRow> {
    static final String[] HEADER = new String[]{
    	"Tipo de material",
    	"Meio de divulgação"
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