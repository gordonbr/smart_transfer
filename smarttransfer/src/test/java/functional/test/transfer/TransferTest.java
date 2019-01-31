package functional.test.transfer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smarttransfer.model.Account;
import com.smarttransfer.util.EMessages;
import functional.test.account.BaseTest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestUtil;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by jonathasalves on 30/01/2019.
 */
public class TransferTest extends BaseTest {

    private String jsonMimeType = "application/json";
    private Account account_source;
    private Account account_target;
    private static TestUtil util;
    private double balance = 1000.50f;

    @BeforeClass
    public static void setUp() throws IOException {
        util = new TestUtil();

    }

    @Before
    public void before() throws IOException {
        account_source = util.createAccount(balance);
        account_target = util.createAccount(balance);
    }

    @Test
    public void transferOkTest() throws IOException {

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/transfer", base_adress));
            double value = 10.20f;

            ObjectNode transferNode = util.createTransferJson(account_source.getId(), account_target.getId(), value);
            StringEntity entity = new StringEntity(transferNode.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            Account accSource = util.getAccount(account_source.getId());
            Account accTarget = util.getAccount(account_target.getId());

            assertEquals(balance - value, accSource.getBalance(), 0.0001f);
            assertEquals(balance + value, accTarget.getBalance(), 0.0001f);
        }
    }

    @Test
    public void transferInsufficientFundsTest() throws IOException {

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/transfer", base_adress));
            double value = 10000.20f;

            ObjectNode transferNode = util.createTransferJson(account_source.getId(), account_target.getId(), value);
            StringEntity entity = new StringEntity(transferNode.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            JsonNode node = util.getJsonNodeForAccountResponse(response);
            assertEquals(EMessages.NOT_ENOUGH_FUNDS.ordinal(), node.get("code").longValue());


            //The accounts were not modified
            Account accSource = util.getAccount(account_source.getId());
            Account accTarget = util.getAccount(account_target.getId());

            assertEquals(balance, accSource.getBalance(), 0.0001f);
            assertEquals(balance, accTarget.getBalance(), 0.0001f);
        }
    }

    @Test
    public void transferTargetAccountNotFoundFundsTest() throws IOException {

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            long wrongAccountId = 9999;
            HttpPost httpPost = new HttpPost(String.format("%s/transfer", base_adress));
            double value = 10.20f;

            ObjectNode transferNode = util.createTransferJson(account_source.getId(), wrongAccountId, value);
            StringEntity entity = new StringEntity(transferNode.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals(jsonMimeType, mimeType);

            JsonNode node = util.getJsonNodeForAccountResponse(response);
            assertEquals(EMessages.ACCOUNT_NOT_FOUND.ordinal(), node.get("code").longValue());


            //The accounts were not modified
            Account accSource = util.getAccount(account_source.getId());
            Account accTarget = util.getAccount(account_target.getId());

            assertEquals(balance, accSource.getBalance(), 0.0001f);
            assertEquals(balance, accTarget.getBalance(), 0.0001f);
        }
    }

    @Test
    public void transferSourceAccountNotFoundFundsTest() throws IOException {

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            long wrongAccountId = 9999;
            HttpPost httpPost = new HttpPost(String.format("%s/transfer", base_adress));
            double value = 10.20f;

            ObjectNode transferNode = util.createTransferJson(wrongAccountId, account_target.getId(), value);
            StringEntity entity = new StringEntity(transferNode.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals(jsonMimeType, mimeType);

            JsonNode node = util.getJsonNodeForAccountResponse(response);
            assertEquals(EMessages.ACCOUNT_NOT_FOUND.ordinal(), node.get("code").longValue());


            //The accounts were not modified
            Account accSource = util.getAccount(account_source.getId());
            Account accTarget = util.getAccount(account_target.getId());

            assertEquals(balance, accSource.getBalance(), 0.0001f);
            assertEquals(balance, accTarget.getBalance(), 0.0001f);
        }
    }

    @Test
    public void transferNegativeTransferTest() throws IOException {

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/transfer", base_adress));
            double value = -1.20f;

            ObjectNode transferNode = util.createTransferJson(account_source.getId(), account_target.getId(), value);
            StringEntity entity = new StringEntity(transferNode.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            JsonNode node = util.getJsonNodeForAccountResponse(response);
            assertEquals(EMessages.INVALID_VALUE.ordinal(), node.get("code").longValue());


            //The accounts were not modified
            Account accSource = util.getAccount(account_source.getId());
            Account accTarget = util.getAccount(account_target.getId());

            assertEquals(balance, accSource.getBalance(), 0.0001f);
            assertEquals(balance, accTarget.getBalance(), 0.0001f);
        }
    }

    @Test
    public void transferNullTransferTest() throws IOException {

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/transfer", base_adress));
            Double value = null;

            ObjectNode transferNode = util.createTransferJson(account_source.getId(), account_target.getId(), value);
            StringEntity entity = new StringEntity(transferNode.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            JsonNode node = util.getJsonNodeForAccountResponse(response);
            assertEquals(EMessages.INVALID_VALUE.ordinal(), node.get("code").longValue());


            //The accounts were not modified
            Account accSource = util.getAccount(account_source.getId());
            Account accTarget = util.getAccount(account_target.getId());

            assertEquals(balance, accSource.getBalance(), 0.0001f);
            assertEquals(balance, accTarget.getBalance(), 0.0001f);
        }
    }

    @Test
    public void transferInvalidJsonTest() throws IOException {

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/transfer", base_adress));

            String invalidJson = "{\t\"accountSourceINVALID\": { \"id\": 1 },\t\"accountTarget\": { \"id\": 2 },\t\"value\": -200}";
            StringEntity entity = new StringEntity(invalidJson);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            JsonNode node = util.getJsonNodeForAccountResponse(response);
            assertEquals(EMessages.INVALID_JSON.ordinal(), node.get("code").longValue());


            //The accounts were not modified
            Account accSource = util.getAccount(account_source.getId());
            Account accTarget = util.getAccount(account_target.getId());

            assertEquals(balance, accSource.getBalance(), 0.0001f);
            assertEquals(balance, accTarget.getBalance(), 0.0001f);
        }
    }
}
