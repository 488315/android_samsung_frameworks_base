package com.android.systemui.statusbar.policy.ui.dialog.composable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ModeTileGridKt {
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0084, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ModeTileGrid(final com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel r18, androidx.compose.runtime.Composer r19, final int r20) {
        /*
            r0 = r18
            r1 = r20
            r2 = 0
            r13 = r19
            androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
            r3 = 708429859(0x2a39c823, float:1.6500737E-13)
            r13.startRestartGroup(r3)
            boolean r3 = r13.changedInstance(r0)
            r4 = 2
            if (r3 == 0) goto L18
            r3 = 4
            goto L19
        L18:
            r3 = r4
        L19:
            r3 = r3 | r1
            r3 = r3 & 3
            if (r3 != r4) goto L2a
            boolean r3 = r13.getSkipping()
            if (r3 != 0) goto L25
            goto L2a
        L25:
            r13.skipToGroupEnd()
            goto Lb1
        L2a:
            boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r3 == 0) goto L35
            java.lang.String r3 = "com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGrid (ModeTileGrid.kt:40)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r3)
        L35:
            kotlinx.coroutines.flow.Flow r3 = r0.tiles
            kotlin.collections.EmptyList r4 = kotlin.collections.EmptyList.INSTANCE
            r5 = 48
            androidx.compose.runtime.MutableState r3 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r3, r4, r13, r5)
            r4 = 1281722002(0x4c658692, float:6.0168776E7)
            r13.startReplaceGroup(r4)
            androidx.compose.foundation.lazy.grid.GridCells$Fixed r11 = new androidx.compose.foundation.lazy.grid.GridCells$Fixed
            r4 = 1
            r11.<init>(r4)
            androidx.compose.ui.Modifier$Companion r5 = androidx.compose.ui.Modifier.Companion
            r6 = 1065353216(0x3f800000, float:1.0)
            androidx.compose.ui.Modifier r5 = androidx.compose.foundation.layout.SizeKt.fillMaxWidth(r5, r6)
            r6 = 280(0x118, float:3.92E-43)
            float r6 = (float) r6
            androidx.compose.ui.unit.Dp$Companion r7 = androidx.compose.ui.unit.Dp.Companion
            r7 = 0
            androidx.compose.ui.Modifier r14 = androidx.compose.foundation.layout.SizeKt.m132heightInVpY3zN4$default(r5, r7, r6, r4)
            androidx.compose.foundation.layout.Arrangement r4 = androidx.compose.foundation.layout.Arrangement.INSTANCE
            r5 = 8
            float r5 = (float) r5
            r4.getClass()
            androidx.compose.foundation.layout.Arrangement$SpacedAligned r9 = androidx.compose.foundation.layout.Arrangement.m91spacedBy0680j_4(r5)
            androidx.compose.foundation.layout.Arrangement$SpacedAligned r8 = androidx.compose.foundation.layout.Arrangement.m91spacedBy0680j_4(r5)
            r4 = 1149733109(0x448788f5, float:1084.2799)
            r13.startReplaceGroup(r4)
            boolean r4 = r13.changed(r3)
            java.lang.Object r5 = r13.rememberedValue()
            if (r4 != 0) goto L86
            androidx.compose.runtime.Composer$Companion r4 = androidx.compose.runtime.Composer.Companion
            r4.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r4 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r4) goto L8e
        L86:
            com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt$$ExternalSyntheticLambda0 r5 = new com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt$$ExternalSyntheticLambda0
            r5.<init>(r3, r2)
            r13.updateRememberedValue(r5)
        L8e:
            r15 = r5
            kotlin.jvm.functions.Function1 r15 = (kotlin.jvm.functions.Function1) r15
            r13.end(r2)
            r6 = 0
            r3 = 1769520(0x1b0030, float:2.479626E-39)
            r12 = 0
            r10 = 0
            r16 = 0
            r7 = 0
            r17 = 0
            r4 = 0
            r5 = 924(0x39c, float:1.295E-42)
            androidx.compose.foundation.lazy.grid.LazyGridDslKt.LazyVerticalGrid(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            r13.end(r2)
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto Lb1
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lb1:
            androidx.compose.runtime.RecomposeScopeImpl r2 = r13.endRestartGroup()
            if (r2 == 0) goto Lbe
            com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt$$ExternalSyntheticLambda1 r3 = new com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt$$ExternalSyntheticLambda1
            r3.<init>(r1)
            r2.block = r3
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.ui.dialog.composable.ModeTileGridKt.ModeTileGrid(com.android.systemui.statusbar.policy.ui.dialog.viewmodel.ModesDialogViewModel, androidx.compose.runtime.Composer, int):void");
    }
}
