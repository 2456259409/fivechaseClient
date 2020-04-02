package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class PropertiesUtil {
    public static String getProperties(String name){
        Properties properties = new Properties();
        File file = new File("src/propertise/application.propertise");
         try {
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
          return properties.getProperty(name);
        }
}
