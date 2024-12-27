package com.android.systemui.notifications.ui.viewmodel;

import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.systemui.scene.shared.model.SceneFamilies;
import kotlin.collections.MapsKt__MapsKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

public final class NotificationsShadeSceneViewModel {
    public final ReadonlyStateFlow destinationScenes;

    public NotificationsShadeSceneViewModel() {
        Swipe.Companion.getClass();
        Swipe swipe = Swipe.Up;
        SceneKey sceneKey = SceneFamilies.Home;
        this.destinationScenes = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(MapsKt__MapsKt.mapOf(swipe.to(sceneKey), Back.INSTANCE.to(sceneKey))));
    }
}
