package com.android.systemui.statusbar.phone.ongoingcall;

import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import com.android.systemui.BasicRune;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingCallChronometer extends Chronometer {
    public boolean isEasyModeOn;
    public boolean isEnoughTimerWidth;
    public boolean isLandscape;
    public boolean isMainDisplay;
    public boolean isRunningTimer;
    public int minimumTextWidth;

    public OngoingCallChronometer(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z = configuration != null && configuration.orientation == 2;
        if (this.isLandscape != z) {
            this.isLandscape = z;
            requestLayout();
        }
        boolean z2 = configuration != null && configuration.semDisplayDeviceType == 0;
        if (this.isMainDisplay != z2) {
            this.isMainDisplay = z2;
            requestLayout();
        }
        boolean z3 = Settings.System.getIntForUser(((Chronometer) this).mContext.getContentResolver(), SettingsHelper.INDEX_EASY_MODE_SWITCH, 1, -2) == 0;
        if (this.isEasyModeOn != z3) {
            this.isEasyModeOn = z3;
            requestLayout();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        boolean z;
        if (!this.isRunningTimer || (!this.isEnoughTimerWidth && !this.isLandscape && !DeviceType.isTablet() && ((((z = this.isMainDisplay) || this.isEasyModeOn) && !z) || !BasicRune.BASIC_FOLDABLE_TYPE_FOLD))) {
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
