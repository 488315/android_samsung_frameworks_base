package com.android.systemui.kairos;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.CompletableLazy;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.NetworkScope;
import com.android.systemui.kairos.internal.StateImpl;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StateLoop extends State {
    public final CompletableLazy deferred;
    public final Init init;

    public StateLoop() {
        super(null);
        this.deferred = new CompletableLazy(null, null, 3, null);
        this.init = new Init(null, new Function1() { // from class: com.android.systemui.kairos.StateLoop$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return (StateImpl) ((State) StateLoop.this.deferred.getValue()).getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect((NetworkScope) obj);
            }
        });
    }

    @Override // com.android.systemui.kairos.State
    public final Init getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos() {
        return this.init;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(StateLoop.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
