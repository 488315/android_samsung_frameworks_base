package com.android.settingslib.graph;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.android.settingslib.Utils;
import com.android.systemui.R;

/* loaded from: classes.dex */
class BluetoothDeviceLayerDrawable$BatteryMeterDrawable extends BatteryMeterDrawableBase {
    public final float mAspectRatio;
    int mFrameColor;

    public BluetoothDeviceLayerDrawable$BatteryMeterDrawable(Context context, int i, int i2) {
        super(context, i);
        Resources resources = context.getResources();
        this.mButtonHeightFraction = resources.getFraction(R.fraction.bt_battery_button_height_fraction, 1, 1);
        this.mAspectRatio = resources.getFraction(R.fraction.bt_battery_ratio_fraction, 1, 1);
        setColorFilter(new PorterDuffColorFilter(Utils.getColorAttrDefaultColor(context, android.R.attr.colorControlNormal, 0), PorterDuff.Mode.SRC_IN));
        this.mLevel = i2;
        unscheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.BatteryMeterDrawableBase$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.invalidateSelf();
            }
        });
        scheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.BatteryMeterDrawableBase$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
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
