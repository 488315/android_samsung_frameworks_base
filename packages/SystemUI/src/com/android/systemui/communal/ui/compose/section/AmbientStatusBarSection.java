package com.android.systemui.communal.ui.compose.section;

import com.android.systemui.ambient.statusbar.dagger.AmbientStatusBarComponent;

public final class AmbientStatusBarSection {
    public final AmbientStatusBarComponent.Factory factory;

    public AmbientStatusBarSection(AmbientStatusBarComponent.Factory factory) {
        this.factory = factory;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void AmbientStatusBar(final com.android.compose.animation.scene.SceneScope r12, androidx.compose.ui.Modifier r13, androidx.compose.runtime.Composer r14, final int r15, final int r16) {
        /*
            r11 = this;
            r1 = r11
            r2 = r12
            r4 = r15
            r0 = 2
            r3 = r14
            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
            r5 = -1870160236(0xffffffff90879e94, float:-5.349244E-29)
            r3.startRestartGroup(r5)
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r16 & r5
            if (r5 == 0) goto L16
            r5 = r4 | 6
            goto L26
        L16:
            r5 = r4 & 14
            if (r5 != 0) goto L25
            boolean r5 = r3.changed(r12)
            if (r5 == 0) goto L22
            r5 = 4
            goto L23
        L22:
            r5 = r0
        L23:
            r5 = r5 | r4
            goto L26
        L25:
            r5 = r4
        L26:
            r6 = r16 & 1
            if (r6 == 0) goto L2e
            r5 = r5 | 48
        L2c:
            r7 = r13
            goto L3f
        L2e:
            r7 = r4 & 112(0x70, float:1.57E-43)
            if (r7 != 0) goto L2c
            r7 = r13
            boolean r8 = r3.changed(r13)
            if (r8 == 0) goto L3c
            r8 = 32
            goto L3e
        L3c:
            r8 = 16
        L3e:
            r5 = r5 | r8
        L3f:
            r0 = r16 & 2
            if (r0 == 0) goto L46
            r5 = r5 | 384(0x180, float:5.38E-43)
            goto L56
        L46:
            r0 = r4 & 896(0x380, float:1.256E-42)
            if (r0 != 0) goto L56
            boolean r0 = r3.changed(r11)
            if (r0 == 0) goto L53
            r0 = 256(0x100, float:3.59E-43)
            goto L55
        L53:
            r0 = 128(0x80, float:1.794E-43)
        L55:
            r5 = r5 | r0
        L56:
            r0 = r5 & 731(0x2db, float:1.024E-42)
            r5 = 146(0x92, float:2.05E-43)
            if (r0 != r5) goto L67
            boolean r0 = r3.getSkipping()
            if (r0 != 0) goto L63
            goto L67
        L63:
            r3.skipToGroupEnd()
            goto L87
        L67:
            if (r6 == 0) goto L6c
            androidx.compose.ui.Modifier$Companion r0 = androidx.compose.ui.Modifier.Companion
            goto L6d
        L6c:
            r0 = r7
        L6d:
            androidx.compose.runtime.OpaqueKey r5 = androidx.compose.runtime.ComposerKt.invocation
            com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$AmbientStatusBar$1 r5 = new com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$AmbientStatusBar$1
            r5.<init>()
            com.android.systemui.communal.ui.compose.Communal$Elements r6 = com.android.systemui.communal.ui.compose.Communal$Elements.INSTANCE
            r6.getClass()
            com.android.compose.animation.scene.ElementKey r6 = com.android.systemui.communal.ui.compose.Communal$Elements.StatusBar
            androidx.compose.ui.Modifier r6 = r12.element(r0, r6)
            r10 = 4
            r7 = 0
            r9 = 0
            r8 = r3
            androidx.compose.ui.viewinterop.AndroidView_androidKt.AndroidView(r5, r6, r7, r8, r9, r10)
            r7 = r0
        L87:
            androidx.compose.runtime.RecomposeScopeImpl r6 = r3.endRestartGroup()
            if (r6 == 0) goto L9b
            com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$AmbientStatusBar$2 r8 = new com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$AmbientStatusBar$2
            r0 = r8
            r1 = r11
            r2 = r12
            r3 = r7
            r4 = r15
            r5 = r16
            r0.<init>()
            r6.block = r8
        L9b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection.AmbientStatusBar(com.android.compose.animation.scene.SceneScope, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }
}
