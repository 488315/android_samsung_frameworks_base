package com.android.systemui.statusbar.dagger;

import com.android.systemui.animation.ActivityLaunchAnimator;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideActivityLaunchAnimatorFactory */
/* loaded from: classes2.dex */
public final class C2629x512569cf implements Provider {
    public static ActivityLaunchAnimator provideActivityLaunchAnimator() {
        return new ActivityLaunchAnimator();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ActivityLaunchAnimator();
    }
}
