package com.android.systemui.scene.data.repository;

import android.os.RemoteException;
import kotlin.jvm.functions.Function0;

public final class WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1 implements Runnable {
    public final /* synthetic */ Function0 $runnable;

    public WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1(Function0 function0) {
        this.$runnable = function0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.$runnable.invoke();
        } catch (RemoteException unused) {
        }
    }
}
