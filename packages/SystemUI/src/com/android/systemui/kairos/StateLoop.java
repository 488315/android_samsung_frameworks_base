package com.android.systemui.kairos;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.CompletableLazy;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.NetworkScope;
import com.android.systemui.kairos.internal.StateImpl;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

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
            public final Object mo781invoke(Object obj) {
                return (StateImpl) ((State) this.f$0.deferred.getValue()).getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect((NetworkScope) obj);
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
