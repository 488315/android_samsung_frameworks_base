package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import kotlinx.coroutines.flow.ReadonlyStateFlow;

public interface SliderViewModel {
    ReadonlyStateFlow getSlider();

    void onValueChangeFinished();

    void onValueChanged(SliderState sliderState, float f);

    void toggleMuted(SliderState sliderState);
}
