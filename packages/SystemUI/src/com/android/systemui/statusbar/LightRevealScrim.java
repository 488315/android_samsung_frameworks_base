package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.keyguard.logging.ScrimLogger;
import com.android.systemui.LsRune;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.util.ColorUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public final class LightRevealScrim extends View {
    public final Paint dimPaint;
    public final Paint gradientPaint;
    public float interpolatedRevealAmount;
    public boolean isScrimOpaque;
    public CentralSurfacesImpl$$ExternalSyntheticLambda2 isScrimOpaqueChangedListener;
    public final String logString;
    public float revealAmount;
    public float revealDimGradientEndColorAlpha;
    public LightRevealEffect revealEffect;
    public final PointF revealGradientCenter;
    public final int revealGradientEndColor;
    public float revealGradientEndColorAlpha;
    public float revealGradientHeight;
    public float revealGradientWidth;
    public ScrimLogger scrimLogger;
    public final Matrix shaderGradientMatrix;

    public LightRevealScrim(Context context) {
        this(context, null, null, null, 14, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void leaveLog$default(LightRevealScrim lightRevealScrim, Integer num, Float f, Float f2, Integer num2, Float f3, Float f4, int i) {
        Object objValueOf;
        Object objValueOf2;
        Object objValueOf3;
        if ((i & 1) != 0) {
            num = null;
        }
        if ((i & 2) != 0) {
            f = null;
        }
        if ((i & 4) != 0) {
            f2 = null;
        }
        if ((i & 8) != 0) {
            num2 = null;
        }
        if ((i & 16) != 0) {
            f3 = null;
        }
        if ((i & 32) != 0) {
            f4 = null;
        }
        LightRevealEffect lightRevealEffect = lightRevealScrim.revealEffect;
        boolean z = lightRevealScrim.isScrimOpaque;
        if (f2 != null) {
            objValueOf = "(" + f2.floatValue() + " -> " + f4 + ")";
            if (objValueOf == null) {
                objValueOf = Float.valueOf(lightRevealScrim.revealAmount);
            }
        }
        if (f != null) {
            objValueOf2 = "(" + f.floatValue() + " -> " + f3 + ")";
            if (objValueOf2 == null) {
                objValueOf2 = Float.valueOf(lightRevealScrim.getAlpha());
            }
        }
        if (num != null) {
            objValueOf3 = "(" + num.intValue() + " -> " + num2 + ")";
            if (objValueOf3 == null) {
                objValueOf3 = Integer.valueOf(lightRevealScrim.getVisibility());
            }
        }
        Log.d("ScrimController", "updateLightReveal revealEffect=" + lightRevealEffect + ", opaque=" + z + " revealAmount=" + objValueOf + " alpha=" + objValueOf2 + " vis=" + objValueOf3);
    }

    @Override // android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        TouchLogger.Companion companion = TouchLogger.Companion;
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "LightRevealScrim", zDispatchTouchEvent);
        return zDispatchTouchEvent;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        Canvas canvas2;
        float f = this.revealGradientWidth;
        if (f > 0.0f) {
            float f2 = this.revealGradientHeight;
            if (f2 > 0.0f && this.revealAmount != 0.0f) {
                Matrix matrix = this.shaderGradientMatrix;
                matrix.setScale(f, f2, 0.0f, 0.0f);
                PointF pointF = this.revealGradientCenter;
                matrix.postTranslate(pointF.x, pointF.y);
                this.gradientPaint.getShader().setLocalMatrix(matrix);
                if (LsRune.AOD_LIGHT_REVEAL) {
                    canvas2 = canvas;
                    canvas2.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.dimPaint);
                } else {
                    canvas2 = canvas;
                }
                canvas2.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.gradientPaint);
                return;
            }
        }
        if (this.revealAmount < 1.0f) {
            canvas.drawColor(this.revealGradientEndColor);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        getMeasuredWidth();
        getMeasuredHeight();
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setAlpha(float f) {
        LightRevealScrim lightRevealScrim;
        if (LsRune.AOD_LIGHT_REVEAL) {
            float alpha = getAlpha();
            if (alpha == f) {
                lightRevealScrim = this;
            } else {
                lightRevealScrim = this;
                leaveLog$default(lightRevealScrim, null, Float.valueOf(alpha), null, null, Float.valueOf(f), null, 45);
            }
        }
        super.setAlpha(f);
        ScrimLogger scrimLogger = lightRevealScrim.scrimLogger;
        if (scrimLogger != null) {
            scrimLogger.d("LightRevealScrim", "alpha", f + " on " + lightRevealScrim.logString);
        }
        lightRevealScrim.updateScrimOpaque();
    }

    public final void setPaintColorFilter() {
        this.gradientPaint.setColorFilter(new PorterDuffColorFilter(ColorUtilKt.getColorWithAlpha(this.revealGradientEndColor, this.revealGradientEndColorAlpha), PorterDuff.Mode.MULTIPLY));
        if (LsRune.AOD_LIGHT_REVEAL) {
            this.dimPaint.setColor(ColorUtilKt.getColorWithAlpha(this.revealGradientEndColor, this.revealDimGradientEndColorAlpha));
        }
    }

    public final void setRevealAmount(float f) {
        LightRevealScrim lightRevealScrim;
        ScrimLogger scrimLogger;
        float f2 = this.revealAmount;
        if (f2 == f) {
            return;
        }
        if (LsRune.AOD_LIGHT_REVEAL && (SecLsScrimControlHelper.DEBUG || f == 0.0f || f == 1.0f || f2 == 0.0f || f2 == 1.0f)) {
            lightRevealScrim = this;
            leaveLog$default(lightRevealScrim, null, null, Float.valueOf(f2), null, null, Float.valueOf(f), 27);
        } else {
            lightRevealScrim = this;
        }
        lightRevealScrim.revealAmount = f;
        if ((f <= 0.0f || f >= 1.0f) && (scrimLogger = lightRevealScrim.scrimLogger) != null) {
            scrimLogger.d("LightRevealScrim", "revealAmount", f + " on " + lightRevealScrim.logString);
        }
        lightRevealScrim.revealEffect.setRevealAmountOnScrim(f, lightRevealScrim);
        lightRevealScrim.updateScrimOpaque();
        TrackTracer.Companion companion = TrackTracer.Companion;
        int i = (int) (lightRevealScrim.revealAmount * 100);
        if (Trace.isEnabled()) {
            String str = "light_reveal_amount " + lightRevealScrim.logString;
            companion.getClass();
            TrackTracer.Companion.instantForGroup(i, "scrim", str);
        }
        lightRevealScrim.invalidate();
    }

    public final void setRevealEffect(LightRevealEffect lightRevealEffect) {
        if (Intrinsics.areEqual(this.revealEffect, lightRevealEffect)) {
            return;
        }
        this.revealEffect = lightRevealEffect;
        lightRevealEffect.setRevealAmountOnScrim(this.revealAmount, this);
        ScrimLogger scrimLogger = this.scrimLogger;
        if (scrimLogger != null) {
            scrimLogger.d("LightRevealScrim", "revealEffect", lightRevealEffect + " on " + this.logString);
        }
        invalidate();
    }

    public final void setRevealGradientBounds(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        this.revealGradientWidth = f5;
        float f6 = f4 - f2;
        this.revealGradientHeight = f6;
        PointF pointF = this.revealGradientCenter;
        pointF.x = (f5 / 2.0f) + f;
        pointF.y = (f6 / 2.0f) + f2;
    }

    public final void setRevealGradientEndColorAlpha(float f) {
        if (this.revealGradientEndColorAlpha == f) {
            return;
        }
        this.revealGradientEndColorAlpha = f;
        setPaintColorFilter();
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        LightRevealScrim lightRevealScrim;
        int visibility;
        if (!LsRune.AOD_LIGHT_REVEAL || (visibility = getVisibility()) == i) {
            lightRevealScrim = this;
        } else {
            lightRevealScrim = this;
            leaveLog$default(lightRevealScrim, Integer.valueOf(visibility), null, null, Integer.valueOf(i), null, null, 54);
        }
        super.setVisibility(i);
        ScrimLogger scrimLogger = lightRevealScrim.scrimLogger;
        if (scrimLogger != null) {
            scrimLogger.d("LightRevealScrim", "visibility", i + " on " + lightRevealScrim.logString);
        }
        lightRevealScrim.updateScrimOpaque();
    }

    public final void updateScrimOpaque() {
        boolean z = this.revealAmount == 0.0f && getAlpha() == 1.0f && getVisibility() == 0;
        if (this.isScrimOpaque != z) {
            this.isScrimOpaque = z;
            CentralSurfacesImpl$$ExternalSyntheticLambda2 centralSurfacesImpl$$ExternalSyntheticLambda2 = this.isScrimOpaqueChangedListener;
            if (centralSurfacesImpl$$ExternalSyntheticLambda2 != null) {
                centralSurfacesImpl$$ExternalSyntheticLambda2.accept(Boolean.valueOf(z));
            }
            ScrimLogger scrimLogger = this.scrimLogger;
            if (scrimLogger != null) {
                scrimLogger.d("LightRevealScrim", "isScrimOpaque", z + " on " + this.logString);
            }
        }
    }

    public LightRevealScrim(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, null, null, 12, null);
    }

    public LightRevealScrim(Context context, AttributeSet attributeSet, Integer num) {
        this(context, attributeSet, num, null, 8, null);
    }

    public /* synthetic */ LightRevealScrim(Context context, AttributeSet attributeSet, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : num2);
    }

    public LightRevealScrim(Context context, AttributeSet attributeSet, Integer num, Integer num2) {
        super(context, attributeSet);
        String simpleName = Reflection.getOrCreateKotlinClass(LightRevealScrim.class).getSimpleName();
        simpleName.getClass();
        this.logString = simpleName + "@" + hashCode();
        this.revealAmount = 1.0f;
        this.revealEffect = LiftReveal.INSTANCE;
        this.revealGradientCenter = new PointF();
        this.revealGradientEndColor = -16777216;
        this.interpolatedRevealAmount = 1.0f;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(-16777216);
        this.dimPaint = paint;
        Paint paint2 = new Paint();
        paint2.setShader(new RadialGradient(0.0f, 0.0f, 1.0f, new int[]{0, -1}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        this.gradientPaint = paint2;
        this.shaderGradientMatrix = new Matrix();
        this.revealEffect.setRevealAmountOnScrim(this.revealAmount, this);
        setPaintColorFilter();
        invalidate();
    }
}
