package com.android.systemui.kairos;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StateInit extends State {
    public final Init init;

    public StateInit(Init init) {
        super(null);
        this.init = init;
    }

    @Override // com.android.systemui.kairos.State
    public final Init getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos() {
        return this.init;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(StateInit.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
