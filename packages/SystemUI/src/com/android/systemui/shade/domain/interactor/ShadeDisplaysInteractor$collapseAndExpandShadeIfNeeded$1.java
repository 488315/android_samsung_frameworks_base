package com.android.systemui.shade.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
final class ShadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1 extends ContinuationImpl {
    int I$0;
    int I$1;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ShadeDisplaysInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplaysInteractor$collapseAndExpandShadeIfNeeded$1(ShadeDisplaysInteractor shadeDisplaysInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = shadeDisplaysInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ShadeDisplaysInteractor.access$collapseAndExpandShadeIfNeeded(this.this$0, 0, null, this);
    }
}
