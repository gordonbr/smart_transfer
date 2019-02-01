import com.smarttransfer.controller.AccountController;
import com.smarttransfer.controller.TransferController;
import com.smarttransfer.util.PropertiesLoader;
import io.javalin.Javalin;

import java.io.IOException;

/**
 * Created by jonathasalves on 26/01/2019.
 */
public class Application {

    public static void main(String args[]) throws IOException {

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        int base_port = Integer.parseInt(propertiesLoader.loadProperty("base_port"));
        String default_content_type = propertiesLoader.loadProperty("default_content_type");

        Javalin app = Javalin.create()
                .enableCorsForAllOrigins()
                .defaultContentType(default_content_type)
                .start(base_port);


        app.get("/account/:id", AccountController.getAccount);
        app.post("/account/:balance", AccountController.saveAccount);
        app.post("/transfer", AccountController.transferMoney);
        app.get("/transfer/by_account_source/:id", TransferController.getByAccountSource);
    }
}
