package com.android.systemui.statusbar.phone.ongoingactivity;

import android.view.View;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ChipAnimationController$animateChipHide$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $animate;
    final /* synthetic */ int $state;
    final /* synthetic */ View $v;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    boolean Z$0;
    int label;
    final /* synthetic */ ChipAnimationController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChipAnimationController$animateChipHide$1(ChipAnimationController chipAnimationController, View view, int i, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = chipAnimationController;
        this.$v = view;
        this.$state = i;
        this.$animate = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ChipAnimationController$animateChipHide$1(this.this$0, this.$v, this.$state, this.$animate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ChipAnimationController$animateChipHide$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00d3 A[Catch: all -> 0x0024, TryCatch #1 {all -> 0x0024, blocks: (B:7:0x001f, B:9:0x00ba, B:11:0x00be, B:14:0x00c3, B:17:0x00ca, B:19:0x00d3, B:20:0x00da, B:24:0x00d7, B:25:0x00e2), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00d7 A[Catch: all -> 0x0024, TryCatch #1 {all -> 0x0024, blocks: (B:7:0x001f, B:9:0x00ba, B:11:0x00be, B:14:0x00c3, B:17:0x00ca, B:19:0x00d3, B:20:0x00da, B:24:0x00d7, B:25:0x00e2), top: B:6:0x001f }] */
    /* JADX WARN: Type inference failed for: r9v3, types: [kotlinx.coroutines.sync.Mutex] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateChipHide$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
