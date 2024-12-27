package com.android.systemui.audio.soundcraft.viewbinding.audioeffect;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.R;

public final class AudioEffectToggleItemViewBinding {
    public final TextView name;
    public final LinearLayout root;

    /* renamed from: switch, reason: not valid java name */
    public final SwitchCompat f23switch;

    public AudioEffectToggleItemViewBinding(View view) {
        this.root = (LinearLayout) view.requireViewById(R.id.item_root);
        this.name = (TextView) view.requireViewById(R.id.item_name);
        this.f23switch = (SwitchCompat) view.requireViewById(R.id.item_switch);
    }
}
