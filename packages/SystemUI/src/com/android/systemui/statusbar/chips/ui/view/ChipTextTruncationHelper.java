package com.android.systemui.statusbar.chips.ui.view;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public final class ChipTextTruncationHelper {
    public SysuiMeasureSpec maximumWidthMeasureSpec;
    public final SysuiMeasureSpec unlimitedWidthMeasureSpec = new SysuiMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0));
    public final View view;
    public SysuiMeasureSpec widthMeasureSpec;

    public ChipTextTruncationHelper(View view) {
        this.view = view;
        this.maximumWidthMeasureSpec = new SysuiMeasureSpec(View.MeasureSpec.makeMeasureSpec(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view, R.dimen.ongoing_activity_chip_max_text_width), Integer.MIN_VALUE));
    }

    public final boolean shouldShowText(int i, SysuiMeasureSpec sysuiMeasureSpec) {
        SysuiMeasureSpec sysuiMeasureSpec2 = this.maximumWidthMeasureSpec;
        if (sysuiMeasureSpec2 == null) {
            sysuiMeasureSpec2 = null;
        }
        int iResolveSize = TextView.resolveSize(i, sysuiMeasureSpec2.specInt);
        int iResolveSize2 = TextView.resolveSize(i, sysuiMeasureSpec.specInt);
        if (iResolveSize2 < iResolveSize) {
            this.widthMeasureSpec = sysuiMeasureSpec;
            iResolveSize = iResolveSize2;
        } else {
            SysuiMeasureSpec sysuiMeasureSpec3 = this.maximumWidthMeasureSpec;
            this.widthMeasureSpec = sysuiMeasureSpec3 != null ? sysuiMeasureSpec3 : null;
        }
        return i <= iResolveSize;
    }
}
