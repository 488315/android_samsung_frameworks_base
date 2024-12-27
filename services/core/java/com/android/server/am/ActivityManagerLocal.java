package com.android.server.am;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public interface ActivityManagerLocal {
    boolean bindSdkSandboxService(
            Intent intent,
            ServiceConnection serviceConnection,
            int i,
            IBinder iBinder,
            String str,
            String str2,
            int i2)
            throws RemoteException;

    boolean bindSdkSandboxService(
            Intent intent,
            ServiceConnection serviceConnection,
            int i,
            IBinder iBinder,
            String str,
            String str2,
            Context.BindServiceFlags bindServiceFlags)
            throws RemoteException;

    boolean bindSdkSandboxService(
            Intent intent,
            ServiceConnection serviceConnection,
            int i,
            String str,
            String str2,
            int i2)
            throws RemoteException;

    boolean canAllowWhileInUsePermissionInFgs(int i, int i2, String str);

    boolean canStartForegroundService(int i, int i2, String str);

    void killSdkSandboxClientAppProcess(IBinder iBinder);

    ComponentName startSdkSandboxService(Intent intent, int i, String str, String str2)
            throws RemoteException;

    boolean stopSdkSandboxService(Intent intent, int i, String str, String str2);

    void tempAllowWhileInUsePermissionInFgs(int i, long j);
}
