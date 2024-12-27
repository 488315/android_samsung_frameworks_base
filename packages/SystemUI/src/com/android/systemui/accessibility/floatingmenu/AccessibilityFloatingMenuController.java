package com.android.systemui.accessibility.floatingmenu;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.WindowManager;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.view.SemWindowManager;
import java.util.Optional;

public final class AccessibilityFloatingMenuController implements AccessibilityButtonModeObserver.ModeChangedListener, AccessibilityButtonTargetsObserver.TargetsChangedListener {
    public final AccessibilityButtonModeObserver mAccessibilityButtonModeObserver;
    public final AccessibilityButtonTargetsObserver mAccessibilityButtonTargetsObserver;
    public int mBtnMode;
    public String mBtnTargets;
    public Context mContext;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    IAccessibilityFloatingMenu mFloatingMenu;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SecureSettings mSecureSettings;
    public final SemDesktopModeManager mSemDesktopModeManager;
    final KeyguardUpdateMonitorCallback mKeyguardCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController.mIsKeyguardVisible = z;
            accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            int i2 = 0;
            accessibilityFloatingMenuController.mContext = accessibilityFloatingMenuController.mContext.createContextAsUser(UserHandle.of(i), 0);
            try {
                i2 = Integer.parseInt(accessibilityFloatingMenuController.mAccessibilityButtonModeObserver.getSettingsValue());
            } catch (NumberFormatException e) {
                Log.e("A11yButtonModeObserver", "Invalid string for  " + e);
            }
            accessibilityFloatingMenuController.mBtnMode = i2;
            String settingsValue = accessibilityFloatingMenuController.mAccessibilityButtonTargetsObserver.getSettingsValue();
            accessibilityFloatingMenuController.mBtnTargets = settingsValue;
            accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, settingsValue, accessibilityFloatingMenuController.mIsKeyguardVisible);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitching(int i) {
            AccessibilityFloatingMenuController.this.destroyFloatingMenu();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserUnlocked() {
            AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, accessibilityFloatingMenuController.mIsKeyguardVisible);
        }
    };
    public final AnonymousClass2 mDesktopModeListener = new AnonymousClass2();
    public final AnonymousClass3 mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController.3
        public final void onFoldStateChanged(boolean z) {
            AccessibilityFloatingMenuController.this.destroyFloatingMenu();
            if (AccessibilityUtils.isFoldedLargeCoverScreen() && !AccessibilityFloatingMenuController.this.mKeyguardManager.isKeyguardSecure()) {
                AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
                accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, false);
            }
            if (z || AccessibilityFloatingMenuController.this.mKeyguardManager.isKeyguardLocked()) {
                return;
            }
            AccessibilityFloatingMenuController accessibilityFloatingMenuController2 = AccessibilityFloatingMenuController.this;
            accessibilityFloatingMenuController2.handleFloatingMenuVisibility(accessibilityFloatingMenuController2.mBtnMode, accessibilityFloatingMenuController2.mBtnTargets, accessibilityFloatingMenuController2.mIsKeyguardVisible);
        }

        public final void onTableModeChanged(boolean z) {
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
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* renamed from: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$2, reason: invalid class name */
    public final class AnonymousClass2 implements SemDesktopModeManager.DesktopModeListener {
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

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$3] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$4] */
    public AccessibilityFloatingMenuController(Context context, WindowManager windowManager, DisplayManager displayManager, AccessibilityManager accessibilityManager, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, AccessibilityButtonModeObserver accessibilityButtonModeObserver, KeyguardUpdateMonitor keyguardUpdateMonitor, SecureSettings secureSettings, DisplayTracker displayTracker) {
        this.mContext = context;
        this.mDisplayManager = displayManager;
        this.mAccessibilityButtonTargetsObserver = accessibilityButtonTargetsObserver;
        this.mAccessibilityButtonModeObserver = accessibilityButtonModeObserver;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSecureSettings = secureSettings;
        this.mDisplayTracker = displayTracker;
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
        this.mSemDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
    }

    public final void destroyFloatingMenu() {
        IAccessibilityFloatingMenu iAccessibilityFloatingMenu = this.mFloatingMenu;
        if (iAccessibilityFloatingMenu == null) {
            return;
        }
        AccessibilityFloatingMenu accessibilityFloatingMenu = (AccessibilityFloatingMenu) iAccessibilityFloatingMenu;
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = accessibilityFloatingMenu.mMenuView;
        boolean z = accessibilityFloatingMenuView.mIsShowing;
        if (z) {
            if (z) {
                accessibilityFloatingMenuView.mIsShowing = false;
                accessibilityFloatingMenuView.mDragAnimator.cancel();
                accessibilityFloatingMenuView.mWindowManager.removeView(accessibilityFloatingMenuView);
                accessibilityFloatingMenuView.setOnApplyWindowInsetsListener(null);
                accessibilityFloatingMenuView.setSystemGestureExclusion();
                accessibilityFloatingMenuView.getContext().unregisterReceiver(accessibilityFloatingMenuView.mAccessibilityFloatingReceiver);
                EditTooltipView editTooltipView = accessibilityFloatingMenuView.mEditTooltipView;
                if (editTooltipView != null) {
                    editTooltipView.hide();
                }
            }
            accessibilityFloatingMenuView.mOnDragEndListener = Optional.ofNullable(null);
            accessibilityFloatingMenu.mDockTooltipView.hide();
            accessibilityFloatingMenu.mContext.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mContentObserver);
            accessibilityFloatingMenu.mContext.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mSizeContentObserver);
            accessibilityFloatingMenu.mContext.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mFadeOutContentObserver);
            accessibilityFloatingMenu.mContext.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mEnabledA11yServicesContentObserver);
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002f, code lost:
    
        if (r10.enabled == 4) goto L55;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleFloatingMenuVisibility(int r8, java.lang.String r9, boolean r10) {
        /*
            Method dump skipped, instructions count: 429
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController.handleFloatingMenuVisibility(int, java.lang.String, boolean):void");
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
        if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP) {
            SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
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
