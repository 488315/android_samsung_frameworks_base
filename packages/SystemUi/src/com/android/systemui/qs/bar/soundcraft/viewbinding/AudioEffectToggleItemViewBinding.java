package com.android.systemui.qs.bar.soundcraft.viewbinding;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioEffectToggleItemViewBinding {
    public final ImageView icon;
    public final TextView name;
    public final LinearLayout root;

    /* renamed from: switch, reason: not valid java name */
    public final SwitchCompat f832switch;

    public AudioEffectToggleItemViewBinding(View view) {
        this.root = (LinearLayout) view.findViewById(R.id.item_root);
        this.icon = (ImageView) view.findViewById(R.id.item_icon);
        this.name = (TextView) view.findViewById(R.id.item_name);
        this.f832switch = (SwitchCompat) view.findViewById(R.id.item_switch);
    }
}
