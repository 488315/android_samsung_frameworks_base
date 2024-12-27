package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
