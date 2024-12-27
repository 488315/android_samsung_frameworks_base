package com.android.systemui.navigationbar.plugin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.WindowManager;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor;
import com.android.systemui.navigationbar.plugin.NavBarStoreAdapterImpl;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.navigationbar.ExtendableBar;
import com.samsung.systemui.splugins.navigationbar.FeatureChecker;
import com.samsung.systemui.splugins.navigationbar.IconThemeBase;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter;
import com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SamsungPluginNavigationBar implements ExtendableBar {
    public final ButtonDispatcherProxy buttonDispatcher;
    public final Context context;
    public final int displayId;
    public boolean keyguardShowing;
    public final NavBarStore navBarStore;
    public final NavigationBarView navigationBarView;
    public final BasicRuneFeatureChecker featureChecker = new BasicRuneFeatureChecker();
    public final TaskStackAdapter taskStackAdapter = new TaskStackAdapter();
    public final int FORCE_SHOW_NAVIGATION_BAR = QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED;
    public final KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
    public final SamsungPluginNavigationBar$keyguardCallback$1 keyguardCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.navigationbar.plugin.SamsungPluginNavigationBar$keyguardCallback$1
        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            SamsungPluginNavigationBar samsungPluginNavigationBar = SamsungPluginNavigationBar.this;
            KeyguardStateController keyguardStateController = samsungPluginNavigationBar.keyguardStateController;
            samsungPluginNavigationBar.keyguardShowing = keyguardStateController != null ? ((KeyguardStateControllerImpl) keyguardStateController).mShowing : false;
        }
    };

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.navigationbar.plugin.SamsungPluginNavigationBar$keyguardCallback$1] */
    public SamsungPluginNavigationBar(NavigationBarView navigationBarView, NavBarStore navBarStore, ButtonDispatcherProxy buttonDispatcherProxy, Context context) {
        this.navigationBarView = navigationBarView;
        this.navBarStore = navBarStore;
        this.buttonDispatcher = buttonDispatcherProxy;
        this.context = context;
        this.displayId = context.getDisplayId();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void forceSetBackGesture(boolean z) {
        com.android.systemui.shared.system.QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN = z;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final int getBarDisplayId() {
        return this.displayId;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final BarLayoutParams getBarLayoutParamsProvider() {
        return (BarLayoutParams) ((NavBarStoreImpl) this.navBarStore).getProvider(2, this.displayId);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final ButtonDispatcherProxyBase getButtonDispatcherProxy() {
        return this.buttonDispatcher;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final ColorSetting getDefaultColorProvider() {
        return (ColorSetting) ((NavBarStoreImpl) this.navBarStore).getProvider(0, 0);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final IconThemeBase getDefaultIconTheme() {
        return this.navigationBarView.getDefaultIconTheme();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final LayoutProviderContainer getDefaultLayoutProviderContainer() {
        return (LayoutProviderContainer) ((NavBarStoreImpl) this.navBarStore).getProvider(1, this.displayId);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final int getDisabledFlags() {
        return ((NavBarStateManagerImpl) this.navBarStore.getNavStateManager()).states.disable1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final FeatureChecker getFeatureChecker() {
        return this.featureChecker;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final NavBarStoreAdapter getNavBarStoreAdapter() {
        NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.navBarStore;
        navBarStoreImpl.getClass();
        NavBarStoreAdapterImpl.Companion companion = NavBarStoreAdapterImpl.Companion;
        LogWrapper logWrapper = new LogWrapper(ModuleType.NAVBAR, null);
        companion.getClass();
        NavBarStoreAdapter navBarStoreAdapter = NavBarStoreAdapterImpl.INSTANCE;
        if (navBarStoreAdapter != null) {
            return navBarStoreAdapter;
        }
        NavBarStoreAdapterImpl navBarStoreAdapterImpl = new NavBarStoreAdapterImpl(navBarStoreImpl, logWrapper);
        NavBarStoreAdapterImpl.INSTANCE = navBarStoreAdapterImpl;
        return navBarStoreAdapterImpl;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final Context getNavigationBarContext() {
        return this.context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final TaskStackAdapterBase getTaskStackAdapter() {
        return this.taskStackAdapter;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isCoverDisplayNavBarEnabled() {
        return ((NavBarStateManagerImpl) ((NavBarStoreImpl) this.navBarStore).getNavStateManager(1)).isCoverDisplayNavBarEnabled();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isCoverLargeScreenTaskEnabled() {
        DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) ((NavBarStateManagerImpl) ((NavBarStoreImpl) this.navBarStore).getNavStateManager(1)).interactorFactory.get(DeviceStateInteractor.class);
        if (deviceStateInteractor != null) {
            return deviceStateInteractor.coverTaskCache;
        }
        return false;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isKeyguardShowing() {
        return this.keyguardShowing;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void registerKeyguardStateCallback() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this.keyguardCallback);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void resetScheduleAutoHide() {
        NavigationBar navigationBar = (NavigationBar) ((NavBarStoreImpl) this.navBarStore).getModule(NavigationBar.class, this.displayId);
        navigationBar.getClass();
        Log.d("NavigationBar", "resetAutoHide()");
        navigationBar.mAutoHideController.touchAutoHide();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setBarLayoutParamsProvider(BarLayoutParams barLayoutParams) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(2, this.displayId, barLayoutParams);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setColorProvider(ColorSetting colorSetting, boolean z) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(0, 0, colorSetting);
        if (z) {
            this.navigationBarView.updateNavigationBarColor();
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setDefaultBarLayoutParamsProvider() {
        ((NavBarStoreImpl) this.navBarStore).setProvider(2, this.displayId, null);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setDefaultIconTheme(IconThemeBase iconThemeBase) {
        NavigationBarView navigationBarView = this.navigationBarView;
        navigationBarView.setDefaultIconTheme(iconThemeBase);
        navigationBarView.updateIconsAndHints();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setForceShowNavigationBarFlag(Context context, boolean z) {
        int i;
        try {
            NotificationShadeWindowView notificationShadeWindowView = ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).getNotificationShadeWindowViewController().mView;
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) notificationShadeWindowView.getLayoutParams();
            int i2 = this.FORCE_SHOW_NAVIGATION_BAR;
            if (z) {
                i = i2 | layoutParams.privateFlags;
            } else {
                i = (~i2) & layoutParams.privateFlags;
            }
            layoutParams.privateFlags = i;
            layoutParams.setTitle("SamsungPluginNavigationBar");
            ((WindowManager) context.getSystemService("window")).updateViewLayout(notificationShadeWindowView, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setIconThemeAlpha(float f) {
        this.navigationBarView.setIconThemeAlpha(f);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setLayoutProviderContainer(LayoutProviderContainer layoutProviderContainer) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(1, this.displayId, layoutProviderContainer);
        this.navigationBarView.updateLayoutProviderView();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setRotationLockAtAngle(boolean z, int i) {
        ((RotationLockController) Dependency.sDependency.getDependencyInner(RotationLockController.class)).setRotationLockedAtAngle(i, "SamsungPluginNavigationBar#setRotationLockAtAngle", z);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setRotationLocked(boolean z) {
        ((RotationLockController) Dependency.sDependency.getDependencyInner(RotationLockController.class)).setRotationLocked("SamsungPluginNavigationBar#setRotationLocked", z);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void unregisterKeyguardStateCallback() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).removeCallback(this.keyguardCallback);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateActiveIndicatorSpringParams(float f, float f2) {
        this.navigationBarView.updateActiveIndicatorSpringParams(f, f2);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateBackGestureIcon(Drawable drawable, Drawable drawable2) {
        this.navigationBarView.updateBackGestureIcon(drawable, drawable2);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateBackPanelColor(int i, int i2, int i3, int i4) {
        this.navigationBarView.updateBackPanelColor(i, i2, i3, i4);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateIconsAndHints(boolean z) {
        NavigationBarView navigationBarView = this.navigationBarView;
        if (z) {
            navigationBarView.reInflateNavBarLayout();
        }
        navigationBarView.updateIconsAndHints();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateOpaqueColor(int i) {
        this.navigationBarView.updateOpaqueColor(i);
    }
}
