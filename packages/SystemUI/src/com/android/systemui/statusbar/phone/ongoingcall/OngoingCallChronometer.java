package com.android.systemui.statusbar.phone.ongoingcall;

import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import com.android.systemui.BasicRune;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class OngoingCallChronometer extends Chronometer {
    public IndicatorGardenPresenter indicatorGardenPresenter;
    public boolean isEasyModeOn;
    public boolean isLandscape;
    public boolean isMainDisplay;
    public boolean isRTL;
    public boolean isRunningTimer;
    public boolean isShowingOAChip;
    public final int[] layoutLocation;
    public int minimumTextWidth;
    public SlimIndicatorViewMediator slimIndicatorViewMediator;
    public int startXOnScreen;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public OngoingCallChronometer(Context context) {
        this(context, null, 0, 6, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateValues();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateValues();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        getLocationOnScreen(this.layoutLocation);
        this.startXOnScreen = this.layoutLocation[0];
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x001c, code lost:
    
        if (r4.isLandscape == false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x005d, code lost:
    
        if (r4.startXOnScreen < (((r0 == null || (r0 = r0.cachedGardenModel) == null) ? 0 : r0.maxWidthLeftContainer) / 2)) goto L47;
     */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        if (this.isRunningTimer) {
            if (!DeviceType.isTablet()) {
                if (!BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    SlimIndicatorViewMediator slimIndicatorViewMediator = this.slimIndicatorViewMediator;
                    boolean zShowAmPmClock = slimIndicatorViewMediator != null ? ((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator).showAmPmClock() : false;
                    SlimIndicatorViewMediator slimIndicatorViewMediator2 = this.slimIndicatorViewMediator;
                    boolean zShowDateClock = slimIndicatorViewMediator2 != null ? ((SlimIndicatorViewMediatorImpl) slimIndicatorViewMediator2).showDateClock() : false;
                    boolean z = this.isLandscape;
                    if (z || (!zShowAmPmClock && !zShowDateClock)) {
                        if (!z && this.isShowingOAChip) {
                            if (getVisibility() == 0) {
                                IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
                            }
                        }
                    }
                } else if (!this.isMainDisplay) {
                    if (this.isEasyModeOn) {
                    }
                }
            }
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(0, 0), i2);
            int measuredWidth = getMeasuredWidth();
            if (measuredWidth > Chronometer.resolveSize(measuredWidth, i)) {
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
            return;
        }
        setMeasuredDimension(0, 0);
    }

    @Override // android.widget.Chronometer
    public final void setBase(long j) {
        this.minimumTextWidth = 0;
        setVisibility(0);
        super.setBase(j);
    }

    public final void updateValues() {
        Configuration configuration = getContext().getResources().getConfiguration();
        boolean z = configuration.orientation == 2;
        if (this.isLandscape != z) {
            this.isLandscape = z;
            requestLayout();
        }
        boolean z2 = configuration.semDisplayDeviceType == 0;
        if (this.isMainDisplay != z2) {
            this.isMainDisplay = z2;
            requestLayout();
        }
        boolean z3 = configuration.getLayoutDirection() == 1;
        if (this.isRTL != z3) {
            this.isRTL = z3;
            requestLayout();
        }
        boolean z4 = Settings.System.getIntForUser(((Chronometer) this).mContext.getContentResolver(), SettingsHelper.INDEX_EASY_MODE_SWITCH, 1, -2) == 0;
        if (this.isEasyModeOn != z4) {
            this.isEasyModeOn = z4;
            requestLayout();
        }
    }

    public OngoingCallChronometer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ OngoingCallChronometer(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public OngoingCallChronometer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.layoutLocation = new int[2];
    }
}
