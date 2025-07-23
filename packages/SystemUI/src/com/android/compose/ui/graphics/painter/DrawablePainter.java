package com.android.compose.ui.graphics.painter;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RememberObserver;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DrawablePainter extends Painter implements RememberObserver {
    public final Drawable drawable;
    public final MutableState drawableIntrinsicSize$delegate;
    public final MutableState drawInvalidateTick$delegate = SnapshotStateKt.mutableStateOf$default(0);
    public final Lazy callback$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.compose.ui.graphics.painter.DrawablePainter$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            final DrawablePainter drawablePainter = DrawablePainter.this;
            return new Drawable.Callback() { // from class: com.android.compose.ui.graphics.painter.DrawablePainter$callback$2$1
                @Override // android.graphics.drawable.Drawable.Callback
                public final void invalidateDrawable(Drawable drawable) {
                    int intValue = ((Number) ((SnapshotMutableStateImpl) DrawablePainter.this.drawInvalidateTick$delegate).getValue()).intValue();
                    ((SnapshotMutableStateImpl) DrawablePainter.this.drawInvalidateTick$delegate).setValue(Integer.valueOf(intValue + 1));
                    DrawablePainter drawablePainter2 = DrawablePainter.this;
                    long access$getIntrinsicSize = DrawablePainterKt.access$getIntrinsicSize(drawablePainter2.drawable);
                    ((SnapshotMutableStateImpl) drawablePainter2.drawableIntrinsicSize$delegate).setValue(Size.m413boximpl(access$getIntrinsicSize));
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                    ((Handler) DrawablePainterKt.MAIN_HANDLER$delegate.getValue()).postAtTime(runnable, j);
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                    ((Handler) DrawablePainterKt.MAIN_HANDLER$delegate.getValue()).removeCallbacks(runnable);
                }
            };
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DrawablePainter(Drawable drawable) {
        this.drawable = drawable;
        this.drawableIntrinsicSize$delegate = SnapshotStateKt.mutableStateOf$default(Size.m413boximpl(DrawablePainterKt.access$getIntrinsicSize(drawable)));
        if (drawable.getIntrinsicWidth() < 0 || drawable.getIntrinsicHeight() < 0) {
            return;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyAlpha(float f) {
        this.drawable.setAlpha(RangesKt___RangesKt.coerceIn(MathKt__MathJVMKt.roundToInt(f * 255), 0, 255));
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyColorFilter(ColorFilter colorFilter) {
        this.drawable.setColorFilter(colorFilter != null ? colorFilter.nativeColorFilter : null);
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void applyLayoutDirection(LayoutDirection layoutDirection) {
        Drawable drawable = this.drawable;
        int i = WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
        int i2 = 1;
        if (i == 1) {
            i2 = 0;
        } else if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        drawable.setLayoutDirection(i2);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* renamed from: getIntrinsicSize-NH-jbRc */
    public final long mo561getIntrinsicSizeNHjbRc() {
        return ((Size) ((SnapshotMutableStateImpl) this.drawableIntrinsicSize$delegate).getValue()).packedValue;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
        onForgotten();
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
        Canvas canvas = drawScope.getDrawContext().getCanvas();
        ((Number) ((SnapshotMutableStateImpl) this.drawInvalidateTick$delegate).getValue()).intValue();
        this.drawable.setBounds(0, 0, MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (drawScope.mo545getSizeNHjbRc() >> 32))), MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (drawScope.mo545getSizeNHjbRc() & 4294967295L))));
        try {
            canvas.save();
            Drawable drawable = this.drawable;
            android.graphics.Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
            drawable.draw(((AndroidCanvas) canvas).internalCanvas);
        } finally {
            canvas.restore();
        }
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
        Object obj = this.drawable;
        if (obj instanceof Animatable) {
            ((Animatable) obj).stop();
        }
        this.drawable.setVisible(false, false);
        this.drawable.setCallback(null);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onRemembered() {
        this.drawable.setCallback((Drawable.Callback) this.callback$delegate.getValue());
        this.drawable.setVisible(true, true);
        Object obj = this.drawable;
        if (obj instanceof Animatable) {
            ((Animatable) obj).start();
        }
    }
}
