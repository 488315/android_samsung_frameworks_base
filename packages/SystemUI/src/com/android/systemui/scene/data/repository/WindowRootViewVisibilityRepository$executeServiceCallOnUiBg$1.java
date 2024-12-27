package com.android.systemui.scene.data.repository;

import android.os.RemoteException;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
