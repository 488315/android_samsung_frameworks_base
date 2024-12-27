package com.android.systemui.surfaceeffects.glowboxeffect;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GlowBoxConfig {
    public final float blurAmount;
    public final int color;
    public final long duration;
    public final long easeInDuration;
    public final long easeOutDuration;
    public final float endCenterX;
    public final float endCenterY;
    public final float height;
    public final float startCenterX;
    public final float startCenterY;
    public final float width;

    public GlowBoxConfig(float f, float f2, float f3, float f4, float f5, float f6, int i, float f7, long j, long j2, long j3) {
        this.startCenterX = f;
        this.startCenterY = f2;
        this.endCenterX = f3;
        this.endCenterY = f4;
        this.width = f5;
        this.height = f6;
        this.color = i;
        this.blurAmount = f7;
        this.duration = j;
        this.easeInDuration = j2;
        this.easeOutDuration = j3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GlowBoxConfig)) {
            return false;
        }
        GlowBoxConfig glowBoxConfig = (GlowBoxConfig) obj;
        return Float.compare(this.startCenterX, glowBoxConfig.startCenterX) == 0 && Float.compare(this.startCenterY, glowBoxConfig.startCenterY) == 0 && Float.compare(this.endCenterX, glowBoxConfig.endCenterX) == 0 && Float.compare(this.endCenterY, glowBoxConfig.endCenterY) == 0 && Float.compare(this.width, glowBoxConfig.width) == 0 && Float.compare(this.height, glowBoxConfig.height) == 0 && this.color == glowBoxConfig.color && Float.compare(this.blurAmount, glowBoxConfig.blurAmount) == 0 && this.duration == glowBoxConfig.duration && this.easeInDuration == glowBoxConfig.easeInDuration && this.easeOutDuration == glowBoxConfig.easeOutDuration;
    }

    public final int hashCode() {
        return Long.hashCode(this.easeOutDuration) + Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.blurAmount, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.color, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.height, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.width, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.endCenterY, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.endCenterX, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.startCenterY, Float.hashCode(this.startCenterX) * 31, 31), 31), 31), 31), 31), 31), 31), 31, this.duration), 31, this.easeInDuration);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("GlowBoxConfig(startCenterX=");
        sb.append(this.startCenterX);
        sb.append(", startCenterY=");
        sb.append(this.startCenterY);
        sb.append(", endCenterX=");
        sb.append(this.endCenterX);
        sb.append(", endCenterY=");
        sb.append(this.endCenterY);
        sb.append(", width=");
        sb.append(this.width);
        sb.append(", height=");
        sb.append(this.height);
        sb.append(", color=");
        sb.append(this.color);
        sb.append(", blurAmount=");
        sb.append(this.blurAmount);
        sb.append(", duration=");
        sb.append(this.duration);
        sb.append(", easeInDuration=");
        sb.append(this.easeInDuration);
        sb.append(", easeOutDuration=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.easeOutDuration, ")", sb);
    }
}
