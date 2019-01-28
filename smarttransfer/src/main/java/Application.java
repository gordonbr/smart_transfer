import com.smarttransfer.controller.AccountController;
import com.smarttransfer.controller.TransferController;
import io.javalin.Javalin;

/**
 * Created by jonathasalves on 26/01/2019.
 */
public class Application {

    public static void main(String args[]) {

        Javalin app = Javalin.create()
                .enableCorsForAllOrigins()
                .defaultContentType("application/json")
                .start(7071);


        app.get("/account/:id", AccountController.getAccount);
        app.post("/account/:balance", AccountController.saveAccount);
        app.post("/transfer", AccountController.transferMoney);
        app.get("/transfer/by_account_source/:id", TransferController.getByAccountSource);
    }
}
