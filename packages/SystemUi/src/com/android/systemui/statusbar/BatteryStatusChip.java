package com.android.systemui.statusbar;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.settingslib.Utils;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.statusbar.events.BackgroundAnimatableView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BatteryStatusChip extends FrameLayout implements BackgroundAnimatableView {
    public final BatteryMeterView batteryMeterView;
    public final LinearLayout roundedContainer;

    /* JADX WARN: Multi-variable type inference failed */
    public BatteryStatusChip(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final int getChipWidth() {
        return getMeasuredWidth();
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final View getContentView() {
        return this.batteryMeterView;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final void setBoundsForAnimation(int i, int i2, int i3, int i4) {
        this.roundedContainer.setLeftTopRightBottom(i - getLeft(), i2 - getTop(), i3 - getLeft(), i4 - getTop());
    }

    public final void updateResources() {
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(R.attr.colorPrimary, getContext(), 0);
        Utils.getColorAttrDefaultColor(R.attr.textColorSecondary, ((FrameLayout) this).mContext, 0);
        this.batteryMeterView.updateColors(colorAttrDefaultColor, colorAttrDefaultColor);
        this.roundedContainer.setBackground(((FrameLayout) this).mContext.getDrawable(com.android.systemui.R.drawable.statusbar_chip_bg));
    }

    public /* synthetic */ BatteryStatusChip(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public BatteryStatusChip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        FrameLayout.inflate(context, com.android.systemui.R.layout.battery_status_chip, this);
        this.roundedContainer = (LinearLayout) findViewById(com.android.systemui.R.id.rounded_container);
        this.batteryMeterView = (BatteryMeterView) findViewById(com.android.systemui.R.id.battery_meter_view);
        updateResources();
    }

    @Override // com.android.systemui.statusbar.events.BackgroundAnimatableView
    public final View getView() {
        return this;
    }
}
