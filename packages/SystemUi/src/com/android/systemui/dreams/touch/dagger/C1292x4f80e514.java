package com.android.systemui.dreams.touch.dagger;

import com.android.p038wm.shell.animation.FlingAnimationUtils;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.dreams.touch.dagger.BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsClosingFactory */
/* loaded from: classes.dex */
public final class C1292x4f80e514 implements Provider {
    public final Provider flingAnimationUtilsBuilderProvider;

    public C1292x4f80e514(Provider provider) {
        this.flingAnimationUtilsBuilderProvider = provider;
    }

    public static FlingAnimationUtils providesSwipeToBouncerFlingAnimationUtilsClosing(Provider provider) {
        FlingAnimationUtils.Builder builder = (FlingAnimationUtils.Builder) provider.get();
        builder.reset();
        builder.mMaxLengthSeconds = 0.6f;
        builder.mSpeedUpFactor = 0.6f;
        return builder.build();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesSwipeToBouncerFlingAnimationUtilsClosing(this.flingAnimationUtilsBuilderProvider);
    }
}
