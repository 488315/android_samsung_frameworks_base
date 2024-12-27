package com.android.systemui.navigationbar;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.IRotationWatcher;
import android.view.IWallpaperVisibilityListener;
import android.view.IWindowManager;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.SystemActions;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler;
import com.android.systemui.navigationbar.gestural.CornerGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$Factory$$ExternalSyntheticLambda0;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

public final class NavBarHelper implements AccessibilityManager.AccessibilityServicesStateChangeListener, AccessibilityButtonModeObserver.ModeChangedListener, AccessibilityButtonTargetsObserver.TargetsChangedListener, OverviewProxyService.OverviewProxyListener, NavigationModeController.ModeChangedListener, Dumpable, CommandQueue.Callbacks, ConfigurationController.ConfigurationListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass3 dispatchRotation;
    public long mA11yButtonState;
    public final AccessibilityButtonModeObserver mAccessibilityButtonModeObserver;
    public final AccessibilityButtonTargetsObserver mAccessibilityButtonTargetsObserver;
    public final AccessibilityGestureHandler mAccessibilityGestureHandler;
    public final AccessibilityManager mAccessibilityManager;
    public final AnonymousClass1 mAssistContentObserver;
    public final Lazy mAssistManagerLazy;
    public boolean mAssistantAvailable;
    public boolean mAssistantTouchGestureEnabled;
    public final Lazy mCentralSurfacesOptionalLazy;
    public final CommandQueue mCommandQueue;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final CornerGestureHandler mCornerGestureHandler;
    public final EdgeBackGestureHandler mEdgeBackGestureHandler;
    public final EdgeBackGestureHandler.Factory mEdgeBackGestureHandlerFactory;
    public final Handler mHandler;
    public final KeyguardStateController mKeyguardStateController;
    public int mLastIMEhints;
    public boolean mLongPressHomeEnabled;
    public final Executor mMainExecutor;
    public int mNavBarMode;
    public final NavBarStore mNavBarStore;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final AnonymousClass4 mRotationWatcher;
    public int mRotationWatcherRotation;
    public final List mStateListeners;
    public final SystemActions mSystemActions;
    public boolean mTogglingNavbarTaskbar;
    public final UserTracker mUserTracker;
    public final AnonymousClass2 mWallpaperVisibilityListener;
    public boolean mWallpaperVisible;
    public int mWindowState;
    public int mWindowStateDisplayId;
    public final SparseIntArray mWindowStateDisplays;
    public final IWindowManager mWm;

    /* renamed from: com.android.systemui.navigationbar.NavBarHelper$2, reason: invalid class name */
    public final class AnonymousClass2 extends IWallpaperVisibilityListener.Stub {
        public AnonymousClass2() {
        }

        public final void onWallpaperVisibilityChanged(final boolean z, final int i) {
            NavBarHelper.this.mHandler.post(new Runnable(z, i) { // from class: com.android.systemui.navigationbar.NavBarHelper$2$$ExternalSyntheticLambda0
                public final /* synthetic */ boolean f$1;
                public final /* synthetic */ int f$2;

                @Override // java.lang.Runnable
                public final void run() {
                    NavBarHelper.AnonymousClass2 anonymousClass2 = NavBarHelper.AnonymousClass2.this;
                    boolean z2 = this.f$1;
                    NavBarHelper navBarHelper = NavBarHelper.this;
                    navBarHelper.mWallpaperVisible = z2;
                    Iterator it = ((ArrayList) navBarHelper.mStateListeners).iterator();
                    while (it.hasNext()) {
                        ((NavBarHelper.NavbarTaskbarStateUpdater) it.next()).updateWallpaperVisibility(z2);
                    }
                }
            });
        }
    }

    /* renamed from: com.android.systemui.navigationbar.NavBarHelper$4, reason: invalid class name */
    public final class AnonymousClass4 extends IRotationWatcher.Stub {
        public AnonymousClass4() {
        }

        public final void onRotationChanged(final int i) {
            if (!BasicRune.NAVBAR_AOSP_BUG_FIX) {
                NavBarHelper.this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarHelper.AnonymousClass4 anonymousClass4 = NavBarHelper.AnonymousClass4.this;
                        int i2 = i;
                        NavBarHelper navBarHelper = NavBarHelper.this;
                        navBarHelper.mRotationWatcherRotation = i2;
                        Iterator it = ((ArrayList) navBarHelper.mStateListeners).iterator();
                        while (it.hasNext()) {
                            ((NavBarHelper.NavbarTaskbarStateUpdater) it.next()).updateRotationWatcherState(i2);
                        }
                    }
                });
                return;
            }
            NavBarHelper navBarHelper = NavBarHelper.this;
            navBarHelper.mRotationWatcherRotation = i;
            if (navBarHelper.mHandler.hasCallbacks(navBarHelper.dispatchRotation)) {
                NavBarHelper navBarHelper2 = NavBarHelper.this;
                navBarHelper2.mHandler.removeCallbacks(navBarHelper2.dispatchRotation);
            }
            NavBarHelper navBarHelper3 = NavBarHelper.this;
            navBarHelper3.mHandler.postAtFrontOfQueue(navBarHelper3.dispatchRotation);
        }
    }

    public NavBarHelper(Context context, AccessibilityManager accessibilityManager, AccessibilityButtonModeObserver accessibilityButtonModeObserver, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, SystemActions systemActions, OverviewProxyService overviewProxyService, Lazy lazy, Lazy lazy2, KeyguardStateController keyguardStateController, NavigationModeController navigationModeController, EdgeBackGestureHandler.Factory factory, IWindowManager iWindowManager, UserTracker userTracker, DisplayTracker displayTracker, NotificationShadeWindowController notificationShadeWindowController, ConfigurationController configurationController, DumpManager dumpManager, CommandQueue commandQueue, Executor executor, NavBarStore navBarStore, DisplayManager displayManager, CornerGestureHandler cornerGestureHandler) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mStateListeners = new ArrayList();
        this.mWindowStateDisplays = new SparseIntArray();
        this.mAssistContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.navigationbar.NavBarHelper.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                NavBarHelper navBarHelper = NavBarHelper.this;
                int i = NavBarHelper.$r8$clinit;
                navBarHelper.updateAssistantAvailability();
            }
        };
        this.mWallpaperVisibilityListener = new AnonymousClass2();
        this.dispatchRotation = new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper.3
            @Override // java.lang.Runnable
            public final void run() {
                NavBarHelper navBarHelper = NavBarHelper.this;
                int i = navBarHelper.mRotationWatcherRotation;
                Iterator it = ((ArrayList) navBarHelper.mStateListeners).iterator();
                while (it.hasNext()) {
                    ((NavbarTaskbarStateUpdater) it.next()).updateRotationWatcherState(i);
                }
            }
        };
        this.mRotationWatcher = new AnonymousClass4();
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.wtf("NavBarHelper", "Unexpected initialization for non-primary user", new Throwable());
            Log.e("NavBarHelper", "OPS not initialized for non-primary user, just return");
            return;
        }
        this.mContext = context;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mCommandQueue = commandQueue;
        this.mContentResolver = context.getContentResolver();
        this.mAccessibilityManager = accessibilityManager;
        this.mAssistManagerLazy = lazy;
        this.mCentralSurfacesOptionalLazy = lazy2;
        this.mKeyguardStateController = keyguardStateController;
        this.mUserTracker = userTracker;
        this.mSystemActions = systemActions;
        this.mAccessibilityButtonModeObserver = accessibilityButtonModeObserver;
        this.mAccessibilityButtonTargetsObserver = accessibilityButtonTargetsObserver;
        this.mWm = iWindowManager;
        displayTracker.getClass();
        factory.getClass();
        this.mEdgeBackGestureHandler = (EdgeBackGestureHandler) factory.mUiThreadContext.runWithScissors(new EdgeBackGestureHandler$Factory$$ExternalSyntheticLambda0(factory, context));
        this.mMainExecutor = executor;
        this.mNavBarMode = navigationModeController.addListener(this);
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this);
        dumpManager.registerDumpable(this);
        if (BasicRune.NAVBAR_ENABLED) {
            this.mEdgeBackGestureHandlerFactory = factory;
            this.mNavBarStore = navBarStore;
        }
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
            this.mCornerGestureHandler = cornerGestureHandler;
            cornerGestureHandler.navBarHelper = this;
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            this.mAccessibilityGestureHandler = new AccessibilityGestureHandler(context, this, this.mNavBarStore, displayManager);
        }
    }

    public static int transitionMode(int i, boolean z) {
        if (z) {
            return 1;
        }
        if ((i & 6) == 6) {
            return 3;
        }
        if ((i & 4) != 0) {
            return 6;
        }
        if ((i & 2) != 0) {
            return 4;
        }
        if ((i & 64) != 0) {
            return 1;
        }
        return (!BasicRune.NAVBAR_ENABLED || (i & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) == 0) ? 0 : 7;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NavbarTaskbarFriendster", "  longPressHomeEnabled="), this.mLongPressHomeEnabled, printWriter, "  mAssistantTouchGestureEnabled="), this.mAssistantTouchGestureEnabled, printWriter, "  mAssistantAvailable="), this.mAssistantAvailable, printWriter, "  mNavBarMode=");
        m.append(this.mNavBarMode);
        printWriter.println(m.toString());
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("  isAssistantGestureAvailable(aiAgent)="), !BasicRune.SUPPORT_AI_AGENT, printWriter);
        }
    }

    public final EdgeBackGestureHandler getEdgeBackGestureHandler(Context context) {
        if (!BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY || context.getDisplayId() != 1) {
            return this.mEdgeBackGestureHandler;
        }
        EdgeBackGestureHandler.Factory factory = this.mEdgeBackGestureHandlerFactory;
        factory.getClass();
        return (EdgeBackGestureHandler) factory.mUiThreadContext.runWithScissors(new EdgeBackGestureHandler$Factory$$ExternalSyntheticLambda0(factory, context));
    }

    public final boolean isImeShown(int i) {
        if (BasicRune.NAVBAR_TASKBAR) {
            this.mLastIMEhints = i;
        }
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mWindowRootView;
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        boolean z2 = windowRootView != null && windowRootView.isAttachedToWindow() && windowRootView.getRootWindowInsets().isVisible(WindowInsets.Type.ime());
        if (BasicRune.NAVBAR_ENABLED) {
            z2 &= ((CentralSurfacesImpl) ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get())).mBouncerShowing;
        }
        return z2 || !(z || (i & 2) == 0);
    }

    @Override // com.android.systemui.accessibility.AccessibilityButtonModeObserver.ModeChangedListener
    public final void onAccessibilityButtonModeChanged(int i) {
        updateA11yState();
    }

    @Override // com.android.systemui.accessibility.AccessibilityButtonTargetsObserver.TargetsChangedListener
    public final void onAccessibilityButtonTargetsChanged(String str) {
        updateA11yState();
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener
    public final void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
        updateA11yState();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        this.mEdgeBackGestureHandler.onConfigurationChanged(configuration);
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onConnectionChanged(boolean z) {
        if (z) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NavBarHelper.this.updateAssistantAvailability();
                }
            });
            if (BasicRune.NAVBAR_TASKBAR) {
                this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(), 0);
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
        updateAssistantAvailability();
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE && (!BasicRune.SUPPORT_AI_AGENT)) {
            this.mCornerGestureHandler.updateIsEnabled();
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            this.mAccessibilityGestureHandler.updateIsEnabled();
        }
    }

    public final void registerNavTaskStateUpdater(NavbarTaskbarStateUpdater navbarTaskbarStateUpdater) {
        ((ArrayList) this.mStateListeners).add(navbarTaskbarStateUpdater);
        if (this.mTogglingNavbarTaskbar || ((ArrayList) this.mStateListeners).size() != 1) {
            navbarTaskbarStateUpdater.updateAccessibilityServicesState();
            navbarTaskbarStateUpdater.updateAssistantAvailable(this.mAssistantAvailable, this.mLongPressHomeEnabled);
        } else {
            this.mAccessibilityManager.addAccessibilityServicesStateChangeListener(this);
            this.mAccessibilityButtonModeObserver.addListener(this);
            this.mAccessibilityButtonTargetsObserver.addListener(this);
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_ASSISTANT), false, this.mAssistContentObserver, -1);
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_long_press_home_enabled"), false, this.mAssistContentObserver, -1);
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("search_all_entrypoints_enabled"), false, this.mAssistContentObserver, -1);
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_touch_gesture_enabled"), false, this.mAssistContentObserver, -1);
            try {
                this.mWm.watchRotation(this.mRotationWatcher, 0);
            } catch (Exception e) {
                Log.w("NavBarHelper", "Failed to register rotation watcher", e);
            }
            try {
                this.mWallpaperVisible = this.mWm.registerWallpaperVisibilityListener(this.mWallpaperVisibilityListener, 0);
            } catch (Exception e2) {
                Log.w("NavBarHelper", "Failed to register wallpaper visibility listener", e2);
            }
            this.mEdgeBackGestureHandler.onNavBarAttached();
            if (BasicRune.NAVBAR_SUPPORT_SEARCLE && (!BasicRune.SUPPORT_AI_AGENT)) {
                CornerGestureHandler cornerGestureHandler = this.mCornerGestureHandler;
                cornerGestureHandler.isAttached = true;
                cornerGestureHandler.updateIsEnabled();
            }
            if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
                AccessibilityGestureHandler accessibilityGestureHandler = this.mAccessibilityGestureHandler;
                accessibilityGestureHandler.isAttached = true;
                Log.d(accessibilityGestureHandler.tag, "onNavBarAttached");
                accessibilityGestureHandler.updateIsEnabled();
            }
            updateAssistantAvailability();
            updateA11yState();
            this.mCommandQueue.recomputeDisableFlags(this.mContext.getDisplayId(), false);
        }
        navbarTaskbarStateUpdater.updateWallpaperVisibility(this.mWallpaperVisible);
        navbarTaskbarStateUpdater.updateRotationWatcherState(this.mRotationWatcherRotation);
    }

    public final void removeNavTaskStateUpdater(NavbarTaskbarStateUpdater navbarTaskbarStateUpdater) {
        ((ArrayList) this.mStateListeners).remove(navbarTaskbarStateUpdater);
        if (this.mTogglingNavbarTaskbar || !((ArrayList) this.mStateListeners).isEmpty()) {
            return;
        }
        this.mAccessibilityManager.removeAccessibilityServicesStateChangeListener(this);
        this.mAccessibilityButtonModeObserver.removeListener(this);
        this.mAccessibilityButtonTargetsObserver.removeListener(this);
        this.mContentResolver.unregisterContentObserver(this.mAssistContentObserver);
        try {
            this.mWm.removeRotationWatcher(this.mRotationWatcher);
        } catch (Exception e) {
            Log.w("NavBarHelper", "Failed to unregister rotation watcher", e);
        }
        try {
            this.mWm.unregisterWallpaperVisibilityListener(this.mWallpaperVisibilityListener, 0);
        } catch (Exception e2) {
            Log.w("NavBarHelper", "Failed to register wallpaper visibility listener", e2);
        }
        this.mEdgeBackGestureHandler.onNavBarDetached();
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE && (!BasicRune.SUPPORT_AI_AGENT)) {
            CornerGestureHandler cornerGestureHandler = this.mCornerGestureHandler;
            cornerGestureHandler.isAttached = false;
            cornerGestureHandler.updateIsEnabled();
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            AccessibilityGestureHandler accessibilityGestureHandler = this.mAccessibilityGestureHandler;
            accessibilityGestureHandler.isAttached = false;
            Log.d(accessibilityGestureHandler.tag, "onNavBarDetached");
            accessibilityGestureHandler.disposeInputChannel();
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void setAssistantOverridesRequested(int[] iArr) {
        ((AssistManager) this.mAssistManagerLazy.get()).mAssistOverrideInvocationTypes = iArr;
        updateAssistantAvailability();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setWindowState(int i, int i2, int i3) {
        if (i2 != 2) {
            return;
        }
        this.mWindowStateDisplayId = i;
        this.mWindowState = i3;
        if (BasicRune.NAVBAR_ENABLED) {
            this.mWindowStateDisplays.put(i, i3);
            if (i3 == 0) {
                this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarWindowStateShowing(), i);
            } else {
                this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarWindowStateHidden(), i);
            }
        }
        ((CentralSurfacesImpl) ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get())).updateBubblesVisibility();
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            this.mAccessibilityGestureHandler.updateIsEnabled();
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void startAssistant(Bundle bundle) {
        ((AssistManager) this.mAssistManagerLazy.get()).startAssist(bundle);
    }

    public final void updateA11yState() {
        int i;
        boolean z;
        long j = this.mA11yButtonState;
        try {
            i = Integer.parseInt(this.mAccessibilityButtonModeObserver.getSettingsValue());
        } catch (NumberFormatException e) {
            Log.e("A11yButtonModeObserver", "Invalid string for  " + e);
            i = 0;
        }
        if (i == 1) {
            this.mA11yButtonState = 0L;
            z = false;
        } else {
            int size = this.mAccessibilityManager.getAccessibilityShortcutTargets(1).size();
            boolean z2 = size >= 1;
            r3 = size >= (BasicRune.NAVBAR_ACCESSIBILITY ? 1 : 2);
            this.mA11yButtonState = (r3 ? 32L : 0L) | (z2 ? 16L : 0L);
            if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
                this.mAccessibilityGestureHandler.updateIsEnabled();
            }
            z = r3;
            r3 = z2;
        }
        if (j != this.mA11yButtonState) {
            updateSystemAction(11, r3);
            updateSystemAction(12, z);
        }
        Iterator it = ((ArrayList) this.mStateListeners).iterator();
        while (it.hasNext()) {
            ((NavbarTaskbarStateUpdater) it.next()).updateAccessibilityServicesState();
        }
    }

    public final void updateAssistantAvailability() {
        if (!BasicRune.NAVBAR_AOSP_BUG_FIX || this.mContext == null || this.mContentResolver == null) {
            return;
        }
        boolean z = false;
        byte b = ((AssistManager) this.mAssistManagerLazy.get()).mAssistUtils.getAssistComponentForUser(((UserTrackerImpl) this.mUserTracker).getUserId()) != null;
        boolean shouldOverrideAssist = ((AssistManager) this.mAssistManagerLazy.get()).shouldOverrideAssist(5);
        this.mLongPressHomeEnabled = Settings.Secure.getIntForUser(this.mContentResolver, shouldOverrideAssist ? "search_all_entrypoints_enabled" : "assist_long_press_home_enabled", this.mContext.getResources().getBoolean(shouldOverrideAssist ? R.bool.config_showPercentageTextDuringRebootToUpdate : R.bool.config_audio_ringer_mode_affects_alarm_stream) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
        boolean z2 = Settings.Secure.getIntForUser(this.mContentResolver, "assist_touch_gesture_enabled", this.mContext.getResources().getBoolean(R.bool.config_autoBrightnessResetAmbientLuxAfterWarmUp) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
        this.mAssistantTouchGestureEnabled = z2;
        if (b != false && z2 && com.android.systemui.shared.system.QuickStepContract.isGesturalMode(this.mNavBarMode)) {
            z = true;
        }
        this.mAssistantAvailable = z;
        boolean z3 = this.mLongPressHomeEnabled;
        Iterator it = this.mStateListeners.iterator();
        while (it.hasNext()) {
            ((NavbarTaskbarStateUpdater) it.next()).updateAssistantAvailable(z, z3);
        }
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE && (!BasicRune.SUPPORT_AI_AGENT)) {
            this.mCornerGestureHandler.updateIsEnabled();
        }
    }

    public final void updateSystemAction(int i, boolean z) {
        int i2;
        String str;
        if (!z) {
            this.mSystemActions.mA11yManager.unregisterSystemAction(i);
            return;
        }
        SystemActions systemActions = this.mSystemActions;
        systemActions.getClass();
        switch (i) {
            case 1:
                i2 = R.string.autofill_save_title;
                str = "SYSTEM_ACTION_BACK";
                break;
            case 2:
                i2 = R.string.autofill_save_type_password;
                str = "SYSTEM_ACTION_HOME";
                break;
            case 3:
                i2 = R.string.autofill_update_title_with_type;
                str = "SYSTEM_ACTION_RECENTS";
                break;
            case 4:
                i2 = R.string.autofill_save_type_username;
                str = "SYSTEM_ACTION_NOTIFICATIONS";
                break;
            case 5:
                i2 = R.string.autofill_update_title_with_3types;
                str = "SYSTEM_ACTION_QUICK_SETTINGS";
                break;
            case 6:
                i2 = R.string.autofill_update_title_with_2types;
                str = "SYSTEM_ACTION_POWER_DIALOG";
                break;
            case 7:
            case 14:
            default:
                return;
            case 8:
                i2 = R.string.autofill_save_type_payment_card;
                str = "SYSTEM_ACTION_LOCK_SCREEN";
                break;
            case 9:
                i2 = R.string.autofill_update_yes;
                str = "SYSTEM_ACTION_TAKE_SCREENSHOT";
                break;
            case 10:
                i2 = R.string.autofill_save_type_generic_card;
                str = "SYSTEM_ACTION_HEADSET_HOOK";
                break;
            case 11:
                i2 = R.string.autofill_update_title;
                str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON";
                break;
            case 12:
                i2 = R.string.autofill_save_yes;
                str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU";
                break;
            case 13:
                i2 = R.string.autofill_save_type_email_address;
                str = "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT";
                break;
            case 15:
                i2 = R.string.autofill_save_title_with_2types;
                str = "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE";
                break;
            case 16:
                i2 = R.string.autofill_save_type_debit_card;
                str = "SYSTEM_ACTION_DPAD_UP";
                break;
            case 17:
                i2 = R.string.autofill_save_title_with_type;
                str = "SYSTEM_ACTION_DPAD_DOWN";
                break;
            case 18:
                i2 = R.string.autofill_save_type_address;
                str = "SYSTEM_ACTION_DPAD_LEFT";
                break;
            case 19:
                i2 = R.string.autofill_save_type_credit_card;
                str = "SYSTEM_ACTION_DPAD_RIGHT";
                break;
            case 20:
                i2 = R.string.autofill_save_title_with_3types;
                str = "SYSTEM_ACTION_DPAD_CENTER";
                break;
        }
        systemActions.mA11yManager.registerSystemAction(systemActions.createRemoteAction(i2, str), i);
    }

    public final class CurrentSysuiState {
        public final int mWindowState;
        public final int mWindowStateDisplayId;

        public CurrentSysuiState(NavBarHelper navBarHelper) {
            this.mWindowStateDisplayId = navBarHelper.mWindowStateDisplayId;
            this.mWindowState = navBarHelper.mWindowState;
        }

        public CurrentSysuiState(NavBarHelper navBarHelper, int i) {
            this.mWindowStateDisplayId = i;
            this.mWindowState = navBarHelper.mWindowStateDisplays.get(i);
        }
    }

    public interface NavbarTaskbarStateUpdater {
        void updateAccessibilityServicesState();

        void updateAssistantAvailable(boolean z, boolean z2);

        default void updateAccessibilityGestureDetected(boolean z) {
        }

        default void updateRotationWatcherState(int i) {
        }

        default void updateWallpaperVisibility(boolean z) {
        }
    }
}
