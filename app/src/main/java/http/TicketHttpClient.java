package http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TicketHttpClient {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private static final String ENCODING_UTF = "UTF-8";
    private static final String ACCEPT = "Accept";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private HttpURLConnection httpClientConnection;
    private HttpResponse httpResponse;
    private StringEntity stringEntity;
    private Gson gson;
    private TicketDto ticketDto;

    public TicketHttpClient() throws IOException {
        URL serverEndpoint = new URL(HttpUtil.ENDPOINT);
        httpClientConnection = (HttpURLConnection) serverEndpoint.openConnection();
        gson = new GsonBuilder().create();
    }

    public Ticket createTicket(Ticket ticket, String username, String password)
            throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(HttpUtil.ENDPOINT);
        httpClientConnection.setRequestMethod("POST");

        httpClientConnection.addRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
        httpClientConnection.addRequestProperty(ACCEPT, APPLICATION_JSON);
        httpClientConnection.addRequestProperty(AUTHORIZATION, BASIC + HttpUtil.buildEncodedCredentials(username, password));

        stringEntity = new StringEntity(gson.toJson(TicketConvertor.convertToDto(ticket)));
        httpPost.setEntity(stringEntity);

        httpResponse = httpClientConnection.execute(httpPost);
        String string = IOUtils.toString(httpResponse.getEntity().getContent(), ENCODING_UTF);

        return TicketConvertor.convertToTicket(gson.fromJson(string, TicketDto.class));
    }

    public Ticket updateTicket(String ticketId, Ticket ticket, String username, String password)
            throws ClientProtocolException, IOException {
        HttpPut httpPut = new HttpPut(HttpUtil.buildUrlWith(ticketId));
        httpPut.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        httpPut.setHeader(ACCEPT, APPLICATION_JSON);
        httpPut.setHeader(AUTHORIZATION, BASIC + HttpUtil.buildEncodedCredentials(username, password));

        stringEntity = new StringEntity(gson.toJson(TicketConvertor.convertToDto(ticket)));
        httpPut.setEntity(stringEntity);

        httpResponse = httpClientConnection.execute(httpPut);
        ticketDto = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), TicketDto.class);
        return TicketConvertor.convertToTicket(ticketDto);
    }

    public Ticket getTicketBy(String ticketId) throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet(HttpUtil.buildUrlWith(ticketId));
        httpGet.setHeader(ACCEPT, APPLICATION_JSON);

        httpResponse = httpClientConnection.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        String entityString = IOUtils.toString(entity.getContent(), ENCODING_UTF);
        return TicketConvertor.convertToTicket(gson.fromJson(entityString, TicketDto.class));
    }

}
