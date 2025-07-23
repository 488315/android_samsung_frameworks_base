package com.android.systemui.shade.domain.interactor;

import com.android.compose.animation.scene.TransitionKey;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface BaseShadeInteractor {
    static /* synthetic */ void collapseQuickSettingsShade$default(BaseShadeInteractor baseShadeInteractor, String str, TransitionKey transitionKey, int i) {
        if ((i & 2) != 0) {
            transitionKey = null;
        }
        baseShadeInteractor.collapseQuickSettingsShade(str, transitionKey, false);
    }

    void collapseEitherShade(String str, TransitionKey transitionKey);

    void collapseNotificationsShade(String str, TransitionKey transitionKey);

    void collapseQuickSettingsShade(String str, TransitionKey transitionKey, boolean z);

    void expandNotificationsShade(String str);

    void expandQuickSettingsShade(String str);

    StateFlow getAnyExpansion();

    StateFlow getQsExpansion();

    StateFlow getShadeExpansion();

    StateFlow isAnyExpanded();

    Flow isQsBypassingShade();

    StateFlow isQsExpanded();

    Flow isQsFullscreen();

    Flow isUserInteractingWithQs();

    Flow isUserInteractingWithShade();
}
