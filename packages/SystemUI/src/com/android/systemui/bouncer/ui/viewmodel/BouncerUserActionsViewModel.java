package com.android.systemui.bouncer.ui.viewmodel;

import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;

/* loaded from: classes.dex */
public final class BouncerUserActionsViewModel extends UserActionsViewModel {

    public interface Factory {
        BouncerUserActionsViewModel create();
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        Back back = Back.INSTANCE;
        OverlayKey overlayKey = Overlays.Bouncer;
        Pair pair = new Pair(back, new UserActionResult.HideOverlay(overlayKey, null, false, 6, null));
        Swipe.Companion.getClass();
        userActionsViewModel$$ExternalSyntheticLambda0.mo781invoke(MapsKt__MapsKt.mapOf(pair, new Pair(Swipe.Down, new UserActionResult.HideOverlay(overlayKey, null, false, 6, null))));
        return Unit.INSTANCE;
    }
}
