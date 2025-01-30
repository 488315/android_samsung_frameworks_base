package com.android.systemui.accessibility.floatingmenu;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.view.SemWindowManager;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
    public final C10152 mDesktopModeListener = new C10152();
    public final C10163 mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController.3
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
    public final C10174 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController.4
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.LOCALE_CHANGED")) {
                AccessibilityFloatingMenuController.this.destroyFloatingMenu();
                AccessibilityFloatingMenuController accessibilityFloatingMenuController = AccessibilityFloatingMenuController.this;
                accessibilityFloatingMenuController.handleFloatingMenuVisibility(accessibilityFloatingMenuController.mBtnMode, accessibilityFloatingMenuController.mBtnTargets, accessibilityFloatingMenuController.mIsKeyguardVisible);
            }
        }
    };
    public boolean mIsKeyguardVisible = false;
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$2 */
    public final class C10152 implements SemDesktopModeManager.DesktopModeListener {
        public C10152() {
        }

        public final void onDesktopModeStateChanged(final SemDesktopModeState semDesktopModeState) {
            if (semDesktopModeState == null || semDesktopModeState.state != 50) {
                return;
            }
            AccessibilityFloatingMenuController.this.mHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityFloatingMenuController.C10152 c10152 = AccessibilityFloatingMenuController.C10152.this;
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
            Context context = accessibilityFloatingMenu.mContext;
            context.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mContentObserver);
            context.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mSizeContentObserver);
            context.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mFadeOutContentObserver);
            context.getContentResolver().unregisterContentObserver(accessibilityFloatingMenu.mEnabledA11yServicesContentObserver);
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x019a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleFloatingMenuVisibility(int i, String str, boolean z) {
        boolean z2;
        int i2;
        SemDesktopModeState desktopModeState;
        if ((z && !AccessibilityUtils.isFoldedLargeCoverScreen()) || (z && AccessibilityUtils.isFoldedLargeCoverScreen() && this.mKeyguardManager.isKeyguardSecure())) {
            destroyFloatingMenu();
            return;
        }
        try {
            desktopModeState = ((SemDesktopModeManager) this.mContext.getSystemService("desktopmode")).getDesktopModeState();
        } catch (NullPointerException unused) {
        }
        if (desktopModeState != null) {
            if (desktopModeState.enabled == 4) {
                z2 = true;
                if (!((z2 || i != 1 || TextUtils.isEmpty(str)) ? false : true)) {
                    destroyFloatingMenu();
                    return;
                }
                if (this.mFloatingMenu == null) {
                    if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
                        i2 = 1;
                    } else {
                        this.mDisplayTracker.getClass();
                        i2 = 0;
                    }
                    Context createWindowContext = this.mContext.createWindowContext(this.mDisplayManager.getDisplay(i2), 2024, null);
                    if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
                        Context context = this.mContext;
                        this.mContext = context.createDisplayContext(((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN")[1]);
                    }
                    this.mFloatingMenu = new AccessibilityFloatingMenu(createWindowContext, Settings.Secure.getInt(this.mContext.getContentResolver(), "accessibility_floating_menu_icon_type", 0) == 9);
                    this.mContext.registerReceiver(this.mBroadcastReceiver, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("android.intent.action.LOCALE_CHANGED"), 2);
                    SemDesktopModeManager semDesktopModeManager = this.mSemDesktopModeManager;
                    if (semDesktopModeManager != null) {
                        semDesktopModeManager.registerListener(this.mDesktopModeListener);
                    }
                }
                AccessibilityFloatingMenu accessibilityFloatingMenu = (AccessibilityFloatingMenu) this.mFloatingMenu;
                final AccessibilityFloatingMenuView accessibilityFloatingMenuView = accessibilityFloatingMenu.mMenuView;
                if (accessibilityFloatingMenuView.mIsShowing) {
                    return;
                }
                Context context2 = accessibilityFloatingMenu.mContext;
                List targets = AccessibilityTargetHelper.getTargets(context2, 0);
                if (targets.isEmpty()) {
                    return;
                }
                if (!accessibilityFloatingMenuView.mIsShowing) {
                    accessibilityFloatingMenuView.mIsShowing = true;
                    accessibilityFloatingMenuView.mWindowManager.addView(accessibilityFloatingMenuView, accessibilityFloatingMenuView.mCurrentLayoutParams);
                    accessibilityFloatingMenuView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda6
                        @Override // android.view.View.OnApplyWindowInsetsListener
                        public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                            AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = AccessibilityFloatingMenuView.this;
                            WindowMetrics currentWindowMetrics = accessibilityFloatingMenuView2.mWindowManager.getCurrentWindowMetrics();
                            if (!currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout()).toRect().equals(accessibilityFloatingMenuView2.mDisplayInsetsRect)) {
                                accessibilityFloatingMenuView2.updateDisplaySizeWith(currentWindowMetrics);
                                if (accessibilityFloatingMenuView2.mIsHideHandle) {
                                    accessibilityFloatingMenuView2.updateHideHandleLocationWith(accessibilityFloatingMenuView2.mPosition);
                                } else {
                                    accessibilityFloatingMenuView2.updateLocationWith(accessibilityFloatingMenuView2.mPosition);
                                }
                            }
                            Rect rect = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.ime()).toRect();
                            if (!rect.equals(accessibilityFloatingMenuView2.mImeInsetsRect)) {
                                if ((rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0) ? false : true) {
                                    accessibilityFloatingMenuView2.mImeInsetsRect.set(rect);
                                } else {
                                    accessibilityFloatingMenuView2.mImeInsetsRect.setEmpty();
                                }
                                if (!accessibilityFloatingMenuView2.mIsHideHandle) {
                                    accessibilityFloatingMenuView2.updateLocationWith(accessibilityFloatingMenuView2.mPosition);
                                }
                            }
                            return windowInsets;
                        }
                    });
                    accessibilityFloatingMenuView.setSystemGestureExclusion();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.android.systemui.accessibility.floatingmenu.SHOW");
                    accessibilityFloatingMenuView.getContext().registerReceiver(accessibilityFloatingMenuView.mAccessibilityFloatingReceiver, intentFilter, 2);
                }
                accessibilityFloatingMenuView.onTargetsChanged(targets);
                accessibilityFloatingMenuView.updateOpacityWith(Settings.Secure.getFloat(context2.getContentResolver(), "accessibility_floating_menu_opacity", 0.55f), Settings.Secure.getInt(context2.getContentResolver(), "accessibility_floating_menu_fade_enabled", 1) == 1);
                accessibilityFloatingMenuView.setSizeType(Settings.Secure.getIntForUser(context2.getContentResolver(), "accessibility_floating_menu_size", 9, -2));
                accessibilityFloatingMenuView.setShapeType(Settings.Secure.getInt(context2.getContentResolver(), "accessibility_floating_menu_icon_type", 0));
                accessibilityFloatingMenuView.mOnDragEndListener = Optional.ofNullable(new AccessibilityFloatingMenu$$ExternalSyntheticLambda0(accessibilityFloatingMenu));
                context2.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_button_targets"), false, accessibilityFloatingMenu.mContentObserver, -2);
                context2.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_floating_menu_size"), false, accessibilityFloatingMenu.mSizeContentObserver, -2);
                ContentResolver contentResolver = context2.getContentResolver();
                Uri uriFor = Settings.Secure.getUriFor("accessibility_floating_menu_fade_enabled");
                AccessibilityFloatingMenu.C10123 c10123 = accessibilityFloatingMenu.mFadeOutContentObserver;
                contentResolver.registerContentObserver(uriFor, false, c10123, -2);
                context2.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_floating_menu_opacity"), false, c10123, -2);
                context2.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("enabled_accessibility_services"), false, accessibilityFloatingMenu.mEnabledA11yServicesContentObserver, -2);
                if (accessibilityFloatingMenu.mIsHideHandle) {
                    accessibilityFloatingMenuView.updateHideHandle((int) AccessibilityFloatingMenu.getPosition(context2).mPercentageY);
                    accessibilityFloatingMenuView.updateHideHandleLocationWith(AccessibilityFloatingMenu.getPosition(context2));
                    return;
                }
                return;
            }
        }
        z2 = false;
        if (z2) {
            if (!((z2 || i != 1 || TextUtils.isEmpty(str)) ? false : true)) {
            }
        }
        if (!((z2 || i != 1 || TextUtils.isEmpty(str)) ? false : true)) {
        }
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
