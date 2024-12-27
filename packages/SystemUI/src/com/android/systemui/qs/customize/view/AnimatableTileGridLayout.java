package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AnimatableTileGridLayout extends GridLayout {
    public final float SCALE_FACTOR;

    public /* synthetic */ AnimatableTileGridLayout(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static void setViewFraction(View view, float f, float f2, float f3) {
        if (view != null) {
            view.setAlpha(f * f3);
        }
        if (view != null) {
            view.setScaleX(((1.0f - f2) * f3) + f2);
        }
        if (view == null) {
            return;
        }
        view.setScaleY(((1.0f - f2) * f3) + f2);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view) {
        super.addView(view);
        setViewFraction(view, 0.0f, this.SCALE_FACTOR, 0.0f);
    }

    public final void setPosition(float f, int i) {
        if (getChildCount() <= 0) {
            return;
        }
        int columnCount = getColumnCount() * (i + 1);
        for (int columnCount2 = getColumnCount() * i; columnCount2 < columnCount && columnCount2 < getChildCount(); columnCount2++) {
            setViewFraction(getChildAt(columnCount2), 1.0f, this.SCALE_FACTOR, f);
        }
    }

    public AnimatableTileGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.SCALE_FACTOR = 0.85f;
    }
}
