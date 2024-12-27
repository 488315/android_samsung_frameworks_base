package com.android.systemui.keyguard.ui.preview;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class KeyguardPreviewRenderer$fetchThemeStyleFromSetting$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardPreviewRenderer this$0;

    public KeyguardPreviewRenderer$fetchThemeStyleFromSetting$1(KeyguardPreviewRenderer keyguardPreviewRenderer, Continuation continuation) {
        super(continuation);
        this.this$0 = keyguardPreviewRenderer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        KeyguardPreviewRenderer keyguardPreviewRenderer = this.this$0;
        int i = KeyguardPreviewRenderer.$r8$clinit;
        return keyguardPreviewRenderer.fetchThemeStyleFromSetting(this);
    }
}
