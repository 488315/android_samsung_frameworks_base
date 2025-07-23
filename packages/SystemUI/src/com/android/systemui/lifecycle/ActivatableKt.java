package com.android.systemui.lifecycle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ActivatableKt {
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0053, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x002c, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final com.android.systemui.lifecycle.Activatable rememberActivated(java.lang.String r4, kotlin.jvm.functions.Function0 r5, androidx.compose.runtime.Composer r6) {
        /*
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            r0 = -317888658(0xffffffffed0d676e, float:-2.7351516E27)
            r6.startReplaceGroup(r0)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L15
            java.lang.String r1 = "com.android.systemui.lifecycle.rememberActivated (Activatable.kt:80)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
        L15:
            r1 = 1312068444(0x4e34935c, float:7.5738906E8)
            r6.startReplaceGroup(r1)
            boolean r0 = r6.changed(r0)
            java.lang.Object r1 = r6.rememberedValue()
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            if (r0 != 0) goto L2e
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r0) goto L38
        L2e:
            java.lang.Object r5 = r5.invoke()
            r1 = r5
            com.android.systemui.lifecycle.Activatable r1 = (com.android.systemui.lifecycle.Activatable) r1
            r6.updateRememberedValue(r1)
        L38:
            com.android.systemui.lifecycle.Activatable r1 = (com.android.systemui.lifecycle.Activatable) r1
            r5 = 0
            r6.end(r5)
            r0 = 1312070294(0x4e349a96, float:7.5750746E8)
            r6.startReplaceGroup(r0)
            boolean r0 = r6.changedInstance(r1)
            java.lang.Object r3 = r6.rememberedValue()
            if (r0 != 0) goto L55
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r0) goto L5e
        L55:
            com.android.systemui.lifecycle.ActivatableKt$rememberActivated$1$1 r3 = new com.android.systemui.lifecycle.ActivatableKt$rememberActivated$1$1
            r0 = 0
            r3.<init>(r4, r1, r0)
            r6.updateRememberedValue(r3)
        L5e:
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3
            r6.end(r5)
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r6, r1, r3)
            boolean r4 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r4 == 0) goto L6f
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L6f:
            r6.end(r5)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.ActivatableKt.rememberActivated(java.lang.String, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer):com.android.systemui.lifecycle.Activatable");
    }
}
