package com.android.systemui.keyguard.ui.preview;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardPreviewRenderer$updateClockAppearance$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardPreviewRenderer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
