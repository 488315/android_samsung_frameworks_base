package com.android.systemui.communal.ui.compose.section;

import com.android.systemui.ambient.statusbar.dagger.AmbientStatusBarComponent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AmbientStatusBarSection {
    public final AmbientStatusBarComponent.Factory factory;

    public AmbientStatusBarSection(AmbientStatusBarComponent.Factory factory) {
        this.factory = factory;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0057, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void AmbientStatusBar(final com.android.compose.animation.scene.ContentScope r7, final androidx.compose.ui.Modifier r8, androidx.compose.runtime.Composer r9, final int r10) {
        /*
            r6 = this;
            r3 = r9
            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
            r9 = -1461877625(0xffffffffa8dd8487, float:-2.4593403E-14)
            r3.startRestartGroup(r9)
            boolean r9 = r3.changed(r7)
            if (r9 == 0) goto L11
            r9 = 4
            goto L12
        L11:
            r9 = 2
        L12:
            r9 = r9 | r10
            boolean r0 = r3.changed(r6)
            r1 = 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L1d
            r0 = r1
            goto L1f
        L1d:
            r0 = 128(0x80, float:1.8E-43)
        L1f:
            r9 = r9 | r0
            r0 = r9 & 147(0x93, float:2.06E-43)
            r2 = 146(0x92, float:2.05E-43)
            if (r0 != r2) goto L31
            boolean r0 = r3.getSkipping()
            if (r0 != 0) goto L2d
            goto L31
        L2d:
            r3.skipToGroupEnd()
            goto L82
        L31:
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L3c
            java.lang.String r0 = "com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection.AmbientStatusBar (AmbientStatusBarSection.kt:35)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L3c:
            r0 = -1714590095(0xffffffff99cd6e71, float:-2.124111E-23)
            r3.startReplaceGroup(r0)
            r9 = r9 & 896(0x380, float:1.256E-42)
            r0 = 0
            if (r9 == r1) goto L49
            r9 = r0
            goto L4a
        L49:
            r9 = 1
        L4a:
            java.lang.Object r1 = r3.rememberedValue()
            if (r9 != 0) goto L59
            androidx.compose.runtime.Composer$Companion r9 = androidx.compose.runtime.Composer.Companion
            r9.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r9 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r9) goto L61
        L59:
            com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$$ExternalSyntheticLambda0 r1 = new com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$$ExternalSyntheticLambda0
            r1.<init>()
            r3.updateRememberedValue(r1)
        L61:
            kotlin.jvm.functions.Function1 r1 = (kotlin.jvm.functions.Function1) r1
            r3.end(r0)
            com.android.systemui.communal.ui.compose.Communal$Elements r9 = com.android.systemui.communal.ui.compose.Communal$Elements.INSTANCE
            r9.getClass()
            com.android.compose.animation.scene.ElementKey r9 = com.android.systemui.communal.ui.compose.Communal$Elements.StatusBar
            androidx.compose.ui.Modifier r9 = r7.element(r8, r9)
            r5 = 4
            r2 = 0
            r4 = 0
            r0 = r1
            r1 = r9
            androidx.compose.ui.viewinterop.AndroidView_androidKt.AndroidView(r0, r1, r2, r3, r4, r5)
            boolean r9 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r9 == 0) goto L82
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L82:
            androidx.compose.runtime.RecomposeScopeImpl r9 = r3.endRestartGroup()
            if (r9 == 0) goto L8f
            com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$$ExternalSyntheticLambda1 r0 = new com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$$ExternalSyntheticLambda1
            r0.<init>(r7, r8, r10)
            r9.block = r0
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection.AmbientStatusBar(com.android.compose.animation.scene.ContentScope, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }
}
