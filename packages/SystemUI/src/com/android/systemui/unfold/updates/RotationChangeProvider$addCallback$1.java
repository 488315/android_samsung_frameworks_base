package com.android.systemui.unfold.updates;

import android.os.RemoteException;
import com.android.systemui.unfold.updates.RotationChangeProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
