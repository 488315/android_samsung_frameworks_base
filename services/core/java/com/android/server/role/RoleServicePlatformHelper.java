package com.android.server.role;

import android.annotation.SystemApi;

import java.util.Map;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public interface RoleServicePlatformHelper {
    String computePackageStateHash(int i);

    Map getLegacyRoleState(int i);
}
