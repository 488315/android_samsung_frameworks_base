package com.android.server.location.provider;

import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.RemoteException;

public final /* synthetic */ class LocationProviderManager$$ExternalSyntheticLambda22
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LocationProviderManager$$ExternalSyntheticLambda22(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                throw new AssertionError((RuntimeException) obj);
            case 1:
                throw new AssertionError((RuntimeException) obj);
            case 2:
                throw ((RuntimeException) obj);
            case 3:
                throw ((RuntimeException) obj);
            case 4:
                throw ((RuntimeException) obj);
            case 5:
                throw ((RuntimeException) obj);
            default:
                try {
                    ((IRemoteCallback) obj).sendResult((Bundle) null);
                    return;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
        }
    }
}
