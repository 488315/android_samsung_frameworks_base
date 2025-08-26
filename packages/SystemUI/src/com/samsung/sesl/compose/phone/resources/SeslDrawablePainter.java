package com.samsung.sesl.compose.phone.resources;

import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RememberObserver;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
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

/* loaded from: classes4.dex */
public class SeslDrawablePainter extends Painter implements RememberObserver {
    public final Drawable drawable;
    public final MutableState drawableIntrinsicSize$delegate;
    public final MutableIntState drawInvalidateTick$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
    public final Lazy callback$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.phone.resources.SeslDrawablePainter$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            final SeslDrawablePainter seslDrawablePainter = this.f$0;
            return new Drawable.Callback() { // from class: com.samsung.sesl.compose.phone.resources.SeslDrawablePainter$callback$2$1
                @Override // android.graphics.drawable.Drawable.Callback
                public final void invalidateDrawable(Drawable drawable) {
                    int intValue = ((SnapshotMutableIntStateImpl) seslDrawablePainter.drawInvalidateTick$delegate).getIntValue();
                    ((SnapshotMutableIntStateImpl) seslDrawablePainter.drawInvalidateTick$delegate).setIntValue(intValue + 1);
                    SeslDrawablePainter seslDrawablePainter2 = seslDrawablePainter;
                    long jAccess$getIntrinsicSize = DrawableResourcesKt.access$getIntrinsicSize(seslDrawablePainter2.drawable);
                    ((SnapshotMutableStateImpl) seslDrawablePainter2.drawableIntrinsicSize$delegate).setValue(Size.m415boximpl(jAccess$getIntrinsicSize));
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                    ((Handler) DrawableResourcesKt.MAIN_HANDLER$delegate.getValue()).postAtTime(runnable, j);
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                    ((Handler) DrawableResourcesKt.MAIN_HANDLER$delegate.getValue()).removeCallbacks(runnable);
                }
            };
        }
    });

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

    public SeslDrawablePainter(Drawable drawable) {
        this.drawable = drawable;
        this.drawableIntrinsicSize$delegate = SnapshotStateKt.mutableStateOf$default(Size.m415boximpl(DrawableResourcesKt.access$getIntrinsicSize(drawable)));
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
    public final long mo563getIntrinsicSizeNHjbRc() {
        return ((Size) ((SnapshotMutableStateImpl) this.drawableIntrinsicSize$delegate).getValue()).packedValue;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
        onForgotten();
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
        Canvas canvas = drawScope.getDrawContext().getCanvas();
        ((SnapshotMutableIntStateImpl) this.drawInvalidateTick$delegate).getIntValue();
        this.drawable.setBounds(new Rect(0, 0, (int) Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()), (int) Size.m417getHeightimpl(drawScope.mo547getSizeNHjbRc())));
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
