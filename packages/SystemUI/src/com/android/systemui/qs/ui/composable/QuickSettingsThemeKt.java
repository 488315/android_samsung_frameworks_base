package com.android.systemui.qs.ui.composable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class QuickSettingsThemeKt {
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0042, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void QuickSettingsTheme(final androidx.compose.runtime.internal.ComposableLambdaImpl r4, androidx.compose.runtime.Composer r5, final int r6) {
        /*
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            r0 = 500179343(0x1dd0218f, float:5.509184E-21)
            r5.startRestartGroup(r0)
            r0 = r6 & 3
            r1 = 2
            if (r0 != r1) goto L18
            boolean r0 = r5.getSkipping()
            if (r0 != 0) goto L14
            goto L18
        L14:
            r5.skipToGroupEnd()
            goto L73
        L18:
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L23
            java.lang.String r0 = "com.android.systemui.qs.ui.composable.QuickSettingsTheme (QuickSettingsTheme.kt:26)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L23:
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalContext
            java.lang.Object r1 = r5.consume(r0)
            android.content.Context r1 = (android.content.Context) r1
            r2 = 792583397(0x2f3ddce5, float:1.7267927E-10)
            r5.startReplaceGroup(r2)
            boolean r2 = r5.changed(r1)
            java.lang.Object r3 = r5.rememberedValue()
            if (r2 != 0) goto L44
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L4f
        L44:
            android.view.ContextThemeWrapper r3 = new android.view.ContextThemeWrapper
            r2 = 2132018953(0x7f140709, float:1.9676227E38)
            r3.<init>(r1, r2)
            r5.updateRememberedValue(r3)
        L4f:
            android.view.ContextThemeWrapper r3 = (android.view.ContextThemeWrapper) r3
            r1 = 0
            r5.end(r1)
            androidx.compose.runtime.ProvidedValue r0 = r0.defaultProvidedValue$runtime_release(r3)
            com.android.systemui.qs.ui.composable.QuickSettingsThemeKt$QuickSettingsTheme$1 r1 = new com.android.systemui.qs.ui.composable.QuickSettingsThemeKt$QuickSettingsTheme$1
            r1.<init>()
            r2 = -980863793(0xffffffffc58934cf, float:-4390.601)
            androidx.compose.runtime.internal.ComposableLambdaImpl r1 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r2, r1, r5)
            r2 = 56
            androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider(r0, r1, r5, r2)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L73
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L73:
            androidx.compose.runtime.RecomposeScopeImpl r5 = r5.endRestartGroup()
            if (r5 == 0) goto L80
            com.android.systemui.qs.ui.composable.QuickSettingsThemeKt$$ExternalSyntheticLambda0 r0 = new com.android.systemui.qs.ui.composable.QuickSettingsThemeKt$$ExternalSyntheticLambda0
            r0.<init>(r6)
            r5.block = r0
        L80:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.composable.QuickSettingsThemeKt.QuickSettingsTheme(androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int):void");
    }
}
