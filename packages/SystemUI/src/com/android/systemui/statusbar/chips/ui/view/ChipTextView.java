package com.android.systemui.statusbar.chips.ui.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ChipTextView extends TextView {
    public final ChipTextTruncationHelper textTruncationHelper;

    public ChipTextView(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ChipTextTruncationHelper chipTextTruncationHelper = this.textTruncationHelper;
        chipTextTruncationHelper.maximumWidthMeasureSpec = new SysuiMeasureSpec(View.MeasureSpec.makeMeasureSpec(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(chipTextTruncationHelper.view, R.dimen.ongoing_activity_chip_max_text_width), Integer.MIN_VALUE));
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(this.textTruncationHelper.unlimitedWidthMeasureSpec.specInt, i2);
        if (!this.textTruncationHelper.shouldShowText(getMeasuredWidth(), new SysuiMeasureSpec(i))) {
            setVisibility(8);
            setMeasuredDimension(0, 0);
        } else {
            SysuiMeasureSpec sysuiMeasureSpec = this.textTruncationHelper.widthMeasureSpec;
            if (sysuiMeasureSpec == null) {
                sysuiMeasureSpec = null;
            }
            super.onMeasure(sysuiMeasureSpec.specInt, i2);
        }
    }

    public ChipTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ ChipTextView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public ChipTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.textTruncationHelper = new ChipTextTruncationHelper(this);
    }
}
