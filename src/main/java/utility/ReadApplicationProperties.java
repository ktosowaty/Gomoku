package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadApplicationProperties {

    public static Properties appProperties;

    private static final Logger LOGGER = Logger.getLogger(ReadApplicationProperties.class.getName());

    public void initProperties() {
        appProperties = new Properties();
        InputStream input = null;
        String propertiesName = "src/main/resources/appProperties.properties";
        try {
            input = new FileInputStream(propertiesName);
            appProperties.load(input);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } finally {
            try {
                if(input != null) input.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }
}
