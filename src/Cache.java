/**
 * Trabalho Final - POO
 * Equipe:
 * 
 * Back-End:  Hélio Potelicki & Luis Felipe Zaguini
 * Front-End: Murilo Eger & Pedro Roweder
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Cache {
	private Connection connection;
	private String name, selectQuery, insertQuery;
	private HashMap<String, Integer> cache = new HashMap<String, Integer>();
	
	public Cache(Connection connection, String name) {
		this.connection = connection;
		this.name = name;
	}
	
	public Cache withSelectQuery(String selectQuery) {
		this.selectQuery = selectQuery;
		return this;
	}
	
	public Cache withInsertQuery(String insertQuery) {
		this.insertQuery = insertQuery;
		return this;
	}
	
	public Integer get(String key, SetInsertQueryValues setInsertQueryValues) throws SQLException, NoContentException {
		if(key.isEmpty()) {
			throw new NoContentException(0, name);
		}
		
		Integer authorId = this.cache.get(key);
		
		if(authorId != null) {
			return authorId;
		}
		
		PreparedStatement stmt;
		ResultSet result;
		
		stmt = connection.prepareStatement(selectQuery);
		stmt.setString(1, key);
		
		result = stmt.executeQuery();
					
		if(result.next()) {
			authorId = result.getInt(1); 
			cache.put(key, authorId);
		} else {
			stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			
			if(setInsertQueryValues != null) {
				SQLException insertValuesException = setInsertQueryValues.apply(stmt);
				
				if(insertValuesException != null) {
					throw insertValuesException;
				}
			} else {
				stmt.setString(1, key);
			}
			
			stmt.execute();

			result = stmt.getGeneratedKeys();
			
			if(result.next()) {
				authorId = result.getInt(1);
				cache.put(key, authorId);
			}
		}
		
		stmt.close();
		
		return authorId;
	}
}
