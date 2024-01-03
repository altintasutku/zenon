package org.altintas.zenon;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Zenon {

    static public Request createRequest(String url, RequestType type) {
        ArrayList<Header> headers = new ArrayList<>();
        ArrayList<QueryParameter> params = new ArrayList<>();
        URL reqUrl;
        try {
            reqUrl = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Request(e);
        }
        return new Request(reqUrl, type, headers, params, null);
    }
}