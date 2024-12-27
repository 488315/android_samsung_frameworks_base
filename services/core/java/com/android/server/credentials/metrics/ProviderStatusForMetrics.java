package com.android.server.credentials.metrics;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
