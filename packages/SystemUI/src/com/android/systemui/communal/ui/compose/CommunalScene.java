package com.android.systemui.communal.ui.compose;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.communal.widgets.WidgetInteractionHandler;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.ComposableScene;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalScene implements ComposableScene {
    public final SystemUIDialogFactory dialogFactory;
    public final WidgetInteractionHandler interactionHandler;
    public final CommunalViewModel viewModel;
    public final SceneKey key = Scenes.Communal;
    public final ReadonlyStateFlow destinationScenes = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(MapsKt__MapsJVMKt.mapOf(new Pair(new Swipe(SwipeDirection.Right, 0, null, 6, null), new UserActionResult(Scenes.Lockscreen, null, 2, null)))));

    public CommunalScene(CommunalViewModel communalViewModel, SystemUIDialogFactory systemUIDialogFactory, WidgetInteractionHandler widgetInteractionHandler) {
    }

    @Override // com.android.systemui.scene.ui.composable.ComposableScene
    public final ReadonlyStateFlow getDestinationScenes() {
        return this.destinationScenes;
    }

    @Override // com.android.systemui.scene.ui.composable.ComposableScene
    public final SceneKey getKey() {
        return this.key;
    }
}
