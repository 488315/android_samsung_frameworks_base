package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.Maybe;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BuildScopeImpl$observe$handle$1 implements DisposableHandle {
    public final /* synthetic */ Ref$ObjectRef $cancelHandle;
    public final /* synthetic */ AtomicReference $subRef;
    public final /* synthetic */ BuildScopeImpl this$0;

    public BuildScopeImpl$observe$handle$1(Ref$ObjectRef<DisposableHandle> ref$ObjectRef, AtomicReference<Maybe> atomicReference, BuildScopeImpl buildScopeImpl) {
        this.$cancelHandle = ref$ObjectRef;
        this.$subRef = atomicReference;
        this.this$0 = buildScopeImpl;
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        T t = this.$cancelHandle.element;
        (t == 0 ? null : (DisposableHandle) t).dispose();
        Maybe maybe = (Maybe) this.$subRef.getAndSet(Maybe.Absent.INSTANCE);
        if (maybe == null || !(maybe instanceof Maybe.Present)) {
            return;
        }
        this.this$0.getNetwork().transaction("observeEffect cancelled", new BuildScopeImpl$observe$handle$1$1$1(maybe, null));
    }
}
