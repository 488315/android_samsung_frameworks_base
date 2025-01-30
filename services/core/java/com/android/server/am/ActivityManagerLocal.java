package com.android.server.am;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes.dex */
public interface ActivityManagerLocal {
    boolean bindSdkSandboxService(Intent intent, ServiceConnection serviceConnection, int i, IBinder iBinder, String str, String str2, int i2);

    boolean bindSdkSandboxService(Intent intent, ServiceConnection serviceConnection, int i, IBinder iBinder, String str, String str2, Context.BindServiceFlags bindServiceFlags);

    boolean bindSdkSandboxService(Intent intent, ServiceConnection serviceConnection, int i, String str, String str2, int i2);

    boolean canAllowWhileInUsePermissionInFgs(int i, int i2, String str);

    boolean canStartForegroundService(int i, int i2, String str);

    void killSdkSandboxClientAppProcess(IBinder iBinder);

    ComponentName startSdkSandboxService(Intent intent, int i, String str, String str2);

    boolean stopSdkSandboxService(Intent intent, int i, String str, String str2);

    void tempAllowWhileInUsePermissionInFgs(int i, long j);
}
