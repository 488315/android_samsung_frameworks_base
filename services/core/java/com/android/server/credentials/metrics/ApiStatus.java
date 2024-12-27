package com.android.server.credentials.metrics;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
