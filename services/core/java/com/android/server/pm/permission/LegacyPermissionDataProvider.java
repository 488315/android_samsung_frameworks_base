package com.android.server.pm.permission;

import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface LegacyPermissionDataProvider {
  Map getAllAppOpPermissionPackages();

  int[] getGidsForUid(int i);

  LegacyPermissionState getLegacyPermissionState(int i);

  List getLegacyPermissions();

  void writeLegacyPermissionStateTEMP();
}
