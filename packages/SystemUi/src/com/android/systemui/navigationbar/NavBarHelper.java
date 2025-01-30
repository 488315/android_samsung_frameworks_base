package com.android.systemui.navigationbar;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.IRotationWatcher;
import android.view.IWallpaperVisibilityListener;
import android.view.IWindowManager;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.SystemActions;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.SearcleGestureHandler;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda13;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarHelper implements AccessibilityManager.AccessibilityServicesStateChangeListener, AccessibilityButtonModeObserver.ModeChangedListener, AccessibilityButtonTargetsObserver.TargetsChangedListener, OverviewProxyService.OverviewProxyListener, NavigationModeController.ModeChangedListener, Dumpable, CommandQueue.Callbacks {
    public static final /* synthetic */ int $r8$clinit = 0;
    public long mA11yButtonState;
    public final AccessibilityButtonModeObserver mAccessibilityButtonModeObserver;
    public final AccessibilityButtonTargetsObserver mAccessibilityButtonTargetsObserver;
    public final AccessibilityGestureHandler mAccessibilityGestureHandler;
    public final AccessibilityManager mAccessibilityManager;
    public final C18411 mAssistContentObserver;
    public final Lazy mAssistManagerLazy;
    public boolean mAssistantAvailable;
    public boolean mAssistantTouchGestureEnabled;
    public final Lazy mCentralSurfacesOptionalLazy;
    public final CommandQueue mCommandQueue;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final EdgeBackGestureHandler mEdgeBackGestureHandler;
    public final EdgeBackGestureHandler.Factory mEdgeBackGestureHandlerFactory;
    public final Handler mHandler;
    public final KeyguardStateController mKeyguardStateController;
    public int mLastIMEhints;
    public boolean mLongPressHomeEnabled;
    public int mNavBarMode;
    public final NavBarStore mNavBarStore;
    public final C18433 mRotationWatcher;
    public int mRotationWatcherRotation;
    public final SearcleGestureHandler mSearcleGestureHandler;
    public final List mStateListeners;
    public final SystemActions mSystemActions;
    public boolean mTogglingNavbarTaskbar;
    public final UserTracker mUserTracker;
    public final C18422 mWallpaperVisibilityListener;
    public boolean mWallpaperVisible;
    public int mWindowState;
    public int mWindowStateDisplayId;
    public final SparseIntArray mWindowStateDisplays;
    public final IWindowManager mWm;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.navigationbar.NavBarHelper$2 */
    public final class C18422 extends IWallpaperVisibilityListener.Stub {
        public C18422() {
        }

        public final void onWallpaperVisibilityChanged(final boolean z, final int i) {
            NavBarHelper.this.mHandler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NavBarHelper.C18422 c18422 = NavBarHelper.C18422.this;
                    boolean z2 = z;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.navigationbar.NavBarHelper$3 */
    public final class C18433 extends IRotationWatcher.Stub {
        public C18433() {
        }

        public final void onRotationChanged(final int i) {
            NavBarHelper.this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NavBarHelper.C18433 c18433 = NavBarHelper.C18433.this;
                    int i2 = i;
                    NavBarHelper navBarHelper = NavBarHelper.this;
                    navBarHelper.mRotationWatcherRotation = i2;
                    Iterator it = ((ArrayList) navBarHelper.mStateListeners).iterator();
                    while (it.hasNext()) {
                        ((NavBarHelper.NavbarTaskbarStateUpdater) it.next()).updateRotationWatcherState(i2);
                    }
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.navigationbar.NavBarHelper$1] */
    public NavBarHelper(Context context, AccessibilityManager accessibilityManager, AccessibilityButtonModeObserver accessibilityButtonModeObserver, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, SystemActions systemActions, OverviewProxyService overviewProxyService, Lazy lazy, Lazy lazy2, KeyguardStateController keyguardStateController, NavigationModeController navigationModeController, EdgeBackGestureHandler.Factory factory, IWindowManager iWindowManager, UserTracker userTracker, DisplayTracker displayTracker, DumpManager dumpManager, CommandQueue commandQueue, NavBarStore navBarStore, BroadcastDispatcher broadcastDispatcher, DisplayManager displayManager) {
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
        this.mWallpaperVisibilityListener = new C18422();
        this.mRotationWatcher = new C18433();
        this.mContext = context;
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
        this.mEdgeBackGestureHandler = factory.create(context);
        this.mNavBarMode = navigationModeController.addListener(this);
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this);
        dumpManager.registerDumpable(this);
        if (BasicRune.NAVBAR_ENABLED) {
            this.mEdgeBackGestureHandlerFactory = factory;
            this.mNavBarStore = navBarStore;
            this.mSearcleGestureHandler = new SearcleGestureHandler(context, this, navBarStore, (OverviewProxyService) Dependency.get(OverviewProxyService.class), broadcastDispatcher, (AssistManager) lazy.get());
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
        if ((i & 256) != 0) {
            return 8;
        }
        if ((i & 2) != 0) {
            return 4;
        }
        if (!BasicRune.NAVBAR_ENABLED || (i & 128) == 0) {
            return (i & 64) != 0 ? 1 : 0;
        }
        return 7;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "NavbarTaskbarFriendster", "  longPressHomeEnabled="), this.mLongPressHomeEnabled, printWriter, "  mAssistantTouchGestureEnabled="), this.mAssistantTouchGestureEnabled, printWriter, "  mAssistantAvailable="), this.mAssistantAvailable, printWriter, "  mNavBarMode=");
        m64m.append(this.mNavBarMode);
        printWriter.println(m64m.toString());
    }

    public final boolean isImeShown(int i) {
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
            this.mLastIMEhints = i;
        }
        NotificationShadeWindowView notificationShadeWindowView = ((Optional) this.mCentralSurfacesOptionalLazy.get()).isPresent() ? ((CentralSurfacesImpl) ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get())).mNotificationShadeWindowView : null;
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        boolean z2 = notificationShadeWindowView != null && notificationShadeWindowView.isAttachedToWindow() && notificationShadeWindowView.getRootWindowInsets().isVisible(WindowInsets.Type.ime());
        if (BasicRune.NAVBAR_ENABLED) {
            z2 &= ((CentralSurfacesImpl) ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get())).mBouncerShowing;
        }
        if (z2) {
            return true;
        }
        return (z || (i & 2) == 0) ? false : true;
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

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onConnectionChanged(boolean z) {
        if (z) {
            updateAssistantAvailability();
            if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
                ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(), 0);
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
        updateAssistantAvailability();
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
            this.mSearcleGestureHandler.updateIsEnabled();
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
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assistant"), false, this.mAssistContentObserver, -1);
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_long_press_home_enabled"), false, this.mAssistContentObserver, -1);
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
            if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
                SearcleGestureHandler searcleGestureHandler = this.mSearcleGestureHandler;
                searcleGestureHandler.isAttached = true;
                searcleGestureHandler.updateIsEnabled();
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
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
            SearcleGestureHandler searcleGestureHandler = this.mSearcleGestureHandler;
            searcleGestureHandler.isAttached = false;
            searcleGestureHandler.updateIsEnabled();
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            AccessibilityGestureHandler accessibilityGestureHandler = this.mAccessibilityGestureHandler;
            accessibilityGestureHandler.isAttached = false;
            Log.d(accessibilityGestureHandler.tag, "onNavBarDetached");
            accessibilityGestureHandler.disposeInputChannel();
        }
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
                ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarWindowStateShowing(), i);
            } else {
                ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarWindowStateHidden(), i);
            }
        }
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get());
        centralSurfacesImpl.getClass();
        centralSurfacesImpl.mBubblesOptional.ifPresent(new CentralSurfacesImpl$$ExternalSyntheticLambda13(centralSurfacesImpl, 0));
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
            int size = this.mAccessibilityManager.getAccessibilityShortcutTargets(0).size();
            boolean z2 = size >= 1;
            r3 = size >= (BasicRune.NAVBAR_ENABLED ? 1 : 2);
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

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateAssistantAvailability() {
        if (!BasicRune.NAVBAR_ENABLED || this.mContext == null || this.mContentResolver == null) {
            return;
        }
        Object[] objArr = ((AssistManager) this.mAssistManagerLazy.get()).mAssistUtils.getAssistComponentForUser(((UserTrackerImpl) this.mUserTracker).getUserId()) != null;
        this.mLongPressHomeEnabled = Settings.Secure.getIntForUser(this.mContentResolver, "assist_long_press_home_enabled", this.mContext.getResources().getBoolean(R.bool.config_appCompatUserAppAspectRatioFullscreenIsEnabled) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
        boolean z = Settings.Secure.getIntForUser(this.mContentResolver, "assist_touch_gesture_enabled", this.mContext.getResources().getBoolean(R.bool.config_appCompatUserAppAspectRatioSettingsIsEnabled) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
        this.mAssistantTouchGestureEnabled = z;
        boolean z2 = objArr == true && z && QuickStepContract.isGesturalMode(this.mNavBarMode);
        this.mAssistantAvailable = z2;
        boolean z3 = this.mLongPressHomeEnabled;
        Iterator it = ((ArrayList) this.mStateListeners).iterator();
        while (it.hasNext()) {
            ((NavbarTaskbarStateUpdater) it.next()).updateAssistantAvailable(z2, z3);
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
                i2 = R.string.auto_data_switch_title;
                str = "SYSTEM_ACTION_BACK";
                break;
            case 2:
                i2 = R.string.autofill_save_notnow;
                str = "SYSTEM_ACTION_HOME";
                break;
            case 3:
                i2 = R.string.autofill_save_type_debit_card;
                str = "SYSTEM_ACTION_RECENTS";
                break;
            case 4:
                i2 = R.string.autofill_save_title_with_2types;
                str = "SYSTEM_ACTION_NOTIFICATIONS";
                break;
            case 5:
                i2 = R.string.autofill_save_type_credit_card;
                str = "SYSTEM_ACTION_QUICK_SETTINGS";
                break;
            case 6:
                i2 = R.string.autofill_save_type_address;
                str = "SYSTEM_ACTION_POWER_DIALOG";
                break;
            case 7:
            case 14:
            default:
                return;
            case 8:
                i2 = R.string.autofill_save_title;
                str = "SYSTEM_ACTION_LOCK_SCREEN";
                break;
            case 9:
                i2 = R.string.autofill_save_type_email_address;
                str = "SYSTEM_ACTION_TAKE_SCREENSHOT";
                break;
            case 10:
                i2 = R.string.autofill_save_no;
                str = "SYSTEM_ACTION_HEADSET_HOOK";
                break;
            case 11:
                i2 = R.string.autofill_save_title_with_type;
                str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON";
                break;
            case 12:
                i2 = R.string.autofill_save_title_with_3types;
                str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU";
                break;
            case 13:
                i2 = R.string.autofill_save_never;
                str = "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT";
                break;
            case 15:
                i2 = R.string.autofill_continue_yes;
                str = "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE";
                break;
            case 16:
                i2 = R.string.autofill_save_accessibility_title;
                str = "SYSTEM_ACTION_DPAD_UP";
                break;
            case 17:
                i2 = R.string.autofill_picker_accessibility_title;
                str = "SYSTEM_ACTION_DPAD_DOWN";
                break;
            case 18:
                i2 = R.string.autofill_picker_no_suggestions;
                str = "SYSTEM_ACTION_DPAD_LEFT";
                break;
            case 19:
                i2 = R.string.autofill_picker_some_suggestions;
                str = "SYSTEM_ACTION_DPAD_RIGHT";
                break;
            case 20:
                i2 = R.string.autofill_error_cannot_autofill;
                str = "SYSTEM_ACTION_DPAD_CENTER";
                break;
        }
        systemActions.mA11yManager.registerSystemAction(systemActions.createRemoteAction(i2, str), i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
