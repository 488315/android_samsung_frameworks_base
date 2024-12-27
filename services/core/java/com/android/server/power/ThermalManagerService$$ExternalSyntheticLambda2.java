package com.android.server.power;

import android.os.IThermalEventListener;
import android.os.IThermalStatusListener;
import android.os.RemoteException;
import android.os.Temperature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ThermalManagerService$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ThermalManagerService$$ExternalSyntheticLambda2(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                try {
                    ((IThermalEventListener) this.f$0).notifyThrottling((Temperature) this.f$1);
                    break;
                } catch (RemoteException | RuntimeException e) {
                    android.util.Slog.e("ThermalManagerService", "Thermal callback failed to call", e);
                    return;
                }
            default:
                ThermalManagerService thermalManagerService = (ThermalManagerService) this.f$0;
                IThermalStatusListener iThermalStatusListener = (IThermalStatusListener) this.f$1;
                thermalManagerService.getClass();
                try {
                    iThermalStatusListener.onStatusChange(thermalManagerService.mStatus);
                    break;
                } catch (RemoteException | RuntimeException e2) {
                    android.util.Slog.e("ThermalManagerService", "Thermal callback failed to call", e2);
                }
        }
    }
}
