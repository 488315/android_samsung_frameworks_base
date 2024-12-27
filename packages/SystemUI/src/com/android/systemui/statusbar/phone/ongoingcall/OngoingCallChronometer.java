package com.android.systemui.statusbar.phone.ongoingcall;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import com.android.systemui.util.DeviceType;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class OngoingCallChronometer extends Chronometer {
    public boolean isEnoughTimerWidth;
    public boolean isLandscape;
    public boolean isRunningTimer;
    public int minimumTextWidth;

    public OngoingCallChronometer(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z = false;
        if (configuration != null && configuration.orientation == 2) {
            z = true;
        }
        if (this.isLandscape != z) {
            this.isLandscape = z;
            requestLayout();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        if (!this.isRunningTimer || (!this.isEnoughTimerWidth && !this.isLandscape && !DeviceType.isTablet())) {
            setMeasuredDimension(0, 0);
            return;
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(0, 0), i2);
        int measuredWidth = getMeasuredWidth();
        if (measuredWidth > Chronometer.resolveSize(measuredWidth, i)) {
            if (this.isEnoughTimerWidth) {
                this.isEnoughTimerWidth = false;
                requestLayout();
            }
            setVisibility(8);
            setMeasuredDimension(0, 0);
            return;
        }
        int i3 = this.minimumTextWidth;
        if (measuredWidth < i3) {
            measuredWidth = i3;
        }
        this.minimumTextWidth = measuredWidth;
        setMeasuredDimension(measuredWidth, View.MeasureSpec.getSize(i2));
    }

    @Override // android.widget.Chronometer
    public final void setBase(long j) {
        this.minimumTextWidth = 0;
        if (!this.isEnoughTimerWidth) {
            this.isEnoughTimerWidth = true;
            requestLayout();
        }
        setVisibility(0);
        super.setBase(j);
    }

    public OngoingCallChronometer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ OngoingCallChronometer(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public OngoingCallChronometer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
