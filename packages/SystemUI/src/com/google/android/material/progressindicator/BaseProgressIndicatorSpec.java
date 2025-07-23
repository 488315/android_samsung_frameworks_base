package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class BaseProgressIndicatorSpec {
    public final int hideAnimationBehavior;
    public final int[] indicatorColors;
    public final int indicatorTrackGapSize;
    public final int showAnimationBehavior;
    public final int trackColor;
    public final int trackCornerRadius;
    public final int trackThickness;

    public BaseProgressIndicatorSpec(Context context, AttributeSet attributeSet, int i, int i2) {
        this.indicatorColors = new int[0];
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.mtrl_progress_track_thickness);
        int[] iArr = R$styleable.BaseProgressIndicator;
        ThemeEnforcement.checkCompatibleTheme(context, attributeSet, i, i2);
        ThemeEnforcement.checkTextAppearance(context, attributeSet, iArr, i, i2, new int[0]);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
        int dimensionPixelSize2 = MaterialResources.getDimensionPixelSize(context, obtainStyledAttributes, 9, dimensionPixelSize);
        this.trackThickness = dimensionPixelSize2;
        this.trackCornerRadius = Math.min(MaterialResources.getDimensionPixelSize(context, obtainStyledAttributes, 8, 0), dimensionPixelSize2 / 2);
        this.showAnimationBehavior = obtainStyledAttributes.getInt(5, 0);
        this.hideAnimationBehavior = obtainStyledAttributes.getInt(1, 0);
        this.indicatorTrackGapSize = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        if (!obtainStyledAttributes.hasValue(2)) {
            this.indicatorColors = new int[]{MaterialColors.getColor(context, R.attr.colorPrimary, -1)};
        } else if (obtainStyledAttributes.peekValue(2).type != 1) {
            this.indicatorColors = new int[]{obtainStyledAttributes.getColor(2, -1)};
        } else {
            int[] intArray = context.getResources().getIntArray(obtainStyledAttributes.getResourceId(2, -1));
            this.indicatorColors = intArray;
            if (intArray.length == 0) {
                throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
            }
        }
        if (obtainStyledAttributes.hasValue(7)) {
            this.trackColor = obtainStyledAttributes.getColor(7, -1);
        } else {
            this.trackColor = this.indicatorColors[0];
            TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.disabledAlpha});
            float f = obtainStyledAttributes2.getFloat(0, 0.2f);
            obtainStyledAttributes2.recycle();
            this.trackColor = MaterialColors.compositeARGBWithAlpha(this.trackColor, (int) (f * 255.0f));
        }
        obtainStyledAttributes.recycle();
    }

    public void validateSpec() {
        if (this.indicatorTrackGapSize < 0) {
            throw new IllegalArgumentException("indicatorTrackGapSize must be >= 0.");
        }
    }
}
