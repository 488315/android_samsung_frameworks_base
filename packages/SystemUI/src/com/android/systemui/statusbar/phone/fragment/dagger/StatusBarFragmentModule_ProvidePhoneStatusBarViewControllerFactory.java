package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StatusBarFragmentModule_ProvidePhoneStatusBarViewControllerFactory implements Provider {
    public final javax.inject.Provider phoneStatusBarViewControllerFactoryProvider;
    public final javax.inject.Provider phoneStatusBarViewProvider;

    public StatusBarFragmentModule_ProvidePhoneStatusBarViewControllerFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.phoneStatusBarViewControllerFactoryProvider = provider;
        this.phoneStatusBarViewProvider = provider2;
    }

    public static PhoneStatusBarViewController providePhoneStatusBarViewController(PhoneStatusBarViewController.Factory factory, PhoneStatusBarView phoneStatusBarView) {
        factory.getClass();
        Flags flags = Flags.INSTANCE;
        factory.featureFlags.getClass();
        return new PhoneStatusBarViewController(phoneStatusBarView, (ScopedUnfoldTransitionProgressProvider) factory.progressProvider.orElse(null), factory.centralSurfaces, factory.statusBarWindowStateController, factory.shadeController, factory.shadeViewController, factory.panelExpansionInteractor, factory.windowRootView, factory.shadeLogger, null, factory.userChipViewModel, factory.viewUtil, factory.configurationController, factory.statusOverlayHoverListenerFactory, factory.indicatorGardenPresenter, factory.privacyDotViewController, factory.dumpManager, factory.viewTreeLogHelper, factory.indicatorMarqueeGardener, factory.statusIconContainerController, factory.indicatorCutoutUtil, factory.twoPhoneModeIconController, factory.phoneStatusBarClockManager, factory.knoxStateBarViewModel, factory.netspeedViewController, factory.samsungStatusBarGrayIconHelper, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePhoneStatusBarViewController((PhoneStatusBarViewController.Factory) this.phoneStatusBarViewControllerFactoryProvider.get(), (PhoneStatusBarView) this.phoneStatusBarViewProvider.get());
    }
}
