package com.android.systemui.notifications.ui.viewmodel;

import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.systemui.scene.shared.model.SceneFamilies;
import kotlin.collections.MapsKt__MapsKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationsShadeSceneViewModel {
    public final ReadonlyStateFlow destinationScenes;

    public NotificationsShadeSceneViewModel() {
        Swipe.Companion.getClass();
        Swipe swipe = Swipe.Up;
        SceneKey sceneKey = SceneFamilies.Home;
        this.destinationScenes = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(MapsKt__MapsKt.mapOf(swipe.to(sceneKey), Back.INSTANCE.to(sceneKey))));
    }
}
