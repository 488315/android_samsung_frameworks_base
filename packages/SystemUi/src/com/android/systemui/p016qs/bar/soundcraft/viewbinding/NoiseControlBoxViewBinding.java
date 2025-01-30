package com.android.systemui.p016qs.bar.soundcraft.viewbinding;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.p016qs.bar.soundcraft.view.noisecontrol.NoiseControlBoxView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoiseControlBoxViewBinding {
    public final ViewGroup boxContainer;
    public final ViewGroup effectView;
    public final ImageView endDrawable;
    public final ImageView middleDrawable;
    public final ViewGroup noiseCancelingBar;
    public final NoiseControlBoxView root;
    public final ImageView startDrawable;

    public NoiseControlBoxViewBinding(View view) {
        NoiseControlBoxView noiseControlBoxView = (NoiseControlBoxView) view.findViewById(R.id.soundcraft_noise_control_box);
        this.root = noiseControlBoxView;
        this.noiseCancelingBar = (ViewGroup) view.findViewById(R.id.soundcraft_active_noise_canceling_bar);
        this.boxContainer = (ViewGroup) view.findViewById(R.id.soundcraft_effect_box_container);
        this.startDrawable = (ImageView) view.findViewById(R.id.soundcraft_effect_box_start);
        this.middleDrawable = (ImageView) view.findViewById(R.id.soundcraft_effect_box_middle);
        this.endDrawable = (ImageView) view.findViewById(R.id.soundcraft_effect_box_end);
        this.effectView = (ViewGroup) noiseControlBoxView.findViewById(R.id.soundcraft_noise_control_effect_view);
    }
}
