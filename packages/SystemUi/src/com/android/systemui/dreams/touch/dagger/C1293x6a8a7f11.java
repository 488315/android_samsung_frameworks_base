package com.android.systemui.dreams.touch.dagger;

import com.android.wm.shell.animation.FlingAnimationUtils;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.dreams.touch.dagger.BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsOpeningFactory */
/* loaded from: classes.dex */
public final class C1293x6a8a7f11 implements Provider {
    public final Provider flingAnimationUtilsBuilderProvider;

    public C1293x6a8a7f11(Provider provider) {
        this.flingAnimationUtilsBuilderProvider = provider;
    }

    public static FlingAnimationUtils providesSwipeToBouncerFlingAnimationUtilsOpening(Provider provider) {
        FlingAnimationUtils.Builder builder = (FlingAnimationUtils.Builder) provider.get();
        builder.reset();
        builder.mMaxLengthSeconds = 0.6f;
        builder.mSpeedUpFactor = 0.6f;
        return builder.build();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesSwipeToBouncerFlingAnimationUtilsOpening(this.flingAnimationUtilsBuilderProvider);
    }
}
