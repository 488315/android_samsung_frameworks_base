package com.android.systemui.mediaprojection.appselector.data;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ShellRecentTaskListProvider$loadRecentTasks$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShellRecentTaskListProvider this$0;

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

    /* JADX WARN: Code restructure failed: missing block: B:6:0x005c, code lost:
    
        if (r13 != null) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$loadRecentTasks$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
