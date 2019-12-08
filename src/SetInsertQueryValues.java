/**
 * Trabalho Final - POO
 * Equipe:
 * 
 * Back-End:  Hélio Potelicki & Luis Felipe Zaguini
 * Front-End: Murilo Eger & Pedro Roweder
 *
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
interface SetInsertQueryValues {
	SQLException apply(PreparedStatement stmt);
}