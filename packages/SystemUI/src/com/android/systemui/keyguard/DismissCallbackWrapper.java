package com.android.systemui.keyguard;

import android.os.RemoteException;
import com.android.internal.policy.IKeyguardDismissCallback;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DismissCallbackWrapper {
    public final IKeyguardDismissCallback mCallback;

    public DismissCallbackWrapper(IKeyguardDismissCallback iKeyguardDismissCallback) {
        this.mCallback = iKeyguardDismissCallback;
    }

    public final void notifyDismissError() {
        try {
            this.mCallback.onDismissError();
        } catch (RemoteException e) {
            android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e);
        }
    }
}
