package com.android.systemui.volume.dialog.sliders.ui;

import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogOverscrollViewModel;
import com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSliderViewBinder {
    public final SliderHapticsViewModel.Factory hapticsViewModelFactory;
    public final VolumeDialogOverscrollViewModel overscrollViewModel;
    public final VolumeDialogSliderViewModel viewModel;

    public VolumeDialogSliderViewBinder(VolumeDialogSliderViewModel volumeDialogSliderViewModel, VolumeDialogOverscrollViewModel volumeDialogOverscrollViewModel, SliderHapticsViewModel.Factory factory) {
        this.viewModel = volumeDialogSliderViewModel;
        this.overscrollViewModel = volumeDialogOverscrollViewModel;
        this.hapticsViewModelFactory = factory;
    }
}
