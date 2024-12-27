package com.android.systemui.keyguard;

import android.os.RemoteException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
