package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public final class ChevronImageView extends ImageView {
    public ChevronImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean resolveLayoutDirection() {
        int layoutDirection = getLayoutDirection();
        boolean resolveLayoutDirection = super.resolveLayoutDirection();
        if (resolveLayoutDirection && getLayoutDirection() != layoutDirection) {
            onRtlPropertiesChanged(getLayoutDirection());
        }
        return resolveLayoutDirection;
    }
}
