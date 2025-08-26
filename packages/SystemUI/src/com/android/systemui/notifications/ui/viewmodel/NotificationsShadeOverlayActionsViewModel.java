package com.android.systemui.notifications.ui.viewmodel;

import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.ui.viewmodel.SceneContainerArea;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;

/* loaded from: classes2.dex */
public final class NotificationsShadeOverlayActionsViewModel extends UserActionsViewModel {

    public interface Factory {
        NotificationsShadeOverlayActionsViewModel create();
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        Swipe.Companion companion = Swipe.Companion;
        companion.getClass();
        Swipe swipe = Swipe.Up;
        OverlayKey overlayKey = Overlays.NotificationsShade;
        userActionsViewModel$$ExternalSyntheticLambda0.mo781invoke(MapsKt__MapsKt.mapOf(new Pair(swipe, new UserActionResult.HideOverlay(overlayKey, null, false, 6, null)), new Pair(Back.INSTANCE, new UserActionResult.HideOverlay(overlayKey, null, false, 6, null)), new Pair(Swipe.Companion.m928DownloWS4t8$default(companion, 0, SceneContainerArea.TopEdgeEndHalf.INSTANCE, 3), new UserActionResult.ReplaceByOverlay(Overlays.QuickSettingsShade, null, false, 6, null))));
        return Unit.INSTANCE;
    }
}
