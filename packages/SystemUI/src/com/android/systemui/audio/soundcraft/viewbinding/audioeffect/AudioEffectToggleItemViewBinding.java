package com.android.systemui.audio.soundcraft.viewbinding.audioeffect;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.R;

/* loaded from: classes.dex */
public final class AudioEffectToggleItemViewBinding {
    public final TextView name;
    public final LinearLayout root;
    public final TextView status;

    /* renamed from: switch, reason: not valid java name */
    public final SwitchCompat f26switch;

    public AudioEffectToggleItemViewBinding(View view) {
        this.root = (LinearLayout) view.requireViewById(R.id.item_root);
        this.name = (TextView) view.requireViewById(R.id.item_name);
        this.f26switch = (SwitchCompat) view.requireViewById(R.id.item_switch);
        this.status = (TextView) view.requireViewById(R.id.item_status);
    }
}
