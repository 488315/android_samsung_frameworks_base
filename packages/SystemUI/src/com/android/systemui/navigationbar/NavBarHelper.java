package com.android.systemui.navigationbar;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.IRotationWatcher;
import android.view.IWallpaperVisibilityListener;
import android.view.IWindowManager;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.AccessibilityGestureTargetsObserver;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.accessibility.SystemActions;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.AccessibilityGestureHandler;
import com.android.systemui.navigationbar.gestural.CornerGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shared.recents.ILauncherProxy;
import com.android.systemui.shared.rotation.RotationPolicyUtil;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class NavBarHelper implements AccessibilityManager.AccessibilityServicesStateChangeListener, AccessibilityButtonModeObserver.ModeChangedListener, AccessibilityButtonTargetsObserver.TargetsChangedListener, AccessibilityGestureTargetsObserver.TargetsChangedListener, LauncherProxyService.LauncherProxyListener, NavigationModeController.ModeChangedListener, Dumpable, CommandQueue.Callbacks, ConfigurationController.ConfigurationListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass3 dispatchRotation;
    public long mA11yButtonState;
    public final AccessibilityButtonModeObserver mAccessibilityButtonModeObserver;
    public final AccessibilityButtonTargetsObserver mAccessibilityButtonTargetsObserver;
    public final AccessibilityGestureHandler mAccessibilityGestureHandler;
    public final AccessibilityGestureTargetsObserver mAccessibilityGestureTargetsObserver;
    public final AccessibilityManager mAccessibilityManager;
    public final AnonymousClass1 mAssistContentObserver;
    public final Lazy mAssistManagerLazy;
    public boolean mAssistantAvailable;
    public boolean mAssistantTouchGestureEnabled;
    public final Handler mBgHandler;
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
    public final LauncherProxyService mLauncherProxyService;
    public boolean mLongPressHomeEnabled;
    public final Executor mMainExecutor;
    public int mNavBarMode;
    public final NavBarStore mNavBarStore;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final AnonymousClass4 mRotationWatcher;
    public int mRotationWatcherRotation;
    private SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsObserver;
    public final List mStateListeners;
    public final SystemActions mSystemActions;
    public boolean mTogglingNavbarTaskbar;
    public UserTracker mUserTracker;
    public final AnonymousClass2 mWallpaperVisibilityListener;
    public boolean mWallpaperVisible;
    public final WindowManager mWindowManager;
    public int mWindowState;
    public int mWindowStateDisplayId;
    public final SparseIntArray mWindowStateDisplays;
    public final IWindowManager mWm;

    /* renamed from: com.android.systemui.navigationbar.NavBarHelper$2, reason: invalid class name */
    public class AnonymousClass2 extends IWallpaperVisibilityListener.Stub {
        public AnonymousClass2() {
        }

        public final void onWallpaperVisibilityChanged(final boolean z, final int i) {
            NavBarHelper.this.mHandler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NavBarHelper.AnonymousClass2 anonymousClass2 = this.f$0;
                    boolean z2 = z;
                    int i2 = i;
                    NavBarHelper navBarHelper = NavBarHelper.this;
                    navBarHelper.mWallpaperVisible = z2;
                    ArrayList arrayList = (ArrayList) navBarHelper.mStateListeners;
                    int size = arrayList.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        ((NavBarHelper.NavbarTaskbarStateUpdater) obj).updateWallpaperVisibility(i2, z2);
                    }
                }
            });
        }
    }

    /* renamed from: com.android.systemui.navigationbar.NavBarHelper$4, reason: invalid class name */
    public class AnonymousClass4 extends IRotationWatcher.Stub {
        public AnonymousClass4() {
        }

        public final void onRotationChanged(final int i) {
            final Boolean boolIsRotationLocked = RotationPolicyUtil.isRotationLocked(NavBarHelper.this.mContext);
            if (!BasicRune.NAVBAR_AOSP_BUG_FIX) {
                NavBarHelper.this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarHelper.AnonymousClass4 anonymousClass4 = this.f$0;
                        int i2 = i;
                        Boolean bool = boolIsRotationLocked;
                        NavBarHelper navBarHelper = NavBarHelper.this;
                        navBarHelper.mRotationWatcherRotation = i2;
                        ArrayList arrayList = (ArrayList) navBarHelper.mStateListeners;
                        int size = arrayList.size();
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj = arrayList.get(i3);
                            i3++;
                            ((NavBarHelper.NavbarTaskbarStateUpdater) obj).updateRotationWatcherState(i2, bool);
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

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.navigationbar.NavBarHelper$3] */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.navigationbar.NavBarHelper$1] */
    public NavBarHelper(Context context, AccessibilityManager accessibilityManager, AccessibilityButtonModeObserver accessibilityButtonModeObserver, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, AccessibilityGestureTargetsObserver accessibilityGestureTargetsObserver, SystemActions systemActions, LauncherProxyService launcherProxyService, Lazy lazy, Lazy lazy2, KeyguardStateController keyguardStateController, NavigationModeController navigationModeController, EdgeBackGestureHandler.Factory factory, IWindowManager iWindowManager, UserTracker userTracker, DisplayTracker displayTracker, NotificationShadeWindowController notificationShadeWindowController, ConfigurationController configurationController, DumpManager dumpManager, CommandQueue commandQueue, Executor executor, Handler handler, NavBarStore navBarStore, SettingsHelper settingsHelper, WindowManager windowManager, DisplayManager displayManager, CornerGestureHandler cornerGestureHandler) {
        Handler handler2 = new Handler(Looper.getMainLooper());
        this.mHandler = handler2;
        this.mStateListeners = new ArrayList();
        this.mWindowStateDisplays = new SparseIntArray();
        this.mAssistContentObserver = new ContentObserver(handler2) { // from class: com.android.systemui.navigationbar.NavBarHelper.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) throws Resources.NotFoundException {
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
                Boolean boolIsRotationLocked = RotationPolicyUtil.isRotationLocked(navBarHelper.mContext);
                ArrayList arrayList = (ArrayList) navBarHelper.mStateListeners;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((NavbarTaskbarStateUpdater) obj).updateRotationWatcherState(i, boolIsRotationLocked);
                }
            }
        };
        this.mRotationWatcher = new AnonymousClass4();
        this.mSettingsObserver = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.navigationbar.NavBarHelper$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) throws Resources.NotFoundException {
                int i = NavBarHelper.$r8$clinit;
                NavBarHelper navBarHelper = this.f$0;
                if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
                    navBarHelper.updateAssistantAvailability();
                }
            }
        };
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
        this.mAccessibilityGestureTargetsObserver = accessibilityGestureTargetsObserver;
        this.mWm = iWindowManager;
        displayTracker.getClass();
        this.mEdgeBackGestureHandler = factory.create(context, windowManager);
        this.mMainExecutor = executor;
        this.mBgHandler = handler;
        this.mNavBarMode = navigationModeController.addListener(this);
        commandQueue.addCallback((CommandQueue.Callbacks) this);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        launcherProxyService.addCallback((LauncherProxyService.LauncherProxyListener) this);
        dumpManager.registerDumpable(this);
        if (BasicRune.NAVBAR_ENABLED) {
            this.mEdgeBackGestureHandlerFactory = factory;
            this.mNavBarStore = navBarStore;
            this.mSettingsHelper = settingsHelper;
            this.mWindowManager = windowManager;
        }
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
            this.mCornerGestureHandler = cornerGestureHandler;
            cornerGestureHandler.navBarHelper = this;
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            this.mAccessibilityGestureHandler = new AccessibilityGestureHandler(context, this, this.mNavBarStore, displayManager);
        }
        this.mLauncherProxyService = launcherProxyService;
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
        return (!BasicRune.NAVBAR_ENABLED || (i & 1048576) == 0) ? 0 : 7;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NavbarTaskbarFriendster", "  longPressHomeEnabled="), this.mLongPressHomeEnabled, printWriter, "  mAssistantTouchGestureEnabled="), this.mAssistantTouchGestureEnabled, printWriter, "  mAssistantAvailable="), this.mAssistantAvailable, printWriter, "  mNavBarMode="), this.mNavBarMode, printWriter);
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  AssistantBySideButton="), BasicRune.SUPPORT_AI_AGENT, printWriter, "  AssistantByNavStar="), (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getNavigationBarSPluginFlags() & 8) != 0, printWriter);
        }
    }

    public final boolean isImeVisible(int i) {
        if (BasicRune.NAVBAR_TASKBAR) {
            this.mLastIMEhints = i;
        }
        WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mWindowRootView;
        boolean z = BasicRune.NAVBAR_ENABLED;
        boolean zIsVisible = z ? this.mKeyguardStateController.isVisible() : ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        boolean z2 = windowRootView != null && windowRootView.isAttachedToWindow() && windowRootView.getRootWindowInsets().isVisible(WindowInsets.Type.ime());
        if (z) {
            z2 &= ((CentralSurfacesImpl) ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get())).mBouncerShowing;
        }
        return z2 || !(zIsVisible || (i & 2) == 0);
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

    @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
    public final void onConnectionChanged(boolean z) {
        ILauncherProxy iLauncherProxy;
        if (z) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    NavBarHelper navBarHelper = this.f$0;
                    int i = NavBarHelper.$r8$clinit;
                    navBarHelper.updateAssistantAvailability();
                }
            });
            if (BasicRune.NAVBAR_TASKBAR) {
                this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable(), 0);
                if (SafeUIState.isSysUiSafeModeEnabled()) {
                    boolean zIsTaskBarEnabled = ((NavBarStateManagerImpl) this.mNavBarStore.getNavStateManager()).isTaskBarEnabled(true);
                    Log.w("NavBarHelper", "onConnectionChanged in SafeMode isTaskbarEnabled=" + zIsTaskBarEnabled);
                    try {
                        LauncherProxyService launcherProxyService = this.mLauncherProxyService;
                        if (launcherProxyService == null || (iLauncherProxy = launcherProxyService.mLauncherProxy) == null) {
                            return;
                        }
                        ((ILauncherProxy.Stub.Proxy) iLauncherProxy).isTaskbarEnabled(zIsTaskBarEnabled);
                    } catch (RemoteException e) {
                        Log.e("NavBarHelper", "Failed to call isTaskbarEnabled()", e);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) throws Resources.NotFoundException {
        this.mNavBarMode = i;
        updateAssistantAvailability();
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
            this.mCornerGestureHandler.updateIsEnabled();
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            this.mAccessibilityGestureHandler.updateIsEnabled();
        }
    }

    public final void registerNavTaskStateUpdater(final NavbarTaskbarStateUpdater navbarTaskbarStateUpdater) {
        ((ArrayList) this.mStateListeners).add(navbarTaskbarStateUpdater);
        if (this.mTogglingNavbarTaskbar || ((ArrayList) this.mStateListeners).size() != 1) {
            navbarTaskbarStateUpdater.updateAccessibilityServicesState();
            navbarTaskbarStateUpdater.updateAssistantAvailable(this.mAssistantAvailable, this.mLongPressHomeEnabled);
        } else {
            this.mAccessibilityManager.addAccessibilityServicesStateChangeListener(this);
            this.mAccessibilityButtonModeObserver.addListener(this);
            this.mAccessibilityButtonTargetsObserver.addListener(this);
            this.mAccessibilityGestureTargetsObserver.addListener(this);
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_ASSISTANT), false, this.mAssistContentObserver, -1);
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_long_press_home_enabled"), false, this.mAssistContentObserver, -1);
            this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_SEARCH_ALL_ENTRYPOINTS_ENABLED), false, this.mAssistContentObserver, -1);
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
            if (BasicRune.NAVBAR_ENABLED) {
                this.mSettingsHelper.registerCallback(this.mSettingsObserver, Settings.Global.getUriFor(SettingsHelper.INDEX_NAVIGATIONBAR_SPLUGIN_FLAGS));
            }
            if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
                CornerGestureHandler cornerGestureHandler = this.mCornerGestureHandler;
                cornerGestureHandler.isAttached = true;
                cornerGestureHandler.updateIsEnabled();
            }
            if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
                AccessibilityGestureHandler accessibilityGestureHandler = this.mAccessibilityGestureHandler;
                accessibilityGestureHandler.isAttached = true;
                Log.d("AccessibilityGestureHandler", "onNavBarAttached");
                accessibilityGestureHandler.updateIsEnabled();
            }
            updateAssistantAvailability();
            updateA11yState();
            this.mCommandQueue.recomputeDisableFlags(this.mContext.getDisplayId(), false);
        }
        navbarTaskbarStateUpdater.updateWallpaperVisibility(0, this.mWallpaperVisible);
        this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                final NavBarHelper navBarHelper = this.f$0;
                final NavBarHelper.NavbarTaskbarStateUpdater navbarTaskbarStateUpdater2 = navbarTaskbarStateUpdater;
                final Boolean boolIsRotationLocked = RotationPolicyUtil.isRotationLocked(navBarHelper.mContext);
                navBarHelper.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavBarHelper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        NavBarHelper navBarHelper2 = navBarHelper;
                        navbarTaskbarStateUpdater2.updateRotationWatcherState(navBarHelper2.mRotationWatcherRotation, boolIsRotationLocked);
                    }
                });
            }
        });
    }

    public final void removeNavTaskStateUpdater(NavbarTaskbarStateUpdater navbarTaskbarStateUpdater) {
        ((ArrayList) this.mStateListeners).remove(navbarTaskbarStateUpdater);
        if (this.mTogglingNavbarTaskbar || !((ArrayList) this.mStateListeners).isEmpty()) {
            return;
        }
        this.mAccessibilityManager.removeAccessibilityServicesStateChangeListener(this);
        this.mAccessibilityButtonModeObserver.removeListener(this);
        this.mAccessibilityButtonTargetsObserver.removeListener(this);
        this.mAccessibilityGestureTargetsObserver.removeListener(this);
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
        if (BasicRune.NAVBAR_ENABLED) {
            this.mSettingsHelper.unregisterCallback(this.mSettingsObserver);
        }
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
            CornerGestureHandler cornerGestureHandler = this.mCornerGestureHandler;
            cornerGestureHandler.isAttached = false;
            cornerGestureHandler.updateIsEnabled();
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            AccessibilityGestureHandler accessibilityGestureHandler = this.mAccessibilityGestureHandler;
            accessibilityGestureHandler.isAttached = false;
            Log.d("AccessibilityGestureHandler", "onNavBarDetached");
            accessibilityGestureHandler.disposeInputChannel();
        }
    }

    @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
    public final void setAssistantOverridesRequested(int[] iArr) throws Resources.NotFoundException {
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

    @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
    public final void startAssistant(Bundle bundle) throws PackageManager.NameNotFoundException {
        ((AssistManager) this.mAssistManagerLazy.get()).startAssist(bundle);
    }

    public void updateA11yState() {
        int i;
        int size;
        int i2;
        String stringForUser;
        long j = this.mA11yButtonState;
        int i3 = 0;
        try {
            i = Integer.parseInt(this.mAccessibilityButtonModeObserver.getSettingsValue());
        } catch (NumberFormatException e) {
            Log.e("A11yButtonModeObserver", "Invalid string for  " + e);
            i = 0;
        }
        if (BasicRune.NAVBAR_ENABLED) {
            size = (i == 1 || (stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "accessibility_button_targets", ((UserTrackerImpl) this.mUserTracker).getUserId())) == null || stringForUser.isEmpty()) ? 0 : stringForUser.split(":").length;
        } else {
            size = this.mAccessibilityManager.getAccessibilityShortcutTargets(this.mNavBarMode == 2 ? 32 : i == 0 ? 1 : 0).size();
        }
        boolean z = size >= 1;
        boolean z2 = size >= (BasicRune.NAVBAR_ACCESSIBILITY ? 1 : 2);
        this.mA11yButtonState = (z2 ? 32L : 0L) | (z ? 16L : 0L);
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            try {
                i2 = Integer.parseInt(this.mAccessibilityButtonModeObserver.getSettingsValue());
            } catch (NumberFormatException e2) {
                Log.e("A11yButtonModeObserver", "Invalid string for  " + e2);
                i2 = 0;
            }
            if (i2 == 1) {
                this.mAccessibilityGestureHandler.updateIsEnabled();
            }
        }
        if (j != this.mA11yButtonState) {
            updateSystemAction(11, z);
            updateSystemAction(12, z2);
        }
        if (BasicRune.NAVBAR_PERFORMANCE_TUNING && j == this.mA11yButtonState) {
            return;
        }
        ArrayList arrayList = (ArrayList) this.mStateListeners;
        int size2 = arrayList.size();
        while (i3 < size2) {
            Object obj = arrayList.get(i3);
            i3++;
            ((NavbarTaskbarStateUpdater) obj).updateAccessibilityServicesState();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateAssistantAvailability() throws Resources.NotFoundException {
        if (!BasicRune.NAVBAR_AOSP_BUG_FIX || this.mContext == null || this.mContentResolver == null) {
            return;
        }
        int i = 0;
        Object[] objArr = ((AssistManager) this.mAssistManagerLazy.get()).mAssistUtils.getAssistComponentForUser(((UserTrackerImpl) this.mUserTracker).getUserId()) != null;
        boolean zShouldOverrideAssist = ((AssistManager) this.mAssistManagerLazy.get()).shouldOverrideAssist(5);
        boolean z = Settings.Secure.getIntForUser(this.mContentResolver, zShouldOverrideAssist ? SettingsHelper.INDEX_SEARCH_ALL_ENTRYPOINTS_ENABLED : "assist_long_press_home_enabled", this.mContext.getResources().getBoolean(zShouldOverrideAssist ? R.bool.config_supportSystemNavigationKeys : R.bool.config_autoPowerModePrefetchLocation) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
        this.mLongPressHomeEnabled = z;
        boolean z2 = BasicRune.NAVBAR_ENABLED;
        if (z2) {
            this.mLongPressHomeEnabled = z && !(BasicRune.SUPPORT_AI_AGENT && (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getNavigationBarSPluginFlags() & 8) == 0);
        }
        this.mAssistantTouchGestureEnabled = Settings.Secure.getIntForUser(this.mContentResolver, "assist_touch_gesture_enabled", this.mContext.getResources().getBoolean(R.bool.config_autoPowerModeUseMotionSensor) ? 1 : 0, ((UserTrackerImpl) this.mUserTracker).getUserId()) != 0;
        boolean z3 = objArr == true && this.mAssistantTouchGestureEnabled && z2 && !(BasicRune.SUPPORT_AI_AGENT && (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getNavigationBarSPluginFlags() & 8) == 0) && QuickStepContract.isGesturalMode(this.mNavBarMode);
        this.mAssistantAvailable = z3;
        boolean z4 = this.mLongPressHomeEnabled;
        ArrayList arrayList = (ArrayList) this.mStateListeners;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((NavbarTaskbarStateUpdater) obj).updateAssistantAvailable(z3, z4);
        }
        if (BasicRune.NAVBAR_SUPPORT_SEARCLE) {
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
                i2 = R.string.autofill_update_title_with_type;
                str = "SYSTEM_ACTION_BACK";
                break;
            case 2:
                i2 = R.string.battery_saver_notification_channel_name;
                str = "SYSTEM_ACTION_HOME";
                break;
            case 3:
                i2 = R.string.biometric_error_device_not_secured;
                str = "SYSTEM_ACTION_RECENTS";
                break;
            case 4:
                i2 = R.string.biometric_dangling_notification_action_not_now;
                str = "SYSTEM_ACTION_NOTIFICATIONS";
                break;
            case 5:
                i2 = R.string.biometric_error_canceled;
                str = "SYSTEM_ACTION_QUICK_SETTINGS";
                break;
            case 6:
                i2 = R.string.biometric_dialog_default_title;
                str = "SYSTEM_ACTION_POWER_DIALOG";
                break;
            case 7:
            case 14:
            default:
                return;
            case 8:
                i2 = R.string.battery_saver_off_notification_title;
                str = "SYSTEM_ACTION_LOCK_SCREEN";
                break;
            case 9:
                i2 = R.string.biometric_error_generic;
                str = "SYSTEM_ACTION_TAKE_SCREENSHOT";
                break;
            case 10:
                i2 = R.string.battery_saver_description_with_learn_more;
                str = "SYSTEM_ACTION_HEADSET_HOOK";
                break;
            case 11:
                i2 = R.string.biometric_dialog_default_subtitle;
                str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON";
                break;
            case 12:
                i2 = R.string.biometric_dangling_notification_action_set_up;
                str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU";
                break;
            case 13:
                i2 = R.string.battery_saver_description;
                str = "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT";
                break;
            case 15:
                i2 = R.string.autofill_update_yes;
                str = "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE";
                break;
            case 16:
                i2 = R.string.battery_saver_charged_notification_summary;
                str = "SYSTEM_ACTION_DPAD_UP";
                break;
            case 17:
                i2 = R.string.back_button_label;
                str = "SYSTEM_ACTION_DPAD_DOWN";
                break;
            case 18:
                i2 = R.string.badPin;
                str = "SYSTEM_ACTION_DPAD_LEFT";
                break;
            case 19:
                i2 = R.string.badPuk;
                str = "SYSTEM_ACTION_DPAD_RIGHT";
                break;
            case 20:
                i2 = R.string.autofill_window_title;
                str = "SYSTEM_ACTION_DPAD_CENTER";
                break;
            case 21:
                i2 = R.string.biometric_app_setting_name;
                str = "SYSTEM_ACTION_MENU";
                break;
            case 22:
                i2 = R.string.beforeOneMonthDurationPast;
                str = "SYSTEM_ACTION_MEDIA_PLAY_PAUSE";
                break;
        }
        systemActions.mA11yManager.registerSystemAction(systemActions.createRemoteAction(i2, str), i);
    }

    public class CurrentSysuiState {
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

        void updateWallpaperVisibility(int i, boolean z);

        default void updateAccessibilityGestureDetected(boolean z) {
        }

        default void updateRotationWatcherState(int i, Boolean bool) {
        }
    }
}
