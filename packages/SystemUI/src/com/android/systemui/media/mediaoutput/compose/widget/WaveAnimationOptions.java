package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WaveAnimationOptions {
    public final boolean animateWave;
    public final int animationSpeedMs;
    public final boolean flatLineOnDrag;
    public final boolean reverse;

    public WaveAnimationOptions() {
        this(false, false, false, 0, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WaveAnimationOptions)) {
            return false;
        }
        WaveAnimationOptions waveAnimationOptions = (WaveAnimationOptions) obj;
        return this.reverse == waveAnimationOptions.reverse && this.flatLineOnDrag == waveAnimationOptions.flatLineOnDrag && this.animateWave == waveAnimationOptions.animateWave && this.animationSpeedMs == waveAnimationOptions.animationSpeedMs;
    }

    public final int hashCode() {
        return Integer.hashCode(this.animationSpeedMs) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.reverse) * 31, 31, this.flatLineOnDrag), 31, this.animateWave);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WaveAnimationOptions(reverse=");
        sb.append(this.reverse);
        sb.append(", flatLineOnDrag=");
        sb.append(this.flatLineOnDrag);
        sb.append(", animateWave=");
        sb.append(this.animateWave);
        sb.append(", animationSpeedMs=");
        return Anchor$$ExternalSyntheticOutline0.m(this.animationSpeedMs, ")", sb);
    }

    public WaveAnimationOptions(boolean z, boolean z2, boolean z3, int i) {
        this.reverse = z;
        this.flatLineOnDrag = z2;
        this.animateWave = z3;
        this.animationSpeedMs = i;
    }

    public /* synthetic */ WaveAnimationOptions(boolean z, boolean z2, boolean z3, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? true : z2, (i2 & 4) != 0 ? true : z3, (i2 & 8) != 0 ? 1000 : i);
    }
}
