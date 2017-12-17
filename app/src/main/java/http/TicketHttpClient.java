package http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TicketHttpClient {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private static final String ENCODING_UTF = "UTF-8";
    private static final String ACCEPT = "Accept";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private URL serverEndpoint;

    private HttpURLConnection httpClientConnection;
    private Gson gson;

    public TicketHttpClient() throws IOException {
        serverEndpoint = new URL(HttpUtil.ENDPOINT);
        gson = new GsonBuilder().create();
    }

    public Ticket createTicket(Ticket ticket, String username, String password)
            throws IOException {
        try {
            httpClientConnection = (HttpURLConnection) serverEndpoint.openConnection();
            httpClientConnection.setRequestMethod("POST");


            httpClientConnection.addRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            httpClientConnection.addRequestProperty(ACCEPT, APPLICATION_JSON);
            httpClientConnection.addRequestProperty(AUTHORIZATION, BASIC + HttpUtil.buildEncodedCredentials(username, password));

            httpClientConnection.setDoInput(true);
            httpClientConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(httpClientConnection.getOutputStream());
            String ticketGson = gson.toJson(TicketConvertor.convertToDto(ticket));
            writeStream(out, ticketGson);

            int responseCode = httpClientConnection.getResponseCode();
            System.out.print("PUTKATAAAAAA " + responseCode);
            BufferedReader responseInputStream = new BufferedReader(new InputStreamReader(httpClientConnection.getInputStream()));


            String result = null;
            StringBuffer response = new StringBuffer();
            while ((result = responseInputStream.readLine()) != null) {
                response.append(result);
            }
            responseInputStream.close();
            System.out.print("The result of the response" + response.toString());

            Ticket penis2 = TicketConvertor.convertToTicket(gson.fromJson(response.toString(), TicketDto.class));

            return penis2;
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            if (httpClientConnection != null) {
                httpClientConnection.disconnect();
            }
        }
        return null;
    }

    private void writeStream(OutputStream out, String data) throws IOException {
        out.write(data.getBytes());
        out.flush();
        out.close();
    }

    public Ticket updateTicket(String ticketId, Ticket ticket, String username, String password)
            throws  IOException {
        try {
            URL customEndpoint = new URL(HttpUtil.ENDPOINT + "/" + ticketId);
            httpClientConnection = (HttpURLConnection) customEndpoint.openConnection();
            httpClientConnection.setRequestMethod("PUT");

            httpClientConnection.addRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            httpClientConnection.addRequestProperty(ACCEPT, APPLICATION_JSON);

            httpClientConnection.setDoInput(true);
            httpClientConnection.setChunkedStreamingMode(0);
//        httpPut.setHeader(AUTHORIZATION, BASIC + HttpUtil.buildEncodedCredentials(username, password));

            OutputStream out = new BufferedOutputStream(httpClientConnection.getOutputStream());
            String ticketString = gson.toJson(TicketConvertor.convertToDto(ticket));
            writeStream(out, ticketString);

            int responseCode = httpClientConnection.getResponseCode();
            System.out.print("PUTKATAAAAAA " + responseCode);
            BufferedReader responseInputStream = new BufferedReader(new InputStreamReader(httpClientConnection.getInputStream()));


            String result = null;
            StringBuffer response = new StringBuffer();
            while ((result = responseInputStream.readLine()) != null) {
                response.append(result);
            }
            responseInputStream.close();
            System.out.print("The result of the response" + response.toString());

            return TicketConvertor.convertToTicket(gson.fromJson(response.toString(), TicketDto.class));
        } finally {
            if (httpClientConnection != null) {
                httpClientConnection.disconnect();
            }
        }
    }

    public Ticket getTicketBy(String ticketId) throws IOException {
        try{
            URL customEndpoint = new URL(HttpUtil.ENDPOINT + "/" + ticketId);
            httpClientConnection = (HttpURLConnection) customEndpoint.openConnection();
            httpClientConnection.setRequestMethod("GET");

            httpClientConnection.addRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            httpClientConnection.addRequestProperty(ACCEPT, APPLICATION_JSON);

            int responseCode = httpClientConnection.getResponseCode();
            System.out.print("PUTKATAAAAAA " + responseCode);
            BufferedReader responseInputStream = new BufferedReader(new InputStreamReader(httpClientConnection.getInputStream()));


            String result = null;
            StringBuffer response = new StringBuffer();
            while ((result = responseInputStream.readLine()) != null) {
                response.append(result);
            }
            responseInputStream.close();
            System.out.print("The result of the response" + response.toString());

            return TicketConvertor.convertToTicket(gson.fromJson(response.toString(), TicketDto.class));
        }finally{
            if (httpClientConnection != null){
                httpClientConnection.disconnect();
            }
        }
    }

}
