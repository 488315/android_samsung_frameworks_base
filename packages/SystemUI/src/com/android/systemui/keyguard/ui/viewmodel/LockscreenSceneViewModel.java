package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import com.android.systemui.util.kotlin.MapUtilsKt;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LockscreenSceneViewModel {
    public final ReadonlyStateFlow destinationScenes;

    public LockscreenSceneViewModel(CoroutineScope coroutineScope, DeviceEntryInteractor deviceEntryInteractor, CommunalInteractor communalInteractor, ShadeInteractor shadeInteractor, KeyguardLongPressViewModel keyguardLongPressViewModel, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel) {
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.destinationScenes = FlowKt.stateIn(FlowKt.transformLatest(shadeInteractorImpl.isShadeTouchable, new LockscreenSceneViewModel$special$$inlined$flatMapLatest$1(null, deviceEntryInteractor, communalInteractor, shadeInteractor, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), destinationScenes(((Boolean) deviceEntryInteractor.isUnlocked.$$delegate_0.getValue()).booleanValue(), false, (ShadeMode) shadeInteractorImpl.baseShadeInteractor.getShadeMode().getValue()));
    }

    public static Map destinationScenes(boolean z, boolean z2, ShadeMode shadeMode) {
        SceneKey sceneKey = SceneFamilies.NotifShade;
        TransitionKeys.INSTANCE.getClass();
        TransitionKey transitionKey = TransitionKeys.ToSplitShade;
        boolean z3 = shadeMode instanceof ShadeMode.Split;
        UserActionResult userActionResult = new UserActionResult(sceneKey, z3 ? transitionKey : null);
        Swipe.Companion.getClass();
        Swipe swipe = Swipe.Left;
        UserActionResult userActionResult2 = new UserActionResult(Scenes.Communal, null, 2, null);
        if (!z2) {
            userActionResult2 = null;
        }
        Pair pair = new Pair(swipe, userActionResult2);
        Pair pair2 = Swipe.Up.to(z ? Scenes.Gone : Scenes.Bouncer);
        SwipeDirection swipeDirection = SwipeDirection.Down;
        Edge edge = Edge.Top;
        Pair pair3 = new Pair(new Swipe(swipeDirection, 1, edge), shadeMode instanceof ShadeMode.Single ? new UserActionResult(Scenes.QuickSettings, null, 2, null) : userActionResult);
        Swipe swipe2 = new Swipe(swipeDirection, 2, edge);
        SceneKey sceneKey2 = SceneFamilies.QuickSettings;
        if (!z3) {
            transitionKey = null;
        }
        return MapUtilsKt.filterValuesNotNull(MapsKt__MapsKt.mapOf(pair, pair2, pair3, new Pair(swipe2, new UserActionResult(sceneKey2, transitionKey)), new Pair(new Swipe(swipeDirection, 1, null, 4, null), userActionResult), new Pair(new Swipe(swipeDirection, 2, null, 4, null), userActionResult)));
    }
}
