package com.android.keyguard;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class PinShapeNonHintingView extends LinearLayout {
    public final Rect mFirstChildVisibleRect;
    public final int mPosition;
    public final ValueAnimator mValueAnimator;

    public PinShapeNonHintingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Utils.getColorAttr(R.^attr-private.materialColorOnTertiaryFixed, getContext()).getDefaultColor();
        ValueAnimator.ofFloat(1.0f, 0.0f);
        this.mFirstChildVisibleRect = new Rect();
        new PinShapeAdapter(context);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (getChildCount() > 2) {
            boolean z2 = false;
            View childAt = getChildAt(0);
            boolean localVisibleRect = childAt.getLocalVisibleRect(this.mFirstChildVisibleRect);
            Rect rect = this.mFirstChildVisibleRect;
            if (rect.right - rect.left < childAt.getWidth() && childAt.getScaleX() == 1.0f) {
                z2 = true;
            }
            if (!localVisibleRect || z2) {
                setGravity(8388629);
                return;
            }
        }
        setGravity(17);
    }
}
