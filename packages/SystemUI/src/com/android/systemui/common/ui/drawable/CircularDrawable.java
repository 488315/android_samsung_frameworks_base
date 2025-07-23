package com.android.systemui.common.ui.drawable;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CircularDrawable extends DrawableWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy path$delegate;

    public CircularDrawable(Drawable drawable) {
        super(drawable);
        this.path$delegate = LazyKt__LazyJVMKt.lazy(new CircularDrawable$$ExternalSyntheticLambda0());
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.save();
        canvas.clipPath((Path) this.path$delegate.getValue());
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        ((Path) this.path$delegate.getValue()).reset();
        ((Path) this.path$delegate.getValue()).addCircle(getBounds().centerX(), getBounds().centerY(), Math.min(getBounds().width(), getBounds().height()) / 2.0f, Path.Direction.CW);
    }
}
