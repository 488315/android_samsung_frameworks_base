package com.android.systemui.haptics.slider;

import com.android.systemui.haptics.slider.SliderTracker;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class SliderTracker$startTracking$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SliderTracker.AnonymousClass1.C02011 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SliderTracker$startTracking$1$1$emit$1(SliderTracker.AnonymousClass1.C02011 c02011, Continuation continuation) {
        super(continuation);
        this.this$0 = c02011;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((SliderEvent) null, (Continuation) this);
    }
}
