package com.android.server.powerstats;

import android.hardware.power.stats.IPowerStats;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PowerStatsHALWrapper$VintfHalCache implements Supplier, IBinder.DeathRecipient {
    public IPowerStats mInstance;

    @Override // android.os.IBinder.DeathRecipient
    public final synchronized void binderDied() {
        Slog.w("PowerStatsHALWrapper", "PowerStats HAL died");
        this.mInstance = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [android.hardware.power.stats.IPowerStats] */
    @Override // java.util.function.Supplier
    public final Object get() {
        IPowerStats iPowerStats;
        IBinder allowBlocking;
        IPowerStats.Stub.Proxy proxy;
        synchronized (this) {
            if (this.mInstance == null
                    && (allowBlocking =
                                    Binder.allowBlocking(
                                            ServiceManager.waitForDeclaredService(
                                                    "android.hardware.power.stats.IPowerStats/default")))
                            != null) {
                int i = IPowerStats.Stub.$r8$clinit;
                IInterface queryLocalInterface =
                        allowBlocking.queryLocalInterface(IPowerStats.DESCRIPTOR);
                if (queryLocalInterface == null || !(queryLocalInterface instanceof IPowerStats)) {
                    IPowerStats.Stub.Proxy proxy2 = new IPowerStats.Stub.Proxy();
                    proxy2.mRemote = allowBlocking;
                    proxy = proxy2;
                } else {
                    proxy = (IPowerStats) queryLocalInterface;
                }
                this.mInstance = proxy;
                try {
                    allowBlocking.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    Slog.e(
                            "PowerStatsHALWrapper",
                            "Unable to register DeathRecipient for " + this.mInstance);
                }
            }
            iPowerStats = this.mInstance;
        }
        return iPowerStats;
    }
}
