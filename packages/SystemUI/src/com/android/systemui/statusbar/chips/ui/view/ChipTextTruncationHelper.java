package com.android.systemui.statusbar.chips.ui.view;

import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSDetailController$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ChipTextTruncationHelper {
    public SysuiMeasureSpec maximumWidthMeasureSpec;
    public final SysuiMeasureSpec unlimitedWidthMeasureSpec = new SysuiMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, 0));
    public final View view;
    public SysuiMeasureSpec widthMeasureSpec;

    public ChipTextTruncationHelper(View view) {
        this.view = view;
        this.maximumWidthMeasureSpec = new SysuiMeasureSpec(View.MeasureSpec.makeMeasureSpec(SecQSDetailController$$ExternalSyntheticOutline0.m(view, R.dimen.ongoing_activity_chip_max_text_width), Integer.MIN_VALUE));
    }

    public final boolean shouldShowText(int i, SysuiMeasureSpec sysuiMeasureSpec) {
        SysuiMeasureSpec sysuiMeasureSpec2 = this.maximumWidthMeasureSpec;
        if (sysuiMeasureSpec2 == null) {
            sysuiMeasureSpec2 = null;
        }
        int resolveSize = TextView.resolveSize(i, sysuiMeasureSpec2.specInt);
        int resolveSize2 = TextView.resolveSize(i, sysuiMeasureSpec.specInt);
        if (resolveSize2 < resolveSize) {
            this.widthMeasureSpec = sysuiMeasureSpec;
            resolveSize = resolveSize2;
        } else {
            SysuiMeasureSpec sysuiMeasureSpec3 = this.maximumWidthMeasureSpec;
            this.widthMeasureSpec = sysuiMeasureSpec3 != null ? sysuiMeasureSpec3 : null;
        }
        return i <= resolveSize;
    }
}
