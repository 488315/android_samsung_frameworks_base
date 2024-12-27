package com.android.server.appop;

import android.annotation.SystemApi;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public interface AppOpsManagerLocal {
    boolean isUidInForeground(int i);
}
