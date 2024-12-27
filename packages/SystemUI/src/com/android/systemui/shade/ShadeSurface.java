package com.android.systemui.shade;

import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda29;
import com.android.systemui.statusbar.policy.HeadsUpManager;

public interface ShadeSurface extends ShadeViewController, ShadeBackActionInteractor, ShadeLockscreenInteractor, PanelExpansionInteractor {
    void cancelAnimation();

    void cancelPendingCollapse(boolean z);

    KeyguardTouchAnimator getTouchAnimator();

    void initDependencies(CentralSurfaces centralSurfaces, CentralSurfacesImpl$$ExternalSyntheticLambda29 centralSurfacesImpl$$ExternalSyntheticLambda29, HeadsUpManager headsUpManager);

    void onAffordanceLaunchEnded();

    void onScreenTurningOn();

    void onThemeChanged();

    void resetAlpha();

    void resetTranslation();

    void setBouncerShowing(boolean z);

    void setDozing(boolean z, boolean z2);

    void setImportantForAccessibility(int i);

    void setTouchAndAnimationDisabled(boolean z);

    void setUserSetupComplete$1(boolean z);

    void setWillPlayDelayedDozeAmountAnimation(boolean z);

    void updateExpansionAndVisibility();

    void updateResources$1();

    default void expandQSForOpenDetail() {
    }
}
