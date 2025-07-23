package com.android.systemui.keyguard;

import java.util.function.IntConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardVisibilityMonitor$registerMonitor$1$1 implements IntConsumer {
    public final /* synthetic */ KeyguardVisibilityMonitor $tmp0;

    public KeyguardVisibilityMonitor$registerMonitor$1$1(KeyguardVisibilityMonitor keyguardVisibilityMonitor) {
        this.$tmp0 = keyguardVisibilityMonitor;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.$tmp0.visibilityChanged(i);
    }
}
