package com.android.server.pm.pkg;

import android.annotation.SystemApi;
import java.util.List;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes3.dex */
public interface AndroidPackageSplit {
    String getClassLoaderName();

    List getDependencies();

    String getName();

    String getPath();

    int getRevisionCode();

    boolean isHasCode();
}
