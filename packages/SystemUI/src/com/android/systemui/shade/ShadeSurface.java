package com.android.systemui.shade;

import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda29;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ShadeSurface extends ShadeViewController, ShadeBackActionInteractor, ShadeLockscreenInteractor, PanelExpansionInteractor {
    void cancelPendingCollapse(boolean z);

    KeyguardTouchAnimator getTouchAnimator();

    void initDependencies(CentralSurfacesImpl centralSurfacesImpl, CentralSurfacesImpl$$ExternalSyntheticLambda29 centralSurfacesImpl$$ExternalSyntheticLambda29, HeadsUpManager headsUpManager);

    void onAffordanceLaunchEnded();

    void onThemeChanged();

    void resetAlpha();

    void resetTranslation();

    void setBouncerShowing(boolean z);

    void setDozing(boolean z, boolean z2);

    void setImportantForAccessibility(int i);

    void setTouchAndAnimationDisabled(boolean z);

    void setUserSetupComplete(boolean z);

    void updateExpansionAndVisibility();

    void updateResources$1();
}
