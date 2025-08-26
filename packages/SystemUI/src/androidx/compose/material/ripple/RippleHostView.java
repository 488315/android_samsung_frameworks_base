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

/* loaded from: classes.dex */
public final class RippleHostView extends View {
    public static final int[] PressedState;
    public static final int[] RestingState;
    public Boolean bounded;
    public Long lastRippleStateChangeTimeMillis;
    public Lambda onInvalidateRipple;
    public RippleHostView$$ExternalSyntheticLambda0 resetRippleRunnable;
    public UnprojectedRipple ripple;

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
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        Runnable runnable = this.resetRippleRunnable;
        if (runnable != null) {
            removeCallbacks(runnable);
            runnable.run();
        }
        Long l = this.lastRippleStateChangeTimeMillis;
        long jLongValue = jCurrentAnimationTimeMillis - (l != null ? l.longValue() : 0L);
        if (z || jLongValue >= 5) {
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
        this.lastRippleStateChangeTimeMillis = Long.valueOf(jCurrentAnimationTimeMillis);
    }

    /* renamed from: updateRippleProperties-biQXAtU, reason: not valid java name */
    public final void m245updateRipplePropertiesbiQXAtU(long j, long j2, int i, float f) {
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
        long jColor = ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), f, Color.m461getColorSpaceimpl(j2));
        Color color = unprojectedRipple.rippleColor;
        if (!(color == null ? false : ULong.m3447equalsimpl0(color.value, jColor))) {
            unprojectedRipple.rippleColor = Color.m456boximpl(jColor);
            unprojectedRipple.setColor(ColorStateList.valueOf(ColorKt.m469toArgb8_81llA(jColor)));
        }
        Rect rect = new Rect(0, 0, MathKt__MathJVMKt.roundToInt(Size.m419getWidthimpl(j)), MathKt__MathJVMKt.roundToInt(Size.m417getHeightimpl(j)));
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
