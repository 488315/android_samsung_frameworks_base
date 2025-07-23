package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RelativeCornerSize implements CornerSize {
    public final float percent;

    public RelativeCornerSize(float f) {
        this.percent = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof RelativeCornerSize) && this.percent == ((RelativeCornerSize) obj).percent;
    }

    @Override // com.google.android.material.shape.CornerSize
    public final float getCornerSize(RectF rectF) {
        return Math.min(rectF.width(), rectF.height()) * this.percent;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.percent)});
    }
}
