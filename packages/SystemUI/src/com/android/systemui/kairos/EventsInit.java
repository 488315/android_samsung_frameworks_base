package com.android.systemui.kairos;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class EventsInit extends Events {
    public final Init init;

    public EventsInit(Init init) {
        super(null);
        this.init = init;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(EventsInit.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
