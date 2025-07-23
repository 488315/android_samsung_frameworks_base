package com.android.systemui.accessibility.floatingmenu;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.IUserInitializationCompleteCallback;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.bluetooth.HearingAidDeviceManager;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AccessibilityFloatingMenuController implements AccessibilityButtonModeObserver.ModeChangedListener, AccessibilityButtonTargetsObserver.TargetsChangedListener {
    public final AccessibilityButtonModeObserver mAccessibilityButtonModeObserver;
    public final AccessibilityButtonTargetsObserver mAccessibilityButtonTargetsObserver;
    public final AccessibilityManager mAccessibilityManager;
    public int mBtnMode;
    public String mBtnTargets;
    public Context mContext;
    public final DeviceStateManager mDeviceStateManager;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    IAccessibilityFloatingMenu mFloatingMenu;
    Handler mHandler;
    public final HearingAidDeviceManager mHearingAidDeviceManager;
    public boolean mIsUserInInitialization;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final NavigationModeController mNavigationModeController;
    public final SecureSettings mSecureSettings;
    public final SemDesktopModeManager mSemDesktopModeManager;
    public final WindowManager mWindowManager;
    final KeyguardUpdateMonitorCallback mKeyguardCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController.mIsKeyguardVisible = z;
            accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitching(int i) {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController.destroyFloatingMenu();
            accessibilityFloatingMenuController.mIsUserInInitialization = true;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, accessibilityFloatingMenuController.mIsKeyguardVisible);
        }
    };
    final UserInitializationCompleteCallback mUserInitializationCompleteCallback = new UserInitializationCompleteCallback();
    public final AnonymousClass2 mDesktopModeListener = new AnonymousClass2();
    public final AnonymousClass3 mDisplayStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController.3
        public final void onDeviceStateChanged(DeviceState deviceState) {
            if (deviceState.getIdentifier() == 3) {
                AccessibilityFloatingMenuController.this.destroyFloatingMenu();
                if (AccessibilityFloatingMenuController.this.mKeyguardManager.isKeyguardLocked()) {
                    return;
                }
                AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
                accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, accessibilityFloatingMenuController.mIsKeyguardVisible);
                return;
            }
            if (deviceState.getIdentifier() == 0) {
                AccessibilityFloatingMenuController.this.destroyFloatingMenu();
                if (!AccessibilityUtils.isFoldedLargeCoverScreen() || AccessibilityFloatingMenuController.this.mKeyguardManager.isKeyguardSecure()) {
                    return;
                }
                AccessibilityFloatingMenuController accessibilityFloatingMenuController2 = AccessibilityFloatingMenuController.this;
                accessibilityFloatingMenuController2.handleFloatingMenuVisibility(accessibilityFloatingMenuController2.mBtnMode, accessibilityFloatingMenuController2.mBtnTargets, false);
            }
        }
    };
    public final AnonymousClass4 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController.4
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.LOCALE_CHANGED".equals(action) || "com.samsung.android.theme.themecenter.THEME_APPLY".equals(action)) {
                AccessibilityFloatingMenuController.this.destroyFloatingMenu();
                AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
                accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, accessibilityFloatingMenuController.mIsKeyguardVisible);
            }
        }
    };
    public boolean mIsKeyguardVisible = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$2, reason: invalid class name */
    public class AnonymousClass2 implements SemDesktopModeManager.DesktopModeListener {
        public AnonymousClass2() {
        }

        public final void onDesktopModeStateChanged(final SemDesktopModeState semDesktopModeState) {
            if (semDesktopModeState == null || semDesktopModeState.state != 50) {
                return;
            }
            AccessibilityFloatingMenuController.this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityFloatingMenuController.AnonymousClass2 anonymousClass2 = AccessibilityFloatingMenuController.AnonymousClass2.this;
                    SemDesktopModeState semDesktopModeState2 = semDesktopModeState;
                    AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
                    accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, accessibilityFloatingMenuController.mIsKeyguardVisible);
                    if (semDesktopModeState2.enabled == 4) {
                        AccessibilityFloatingMenuController accessibilityFloatingMenuController2 = AccessibilityFloatingMenuController.this;
                        accessibilityFloatingMenuController2.mSemDesktopModeManager.registerListener(accessibilityFloatingMenuController2.mDesktopModeListener);
                    }
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UserInitializationCompleteCallback extends IUserInitializationCompleteCallback.Stub {
        public UserInitializationCompleteCallback() {
        }

        public final void onUserInitializationComplete(int i) {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            int i2 = 0;
            accessibilityFloatingMenuController.mIsUserInInitialization = false;
            try {
                i2 = Integer.parseInt(accessibilityFloatingMenuController.mAccessibilityButtonModeObserver.getSettingsValue());
            } catch (NumberFormatException e) {
                Log.e("A11yButtonModeObserver", "Invalid string for  " + e);
            }
            accessibilityFloatingMenuController.mBtnMode = i2;
            AccessibilityFloatingMenuController accessibilityFloatingMenuController2 = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController2.mBtnTargets = accessibilityFloatingMenuController2.mAccessibilityButtonTargetsObserver.getSettingsValue();
            AccessibilityFloatingMenuController.this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$UserInitializationCompleteCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityFloatingMenuController.UserInitializationCompleteCallback userInitializationCompleteCallback = AccessibilityFloatingMenuController.UserInitializationCompleteCallback.this;
                    AccessibilityFloatingMenuController.this.destroyFloatingMenu();
                    AccessibilityFloatingMenuController accessibilityFloatingMenuController3 = AccessibilityFloatingMenuController.this;
                    accessibilityFloatingMenuController3.handleFloatingMenuVisibility(accessibilityFloatingMenuController3.mBtnMode, accessibilityFloatingMenuController3.mBtnTargets, accessibilityFloatingMenuController3.mIsKeyguardVisible);
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$3] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$4] */
    public AccessibilityFloatingMenuController(Context context, WindowManager windowManager, DisplayManager displayManager, AccessibilityManager accessibilityManager, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, AccessibilityButtonModeObserver accessibilityButtonModeObserver, HearingAidDeviceManager hearingAidDeviceManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SecureSettings secureSettings, DisplayTracker displayTracker, NavigationModeController navigationModeController, Handler handler) {
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mDisplayManager = displayManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mAccessibilityButtonTargetsObserver = accessibilityButtonTargetsObserver;
        this.mAccessibilityButtonModeObserver = accessibilityButtonModeObserver;
        this.mHearingAidDeviceManager = hearingAidDeviceManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSecureSettings = secureSettings;
        this.mDisplayTracker = displayTracker;
        this.mNavigationModeController = navigationModeController;
        this.mHandler = handler;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        this.mSemDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
        this.mDeviceStateManager = (DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class);
    }

    public final void destroyFloatingMenu() {
        IAccessibilityFloatingMenu iAccessibilityFloatingMenu = this.mFloatingMenu;
        if (iAccessibilityFloatingMenu == null) {
            return;
        }
        AccessibilityFloatingMenu accessibilityFloatingMenu = (AccessibilityFloatingMenu) iAccessibilityFloatingMenu;
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = accessibilityFloatingMenu.mMenuView;
        if (accessibilityFloatingMenuView.mIsShowing) {
            accessibilityFloatingMenuView.hide$1();
            accessibilityFloatingMenuView.mOnDragEndListener = Optional.ofNullable(null);
            MenuInfoRepository menuInfoRepository = accessibilityFloatingMenu.mInfoRepository;
            menuInfoRepository.mContext.getContentResolver().unregisterContentObserver(menuInfoRepository.mMenuTargetFeaturesContentObserver);
            menuInfoRepository.mContext.getContentResolver().unregisterContentObserver(menuInfoRepository.mMenuSizeContentObserver);
            menuInfoRepository.mContext.getContentResolver().unregisterContentObserver(menuInfoRepository.mMenuFadeOutContentObserver);
            menuInfoRepository.mContext.unregisterComponentCallbacks(menuInfoRepository.mComponentCallbacks);
            menuInfoRepository.mAccessibilityManager.removeAccessibilityServicesStateChangeListener(menuInfoRepository.mA11yServicesStateChangeListener);
            if (menuInfoRepository.mHearingAidDeviceManager != null) {
                ((ConcurrentHashMap) HearingAidDeviceManager.mConnectionStatusListeners).remove(menuInfoRepository.mHearingDeviceStatusListener);
            }
            accessibilityFloatingMenu.mNavigationModeController.removeListener(accessibilityFloatingMenu.mNavigationModeChangedListener);
        }
        this.mFloatingMenu = null;
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
            SemDesktopModeManager semDesktopModeManager = this.mSemDesktopModeManager;
            if (semDesktopModeManager != null) {
                semDesktopModeManager.unregisterListener(this.mDesktopModeListener);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public final void handleFloatingMenuVisibility(int i, String str, boolean z) {
        int i2;
        if (((z || this.mIsUserInInitialization) && !AccessibilityUtils.isFoldedLargeCoverScreen()) || (z && AccessibilityUtils.isFoldedLargeCoverScreen() && this.mKeyguardManager.isKeyguardSecure())) {
            destroyFloatingMenu();
            return;
        }
        if (!AccessibilityUtils.isDesktopWindowing(this.mContext)) {
            if (i == 1 && !TextUtils.isEmpty(str)) {
                if (this.mFloatingMenu == null) {
                    DisplayManager displayManager = this.mDisplayManager;
                    if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
                        i2 = 1;
                    } else {
                        this.mDisplayTracker.getClass();
                        i2 = 0;
                    }
                    Context createWindowContext = this.mContext.createWindowContext(displayManager.getDisplay(i2), 2024, null);
                    if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
                        this.mContext = AccessibilityUtils.getSubDisplayContext(this.mContext);
                    }
                    this.mFloatingMenu = new AccessibilityFloatingMenu(createWindowContext, this.mWindowManager, this.mAccessibilityManager, this.mSecureSettings, this.mNavigationModeController, this.mHearingAidDeviceManager);
                    this.mContext.registerReceiver(this.mBroadcastReceiver, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.LOCALE_CHANGED", "com.samsung.android.theme.themecenter.THEME_APPLY"), 2);
                    SemDesktopModeManager semDesktopModeManager = this.mSemDesktopModeManager;
                    if (semDesktopModeManager != null) {
                        semDesktopModeManager.registerListener(this.mDesktopModeListener);
                    }
                }
                AccessibilityFloatingMenu accessibilityFloatingMenu = (AccessibilityFloatingMenu) this.mFloatingMenu;
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = accessibilityFloatingMenu.mMenuView;
                if (accessibilityFloatingMenuView.mIsShowing) {
                    return;
                }
                List targets = AccessibilityTargetHelper.getTargets(accessibilityFloatingMenu.mContext, 1);
                if (targets.isEmpty()) {
                    return;
                }
                accessibilityFloatingMenuView.show();
                accessibilityFloatingMenuView.onTargetFeaturesChanged(targets);
                accessibilityFloatingMenuView.updateOpacityWith(Settings.Secure.getFloat(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_opacity", 0.55f), Settings.Secure.getInt(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_fade_enabled", 1) == 1);
                accessibilityFloatingMenuView.setSizeType(Settings.Secure.getIntForUser(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_size", 9, -2));
                accessibilityFloatingMenuView.setShapeType(Settings.Secure.getInt(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_icon_type", 0));
                accessibilityFloatingMenuView.mOnDragEndListener = Optional.ofNullable(new AccessibilityFloatingMenu$$ExternalSyntheticLambda1(accessibilityFloatingMenu));
                if (accessibilityFloatingMenu.mIsHideHandle) {
                    accessibilityFloatingMenuView.updateHideHandle((int) AccessibilityFloatingMenu.getPosition(accessibilityFloatingMenu.mContext).mPercentageY);
                    accessibilityFloatingMenuView.updateHideHandleLocationWith(AccessibilityFloatingMenu.getPosition(accessibilityFloatingMenu.mContext));
                }
                MenuInfoRepository menuInfoRepository = accessibilityFloatingMenu.mInfoRepository;
                SecureSettings secureSettings = menuInfoRepository.mSecureSettings;
                secureSettings.registerContentObserverForUserSync(secureSettings.getUriFor("accessibility_button_targets"), false, menuInfoRepository.mMenuTargetFeaturesContentObserver, -2);
                secureSettings.registerContentObserverForUserSync(secureSettings.getUriFor(SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES), false, menuInfoRepository.mMenuTargetFeaturesContentObserver, -2);
                secureSettings.registerContentObserverForUserSync(secureSettings.getUriFor("accessibility_floating_menu_size"), false, menuInfoRepository.mMenuSizeContentObserver, -2);
                secureSettings.registerContentObserverForUserSync(secureSettings.getUriFor("accessibility_floating_menu_fade_enabled"), false, menuInfoRepository.mMenuFadeOutContentObserver, -2);
                secureSettings.registerContentObserverForUserSync(secureSettings.getUriFor("accessibility_floating_menu_opacity"), false, menuInfoRepository.mMenuFadeOutContentObserver, -2);
                menuInfoRepository.mContext.registerComponentCallbacks(menuInfoRepository.mComponentCallbacks);
                menuInfoRepository.mAccessibilityManager.addAccessibilityServicesStateChangeListener(menuInfoRepository.mA11yServicesStateChangeListener);
                if (menuInfoRepository.mHearingAidDeviceManager != null) {
                    ((ConcurrentHashMap) HearingAidDeviceManager.mConnectionStatusListeners).put(menuInfoRepository.mHearingDeviceStatusListener, ThreadUtils.getBackgroundExecutor());
                }
                accessibilityFloatingMenu.mNavigationModeController.addListener(accessibilityFloatingMenu.mNavigationModeChangedListener);
                return;
            }
        }
        destroyFloatingMenu();
    }

    public final void init() {
        int i;
        AccessibilityButtonModeObserver accessibilityButtonModeObserver = this.mAccessibilityButtonModeObserver;
        try {
            i = Integer.parseInt(accessibilityButtonModeObserver.getSettingsValue());
        } catch (NumberFormatException e) {
            Log.e("A11yButtonModeObserver", "Invalid string for  " + e);
            i = 0;
        }
        this.mBtnMode = i;
        AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver = this.mAccessibilityButtonTargetsObserver;
        this.mBtnTargets = accessibilityButtonTargetsObserver.getSettingsValue();
        accessibilityButtonModeObserver.addListener(this);
        accessibilityButtonTargetsObserver.addListener(this);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardCallback);
        this.mAccessibilityManager.registerUserInitializationCompleteCallback(this.mUserInitializationCompleteCallback);
        if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP) {
            this.mDeviceStateManager.registerCallback(this.mContext.getMainExecutor(), this.mDisplayStateCallback);
        }
    }

    @Override // com.android.systemui.accessibility.AccessibilityButtonModeObserver.ModeChangedListener
    public final void onAccessibilityButtonModeChanged(int i) {
        this.mBtnMode = i;
        handleFloatingMenuVisibility(i, this.mBtnTargets, this.mIsKeyguardVisible);
    }

    @Override // com.android.systemui.accessibility.AccessibilityButtonTargetsObserver.TargetsChangedListener
    public final void onAccessibilityButtonTargetsChanged(String str) {
        this.mBtnTargets = str;
        handleFloatingMenuVisibility(this.mBtnMode, str, this.mIsKeyguardVisible);
    }
}
