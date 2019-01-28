import com.smarttransfer.controller.AccountController;
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


        app.get("/account/:id", AccountController.saveAccount);
        app.post("/account/:balance", AccountController.getAccount);
        app.post("/transfer", AccountController.transferMoney);
    }

}
