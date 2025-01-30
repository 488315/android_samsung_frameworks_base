package com.android.systemui.p016qs.bar.soundcraft.viewbinding;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseCancelingLevelViewBinding {
    public final SeekBar noiseCancelingSeekbar;
    public final LinearLayout root;

    public NoiseCancelingLevelViewBinding(View view) {
        this.root = (LinearLayout) view.findViewById(R.id.item_root);
        this.noiseCancelingSeekbar = (SeekBar) view.findViewById(R.id.noise_canceling_seekbar);
    }
}
