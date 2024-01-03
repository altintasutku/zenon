package org.altintas.zenon;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Request {

    private URL url;
    private ArrayList<QueryParameter> params;
    private RequestType type;
    private ArrayList<Header> headers;
    private JSONObject body;
    private Exception error;
    private HttpURLConnection connection;

    public Request(@NotNull URL url,
                   @NotNull RequestType type,
                   ArrayList<Header> headers,
                   ArrayList<QueryParameter> params,
                   JSONObject body) {
        this.url = url;
        this.type = type;
        this.headers = headers;
        this.body = body;
        this.params = params;
        try {
            this.connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod(type.toString());
        } catch (IOException e) {
            e.printStackTrace();
            this.error = e;
            throw new RuntimeException(e);
        }
    }

    public Request (Exception e){
        this.error = e;
    }

    public void send(OnCompleteListener onCompleteListener){
        if(type == RequestType.POST || type == RequestType.PUT)
            writeBody();
        try {
            connection.connect();
            onCompleteListener.completeListener(new Result(connection));
        } catch (IOException e) {
            e.printStackTrace();
            onCompleteListener.completeListener(new Result(e));
        }
    }

    public Result send(){
        if(type == RequestType.POST || type == RequestType.PUT)
            writeBody();
        try {
            connection.connect();
            return new Result(connection);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(e);
        }
    }

    private void writeBody(){
        connection.setDoOutput(true);
        try {
            connection.getOutputStream().write(body.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request addHeader(String key, String value){
        headers.add(new Header(key,value));
        return this;
    }

    public Request setHeaders(Header header){
        headers.add(header);
        return this;
    }

    public Request addMultipleHeaders(ArrayList<Header> headers){
        this.headers.addAll(headers);
        return this;
    }

    public Request addBodyItem(String key, String value){
        try {
            body.put(key,value);
        }catch (Exception e){
            e.printStackTrace();
            this.error = e;
        }
        return this;
    }

    public Request setBody(JSONObject body){
        this.body = body;
        return this;
    }

    public Request addMultipleBodyItems(HashMap<String,String> bodyItems){
        for (String key : bodyItems.keySet()) {
            addBodyItem(key,bodyItems.get(key));
        }
        return this;
    }

    public Request addParam(String key, String value){
        params.add(new QueryParameter(key,value));
        return this;
    }

    public Request setParams(QueryParameter param){
        params.add(param);
        return this;

    }

    public Request addMultipleParams(ArrayList<QueryParameter> params){
        this.params.addAll(params);
        return this;
    }

    public String getUrl() {
        return url.toString();
    }

    public RequestType getType() {
        return type;
    }

    public ArrayList<Header> getHeaders() {
        return headers;
    }

    public JSONObject getBody() {
        return body;
    }

    public ArrayList<QueryParameter> getParams() {
        return params;
    }

    public Exception getError() {
        return error;
    }
}
