package com.android.systemui.statusbar.phone.fragment.dagger;

import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvidePhoneStatusBarViewControllerFactory */
/* loaded from: classes2.dex */
public final class C3195xfe780fd7 implements Provider {
    public final Provider phoneStatusBarViewControllerFactoryProvider;
    public final Provider phoneStatusBarViewProvider;

    public C3195xfe780fd7(Provider provider, Provider provider2) {
        this.phoneStatusBarViewControllerFactoryProvider = provider;
        this.phoneStatusBarViewProvider = provider2;
    }

    public static PhoneStatusBarViewController providePhoneStatusBarViewController(PhoneStatusBarViewController.Factory factory, PhoneStatusBarView phoneStatusBarView) {
        factory.getClass();
        Flags flags = Flags.INSTANCE;
        factory.featureFlags.getClass();
        return new PhoneStatusBarViewController(phoneStatusBarView, (ScopedUnfoldTransitionProgressProvider) factory.progressProvider.orElse(null), factory.centralSurfaces, factory.shadeController, factory.shadeLogger, null, factory.userChipViewModel, factory.viewUtil, factory.configurationController, factory.indicatorGardenPresenter, factory.dumpManager, factory.viewTreeLogHelper, factory.netspeedViewController, factory.knoxStateBarViewModel, factory.statusIconContainerController, factory.privacyDotViewController, factory.samsungStatusBarGrayIconHelper, factory.twoPhoneModeIconController, factory.phoneStatusBarClockManager, factory.indicatorCutoutUtil, factory.indicatorMarqueeGardener, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePhoneStatusBarViewController((PhoneStatusBarViewController.Factory) this.phoneStatusBarViewControllerFactoryProvider.get(), (PhoneStatusBarView) this.phoneStatusBarViewProvider.get());
    }
}
