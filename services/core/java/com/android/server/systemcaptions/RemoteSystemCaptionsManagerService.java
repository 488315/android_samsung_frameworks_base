package com.android.server.systemcaptions;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Slog;

public final class RemoteSystemCaptionsManagerService {
    public final Context mContext;
    public final Intent mIntent;
    public IBinder mService;
    public final int mUserId;
    public final boolean mVerbose;
    public final Object mLock = new Object();
    public final RemoteServiceConnection mServiceConnection = new RemoteServiceConnection();
    public boolean mBinding = false;
    public boolean mDestroyed = false;
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    public final class RemoteServiceConnection implements ServiceConnection {
        public RemoteServiceConnection() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (RemoteSystemCaptionsManagerService.this.mLock) {
                try {
                    if (RemoteSystemCaptionsManagerService.this.mVerbose) {
                        Slog.v("RemoteSystemCaptionsManagerService", "onServiceConnected()");
                    }
                    RemoteSystemCaptionsManagerService remoteSystemCaptionsManagerService =
                            RemoteSystemCaptionsManagerService.this;
                    if (!remoteSystemCaptionsManagerService.mDestroyed
                            && remoteSystemCaptionsManagerService.mBinding) {
                        remoteSystemCaptionsManagerService.mBinding = false;
                        remoteSystemCaptionsManagerService.mService = iBinder;
                        return;
                    }
                    Slog.wtf(
                            "RemoteSystemCaptionsManagerService",
                            "onServiceConnected() dispatched after unbindService");
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (RemoteSystemCaptionsManagerService.this.mLock) {
                try {
                    if (RemoteSystemCaptionsManagerService.this.mVerbose) {
                        Slog.v("RemoteSystemCaptionsManagerService", "onServiceDisconnected()");
                    }
                    RemoteSystemCaptionsManagerService remoteSystemCaptionsManagerService =
                            RemoteSystemCaptionsManagerService.this;
                    remoteSystemCaptionsManagerService.mBinding = true;
                    remoteSystemCaptionsManagerService.mService = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public RemoteSystemCaptionsManagerService(
            Context context, ComponentName componentName, boolean z, int i) {
        this.mContext = context;
        this.mUserId = i;
        this.mVerbose = z;
        this.mIntent =
                new Intent("android.service.systemcaptions.SystemCaptionsManagerService")
                        .setComponent(componentName);
    }
}
