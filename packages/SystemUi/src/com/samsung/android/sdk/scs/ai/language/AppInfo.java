package com.samsung.android.sdk.scs.ai.language;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AppInfo {
    public final String accessToken;
    public final String apiKey;
    public final String appId;
    public final boolean enableStreaming;
    public final RequestType requestType;
    public final String serverUrl;
    public final String signingKey;
    public final String userId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public String apiKey = "";
        public String signingKey = "";
        public final String AppId = "";
        public final String accessToken = "";
        public final String userId = "";
        public final String serverUrl = "";
        public RequestType requestType = RequestType.CLOUD;
        public boolean enableStreaming = false;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.serverUrl = builder.serverUrl;
        this.appId = builder.AppId;
        this.signingKey = builder.signingKey;
        this.accessToken = builder.accessToken;
        this.userId = builder.userId;
        this.requestType = builder.requestType;
        this.enableStreaming = builder.enableStreaming;
    }
}
