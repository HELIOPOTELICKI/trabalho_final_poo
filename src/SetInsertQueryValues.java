import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
interface SetInsertQueryValues {
	SQLException apply(PreparedStatement stmt);
}