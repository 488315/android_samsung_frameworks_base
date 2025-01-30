package com.android.systemui.navigationbar.store;

import android.R;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.FactoryTest;
import android.provider.Settings;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.navigationbar.SamsungNavigationBarProxy;
import com.android.systemui.navigationbar.interactor.CoverDisplayWidgetInteractor;
import com.android.systemui.navigationbar.interactor.DesktopModeInteractor;
import com.android.systemui.navigationbar.interactor.DeviceStateInteractor;
import com.android.systemui.navigationbar.interactor.GestureNavigationSettingsInteractor;
import com.android.systemui.navigationbar.interactor.InteractorFactory;
import com.android.systemui.navigationbar.interactor.KnoxStateMonitorInteractor;
import com.android.systemui.navigationbar.interactor.TaskBarInteractor;
import com.android.systemui.navigationbar.layout.NavBarCoverLayoutParams;
import com.android.systemui.navigationbar.layout.NavBarLayoutParams;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.util.StoreLogUtil;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.view.SemWindowManager;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarStateManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final EventTypeFactory eventTypeFactory;
    public final InteractorFactory interactorFactory;
    public LayoutProviderContainer layoutProviderContainer;
    public final StoreLogUtil logWrapper;
    public BarLayoutParams navBarLayoutParams;
    public final NavBarRemoteViewManager navBarRemoteViewManager;
    public final NavBarStore navBarStore;
    public final SettingsHelper settingsHelper;
    public States states;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NavBarStateManager(Context context, NavBarStore navBarStore, SettingsHelper settingsHelper, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, Point point) {
        this.context = context;
        this.navBarStore = navBarStore;
        this.settingsHelper = settingsHelper;
        this.interactorFactory = interactorFactory;
        this.logWrapper = storeLogUtil;
        this.layoutProviderContainer = layoutProviderContainer;
        this.navBarRemoteViewManager = navBarRemoteViewManager;
        boolean z = false;
        this.states = new States(point, false, false, null, 0, 0, 0, false, 0, false, false, 0, z, z, false, false, false, false, false, 0, false, false, false, false, false, 0, null, 134217726, null);
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
        if (isGameMode(false)) {
            SettingsHelper settingsHelper = this.settingsHelper;
            settingsHelper.getClass();
            boolean z3 = BasicRune.NAVBAR_REMOTEVIEW;
            if (z3 && settingsHelper.mItemLists.get("game_double_swipe_enable").getIntValue() != 0) {
                if (!(z3 && settingsHelper.mItemLists.get("game_show_floating_icon").getIntValue() != 0)) {
                    z2 = true;
                }
            }
        }
        if (!z) {
            return z2;
        }
        Boolean valueOf = Boolean.valueOf(z2);
        logNavBarStates(valueOf, "canShowFloatingGameTools");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if ((!isGestureMode() || (r4.settingsHelper.isNavigationBarHideKeyboardButtonEnabled() && canPlaceKeyboardButton(r5))) != false) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean canShowKeyboardButtonForRotation(int i) {
        boolean z = false;
        boolean z2 = Settings.Secure.getIntForUser(this.context.getContentResolver(), "show_keyboard_button", 0, this.states.lastTaskUserId) != 0;
        if (isGestureMode()) {
            z2 = !supportLargeCoverScreenNavBar() && z2 && canPlaceKeyboardButton(i);
        }
        if (!z2) {
            if (!(BasicRune.NAVBAR_MULTI_MODAL_ICON && (!isGestureMode() || (this.navBarRemoteViewManager.isSetMultimodalButton() && canPlaceKeyboardButton(i))))) {
            }
        }
        z = true;
        String m31m = LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("canShowKeyboardButtonForRotation(", i, ")");
        Boolean valueOf = Boolean.valueOf(z);
        logNavBarStates(valueOf, m31m);
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    public final int getButtonWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
        int buttonWidth = layoutProvider.getButtonWidth(this.states.displaySize, z);
        Integer valueOf = Integer.valueOf(buttonWidth);
        logNavBarStates(valueOf, "getButtonWidth(land: " + z + ")");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.intValue();
    }

    public final String getDefaultLayout() {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
        String layout = layoutProvider.getLayout(!r1.isNavBarButtonOrderDefault(), this.settingsHelper.getNavigationBarAlignPosition());
        logNavBarStates(layout, "getDefaultLayout");
        Intrinsics.checkNotNull(layout);
        return layout;
    }

    public final int getGestureWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
        int gestureWidth = layoutProvider.getGestureWidth(this.states.displaySize, z);
        Integer valueOf = Integer.valueOf(gestureWidth);
        logNavBarStates(valueOf, "getGestureWidth(land: " + z + ")");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.intValue();
    }

    public final int getSpaceWidth(boolean z) {
        LayoutProvider layoutProvider = this.states.layoutProvider;
        Intrinsics.checkNotNull(layoutProvider);
        int spaceWidth = layoutProvider.getSpaceWidth(this.states.displaySize, z, isSideAndBottomGestureMode(false));
        Integer valueOf = Integer.valueOf(spaceWidth);
        logNavBarStates(valueOf, "getSpaceWidth(land: " + z + ")");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.intValue();
    }

    public final boolean isBlockingGestureOnGame() {
        boolean z = false;
        if (canShowFloatingGameTools(false) && !isIMEShowing(false)) {
            z = true;
        }
        Boolean valueOf = Boolean.valueOf(z);
        logNavBarStates(valueOf, "isBlockingGestureOnGame");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    public final boolean isBottomGestureMode(boolean z) {
        boolean z2 = this.states.navigationMode == 3;
        if (!z) {
            return z2;
        }
        Boolean valueOf = Boolean.valueOf(z2);
        logNavBarStates(valueOf, "isBottomGestureMode");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002b, code lost:
    
        if ((r0 != null ? r0.foldCache : false) != false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isCoverDisplayNavBarEnabled() {
        boolean z = true;
        if (!supportLargeCoverScreenNavBar()) {
            if (this.states.supportCoverScreen) {
                DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) this.interactorFactory.get(DeviceStateInteractor.class);
            }
            z = false;
            Boolean valueOf = Boolean.valueOf(z);
            logNavBarStates(valueOf, "supportCoverScreenNavBar");
            Intrinsics.checkNotNull(valueOf);
            if (valueOf.booleanValue()) {
                return isCoverLauncherNavBarEnabled();
            }
        } else if (isLargeCoverScreenSyncEnabled() || isCoverLauncherNavBarEnabled()) {
            return true;
        }
        return false;
    }

    public final boolean isCoverLauncherNavBarEnabled() {
        Boolean bool;
        if (!this.states.supportCoverScreen) {
            return false;
        }
        CoverDisplayWidgetInteractor coverDisplayWidgetInteractor = (CoverDisplayWidgetInteractor) this.interactorFactory.get(CoverDisplayWidgetInteractor.class);
        if (coverDisplayWidgetInteractor != null) {
            SettingsHelper settingsHelper = coverDisplayWidgetInteractor.settingsHelper;
            settingsHelper.getClass();
            bool = Boolean.valueOf((LsRune.SUBSCREEN_WATCHFACE && settingsHelper.mItemLists.get("show_navigation_for_subscreen").getIntValue() != 0) && SemWindowManager.getInstance().isFolded());
        } else {
            bool = null;
        }
        logNavBarStates(bool, "isCoverLauncherNavBarEnabled");
        return bool != null ? bool.booleanValue() : false;
    }

    public final boolean isGameMode(boolean z) {
        NavBarRemoteView navBarRemoteView = (NavBarRemoteView) this.navBarRemoteViewManager.leftViewList.peek();
        boolean areEqual = navBarRemoteView != null ? Intrinsics.areEqual("com.samsung.android.game.gametools", navBarRemoteView.requestClass) : false;
        if (!z) {
            return areEqual;
        }
        Boolean valueOf = Boolean.valueOf(areEqual);
        logNavBarStates(valueOf, "isGameMode");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    public final boolean isGestureHintEnabled() {
        return this.settingsHelper.isNavigationBarGestureHintEnabled();
    }

    public final boolean isGestureMode() {
        return isBottomGestureMode(false) || isSideAndBottomGestureMode(false);
    }

    public final boolean isIMEShowing(boolean z) {
        boolean z2 = (this.states.iconHint & 1) != 0;
        if (!z) {
            return z2;
        }
        Boolean valueOf = Boolean.valueOf(z2);
        logNavBarStates(valueOf, "isIMEShowing");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    public final boolean isLargeCoverScreenSyncEnabled() {
        if (!this.states.supportLargeCoverScreen) {
            return false;
        }
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.getClass();
        Boolean valueOf = Boolean.valueOf(BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && settingsHelper.mItemLists.get("large_cover_screen_navigation").getIntValue() != 0);
        logNavBarStates(valueOf, "isLargeCoverScreenSyncEnabled");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    public final boolean isLargeCoverTaskEnabled() {
        DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) this.interactorFactory.get(DeviceStateInteractor.class);
        if (deviceStateInteractor != null) {
            return deviceStateInteractor.coverTaskCache;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0090, code lost:
    
        r3 = r12.states;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0092, code lost:
    
        if (r0 != 1) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0094, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0095, code lost:
    
        r3.multiModalForLargeCover = java.lang.Boolean.valueOf(r2);
        android.util.Log.d("NavBarStateManager", "multiModalForLargeCover : " + r12.states.multiModalForLargeCover);
        r12 = r12.states.multiModalForLargeCover;
        kotlin.jvm.internal.Intrinsics.checkNotNull(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00bc, code lost:
    
        return r12.booleanValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x008d, code lost:
    
        if (r11 == null) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isMultiModalAvailableInLargeCover() {
        int i;
        String string;
        boolean z = false;
        if (!supportLargeCoverScreenNavBar()) {
            Log.d("NavBarStateManager", "multiModalForLargeCover = false (not in cover display)");
            return false;
        }
        Boolean bool = this.states.multiModalForLargeCover;
        if (bool != null) {
            Intrinsics.checkNotNull(bool);
            return bool.booleanValue();
        }
        Cursor cursor = null;
        try {
            try {
                cursor = this.context.getContentResolver().query(Uri.parse("content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider"), null, null, new String[]{"cover_voice_icon"}, null);
                i = 0;
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        try {
                            int columnIndex = cursor.getColumnIndex("NAME");
                            if (columnIndex != -1 && (string = cursor.getString(columnIndex)) != null) {
                                if ((string.length() > 0) && Intrinsics.areEqual(string, "cover_voice_icon")) {
                                    i = cursor.getInt(cursor.getColumnIndex("VALUE"));
                                }
                            }
                        } catch (Exception e) {
                            e = e;
                            Log.e("NavBarStateManager", "Failed to retrieve cover_voice_icon. " + e);
                        }
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception e2) {
            e = e2;
            i = 0;
        }
    }

    public final boolean isNavBarButtonForcedVisible() {
        GestureNavigationSettingsInteractor gestureNavigationSettingsInteractor = (GestureNavigationSettingsInteractor) this.interactorFactory.get(GestureNavigationSettingsInteractor.class);
        if (gestureNavigationSettingsInteractor != null) {
            return gestureNavigationSettingsInteractor.buttonForcedVisible;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0031, code lost:
    
        if ((r3 != null && r3.isEnabled()) != false) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isNavBarHidden() {
        boolean z;
        if (isNavBarHiddenByKnox()) {
            return true;
        }
        if (!((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone()) {
            if (BasicRune.NAVBAR_NEW_DEX) {
                DesktopModeInteractor desktopModeInteractor = (DesktopModeInteractor) this.interactorFactory.get(DesktopModeInteractor.class);
            }
            z = false;
            return !z;
        }
        z = true;
        if (!z) {
        }
    }

    public final boolean isNavBarHiddenByKnox() {
        EdmMonitor edmMonitor;
        Boolean valueOf = Boolean.valueOf((((KnoxStateMonitorInteractor) this.interactorFactory.get(KnoxStateMonitorInteractor.class)) == null || (edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor) == null || !edmMonitor.mIsNavigationBarHidden) ? false : true);
        logNavBarStates(valueOf, "isNavBarHiddenByKnox");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    public final boolean isNavigationBarUseThemeDefault() {
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.getClass();
        boolean z = true;
        if (BasicRune.NAVBAR_ENABLED && settingsHelper.mItemLists.get("navigationbar_use_theme_default").getIntValue() != 1) {
            z = false;
        }
        Boolean valueOf = Boolean.valueOf(z);
        logNavBarStates(valueOf, "isNavigationBarUseThemeDefault");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    public final boolean isOpaqueNavigationBar() {
        int i = this.states.transitionMode;
        return i == 4 || i == 3 || (i == 8 && !isNavigationBarUseThemeDefault());
    }

    public final boolean isSideAndBottomGestureMode(boolean z) {
        boolean z2 = this.states.navigationMode == 2;
        if (!z) {
            return z2;
        }
        Boolean valueOf = Boolean.valueOf(z2);
        logNavBarStates(valueOf, "isSideAndBottomGestureMode");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x0148, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0154, code lost:
    
        if (r13 == false) goto L106;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0141  */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r16v0, types: [com.android.systemui.navigationbar.store.NavBarStateManager] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v23 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isTaskBarEnabled(boolean z) {
        ?? r11;
        Context context;
        String str;
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.getClass();
        boolean z2 = false;
        ?? r4 = 1;
        r4 = 1;
        ?? r2 = BasicRune.NAVBAR_SUPPORT_TASKBAR && settingsHelper.mItemLists.get("task_bar").getIntValue() != 0;
        boolean z3 = !FactoryTest.isFactoryBinary();
        InteractorFactory interactorFactory = this.interactorFactory;
        TaskBarInteractor taskBarInteractor = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class);
        boolean z4 = taskBarInteractor != null ? taskBarInteractor.userUnlocked : false;
        boolean z5 = this.states.deviceProvisioned;
        boolean z6 = !settingsHelper.isEasyModeOn();
        boolean z7 = !settingsHelper.isUltraPowerSavingMode();
        ActivityManagerWrapper.sInstance.getClass();
        if (ActivityTaskManager.getService().getLockTaskModeState() == 1) {
            r11 = true;
            ?? r112 = r11 ^ true;
            TaskBarInteractor taskBarInteractor2 = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class);
            ?? r7 = taskBarInteractor2 == null && taskBarInteractor2.isDefaultHome;
            context = this.context;
            boolean z8 = Settings.Secure.getIntForUser(context.getContentResolver(), "user_setup_complete", 0, -2) != 1;
            if (BasicRune.NAVBAR_NEW_DEX) {
                DesktopModeInteractor desktopModeInteractor = (DesktopModeInteractor) interactorFactory.get(DesktopModeInteractor.class);
                if ((desktopModeInteractor != null && desktopModeInteractor.isEnabled()) != false) {
                    z2 = true;
                }
            }
            this.states.userSetupCompleted = z8;
            if (z) {
                str = "";
            } else {
                str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m((r2 != false ? "a" : ImsProfile.TIMER_NAME_A).concat(z3 ? "b" : ImsProfile.TIMER_NAME_B), z4 ? "c" : ImsProfile.TIMER_NAME_C), context.getResources().getConfiguration().semDisplayDeviceType == 0 ? "d" : ImsProfile.TIMER_NAME_D), DeviceType.isTablet() ? "e" : ImsProfile.TIMER_NAME_E), z5 ? "f" : ImsProfile.TIMER_NAME_F), z6 ? "g" : ImsProfile.TIMER_NAME_G), z7 ? "h" : ImsProfile.TIMER_NAME_H), r112 != false ? "i" : ImsProfile.TIMER_NAME_I), r7 != false ? "j" : ImsProfile.TIMER_NAME_J), z8 ? "k" : ImsProfile.TIMER_NAME_K), z2 ? "l" : "L");
            }
            if (r2 != false && z3 && z4) {
                if (!(context.getResources().getConfiguration().semDisplayDeviceType != 0)) {
                }
                if (z5) {
                    if (!z6) {
                        if (!z7) {
                            if (r112 == false) {
                                if (r7 == false) {
                                }
                            }
                        }
                    }
                }
            }
            if (z2 || !z4) {
                r4 = 0;
            }
            Settings.Global.putInt(context.getContentResolver(), "sem_task_bar_available", r4);
            if (z) {
                return r4;
            }
            String m29m = PathParser$$ExternalSyntheticOutline0.m29m("isTaskbarEnabled(", str, ")");
            Boolean valueOf = Boolean.valueOf((boolean) r4);
            logNavBarStates(valueOf, m29m);
            Intrinsics.checkNotNull(valueOf);
            return valueOf.booleanValue();
        }
        r11 = false;
        ?? r1122 = r11 ^ true;
        TaskBarInteractor taskBarInteractor22 = (TaskBarInteractor) interactorFactory.get(TaskBarInteractor.class);
        if (taskBarInteractor22 == null) {
        }
        context = this.context;
        if (Settings.Secure.getIntForUser(context.getContentResolver(), "user_setup_complete", 0, -2) != 1) {
        }
        if (BasicRune.NAVBAR_NEW_DEX) {
        }
        this.states.userSetupCompleted = z8;
        if (z) {
        }
        if (r2 != false) {
            if (!(context.getResources().getConfiguration().semDisplayDeviceType != 0)) {
            }
            if (z5) {
            }
        }
        if (z2) {
        }
        r4 = 0;
        Settings.Global.putInt(context.getContentResolver(), "sem_task_bar_available", r4);
        if (z) {
        }
    }

    public final void logNavBarStates(Object obj, String str) {
        StringBuilder m61m = AbstractC0662xaf167275.m61m("NavBarStates(", this.context.getDisplayId(), ") ", str, ": ");
        m61m.append(obj);
        String sb = m61m.toString();
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (storeLogUtil.allowLogging) {
            storeLogUtil.printLog(storeLogUtil.lastDepth, sb);
        }
    }

    public final void onNavigationBarCreated() {
        States states = this.states;
        int displayId = this.context.getDisplayId();
        boolean z = false;
        states.supportCoverScreen = BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && displayId == 1;
        states.supportLargeCoverScreen = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && displayId == 1;
        EventTypeFactory eventTypeFactory = this.eventTypeFactory;
        Context context = eventTypeFactory.context;
        Resources resources = context.getResources();
        List list = eventTypeFactory.updatableEvents;
        ArrayList arrayList = (ArrayList) list;
        arrayList.clear();
        arrayList.add(new EventTypeFactory.EventType.OnConfigChanged(resources.getConfiguration()));
        boolean z2 = resources.getBoolean(R.bool.config_letterboxIsEducationEnabled) && !DeviceType.isTablet();
        if (resources.getBoolean(com.android.systemui.R.bool.config_navBarSupportPhoneLayoutProvider) && !DeviceType.isTablet()) {
            z = true;
        }
        arrayList.add(new EventTypeFactory.EventType.OnNavBarConfigChanged(z2, z, resources.getBoolean(R.bool.config_letterboxIsPolicyForIgnoringRequestedOrientationEnabled), resources.getInteger(R.integer.config_notificationsBatteryFullARGB)));
        arrayList.add(new EventTypeFactory.EventType.OnRotationChanged(context.getResources().getConfiguration().windowConfiguration.getRotation()));
        arrayList.add(new EventTypeFactory.EventType.OnDeviceProvisionedChanged(((DeviceProvisionedControllerImpl) ((DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class))).isDeviceProvisioned()));
        Iterator it = CollectionsKt___CollectionsKt.toList(list).iterator();
        while (it.hasNext()) {
            updateStateFromEvent((EventTypeFactory.EventType) it.next());
        }
        updateLayoutProvider();
    }

    public final boolean shouldShowSUWStyle() {
        States states = this.states;
        return ((!states.deviceProvisioned || !states.userSetupCompleted) && !((CentralSurfacesImpl) ((CentralSurfaces) ((NavBarStoreImpl) this.navBarStore).getModule(CentralSurfaces.class, 0))).isKeyguardShowing()) || isNavBarButtonForcedVisible();
    }

    public final boolean supportLargeCoverScreenNavBar() {
        boolean z = false;
        if (this.states.supportLargeCoverScreen) {
            DeviceStateInteractor deviceStateInteractor = (DeviceStateInteractor) this.interactorFactory.get(DeviceStateInteractor.class);
            if (deviceStateInteractor != null ? deviceStateInteractor.foldCache : false) {
                z = true;
            }
        }
        Boolean valueOf = Boolean.valueOf(z);
        logNavBarStates(valueOf, "supportLargeCoverScreenNavBar");
        Intrinsics.checkNotNull(valueOf);
        return valueOf.booleanValue();
    }

    public final void updateLayoutProvider() {
        StoreLogUtil storeLogUtil = this.logWrapper;
        if (storeLogUtil.allowLogging) {
            storeLogUtil.printLog(storeLogUtil.lastDepth, "updateLayoutProvider()");
        }
        States states = this.states;
        states.layoutProvider = this.layoutProviderContainer.updateLayoutProvider(supportLargeCoverScreenNavBar(), states.supportPhoneLayoutProvider);
    }

    public final void updateStateFromEvent(EventTypeFactory.EventType eventType) {
        States states = this.states;
        if (eventType instanceof EventTypeFactory.EventType.OnConfigChanged) {
            Configuration configuration = ((EventTypeFactory.EventType.OnConfigChanged) eventType).newConfig;
            states.rotation = configuration.windowConfiguration.getRotation();
            states.darkMode = (configuration.uiMode & 32) != 0;
            Point point = new Point();
            point.set(configuration.windowConfiguration.getBounds().width(), configuration.windowConfiguration.getBounds().height());
            Point point2 = states.displaySize;
            states.displayChanged = Math.min(point2.x, point2.y) != Math.min(point.x, point.y);
            states.displaySize = point;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarConfigChanged) {
            EventTypeFactory.EventType.OnNavBarConfigChanged onNavBarConfigChanged = (EventTypeFactory.EventType.OnNavBarConfigChanged) eventType;
            states.canMove = onNavBarConfigChanged.canMove;
            states.supportPhoneLayoutProvider = onNavBarConfigChanged.supportPhoneLayoutProvider;
            states.imeDownButtonForAllRotation = onNavBarConfigChanged.imeDownButtonForAllRotation;
            states.navigationMode = onNavBarConfigChanged.navigationMode;
            states.displayChanged = false;
            states.layoutProvider = this.layoutProviderContainer.updateLayoutProvider(supportLargeCoverScreenNavBar(), onNavBarConfigChanged.supportPhoneLayoutProvider);
            SamsungNavigationBarProxy.Companion.getClass();
            if (SamsungNavigationBarProxy.INSTANCE == null) {
                SamsungNavigationBarProxy.INSTANCE = new SamsungNavigationBarProxy();
            }
            updateLayoutProvider();
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnRotationChanged) {
            states.rotation = ((EventTypeFactory.EventType.OnRotationChanged) eventType).rotation;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnDeviceProvisionedChanged) {
            states.deviceProvisioned = ((EventTypeFactory.EventType.OnDeviceProvisionedChanged) eventType).provisioned;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarIconHintChanged) {
            states.iconHint = ((EventTypeFactory.EventType.OnNavBarIconHintChanged) eventType).iconHint;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged) {
            states.gestureDisabledByPolicy = ((EventTypeFactory.EventType.OnEdgeBackGestureDisabledByPolicyChanged) eventType).disabled;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnUpdateSpayVisibility) {
            states.sPayShowing = ((EventTypeFactory.EventType.OnUpdateSpayVisibility) eventType).showing;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnSetGestureHintVisibility) {
            EventTypeFactory.EventType.OnSetGestureHintVisibility onSetGestureHintVisibility = (EventTypeFactory.EventType.OnSetGestureHintVisibility) eventType;
            states.recentVisible = onSetGestureHintVisibility.recentVisible;
            states.homeVisible = onSetGestureHintVisibility.homeVisible;
            states.backVisible = onSetGestureHintVisibility.backVisible;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged) {
            states.hardKeyIntentPolicy = ((EventTypeFactory.EventType.OnHardKeyIntentPolicyChanged) eventType).intentStatus;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnNavBarTransitionModeChanged) {
            states.transitionMode = ((EventTypeFactory.EventType.OnNavBarTransitionModeChanged) eventType).transitionMode;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnUpdateRegionSamplingListener) {
            states.regionSamplingEnabled = ((EventTypeFactory.EventType.OnUpdateRegionSamplingListener) eventType).registered;
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnLayoutContainerChanged) {
            this.layoutProviderContainer = ((EventTypeFactory.EventType.OnLayoutContainerChanged) eventType).layoutProviderContainer;
            updateLayoutProvider();
            return;
        }
        if (eventType instanceof EventTypeFactory.EventType.OnSetDisableFlags) {
            EventTypeFactory.EventType.OnSetDisableFlags onSetDisableFlags = (EventTypeFactory.EventType.OnSetDisableFlags) eventType;
            states.disable1 = onSetDisableFlags.disable1;
            states.disable2 = onSetDisableFlags.disable2;
        } else {
            if (!(eventType instanceof EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged)) {
                if (eventType instanceof EventTypeFactory.EventType.OnCoverRotationChanged) {
                    states.rotation = ((EventTypeFactory.EventType.OnCoverRotationChanged) eventType).rotation;
                    states.layoutProvider = this.layoutProviderContainer.updateLayoutProvider(supportLargeCoverScreenNavBar(), true);
                    return;
                }
                return;
            }
            BarLayoutParams barLayoutParams = ((EventTypeFactory.EventType.OnBarLayoutParamsProviderChanged) eventType).layoutParamsProvider;
            if (barLayoutParams != null) {
                this.navBarLayoutParams = barLayoutParams;
                return;
            }
            boolean supportLargeCoverScreenNavBar = supportLargeCoverScreenNavBar();
            Context context = this.context;
            this.navBarLayoutParams = supportLargeCoverScreenNavBar ? new NavBarCoverLayoutParams(context, this) : new NavBarLayoutParams(context, this);
        }
    }

    public final void updateUseThemeDefault() {
        SettingsHelper settingsHelper = this.settingsHelper;
        String activeThemePackage = settingsHelper.getActiveThemePackage();
        int i = 1;
        if (!(activeThemePackage != null && activeThemePackage.length() > 0) && !settingsHelper.isColorThemeEnabled$1()) {
            i = 0;
        }
        Settings.Global.putInt(settingsHelper.mResolver, "navigationbar_use_theme_default", i);
    }

    public /* synthetic */ NavBarStateManager(Context context, NavBarStore navBarStore, SettingsHelper settingsHelper, InteractorFactory interactorFactory, StoreLogUtil storeLogUtil, LayoutProviderContainer layoutProviderContainer, NavBarRemoteViewManager navBarRemoteViewManager, Point point, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, navBarStore, settingsHelper, interactorFactory, storeLogUtil, layoutProviderContainer, navBarRemoteViewManager, (i & 128) != 0 ? new Point() : point);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class States {
        public boolean backVisible;
        public boolean canMove;
        public boolean darkMode;
        public boolean deviceProvisioned;
        public int disable1;
        public int disable2;
        public boolean displayChanged;
        public Point displaySize;
        public boolean gestureDisabledByPolicy;
        public boolean hardKeyIntentPolicy;
        public boolean homeVisible;
        public int iconHint;
        public boolean imeDownButtonForAllRotation;
        public int lastTaskUserId;
        public boolean layoutChangedBeforeAttached;
        public LayoutProvider layoutProvider;
        public Boolean multiModalForLargeCover;
        public int navigationMode;
        public boolean recentVisible;
        public boolean regionSamplingEnabled;
        public int rotation;
        public boolean sPayShowing;
        public boolean supportCoverScreen;
        public boolean supportLargeCoverScreen;
        public boolean supportPhoneLayoutProvider;
        public int transitionMode;
        public boolean userSetupCompleted;

        public States(Point point, boolean z, boolean z2, LayoutProvider layoutProvider, int i, int i2, int i3, boolean z3, int i4, boolean z4, boolean z5, int i5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, int i6, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, int i7, Boolean bool) {
            this.displaySize = point;
            this.canMove = z;
            this.supportPhoneLayoutProvider = z2;
            this.layoutProvider = layoutProvider;
            this.navigationMode = i;
            this.disable1 = i2;
            this.disable2 = i3;
            this.darkMode = z3;
            this.rotation = i4;
            this.deviceProvisioned = z4;
            this.userSetupCompleted = z5;
            this.iconHint = i5;
            this.sPayShowing = z6;
            this.gestureDisabledByPolicy = z7;
            this.recentVisible = z8;
            this.homeVisible = z9;
            this.backVisible = z10;
            this.hardKeyIntentPolicy = z11;
            this.imeDownButtonForAllRotation = z12;
            this.transitionMode = i6;
            this.regionSamplingEnabled = z13;
            this.displayChanged = z14;
            this.layoutChangedBeforeAttached = z15;
            this.supportCoverScreen = z16;
            this.supportLargeCoverScreen = z17;
            this.lastTaskUserId = i7;
            this.multiModalForLargeCover = bool;
        }

        public static States copy$default(States states) {
            Point point = states.displaySize;
            boolean z = states.canMove;
            boolean z2 = states.supportPhoneLayoutProvider;
            LayoutProvider layoutProvider = states.layoutProvider;
            int i = states.navigationMode;
            int i2 = states.disable1;
            int i3 = states.disable2;
            boolean z3 = states.darkMode;
            int i4 = states.rotation;
            boolean z4 = states.deviceProvisioned;
            boolean z5 = states.userSetupCompleted;
            int i5 = states.iconHint;
            boolean z6 = states.sPayShowing;
            boolean z7 = states.gestureDisabledByPolicy;
            boolean z8 = states.recentVisible;
            boolean z9 = states.homeVisible;
            boolean z10 = states.backVisible;
            boolean z11 = states.hardKeyIntentPolicy;
            boolean z12 = states.imeDownButtonForAllRotation;
            int i6 = states.transitionMode;
            boolean z13 = states.regionSamplingEnabled;
            boolean z14 = states.displayChanged;
            boolean z15 = states.layoutChangedBeforeAttached;
            boolean z16 = states.supportCoverScreen;
            boolean z17 = states.supportLargeCoverScreen;
            int i7 = states.lastTaskUserId;
            Boolean bool = states.multiModalForLargeCover;
            states.getClass();
            return new States(point, z, z2, layoutProvider, i, i2, i3, z3, i4, z4, z5, i5, z6, z7, z8, z9, z10, z11, z12, i6, z13, z14, z15, z16, z17, i7, bool);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof States)) {
                return false;
            }
            States states = (States) obj;
            return Intrinsics.areEqual(this.displaySize, states.displaySize) && this.canMove == states.canMove && this.supportPhoneLayoutProvider == states.supportPhoneLayoutProvider && Intrinsics.areEqual(this.layoutProvider, states.layoutProvider) && this.navigationMode == states.navigationMode && this.disable1 == states.disable1 && this.disable2 == states.disable2 && this.darkMode == states.darkMode && this.rotation == states.rotation && this.deviceProvisioned == states.deviceProvisioned && this.userSetupCompleted == states.userSetupCompleted && this.iconHint == states.iconHint && this.sPayShowing == states.sPayShowing && this.gestureDisabledByPolicy == states.gestureDisabledByPolicy && this.recentVisible == states.recentVisible && this.homeVisible == states.homeVisible && this.backVisible == states.backVisible && this.hardKeyIntentPolicy == states.hardKeyIntentPolicy && this.imeDownButtonForAllRotation == states.imeDownButtonForAllRotation && this.transitionMode == states.transitionMode && this.regionSamplingEnabled == states.regionSamplingEnabled && this.displayChanged == states.displayChanged && this.layoutChangedBeforeAttached == states.layoutChangedBeforeAttached && this.supportCoverScreen == states.supportCoverScreen && this.supportLargeCoverScreen == states.supportLargeCoverScreen && this.lastTaskUserId == states.lastTaskUserId && Intrinsics.areEqual(this.multiModalForLargeCover, states.multiModalForLargeCover);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int hashCode = this.displaySize.hashCode() * 31;
            boolean z = this.canMove;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = (hashCode + i) * 31;
            boolean z2 = this.supportPhoneLayoutProvider;
            int i3 = z2;
            if (z2 != 0) {
                i3 = 1;
            }
            int i4 = (i2 + i3) * 31;
            LayoutProvider layoutProvider = this.layoutProvider;
            int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.disable2, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.disable1, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.navigationMode, (i4 + (layoutProvider == null ? 0 : layoutProvider.hashCode())) * 31, 31), 31), 31);
            boolean z3 = this.darkMode;
            int i5 = z3;
            if (z3 != 0) {
                i5 = 1;
            }
            int m42m2 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.rotation, (m42m + i5) * 31, 31);
            boolean z4 = this.deviceProvisioned;
            int i6 = z4;
            if (z4 != 0) {
                i6 = 1;
            }
            int i7 = (m42m2 + i6) * 31;
            boolean z5 = this.userSetupCompleted;
            int i8 = z5;
            if (z5 != 0) {
                i8 = 1;
            }
            int m42m3 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.iconHint, (i7 + i8) * 31, 31);
            boolean z6 = this.sPayShowing;
            int i9 = z6;
            if (z6 != 0) {
                i9 = 1;
            }
            int i10 = (m42m3 + i9) * 31;
            boolean z7 = this.gestureDisabledByPolicy;
            int i11 = z7;
            if (z7 != 0) {
                i11 = 1;
            }
            int i12 = (i10 + i11) * 31;
            boolean z8 = this.recentVisible;
            int i13 = z8;
            if (z8 != 0) {
                i13 = 1;
            }
            int i14 = (i12 + i13) * 31;
            boolean z9 = this.homeVisible;
            int i15 = z9;
            if (z9 != 0) {
                i15 = 1;
            }
            int i16 = (i14 + i15) * 31;
            boolean z10 = this.backVisible;
            int i17 = z10;
            if (z10 != 0) {
                i17 = 1;
            }
            int i18 = (i16 + i17) * 31;
            boolean z11 = this.hardKeyIntentPolicy;
            int i19 = z11;
            if (z11 != 0) {
                i19 = 1;
            }
            int i20 = (i18 + i19) * 31;
            boolean z12 = this.imeDownButtonForAllRotation;
            int i21 = z12;
            if (z12 != 0) {
                i21 = 1;
            }
            int m42m4 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.transitionMode, (i20 + i21) * 31, 31);
            boolean z13 = this.regionSamplingEnabled;
            int i22 = z13;
            if (z13 != 0) {
                i22 = 1;
            }
            int i23 = (m42m4 + i22) * 31;
            boolean z14 = this.displayChanged;
            int i24 = z14;
            if (z14 != 0) {
                i24 = 1;
            }
            int i25 = (i23 + i24) * 31;
            boolean z15 = this.layoutChangedBeforeAttached;
            int i26 = z15;
            if (z15 != 0) {
                i26 = 1;
            }
            int i27 = (i25 + i26) * 31;
            boolean z16 = this.supportCoverScreen;
            int i28 = z16;
            if (z16 != 0) {
                i28 = 1;
            }
            int i29 = (i27 + i28) * 31;
            boolean z17 = this.supportLargeCoverScreen;
            int m42m5 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.lastTaskUserId, (i29 + (z17 ? 1 : z17 ? 1 : 0)) * 31, 31);
            Boolean bool = this.multiModalForLargeCover;
            return m42m5 + (bool != null ? bool.hashCode() : 0);
        }

        public final String toString() {
            Point point = this.displaySize;
            boolean z = this.canMove;
            boolean z2 = this.supportPhoneLayoutProvider;
            LayoutProvider layoutProvider = this.layoutProvider;
            int i = this.navigationMode;
            int i2 = this.disable1;
            int i3 = this.disable2;
            boolean z3 = this.darkMode;
            int i4 = this.rotation;
            boolean z4 = this.deviceProvisioned;
            boolean z5 = this.userSetupCompleted;
            int i5 = this.iconHint;
            boolean z6 = this.sPayShowing;
            boolean z7 = this.gestureDisabledByPolicy;
            boolean z8 = this.recentVisible;
            boolean z9 = this.homeVisible;
            boolean z10 = this.backVisible;
            boolean z11 = this.hardKeyIntentPolicy;
            boolean z12 = this.imeDownButtonForAllRotation;
            int i6 = this.transitionMode;
            boolean z13 = this.regionSamplingEnabled;
            boolean z14 = this.displayChanged;
            boolean z15 = this.layoutChangedBeforeAttached;
            boolean z16 = this.supportCoverScreen;
            boolean z17 = this.supportLargeCoverScreen;
            int i7 = this.lastTaskUserId;
            Boolean bool = this.multiModalForLargeCover;
            StringBuilder sb = new StringBuilder("States(displaySize=");
            sb.append(point);
            sb.append(", canMove=");
            sb.append(z);
            sb.append(", supportPhoneLayoutProvider=");
            sb.append(z2);
            sb.append(", layoutProvider=");
            sb.append(layoutProvider);
            sb.append(", navigationMode=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, i, ", disable1=", i2, ", disable2=");
            sb.append(i3);
            sb.append(", darkMode=");
            sb.append(z3);
            sb.append(", rotation=");
            sb.append(i4);
            sb.append(", deviceProvisioned=");
            sb.append(z4);
            sb.append(", userSetupCompleted=");
            sb.append(z5);
            sb.append(", iconHint=");
            sb.append(i5);
            sb.append(", sPayShowing=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z6, ", gestureDisabledByPolicy=", z7, ", recentVisible=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z8, ", homeVisible=", z9, ", backVisible=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z10, ", hardKeyIntentPolicy=", z11, ", imeDownButtonForAllRotation=");
            sb.append(z12);
            sb.append(", transitionMode=");
            sb.append(i6);
            sb.append(", regionSamplingEnabled=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z13, ", displayChanged=", z14, ", layoutChangedBeforeAttached=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z15, ", supportCoverScreen=", z16, ", supportLargeCoverScreen=");
            sb.append(z17);
            sb.append(", lastTaskUserId=");
            sb.append(i7);
            sb.append(", multiModalForLargeCover=");
            sb.append(bool);
            sb.append(")");
            return sb.toString();
        }

        public /* synthetic */ States(Point point, boolean z, boolean z2, LayoutProvider layoutProvider, int i, int i2, int i3, boolean z3, int i4, boolean z4, boolean z5, int i5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, int i6, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, int i7, Boolean bool, int i8, DefaultConstructorMarker defaultConstructorMarker) {
            this(point, (i8 & 2) != 0 ? true : z, (i8 & 4) != 0 ? true : z2, (i8 & 8) != 0 ? null : layoutProvider, (i8 & 16) != 0 ? 0 : i, (i8 & 32) != 0 ? 0 : i2, (i8 & 64) != 0 ? 0 : i3, (i8 & 128) != 0 ? false : z3, (i8 & 256) != 0 ? 0 : i4, (i8 & 512) != 0 ? false : z4, (i8 & 1024) != 0 ? true : z5, (i8 & 2048) != 0 ? 0 : i5, (i8 & 4096) != 0 ? false : z6, (i8 & 8192) != 0 ? false : z7, (i8 & 16384) != 0 ? true : z8, (i8 & 32768) != 0 ? true : z9, (i8 & 65536) != 0 ? true : z10, (i8 & 131072) != 0 ? false : z11, (i8 & 262144) != 0 ? false : z12, (i8 & 524288) != 0 ? 0 : i6, (i8 & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0 ? false : z13, (i8 & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0 ? false : z14, (i8 & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0 ? false : z15, (i8 & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) != 0 ? false : z16, (i8 & 16777216) != 0 ? false : z17, (i8 & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) == 0 ? i7 : 0, (i8 & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) != 0 ? null : bool);
        }
    }
}
