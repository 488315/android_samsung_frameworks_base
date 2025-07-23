package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LinearProgressIndicatorSpec extends BaseProgressIndicatorSpec {
    public boolean drawHorizontallyInverse;
    public final int indeterminateAnimationType;
    public final int indicatorDirection;
    public final int trackStopIndicatorSize;

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.linearProgressIndicatorStyle);
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicatorSpec
    public final void validateSpec() {
        super.validateSpec();
        if (this.trackStopIndicatorSize < 0) {
            throw new IllegalArgumentException("Stop indicator size must be >= 0.");
        }
        if (this.indeterminateAnimationType == 0) {
            if (this.trackCornerRadius > 0 && this.indicatorTrackGapSize == 0) {
                throw new IllegalArgumentException("Rounded corners without gap are not supported in contiguous indeterminate animation.");
            }
            if (this.indicatorColors.length < 3) {
                throw new IllegalArgumentException("Contiguous indeterminate animation must be used with 3 or more indicator colors.");
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Widget_MaterialComponents_LinearProgressIndicator);
        int i2 = LinearProgressIndicator.$r8$clinit;
    }

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int[] iArr = R$styleable.LinearProgressIndicator;
        int i3 = LinearProgressIndicator.$r8$clinit;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, iArr, R.attr.linearProgressIndicatorStyle, R.style.Widget_MaterialComponents_LinearProgressIndicator, new int[0]);
        this.indeterminateAnimationType = obtainStyledAttributes.getInt(0, 1);
        int i4 = obtainStyledAttributes.getInt(1, 0);
        this.indicatorDirection = i4;
        this.trackStopIndicatorSize = Math.min(obtainStyledAttributes.getDimensionPixelSize(2, 0), this.trackThickness);
        obtainStyledAttributes.recycle();
        validateSpec();
        this.drawHorizontallyInverse = i4 == 1;
    }
}
