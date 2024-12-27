package com.android.server.companion.virtual;

import android.companion.virtual.IVirtualDeviceIntentInterceptor;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;

import java.util.Map;

public final /* synthetic */ class VirtualDeviceImpl$$ExternalSyntheticLambda2 {
    public final /* synthetic */ VirtualDeviceImpl f$0;

    public boolean shouldInterceptIntent(Intent intent) {
        boolean z;
        VirtualDeviceImpl virtualDeviceImpl = this.f$0;
        synchronized (virtualDeviceImpl.mVirtualDeviceLock) {
            try {
                z = false;
                for (Map.Entry entry :
                        ((ArrayMap) virtualDeviceImpl.mIntentInterceptors).entrySet()) {
                    IntentFilter intentFilter = (IntentFilter) entry.getValue();
                    if (android.companion.virtualdevice.flags.Flags
                                    .intentInterceptionActionMatchingFix()
                            && intent.getAction() == null
                            && intentFilter.countActions() != 0) {}
                    if (intentFilter.match(
                                    intent.getAction(),
                                    intent.getType(),
                                    intent.getScheme(),
                                    intent.getData(),
                                    intent.getCategories(),
                                    "VirtualDeviceImpl")
                            >= 0) {
                        try {
                            IVirtualDeviceIntentInterceptor.Stub.asInterface(
                                            (IBinder) entry.getKey())
                                    .onIntentIntercepted(
                                            new Intent(intent.getAction(), intent.getData()));
                            z = true;
                        } catch (RemoteException e) {
                            Slog.w(
                                    "VirtualDeviceImpl",
                                    "Unable to call mVirtualDeviceIntentInterceptor",
                                    e);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }
}
