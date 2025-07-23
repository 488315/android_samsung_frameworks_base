package com.android.systemui.util.kotlin;

import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActivatableFlowDumperImpl extends SimpleFlowDumper implements ActivatableFlowDumper {
    public static final int $stable = 8;
    private final DumpManager dumpManager;
    private final String dumpManagerName;
    private final ActivatableFlowDumperImpl$registration$1 registration = new ActivatableFlowDumperImpl$registration$1(this);

    public ActivatableFlowDumperImpl(DumpManager dumpManager, String str) {
        this.dumpManager = dumpManager;
        this.dumpManagerName = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("[", getIdString(this), "] ", str);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.util.kotlin.ActivatableFlowDumper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object activateFlowDumper(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$activateFlowDumper$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$activateFlowDumper$1 r0 = (com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$activateFlowDumper$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$activateFlowDumper$1 r0 = new com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$activateFlowDumper$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L3d
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.util.kotlin.ActivatableFlowDumperImpl$registration$1 r4 = r4.registration
            r0.label = r3
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L3d
            return r1
        L3d:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.ActivatableFlowDumperImpl.activateFlowDumper(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
