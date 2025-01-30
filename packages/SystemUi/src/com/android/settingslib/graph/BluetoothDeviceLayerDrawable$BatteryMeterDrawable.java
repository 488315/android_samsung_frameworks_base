package com.android.settingslib.graph;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.android.settingslib.Utils;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class BluetoothDeviceLayerDrawable$BatteryMeterDrawable extends BatteryMeterDrawableBase {
    public final float mAspectRatio;
    int mFrameColor;

    public BluetoothDeviceLayerDrawable$BatteryMeterDrawable(Context context, int i, int i2) {
        super(context, i);
        Resources resources = context.getResources();
        final int i3 = 1;
        this.mButtonHeightFraction = resources.getFraction(R.fraction.bt_battery_button_height_fraction, 1, 1);
        this.mAspectRatio = resources.getFraction(R.fraction.bt_battery_ratio_fraction, 1, 1);
        final int i4 = 0;
        setColorFilter(new PorterDuffColorFilter(Utils.getColorAttrDefaultColor(android.R.attr.colorControlNormal, context, 0), PorterDuff.Mode.SRC_IN));
        this.mLevel = i2;
        unscheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.BatteryMeterDrawableBase$$ExternalSyntheticLambda0
            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // java.lang.Runnable
            public final void run() {
                switch (i4) {
                }
                this.invalidateSelf();
            }
        });
        scheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.BatteryMeterDrawableBase$$ExternalSyntheticLambda0
            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
            @Override // java.lang.Runnable
            public final void run() {
                switch (i3) {
                }
                this.invalidateSelf();
            }
        }, 0L);
        this.mFrameColor = i;
    }

    @Override // com.android.settingslib.graph.BatteryMeterDrawableBase
    public final float getAspectRatio() {
        return this.mAspectRatio;
    }

    @Override // com.android.settingslib.graph.BatteryMeterDrawableBase
    public final float getRadiusRatio() {
        return 0.0f;
    }
}
