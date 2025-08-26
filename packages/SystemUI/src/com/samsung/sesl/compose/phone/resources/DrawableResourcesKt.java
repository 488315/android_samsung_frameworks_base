package com.samsung.sesl.compose.phone.resources;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.painter.ColorPainter;
import androidx.compose.ui.graphics.painter.Painter;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;

/* loaded from: classes4.dex */
public abstract class DrawableResourcesKt {
    public static final Lazy MAIN_HANDLER$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new DrawableResourcesKt$$ExternalSyntheticLambda0());

    public static final long access$getIntrinsicSize(Drawable drawable) {
        if (drawable.getIntrinsicWidth() >= 0 && drawable.getIntrinsicHeight() >= 0) {
            return SizeKt.Size(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        Size.Companion.getClass();
        return Size.Unspecified;
    }

    public static final Painter seslDrawablePainter(int i, Context context) {
        Drawable drawable = context.getDrawable(i);
        return drawable == null ? EmptyPainter.INSTANCE : drawable instanceof ColorDrawable ? new ColorPainter(ColorKt.Color(((ColorDrawable) drawable).getColor()), null) : new SeslDrawablePainter(drawable.mutate());
    }

    public static final Drawable seslDrawableResource(int i, Context context) {
        Drawable drawable = context.getDrawable(i);
        if (drawable != null) {
            return drawable;
        }
        SeslDrawableTokens.Companion.getClass();
        return SeslDrawableTokens.emptyDrawable;
    }

    public static final Painter seslToPainter(Drawable drawable) {
        return drawable instanceof ColorDrawable ? new ColorPainter(ColorKt.Color(((ColorDrawable) drawable).getColor()), null) : new SeslDrawablePainter(drawable.mutate());
    }
}
