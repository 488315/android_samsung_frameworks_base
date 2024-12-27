package com.android.systemui.unfold.updates;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceFoldStateProvider$ScreenStatusListener$executeInProgressThread$1 implements Runnable {
    public final /* synthetic */ Function0 $f;

    public DeviceFoldStateProvider$ScreenStatusListener$executeInProgressThread$1(Function0 function0) {
        this.$f = function0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$f.invoke();
    }
}
