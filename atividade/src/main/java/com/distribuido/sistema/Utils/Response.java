package com.distribuido.sistema.Utils;

import java.io.Serializable;
import java.util.Map;

public class Response implements Serializable {

    private Map<Object, Object> body;

    private int status;

    public Response() {
    }

    public Response(Map<Object,Object> body, int status) {
        this.body = body;
        this.status = status;
    }

    public Map<Object,Object> getBody() {
        return this.body;
    }

    public void setBody(Map<Object,Object> body) {
        this.body = body;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
