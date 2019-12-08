/**
 * Trabalho Final - POO
 * Equipe:
 * 
 * Back-End:  H�lio Potelicki & Luis Felipe Zaguini
 * Front-End: Murilo Eger & Pedro Roweder
 *
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
interface SetInsertQueryValues {
	SQLException apply(PreparedStatement stmt);
}