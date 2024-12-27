package com.samsung.context.sdk.samsunganalytics.internal.connection;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    String method;

    HttpMethod(String str) {
        this.method = str;
    }
}
