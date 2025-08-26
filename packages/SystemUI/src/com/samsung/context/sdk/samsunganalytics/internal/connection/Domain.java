package com.samsung.context.sdk.samsunganalytics.internal.connection;

/* loaded from: classes4.dex */
public enum Domain {
    REGISTRATION("https://regi.di.atlas.samsung.com"),
    POLICY("https://dc.di.atlas.samsung.com"),
    DLS("");

    String domain;

    Domain(String str) {
        this.domain = str;
    }

    public final void setDomain(String str) {
        this.domain = str;
    }
}
