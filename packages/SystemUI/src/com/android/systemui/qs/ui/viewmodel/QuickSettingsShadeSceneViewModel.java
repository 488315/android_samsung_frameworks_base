package com.android.systemui.qs.ui.viewmodel;

import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.shade.ui.viewmodel.OverlayShadeViewModel;
import kotlin.collections.MapsKt__MapsKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

public final class QuickSettingsShadeSceneViewModel {
    public final BrightnessSliderViewModel brightnessSliderViewModel;
    public final ReadonlyStateFlow destinationScenes;
    public final EditModeViewModel editModeViewModel;
    public final OverlayShadeViewModel overlayShadeViewModel;
    public final TileGridViewModel tileGridViewModel;

    public QuickSettingsShadeSceneViewModel(OverlayShadeViewModel overlayShadeViewModel, BrightnessSliderViewModel brightnessSliderViewModel, TileGridViewModel tileGridViewModel, EditModeViewModel editModeViewModel, QSSceneAdapter qSSceneAdapter) {
        Swipe.Companion.getClass();
        Swipe swipe = Swipe.Up;
        SceneKey sceneKey = SceneFamilies.Home;
        this.destinationScenes = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(MapsKt__MapsKt.mapOf(swipe.to(sceneKey), Back.INSTANCE.to(sceneKey))));
    }
}
