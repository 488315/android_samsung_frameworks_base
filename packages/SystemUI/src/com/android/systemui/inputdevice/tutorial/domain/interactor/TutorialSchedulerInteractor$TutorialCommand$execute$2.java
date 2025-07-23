package com.android.systemui.inputdevice.tutorial.domain.interactor;

import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TutorialSchedulerInteractor$TutorialCommand$execute$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ PrintWriter $pw;
    Object L$0;
    int label;
    final /* synthetic */ TutorialSchedulerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TutorialSchedulerInteractor$TutorialCommand$execute$2(PrintWriter printWriter, TutorialSchedulerInteractor tutorialSchedulerInteractor, Continuation continuation) {
        super(2, continuation);
        this.$pw = printWriter;
        this.this$0 = tutorialSchedulerInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TutorialSchedulerInteractor$TutorialCommand$execute$2(this.$pw, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSchedulerInteractor$TutorialCommand$execute$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00bb, code lost:
    
        if (r7 == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a2, code lost:
    
        if (r7 == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x008b, code lost:
    
        if (r7 == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
    
        if (r7 == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005a, code lost:
    
        if (r7 == r0) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialCommand$execute$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
