package org.altintas.zenon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Result {

    private int statusCode;
    private String data;
    private boolean success;
    private Exception error;
    private HttpURLConnection connection;

    public Result(HttpURLConnection connection) {
        this.connection = connection;
        try {
            this.statusCode = connection.getResponseCode();
            this.success = statusCode == 200;
            this.data = getResult(connection);
        } catch (Exception e) {
            e.printStackTrace();
            this.error = e;
        }
        connection.disconnect();
    }

    public Result(Exception e) {
        this.error = e;
    }

    private String getResult(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getData() {
        return data;
    }

    public JSONObject getJson() {
        try {
            return new JSONObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public JSONArray getJsonArray() {
        try {
            return new JSONArray(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public Exception getError() {
        return error;
    }

    public HttpURLConnection getHttpConnection() {
        return connection;
    }
}
