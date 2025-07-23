package com.android.systemui.statusbar.phone.ongoingactivity;

import android.view.View;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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

    /* JADX WARN: Can't wrap try/catch for region: R(6:0|1|(5:(1:(1:(9:5|6|7|8|(1:10)|(5:15|(1:17)(2:22|(1:24)(1:25))|18|19|20)|26|27|28)(2:32|33))(1:34))(1:48)|37|38|(7:41|8|(0)|(6:12|15|(0)(0)|18|19|20)|26|27|28)|40)|35|36|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00fc, code lost:
    
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00fa, code lost:
    
        r14 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0060, code lost:
    
        if (r3.lock(r13) == r2) goto L20;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x00ac A[Catch: all -> 0x0027, TryCatch #0 {all -> 0x0027, blocks: (B:7:0x0022, B:8:0x00a4, B:10:0x00ac, B:12:0x00b3, B:15:0x00ba, B:17:0x00c2, B:18:0x00e5, B:22:0x00c6, B:24:0x00ce, B:25:0x00d2, B:26:0x00ed), top: B:6:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c2 A[Catch: all -> 0x0027, TryCatch #0 {all -> 0x0027, blocks: (B:7:0x0022, B:8:0x00a4, B:10:0x00ac, B:12:0x00b3, B:15:0x00ba, B:17:0x00c2, B:18:0x00e5, B:22:0x00c6, B:24:0x00ce, B:25:0x00d2, B:26:0x00ed), top: B:6:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00c6 A[Catch: all -> 0x0027, TryCatch #0 {all -> 0x0027, blocks: (B:7:0x0022, B:8:0x00a4, B:10:0x00ac, B:12:0x00b3, B:15:0x00ba, B:17:0x00c2, B:18:0x00e5, B:22:0x00c6, B:24:0x00ce, B:25:0x00d2, B:26:0x00ed), top: B:6:0x0022 }] */
    /* JADX WARN: Type inference failed for: r10v4, types: [kotlinx.coroutines.sync.Mutex] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateChipShow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
