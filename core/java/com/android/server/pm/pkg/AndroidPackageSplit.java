package com.android.server.pm.pkg;

import android.annotation.SystemApi;

import java.util.List;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes5.dex */
public interface AndroidPackageSplit {
    String getClassLoaderName();

    List<AndroidPackageSplit> getDependencies();

    String getName();

    String getPath();

    int getRevisionCode();

    boolean isHasCode();
}
