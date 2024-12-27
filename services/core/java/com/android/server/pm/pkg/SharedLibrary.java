package com.android.server.pm.pkg;

import android.annotation.SystemApi;
import android.content.pm.VersionedPackage;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface SharedLibrary {
    List getAllCodePaths();

    VersionedPackage getDeclaringPackage();

    List getDependencies();

    List getDependentPackages();

    String getName();

    String getPackageName();

    String getPath();

    int getType();

    long getVersion();

    boolean isNative();
}
