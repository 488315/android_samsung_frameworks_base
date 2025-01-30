package com.android.keyguard;

import android.content.Context;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardDisplayManager_Factory implements Provider {
    public final Provider contextProvider;
    public final Provider deviceStateHelperProvider;
    public final Provider disableHandlerProvider;
    public final Provider displayTrackerProvider;
    public final Provider keyguardFoldControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardStatusViewComponentFactoryProvider;
    public final Provider mainExecutorProvider;
    public final Provider navigationBarControllerLazyProvider;
    public final Provider uiBgExecutorProvider;

    public KeyguardDisplayManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.keyguardFoldControllerProvider = provider;
        this.disableHandlerProvider = provider2;
        this.contextProvider = provider3;
        this.navigationBarControllerLazyProvider = provider4;
        this.keyguardStatusViewComponentFactoryProvider = provider5;
        this.displayTrackerProvider = provider6;
        this.mainExecutorProvider = provider7;
        this.uiBgExecutorProvider = provider8;
        this.deviceStateHelperProvider = provider9;
        this.keyguardStateControllerProvider = provider10;
    }

    public static KeyguardDisplayManager newInstance(KeyguardFoldController keyguardFoldController, KeyguardPresentationDisabler keyguardPresentationDisabler, Context context, Lazy lazy, KeyguardStatusViewComponent.Factory factory, DisplayTracker displayTracker, Executor executor, Executor executor2, Object obj, KeyguardStateController keyguardStateController) {
        return new KeyguardDisplayManager(keyguardFoldController, keyguardPresentationDisabler, context, lazy, factory, displayTracker, executor, executor2, (KeyguardDisplayManager.DeviceStateHelper) obj, keyguardStateController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((KeyguardFoldController) this.keyguardFoldControllerProvider.get(), (KeyguardPresentationDisabler) this.disableHandlerProvider.get(), (Context) this.contextProvider.get(), DoubleCheck.lazy(this.navigationBarControllerLazyProvider), (KeyguardStatusViewComponent.Factory) this.keyguardStatusViewComponentFactoryProvider.get(), (DisplayTracker) this.displayTrackerProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.uiBgExecutorProvider.get(), this.deviceStateHelperProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get());
    }
}
