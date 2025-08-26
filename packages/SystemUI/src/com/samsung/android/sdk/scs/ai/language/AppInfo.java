package com.samsung.android.sdk.scs.ai.language;

/* loaded from: classes4.dex */
public class AppInfo {
    public final String accessToken;
    public final String accountType;
    public final String apiKey;
    public final String appId;
    public final RequestType requestType;
    public final String serverUrl;
    public final String signingKey;
    public final String userId;

    public class Builder {
        public String apiKey = "";
        public String signingKey = "";
        public RequestType requestType = RequestType.CLOUD;
    }

    public enum RequestType {
        CLOUD,
        ONDEVICE,
        /* JADX INFO: Fake field, exist only in values array */
        ONDEVICE_EXTERNAL
    }

    public /* synthetic */ AppInfo(Builder builder, int i) {
        this(builder);
    }

    private AppInfo(Builder builder) {
        this.apiKey = builder.apiKey;
        this.serverUrl = "";
        this.appId = "";
        this.signingKey = builder.signingKey;
        this.accessToken = "";
        this.userId = "";
        this.accountType = "B2C";
        this.requestType = builder.requestType;
    }
}
