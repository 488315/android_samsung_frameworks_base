package com.android.systemui.statusbar.dagger;

import android.service.dreams.IDreamManager;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CentralSurfacesDependenciesModule$1 implements DialogTransitionAnimator.Callback {
    public final /* synthetic */ Lazy val$alternateBouncerInteractor;
    public final /* synthetic */ IDreamManager val$dreamManager;
    public final /* synthetic */ KeyguardStateController val$keyguardStateController;

    public CentralSurfacesDependenciesModule$1(IDreamManager iDreamManager, KeyguardStateController keyguardStateController, Lazy lazy) {
        this.val$dreamManager = iDreamManager;
        this.val$keyguardStateController = keyguardStateController;
        this.val$alternateBouncerInteractor = lazy;
    }
}
