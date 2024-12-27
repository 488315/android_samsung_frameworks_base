package com.android.systemui.settings.brightness.ui.viewModel;

import android.content.res.Resources;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.MirrorController;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BrightnessMirrorViewModel implements MirrorController {
    public final StateFlowImpl _locationAndSize;
    public final BrightnessSliderController _toggleSlider;
    public final BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor;
    public final ReadonlyStateFlow isShowing;
    public final ReadonlyStateFlow locationAndSize;
    public final Resources resources;
    public final BrightnessSliderController.Factory sliderControllerFactory;
    public final int[] tempPosition = new int[2];

    public BrightnessMirrorViewModel(BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor, Resources resources, BrightnessSliderController.Factory factory) {
        this.brightnessMirrorShowingInteractor = brightnessMirrorShowingInteractor;
        this.resources = resources;
        ReadonlyStateFlow readonlyStateFlow = brightnessMirrorShowingInteractor.isShowing;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new LocationAndSize(0, 0, 0, 7, null));
        this._locationAndSize = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final /* bridge */ /* synthetic */ void addCallback(Object obj) {
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void hideMirror() {
        this.brightnessMirrorShowingInteractor.brightnessMirrorShowingRepository._isShowing.updateState(null, Boolean.FALSE);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final /* bridge */ /* synthetic */ void removeCallback(Object obj) {
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void setLocationAndSize(View view) {
        View rootView;
        int[] iArr = this.tempPosition;
        view.getLocationInWindow(iArr);
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.rounded_slider_background_padding);
        BrightnessSliderController brightnessSliderController = this._toggleSlider;
        if (brightnessSliderController != null && (rootView = brightnessSliderController.getRootView()) != null) {
            rootView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        }
        int i = iArr[1] - dimensionPixelSize;
        int i2 = dimensionPixelSize * 2;
        this._locationAndSize.updateState(null, new LocationAndSize(i, view.getMeasuredWidth() + i2, view.getMeasuredHeight() + i2));
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void showMirror() {
        this.brightnessMirrorShowingInteractor.brightnessMirrorShowingRepository._isShowing.updateState(null, Boolean.TRUE);
    }
}
