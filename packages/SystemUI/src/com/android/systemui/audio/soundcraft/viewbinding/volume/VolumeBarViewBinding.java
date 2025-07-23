package com.android.systemui.audio.soundcraft.viewbinding.volume;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar;
import com.android.systemui.audio.soundcraft.view.volume.VolumeBarView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VolumeBarViewBinding {
    public final VolumeBarView root;
    public final SoundCraftVolumeSeekBar volumeBar;
    public final SoundCraftVolumeIcon volumeIcon;

    public VolumeBarViewBinding(View view) {
        this.root = (VolumeBarView) view.requireViewById(R.id.soundcraft_volume_box);
        this.volumeBar = (SoundCraftVolumeSeekBar) view.requireViewById(R.id.volume_seekbar);
        this.volumeIcon = (SoundCraftVolumeIcon) view.requireViewById(R.id.volume_icon);
    }
}
