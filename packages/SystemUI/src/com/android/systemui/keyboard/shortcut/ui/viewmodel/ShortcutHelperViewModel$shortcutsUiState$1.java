package com.android.systemui.keyboard.shortcut.ui.viewmodel;

import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
final class ShortcutHelperViewModel$shortcutsUiState$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ ShortcutHelperViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperViewModel$shortcutsUiState$1(ShortcutHelperViewModel shortcutHelperViewModel, Continuation continuation) {
        super(4, continuation);
        this.this$0 = shortcutHelperViewModel;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        ShortcutHelperViewModel$shortcutsUiState$1 shortcutHelperViewModel$shortcutsUiState$1 = new ShortcutHelperViewModel$shortcutsUiState$1(this.this$0, (Continuation) obj4);
        shortcutHelperViewModel$shortcutsUiState$1.L$0 = (String) obj;
        shortcutHelperViewModel$shortcutsUiState$1.L$1 = (List) obj2;
        shortcutHelperViewModel$shortcutsUiState$1.Z$0 = zBooleanValue;
        return shortcutHelperViewModel$shortcutsUiState$1.invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException
        */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object r53) {
        /*
            Method dump skipped, instructions count: 2671
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$shortcutsUiState$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
