package com.samsung.context.sdk.samsunganalytics.internal.connection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public enum Directory {
    DEVICE_CONTROLLER_DIR("/v1/quotas"),
    DATA_DELETE("/app/delete"),
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
