package com.android.systemui.bouncer.ui.binder;

import com.android.systemui.bouncer.ui.binder.KeyguardBouncerViewBinder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
final class KeyguardBouncerViewBinder$bind$1$1$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardBouncerViewBinder.AnonymousClass1.C01251.C01261.C01271 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBouncerViewBinder$bind$1$1$1$1$emit$1(KeyguardBouncerViewBinder.AnonymousClass1.C01251.C01261.C01271 c01271, Continuation continuation) {
        super(continuation);
        this.this$0 = c01271;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(false, (Continuation) this);
    }
}
