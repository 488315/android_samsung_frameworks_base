package com.android.server.location.injector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.PowerManager;
import com.android.internal.util.Preconditions;
import com.android.server.FgThread;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemDeviceIdleHelper {
    public final Context mContext;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    public PowerManager mPowerManager;
    public AnonymousClass1 mReceiver;
    public boolean mRegistrationRequired;
    public boolean mSystemReady;

    public SystemDeviceIdleHelper(Context context) {
        this.mContext = context;
    }

    public final synchronized void addListener(DeviceIdleHelper$DeviceIdleListener deviceIdleHelper$DeviceIdleListener) {
        if (this.mListeners.add(deviceIdleHelper$DeviceIdleListener) && this.mListeners.size() == 1) {
            synchronized (this) {
                this.mRegistrationRequired = true;
                onRegistrationStateChanged();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.content.BroadcastReceiver, com.android.server.location.injector.SystemDeviceIdleHelper$1] */
    public final void onRegistrationStateChanged() {
        AnonymousClass1 anonymousClass1;
        if (this.mSystemReady) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean z = this.mRegistrationRequired;
                if (z && this.mReceiver == null) {
                    ?? r2 = new BroadcastReceiver() { // from class: com.android.server.location.injector.SystemDeviceIdleHelper.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            SystemDeviceIdleHelper systemDeviceIdleHelper = SystemDeviceIdleHelper.this;
                            Preconditions.checkState(systemDeviceIdleHelper.mPowerManager != null);
                            boolean isDeviceIdleMode = systemDeviceIdleHelper.mPowerManager.isDeviceIdleMode();
                            Iterator it = systemDeviceIdleHelper.mListeners.iterator();
                            while (it.hasNext()) {
                                ((DeviceIdleHelper$DeviceIdleListener) it.next()).onDeviceIdleChanged(isDeviceIdleMode);
                            }
                        }
                    };
                    this.mContext.registerReceiver(r2, new IntentFilter("android.os.action.DEVICE_IDLE_MODE_CHANGED"), null, FgThread.getHandler());
                    this.mReceiver = r2;
                } else if (!z && (anonymousClass1 = this.mReceiver) != null) {
                    this.mReceiver = null;
                    this.mContext.unregisterReceiver(anonymousClass1);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }
}
