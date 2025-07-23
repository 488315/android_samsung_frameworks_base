package com.samsung.context.sdk.samsunganalytics.internal.connection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
