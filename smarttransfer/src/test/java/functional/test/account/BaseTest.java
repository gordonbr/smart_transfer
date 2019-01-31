package functional.test.account;

import com.smarttransfer.util.PropertiesLoader;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;

/**
 * Created by jonathasalves on 30/01/2019.
 */
public class BaseTest {

    protected static PropertiesLoader propertiesLoader;
    protected static String base_adress;

    @BeforeClass
    public static void setUpBase() throws IOException {
        propertiesLoader = new PropertiesLoader();
        base_adress = String.format("%s:%s", propertiesLoader.loadProperty("base_url"),
                propertiesLoader.loadProperty("base_port"));
    }

    @AfterClass
    public static void tearDownClass() {

    }
}
