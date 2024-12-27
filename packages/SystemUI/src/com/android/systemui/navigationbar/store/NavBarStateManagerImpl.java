package com.android.systemui.navigationbar.store;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.provider.Settings;
import android.util.Log;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
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
import com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor;
import com.android.systemui.navigationbar.layout.NavBarCoverLayoutParams;
import com.android.systemui.navigationbar.layout.NavBarLayoutParams;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.util.StoreLogUtil;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public NavBarStateManagerImpl(Context context, NavBarStore navBarStore, SettingsHelper settingsHelper, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, Point point) {
        this.context = context;
        this.navBarStore = navBarStore;
        this.settingsHelper = settingsHelper;
        this.interactorFactory = interactorFactory;
        this.logWrapper = storeLogUtil;
        this.layoutProviderContainer = layoutProviderContainer;
        this.navBarRemoteViewManager = navBarRemoteViewManager;
        boolean z = false;
        this.states = new NavBarStates(point, false, false, null, 0, 0, 0, false, 0, false, false, 0, z, z, false, false, false, false, false, 0, false, false, false, false, false, 0, null, 134217726, null);
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
        boolean z = (!this.settingsHelper.isNavigationBarGestureHintEnabled() || this.states.sPayShowing || isNavBarButtonForcedVisible()) ? false : true;
        logNavBarStates(Boolean.valueOf(z), "canShowGestureHint");
        return z;
    }

    public final boolean canShowHideKeyboardButtonForRotation(int i) {
        if (isGestureMode()) {
            return this.settingsHelper.isNavigationBarHideKeyboardButtonEnabled() && canPlaceKeyboardButton(i);
        }
        return true;
    }

    public final boolean canShowKeyboardButtonForRotation(int i) {
        boolean z = Settings.Secure.getIntForUser(this.context.getContentResolver(), SettingsHelper.INDEX_SHOW_KEYBOARD_BUTTON, 0, this.states.lastTaskUserId) != 0;
        if (isGestureMode()) {
            z = !supportLargeCoverScreenNavBar() && z && canPlaceKeyboardButton(i);
        }
        boolean z2 = z || canShowMultiModalButtonForRotation(i) || canShowHideKeyboardButtonForRotation(i);
        logNavBarStates(Boolean.valueOf(z2), LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "canShowKeyboardButtonForRotation(", ")"));
        return z2;
    }

    public final boolean canShowKeyboardButtonOnLeft() {
        return !((((SysUiState) Dependency.sDependency.getDependencyInner(SysUiState.class)).mFlags & 16) == 0 || isGestureMode()) || Settings.Secure.getIntForUser(this.context.getContentResolver(), SettingsHelper.INDEX_KEYBOARD_BUTTON_POSITION, 0, this.states.lastTaskUserId) == 0;
    }

    public final boolean canShowMultiModalButtonForRotation(int i) {
        if (BasicRune.NAVBAR_MULTI_MODAL_ICON) {
            return !isGestureMode() || (this.navBarRemoteViewManager.isSetMultimodalButton() && canPlaceKeyboardButton(i));
        }
        return false;
    }

    public final int getButtonWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
        int buttonWidth = layoutProvider.getButtonWidth(this.states.displaySize, z);
        Integer valueOf = Integer.valueOf(buttonWidth);
        logNavBarStates(valueOf, "getButtonWidth(land: " + z + ")");
        return valueOf.intValue();
    }

    public final String getDefaultLayout() {
        SettingsHelper settingsHelper = this.settingsHelper;
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
        String layout = layoutProvider.getLayout(!settingsHelper.isNavBarButtonOrderDefault(), settingsHelper.getNavigationBarAlignPosition());
        logNavBarStates(layout, "getDefaultLayout");
        Intrinsics.checkNotNull(layout);
        return layout;
    }

    public final String getGesturalLayout(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
        String gesturalLayout = layoutProvider.getGesturalLayout(z, !this.settingsHelper.isNavBarButtonOrderDefault());
        logNavBarStates(gesturalLayout, "getGesturalLayout");
        Intrinsics.checkNotNull(gesturalLayout);
        return gesturalLayout;
    }

    public final int getGestureWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
        int gestureWidth = layoutProvider.getGestureWidth(this.states.displaySize, z);
        Integer valueOf = Integer.valueOf(gestureWidth);
        logNavBarStates(valueOf, "getGestureWidth(land: " + z + ")");
        return valueOf.intValue();
    }

    public final SettingsHelper getSettingHelper() {
        return this.settingsHelper;
    }

    public final int getSpaceWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002d, code lost:
    
        if ((r0 != null ? r0.foldCache : false) != false) goto L19;
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
            if (r0 == 0) goto L17
            boolean r0 = r4.isLargeCoverScreenSyncEnabled()
            if (r0 != 0) goto L41
            boolean r4 = r4.isCoverLauncherNavBarEnabled()
            if (r4 == 0) goto L15
            goto L41
        L15:
            r1 = r2
            goto L41
        L17:
            com.android.systemui.navigationbar.model.NavBarStates r0 = r4.states
            boolean r0 = r0.supportCoverScreen
            if (r0 == 0) goto L30
            com.android.systemui.navigationbar.interactor.InteractorFactory r0 = r4.interactorFactory
            java.lang.Class<com.android.systemui.navigationbar.interactor.DeviceStateInteractor> r3 = com.android.systemui.navigationbar.interactor.DeviceStateInteractor.class
            java.lang.Object r0 = r0.get(r3)
            com.android.systemui.navigationbar.interactor.DeviceStateInteractor r0 = (com.android.systemui.navigationbar.interactor.DeviceStateInteractor) r0
            if (r0 == 0) goto L2c
            boolean r0 = r0.foldCache
            goto L2d
        L2c:
            r0 = r2
        L2d:
            if (r0 == 0) goto L30
            goto L31
        L30:
            r1 = r2
        L31:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r1)
            java.lang.String r3 = "supportCoverScreenNavBar"
            r4.logNavBarStates(r0, r3)
            if (r1 == 0) goto L15
            boolean r1 = r4.isCoverLauncherNavBarEnabled()
        L41:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStateManagerImpl.isCoverDisplayNavBarEnabled():boolean");
    }

    public final boolean isCoverLauncherNavBarEnabled() {
        if (!this.states.supportCoverScreen) {
            return false;
        }
        CoverDisplayWidgetInteractor coverDisplayWidgetInteractor = (CoverDisplayWidgetInteractor) this.interactorFactory.get(CoverDisplayWidgetInteractor.class);
        Boolean valueOf = coverDisplayWidgetInteractor != null ? Boolean.valueOf(coverDisplayWidgetInteractor.isEnabled()) : null;
        logNavBarStates(valueOf, "isCoverLauncherNavBarEnabled");
        return valueOf != null ? valueOf.booleanValue() : false;
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
        if (this.states.supportLargeCoverScreen) {
            boolean isLargeCoverScreenNavigation = this.settingsHelper.isLargeCoverScreenNavigation();
            logNavBarStates(Boolean.valueOf(isLargeCoverScreenNavigation), "isLargeCoverScreenSyncEnabled");
            if (isLargeCoverScreenNavigation) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0089, code lost:
    
        r3 = r11.states;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x008c, code lost:
    
        if (r0 != 1) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x008e, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x008f, code lost:
    
        r3.multiModalForLargeCover = java.lang.Boolean.valueOf(r1);
        android.util.Log.d("NavBarStateManager", "multiModalForLargeCover : " + r11.states.multiModalForLargeCover);
        r11 = r11.states.multiModalForLargeCover;
        kotlin.jvm.internal.Intrinsics.checkNotNull(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00b5, code lost:
    
        return r11.booleanValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0086, code lost:
    
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
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            boolean r11 = r0.booleanValue()
            return r11
        L1d:
            java.lang.String r0 = "content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider"
            java.lang.String r3 = "cover_voice_icon"
            java.lang.String[] r8 = new java.lang.String[]{r3}
            r10 = 0
            android.content.Context r4 = r11.context     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L70
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L70
            android.net.Uri r5 = android.net.Uri.parse(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L70
            r7 = 0
            r9 = 0
            r6 = 0
            android.database.Cursor r10 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L70
            r0 = r1
            if (r10 == 0) goto L6a
        L3a:
            boolean r4 = r10.moveToNext()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            if (r4 == 0) goto L6a
            java.lang.String r4 = "NAME"
            int r4 = r10.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r5 = -1
            if (r4 == r5) goto L3a
            java.lang.String r4 = r10.getString(r4)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            if (r4 == 0) goto L3a
            int r5 = r4.length()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            if (r5 <= 0) goto L3a
            boolean r4 = r4.equals(r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            if (r4 == 0) goto L3a
            java.lang.String r4 = "VALUE"
            int r4 = r10.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            int r0 = r10.getInt(r4)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            goto L3a
        L66:
            r11 = move-exception
            goto Lb6
        L68:
            r3 = move-exception
            goto L72
        L6a:
            if (r10 == 0) goto L89
        L6c:
            r10.close()
            goto L89
        L70:
            r3 = move-exception
            r0 = r1
        L72:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L66
            r4.<init>()     // Catch: java.lang.Throwable -> L66
            java.lang.String r5 = "Failed to retrieve cover_voice_icon. "
            r4.append(r5)     // Catch: java.lang.Throwable -> L66
            r4.append(r3)     // Catch: java.lang.Throwable -> L66
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> L66
            android.util.Log.e(r2, r3)     // Catch: java.lang.Throwable -> L66
            if (r10 == 0) goto L89
            goto L6c
        L89:
            com.android.systemui.navigationbar.model.NavBarStates r3 = r11.states
            r4 = 1
            if (r0 != r4) goto L8f
            r1 = r4
        L8f:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r1)
            r3.multiModalForLargeCover = r0
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
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11)
            boolean r11 = r11.booleanValue()
            return r11
        Lb6:
            if (r10 == 0) goto Lbb
            r10.close()
        Lbb:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStateManagerImpl.isMultiModalAvailableInLargeCover():boolean");
    }

    public final boolean isNavBarButtonForcedVisible() {
        GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = (GestureNavigationSettingsInteractor) this.interactorFactory.get(GestureNavigationSettingsInteractor.class);
        if (gestureNavigationSettingsInteractor != null) {
            return gestureNavigationSettingsInteractor.buttonForcedVisible;
        }
        return false;
    }

    public final boolean isNavBarHidden() {
        DesktopModeInteractor desktopModeInteractor;
        if (isNavBarHiddenByKnox() || ((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isStandalone()) {
            return true;
        }
        return BasicRune.NAVBAR_NEW_DEX && (desktopModeInteractor = (DesktopModeInteractor) this.interactorFactory.get(DesktopModeInteractor.class)) != null && desktopModeInteractor.isEnabled();
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
    /* JADX WARN: Removed duplicated region for block: B:24:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0105  */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isTaskBarEnabled(boolean r15) {
        /*
            Method dump skipped, instructions count: 338
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
        boolean z = false;
        navBarStates.supportCoverScreen = BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && displayId == 1;
        navBarStates.supportLargeCoverScreen = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && displayId == 1;
        EventTypeFactory eventTypeFactory = this.eventTypeFactory;
        Resources resources = eventTypeFactory.context.getResources();
        ((ArrayList) eventTypeFactory.updatableEvents).clear();
        ((ArrayList) eventTypeFactory.updatableEvents).add(new EventTypeFactory.EventType.OnConfigChanged(resources.getConfiguration()));
        List list = eventTypeFactory.updatableEvents;
        boolean z2 = resources.getBoolean(R.bool.config_orderUnlockAndWake) && !DeviceType.isTablet();
        if (resources.getBoolean(com.android.systemui.R.bool.config_navBarSupportPhoneLayoutProvider) && !DeviceType.isTablet()) {
            z = true;
        }
        ((ArrayList) list).add(new EventTypeFactory.EventType.OnNavBarConfigChanged(z2, z, resources.getBoolean(R.bool.config_pdp_reject_enable_retry), resources.getInteger(R.integer.config_pinnerWebviewPinBytes)));
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001e, code lost:
    
        if (((com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) ((com.android.systemui.statusbar.policy.KeyguardStateController) ((com.android.systemui.navigationbar.store.NavBarStoreImpl) r0).getModule(com.android.systemui.statusbar.policy.KeyguardStateController.class, 0))).mShowing == false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldShowSUWStyle() {
        /*
            r3 = this;
            com.android.systemui.navigationbar.model.NavBarStates r0 = r3.states
            boolean r1 = r0.deviceProvisioned
            r2 = 0
            if (r1 == 0) goto Lb
            boolean r0 = r0.userSetupCompleted
            if (r0 != 0) goto L21
        Lb:
            com.android.systemui.navigationbar.store.NavBarStore r0 = r3.navBarStore
            r0.getClass()
            com.android.systemui.navigationbar.store.NavBarStoreImpl r0 = (com.android.systemui.navigationbar.store.NavBarStoreImpl) r0
            java.lang.Class<com.android.systemui.statusbar.policy.KeyguardStateController> r1 = com.android.systemui.statusbar.policy.KeyguardStateController.class
            java.lang.Object r0 = r0.getModule(r1, r2)
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = (com.android.systemui.statusbar.policy.KeyguardStateController) r0
            com.android.systemui.statusbar.policy.KeyguardStateControllerImpl r0 = (com.android.systemui.statusbar.policy.KeyguardStateControllerImpl) r0
            boolean r0 = r0.mShowing
            if (r0 != 0) goto L21
            goto L27
        L21:
            boolean r3 = r3.isNavBarButtonForcedVisible()
            if (r3 == 0) goto L28
        L27:
            r2 = 1
        L28:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarStateManagerImpl.shouldShowSUWStyle():boolean");
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
            SamsungNavigationBarProxy.Companion.getInstance();
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
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarButtonForcedVisibleChanged) {
            navBarStates.userSetupCompleted = ((EventTypeFactory.EventType.OnNavBarButtonForcedVisibleChanged) eventType).userSetupComplete;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarIconHintChanged) {
            navBarStates.iconHint = ((EventTypeFactory.EventType.OnNavBarIconHintChanged) eventType).iconHint;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged) {
            navBarStates.gestureDisabledByPolicy = ((EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged) eventType).disabled;
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
        } else {
            if (!(eventType instanceof EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged)) {
                if (eventType instanceof EventTypeFactory.EventType.OnCoverRotationChanged) {
                    navBarStates.rotation = ((EventTypeFactory.EventType.OnCoverRotationChanged) eventType).rotation;
                    navBarStates.layoutProvider = this.layoutProviderContainer.updateLayoutProvider(supportLargeCoverScreenNavBar(), true);
                    return;
                }
                return;
            }
            BarLayoutParams barLayoutParams = ((EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged) eventType).layoutParamsProvider;
            if (barLayoutParams == null) {
                this.navBarLayoutParams = supportLargeCoverScreenNavBar() ? new NavBarCoverLayoutParams(this.context, this) : new NavBarLayoutParams(this.context, this);
            } else {
                this.navBarLayoutParams = barLayoutParams;
            }
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

    public /* synthetic */ NavBarStateManagerImpl(Context context, NavBarStore navBarStore, SettingsHelper settingsHelper, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, Point point, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, navBarStore, settingsHelper, interactorFactory, storeLogUtil, layoutProviderContainer, navBarRemoteViewManager, (i & 128) != 0 ? new Point() : point);
    }
}
