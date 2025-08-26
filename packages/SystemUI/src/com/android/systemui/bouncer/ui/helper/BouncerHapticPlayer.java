package com.android.systemui.bouncer.ui.helper;

import dagger.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;

/* loaded from: classes.dex */
public final class BouncerHapticPlayer {
    public final Lazy msdlPlayer;

    public BouncerHapticPlayer(Lazy lazy) {
        this.msdlPlayer = lazy;
        LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new BouncerHapticPlayer$$ExternalSyntheticLambda0());
    }
}
