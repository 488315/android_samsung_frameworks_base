package com.android.systemui.statusbar.dagger;

import android.service.dreams.IDreamManager;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CentralSurfacesDependenciesModule$1 implements DialogTransitionAnimator.Callback {
    public final /* synthetic */ Lazy val$alternateBouncerInteractor;
    public final /* synthetic */ IDreamManager val$dreamManager;
    public final /* synthetic */ KeyguardStateController val$keyguardStateController;

    public CentralSurfacesDependenciesModule$1(IDreamManager iDreamManager, KeyguardStateController keyguardStateController, Lazy lazy) {
        this.val$dreamManager = iDreamManager;
        this.val$keyguardStateController = keyguardStateController;
        this.val$alternateBouncerInteractor = lazy;
    }
}
