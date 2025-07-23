package androidx.compose.material3;

import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsSides;
import androidx.compose.foundation.layout.WindowInsets_androidKt;
import androidx.compose.material3.tokens.SheetBottomTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BottomSheetDefaults {
    public static final float Elevation;
    public static final BottomSheetDefaults INSTANCE = new BottomSheetDefaults();
    public static final float PositionalThreshold;
    public static final float SheetMaxWidth;
    public static final float VelocityThreshold;

    static {
        SheetBottomTokens.INSTANCE.getClass();
        Elevation = SheetBottomTokens.DockedModalContainerElevation;
        Dp.Companion companion = Dp.Companion;
        SheetMaxWidth = 640;
        PositionalThreshold = 56;
        VelocityThreshold = 125;
    }

    private BottomSheetDefaults() {
    }

    public static long getContainerColor(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.<get-ContainerColor> (SheetDefaults.kt:381)");
        }
        SheetBottomTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(SheetBottomTokens.DockedContainerColor, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static Shape getExpandedShape(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.<get-ExpandedShape> (SheetDefaults.kt:377)");
        }
        SheetBottomTokens.INSTANCE.getClass();
        Shape value = ShapesKt.getValue(SheetBottomTokens.DockedContainerShape, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static WindowInsets getWindowInsets(ComposerImpl composerImpl) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.<get-windowInsets> (SheetDefaults.kt:398)");
        }
        WindowInsets.Companion companion = WindowInsets.Companion;
        WindowInsets safeDrawing = WindowInsets_androidKt.getSafeDrawing(composerImpl);
        WindowInsetsSides.Companion.getClass();
        WindowInsets m148onlybOOhFvg = WindowInsetsKt.m148onlybOOhFvg(safeDrawing, WindowInsetsSides.Bottom);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return m148onlybOOhFvg;
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0148, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L94;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0052  */
    /* renamed from: DragHandle-lgZ2HuY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m249DragHandlelgZ2HuY(androidx.compose.ui.Modifier r25, float r26, float r27, androidx.compose.ui.graphics.Shape r28, long r29, androidx.compose.runtime.Composer r31, final int r32, final int r33) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.BottomSheetDefaults.m249DragHandlelgZ2HuY(androidx.compose.ui.Modifier, float, float, androidx.compose.ui.graphics.Shape, long, androidx.compose.runtime.Composer, int, int):void");
    }
}
