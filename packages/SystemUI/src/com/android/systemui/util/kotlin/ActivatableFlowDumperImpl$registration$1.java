package com.android.systemui.util.kotlin;

import com.android.systemui.lifecycle.ExclusiveActivatable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActivatableFlowDumperImpl$registration$1 extends ExclusiveActivatable {
    final /* synthetic */ ActivatableFlowDumperImpl this$0;

    public ActivatableFlowDumperImpl$registration$1(ActivatableFlowDumperImpl activatableFlowDumperImpl) {
        this.this$0 = activatableFlowDumperImpl;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object onActivated(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1$onActivated$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1$onActivated$1 r0 = (com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1$onActivated$1 r0 = new com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1$onActivated$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2b:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1 r5 = (com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1) r5
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L33
            goto L54
        L33:
            r6 = move-exception
            goto L5a
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl r6 = r5.this$0     // Catch: java.lang.Throwable -> L33
            com.android.systemui.dump.DumpManager r6 = com.android.systemui.util.kotlin.ActivatableFlowDumperImpl.access$getDumpManager$p(r6)     // Catch: java.lang.Throwable -> L33
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl r2 = r5.this$0     // Catch: java.lang.Throwable -> L33
            java.lang.String r2 = com.android.systemui.util.kotlin.ActivatableFlowDumperImpl.access$getDumpManagerName$p(r2)     // Catch: java.lang.Throwable -> L33
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl r4 = r5.this$0     // Catch: java.lang.Throwable -> L33
            r6.registerCriticalDumpable(r2, r4)     // Catch: java.lang.Throwable -> L33
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L33
            r0.label = r3     // Catch: java.lang.Throwable -> L33
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L33
            if (r6 != r1) goto L54
            return r1
        L54:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L33
            r6.<init>()     // Catch: java.lang.Throwable -> L33
            throw r6     // Catch: java.lang.Throwable -> L33
        L5a:
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl r0 = r5.this$0
            com.android.systemui.dump.DumpManager r0 = com.android.systemui.util.kotlin.ActivatableFlowDumperImpl.access$getDumpManager$p(r0)
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl r5 = r5.this$0
            java.lang.String r5 = com.android.systemui.util.kotlin.ActivatableFlowDumperImpl.access$getDumpManagerName$p(r5)
            r0.unregisterDumpable(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
