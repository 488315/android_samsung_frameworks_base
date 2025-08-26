package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
final class PinBouncerViewModel$onActivated$2$2$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PinBouncerViewModel.AnonymousClass2.C01562.AnonymousClass1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinBouncerViewModel$onActivated$2$2$1$emit$1(PinBouncerViewModel.AnonymousClass2.C01562.AnonymousClass1 anonymousClass1, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((PinBouncerViewModel.Request) null, (Continuation) this);
    }
}
