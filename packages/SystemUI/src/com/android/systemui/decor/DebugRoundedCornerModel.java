package com.android.systemui.decor;

import android.graphics.Path;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class DebugRoundedCornerModel {
    public final int height;
    public final Path path;
    public final float scaleX;
    public final float scaleY;
    public final int width;

    public DebugRoundedCornerModel(Path path, int i, int i2, float f, float f2) {
        this.path = path;
        this.width = i;
        this.height = i2;
        this.scaleX = f;
        this.scaleY = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DebugRoundedCornerModel)) {
            return false;
        }
        DebugRoundedCornerModel debugRoundedCornerModel = (DebugRoundedCornerModel) obj;
        return Intrinsics.areEqual(this.path, debugRoundedCornerModel.path) && this.width == debugRoundedCornerModel.width && this.height == debugRoundedCornerModel.height && Float.compare(this.scaleX, debugRoundedCornerModel.scaleX) == 0 && Float.compare(this.scaleY, debugRoundedCornerModel.scaleY) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.scaleY) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.scaleX, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.height, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.width, this.path.hashCode() * 31, 31), 31), 31);
    }

    public final String toString() {
        Path path = this.path;
        StringBuilder sb = new StringBuilder("DebugRoundedCornerModel(path=");
        sb.append(path);
        sb.append(", width=");
        sb.append(this.width);
        sb.append(", height=");
        sb.append(this.height);
        sb.append(", scaleX=");
        sb.append(this.scaleX);
        sb.append(", scaleY=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(sb, this.scaleY, ")");
    }
}
