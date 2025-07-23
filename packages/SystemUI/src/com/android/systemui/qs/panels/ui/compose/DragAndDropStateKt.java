package com.android.systemui.qs.panels.ui.compose;

import android.view.DragEvent;
import androidx.compose.ui.draganddrop.DragAndDropEvent;
import androidx.compose.ui.geometry.Offset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class DragAndDropStateKt {
    public static final long access$toOffset(DragAndDropEvent dragAndDropEvent) {
        DragEvent dragEvent = dragAndDropEvent.dragEvent;
        float x = dragEvent.getX();
        float y = dragEvent.getY();
        long floatToRawIntBits = (Float.floatToRawIntBits(x) << 32) | (Float.floatToRawIntBits(y) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0073, code lost:
    
        if (r14 == androidx.compose.runtime.Composer.Companion.Empty) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.Modifier dragAndDropTileSource(androidx.compose.ui.Modifier r8, com.android.systemui.qs.panels.shared.model.SizedTileImpl r9, com.android.systemui.qs.panels.ui.compose.DragAndDropState r10, com.android.systemui.qs.panels.ui.compose.DragType r11, kotlin.jvm.functions.Function0 r12, androidx.compose.runtime.Composer r13, int r14) {
        /*
            androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
            r0 = 428400562(0x1988dfb2, float:1.415242E-23)
            r13.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "com.android.systemui.qs.panels.ui.compose.dragAndDropTileSource (DragAndDropState.kt:185)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            androidx.compose.runtime.MutableState r5 = androidx.compose.runtime.SnapshotStateKt.rememberUpdatedState(r10, r13)
            r10 = 955041229(0x38ecc5cd, float:1.12902E-4)
            r13.startReplaceGroup(r10)
            boolean r10 = r13.changed(r5)
            r0 = r14 & 112(0x70, float:1.57E-43)
            r0 = r0 ^ 48
            r1 = 32
            r2 = 1
            r7 = 0
            if (r0 <= r1) goto L31
            boolean r0 = r13.changedInstance(r9)
            if (r0 != 0) goto L35
        L31:
            r0 = r14 & 48
            if (r0 != r1) goto L37
        L35:
            r0 = r2
            goto L38
        L37:
            r0 = r7
        L38:
            r10 = r10 | r0
            r0 = r14 & 7168(0x1c00, float:1.0045E-41)
            r0 = r0 ^ 3072(0xc00, float:4.305E-42)
            r1 = 2048(0x800, float:2.87E-42)
            if (r0 <= r1) goto L47
            boolean r0 = r13.changed(r11)
            if (r0 != 0) goto L4b
        L47:
            r0 = r14 & 3072(0xc00, float:4.305E-42)
            if (r0 != r1) goto L4d
        L4b:
            r0 = r2
            goto L4e
        L4d:
            r0 = r7
        L4e:
            r10 = r10 | r0
            r0 = 57344(0xe000, float:8.0356E-41)
            r0 = r0 & r14
            r0 = r0 ^ 24576(0x6000, float:3.4438E-41)
            r1 = 16384(0x4000, float:2.2959E-41)
            if (r0 <= r1) goto L5f
            boolean r0 = r13.changed(r12)
            if (r0 != 0) goto L65
        L5f:
            r14 = r14 & 24576(0x6000, float:3.4438E-41)
            if (r14 != r1) goto L64
            goto L65
        L64:
            r2 = r7
        L65:
            r10 = r10 | r2
            java.lang.Object r14 = r13.rememberedValue()
            if (r10 != 0) goto L75
            androidx.compose.runtime.Composer$Companion r10 = androidx.compose.runtime.Composer.Companion
            r10.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r10 = androidx.compose.runtime.Composer.Companion.Empty
            if (r14 != r10) goto L82
        L75:
            com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropTileSource$1$1 r1 = new com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt$dragAndDropTileSource$1$1
            r6 = 0
            r2 = r9
            r3 = r11
            r4 = r12
            r1.<init>(r2, r3, r4, r5, r6)
            r13.updateRememberedValue(r1)
            r14 = r1
        L82:
            kotlin.jvm.functions.Function2 r14 = (kotlin.jvm.functions.Function2) r14
            r13.end(r7)
            androidx.compose.ui.Modifier r8 = androidx.compose.foundation.draganddrop.AndroidDragAndDropSource_androidKt.dragAndDropSource(r8, r14)
            boolean r9 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r9 == 0) goto L94
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L94:
            r13.end(r7)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.DragAndDropStateKt.dragAndDropTileSource(androidx.compose.ui.Modifier, com.android.systemui.qs.panels.shared.model.SizedTileImpl, com.android.systemui.qs.panels.ui.compose.DragAndDropState, com.android.systemui.qs.panels.ui.compose.DragType, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):androidx.compose.ui.Modifier");
    }
}
