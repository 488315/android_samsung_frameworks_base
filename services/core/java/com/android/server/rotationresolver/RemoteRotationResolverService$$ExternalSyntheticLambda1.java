package com.android.server.rotationresolver;

import android.os.ICancellationSignal;
import android.os.RemoteException;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteRotationResolverService$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RemoteRotationResolverService.RotationRequest f$0;

    public /* synthetic */ RemoteRotationResolverService$$ExternalSyntheticLambda1(
            RemoteRotationResolverService.RotationRequest rotationRequest, int i) {
        this.$r8$classId = i;
        this.f$0 = rotationRequest;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        RemoteRotationResolverService.RotationRequest rotationRequest = this.f$0;
        switch (i) {
            case 0:
                int i2 = RemoteRotationResolverService.$r8$clinit;
                synchronized (rotationRequest.mLock) {
                    try {
                        if (!rotationRequest.mIsFulfilled) {
                            rotationRequest.mCallbackInternal.onFailure(1);
                            Slog.d(
                                    "RemoteRotationResolverService",
                                    "Trying to cancel the remote request. Reason: Timed out.");
                            rotationRequest.cancelInternal();
                        }
                    } finally {
                    }
                }
                return;
            default:
                synchronized (rotationRequest.mLock) {
                    try {
                        if (rotationRequest.mIsFulfilled) {
                            return;
                        }
                        rotationRequest.mIsFulfilled = true;
                        try {
                            ICancellationSignal iCancellationSignal = rotationRequest.mCancellation;
                            if (iCancellationSignal != null) {
                                iCancellationSignal.cancel();
                                rotationRequest.mCancellation = null;
                            }
                        } catch (RemoteException unused) {
                            int i3 = RemoteRotationResolverService.$r8$clinit;
                            Slog.w(
                                    "RemoteRotationResolverService",
                                    "Failed to cancel request in remote service.");
                        }
                        return;
                    } finally {
                    }
                }
        }
    }
}
