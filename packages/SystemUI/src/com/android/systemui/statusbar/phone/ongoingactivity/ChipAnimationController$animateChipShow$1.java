package com.android.systemui.statusbar.phone.ongoingactivity;

import android.view.View;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ChipAnimationController$animateChipShow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $animate;
    final /* synthetic */ View $v;
    Object L$0;
    Object L$1;
    Object L$2;
    boolean Z$0;
    int label;
    final /* synthetic */ ChipAnimationController this$0;

    public ChipAnimationController$animateChipShow$1(ChipAnimationController chipAnimationController, View view, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = chipAnimationController;
        this.$v = view;
        this.$animate = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ChipAnimationController$animateChipShow$1(this.this$0, this.$v, this.$animate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ChipAnimationController$animateChipShow$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00fb, code lost:
    
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00f9, code lost:
    
        r14 = r13;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateChipShow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
