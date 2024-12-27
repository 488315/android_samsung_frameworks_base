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
import android.view.MotionEvent;
import android.view.View;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.ScrimLogger;
import com.android.systemui.LsRune;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.util.ColorUtilKt;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public final class LightRevealScrim extends View {
    public final Paint dimPaint;
    public final Paint gradientPaint;
    public float interpolatedRevealAmount;
    public boolean isScrimOpaque;
    public Consumer isScrimOpaqueChangedListener;
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

    public LightRevealScrim(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, null, null, 12, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
    
        if (r9 == null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x005e, code lost:
    
        if (r8 == null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void leaveLog$default(com.android.systemui.statusbar.LightRevealScrim r6, java.lang.Integer r7, java.lang.Float r8, java.lang.Float r9, int r10) {
        /*
            r0 = r10 & 1
            r1 = 0
            if (r0 == 0) goto L6
            r7 = r1
        L6:
            r0 = r10 & 2
            if (r0 == 0) goto Lb
            r8 = r1
        Lb:
            r10 = r10 & 4
            if (r10 == 0) goto L10
            r9 = r1
        L10:
            com.android.systemui.statusbar.LightRevealEffect r10 = r6.revealEffect
            boolean r0 = r6.isScrimOpaque
            java.lang.String r1 = ")"
            java.lang.String r2 = " -> "
            java.lang.String r3 = "("
            if (r9 == 0) goto L39
            float r9 = r9.floatValue()
            float r4 = r6.revealAmount
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r3)
            r5.append(r9)
            r5.append(r2)
            r5.append(r4)
            r5.append(r1)
            java.lang.String r9 = r5.toString()
            if (r9 != 0) goto L3f
        L39:
            float r9 = r6.revealAmount
            java.lang.Float r9 = java.lang.Float.valueOf(r9)
        L3f:
            if (r8 == 0) goto L60
            float r8 = r8.floatValue()
            float r4 = r6.getAlpha()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r3)
            r5.append(r8)
            r5.append(r2)
            r5.append(r4)
            r5.append(r1)
            java.lang.String r8 = r5.toString()
            if (r8 != 0) goto L68
        L60:
            float r8 = r6.getAlpha()
            java.lang.Float r8 = java.lang.Float.valueOf(r8)
        L68:
            if (r7 == 0) goto L78
            int r7 = r7.intValue()
            int r4 = r6.getVisibility()
            java.lang.String r7 = androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(r7, r4, r3, r2, r1)
            if (r7 != 0) goto L80
        L78:
            int r6 = r6.getVisibility()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r6)
        L80:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r1 = "updateLightReveal revealEffect="
            r6.<init>(r1)
            r6.append(r10)
            java.lang.String r10 = ", opaque="
            r6.append(r10)
            r6.append(r0)
            java.lang.String r10 = " revealAmount="
            r6.append(r10)
            r6.append(r9)
            java.lang.String r9 = " alpha="
            r6.append(r9)
            r6.append(r8)
            java.lang.String r8 = " vis="
            r6.append(r8)
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = "ScrimController"
            android.util.Log.d(r7, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.LightRevealScrim.leaveLog$default(com.android.systemui.statusbar.LightRevealScrim, java.lang.Integer, java.lang.Float, java.lang.Float, int):void");
    }

    @Override // android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        TouchLogger.Companion companion = TouchLogger.Companion;
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "LightRevealScrim", dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
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
                    canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.dimPaint);
                }
                canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.gradientPaint);
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

    @Override // android.view.View
    public final void setAlpha(float f) {
        if (LsRune.AOD_LIGHT_REVEAL) {
            float alpha = getAlpha();
            if (alpha != f) {
                leaveLog$default(this, null, Float.valueOf(alpha), null, 5);
            }
        }
        super.setAlpha(f);
        ScrimLogger scrimLogger = this.scrimLogger;
        if (scrimLogger != null) {
            scrimLogger.d("LightRevealScrim", "alpha", f + " on " + this.logString);
        }
        updateScrimOpaque();
    }

    public final void setPaintColorFilter() {
        this.gradientPaint.setColorFilter(new PorterDuffColorFilter(ColorUtilKt.getColorWithAlpha(this.revealGradientEndColor, this.revealGradientEndColorAlpha), PorterDuff.Mode.MULTIPLY));
        if (LsRune.AOD_LIGHT_REVEAL) {
            this.dimPaint.setColor(ColorUtilKt.getColorWithAlpha(this.revealGradientEndColor, this.revealDimGradientEndColorAlpha));
        }
    }

    public final void setRevealAmount(float f) {
        ScrimLogger scrimLogger;
        float f2 = this.revealAmount;
        if (f2 == f) {
            return;
        }
        if (LsRune.AOD_LIGHT_REVEAL && (SecLsScrimControlHelper.DEBUG || f == 0.0f || f == 1.0f || f2 == 0.0f || f2 == 1.0f)) {
            leaveLog$default(this, null, null, Float.valueOf(f2), 3);
        }
        this.revealAmount = f;
        if ((f <= 0.0f || f >= 1.0f) && (scrimLogger = this.scrimLogger) != null) {
            scrimLogger.d("LightRevealScrim", "revealAmount", f + " on " + this.logString);
        }
        this.revealEffect.setRevealAmountOnScrim(f, this);
        updateScrimOpaque();
        Trace.traceCounter(4096L, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("light_reveal_amount ", this.logString), (int) (this.revealAmount * 100));
        invalidate();
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
        int visibility;
        if (LsRune.AOD_LIGHT_REVEAL && (visibility = getVisibility()) != i) {
            leaveLog$default(this, Integer.valueOf(visibility), null, null, 6);
        }
        super.setVisibility(i);
        ScrimLogger scrimLogger = this.scrimLogger;
        if (scrimLogger != null) {
            scrimLogger.d("LightRevealScrim", "visibility", i + " on " + this.logString);
        }
        updateScrimOpaque();
    }

    public final void updateScrimOpaque() {
        boolean z = this.revealAmount == 0.0f && getAlpha() == 1.0f && getVisibility() == 0;
        if (this.isScrimOpaque != z) {
            this.isScrimOpaque = z;
            Consumer consumer = this.isScrimOpaqueChangedListener;
            if (consumer != null) {
                consumer.accept(Boolean.valueOf(z));
            }
            ScrimLogger scrimLogger = this.scrimLogger;
            if (scrimLogger != null) {
                scrimLogger.d("LightRevealScrim", "isScrimOpaque", z + " on " + this.logString);
            }
        }
    }

    public LightRevealScrim(Context context, AttributeSet attributeSet, Integer num) {
        this(context, attributeSet, num, null, 8, null);
    }

    public /* synthetic */ LightRevealScrim(Context context, AttributeSet attributeSet, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : num2);
    }

    public LightRevealScrim(Context context, AttributeSet attributeSet, Integer num, Integer num2) {
        super(context, attributeSet);
        String simpleName = Reflection.getOrCreateKotlinClass(LightRevealScrim.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
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
