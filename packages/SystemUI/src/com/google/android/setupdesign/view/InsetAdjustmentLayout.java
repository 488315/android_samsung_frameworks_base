package com.google.android.setupdesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;

/* loaded from: classes4.dex */
public class InsetAdjustmentLayout extends LinearLayout {
    public static final Logger LOG = new Logger("InsetAdjustmentLayout");

    public InsetAdjustmentLayout(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (PartnerConfigHelper.isGlifExpressiveEnabled(getContext()) && windowInsets.getSystemWindowInsetBottom() > 0) {
            LOG.atDebug("NavigationBarHeight: " + windowInsets.getSystemWindowInsetBottom());
            windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), 0);
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    public InsetAdjustmentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public InsetAdjustmentLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
