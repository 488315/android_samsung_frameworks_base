package com.android.systemui.qs.panels.ui.compose;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class TileListenerKt {
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0065, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TileListener(final java.util.List r4, final kotlin.jvm.functions.Function0 r5, androidx.compose.runtime.Composer r6, final int r7) {
        /*
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            r0 = 1403942094(0x53ae74ce, float:1.4985679E12)
            r6.startRestartGroup(r0)
            r0 = r7 & 6
            if (r0 != 0) goto L17
            boolean r0 = r6.changedInstance(r4)
            if (r0 == 0) goto L14
            r0 = 4
            goto L15
        L14:
            r0 = 2
        L15:
            r0 = r0 | r7
            goto L18
        L17:
            r0 = r7
        L18:
            r1 = r7 & 48
            r2 = 32
            if (r1 != 0) goto L29
            boolean r1 = r6.changedInstance(r5)
            if (r1 == 0) goto L26
            r1 = r2
            goto L28
        L26:
            r1 = 16
        L28:
            r0 = r0 | r1
        L29:
            r1 = r0 & 19
            r3 = 18
            if (r1 != r3) goto L3a
            boolean r1 = r6.getSkipping()
            if (r1 != 0) goto L36
            goto L3a
        L36:
            r6.skipToGroupEnd()
            goto L81
        L3a:
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L45
            java.lang.String r1 = "com.android.systemui.qs.panels.ui.compose.TileListener (TileListener.kt:28)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
        L45:
            r1 = -305747494(0xffffffffedc6a9da, float:-7.685421E27)
            r6.startReplaceGroup(r1)
            r0 = r0 & 112(0x70, float:1.57E-43)
            r1 = 0
            if (r0 != r2) goto L52
            r0 = 1
            goto L53
        L52:
            r0 = r1
        L53:
            boolean r2 = r6.changedInstance(r4)
            r0 = r0 | r2
            java.lang.Object r2 = r6.rememberedValue()
            if (r0 != 0) goto L67
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r0) goto L70
        L67:
            com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1 r2 = new com.android.systemui.qs.panels.ui.compose.TileListenerKt$TileListener$1$1
            r0 = 0
            r2.<init>(r4, r5, r0)
            r6.updateRememberedValue(r2)
        L70:
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2
            r6.end(r1)
            androidx.compose.runtime.EffectsKt.LaunchedEffect(r6, r4, r2)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L81
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L81:
            androidx.compose.runtime.RecomposeScopeImpl r6 = r6.endRestartGroup()
            if (r6 == 0) goto L8e
            com.android.systemui.qs.panels.ui.compose.TileListenerKt$$ExternalSyntheticLambda0 r0 = new com.android.systemui.qs.panels.ui.compose.TileListenerKt$$ExternalSyntheticLambda0
            r0.<init>()
            r6.block = r0
        L8e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.TileListenerKt.TileListener(java.util.List, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):void");
    }
}
