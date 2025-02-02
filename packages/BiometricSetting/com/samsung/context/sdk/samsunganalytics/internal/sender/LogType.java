package com.samsung.context.sdk.samsunganalytics.internal.sender;

/* loaded from: classes.dex */
public enum LogType {
    DEVICE("dvc"),
    UIX("uix");

    String abbrev;

    LogType(String str) {
        this.abbrev = str;
    }

    public final String getAbbrev() {
        return this.abbrev;
    }
}
