package com.android.systemui.mediaprojection.appselector.data;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ShellRecentTaskListProvider$loadRecentTasks$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShellRecentTaskListProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShellRecentTaskListProvider$loadRecentTasks$2(ShellRecentTaskListProvider shellRecentTaskListProvider, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shellRecentTaskListProvider;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShellRecentTaskListProvider$loadRecentTasks$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShellRecentTaskListProvider$loadRecentTasks$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x005a, code lost:
    
        if (r14 != null) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0195  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$loadRecentTasks$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
