package com.android.compose.ui.graphics.painter;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.painter.ColorPainter;
import androidx.compose.ui.graphics.painter.Painter;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;

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
        long jFloatToRawIntBits = (Float.floatToRawIntBits(intrinsicWidth) << 32) | (Float.floatToRawIntBits(intrinsicHeight) & 4294967295L);
        Size.Companion companion = Size.Companion;
        return jFloatToRawIntBits;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Painter rememberDrawablePainter(Drawable drawable, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1466352340);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.compose.ui.graphics.painter.rememberDrawablePainter (DrawablePainter.kt:152)");
        }
        composerImpl.startReplaceGroup(926325178);
        boolean zChanged = composerImpl.changed(drawable);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                if (drawable == null) {
                    objRememberedValue = EmptyPainter.INSTANCE;
                } else {
                    objRememberedValue = drawable instanceof ColorDrawable ? new ColorPainter(ColorKt.Color(((ColorDrawable) drawable).getColor()), null) : new DrawablePainter(drawable.mutate());
                }
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        Painter painter = (Painter) objRememberedValue;
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return painter;
    }
}
