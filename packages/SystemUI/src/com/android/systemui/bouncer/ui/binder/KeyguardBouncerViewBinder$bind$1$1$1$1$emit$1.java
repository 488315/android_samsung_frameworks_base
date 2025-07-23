package com.android.systemui.bouncer.ui.binder;

import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder$bind$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class KeyguardBouncerViewBinder$bind$1$1$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardBouncerViewBinder$bind$1.AnonymousClass1.C00681.C00691 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBouncerViewBinder$bind$1$1$1$1$emit$1(KeyguardBouncerViewBinder$bind$1.AnonymousClass1.C00681.C00691 c00691, Continuation continuation) {
        super(continuation);
        this.this$0 = c00691;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(false, (Continuation) this);
    }
}
