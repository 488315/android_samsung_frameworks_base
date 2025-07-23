package com.android.systemui.navigationbar.store;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.provider.Settings;
import android.util.Log;
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
import com.android.systemui.navigationbar.layout.NavBarCoverLayoutParams;
import com.android.systemui.navigationbar.layout.NavBarLayoutParams;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.util.StoreLogUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0052, code lost:
    
        if (canShowHideKeyboardButtonForRotation(r5) == false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean canShowKeyboardButtonForRotation(int r5) {
        /*
            r4 = this;
            android.content.Context r0 = r4.context
            android.content.ContentResolver r0 = r0.getContentResolver()
            com.android.systemui.navigationbar.model.NavBarStates r1 = r4.states
            int r1 = r1.lastTaskUserId
            java.lang.String r2 = "show_keyboard_button"
            r3 = 0
            int r0 = android.provider.Settings.Secure.getIntForUser(r0, r2, r3, r1)
            r1 = 1
            if (r0 == 0) goto L17
            r0 = r1
            goto L18
        L17:
            r0 = r3
        L18:
            boolean r2 = r4.isGestureMode()
            if (r2 == 0) goto L2f
            boolean r2 = r4.supportLargeCoverScreenNavBar()
            if (r2 != 0) goto L2e
            if (r0 == 0) goto L2e
            boolean r0 = r4.canPlaceKeyboardButton(r5)
            if (r0 == 0) goto L2e
            r0 = r1
            goto L2f
        L2e:
            r0 = r3
        L2f:
            if (r0 != 0) goto L54
            boolean r0 = com.android.systemui.BasicRune.NAVBAR_MULTI_MODAL_ICON
            if (r0 == 0) goto L4b
            boolean r0 = r4.isGestureMode()
            if (r0 == 0) goto L49
            com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager r0 = r4.navBarRemoteViewManager
            boolean r0 = r0.isSetMultimodalButton()
            if (r0 == 0) goto L4b
            boolean r0 = r4.canPlaceKeyboardButton(r5)
            if (r0 == 0) goto L4b
        L49:
            r0 = r1
            goto L4c
        L4b:
            r0 = r3
        L4c:
            if (r0 != 0) goto L54
            boolean r0 = r4.canShowHideKeyboardButtonForRotation(r5)
            if (r0 == 0) goto L55
        L54:
            r3 = r1
        L55:
            java.lang.String r0 = "canShowKeyboardButtonForRotation("
            java.lang.String r1 = ")"
            java.lang.String r5 = androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(r5, r0, r1)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r3)
            r4.logNavBarStates(r0, r5)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStateManagerImpl.canShowKeyboardButtonForRotation(int):boolean");
    }

    public final boolean canShowKeyboardButtonOnLeft() {
        return !((((SysUiState) Dependency.sDependency.getDependencyInner(SysUiState.class)).getFlags() & 16) == 0 || isGestureMode()) || Settings.Secure.getIntForUser(this.context.getContentResolver(), SettingsHelper.INDEX_KEYBOARD_BUTTON_POSITION, 0, this.states.lastTaskUserId) == 0;
    }

    public final int getButtonWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        layoutProvider.getClass();
        int buttonWidth = layoutProvider.getButtonWidth(this.states.displaySize, z);
        Integer valueOf = Integer.valueOf(buttonWidth);
        logNavBarStates(valueOf, "getButtonWidth(land: " + z + ")");
        return valueOf.intValue();
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
        Integer valueOf = Integer.valueOf(gestureWidth);
        logNavBarStates(valueOf, "getGestureWidth(land: " + z + ")");
        return valueOf.intValue();
    }

    public final int getNavBarHeight(int i) {
        return shouldShowSUWStyle() ? this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_left_edge_size) : this.navBarLayoutParams.getBarHeight(this.states.canMove, i);
    }

    public final SettingsHelper getSettingHelper() {
        return this.settingsHelper;
    }

    public final int getSpaceWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        layoutProvider.getClass();
        int spaceWidth = layoutProvider.getSpaceWidth(this.states.displaySize, z, NavBarStateManager.isSideAndBottomGestureMode$default(this));
        Integer valueOf = Integer.valueOf(spaceWidth);
        logNavBarStates(valueOf, "getSpaceWidth(land: " + z + ")");
        return valueOf.intValue();
    }

    public final boolean isBottomGestureMode(boolean z) {
        boolean z2 = this.states.navigationMode == 3;
        if (z) {
            logNavBarStates(Boolean.valueOf(z2), "isBottomGestureMode");
        }
        return z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
    
        if ((r0 != null ? r0.foldCache : false) != false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isCoverDisplayNavBarEnabled() {
        /*
            r4 = this;
            boolean r0 = r4.supportLargeCoverScreenNavBar()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L15
            boolean r0 = r4.isLargeCoverScreenSyncEnabled()
            if (r0 != 0) goto L14
            boolean r4 = r4.isCoverLauncherNavBarEnabled()
            if (r4 == 0) goto L40
        L14:
            return r1
        L15:
            com.android.systemui.navigationbar.model.NavBarStates r0 = r4.states
            boolean r0 = r0.supportCoverScreen
            if (r0 == 0) goto L2e
            com.android.systemui.navigationbar.interactor.InteractorFactory r0 = r4.interactorFactory
            java.lang.Class<com.android.systemui.navigationbar.interactor.DeviceStateInteractor> r3 = com.android.systemui.navigationbar.interactor.DeviceStateInteractor.class
            java.lang.Object r0 = r0.get(r3)
            com.android.systemui.navigationbar.interactor.DeviceStateInteractor r0 = (com.android.systemui.navigationbar.interactor.DeviceStateInteractor) r0
            if (r0 == 0) goto L2a
            boolean r0 = r0.foldCache
            goto L2b
        L2a:
            r0 = r2
        L2b:
            if (r0 == 0) goto L2e
            goto L2f
        L2e:
            r1 = r2
        L2f:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r1)
            java.lang.String r3 = "supportCoverScreenNavBar"
            r4.logNavBarStates(r0, r3)
            if (r1 == 0) goto L40
            boolean r4 = r4.isCoverLauncherNavBarEnabled()
            return r4
        L40:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStateManagerImpl.isCoverDisplayNavBarEnabled():boolean");
    }

    public final boolean isCoverLauncherNavBarEnabled() {
        if (this.states.supportCoverScreen) {
            CoverDisplayWidgetInteractor coverDisplayWidgetInteractor = (CoverDisplayWidgetInteractor) this.interactorFactory.get(CoverDisplayWidgetInteractor.class);
            Boolean valueOf = coverDisplayWidgetInteractor != null ? Boolean.valueOf(coverDisplayWidgetInteractor.isEnabled()) : null;
            logNavBarStates(valueOf, "isCoverLauncherNavBarEnabled");
            if (valueOf != null ? valueOf.booleanValue() : false) {
                return true;
            }
        }
        return false;
    }

    public final boolean isGameMode(boolean z) {
        NavBarRemoteView navBarRemoteView = (NavBarRemoteView) this.navBarRemoteViewManager.leftViewList.peek();
        boolean equals = navBarRemoteView != null ? "com.samsung.android.game.gametools".equals(navBarRemoteView.requestClass) : false;
        if (z) {
            logNavBarStates(Boolean.valueOf(equals), "isGameMode");
        }
        return equals;
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
        boolean isLargeCoverScreenNavigation = this.settingsHelper.isLargeCoverScreenNavigation();
        logNavBarStates(Boolean.valueOf(isLargeCoverScreenNavigation), "isLargeCoverScreenSyncEnabled");
        return isLargeCoverScreenNavigation;
    }

    public final boolean isLargeCoverTaskEnabled() {
        DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) this.interactorFactory.get(DeviceStateInteractor.class);
        if (deviceStateInteractor != null) {
            return deviceStateInteractor.coverTaskCache;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x006b, code lost:
    
        if (r10 != null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006d, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x008a, code lost:
    
        r0 = r11.states;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x008d, code lost:
    
        if (r4 != 1) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x008f, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0090, code lost:
    
        r0.multiModalForLargeCover = java.lang.Boolean.valueOf(r1);
        android.util.Log.d("NavBarStateManager", "multiModalForLargeCover : " + r11.states.multiModalForLargeCover);
        r11 = r11.states.multiModalForLargeCover;
        r11.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00b6, code lost:
    
        return r11.booleanValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0087, code lost:
    
        if (r10 == null) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isMultiModalAvailableInLargeCover() {
        /*
            r11 = this;
            boolean r0 = r11.supportLargeCoverScreenNavBar()
            r1 = 0
            java.lang.String r2 = "NavBarStateManager"
            if (r0 != 0) goto Lf
            java.lang.String r11 = "multiModalForLargeCover = false (not in cover display)"
            android.util.Log.d(r2, r11)
            return r1
        Lf:
            com.android.systemui.navigationbar.model.NavBarStates r0 = r11.states
            java.lang.Boolean r0 = r0.multiModalForLargeCover
            if (r0 == 0) goto L1d
            r0.getClass()
            boolean r11 = r0.booleanValue()
            return r11
        L1d:
            java.lang.String r0 = "content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider"
            java.lang.String r3 = "cover_voice_icon"
            java.lang.String[] r8 = new java.lang.String[]{r3}
            r10 = 0
            android.content.Context r4 = r11.context     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L71
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L71
            android.net.Uri r5 = android.net.Uri.parse(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L71
            r7 = 0
            r9 = 0
            r6 = 0
            android.database.Cursor r10 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L71
            r4 = r1
            if (r10 == 0) goto L6b
        L3a:
            boolean r0 = r10.moveToNext()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            if (r0 == 0) goto L6b
            java.lang.String r0 = "NAME"
            int r0 = r10.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            r5 = -1
            if (r0 == r5) goto L3a
            java.lang.String r0 = r10.getString(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            if (r0 == 0) goto L3a
            int r5 = r0.length()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            if (r5 <= 0) goto L3a
            boolean r0 = r0.equals(r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            if (r0 == 0) goto L3a
            java.lang.String r0 = "VALUE"
            int r0 = r10.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            int r4 = r10.getInt(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L69
            goto L3a
        L66:
            r0 = move-exception
            r11 = r0
            goto Lb7
        L69:
            r0 = move-exception
            goto L73
        L6b:
            if (r10 == 0) goto L8a
        L6d:
            r10.close()
            goto L8a
        L71:
            r0 = move-exception
            r4 = r1
        L73:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L66
            r3.<init>()     // Catch: java.lang.Throwable -> L66
            java.lang.String r5 = "Failed to retrieve cover_voice_icon. "
            r3.append(r5)     // Catch: java.lang.Throwable -> L66
            r3.append(r0)     // Catch: java.lang.Throwable -> L66
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L66
            android.util.Log.e(r2, r0)     // Catch: java.lang.Throwable -> L66
            if (r10 == 0) goto L8a
            goto L6d
        L8a:
            com.android.systemui.navigationbar.model.NavBarStates r0 = r11.states
            r3 = 1
            if (r4 != r3) goto L90
            r1 = r3
        L90:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r0.multiModalForLargeCover = r1
            com.android.systemui.navigationbar.model.NavBarStates r0 = r11.states
            java.lang.Boolean r0 = r0.multiModalForLargeCover
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "multiModalForLargeCover : "
            r1.<init>(r3)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.util.Log.d(r2, r0)
            com.android.systemui.navigationbar.model.NavBarStates r11 = r11.states
            java.lang.Boolean r11 = r11.multiModalForLargeCover
            r11.getClass()
            boolean r11 = r11.booleanValue()
            return r11
        Lb7:
            if (r10 == 0) goto Lbc
            r10.close()
        Lbc:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStateManagerImpl.isMultiModalAvailableInLargeCover():boolean");
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
        boolean isNavigationBarUseThemeDefault = this.settingsHelper.isNavigationBarUseThemeDefault();
        logNavBarStates(Boolean.valueOf(isNavigationBarUseThemeDefault), "isNavigationBarUseThemeDefault");
        return isNavigationBarUseThemeDefault;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x015b  */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isTaskBarEnabled(boolean r17) {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStateManagerImpl.isTaskBarEnabled(boolean):boolean");
    }

    public final void logNavBarStates(Object obj, String str) {
        StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(this.context.getDisplayId(), "NavBarStates(", ") ", str, ": ");
        m.append(obj);
        Log.d("NavBarStateManager", m.toString());
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
