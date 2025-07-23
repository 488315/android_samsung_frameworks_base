package androidx.compose.material.ripple;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AnimationUtils;
import androidx.compose.material.ripple.UnprojectedRipple;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RippleHostView extends View {
    public static final int[] PressedState;
    public static final int[] RestingState;
    public Boolean bounded;
    public Long lastRippleStateChangeTimeMillis;
    public Lambda onInvalidateRipple;
    public RippleHostView$$ExternalSyntheticLambda0 resetRippleRunnable;
    public UnprojectedRipple ripple;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        PressedState = new int[]{R.attr.state_pressed, R.attr.state_enabled};
        RestingState = new int[0];
    }

    public RippleHostView(Context context) {
        super(context);
    }

    public final void disposeRipple() {
        this.onInvalidateRipple = null;
        Runnable runnable = this.resetRippleRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
            RippleHostView$$ExternalSyntheticLambda0 rippleHostView$$ExternalSyntheticLambda0 = this.resetRippleRunnable;
            rippleHostView$$ExternalSyntheticLambda0.getClass();
            rippleHostView$$ExternalSyntheticLambda0.run();
        } else {
            UnprojectedRipple unprojectedRipple = this.ripple;
            if (unprojectedRipple != null) {
                unprojectedRipple.setState(RestingState);
            }
        }
        UnprojectedRipple unprojectedRipple2 = this.ripple;
        if (unprojectedRipple2 == null) {
            return;
        }
        unprojectedRipple2.setVisible(false, false);
        unscheduleDrawable(unprojectedRipple2);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        ?? r0 = this.onInvalidateRipple;
        if (r0 != 0) {
            r0.invoke();
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    public final void setRippleState(boolean z) {
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        Runnable runnable = this.resetRippleRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
            runnable.run();
        }
        Long l = this.lastRippleStateChangeTimeMillis;
        long longValue = currentAnimationTimeMillis - (l != null ? l.longValue() : 0L);
        if (z || longValue >= 5) {
            int[] iArr = z ? PressedState : RestingState;
            UnprojectedRipple unprojectedRipple = this.ripple;
            if (unprojectedRipple != null) {
                unprojectedRipple.setState(iArr);
            }
        } else {
            RippleHostView$$ExternalSyntheticLambda0 rippleHostView$$ExternalSyntheticLambda0 = new RippleHostView$$ExternalSyntheticLambda0(this);
            this.resetRippleRunnable = rippleHostView$$ExternalSyntheticLambda0;
            postDelayed(rippleHostView$$ExternalSyntheticLambda0, 50L);
        }
        this.lastRippleStateChangeTimeMillis = Long.valueOf(currentAnimationTimeMillis);
    }

    /* renamed from: updateRippleProperties-biQXAtU, reason: not valid java name */
    public final void m244updateRipplePropertiesbiQXAtU(long j, long j2, int i, float f) {
        long Color;
        UnprojectedRipple unprojectedRipple = this.ripple;
        if (unprojectedRipple == null) {
            return;
        }
        Integer num = unprojectedRipple.rippleRadius;
        if (num == null || num.intValue() != i) {
            unprojectedRipple.rippleRadius = Integer.valueOf(i);
            UnprojectedRipple.MRadiusHelper.INSTANCE.setRadius(unprojectedRipple, i);
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        Color = ColorKt.Color(Color.m461getRedimpl(j2), Color.m460getGreenimpl(j2), Color.m458getBlueimpl(j2), f, Color.m459getColorSpaceimpl(j2));
        Color color = unprojectedRipple.rippleColor;
        if (!(color == null ? false : ULong.m3427equalsimpl0(color.value, Color))) {
            unprojectedRipple.rippleColor = Color.m454boximpl(Color);
            unprojectedRipple.setColor(ColorStateList.valueOf(ColorKt.m467toArgb8_81llA(Color)));
        }
        Rect rect = new Rect(0, 0, MathKt__MathJVMKt.roundToInt(Size.m417getWidthimpl(j)), MathKt__MathJVMKt.roundToInt(Size.m415getHeightimpl(j)));
        setLeft(rect.left);
        setTop(rect.top);
        setRight(rect.right);
        setBottom(rect.bottom);
        unprojectedRipple.setBounds(rect);
    }

    @Override // android.view.View
    public final void refreshDrawableState() {
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }
}
