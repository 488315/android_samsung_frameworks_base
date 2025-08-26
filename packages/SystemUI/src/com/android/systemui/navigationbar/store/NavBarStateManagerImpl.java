package com.android.systemui.navigationbar.store;

import android.R;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor;
import com.android.systemui.navigationbar.interactor.TaskBarInteractor;
import com.android.systemui.navigationbar.layout.NavBarCoverLayoutParams;
import com.android.systemui.navigationbar.layout.NavBarLayoutParams;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.util.StoreLogUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class NavBarStateManagerImpl implements NavBarStateManager {
    public final Context context;
    public final EventTypeFactory eventTypeFactory;
    public final InteractorFactory interactorFactory;
    public LayoutProviderContainer layoutProviderContainer;
    public final StoreLogUtil logWrapper;
    public BarLayoutParams navBarLayoutParams;
    public final NavBarRemoteViewManager navBarRemoteViewManager;
    public final NavBarStore navBarStore;
    private final SettingsHelper settingsHelper;
    public NavBarStates states;

    public NavBarStateManagerImpl(Context context, NavBarStore navBarStore, SettingsHelper settingsHelper, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, UserTracker userTracker, Point point) {
        this.context = context;
        this.navBarStore = navBarStore;
        this.settingsHelper = settingsHelper;
        this.interactorFactory = interactorFactory;
        this.logWrapper = storeLogUtil;
        this.layoutProviderContainer = layoutProviderContainer;
        this.navBarRemoteViewManager = navBarRemoteViewManager;
        this.states = new NavBarStates(point, false, false, null, 0, 0, 0, false, 0, false, false, 0, false, 0, false, false, false, false, false, 0, false, false, false, false, false, 0, null, 134217726, null);
        this.navBarLayoutParams = new NavBarLayoutParams(context, this);
        EventTypeFactory.Companion.getClass();
        EventTypeFactory eventTypeFactory = EventTypeFactory.INSTANCE;
        if (eventTypeFactory == null) {
            eventTypeFactory = new EventTypeFactory(context);
            EventTypeFactory.INSTANCE = eventTypeFactory;
        }
        this.eventTypeFactory = eventTypeFactory;
        onNavigationBarCreated();
    }

    public final boolean canPlaceKeyboardButton(int i) {
        return i == 0 || i == 2 || DeviceType.isTablet() || this.states.imeDownButtonForAllRotation;
    }

    public final boolean canShowButtonInLargeCoverIme() {
        return isGestureMode() ? isMultiModalAvailableInLargeCover() || this.settingsHelper.isNavigationBarHideKeyboardButtonEnabled() : isMultiModalAvailableInLargeCover();
    }

    public final boolean canShowFloatingGameTools(boolean z) {
        boolean z2 = false;
        if (isGameMode(false) && this.settingsHelper.isNavigationBarGestureProtectionEnabled() && !this.settingsHelper.isGameToolsEnabled()) {
            z2 = true;
        }
        if (z) {
            logNavBarStates(Boolean.valueOf(z2), "canShowFloatingGameTools");
        }
        return z2;
    }

    public final boolean canShowGestureHint() {
        boolean z = (!this.settingsHelper.isNavigationBarGestureHintEnabled() || this.states.sPayShowing || shouldShowSUWStyle()) ? false : true;
        logNavBarStates(Boolean.valueOf(z), "canShowGestureHint");
        return z;
    }

    public final boolean canShowHideKeyboardButtonForRotation(int i) {
        if (!isGestureMode() || shouldShowSUWStyle()) {
            return true;
        }
        return this.settingsHelper.isNavigationBarHideKeyboardButtonEnabled() && canPlaceKeyboardButton(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean canShowKeyboardButtonForRotation(int i) {
        boolean z = false;
        boolean z2 = Settings.Secure.getIntForUser(this.context.getContentResolver(), SettingsHelper.INDEX_SHOW_KEYBOARD_BUTTON, 0, this.states.lastTaskUserId) != 0;
        if (isGestureMode()) {
            z2 = !supportLargeCoverScreenNavBar() && z2 && canPlaceKeyboardButton(i);
        }
        if (z2) {
            z = true;
        } else {
            if ((BasicRune.NAVBAR_MULTI_MODAL_ICON && (!isGestureMode() || (this.navBarRemoteViewManager.isSetMultimodalButton() && canPlaceKeyboardButton(i)))) || canShowHideKeyboardButtonForRotation(i)) {
            }
        }
        logNavBarStates(Boolean.valueOf(z), ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "canShowKeyboardButtonForRotation(", ")"));
        return z;
    }

    public final boolean canShowKeyboardButtonOnLeft() {
        return !((((SysUiState) Dependency.sDependency.getDependencyInner(SysUiState.class)).getFlags() & 16) == 0 || isGestureMode()) || Settings.Secure.getIntForUser(this.context.getContentResolver(), SettingsHelper.INDEX_KEYBOARD_BUTTON_POSITION, 0, this.states.lastTaskUserId) == 0;
    }

    public final int getButtonWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        layoutProvider.getClass();
        int buttonWidth = layoutProvider.getButtonWidth(this.states.displaySize, z);
        Integer numValueOf = Integer.valueOf(buttonWidth);
        logNavBarStates(numValueOf, "getButtonWidth(land: " + z + ")");
        return numValueOf.intValue();
    }

    public final String getDefaultLayout() {
        SettingsHelper settingsHelper = this.settingsHelper;
        LayoutProvider layoutProvider = this.states.layoutProvider;
        layoutProvider.getClass();
        String layout = layoutProvider.getLayout(!settingsHelper.isNavBarButtonOrderDefault(), settingsHelper.getNavigationBarAlignPosition());
        logNavBarStates(layout, "getDefaultLayout");
        layout.getClass();
        return layout;
    }

    public final String getGesturalLayout(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        layoutProvider.getClass();
        String gesturalLayout = layoutProvider.getGesturalLayout(z, !this.settingsHelper.isNavBarButtonOrderDefault());
        logNavBarStates(gesturalLayout, "getGesturalLayout");
        gesturalLayout.getClass();
        return gesturalLayout;
    }

    public final int getGestureWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        layoutProvider.getClass();
        int gestureWidth = layoutProvider.getGestureWidth(this.states.displaySize, z);
        Integer numValueOf = Integer.valueOf(gestureWidth);
        logNavBarStates(numValueOf, "getGestureWidth(land: " + z + ")");
        return numValueOf.intValue();
    }

    public final int getNavBarHeight(int i) {
        return shouldShowSUWStyle() ? this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_right_edge_size) : this.navBarLayoutParams.getBarHeight(this.states.canMove, i);
    }

    public final SettingsHelper getSettingHelper() {
        return this.settingsHelper;
    }

    public final int getSpaceWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        layoutProvider.getClass();
        int spaceWidth = layoutProvider.getSpaceWidth(this.states.displaySize, z, NavBarStateManager.isSideAndBottomGestureMode$default(this));
        Integer numValueOf = Integer.valueOf(spaceWidth);
        logNavBarStates(numValueOf, "getSpaceWidth(land: " + z + ")");
        return numValueOf.intValue();
    }

    public final boolean isBottomGestureMode(boolean z) {
        boolean z2 = this.states.navigationMode == 3;
        if (z) {
            logNavBarStates(Boolean.valueOf(z2), "isBottomGestureMode");
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isCoverDisplayNavBarEnabled() {
        boolean z = true;
        if (supportLargeCoverScreenNavBar()) {
            if (isLargeCoverScreenSyncEnabled() || isCoverLauncherNavBarEnabled()) {
                return true;
            }
        } else if (this.states.supportCoverScreen) {
            DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) this.interactorFactory.get(DeviceStateInteractor.class);
            if (!(deviceStateInteractor != null ? deviceStateInteractor.foldCache : false)) {
            }
            logNavBarStates(Boolean.valueOf(z), "supportCoverScreenNavBar");
            if (z) {
            }
        } else {
            z = false;
            logNavBarStates(Boolean.valueOf(z), "supportCoverScreenNavBar");
            if (z) {
                return isCoverLauncherNavBarEnabled();
            }
        }
        return false;
    }

    public final boolean isCoverLauncherNavBarEnabled() {
        if (this.states.supportCoverScreen) {
            CoverDisplayWidgetInteractor coverDisplayWidgetInteractor = (CoverDisplayWidgetInteractor) this.interactorFactory.get(CoverDisplayWidgetInteractor.class);
            Boolean boolValueOf = coverDisplayWidgetInteractor != null ? Boolean.valueOf(coverDisplayWidgetInteractor.isEnabled()) : null;
            logNavBarStates(boolValueOf, "isCoverLauncherNavBarEnabled");
            if (boolValueOf != null ? boolValueOf.booleanValue() : false) {
                return true;
            }
        }
        return false;
    }

    public final boolean isGameMode(boolean z) {
        NavBarRemoteView navBarRemoteView = (NavBarRemoteView) this.navBarRemoteViewManager.leftViewList.peek();
        boolean zEquals = navBarRemoteView != null ? "com.samsung.android.game.gametools".equals(navBarRemoteView.requestClass) : false;
        if (z) {
            logNavBarStates(Boolean.valueOf(zEquals), "isGameMode");
        }
        return zEquals;
    }

    public final boolean isGestureHintEnabled() {
        return this.settingsHelper.isNavigationBarGestureHintEnabled();
    }

    public final boolean isGestureMode() {
        return isBottomGestureMode(false) || NavBarStateManager.isSideAndBottomGestureMode$default(this);
    }

    public final boolean isLargeCoverScreenSyncEnabled() {
        if (!this.states.supportLargeCoverScreen) {
            return false;
        }
        boolean zIsLargeCoverScreenNavigation = this.settingsHelper.isLargeCoverScreenNavigation();
        logNavBarStates(Boolean.valueOf(zIsLargeCoverScreenNavigation), "isLargeCoverScreenSyncEnabled");
        return zIsLargeCoverScreenNavigation;
    }

    public final boolean isLargeCoverTaskEnabled() {
        DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) this.interactorFactory.get(DeviceStateInteractor.class);
        if (deviceStateInteractor != null) {
            return deviceStateInteractor.coverTaskCache;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x006d A[PHI: r4 r10
      0x006d: PHI (r4v3 int) = (r4v1 int), (r4v10 int) binds: [B:35:0x0087, B:30:0x006b] A[DONT_GENERATE, DONT_INLINE]
      0x006d: PHI (r10v3 android.database.Cursor) = (r10v2 android.database.Cursor), (r10v4 android.database.Cursor) binds: [B:35:0x0087, B:30:0x006b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isMultiModalAvailableInLargeCover() {
        int i;
        String string;
        if (!supportLargeCoverScreenNavBar()) {
            Log.d("NavBarStateManager", "multiModalForLargeCover = false (not in cover display)");
            return false;
        }
        Boolean bool = this.states.multiModalForLargeCover;
        if (bool != null) {
            bool.getClass();
            return bool.booleanValue();
        }
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.context.getContentResolver().query(Uri.parse("content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider"), null, null, new String[]{"cover_voice_icon"}, null);
                i = 0;
                if (cursorQuery != null) {
                    while (cursorQuery.moveToNext()) {
                        try {
                            int columnIndex = cursorQuery.getColumnIndex("NAME");
                            if (columnIndex != -1 && (string = cursorQuery.getString(columnIndex)) != null && string.length() > 0 && string.equals("cover_voice_icon")) {
                                i = cursorQuery.getInt(cursorQuery.getColumnIndex("VALUE"));
                            }
                        } catch (Exception e) {
                            e = e;
                            Log.e("NavBarStateManager", "Failed to retrieve cover_voice_icon. " + e);
                            if (cursorQuery != null) {
                            }
                            this.states.multiModalForLargeCover = Boolean.valueOf(i == 1);
                            Log.d("NavBarStateManager", "multiModalForLargeCover : " + this.states.multiModalForLargeCover);
                            Boolean bool2 = this.states.multiModalForLargeCover;
                            bool2.getClass();
                            return bool2.booleanValue();
                        }
                    }
                }
            } finally {
            }
        } catch (Exception e2) {
            e = e2;
            i = 0;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        this.states.multiModalForLargeCover = Boolean.valueOf(i == 1);
        Log.d("NavBarStateManager", "multiModalForLargeCover : " + this.states.multiModalForLargeCover);
        Boolean bool22 = this.states.multiModalForLargeCover;
        bool22.getClass();
        return bool22.booleanValue();
    }

    public final boolean isNavBarHidden() {
        DesktopModeInteractor desktopModeInteractor;
        return isNavBarHiddenByKnox() || (BasicRune.NAVBAR_DESKTOP && (desktopModeInteractor = (DesktopModeInteractor) this.interactorFactory.get(DesktopModeInteractor.class)) != null && desktopModeInteractor.isEnabled());
    }

    public final boolean isNavBarHiddenByKnox() {
        EdmMonitor edmMonitor;
        boolean z = false;
        if (((KnoxStateMonitorInteractor) this.interactorFactory.get(KnoxStateMonitorInteractor.class)) != null && (edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor) != null && edmMonitor.mIsNavigationBarHidden) {
            z = true;
        }
        logNavBarStates(Boolean.valueOf(z), "isNavBarHiddenByKnox");
        return z;
    }

    public final boolean isNavigationBarUseThemeDefault() {
        boolean zIsNavigationBarUseThemeDefault = this.settingsHelper.isNavigationBarUseThemeDefault();
        logNavBarStates(Boolean.valueOf(zIsNavigationBarUseThemeDefault), "isNavigationBarUseThemeDefault");
        return zIsNavigationBarUseThemeDefault;
    }

    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v4 */
    public final boolean isTaskBarEnabled(boolean z) {
        String strM;
        DesktopModeInteractor desktopModeInteractor;
        Boolean bool;
        boolean zIsTaskBarEnabled = this.settingsHelper.isTaskBarEnabled();
        boolean zIsFactoryBinary = DeviceType.isFactoryBinary();
        InteractorFactory interactorFactory = this.interactorFactory;
        TaskBarInteractor taskBarInteractor = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class);
        boolean zBooleanValue = (taskBarInteractor == null || (bool = taskBarInteractor.userUnlocked) == null) ? false : bool.booleanValue();
        boolean z2 = this.states.deviceProvisioned;
        boolean zIsEasyModeOn = this.settingsHelper.isEasyModeOn();
        boolean zIsUltraPowerSavingMode = this.settingsHelper.isUltraPowerSavingMode();
        ActivityManagerWrapper.sInstance.getClass();
        ?? r10 = 1;
        r10 = 1;
        boolean z3 = ActivityTaskManager.getService().getLockTaskModeState() == 1;
        TaskBarInteractor taskBarInteractor2 = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class);
        boolean z4 = taskBarInteractor2 != null && taskBarInteractor2.isDefaultHome;
        boolean z5 = this.states.userSetupCompleteForCurrentUser;
        boolean z6 = BasicRune.NAVBAR_DESKTOP && (desktopModeInteractor = (DesktopModeInteractor) interactorFactory.get(DesktopModeInteractor.class)) != null && desktopModeInteractor.isEnabled();
        TaskBarInteractor taskBarInteractor3 = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class);
        boolean z7 = taskBarInteractor3 != null && taskBarInteractor3.fitToActiveDisplay;
        boolean zIsSysUiSafeModeEnabled = SafeUIState.isSysUiSafeModeEnabled();
        if (z) {
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m((zIsTaskBarEnabled ? "a" : ImsProfile.TIMER_NAME_A).concat(!zIsFactoryBinary ? "b" : ImsProfile.TIMER_NAME_B), zBooleanValue ? "c" : ImsProfile.TIMER_NAME_C), this.context.getResources().getConfiguration().semDisplayDeviceType == 0 ? "d" : ImsProfile.TIMER_NAME_D), DeviceType.isTablet() ? "e" : ImsProfile.TIMER_NAME_E), z2 ? "f" : ImsProfile.TIMER_NAME_F), !zIsEasyModeOn ? "g" : ImsProfile.TIMER_NAME_G), !zIsUltraPowerSavingMode ? "h" : ImsProfile.TIMER_NAME_H), !z3 ? "i" : ImsProfile.TIMER_NAME_I), z4 ? "j" : ImsProfile.TIMER_NAME_J), z5 ? "k" : ImsProfile.TIMER_NAME_K), z6 ? "l" : "L"), !z7 ? "m" : "M"), !zIsSysUiSafeModeEnabled ? "n" : "N");
        } else {
            strM = "";
        }
        if ((!zIsTaskBarEnabled || zIsFactoryBinary || !zBooleanValue || ((this.context.getResources().getConfiguration().semDisplayDeviceType != 0 && !DeviceType.isTablet()) || z7 || !z2 || zIsEasyModeOn || zIsUltraPowerSavingMode || z3 || !z4 || !z5 || zIsSysUiSafeModeEnabled)) && (!z6 || !zBooleanValue)) {
            r10 = 0;
        }
        Settings.Global.putInt(this.context.getContentResolver(), "sem_task_bar_available", r10);
        if (z) {
            logNavBarStates(Boolean.valueOf((boolean) r10), ContentInViewNode$Request$$ExternalSyntheticOutline0.m("isTaskbarEnabled(", strM, ")"));
        }
        return r10;
    }

    public final void logNavBarStates(Object obj, String str) {
        StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(this.context.getDisplayId(), "NavBarStates(", ") ", str, ": ");
        sbM.append(obj);
        Log.d("NavBarStateManager", sbM.toString());
    }

    public final void onNavigationBarCreated() {
        NavBarStates navBarStates = this.states;
        int displayId = this.context.getDisplayId();
        navBarStates.supportCoverScreen = BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && displayId == 1;
        navBarStates.supportLargeCoverScreen = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && displayId == 1;
        this.states.userSetupCompleteForCurrentUser = Settings.Secure.getIntForUser(this.context.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) == 1;
        EventTypeFactory eventTypeFactory = this.eventTypeFactory;
        Resources resources = eventTypeFactory.context.getResources();
        ((ArrayList) eventTypeFactory.updatableEvents).clear();
        ((ArrayList) eventTypeFactory.updatableEvents).add(new EventTypeFactory.EventType.OnConfigChanged(resources.getConfiguration()));
        ((ArrayList) eventTypeFactory.updatableEvents).add(new EventTypeFactory.EventType.OnNavBarConfigChanged(resources.getBoolean(R.bool.config_safe_media_volume_enabled) && !DeviceType.isTablet(), resources.getBoolean(com.android.systemui.R.bool.config_navBarSupportPhoneLayoutProvider) && !DeviceType.isTablet(), resources.getBoolean(R.bool.config_secondaryBuiltInDisplayIsRound), resources.getInteger(R.integer.config_screenTimeoutOverride)));
        ((ArrayList) eventTypeFactory.updatableEvents).add(new EventTypeFactory.EventType.OnRotationChanged(eventTypeFactory.context.getResources().getConfiguration().windowConfiguration.getRotation()));
        ((ArrayList) eventTypeFactory.updatableEvents).add(new EventTypeFactory.EventType.OnDeviceProvisionedChanged(((DeviceProvisionedControllerImpl) ((DeviceProvisionedController) Dependency.sDependency.getDependencyInner(DeviceProvisionedController.class))).deviceProvisioned.get()));
        Iterator it = CollectionsKt___CollectionsKt.toList(eventTypeFactory.updatableEvents).iterator();
        while (it.hasNext()) {
            updateStateFromEvent((EventTypeFactory.EventType) it.next());
        }
        updateLayoutProvider();
    }

    public final boolean rotateDisabledByPolicy() {
        return !this.settingsHelper.isNavigationBarRotateSuggestionEnabled() || this.settingsHelper.isEmergencyMode();
    }

    public final boolean shouldShowSUWStyle() {
        if (!this.states.userSetupCompleteForCurrentUser) {
            NavBarStore navBarStore = this.navBarStore;
            navBarStore.getClass();
            if (!((KeyguardStateControllerImpl) ((KeyguardStateController) ((NavBarStoreImpl) navBarStore).getModule(KeyguardStateController.class, 0))).mShowing) {
                return true;
            }
        }
        return false;
    }

    public final boolean supportLargeCoverScreenNavBar() {
        boolean z = false;
        if (this.states.supportLargeCoverScreen) {
            DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) this.interactorFactory.get(DeviceStateInteractor.class);
            if (deviceStateInteractor != null ? deviceStateInteractor.foldCache : false) {
                z = true;
            }
        }
        logNavBarStates(Boolean.valueOf(z), "supportLargeCoverScreenNavBar");
        return z;
    }

    public final void updateLayoutProvider() {
        String str = "updateLayoutProvider() layoutProviderContainer = " + this.layoutProviderContainer;
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (storeLogUtil.allowLogging) {
            storeLogUtil.printLog(storeLogUtil.lastDepth, str);
        }
        NavBarStates navBarStates = this.states;
        navBarStates.layoutProvider = this.layoutProviderContainer.updateLayoutProvider(supportLargeCoverScreenNavBar(), navBarStates.supportPhoneLayoutProvider);
    }

    public final void updateStateFromEvent(EventTypeFactory.EventType eventType) {
        NavBarStates navBarStates = this.states;
        if (eventType instanceof EventTypeFactory.EventType.OnConfigChanged) {
            EventTypeFactory.EventType.OnConfigChanged onConfigChanged = (EventTypeFactory.EventType.OnConfigChanged) eventType;
            navBarStates.rotation = onConfigChanged.newConfig.windowConfiguration.getRotation();
            navBarStates.darkMode = (onConfigChanged.newConfig.uiMode & 32) != 0;
            Point point = new Point();
            point.set(onConfigChanged.newConfig.windowConfiguration.getBounds().width(), onConfigChanged.newConfig.windowConfiguration.getBounds().height());
            Point point2 = navBarStates.displaySize;
            navBarStates.displayChanged = Math.min(point2.x, point2.y) != Math.min(point.x, point.y);
            navBarStates.displaySize = point;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarConfigChanged) {
            EventTypeFactory.EventType.OnNavBarConfigChanged onNavBarConfigChanged = (EventTypeFactory.EventType.OnNavBarConfigChanged) eventType;
            navBarStates.canMove = onNavBarConfigChanged.canMove;
            navBarStates.supportPhoneLayoutProvider = onNavBarConfigChanged.supportPhoneLayoutProvider;
            navBarStates.imeDownButtonForAllRotation = onNavBarConfigChanged.imeDownButtonForAllRotation;
            navBarStates.navigationMode = onNavBarConfigChanged.navigationMode;
            navBarStates.displayChanged = false;
            navBarStates.layoutProvider = this.layoutProviderContainer.updateLayoutProvider(supportLargeCoverScreenNavBar(), onNavBarConfigChanged.supportPhoneLayoutProvider);
            SamsungNavigationBarProxy.Companion.getClass();
            if (SamsungNavigationBarProxy.INSTANCE == null) {
                SamsungNavigationBarProxy.INSTANCE = new SamsungNavigationBarProxy();
            }
            updateLayoutProvider();
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnRotationChanged) {
            navBarStates.rotation = ((EventTypeFactory.EventType.OnRotationChanged) eventType).rotation;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnDeviceProvisionedChanged) {
            navBarStates.deviceProvisioned = ((EventTypeFactory.EventType.OnDeviceProvisionedChanged) eventType).provisioned;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarIconHintChanged) {
            navBarStates.iconHint = ((EventTypeFactory.EventType.OnNavBarIconHintChanged) eventType).iconHint;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnEdgeBackGestureDisablePolicyChanged) {
            navBarStates.gestureDisablePolicy = ((EventTypeFactory.EventType.OnEdgeBackGestureDisablePolicyChanged) eventType).policy;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnUpdateSpayVisibility) {
            navBarStates.sPayShowing = ((EventTypeFactory.EventType.OnUpdateSpayVisibility) eventType).showing;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnSetGestureHintVisibility) {
            EventTypeFactory.EventType.OnSetGestureHintVisibility onSetGestureHintVisibility = (EventTypeFactory.EventType.OnSetGestureHintVisibility) eventType;
            navBarStates.recentVisible = onSetGestureHintVisibility.recentVisible;
            navBarStates.homeVisible = onSetGestureHintVisibility.homeVisible;
            navBarStates.backVisible = onSetGestureHintVisibility.backVisible;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged) {
            navBarStates.hardKeyIntentPolicy = ((EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged) eventType).intentStatus;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarTransitionModeChanged) {
            navBarStates.transitionMode = ((EventTypeFactory.EventType.OnNavBarTransitionModeChanged) eventType).transitionMode;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnUpdateRegionSamplingListener) {
            navBarStates.regionSamplingEnabled = ((EventTypeFactory.EventType.OnUpdateRegionSamplingListener) eventType).registered;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnLayoutContainerChanged) {
            this.layoutProviderContainer = ((EventTypeFactory.EventType.OnLayoutContainerChanged) eventType).layoutProviderContainer;
            updateLayoutProvider();
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnSetDisableFlags) {
            EventTypeFactory.EventType.OnSetDisableFlags onSetDisableFlags = (EventTypeFactory.EventType.OnSetDisableFlags) eventType;
            navBarStates.disable1 = onSetDisableFlags.disable1;
            navBarStates.disable2 = onSetDisableFlags.disable2;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged) {
            BarLayoutParams barLayoutParams = ((EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged) eventType).layoutParamsProvider;
            if (barLayoutParams == null) {
                this.navBarLayoutParams = supportLargeCoverScreenNavBar() ? new NavBarCoverLayoutParams(this.context, this) : new NavBarLayoutParams(this.context, this);
                return;
            } else {
                this.navBarLayoutParams = barLayoutParams;
                return;
            }
        }
        if (eventType instanceof EventTypeFactory.EventType.OnCoverRotationChanged) {
            navBarStates.rotation = ((EventTypeFactory.EventType.OnCoverRotationChanged) eventType).rotation;
            navBarStates.layoutProvider = this.layoutProviderContainer.updateLayoutProvider(supportLargeCoverScreenNavBar(), true);
        } else if (eventType instanceof EventTypeFactory.EventType.OnNavBarStyleChanged) {
            navBarStates.userSetupCompleteForCurrentUser = ((EventTypeFactory.EventType.OnNavBarStyleChanged) eventType).currentUserSetupComplete;
        } else if (eventType instanceof EventTypeFactory.EventType.OnUpdateTaskbarAvailable) {
            navBarStates.userSetupCompleteForCurrentUser = Settings.Secure.getIntForUser(this.context.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) == 1;
        }
    }

    public final void updateUseThemeDefault() {
        String activeThemePackage = this.settingsHelper.getActiveThemePackage();
        int i = 1;
        boolean z = activeThemePackage != null && activeThemePackage.length() > 0;
        SettingsHelper settingsHelper = this.settingsHelper;
        if (!z && !settingsHelper.isColorThemeEnabled()) {
            i = 0;
        }
        settingsHelper.setNavigationBarUseThemeDefault(i);
    }

    public /* synthetic */ NavBarStateManagerImpl(Context context, NavBarStore navBarStore, SettingsHelper settingsHelper, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, UserTracker userTracker, Point point, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, navBarStore, settingsHelper, interactorFactory, storeLogUtil, layoutProviderContainer, navBarRemoteViewManager, userTracker, (i & 256) != 0 ? new Point() : point);
    }
}
