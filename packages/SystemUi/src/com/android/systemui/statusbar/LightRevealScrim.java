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
import android.view.View;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.statusbar.phone.SecLsScrimControlHelper;
import com.android.systemui.util.ColorUtilKt;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.ArrayList;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LightRevealScrim extends View implements PanelScreenShotLogger.LogProvider {
    public final Paint dimPaint;
    public final Paint gradientPaint;
    public float interpolatedRevealAmount;
    public boolean isScrimOpaque;
    public Consumer isScrimOpaqueChangedListener;
    public float revealAmount;
    public float revealDimGradientEndColorAlpha;
    public LightRevealEffect revealEffect;
    public final PointF revealGradientCenter;
    public final int revealGradientEndColor;
    public float revealGradientEndColorAlpha;
    public float revealGradientHeight;
    public float revealGradientWidth;
    public final Matrix shaderGradientMatrix;
    public float startColorAlpha;
    public int viewHeight;
    public int viewWidth;

    public LightRevealScrim(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, null, null, 12, null);
    }

    public static void leaveLog$default(LightRevealScrim lightRevealScrim, Integer num, Float f, Float f2, int i) {
        if ((i & 1) != 0) {
            num = null;
        }
        if ((i & 2) != 0) {
            f = null;
        }
        if ((i & 4) != 0) {
            f2 = null;
        }
        Log.d("ScrimController", lightRevealScrim.dumpLog(num, f, f2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0027, code lost:
    
        if (r10 == null) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x004e, code lost:
    
        if (r9 == null) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String dumpLog(Integer num, Float f, Float f2) {
        Object valueOf;
        Object valueOf2;
        Object valueOf3;
        LightRevealEffect lightRevealEffect = this.revealEffect;
        boolean z = this.isScrimOpaque;
        if (f2 != null) {
            valueOf = "(" + f2.floatValue() + " -> " + this.revealAmount + ")";
        }
        valueOf = Float.valueOf(this.revealAmount);
        if (f != null) {
            valueOf2 = "(" + f.floatValue() + " -> " + getAlpha() + ")";
        }
        valueOf2 = Float.valueOf(getAlpha());
        if (num == null || (valueOf3 = SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("(", num.intValue(), " -> ", getVisibility(), ")")) == null) {
            valueOf3 = Integer.valueOf(getVisibility());
        }
        return "updateLightReveal revealEffect=" + lightRevealEffect + ", opaque=" + z + " revealAmount=" + valueOf + " alpha=" + valueOf2 + " vis=" + valueOf3;
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("LightRevealScrim", arrayList);
        arrayList.add(dumpLog(null, null, null));
        return arrayList;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (canvas != null && this.revealGradientWidth > 0.0f && this.revealGradientHeight > 0.0f) {
            if (!(this.revealAmount == 0.0f)) {
                float f = this.startColorAlpha;
                if (f > 0.0f) {
                    canvas.drawColor(ColorUtilKt.getColorWithAlpha(f, this.revealGradientEndColor));
                }
                Matrix matrix = this.shaderGradientMatrix;
                matrix.setScale(this.revealGradientWidth, this.revealGradientHeight, 0.0f, 0.0f);
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
        if (this.revealAmount >= 1.0f || canvas == null) {
            return;
        }
        canvas.drawColor(this.revealGradientEndColor);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.viewWidth = getMeasuredWidth();
        this.viewHeight = getMeasuredHeight();
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        if (!LsRune.AOD_LIGHT_REVEAL) {
            super.setAlpha(f);
            updateScrimOpaque();
            return;
        }
        float alpha = getAlpha();
        super.setAlpha(f);
        updateScrimOpaque();
        if (alpha == f) {
            return;
        }
        leaveLog$default(this, null, Float.valueOf(alpha), null, 5);
    }

    public final void setPaintColorFilter() {
        this.gradientPaint.setColorFilter(new PorterDuffColorFilter(ColorUtilKt.getColorWithAlpha(this.revealGradientEndColorAlpha, this.revealGradientEndColor), PorterDuff.Mode.MULTIPLY));
        if (LsRune.AOD_LIGHT_REVEAL) {
            this.dimPaint.setColor(ColorUtilKt.getColorWithAlpha(this.revealDimGradientEndColorAlpha, this.revealGradientEndColor));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0043, code lost:
    
        if ((r0 == 1.0f) != false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setRevealAmount(float f) {
        float f2 = this.revealAmount;
        if (f2 == f) {
            return;
        }
        if (LsRune.AOD_LIGHT_REVEAL) {
            this.revealAmount = f;
            this.revealEffect.setRevealAmountOnScrim(f, this);
            updateScrimOpaque();
            if (!SecLsScrimControlHelper.DEBUG) {
                if (!(f == 0.0f)) {
                    if (!(f == 1.0f)) {
                        if (!(f2 == 0.0f)) {
                        }
                    }
                }
            }
            leaveLog$default(this, null, null, Float.valueOf(f2), 3);
        } else {
            this.revealAmount = f;
            this.revealEffect.setRevealAmountOnScrim(f, this);
            updateScrimOpaque();
        }
        Trace.traceCounter(4096L, "light_reveal_amount", (int) (this.revealAmount * 100));
        invalidate();
    }

    public final void setRevealEffect(LightRevealEffect lightRevealEffect) {
        if (Intrinsics.areEqual(this.revealEffect, lightRevealEffect)) {
            return;
        }
        this.revealEffect = lightRevealEffect;
        lightRevealEffect.setRevealAmountOnScrim(this.revealAmount, this);
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
        if (!LsRune.AOD_LIGHT_REVEAL) {
            super.setVisibility(i);
            updateScrimOpaque();
            return;
        }
        int visibility = getVisibility();
        super.setVisibility(i);
        updateScrimOpaque();
        if (visibility != i) {
            leaveLog$default(this, Integer.valueOf(visibility), null, null, 6);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
    
        if (getVisibility() == 0) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateScrimOpaque() {
        boolean z = true;
        if (this.revealAmount == 0.0f) {
            if (getAlpha() == 1.0f) {
            }
        }
        z = false;
        if (this.isScrimOpaque != z) {
            this.isScrimOpaque = z;
            Consumer consumer = this.isScrimOpaqueChangedListener;
            if (consumer == null) {
                consumer = null;
            }
            consumer.accept(Boolean.valueOf(z));
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
        this.revealAmount = 1.0f;
        this.revealEffect = LiftReveal.INSTANCE;
        this.revealGradientCenter = new PointF();
        this.viewWidth = num != null ? num.intValue() : 0;
        this.viewHeight = num2 != null ? num2.intValue() : 0;
        this.revealGradientEndColor = EmergencyPhoneWidget.BG_COLOR;
        this.interpolatedRevealAmount = 1.0f;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(EmergencyPhoneWidget.BG_COLOR);
        this.dimPaint = paint;
        Paint paint2 = new Paint();
        paint2.setShader(new RadialGradient(0.0f, 0.0f, 1.0f, new int[]{0, -1}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        this.gradientPaint = paint2;
        this.shaderGradientMatrix = new Matrix();
        this.revealEffect.setRevealAmountOnScrim(this.revealAmount, this);
        setPaintColorFilter();
        invalidate();
        PanelScreenShotLogger.INSTANCE.addLogProvider("LightRevealScrim", this);
    }
}
