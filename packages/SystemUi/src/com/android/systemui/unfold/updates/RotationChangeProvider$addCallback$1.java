package com.android.systemui.unfold.updates;

import android.os.RemoteException;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RotationChangeProvider$addCallback$1 implements Runnable {
    public final /* synthetic */ RotationChangeProvider.RotationListener $listener;
    public final /* synthetic */ RotationChangeProvider this$0;

    public RotationChangeProvider$addCallback$1(RotationChangeProvider rotationChangeProvider, RotationChangeProvider.RotationListener rotationListener) {
        this.this$0 = rotationChangeProvider;
        this.$listener = rotationListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (((ArrayList) this.this$0.listeners).isEmpty()) {
            RotationChangeProvider rotationChangeProvider = this.this$0;
            rotationChangeProvider.getClass();
            try {
                rotationChangeProvider.displayManager.registerDisplayListener(rotationChangeProvider.displayListener, rotationChangeProvider.mainHandler);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.this$0.listeners.add(this.$listener);
    }
}
