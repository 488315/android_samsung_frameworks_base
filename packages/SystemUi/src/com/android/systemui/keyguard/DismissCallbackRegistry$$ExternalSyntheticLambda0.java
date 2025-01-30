package com.android.systemui.keyguard;

import android.os.RemoteException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DismissCallbackRegistry$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DismissCallbackWrapper f$0;

    public /* synthetic */ DismissCallbackRegistry$$ExternalSyntheticLambda0(DismissCallbackWrapper dismissCallbackWrapper, int i) {
        this.$r8$classId = i;
        this.f$0 = dismissCallbackWrapper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DismissCallbackWrapper dismissCallbackWrapper = this.f$0;
                dismissCallbackWrapper.getClass();
                try {
                    dismissCallbackWrapper.mCallback.onDismissCancelled();
                    break;
                } catch (RemoteException e) {
                    android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e);
                }
            default:
                DismissCallbackWrapper dismissCallbackWrapper2 = this.f$0;
                dismissCallbackWrapper2.getClass();
                try {
                    dismissCallbackWrapper2.mCallback.onDismissSucceeded();
                    break;
                } catch (RemoteException e2) {
                    android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e2);
                    return;
                }
        }
    }
}
