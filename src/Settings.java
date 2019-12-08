/**
 * Trabalho Final - POO
 * Equipe:
 * 
 * Back-End: Hélio Potelicki & Luis Felipe Zaguini
 * Front-End: Murilo Eger & Pedro Roweder
 *
 */

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class Settings {
	private Configuration config;
	
	public Settings() throws ConfigurationException {
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(params.properties()
		        .setFileName("config.properties"));
		
		builder.setAutoSave(true);
		
		this.config = builder.getConfiguration();
	}
	
	public String getCSVFile() {
		return this.config.getString("csv_file");
	}
	
	public void setCSVFile(String file) {
		this.config.setProperty("csv_file", file);
	}
	
	public String getDBHost() {
		// return this.config.getString("db_host", "localhost:3306");
		return this.config.getString("db_host");
	}
	
	public void setDBHost(String host) {
		this.config.setProperty("db_host", host);
	}
	
	public String getDBName() {
		// return this.config.getString("db_name", "trabalho_final_poo");
		return this.config.getString("db_name");
	}
	
	public void setDBName(String db_name) {
		this.config.setProperty("db_name", db_name);
	}
	
	public String getDBUser() {
		// return this.config.getString("db_user", "root");
		return this.config.getString("db_user");
	}
	
	public void setDBUser(String db_user) {
		this.config.setProperty("db_user", db_user);
	}
	
	public String getDBPassword() {
		// return this.config.getString("db_password", "");
		return this.config.getString("db_password");
	}
	
	public void setDBPassword(String db_password) {
		this.config.setProperty("db_password", db_password);
	}
}
