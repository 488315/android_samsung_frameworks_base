package com.android.systemui.keyboard.shortcut.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1 extends ContinuationImpl {
    Object L$0;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ShortcutCustomizationViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutCustomizationViewModel$getErrorMessageForPressedKeys$1(ShortcutCustomizationViewModel shortcutCustomizationViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = shortcutCustomizationViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ShortcutCustomizationViewModel.access$getErrorMessageForPressedKeys(this.this$0, null, this);
    }
}
