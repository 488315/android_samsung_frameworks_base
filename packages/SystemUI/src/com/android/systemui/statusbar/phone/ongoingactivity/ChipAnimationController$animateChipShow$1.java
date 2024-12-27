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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Can't wrap try/catch for region: R(6:0|1|(4:(1:(1:(9:5|6|7|(5:12|(2:14|(1:16))(2:21|(1:23)(1:24))|17|18|19)|25|(1:27)|28|29|30)(2:34|35))(1:36))(2:50|(1:52))|39|40|(1:42)(7:43|(6:9|12|(0)(0)|17|18|19)|25|(0)|28|29|30))|37|38|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00fb, code lost:
    
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00f9, code lost:
    
        r14 = r13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00b5 A[Catch: all -> 0x0027, TryCatch #1 {all -> 0x0027, blocks: (B:7:0x0022, B:9:0x00a6, B:12:0x00ad, B:14:0x00b5, B:16:0x00b9, B:17:0x00dc, B:21:0x00c1, B:23:0x00c9, B:24:0x00cd, B:25:0x00e4, B:27:0x00e8, B:28:0x00ed), top: B:6:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00c1 A[Catch: all -> 0x0027, TryCatch #1 {all -> 0x0027, blocks: (B:7:0x0022, B:9:0x00a6, B:12:0x00ad, B:14:0x00b5, B:16:0x00b9, B:17:0x00dc, B:21:0x00c1, B:23:0x00c9, B:24:0x00cd, B:25:0x00e4, B:27:0x00e8, B:28:0x00ed), top: B:6:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00e8 A[Catch: all -> 0x0027, TryCatch #1 {all -> 0x0027, blocks: (B:7:0x0022, B:9:0x00a6, B:12:0x00ad, B:14:0x00b5, B:16:0x00b9, B:17:0x00dc, B:21:0x00c1, B:23:0x00c9, B:24:0x00cd, B:25:0x00e4, B:27:0x00e8, B:28:0x00ed), top: B:6:0x0022 }] */
    /* JADX WARN: Type inference failed for: r10v4, types: [kotlinx.coroutines.sync.Mutex] */
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
