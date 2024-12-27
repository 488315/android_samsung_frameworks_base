package com.android.systemui.audio.soundcraft.viewbinding.volume;

import android.view.View;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIcon;
import com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeSeekBar;
import com.android.systemui.audio.soundcraft.view.volume.VolumeBarView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class VolumeBarViewBinding {
    public final VolumeBarView root;
    public final SoundCraftVolumeSeekBar volumeBar;
    public final SoundCraftVolumeIcon volumeIcon;
    public final ImageView volumeStatusIcon;

    public VolumeBarViewBinding(View view) {
        this.root = (VolumeBarView) view.requireViewById(R.id.soundcraft_volume_box);
        this.volumeBar = (SoundCraftVolumeSeekBar) view.requireViewById(R.id.volume_seekbar);
        this.volumeIcon = (SoundCraftVolumeIcon) view.requireViewById(R.id.volume_icon);
        this.volumeStatusIcon = (ImageView) view.requireViewById(R.id.volume_status_icon);
    }
}
