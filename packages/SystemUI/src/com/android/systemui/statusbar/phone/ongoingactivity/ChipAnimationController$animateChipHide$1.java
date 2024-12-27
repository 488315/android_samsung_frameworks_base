package com.android.systemui.statusbar.phone.ongoingactivity;

import android.view.View;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Can't wrap try/catch for region: R(7:0|1|(4:(1:(1:(7:5|6|7|(5:19|(2:21|(1:23))(1:28)|24|25|26)|29|30|31)(2:35|36))(1:37))(2:52|(1:54)(1:55))|41|42|(1:44)(5:45|(8:9|11|16|19|(0)(0)|24|25|26)|29|30|31))|38|39|40|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00fe, code lost:
    
        r14 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00fc, code lost:
    
        r15 = r14;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00db A[Catch: all -> 0x0024, TryCatch #0 {all -> 0x0024, blocks: (B:7:0x001f, B:9:0x00ba, B:11:0x00c2, B:13:0x00c6, B:16:0x00cb, B:19:0x00d2, B:21:0x00db, B:23:0x00df, B:24:0x00e6, B:28:0x00e3, B:29:0x00ee), top: B:6:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00e3 A[Catch: all -> 0x0024, TryCatch #0 {all -> 0x0024, blocks: (B:7:0x001f, B:9:0x00ba, B:11:0x00c2, B:13:0x00c6, B:16:0x00cb, B:19:0x00d2, B:21:0x00db, B:23:0x00df, B:24:0x00e6, B:28:0x00e3, B:29:0x00ee), top: B:6:0x001f }] */
    /* JADX WARN: Type inference failed for: r9v3, types: [kotlinx.coroutines.sync.Mutex] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.ChipAnimationController$animateChipHide$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
