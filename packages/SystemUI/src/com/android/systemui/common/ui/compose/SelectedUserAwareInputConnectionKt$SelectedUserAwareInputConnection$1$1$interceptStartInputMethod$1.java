package com.android.systemui.common.ui.compose;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1$interceptStartInputMethod$1(SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1 selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1, Continuation continuation) {
        super(continuation);
        this.this$0 = selectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.interceptStartInputMethod(null, null, this);
    }
}
