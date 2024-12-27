package com.android.server;

import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class StorageManagerService$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StorageManagerService$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((StorageManagerService) obj).connectStoraged();
                return;
            case 1:
                ((StorageManagerService) obj).connectVold();
                return;
            default:
                StorageManagerService.AppFuseMountScope appFuseMountScope =
                        (StorageManagerService.AppFuseMountScope) obj;
                appFuseMountScope.getClass();
                try {
                    StorageManagerService.this.mVold.unmountAppFuse(
                            appFuseMountScope.uid, appFuseMountScope.mountId);
                    return;
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
        }
    }
}
