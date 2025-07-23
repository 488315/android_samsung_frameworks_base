package com.android.systemui.keyboard.shortcut.data.source;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class InputShortcutsSource$shortcutGroups$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ InputShortcutsSource this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InputShortcutsSource$shortcutGroups$1(InputShortcutsSource inputShortcutsSource, Continuation continuation) {
        super(continuation);
        this.this$0 = inputShortcutsSource;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.shortcutGroups(0, this);
    }
}
