package com.android.systemui.statusbar.phone;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
final class LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ LegacyActivityStarterInternalImpl$registerTransition$factory$1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1(LegacyActivityStarterInternalImpl$registerTransition$factory$1 legacyActivityStarterInternalImpl$registerTransition$factory$1, Continuation continuation) {
        super(continuation);
        this.this$0 = legacyActivityStarterInternalImpl$registerTransition$factory$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.createController(false, this);
    }
}
