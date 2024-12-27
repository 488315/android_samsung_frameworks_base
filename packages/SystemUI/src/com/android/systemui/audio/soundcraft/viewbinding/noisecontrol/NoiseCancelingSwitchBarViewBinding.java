package com.android.systemui.audio.soundcraft.viewbinding.noisecontrol;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.R;

public final class NoiseCancelingSwitchBarViewBinding {
    public final ImageView icon;
    public final LinearLayout root;

    /* renamed from: switch, reason: not valid java name */
    public final SwitchCompat f24switch;

    public NoiseCancelingSwitchBarViewBinding(View view) {
        this.root = (LinearLayout) view.requireViewById(R.id.item_root);
        this.icon = (ImageView) view.requireViewById(R.id.item_icon);
        this.f24switch = (SwitchCompat) view.requireViewById(R.id.item_switch);
    }
}
