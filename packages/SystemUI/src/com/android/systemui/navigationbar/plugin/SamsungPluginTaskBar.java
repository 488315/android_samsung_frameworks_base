package com.android.systemui.navigationbar.plugin;

import android.content.Context;
import android.os.Bundle;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.navigationbar.SamsungNavigationBarInflaterView;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.plugin.NavBarStoreAdapterImpl;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import com.samsung.systemui.splugins.navigationbar.ExtendableBar;
import com.samsung.systemui.splugins.navigationbar.FeatureChecker;
import com.samsung.systemui.splugins.navigationbar.IconThemeBase;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import com.samsung.systemui.splugins.navigationbar.NavBarStoreAdapter;
import com.samsung.systemui.splugins.navigationbar.TaskStackAdapterBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.text.StringsKt__StringsKt;

public final class SamsungPluginTaskBar implements ExtendableBar {
    public final TaskBarButtonDispatcherProxy buttonDispatcherProxy;
    public NavBarIconResourceMapper iconResourceMapper;
    public boolean isKeyguardShowing;
    public final SamsungPluginTaskBar$keyguardCallback$1 keyguardCallback;
    public final KeyguardStateController keyguardStateController;
    public final Context mainContext;
    public final int mainDisplayId;
    public final NavBarStateManager navBarStateManager;
    public final NavBarStore navBarStore;
    public final Bundle pluginBundle;
    public final RotationLockController rotationLockContainer;
    public TaskbarDelegate taskbarDelegate;

    public SamsungPluginTaskBar(NavBarStore navBarStore, Context context) {
        this.navBarStore = navBarStore;
        this.mainContext = context;
        int displayId = context.getDisplayId();
        this.mainDisplayId = displayId;
        Bundle bundle = new Bundle();
        this.pluginBundle = bundle;
        this.navBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(displayId);
        this.buttonDispatcherProxy = new TaskBarButtonDispatcherProxy(context, bundle);
        this.keyguardStateController = (KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class);
        this.rotationLockContainer = (RotationLockController) Dependency.sDependency.getDependencyInner(RotationLockController.class);
        this.keyguardCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar$keyguardCallback$1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                SamsungPluginTaskBar samsungPluginTaskBar = SamsungPluginTaskBar.this;
                KeyguardStateController keyguardStateController = samsungPluginTaskBar.keyguardStateController;
                if (keyguardStateController != null) {
                    samsungPluginTaskBar.isKeyguardShowing = ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
                }
            }
        };
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void forceSetBackGesture(boolean z) {
        QuickStepContract.SYSUI_FORCE_SET_BACK_GESTURE_BY_SPLUGIN = z;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final int getBarDisplayId() {
        return this.mainDisplayId;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final BarLayoutParams getBarLayoutParamsProvider() {
        return (BarLayoutParams) ((NavBarStoreImpl) this.navBarStore).getProvider(2, this.mainDisplayId);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final ButtonDispatcherProxyBase getButtonDispatcherProxy() {
        return this.buttonDispatcherProxy;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final ColorSetting getDefaultColorProvider() {
        return (ColorSetting) ((NavBarStoreImpl) this.navBarStore).getProvider(0, this.mainDisplayId);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final IconThemeBase getDefaultIconTheme() {
        NavBarIconResourceMapper navBarIconResourceMapper = this.iconResourceMapper;
        if (navBarIconResourceMapper != null) {
            return navBarIconResourceMapper.getDefaultIconTheme();
        }
        return null;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final LayoutProviderContainer getDefaultLayoutProviderContainer() {
        return (LayoutProviderContainer) ((NavBarStoreImpl) this.navBarStore).getProvider(1, this.mainDisplayId);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final int getDisabledFlags() {
        TaskbarDelegate taskbarDelegate = this.taskbarDelegate;
        if (taskbarDelegate != null) {
            return taskbarDelegate.getDisabledFlags();
        }
        return 0;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final FeatureChecker getFeatureChecker() {
        return new BasicRuneFeatureChecker();
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
        return this.mainContext;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final TaskStackAdapterBase getTaskStackAdapter() {
        return null;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isKeyguardShowing() {
        return this.isKeyguardShowing;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final boolean isTaskbarEnabled() {
        boolean isTaskBarEnabled;
        isTaskBarEnabled = ((NavBarStateManagerImpl) this.navBarStateManager).isTaskBarEnabled(false);
        return isTaskBarEnabled;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void notifyForceImmersiveStateChanged() {
        updatePluginBundle();
    }

    public final void parseAndUpdateBundle() {
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.navBarStateManager;
        List split$default = StringsKt__StringsKt.split$default(navBarStateManagerImpl.isGestureMode() ? navBarStateManagerImpl.getGesturalLayout(navBarStateManagerImpl.isBottomGestureMode(false)) : navBarStateManagerImpl.getDefaultLayout(), new String[]{";"}, 3, 2);
        if (split$default.size() != 3) {
            return;
        }
        List split$default2 = StringsKt__StringsKt.split$default((CharSequence) split$default.get(0), new String[]{","}, 0, 6);
        List split$default3 = StringsKt__StringsKt.split$default((CharSequence) split$default.get(1), new String[]{","}, 0, 6);
        List split$default4 = StringsKt__StringsKt.split$default((CharSequence) split$default.get(2), new String[]{","}, 0, 6);
        this.pluginBundle.putStringArrayList("order", new ArrayList<>());
        this.pluginBundle.putBoolean("pin", false);
        Iterator it = ((ArrayList) CollectionsKt___CollectionsKt.plus((Iterable) split$default4, (Collection) CollectionsKt___CollectionsKt.plus((Iterable) split$default3, (Collection) split$default2))).iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str.equals("recent") || str.equals(BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN) || str.equals("back")) {
                ArrayList<String> stringArrayList = this.pluginBundle.getStringArrayList("order");
                if (stringArrayList != null) {
                    stringArrayList.add(i, str);
                }
            } else {
                SamsungNavigationBarInflaterView.Companion.getClass();
                if (str.startsWith(SamsungNavigationBarInflaterView.navkey)) {
                    String substring = !StringsKt__StringsKt.contains(str, "(", false) ? "1" : str.substring(StringsKt__StringsKt.indexOf$default((CharSequence) str, "(", 0, false, 6) + 1, StringsKt__StringsKt.indexOf$default((CharSequence) str, ":", 0, false, 6));
                    ArrayList<String> stringArrayList2 = this.pluginBundle.getStringArrayList("order");
                    if (stringArrayList2 != null) {
                        stringArrayList2.add(i, substring);
                    }
                } else if (str.equals(SamsungNavigationBarInflaterView.pin)) {
                    this.pluginBundle.putBoolean("pin", true);
                }
            }
            i++;
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void registerKeyguardStateCallback() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).addCallback(this.keyguardCallback);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setBarLayoutParamsProvider(BarLayoutParams barLayoutParams) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(2, this.mainDisplayId, barLayoutParams);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setColorProvider(ColorSetting colorSetting, boolean z) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(0, this.mainDisplayId, colorSetting);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setDefaultBarLayoutParamsProvider() {
        ((NavBarStoreImpl) this.navBarStore).setProvider(2, this.mainDisplayId, null);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setDefaultIconTheme(IconThemeBase iconThemeBase) {
        NavBarIconResourceMapper navBarIconResourceMapper = this.iconResourceMapper;
        if (navBarIconResourceMapper != null) {
            navBarIconResourceMapper.logWrapper.d(navBarIconResourceMapper.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setPreloadedIconSet() null: ", iconThemeBase == null));
            navBarIconResourceMapper.preloadedIconSet = iconThemeBase;
        }
        TaskbarDelegate taskbarDelegate = this.taskbarDelegate;
        if (taskbarDelegate != null) {
            taskbarDelegate.updateTaskbarButtonIconsAndHints();
        }
        if (((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
            updatePluginBundle();
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setIconThemeAlpha(float f) {
        this.pluginBundle.putFloat("alpha", f);
        if (((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode()) {
            return;
        }
        updatePluginBundle();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setLayoutProviderContainer(LayoutProviderContainer layoutProviderContainer) {
        ((NavBarStoreImpl) this.navBarStore).setProvider(1, this.mainDisplayId, layoutProviderContainer);
        parseAndUpdateBundle();
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setRotationLockAtAngle(boolean z, int i) {
        RotationLockController rotationLockController = this.rotationLockContainer;
        if (rotationLockController != null) {
            rotationLockController.setRotationLockedAtAngle(i, "SamsungPluginTaskBar#setRotationLockAtAngle", z);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setRotationLocked(boolean z) {
        RotationLockController rotationLockController = this.rotationLockContainer;
        if (rotationLockController != null) {
            rotationLockController.setRotationLocked("SamsungPluginTaskBar#setRotationLocked", z);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void unregisterKeyguardStateCallback() {
        KeyguardStateController keyguardStateController = this.keyguardStateController;
        if (keyguardStateController != null) {
            ((KeyguardStateControllerImpl) keyguardStateController).removeCallback(this.keyguardCallback);
        }
    }

    public final void updatePluginBundle() {
        NavBarEvents navBarEvents = new NavBarEvents(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
        navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_SPLUGIN_BUNDLE;
        navBarEvents.pluginBundle = this.pluginBundle;
        TaskbarDelegate taskbarDelegate = this.taskbarDelegate;
        if (taskbarDelegate != null) {
            taskbarDelegate.handleNavigationBarEvent(navBarEvents);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void resetScheduleAutoHide() {
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void updateOpaqueColor(int i) {
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ExtendableBar
    public final void setForceShowNavigationBarFlag(Context context, boolean z) {
    }
}
