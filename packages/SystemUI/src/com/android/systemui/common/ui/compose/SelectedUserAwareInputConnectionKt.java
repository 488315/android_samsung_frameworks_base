package com.android.systemui.common.ui.compose;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SelectedUserAwareInputConnectionKt {
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004a, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SelectedUserAwareInputConnection(final int r4, final androidx.compose.runtime.internal.ComposableLambdaImpl r5, androidx.compose.runtime.Composer r6, final int r7) {
        /*
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            r0 = 1182961154(0x46828e02, float:16711.004)
            r6.startRestartGroup(r0)
            boolean r0 = r6.changed(r4)
            r1 = 4
            if (r0 == 0) goto L11
            r0 = r1
            goto L12
        L11:
            r0 = 2
        L12:
            r0 = r0 | r7
            r2 = r0 & 19
            r3 = 18
            if (r2 != r3) goto L24
            boolean r2 = r6.getSkipping()
            if (r2 != 0) goto L20
            goto L24
        L20:
            r6.skipToGroupEnd()
            goto L73
        L24:
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L2f
            java.lang.String r2 = "com.android.systemui.common.ui.compose.SelectedUserAwareInputConnection (SelectedUserAwareInputConnection.kt:49)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r2)
        L2f:
            r2 = 1655794033(0x62b16971, float:1.6363358E21)
            r6.startReplaceGroup(r2)
            r0 = r0 & 14
            r2 = 0
            if (r0 != r1) goto L3c
            r0 = 1
            goto L3d
        L3c:
            r0 = r2
        L3d:
            java.lang.Object r1 = r6.rememberedValue()
            if (r0 != 0) goto L4c
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r0) goto L54
        L4c:
            com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1 r1 = new com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1
            r1.<init>(r4)
            r6.updateRememberedValue(r1)
        L54:
            androidx.compose.ui.platform.PlatformTextInputInterceptor r1 = (androidx.compose.ui.platform.PlatformTextInputInterceptor) r1
            r6.end(r2)
            com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$2 r0 = new com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$2
            r0.<init>()
            r2 = 1346161880(0x503cccd8, float:1.2670165E10)
            androidx.compose.runtime.internal.ComposableLambdaImpl r0 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r2, r0, r6)
            r2 = 48
            androidx.compose.ui.platform.PlatformTextInputModifierNodeKt.InterceptPlatformTextInput(r1, r0, r6, r2)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L73
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L73:
            androidx.compose.runtime.RecomposeScopeImpl r6 = r6.endRestartGroup()
            if (r6 == 0) goto L80
            com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$$ExternalSyntheticLambda0 r0 = new com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$$ExternalSyntheticLambda0
            r0.<init>(r4, r5, r7)
            r6.block = r0
        L80:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt.SelectedUserAwareInputConnection(int, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int):void");
    }
}
