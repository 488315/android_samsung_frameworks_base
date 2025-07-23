package com.android.systemui.util.composable.kairos;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RememberKairosActivatableKt {
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0047, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0072, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T extends com.android.systemui.KairosActivatable> T rememberKairosActivatable(com.android.systemui.kairos.KairosNetwork r2, java.lang.Object r3, kotlin.jvm.functions.Function0 r4, androidx.compose.runtime.Composer r5, int r6, int r7) {
        /*
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            r0 = 1141740364(0x440d934c, float:566.3015)
            r5.startReplaceGroup(r0)
            r7 = r7 & 2
            if (r7 == 0) goto Le
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
        Le:
            boolean r7 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r7 == 0) goto L19
            java.lang.String r7 = "com.android.systemui.util.composable.kairos.rememberKairosActivatable (RememberKairosActivatable.kt:31)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r7)
        L19:
            r7 = -991251959(0xffffffffc4eab209, float:-1877.5636)
            r5.startReplaceGroup(r7)
            boolean r3 = r5.changed(r3)
            r7 = r6 & 896(0x380, float:1.256E-42)
            r7 = r7 ^ 384(0x180, float:5.38E-43)
            r0 = 256(0x100, float:3.59E-43)
            r1 = 0
            if (r7 <= r0) goto L32
            boolean r7 = r5.changed(r4)
            if (r7 != 0) goto L36
        L32:
            r6 = r6 & 384(0x180, float:5.38E-43)
            if (r6 != r0) goto L38
        L36:
            r6 = 1
            goto L39
        L38:
            r6 = r1
        L39:
            r3 = r3 | r6
            java.lang.Object r6 = r5.rememberedValue()
            androidx.compose.runtime.Composer$Companion r7 = androidx.compose.runtime.Composer.Companion
            if (r3 != 0) goto L49
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r6 != r3) goto L53
        L49:
            java.lang.Object r3 = r4.invoke()
            r6 = r3
            com.android.systemui.KairosActivatable r6 = (com.android.systemui.KairosActivatable) r6
            r5.updateRememberedValue(r6)
        L53:
            com.android.systemui.KairosActivatable r6 = (com.android.systemui.KairosActivatable) r6
            r5.end(r1)
            r3 = -991249329(0xffffffffc4eabc4f, float:-1877.8846)
            r5.startReplaceGroup(r3)
            boolean r3 = r5.changedInstance(r2)
            boolean r4 = r5.changedInstance(r6)
            r3 = r3 | r4
            java.lang.Object r4 = r5.rememberedValue()
            if (r3 != 0) goto L74
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r4 != r3) goto L7d
        L74:
            com.android.systemui.util.composable.kairos.RememberKairosActivatableKt$rememberKairosActivatable$1$1 r4 = new com.android.systemui.util.composable.kairos.RememberKairosActivatableKt$rememberKairosActivatable$1$1
            r3 = 0
            r4.<init>(r2, r6, r3)
            r5.updateRememberedValue(r4)
        L7d:
            kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
            r5.end(r1)
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r6, r2, r4, r5)
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L8e
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L8e:
            r5.end(r1)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.composable.kairos.RememberKairosActivatableKt.rememberKairosActivatable(com.android.systemui.kairos.KairosNetwork, java.lang.Object, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int):com.android.systemui.KairosActivatable");
    }
}
