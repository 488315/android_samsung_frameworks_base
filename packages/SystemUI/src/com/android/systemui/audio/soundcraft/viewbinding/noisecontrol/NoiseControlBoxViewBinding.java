package com.android.systemui.audio.soundcraft.viewbinding.noisecontrol;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlBoxView;
import com.android.systemui.audio.soundcraft.view.noisecontrol.NoiseControlLineView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class NoiseControlBoxViewBinding {
    public final ViewGroup boxContainer;
    public final ViewGroup effectView;
    public final ViewGroup noiseCancelingBar;
    public final NoiseControlLineView noiseControlLineView;
    public final NoiseControlBoxView root;

    public NoiseControlBoxViewBinding(View view) {
        NoiseControlBoxView noiseControlBoxView = (NoiseControlBoxView) view.requireViewById(R.id.soundcraft_noise_control_box);
        this.root = noiseControlBoxView;
        this.noiseCancelingBar = (ViewGroup) view.requireViewById(R.id.soundcraft_active_noise_canceling_bar);
        this.boxContainer = (ViewGroup) view.requireViewById(R.id.soundcraft_effect_box_container);
        this.effectView = (ViewGroup) noiseControlBoxView.requireViewById(R.id.soundcraft_noise_control_effect_view);
        this.noiseControlLineView = (NoiseControlLineView) noiseControlBoxView.requireViewById(R.id.soundcraft_effect_line);
    }
}
