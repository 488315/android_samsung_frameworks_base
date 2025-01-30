package com.android.systemui.surfaceeffects.turbulencenoise;

import android.graphics.BlendMode;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TurbulenceNoiseAnimationConfig {
    public static final BlendMode DEFAULT_BLEND_MODE;
    public final int backgroundColor;
    public final BlendMode blendMode;
    public final int color;
    public final float easeInDuration;
    public final float easeOutDuration;
    public final float gridCount;
    public final float height;
    public final float lumaMatteBlendFactor;
    public final float lumaMatteOverallBrightness;
    public final float luminosityMultiplier;
    public final float maxDuration;
    public final float noiseMoveSpeedX;
    public final float noiseMoveSpeedY;
    public final float noiseMoveSpeedZ;
    public final Runnable onAnimationEnd;
    public final int opacity;
    public final float pixelDensity;
    public final float width;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        DEFAULT_BLEND_MODE = BlendMode.SRC_OVER;
    }

    public TurbulenceNoiseAnimationConfig() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, 0.0f, 0.0f, 262143, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TurbulenceNoiseAnimationConfig)) {
            return false;
        }
        TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = (TurbulenceNoiseAnimationConfig) obj;
        return Float.compare(this.gridCount, turbulenceNoiseAnimationConfig.gridCount) == 0 && Float.compare(this.luminosityMultiplier, turbulenceNoiseAnimationConfig.luminosityMultiplier) == 0 && Float.compare(this.noiseMoveSpeedX, turbulenceNoiseAnimationConfig.noiseMoveSpeedX) == 0 && Float.compare(this.noiseMoveSpeedY, turbulenceNoiseAnimationConfig.noiseMoveSpeedY) == 0 && Float.compare(this.noiseMoveSpeedZ, turbulenceNoiseAnimationConfig.noiseMoveSpeedZ) == 0 && this.color == turbulenceNoiseAnimationConfig.color && this.backgroundColor == turbulenceNoiseAnimationConfig.backgroundColor && this.opacity == turbulenceNoiseAnimationConfig.opacity && Float.compare(this.width, turbulenceNoiseAnimationConfig.width) == 0 && Float.compare(this.height, turbulenceNoiseAnimationConfig.height) == 0 && Float.compare(this.maxDuration, turbulenceNoiseAnimationConfig.maxDuration) == 0 && Float.compare(this.easeInDuration, turbulenceNoiseAnimationConfig.easeInDuration) == 0 && Float.compare(this.easeOutDuration, turbulenceNoiseAnimationConfig.easeOutDuration) == 0 && Float.compare(this.pixelDensity, turbulenceNoiseAnimationConfig.pixelDensity) == 0 && this.blendMode == turbulenceNoiseAnimationConfig.blendMode && Intrinsics.areEqual(this.onAnimationEnd, turbulenceNoiseAnimationConfig.onAnimationEnd) && Float.compare(this.lumaMatteBlendFactor, turbulenceNoiseAnimationConfig.lumaMatteBlendFactor) == 0 && Float.compare(this.lumaMatteOverallBrightness, turbulenceNoiseAnimationConfig.lumaMatteOverallBrightness) == 0;
    }

    public final int hashCode() {
        int hashCode = (this.blendMode.hashCode() + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.pixelDensity, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.easeOutDuration, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.easeInDuration, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.maxDuration, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.height, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.width, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.opacity, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.backgroundColor, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.color, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.noiseMoveSpeedZ, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.noiseMoveSpeedY, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.noiseMoveSpeedX, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.luminosityMultiplier, Float.hashCode(this.gridCount) * 31, 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31)) * 31;
        Runnable runnable = this.onAnimationEnd;
        return Float.hashCode(this.lumaMatteOverallBrightness) + UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.lumaMatteBlendFactor, (hashCode + (runnable == null ? 0 : runnable.hashCode())) * 31, 31);
    }

    public final String toString() {
        return "TurbulenceNoiseAnimationConfig(gridCount=" + this.gridCount + ", luminosityMultiplier=" + this.luminosityMultiplier + ", noiseMoveSpeedX=" + this.noiseMoveSpeedX + ", noiseMoveSpeedY=" + this.noiseMoveSpeedY + ", noiseMoveSpeedZ=" + this.noiseMoveSpeedZ + ", color=" + this.color + ", backgroundColor=" + this.backgroundColor + ", opacity=" + this.opacity + ", width=" + this.width + ", height=" + this.height + ", maxDuration=" + this.maxDuration + ", easeInDuration=" + this.easeInDuration + ", easeOutDuration=" + this.easeOutDuration + ", pixelDensity=" + this.pixelDensity + ", blendMode=" + this.blendMode + ", onAnimationEnd=" + this.onAnimationEnd + ", lumaMatteBlendFactor=" + this.lumaMatteBlendFactor + ", lumaMatteOverallBrightness=" + this.lumaMatteOverallBrightness + ")";
    }

    public TurbulenceNoiseAnimationConfig(float f, float f2, float f3, float f4, float f5, int i, int i2, int i3, float f6, float f7, float f8, float f9, float f10, float f11, BlendMode blendMode, Runnable runnable, float f12, float f13) {
        this.gridCount = f;
        this.luminosityMultiplier = f2;
        this.noiseMoveSpeedX = f3;
        this.noiseMoveSpeedY = f4;
        this.noiseMoveSpeedZ = f5;
        this.color = i;
        this.backgroundColor = i2;
        this.opacity = i3;
        this.width = f6;
        this.height = f7;
        this.maxDuration = f8;
        this.easeInDuration = f9;
        this.easeOutDuration = f10;
        this.pixelDensity = f11;
        this.blendMode = blendMode;
        this.onAnimationEnd = runnable;
        this.lumaMatteBlendFactor = f12;
        this.lumaMatteOverallBrightness = f13;
    }

    public /* synthetic */ TurbulenceNoiseAnimationConfig(float f, float f2, float f3, float f4, float f5, int i, int i2, int i3, float f6, float f7, float f8, float f9, float f10, float f11, BlendMode blendMode, Runnable runnable, float f12, float f13, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 1.2f : f, (i4 & 2) != 0 ? 1.0f : f2, (i4 & 4) != 0 ? 0.0f : f3, (i4 & 8) != 0 ? 0.0f : f4, (i4 & 16) != 0 ? 0.3f : f5, (i4 & 32) != 0 ? -1 : i, (i4 & 64) != 0 ? EmergencyPhoneWidget.BG_COLOR : i2, (i4 & 128) != 0 ? 150 : i3, (i4 & 256) != 0 ? 0.0f : f6, (i4 & 512) != 0 ? 0.0f : f7, (i4 & 1024) != 0 ? 30000.0f : f8, (i4 & 2048) != 0 ? 750.0f : f9, (i4 & 4096) == 0 ? f10 : 750.0f, (i4 & 8192) != 0 ? 1.0f : f11, (i4 & 16384) != 0 ? DEFAULT_BLEND_MODE : blendMode, (i4 & 32768) != 0 ? null : runnable, (i4 & 65536) != 0 ? 1.0f : f12, (i4 & 131072) != 0 ? 0.0f : f13);
    }
}
