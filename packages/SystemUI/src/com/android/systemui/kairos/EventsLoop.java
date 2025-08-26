package com.android.systemui.kairos;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.CompletableLazy;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.NetworkScope;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class EventsLoop extends Events {
    public final CompletableLazy deferred;
    public final Init init;

    public EventsLoop() {
        super(null);
        this.deferred = new CompletableLazy(null, null, 3, null);
        this.init = new Init(null, new Function1() { // from class: com.android.systemui.kairos.EventsLoop$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return (EventsImpl) EventsKt.getInit((Events) this.f$0.deferred.getValue()).connect((NetworkScope) obj);
            }
        });
    }

    public final void setLoopback(Events events) {
        if (events != null) {
            CompletableLazy completableLazy = this.deferred;
            if (completableLazy.isInitialized()) {
                throw new IllegalStateException("EventsLoop.loopback has already been set.");
            }
            completableLazy.setValue(events);
        }
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(EventsLoop.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
