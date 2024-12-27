package com.android.systemui.surfaceeffects.turbulencenoise;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import java.util.Random;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TurbulenceNoiseAnimationConfig {
    public static final Random random;
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
    public final float noiseOffsetX;
    public final float noiseOffsetY;
    public final float noiseOffsetZ;
    public final float pixelDensity;
    public final int screenColor;
    public final boolean shouldInverseNoiseLuminosity;
    public final float width;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        random = new Random();
    }

    public TurbulenceNoiseAnimationConfig() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, 524287, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TurbulenceNoiseAnimationConfig)) {
            return false;
        }
        TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = (TurbulenceNoiseAnimationConfig) obj;
        return Float.compare(this.gridCount, turbulenceNoiseAnimationConfig.gridCount) == 0 && Float.compare(this.luminosityMultiplier, turbulenceNoiseAnimationConfig.luminosityMultiplier) == 0 && Float.compare(this.noiseOffsetX, turbulenceNoiseAnimationConfig.noiseOffsetX) == 0 && Float.compare(this.noiseOffsetY, turbulenceNoiseAnimationConfig.noiseOffsetY) == 0 && Float.compare(this.noiseOffsetZ, turbulenceNoiseAnimationConfig.noiseOffsetZ) == 0 && Float.compare(this.noiseMoveSpeedX, turbulenceNoiseAnimationConfig.noiseMoveSpeedX) == 0 && Float.compare(this.noiseMoveSpeedY, turbulenceNoiseAnimationConfig.noiseMoveSpeedY) == 0 && Float.compare(this.noiseMoveSpeedZ, turbulenceNoiseAnimationConfig.noiseMoveSpeedZ) == 0 && this.color == turbulenceNoiseAnimationConfig.color && this.screenColor == turbulenceNoiseAnimationConfig.screenColor && Float.compare(this.width, turbulenceNoiseAnimationConfig.width) == 0 && Float.compare(this.height, turbulenceNoiseAnimationConfig.height) == 0 && Float.compare(this.maxDuration, turbulenceNoiseAnimationConfig.maxDuration) == 0 && Float.compare(this.easeInDuration, turbulenceNoiseAnimationConfig.easeInDuration) == 0 && Float.compare(this.easeOutDuration, turbulenceNoiseAnimationConfig.easeOutDuration) == 0 && Float.compare(this.pixelDensity, turbulenceNoiseAnimationConfig.pixelDensity) == 0 && Float.compare(this.lumaMatteBlendFactor, turbulenceNoiseAnimationConfig.lumaMatteBlendFactor) == 0 && Float.compare(this.lumaMatteOverallBrightness, turbulenceNoiseAnimationConfig.lumaMatteOverallBrightness) == 0 && this.shouldInverseNoiseLuminosity == turbulenceNoiseAnimationConfig.shouldInverseNoiseLuminosity;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.shouldInverseNoiseLuminosity) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.lumaMatteOverallBrightness, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.lumaMatteBlendFactor, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pixelDensity, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.easeOutDuration, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.easeInDuration, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxDuration, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.height, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.width, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.screenColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.color, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.noiseMoveSpeedZ, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.noiseMoveSpeedY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.noiseMoveSpeedX, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.noiseOffsetZ, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.noiseOffsetY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.noiseOffsetX, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.luminosityMultiplier, Float.hashCode(this.gridCount) * 31, 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TurbulenceNoiseAnimationConfig(gridCount=");
        sb.append(this.gridCount);
        sb.append(", luminosityMultiplier=");
        sb.append(this.luminosityMultiplier);
        sb.append(", noiseOffsetX=");
        sb.append(this.noiseOffsetX);
        sb.append(", noiseOffsetY=");
        sb.append(this.noiseOffsetY);
        sb.append(", noiseOffsetZ=");
        sb.append(this.noiseOffsetZ);
        sb.append(", noiseMoveSpeedX=");
        sb.append(this.noiseMoveSpeedX);
        sb.append(", noiseMoveSpeedY=");
        sb.append(this.noiseMoveSpeedY);
        sb.append(", noiseMoveSpeedZ=");
        sb.append(this.noiseMoveSpeedZ);
        sb.append(", color=");
        sb.append(this.color);
        sb.append(", screenColor=");
        sb.append(this.screenColor);
        sb.append(", width=");
        sb.append(this.width);
        sb.append(", height=");
        sb.append(this.height);
        sb.append(", maxDuration=");
        sb.append(this.maxDuration);
        sb.append(", easeInDuration=");
        sb.append(this.easeInDuration);
        sb.append(", easeOutDuration=");
        sb.append(this.easeOutDuration);
        sb.append(", pixelDensity=");
        sb.append(this.pixelDensity);
        sb.append(", lumaMatteBlendFactor=");
        sb.append(this.lumaMatteBlendFactor);
        sb.append(", lumaMatteOverallBrightness=");
        sb.append(this.lumaMatteOverallBrightness);
        sb.append(", shouldInverseNoiseLuminosity=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.shouldInverseNoiseLuminosity, ")");
    }

    public TurbulenceNoiseAnimationConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i, int i2, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, boolean z) {
        this.gridCount = f;
        this.luminosityMultiplier = f2;
        this.noiseOffsetX = f3;
        this.noiseOffsetY = f4;
        this.noiseOffsetZ = f5;
        this.noiseMoveSpeedX = f6;
        this.noiseMoveSpeedY = f7;
        this.noiseMoveSpeedZ = f8;
        this.color = i;
        this.screenColor = i2;
        this.width = f9;
        this.height = f10;
        this.maxDuration = f11;
        this.easeInDuration = f12;
        this.easeOutDuration = f13;
        this.pixelDensity = f14;
        this.lumaMatteBlendFactor = f15;
        this.lumaMatteOverallBrightness = f16;
        this.shouldInverseNoiseLuminosity = z;
    }

    public /* synthetic */ TurbulenceNoiseAnimationConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i, int i2, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 1.2f : f, (i3 & 2) != 0 ? 1.0f : f2, (i3 & 4) != 0 ? random.nextFloat() : f3, (i3 & 8) != 0 ? random.nextFloat() : f4, (i3 & 16) != 0 ? random.nextFloat() : f5, (i3 & 32) != 0 ? 0.0f : f6, (i3 & 64) != 0 ? 0.0f : f7, (i3 & 128) != 0 ? 0.3f : f8, (i3 & 256) != 0 ? -1 : i, (i3 & 512) != 0 ? -16777216 : i2, (i3 & 1024) != 0 ? 0.0f : f9, (i3 & 2048) != 0 ? 0.0f : f10, (i3 & 4096) != 0 ? 30000.0f : f11, (i3 & 8192) != 0 ? 750.0f : f12, (i3 & 16384) == 0 ? f13 : 750.0f, (32768 & i3) != 0 ? 1.0f : f14, (i3 & 65536) != 0 ? 1.0f : f15, (i3 & 131072) != 0 ? 0.0f : f16, (i3 & 262144) != 0 ? false : z);
    }
}
