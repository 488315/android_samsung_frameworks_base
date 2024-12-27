package com.android.systemui.audio.soundcraft.viewbinding.noisecontrol;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
