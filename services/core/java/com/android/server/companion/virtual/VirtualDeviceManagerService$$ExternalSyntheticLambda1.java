package com.android.server.companion.virtual;

import android.companion.virtual.IVirtualDeviceListener;
import android.os.RemoteException;
import android.util.Slog;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class VirtualDeviceManagerService$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ VirtualDeviceManagerService$$ExternalSyntheticLambda1(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        IVirtualDeviceListener iVirtualDeviceListener = (IVirtualDeviceListener) obj;
        switch (i) {
            case 0:
                try {
                    iVirtualDeviceListener.onVirtualDeviceClosed(i2);
                    break;
                } catch (RemoteException e) {
                    Slog.i("VirtualDeviceManagerService", "Failed to invoke onVirtualDeviceClosed listener: " + e.getMessage());
                    return;
                }
            default:
                try {
                    iVirtualDeviceListener.onVirtualDeviceCreated(i2);
                    break;
                } catch (RemoteException e2) {
                    Slog.i("VirtualDeviceManagerService", "Failed to invoke onVirtualDeviceCreated listener: " + e2.getMessage());
                }
        }
    }
}
