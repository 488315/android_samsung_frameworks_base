package com.android.server.role;

import android.annotation.SystemApi;
import java.util.Map;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes3.dex */
public interface RoleServicePlatformHelper {
  String computePackageStateHash(int i);

  Map getLegacyRoleState(int i);
}
