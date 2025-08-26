package com.android.systemui.settings.brightness.ui.viewModel;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.MirrorController;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class BrightnessMirrorViewModel implements MirrorController {
    public final StateFlowImpl _locationAndSize;
    public final BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor;
    public final StateFlow isShowing;
    public final ReadonlyStateFlow locationAndSize;
    public final Resources resources;
    public final BrightnessSliderController.Factory sliderControllerFactory;
    public final int[] tempPosition = new int[2];

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
    }

    static {
        new Companion(null);
    }

    public BrightnessMirrorViewModel(BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor, Resources resources, BrightnessSliderController.Factory factory) {
        this.brightnessMirrorShowingInteractor = brightnessMirrorShowingInteractor;
        this.resources = resources;
        this.sliderControllerFactory = factory;
        this.isShowing = brightnessMirrorShowingInteractor.isShowing();
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new LocationAndSize(0, 0, 0, 0, 15, null));
        this._locationAndSize = stateFlowImplMutableStateFlow;
        this.locationAndSize = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final /* bridge */ /* synthetic */ void addCallback(Object obj) {
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void hideMirror() {
        this.brightnessMirrorShowingInteractor.setMirrorShowing(false);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final /* bridge */ /* synthetic */ void removeCallback(Object obj) {
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void setLocationAndSize(View view) throws Resources.NotFoundException {
        int[] iArr = this.tempPosition;
        view.getLocationInWindow(iArr);
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.rounded_slider_background_padding);
        int top = 0;
        View view2 = view;
        while (true) {
            if (view2.getId() == R.id.quick_settings_container) {
                break;
            }
            top += view2.getTop();
            Object parent = view2.getParent();
            view2 = parent instanceof View ? (View) parent : null;
            if (view2 == null) {
                Log.wtf("BrightnessMirrorViewModel", "Couldn't find container in parents of " + view);
                break;
            }
        }
        int i = top - dimensionPixelSize;
        int i2 = iArr[1] - dimensionPixelSize;
        int i3 = dimensionPixelSize * 2;
        this._locationAndSize.updateState(null, new LocationAndSize(i, i2, view.getMeasuredWidth() + i3, view.getMeasuredHeight() + i3));
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void showMirror() {
        this.brightnessMirrorShowingInteractor.setMirrorShowing(true);
    }
}
