package com.samsung.context.sdk.samsunganalytics.internal.sender;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
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
