package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.bandaid.BandAidPackFactoryBase;
import com.android.systemui.navigationbar.gestural.GestureHintAnimator;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.StoreLogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvidesNavBarStoreFactory implements Provider {
    public final Provider bandAidPackFactoryBaseProvider;
    public final Provider contextProvider;
    public final Provider displayManagerProvider;
    public final Provider gestureHintAnimatorFactoryProvider;
    public final Provider interactorFactoryProvider;
    public final Provider layoutProviderContainerProvider;
    public final Provider navBarRemoteViewManagerProvider;
    public final Provider settingsHelperProvider;
    public final Provider storeLogUtilProvider;
    public final Provider sysUiFlagContainerProvider;

    public SamsungServicesModule_ProvidesNavBarStoreFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.contextProvider = provider;
        this.displayManagerProvider = provider2;
        this.settingsHelperProvider = provider3;
        this.layoutProviderContainerProvider = provider4;
        this.navBarRemoteViewManagerProvider = provider5;
        this.bandAidPackFactoryBaseProvider = provider6;
        this.interactorFactoryProvider = provider7;
        this.storeLogUtilProvider = provider8;
        this.gestureHintAnimatorFactoryProvider = provider9;
        this.sysUiFlagContainerProvider = provider10;
    }

    public static NavBarStoreImpl providesNavBarStore(Context context, DisplayManager displayManager, SettingsHelper settingsHelper, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, BandAidPackFactoryBase bandAidPackFactoryBase, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, GestureHintAnimator.Factory factory, SysUiState sysUiState) {
        return new NavBarStoreImpl(context, displayManager, settingsHelper, layoutProviderContainer, navBarRemoteViewManager, bandAidPackFactoryBase, interactorFactory, storeLogUtil, factory, sysUiState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesNavBarStore((Context) this.contextProvider.get(), (DisplayManager) this.displayManagerProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (LayoutProviderContainer) this.layoutProviderContainerProvider.get(), (NavBarRemoteViewManager) this.navBarRemoteViewManagerProvider.get(), (BandAidPackFactoryBase) this.bandAidPackFactoryBaseProvider.get(), (InteractorFactory) this.interactorFactoryProvider.get(), (StoreLogUtil) this.storeLogUtilProvider.get(), (GestureHintAnimator.Factory) this.gestureHintAnimatorFactoryProvider.get(), (SysUiState) this.sysUiFlagContainerProvider.get());
    }
}
