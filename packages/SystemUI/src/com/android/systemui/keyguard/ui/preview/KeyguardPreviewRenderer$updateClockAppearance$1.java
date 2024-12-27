package com.android.systemui.keyguard.ui.preview;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class KeyguardPreviewRenderer$updateClockAppearance$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardPreviewRenderer this$0;

    public KeyguardPreviewRenderer$updateClockAppearance$1(KeyguardPreviewRenderer keyguardPreviewRenderer, Continuation continuation) {
        super(continuation);
        this.this$0 = keyguardPreviewRenderer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return KeyguardPreviewRenderer.access$updateClockAppearance(this.this$0, null, this);
    }
}
