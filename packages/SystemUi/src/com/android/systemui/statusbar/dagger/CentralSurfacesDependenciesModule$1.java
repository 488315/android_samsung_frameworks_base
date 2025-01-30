package com.android.systemui.statusbar.dagger;

import android.service.dreams.IDreamManager;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CentralSurfacesDependenciesModule$1 implements DialogLaunchAnimator.Callback {
    public final /* synthetic */ Lazy val$alternateBouncerInteractor;
    public final /* synthetic */ IDreamManager val$dreamManager;
    public final /* synthetic */ KeyguardStateController val$keyguardStateController;

    public CentralSurfacesDependenciesModule$1(IDreamManager iDreamManager, KeyguardStateController keyguardStateController, Lazy lazy) {
        this.val$dreamManager = iDreamManager;
        this.val$keyguardStateController = keyguardStateController;
        this.val$alternateBouncerInteractor = lazy;
    }
}
