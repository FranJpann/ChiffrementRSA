package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    Properties props = new Properties();

    public ConfigProperties() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfigValue(String configKey){
        return props.getProperty(configKey);
    }
}
