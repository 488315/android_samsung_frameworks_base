package com.android.server.pm.pkg;

import android.annotation.SystemApi;
import android.content.pm.VersionedPackage;
import java.util.List;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes3.dex */
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
