package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarttransfer.model.Account;
import com.smarttransfer.repository.AccountDAO;
import com.smarttransfer.util.HibernateUtil;
import com.smarttransfer.util.PropertiesLoader;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.Session;

import java.io.IOException;

/**
 * Created by jonathasalves on 30/01/2019.
 *  #############################
 *
 * For the sake of simplicity and time saving, I'll use the system classes themselves to fill the database for the tests.
 *
 * ##############################
 */
public class TestUtil {

    protected static PropertiesLoader propertiesLoader;
    protected static String base_adress;

    public TestUtil() throws IOException {
        propertiesLoader = new PropertiesLoader();
        base_adress = String.format("%s:%s", propertiesLoader.loadProperty("base_url"),
                propertiesLoader.loadProperty("base_port"));
    }


    public Account createAccount(double balance) throws IOException {
        Account account = new Account();

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/account/%f", base_adress, balance));

            CloseableHttpResponse response = client.execute(httpPost);

            JsonNode node = getJsonNodeForAccountResponse(response);

            account.setId(node.get("id").longValue());
            account.setBalance(node.get("balance").doubleValue());
        }
        return account;
    }

    public Account getAccount(long accountId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            AccountDAO accountDAO = new AccountDAO();
            return accountDAO.load(session, accountId);
        }
    }

    public void deleteAccount(long accountId) {
        try(Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            AccountDAO accountDAO = new AccountDAO();
            accountDAO.delete(session, accountId);
        }
    }

    public JsonNode getJsonNodeForAccountResponse(HttpResponse httpResponse) throws IOException {
        String json = IOUtils.toString(httpResponse.getEntity().getContent());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(json);
    }


}
