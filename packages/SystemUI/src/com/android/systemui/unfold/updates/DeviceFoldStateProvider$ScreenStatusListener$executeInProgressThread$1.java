package com.android.systemui.unfold.updates;

import kotlin.jvm.functions.Function0;

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
