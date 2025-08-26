package com.android.systemui.volume.dialog.sliders.dagger;

import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import com.android.systemui.volume.dialog.sliders.ui.VolumeDialogOverscrollViewBinder;
import com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder;

/* loaded from: classes3.dex */
public interface VolumeDialogSliderComponent {

    public interface Factory {
        VolumeDialogSliderComponent create(VolumeDialogSliderType volumeDialogSliderType);
    }

    VolumeDialogOverscrollViewBinder overscrollViewBinder();

    VolumeDialogSliderViewBinder sliderViewBinder();
}
