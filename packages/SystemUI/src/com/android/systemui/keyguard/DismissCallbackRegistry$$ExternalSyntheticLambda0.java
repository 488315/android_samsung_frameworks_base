package com.android.systemui.keyguard;

import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DismissCallbackRegistry$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DismissCallbackWrapper f$0;

    public /* synthetic */ DismissCallbackRegistry$$ExternalSyntheticLambda0(DismissCallbackWrapper dismissCallbackWrapper, int i) {
        this.$r8$classId = i;
        this.f$0 = dismissCallbackWrapper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DismissCallbackWrapper dismissCallbackWrapper = this.f$0;
        dismissCallbackWrapper.getClass();
        switch (i) {
            case 0:
                try {
                    dismissCallbackWrapper.mCallback.onDismissCancelled();
                    break;
                } catch (RemoteException e) {
                    android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e);
                    return;
                }
            default:
                try {
                    dismissCallbackWrapper.mCallback.onDismissSucceeded();
                    break;
                } catch (RemoteException e2) {
                    android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e2);
                }
        }
    }
}
