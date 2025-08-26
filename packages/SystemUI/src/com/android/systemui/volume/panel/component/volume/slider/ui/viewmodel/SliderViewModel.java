package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public interface SliderViewModel {
    ReadonlyStateFlow getSlider();

    SliderHapticsViewModel.Factory getSliderHapticsViewModelFactory();

    void onValueChangeFinished();

    void onValueChanged(SliderState sliderState, float f);

    void toggleMuted(SliderState sliderState);
}
