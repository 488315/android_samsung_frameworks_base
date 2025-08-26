package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder;
import kotlin.Pair;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class KeyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardPreviewClockViewBinder.AnonymousClass3.AnonymousClass1.C02921.AnonymousClass4 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1(KeyguardPreviewClockViewBinder.AnonymousClass3.AnonymousClass1.C02921.AnonymousClass4 anonymousClass4, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((Pair) null, (Continuation) this);
    }
}
