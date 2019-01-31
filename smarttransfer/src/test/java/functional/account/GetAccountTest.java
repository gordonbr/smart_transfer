package functional.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarttransfer.model.Account;
import com.smarttransfer.util.EMessages;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestUtil;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by jonathasalves on 30/01/2019.
 */
public class GetAccountTest extends BaseTest {

    private String jsonMimeType = "application/json";
    private static Account account;
    private static TestUtil util;
    private static double balance = 1000.50f;

    @BeforeClass
    public static void setUp() throws IOException {
        util = new TestUtil();
        account = util.createAccount(balance);
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void getAccountOkTest() throws IOException {


        HttpUriRequest request = new HttpGet( String.format("%s/account/%d", base_adress, account.getId()) );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode());

        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );

        JsonNode node = util.getJsonNodeForAccountResponse(httpResponse);

        assertEquals(account.getId(), node.get("id").intValue());
        assertEquals(balance, node.get("balance").floatValue(), 0.001f);
    }


    @Test
    public void getAccountNotFound() throws IOException {

        int nonExistentAccountNumber = 222;

        HttpUriRequest request = new HttpGet( String.format("%s/account/%d", base_adress, nonExistentAccountNumber) );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());

        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );

        JsonNode node = util.getJsonNodeForAccountResponse(httpResponse);

        assertEquals(EMessages.ACCOUNT_NOT_FOUND.ordinal(), node.get("code").intValue());
    }

    @Test
    public void getAccountInvalidNumber() throws IOException {
        int invalidAccountNumber = -1;

        HttpUriRequest request = new HttpGet( String.format("%s/account/%d", base_adress, invalidAccountNumber) );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());

        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );

        JsonNode node = util.getJsonNodeForAccountResponse(httpResponse);

        assertEquals(EMessages.INVALID_VALUE.ordinal(), node.get("code").intValue());
    }

    @Test
    public void getAccountNullNumber() throws IOException {
        String nullAccountNumber = null;

        HttpUriRequest request = new HttpGet( String.format("%s/account/%d", base_adress, nullAccountNumber) );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, httpResponse.getStatusLine().getStatusCode());

        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );

        JsonNode node = util.getJsonNodeForAccountResponse(httpResponse);

        assertEquals(EMessages.INVALID_VALUE.ordinal(), node.get("code").intValue());
    }

}
