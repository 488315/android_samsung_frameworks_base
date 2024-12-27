package com.samsung.context.sdk.samsunganalytics.internal.connection;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
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
