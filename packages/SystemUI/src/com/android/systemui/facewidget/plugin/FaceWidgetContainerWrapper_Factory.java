package com.android.systemui.facewidget.plugin;

import android.content.Context;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class FaceWidgetContainerWrapper_Factory implements Provider {
    public final Provider contextProvider;
    public final Provider keyguardStatusViewAlphaChangeControllerWrapperProvider;
    public final Provider mKeyguardStateControllerProvider;
    public final Provider mLoggerProvider;
    public final Provider mMascotViewContainerProvider;
    public final Provider mScreenOffAnimationControllerProvider;

    public FaceWidgetContainerWrapper_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.keyguardStatusViewAlphaChangeControllerWrapperProvider = provider2;
        this.mKeyguardStateControllerProvider = provider3;
        this.mScreenOffAnimationControllerProvider = provider4;
        this.mLoggerProvider = provider5;
        this.mMascotViewContainerProvider = provider6;
    }

    public static FaceWidgetContainerWrapper newInstance(Context context, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper) {
        return new FaceWidgetContainerWrapper(context, keyguardStatusViewAlphaChangeControllerWrapper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = new FaceWidgetContainerWrapper((Context) this.contextProvider.get(), (KeyguardStatusViewAlphaChangeControllerWrapper) this.keyguardStatusViewAlphaChangeControllerWrapperProvider.get());
        faceWidgetContainerWrapper.mKeyguardStateController = (KeyguardStateController) this.mKeyguardStateControllerProvider.get();
        faceWidgetContainerWrapper.mScreenOffAnimationController = (ScreenOffAnimationController) this.mScreenOffAnimationControllerProvider.get();
        faceWidgetContainerWrapper.mLogger = (KeyguardLogger) this.mLoggerProvider.get();
        faceWidgetContainerWrapper.mMascotViewContainer = (DcmMascotViewContainer) this.mMascotViewContainerProvider.get();
        return faceWidgetContainerWrapper;
    }
}
