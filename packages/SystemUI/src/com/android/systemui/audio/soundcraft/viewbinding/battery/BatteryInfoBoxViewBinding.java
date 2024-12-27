package com.android.systemui.audio.soundcraft.viewbinding.battery;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.battery.BatteryInfoBoxView;

public final class BatteryInfoBoxViewBinding {
    public final TextView budsBatteryInfoText;
    public final LinearLayout budsBatteryView;
    public final ImageView budsIconLeft;
    public final ImageView budsIconRight;
    public final TextView cradleBatteryInfoText;
    public final LinearLayout cradleBatteryView;
    public final ImageView cradleIcon;
    public final BatteryInfoBoxView root;

    public BatteryInfoBoxViewBinding(View view) {
        this.root = (BatteryInfoBoxView) view.requireViewById(R.id.soundcraft_battery_info_box);
        this.budsBatteryView = (LinearLayout) view.requireViewById(R.id.buds_battery_view);
        this.budsIconLeft = (ImageView) view.requireViewById(R.id.buds_icon_left);
        this.budsIconRight = (ImageView) view.requireViewById(R.id.buds_icon_right);
        this.budsBatteryInfoText = (TextView) view.requireViewById(R.id.buds_battery_info_text);
        this.cradleBatteryView = (LinearLayout) view.requireViewById(R.id.cradle_battery_view);
        this.cradleIcon = (ImageView) view.requireViewById(R.id.cradle_icon);
        this.cradleBatteryInfoText = (TextView) view.requireViewById(R.id.cradle_battery_info_text);
    }
}
