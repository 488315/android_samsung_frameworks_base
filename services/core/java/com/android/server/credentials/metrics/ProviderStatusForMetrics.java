package com.android.server.credentials.metrics;

public enum ProviderStatusForMetrics {
    /* JADX INFO: Fake field, exist only in values array */
    EF0("UNKNOWN"),
    FINAL_FAILURE("FINAL_FAILURE"),
    QUERY_FAILURE("QUERY_FAILURE"),
    FINAL_SUCCESS("FINAL_SUCCESS"),
    QUERY_SUCCESS("QUERY_SUCCESS");

    private final int mInnerMetricCode;

    ProviderStatusForMetrics(String str) {
        this.mInnerMetricCode = r2;
    }

    public final int getMetricCode() {
        return this.mInnerMetricCode;
    }
}
