package com.samsung.context.sdk.samsunganalytics.internal.connection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum Directory {
    DEVICE_CONTROLLER_DIR("/v3/sdk/quotas"),
    DATA_DELETE("/v3/sdk/indiv/delete"),
    DLS_DIR(""),
    DLS_DIR_BAT("");

    String directory;

    Directory(String str) {
        this.directory = str;
    }

    public final void setDirectory(String str) {
        this.directory = str;
    }
}
