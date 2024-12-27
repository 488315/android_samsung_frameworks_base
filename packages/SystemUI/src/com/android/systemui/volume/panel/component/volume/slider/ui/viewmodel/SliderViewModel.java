package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface SliderViewModel {
    ReadonlyStateFlow getSlider();

    void onValueChangeFinished();

    void onValueChanged(SliderState sliderState, float f);

    void toggleMuted(SliderState sliderState);
}
