package com.android.systemui.bouncer.ui.helper;

import dagger.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerHapticPlayer {
    public final Lazy msdlPlayer;

    public BouncerHapticPlayer(Lazy lazy) {
        this.msdlPlayer = lazy;
        LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new BouncerHapticPlayer$$ExternalSyntheticLambda0());
    }
}
