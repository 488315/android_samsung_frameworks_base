package com.android.systemui.audio.soundcraft.viewbinding.battery;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.battery.BatteryInfoBoxView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BatteryInfoBoxViewBinding {
    public final TextView budsBatteryInfoText;
    public final LinearLayout budsBatteryView;
    public final TextView cradleBatteryInfoText;
    public final LinearLayout cradleBatteryView;
    public final BatteryInfoBoxView root;

    public BatteryInfoBoxViewBinding(View view) {
        this.root = (BatteryInfoBoxView) view.requireViewById(R.id.soundcraft_battery_info_box);
        this.budsBatteryView = (LinearLayout) view.requireViewById(R.id.buds_battery_view);
        this.budsBatteryInfoText = (TextView) view.requireViewById(R.id.buds_battery_info_text);
        this.cradleBatteryView = (LinearLayout) view.requireViewById(R.id.cradle_battery_view);
        this.cradleBatteryInfoText = (TextView) view.requireViewById(R.id.cradle_battery_info_text);
    }
}
