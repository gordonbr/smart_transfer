package functional.test.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.smarttransfer.util.EMessages;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestUtil;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jonathasalves on 30/01/2019.
 */
public class CreateAccountTest extends BaseTest{

    private static TestUtil util;
    private String jsonMimeType = "application/json";

    @BeforeClass
    public static void setUp() throws IOException {
        util = new TestUtil();
    }

    @Test
    public void createAccountOkTest() throws IOException {

        double initialBalance = 3000.50;

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/account/%f", base_adress, initialBalance));


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            JsonNode node = util.getJsonNodeForAccountResponse(response);

            assertTrue(node.get("id").intValue() > 0);
            assertEquals(0, node.get("versionID").intValue());
            assertEquals(initialBalance, node.get("balance").doubleValue(), 0.001f);
        }
    }

    @Test
    public void createAccountInvalidBalanceTest() throws IOException {

        int invalidInitialBalance = -1;

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/account/%d", base_adress, invalidInitialBalance));


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            JsonNode node = util.getJsonNodeForAccountResponse(response);

            assertEquals(EMessages.INVALID_VALUE.ordinal(), node.get("code").longValue());
        }
    }

    @Test
    public void createAccountInvalidBalanceTest2() throws IOException {

        String invalidInitialBalance = "test";

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/account/%s", base_adress, invalidInitialBalance));


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            JsonNode node = util.getJsonNodeForAccountResponse(response);

            assertEquals(EMessages.INVALID_VALUE.ordinal(), node.get("code").longValue());
        }
    }

    @Test
    public void createAccountNullBalanceTest() throws IOException {

        String nullInitialBalance = null;

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("%s/account/%d", base_adress, nullInitialBalance));


            CloseableHttpResponse response = client.execute(httpPost);
            assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());

            String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            assertEquals( jsonMimeType, mimeType );

            JsonNode node = util.getJsonNodeForAccountResponse(response);

            assertEquals(EMessages.INVALID_VALUE.ordinal(), node.get("code").longValue());
        }
    }
}
