package com.android.systemui.unfold.updates;

import android.os.RemoteException;
import com.android.systemui.unfold.updates.RotationChangeProvider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class RotationChangeProvider$addCallback$1 implements Runnable {
    public final /* synthetic */ RotationChangeProvider.RotationListener $listener;
    public final /* synthetic */ RotationChangeProvider this$0;

    public RotationChangeProvider$addCallback$1(RotationChangeProvider rotationChangeProvider, RotationChangeProvider.RotationListener rotationListener) {
        this.this$0 = rotationChangeProvider;
        this.$listener = rotationListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.this$0.listeners.isEmpty()) {
            RotationChangeProvider rotationChangeProvider = this.this$0;
            rotationChangeProvider.getClass();
            try {
                rotationChangeProvider.displayManager.registerDisplayListener(rotationChangeProvider.displayListener, rotationChangeProvider.callbackHandler);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.this$0.listeners.add(this.$listener);
    }
}
