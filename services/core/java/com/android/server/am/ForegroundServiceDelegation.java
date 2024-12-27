package com.android.server.am;

import android.app.ForegroundServiceDelegationOptions;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ForegroundServiceDelegation {
    public final IBinder mBinder = new Binder();
    public final ServiceConnection mConnection;
    public final ForegroundServiceDelegationOptions mOptions;

    public ForegroundServiceDelegation(
            ForegroundServiceDelegationOptions foregroundServiceDelegationOptions,
            ServiceConnection serviceConnection) {
        this.mOptions = foregroundServiceDelegationOptions;
        this.mConnection = serviceConnection;
    }
}
