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
		return this.config.getString("csvfile");
	}
	
	public void setCSVFile(String file) {
		this.config.setProperty("csvfile", file);
	}
}
