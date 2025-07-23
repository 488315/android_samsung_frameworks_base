package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HomeStatusBarModule_ProvidePhoneStatusBarViewControllerFactory implements Provider {
    public final Provider phoneStatusBarViewControllerFactoryProvider;
    public final Provider phoneStatusBarViewProvider;

    public HomeStatusBarModule_ProvidePhoneStatusBarViewControllerFactory(Provider provider, Provider provider2) {
        this.phoneStatusBarViewControllerFactoryProvider = provider;
        this.phoneStatusBarViewProvider = provider2;
    }

    public static PhoneStatusBarViewController providePhoneStatusBarViewController(PhoneStatusBarViewController.Factory factory, PhoneStatusBarView phoneStatusBarView) {
        factory.getClass();
        Flags flags = Flags.INSTANCE;
        factory.featureFlags.getClass();
        return new PhoneStatusBarViewController(phoneStatusBarView, factory.ext, (ScopedUnfoldTransitionProgressProvider) factory.progressProvider.orElse(null), factory.centralSurfaces, factory.statusBarWindowStateController, factory.shadeController, factory.shadeViewController, factory.panelExpansionInteractor, factory.statusBarLongPressGestureDetector, factory.windowRootView, factory.shadeLogger, null, factory.userChipViewModel, factory.viewUtil, factory.configurationController, factory.statusOverlayHoverListenerFactory, factory.darkIconDispatcher, factory.statusBarContentInsetsProviderStore, factory.lazyStatusBarShadeDisplayPolicy, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePhoneStatusBarViewController((PhoneStatusBarViewController.Factory) this.phoneStatusBarViewControllerFactoryProvider.get(), (PhoneStatusBarView) this.phoneStatusBarViewProvider.get());
    }
}
