package com.android.compose.ui.graphics.painter;

import android.graphics.drawable.Drawable;
import androidx.compose.ui.geometry.Size;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DrawablePainterKt {
    public static final Lazy MAIN_HANDLER$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new DrawablePainterKt$$ExternalSyntheticLambda0());

    public static final long access$getIntrinsicSize(Drawable drawable) {
        if (drawable.getIntrinsicWidth() < 0 || drawable.getIntrinsicHeight() < 0) {
            Size.Companion.getClass();
            return Size.Unspecified;
        }
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        long floatToRawIntBits = (Float.floatToRawIntBits(intrinsicWidth) << 32) | (Float.floatToRawIntBits(intrinsicHeight) & 4294967295L);
        Size.Companion companion = Size.Companion;
        return floatToRawIntBits;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002a, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.graphics.painter.Painter rememberDrawablePainter(android.graphics.drawable.Drawable r3, androidx.compose.runtime.Composer r4) {
        /*
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            r0 = -1466352340(0xffffffffa8993d2c, float:-1.7012941E-14)
            r4.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "com.android.compose.ui.graphics.painter.rememberDrawablePainter (DrawablePainter.kt:152)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            r0 = 926325178(0x373699ba, float:1.0883838E-5)
            r4.startReplaceGroup(r0)
            boolean r0 = r4.changed(r3)
            java.lang.Object r1 = r4.rememberedValue()
            if (r0 != 0) goto L2c
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r0) goto L55
        L2c:
            if (r3 != 0) goto L32
            com.android.compose.ui.graphics.painter.EmptyPainter r3 = com.android.compose.ui.graphics.painter.EmptyPainter.INSTANCE
            r1 = r3
            goto L52
        L32:
            boolean r0 = r3 instanceof android.graphics.drawable.ColorDrawable
            if (r0 == 0) goto L48
            androidx.compose.ui.graphics.painter.ColorPainter r0 = new androidx.compose.ui.graphics.painter.ColorPainter
            android.graphics.drawable.ColorDrawable r3 = (android.graphics.drawable.ColorDrawable) r3
            int r3 = r3.getColor()
            long r1 = androidx.compose.ui.graphics.ColorKt.Color(r3)
            r3 = 0
            r0.<init>(r1, r3)
        L46:
            r1 = r0
            goto L52
        L48:
            com.android.compose.ui.graphics.painter.DrawablePainter r0 = new com.android.compose.ui.graphics.painter.DrawablePainter
            android.graphics.drawable.Drawable r3 = r3.mutate()
            r0.<init>(r3)
            goto L46
        L52:
            r4.updateRememberedValue(r1)
        L55:
            androidx.compose.ui.graphics.painter.Painter r1 = (androidx.compose.ui.graphics.painter.Painter) r1
            r3 = 0
            r4.end(r3)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L64
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L64:
            r4.end(r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.ui.graphics.painter.DrawablePainterKt.rememberDrawablePainter(android.graphics.drawable.Drawable, androidx.compose.runtime.Composer):androidx.compose.ui.graphics.painter.Painter");
    }
}
