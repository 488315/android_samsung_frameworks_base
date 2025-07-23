package com.android.systemui.volume.dialog.sliders.dagger;

import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import com.android.systemui.volume.dialog.sliders.ui.VolumeDialogOverscrollViewBinder;
import com.android.systemui.volume.dialog.sliders.ui.VolumeDialogSliderViewBinder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface VolumeDialogSliderComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        VolumeDialogSliderComponent create(VolumeDialogSliderType volumeDialogSliderType);
    }

    VolumeDialogOverscrollViewBinder overscrollViewBinder();

    VolumeDialogSliderViewBinder sliderViewBinder();
}
