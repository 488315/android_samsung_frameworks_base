package com.android.systemui.keyguard;

import android.os.RemoteException;
import com.android.internal.policy.IKeyguardDismissCallback;

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
