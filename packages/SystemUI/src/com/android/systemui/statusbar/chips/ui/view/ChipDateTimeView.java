package com.android.systemui.statusbar.chips.ui.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DateTimeView;
import com.android.systemui.R;
import com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ChipDateTimeView extends DateTimeView {
    public final ChipTextTruncationHelper textTruncationHelper;

    /* JADX WARN: Multi-variable type inference failed */
    public ChipDateTimeView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ChipTextTruncationHelper chipTextTruncationHelper = this.textTruncationHelper;
        chipTextTruncationHelper.maximumWidthMeasureSpec = new SysuiMeasureSpec(View.MeasureSpec.makeMeasureSpec(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(chipTextTruncationHelper.view, R.dimen.ongoing_activity_chip_max_text_width), Integer.MIN_VALUE));
    }

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

    public /* synthetic */ ChipDateTimeView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ChipDateTimeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.textTruncationHelper = new ChipTextTruncationHelper(this);
    }
}
