package com.android.server.credentials.metrics;

public enum ApiStatus {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    CLIENT_CANCELED("CLIENT_CANCELED"),
    USER_CANCELED("USER_CANCELED");

    private final int mInnerMetricCode;

    ApiStatus(String str) {
        this.mInnerMetricCode = r2;
    }

    public final int getMetricCode() {
        return this.mInnerMetricCode;
    }
}
