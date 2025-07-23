package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.Maybe;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScheduledAction {
    public final CompletableDeferred onResult;
    public final Function2 onStartTransaction;
    public Maybe result;

    public ScheduledAction(String str, CompletableDeferred completableDeferred, Function2 function2) {
        this.onResult = completableDeferred;
        this.onStartTransaction = function2;
        Maybe.Companion.getClass();
        this.result = Maybe.Companion.absent;
    }

    public final void completed() {
        CompletableDeferred completableDeferred = this.onResult;
        if (completableDeferred != null) {
            Maybe maybe = this.result;
            if (maybe instanceof Maybe.Present) {
                ((CompletableDeferredImpl) completableDeferred).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(((Maybe.Present) maybe).value);
            }
        }
        Maybe.Companion.getClass();
        this.result = Maybe.Companion.absent;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object started(com.android.systemui.kairos.internal.EvalScope r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.kairos.internal.ScheduledAction$started$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.kairos.internal.ScheduledAction$started$1 r0 = (com.android.systemui.kairos.internal.ScheduledAction$started$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.kairos.internal.ScheduledAction$started$1 r0 = new com.android.systemui.kairos.internal.ScheduledAction$started$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r5 = r0.L$1
            com.android.systemui.kairos.util.Maybe$Companion r5 = (com.android.systemui.kairos.util.Maybe.Companion) r5
            java.lang.Object r6 = r0.L$0
            com.android.systemui.kairos.internal.ScheduledAction r6 = (com.android.systemui.kairos.internal.ScheduledAction) r6
            kotlin.ResultKt.throwOnFailure(r7)
            r4 = r7
            r7 = r5
            r5 = r6
            r6 = r4
            goto L4f
        L33:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.kairos.util.Maybe$Companion r7 = com.android.systemui.kairos.util.Maybe.Companion
            r0.L$0 = r5
            r0.L$1 = r7
            r0.label = r3
            kotlin.jvm.functions.Function2 r2 = r5.onStartTransaction
            java.lang.Object r6 = r2.invoke(r6, r0)
            if (r6 != r1) goto L4f
            return r1
        L4f:
            r7.getClass()
            com.android.systemui.kairos.util.Maybe$Present r6 = com.android.systemui.kairos.util.Maybe.Present.m2573boximpl(r6)
            r5.result = r6
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.ScheduledAction.started(com.android.systemui.kairos.internal.EvalScope, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public /* synthetic */ ScheduledAction(String str, CompletableDeferred completableDeferred, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : completableDeferred, function2);
    }
}
