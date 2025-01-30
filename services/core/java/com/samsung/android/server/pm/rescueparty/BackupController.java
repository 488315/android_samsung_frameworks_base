package com.samsung.android.server.pm.rescueparty;

/* loaded from: classes2.dex */
public interface BackupController {
    String getControllerName();

    void onSystemReady();

    void saveFiles();
}
