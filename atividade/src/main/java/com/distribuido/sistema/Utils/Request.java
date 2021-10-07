package com.distribuido.sistema.Utils;

import java.io.Serializable;
import java.util.Map;

public class Request implements Serializable {

    private Header header;

    private Map<Object, Object> body;

    public Request() {
    }

    public Request(Header header, Map<Object,Object> body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return this.header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Map<Object,Object> getBody() {
        return this.body;
    }

    public void setBody(Map<Object,Object> body) {
        this.body = body;
    }

    public static class Header {
        private String token;

        public Header() {

        }

        public Header(String token) {
            this.token = token;
        }

        public String getToken() {
            return this.token;
        }
    
        public void setToken(String token) {
            this.token = token;
        }
    }
}
