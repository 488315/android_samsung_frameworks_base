package com.samsung.android.sesl.visualeffect.surfaceeffects.ripple;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.util.Log;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader;
import java.util.ArrayList;
import java.util.Comparator;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RippleAnimation {
    public final ValueAnimator animator;
    public final RippleShader rippleShader;

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
    }

    public RippleAnimation(RippleAnimationConfig rippleAnimationConfig) {
        RippleShader rippleShader = new RippleShader();
        this.rippleShader = rippleShader;
        this.animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        rippleShader.setFloatUniform("in_center", rippleAnimationConfig.centerX, rippleAnimationConfig.centerY);
        RippleShader.RippleSize rippleSize = rippleShader.rippleSize;
        float f = rippleAnimationConfig.maxWidth;
        float f2 = rippleAnimationConfig.scale;
        float f3 = rippleAnimationConfig.maxHeight * f2;
        rippleSize.getClass();
        RippleShader.SizeAtProgress[] sizeAtProgressArr = {rippleSize.initialSize, new RippleShader.SizeAtProgress(1.0f, f * f2, f3)};
        ((ArrayList) rippleSize.sizes).clear();
        CollectionsKt__MutableCollectionsKt.addAll(rippleSize.sizes, sizeAtProgressArr);
        ArrayList arrayList = (ArrayList) rippleSize.sizes;
        if (arrayList.size() > 1) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: com.samsung.android.sesl.visualeffect.surfaceeffects.ripple.RippleShader$RippleSize$setSizeAtProgresses$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Float.valueOf(((RippleShader.SizeAtProgress) obj).t), Float.valueOf(((RippleShader.SizeAtProgress) obj2).t));
                }
            });
        }
        rippleShader.setFloatUniform("in_pixelDensity", rippleAnimationConfig.pixelDensity);
        int alphaComponent = ColorUtils.setAlphaComponent(rippleAnimationConfig.color, rippleAnimationConfig.opacity);
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(alphaComponent, "!!!!color:", "sparkleColor");
        rippleShader.setColorUniform("in_color", alphaComponent);
        int i = rippleAnimationConfig.sparkleColor;
        float red = Color.red(i) / 255.0f;
        float green = Color.green(i) / 255.0f;
        float blue = Color.blue(i) / 255.0f;
        StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("!!!!sparkleColor:", red, ",", green, ",");
        m.append(blue);
        m.append(",1.0");
        Log.i("sparkleColor", m.toString());
        rippleShader.setFloatUniform("in_sparkleColor", red, green, blue, 1.0f);
        rippleShader.setFloatUniform("in_sparkle_strength", rippleAnimationConfig.sparkleStrength);
        assignFadeParams(rippleShader.baseRingFadeParams, rippleAnimationConfig.baseRingFadeParams);
        assignFadeParams(rippleShader.sparkleRingFadeParams, rippleAnimationConfig.sparkleRingFadeParams);
        assignFadeParams(rippleShader.centerFillFadeParams, rippleAnimationConfig.centerFillFadeParams);
    }

    public static void assignFadeParams(RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2) {
        if (fadeParams2 != null) {
            fadeParams.fadeInStart = fadeParams2.fadeInStart;
            fadeParams.fadeInEnd = fadeParams2.fadeInEnd;
            fadeParams.fadeOutStart = fadeParams2.fadeOutStart;
            fadeParams.fadeOutEnd = fadeParams2.fadeOutEnd;
        }
    }
}
