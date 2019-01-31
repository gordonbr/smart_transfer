package com.smarttransfer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jonathasalves on 30/01/2019.
 */
public class PropertiesLoader {

    final private String FILE = "config.properties";
    private Properties properties;
    private InputStream inputStream;

    public PropertiesLoader() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = loader.getResourceAsStream(FILE)){
            this.properties = new Properties();
            this.properties.load(inputStream);
        }
    }

    public String loadProperty(String property) {
        return this.properties.getProperty(property);
    }


}
