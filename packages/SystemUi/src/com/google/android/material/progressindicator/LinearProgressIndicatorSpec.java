package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LinearProgressIndicatorSpec extends BaseProgressIndicatorSpec {
    public boolean drawHorizontallyInverse;
    public final int indeterminateAnimationType;
    public final int indicatorDirection;

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.linearProgressIndicatorStyle);
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicatorSpec
    public final void validateSpec() {
        if (this.indeterminateAnimationType == 0) {
            if (this.trackCornerRadius > 0) {
                throw new IllegalArgumentException("Rounded corners are not supported in contiguous indeterminate animation.");
            }
            if (this.indicatorColors.length < 3) {
                throw new IllegalArgumentException("Contiguous indeterminate animation must be used with 3 or more indicator colors.");
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 2132019115);
        int i2 = LinearProgressIndicator.$r8$clinit;
    }

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int[] iArr = R$styleable.LinearProgressIndicator;
        int i3 = LinearProgressIndicator.$r8$clinit;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, iArr, R.attr.linearProgressIndicatorStyle, 2132019115, new int[0]);
        this.indeterminateAnimationType = obtainStyledAttributes.getInt(0, 1);
        int i4 = obtainStyledAttributes.getInt(1, 0);
        this.indicatorDirection = i4;
        obtainStyledAttributes.recycle();
        validateSpec();
        this.drawHorizontallyInverse = i4 == 1;
    }
}
