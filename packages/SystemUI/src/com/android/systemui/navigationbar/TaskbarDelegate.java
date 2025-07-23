package com.android.systemui.navigationbar;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import android.view.WindowInsets;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.SysUIToast;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.IconDrawableUtil;
import com.android.systemui.navigationbar.util.NavigationBarLargeScreenUtil;
import com.android.systemui.navigationbar.views.NavBarButtonDrawableProvider;
import com.android.systemui.navigationbar.views.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.views.buttons.KeyButtonDrawableProvider;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.android.systemui.shared.recents.ILauncherProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.AutoHideControllerImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarControllerImpl;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.back.BackAnimationController;
import com.samsung.systemui.splugins.navigationbar.IconResource;
import com.samsung.systemui.splugins.navigationbar.IconType;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TaskbarDelegate implements CommandQueue.Callbacks, LauncherProxyService.LauncherProxyListener, NavigationModeController.ModeChangedListener, Dumpable {
    public int mAppearance;
    public AutoHideController mAutoHideController;
    public BackAnimationController.BackAnimationImpl mBackAnimation;
    public int mBehavior;
    public final Handler mBgHandler;
    public CommandQueue mCommandQueue;
    public final Context mContext;
    public int mDefaultDisplayId;
    public int mDisabledFlags;
    public final DisplayManager mDisplayManager;
    public DisplayTracker mDisplayTracker;
    public EdgeBackGestureHandler mEdgeBackGestureHandler;
    public final NavBarIconResourceMapper mIconResourceMapper;
    public boolean mInitialized;
    public LauncherProxyService mLauncherProxyService;
    public LightBarController mLightBarController;
    public LightBarTransitionsController mLightBarTransitionsController;
    public final LightBarTransitionsController.Factory mLightBarTransitionsControllerFactory;
    public NavBarHelper mNavBarHelper;
    public NavBarRemoteViewManager mNavBarRemoteViewManager;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public int mNavbarFlags;
    public NavigationModeController mNavigationModeController;
    public Optional mPipOptional;
    public final SamsungPluginTaskBar mPluginTaskbar;
    public ScreenPinningNotify mScreenPinningNotify;
    public final StatusBarStateController mStatusBarStateController;
    public SysUiState mSysUiState;
    public TaskStackChangeListeners mTaskStackChangeListeners;
    public boolean mTaskbarTransientShowing;
    public int mTransitionMode;
    public Context mWindowContext;
    public final AnonymousClass1 mNavbarTaskbarStateUpdater = new NavBarHelper.NavbarTaskbarStateUpdater() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.1
        @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
        public final void updateAccessibilityServicesState() {
            TaskbarDelegate.this.updateSysuiFlags();
        }

        @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
        public final void updateAssistantAvailable(boolean z, boolean z2) {
            ILauncherProxy iLauncherProxy = TaskbarDelegate.this.mLauncherProxyService.mLauncherProxy;
            if (iLauncherProxy == null) {
                return;
            }
            try {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onAssistantAvailable(z, z2);
            } catch (RemoteException e) {
                Log.e("TaskbarDelegate", "onAssistantAvailable() failed, available: " + z, e);
            }
        }

        @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
        public final void updateWallpaperVisibility(int i, boolean z) {
            ILauncherProxy iLauncherProxy = TaskbarDelegate.this.mLauncherProxyService.mLauncherProxy;
            if (iLauncherProxy == null) {
                return;
            }
            try {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).updateWallpaperVisibility(i, z);
            } catch (RemoteException e) {
                Log.e("TaskbarDelegate", "updateWallpaperVisibility() failed, visible: " + z, e);
            }
        }
    };
    public int mTaskBarWindowState = 0;
    public final AnonymousClass2 mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.2
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onLockTaskModeChanged(int i) {
            ((SysUiStateImpl) TaskbarDelegate.this.mSysUiState.setFlag(1L, i == 2)).commitUpdate();
        }
    };
    public int mNavigationMode = -1;
    public final AnonymousClass3 mAutoHideUiElement = new AutoHideUiElement() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.3
        @Override // com.android.systemui.statusbar.AutoHideUiElement
        public final void hide() {
            TaskbarDelegate taskbarDelegate = TaskbarDelegate.this;
            if (taskbarDelegate.mTaskbarTransientShowing) {
                taskbarDelegate.mTaskbarTransientShowing = false;
                taskbarDelegate.onTransientStateChanged();
            }
        }

        @Override // com.android.systemui.statusbar.AutoHideUiElement
        public final boolean isVisible() {
            return TaskbarDelegate.this.mTaskbarTransientShowing;
        }

        @Override // com.android.systemui.statusbar.AutoHideUiElement
        public final void synchronizeState() {
            TaskbarDelegate taskbarDelegate = TaskbarDelegate.this;
            for (Display display : ((DisplayTrackerImpl) taskbarDelegate.mDisplayTracker).displayManager.getDisplays()) {
                int displayId = display.getDisplayId();
                ILauncherProxy iLauncherProxy = taskbarDelegate.mLauncherProxyService.mLauncherProxy;
                if (iLauncherProxy != null) {
                    try {
                        ((ILauncherProxy.Stub.Proxy) iLauncherProxy).checkNavBarModes(displayId);
                    } catch (RemoteException e) {
                        Log.e("TaskbarDelegate", "checkNavBarModes() failed", e);
                    }
                }
            }
        }
    };
    public boolean shouldInitializeAgain = false;
    public final TaskbarDelegate$$ExternalSyntheticLambda0 mPipListener = new TaskbarDelegate$$ExternalSyntheticLambda0(this, 0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.navigationbar.TaskbarDelegate$4, reason: invalid class name */
    public class AnonymousClass4 implements LightBarTransitionsController.DarkIntensityApplier {
        public AnonymousClass4() {
        }

        @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
        public final void applyDarkIntensity(final float f) {
            TaskbarDelegate.this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.navigationbar.TaskbarDelegate$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TaskbarDelegate.AnonymousClass4 anonymousClass4 = TaskbarDelegate.AnonymousClass4.this;
                    TaskbarDelegate.this.mLauncherProxyService.onNavButtonsDarkIntensityChanged(f);
                }
            });
        }

        @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
        public final int getTintAnimationDuration() {
            return 120;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.navigationbar.TaskbarDelegate$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.navigationbar.TaskbarDelegate$2] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.navigationbar.TaskbarDelegate$3] */
    public TaskbarDelegate(Context context, LightBarTransitionsController.Factory factory, StatusBarKeyguardViewManager statusBarKeyguardViewManager, StatusBarStateController statusBarStateController, NavBarStore navBarStore, Handler handler) {
        this.mLightBarTransitionsControllerFactory = factory;
        this.mContext = context;
        this.mBgHandler = handler;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        statusBarKeyguardViewManager.setTaskbarDelegate(this);
        this.mStatusBarStateController = statusBarStateController;
        if (BasicRune.NAVBAR_ENABLED) {
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = navBarStore.getNavStateManager();
        }
        if (BasicRune.NAVBAR_TASKBAR) {
            NavBarButtonDrawableProvider.Companion.getClass();
            NavBarButtonDrawableProvider navBarButtonDrawableProvider = NavBarButtonDrawableProvider.INSTANCE;
            if (navBarButtonDrawableProvider == null) {
                navBarButtonDrawableProvider = new NavBarButtonDrawableProvider();
                NavBarButtonDrawableProvider.INSTANCE = navBarButtonDrawableProvider;
            }
            NavBarIconResourceMapper navBarIconResourceMapper = new NavBarIconResourceMapper(navBarButtonDrawableProvider, this.mNavBarStore, context);
            this.mIconResourceMapper = navBarIconResourceMapper;
            SamsungPluginTaskBar samsungPluginTaskBar = new SamsungPluginTaskBar(this.mNavBarStore, context);
            this.mPluginTaskbar = samsungPluginTaskBar;
            samsungPluginTaskBar.taskbarDelegate = this;
            samsungPluginTaskBar.iconResourceMapper = navBarIconResourceMapper;
        }
    }

    public static void loggingAttributesChanged(String str, int i, int i2, boolean z) {
        StringBuilder sb = new StringBuilder("onSystemBarAttributesChanged() - displayId:");
        sb.append(i2);
        sb.append(", appearance:");
        sb.append(i);
        if (i != 0) {
            sb.append(" (");
            sb.append((i & 16) != 0 ? "LIGHT_NAVIGATION_BARS " : "");
            sb.append((i & 2) != 0 ? "OPAQUE_NAVIGATION_BARS " : "");
            sb.append((i & 64) != 0 ? "SEMI_TRANSPARENT_NAVIGATION_BARS " : "");
            sb.append((1048576 & i) != 0 ? "LIGHT_SEMI_TRANSPARENT_NAVIGATION_BARS " : "");
            sb.append((i & 4194304) != 0 ? "DESKTOP_TRANSPARENT_NAVIGATION_BARS" : "");
            sb.append(")");
        }
        sb.append(", navbarColorManagedByIme: ");
        sb.append(z);
        if (!str.contains("com.att")) {
            sb.append(", packageName: ");
            sb.append(str);
        }
        Log.d("TaskbarDelegate", sb.toString());
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void abortTransient(int i, int i2) {
        if (i == this.mDefaultDisplayId && (WindowInsets.Type.navigationBars() & i2) != 0 && this.mTaskbarTransientShowing) {
            this.mTaskbarTransientShowing = false;
            onTransientStateChanged();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionCancelled(int i) {
        appTransitionPending(false);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionFinished(int i) {
        appTransitionPending(false);
    }

    public final void appTransitionPending(boolean z) {
        ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
        if (iLauncherProxy == null) {
            return;
        }
        try {
            ((ILauncherProxy.Stub.Proxy) iLauncherProxy).appTransitionPending(z);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "appTransitionPending() failed, pending: " + z, e);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
        appTransitionPending(false);
    }

    public final void configurationChanged(Resources resources, Configuration configuration) {
        EventTypeFactory.EventType.OnConfigChanged onConfigChanged = new EventTypeFactory.EventType.OnConfigChanged(configuration);
        int i = this.mDefaultDisplayId;
        NavBarStore navBarStore = this.mNavBarStore;
        navBarStore.handleEvent(this, onConfigChanged, i);
        navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarConfigChanged(resources.getBoolean(R.bool.config_safe_media_volume_enabled), !NavigationBarLargeScreenUtil.isLargeScreen(this.mContext), resources.getBoolean(R.bool.config_secondaryBuiltInDisplayIsRound), resources.getInteger(R.integer.config_screenTimeoutOverride)), this.mDefaultDisplayId);
    }

    public final void destroy() {
        if (this.mInitialized) {
            if (BasicRune.NAVBAR_TASKBAR) {
                if (this.mTaskbarTransientShowing) {
                    this.mTaskbarTransientShowing = false;
                    onTransientStateChanged();
                }
                if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                    AutoHideController autoHideController = this.mAutoHideController;
                    AnonymousClass3 anonymousClass3 = this.mAutoHideUiElement;
                    AutoHideControllerImpl.AutoHideUiElementObserver autoHideUiElementObserver = ((AutoHideControllerImpl) autoHideController).mObserver;
                    autoHideUiElementObserver.getClass();
                    ((ArrayList) autoHideUiElementObserver.mList).remove(anonymousClass3);
                    LightBarController lightBarController = this.mLightBarController;
                    ((LightBarControllerImpl) lightBarController).mObserver.mList.remove(this.mLightBarTransitionsController);
                }
                this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnTaskbarDetachedFromWindow(), this.mDefaultDisplayId);
            }
            this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
            this.mLauncherProxyService.removeCallback((LauncherProxyService.LauncherProxyListener) this);
            this.mNavigationModeController.removeListener(this);
            this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
            this.mScreenPinningNotify = null;
            this.mWindowContext = null;
            ((AutoHideControllerImpl) this.mAutoHideController).mNavigationBar = null;
            LightBarTransitionsController lightBarTransitionsController = this.mLightBarTransitionsController;
            CommandQueue commandQueue = lightBarTransitionsController.mCommandQueue;
            LightBarTransitionsController.Callback callback = lightBarTransitionsController.mCallback;
            commandQueue.removeCallback((CommandQueue.Callbacks) callback);
            lightBarTransitionsController.mStatusBarStateController.removeCallback(callback);
            lightBarTransitionsController.mGestureNavigationSettingsObserver.unregister();
            LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) this.mLightBarController;
            lightBarControllerImpl.mNavigationBarController = null;
            lightBarControllerImpl.updateNavigation();
            this.mPipOptional.ifPresent(new TaskbarDelegate$$ExternalSyntheticLambda0(this, 1));
            this.mTaskStackChangeListeners.unregisterTaskStackListener(this.mTaskStackListener);
            this.mInitialized = false;
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.d("TaskbarDelegate", "TaskbarDelegate#destroy");
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        this.mDisabledFlags = i2;
        updateSysuiFlags();
        LauncherProxyService launcherProxyService = this.mLauncherProxyService;
        launcherProxyService.getClass();
        try {
            ILauncherProxy iLauncherProxy = launcherProxyService.mLauncherProxy;
            if (iLauncherProxy != null) {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).disable(i, i2, i3, z);
            } else {
                Log.e("LauncherProxyService", "Failed to get launcher proxy for disable flags.");
            }
        } catch (RemoteException e) {
            Log.e("LauncherProxyService", "Failed to call disable()", e);
        }
        if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
            return;
        }
        ((AssistManager) Dependency.sDependency.getDependencyInner(AssistManager.class)).mDisabledFlags = i2;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("TaskbarDelegate (mDefaultDisplayId=" + this.mDefaultDisplayId + "):");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mNavbarFlags="), this.mNavbarFlags, printWriter, "  mNavigationMode="), this.mNavigationMode, printWriter, "  mDisabledFlags="), this.mDisabledFlags, printWriter, "  mTaskBarWindowState="), this.mTaskBarWindowState, printWriter, "  mBehavior="), this.mBehavior, printWriter, "  mTaskbarTransientShowing="), this.mTaskbarTransientShowing, printWriter);
        this.mEdgeBackGestureHandler.dump(printWriter);
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public final void init(int i) {
        Trace.beginSection("TaskbarDelegate#init");
        try {
            if (this.mInitialized) {
                return;
            }
            this.mDefaultDisplayId = i;
            NavBarHelper navBarHelper = this.mNavBarHelper;
            navBarHelper.getClass();
            NavBarHelper.CurrentSysuiState currentSysuiState = new NavBarHelper.CurrentSysuiState(navBarHelper);
            if (currentSysuiState.mWindowStateDisplayId == this.mDefaultDisplayId) {
                this.mTaskBarWindowState = currentSysuiState.mWindowState;
            }
            this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
            this.mLauncherProxyService.addCallback((LauncherProxyService.LauncherProxyListener) this);
            onNavigationModeChanged(this.mNavigationModeController.addListener(this));
            this.mNavBarHelper.registerNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
            Context createWindowContext = this.mContext.createWindowContext(this.mDisplayManager.getDisplay(i), 2, null);
            this.mWindowContext = createWindowContext;
            this.mScreenPinningNotify = new ScreenPinningNotify(createWindowContext);
            updateSysuiFlags();
            boolean z = BasicRune.NAVBAR_TASKBAR;
            AnonymousClass3 anonymousClass3 = this.mAutoHideUiElement;
            if (z) {
                this.shouldInitializeAgain = false;
                this.mNavbarFlags = this.mNavBarHelper.mLastIMEhints;
                LightBarTransitionsController create = this.mLightBarTransitionsControllerFactory.create(new AnonymousClass4());
                this.mLightBarTransitionsController = create;
                this.mLauncherProxyService.onNavButtonsDarkIntensityChanged(create.mDarkIntensity);
                if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                    ((AutoHideControllerImpl) this.mAutoHideController).registerElementToObserver(anonymousClass3);
                    LightBarController lightBarController = this.mLightBarController;
                    LightBarTransitionsController lightBarTransitionsController = this.mLightBarTransitionsController;
                    LightBarControllerImpl.LightBarTransientObserver lightBarTransientObserver = ((LightBarControllerImpl) lightBarController).mObserver;
                    lightBarTransientObserver.mList.remove(lightBarTransitionsController);
                    if (lightBarTransitionsController != null) {
                        lightBarTransientObserver.mList.add(lightBarTransitionsController);
                    }
                }
                this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnTaskbarAttachedToWindow(), this.mDefaultDisplayId);
                configurationChanged(this.mContext.getResources(), this.mContext.getResources().getConfiguration());
            }
            ((AutoHideControllerImpl) this.mAutoHideController).mNavigationBar = anonymousClass3;
            LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) this.mLightBarController;
            lightBarControllerImpl.mNavigationBarController = this.mLightBarTransitionsController;
            lightBarControllerImpl.updateNavigation();
            this.mPipOptional.ifPresent(new TaskbarDelegate$$ExternalSyntheticLambda0(this, 2));
            this.mEdgeBackGestureHandler.setBackAnimation(this.mBackAnimation);
            this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackListener);
            this.mInitialized = true;
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.d("TaskbarDelegate", "TaskbarDelegate#init");
            }
        } finally {
            Trace.endSection();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedGameToolsWin(boolean z) {
        if (!BasicRune.NAVBAR_TASKBAR || this.mAutoHideController == null) {
            return;
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("notifyRequestedGameToolsWin visible : ", "TaskbarDelegate", z);
        ((AutoHideControllerImpl) this.mAutoHideController).notifyRequestedGameToolsWin(z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedSystemKey(boolean z, boolean z2) {
        if (BasicRune.NAVBAR_TASKBAR) {
            ((SysUiStateImpl) this.mSysUiState.setFlag(274877906944L, z).setFlag(549755813888L, z2)).commitUpdate();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifySamsungPayInfo(int i, boolean z, Rect rect) {
        if (BasicRune.NAVBAR_TASKBAR && this.mDefaultDisplayId == i && i == 0) {
            Log.d("TaskbarDelegate", String.format("notifySamsungPayInfo displayId: %d, visible: %s", Integer.valueOf(i), Boolean.valueOf(z)));
            LauncherProxyService launcherProxyService = this.mLauncherProxyService;
            int width = rect.width();
            launcherProxyService.getClass();
            try {
                ILauncherProxy iLauncherProxy = launcherProxyService.mLauncherProxy;
                if (iLauncherProxy != null) {
                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy).notifyPayInfo(width, z);
                }
            } catch (RemoteException e) {
                Log.e("LauncherProxyService", "Failed to notify pay info.", e);
            }
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateSpayVisibility(z, rect.width()));
        }
    }

    @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
    public final void onConnectionChanged(boolean z) {
        if (BasicRune.NAVBAR_TASKBAR && z && this.shouldInitializeAgain) {
            this.shouldInitializeAgain = false;
            onInitializedTaskbarNavigationBar();
            try {
                ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
                if (iLauncherProxy != null) {
                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy).isTaskbarEnabled(((NavBarStateManagerImpl) this.mNavBarStateManager).isTaskBarEnabled(false));
                }
            } catch (Exception e) {
                Log.e("TaskbarDelegate", "Failed to call isTaskbarEnabled()", e);
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onDisplayAddSystemDecorations(int i) {
        this.mEdgeBackGestureHandler.getClass();
        if (this.mLauncherProxyService.mLauncherProxy == null) {
            return;
        }
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onDisplayAddSystemDecorations id=", "TaskbarDelegate");
        }
        try {
            ((ILauncherProxy.Stub.Proxy) this.mLauncherProxyService.mLauncherProxy).onDisplayAddSystemDecorations(i);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "onDisplayAddSystemDecorations() failed", e);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onDisplayRemoveSystemDecorations(int i) {
        this.mEdgeBackGestureHandler.getClass();
        if (this.mLauncherProxyService.mLauncherProxy == null) {
            return;
        }
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onDisplayRemoveSystemDecorations id=", "TaskbarDelegate");
        }
        try {
            ((ILauncherProxy.Stub.Proxy) this.mLauncherProxyService.mLauncherProxy).onDisplayRemoveSystemDecorations(i);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "onDisplaySystemDecorationsRemoved() failed", e);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onDisplayRemoved(int i) {
        this.mEdgeBackGestureHandler.getClass();
        ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
        if (iLauncherProxy == null) {
            return;
        }
        try {
            ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onDisplayRemoved(i);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "onDisplayRemoved() failed", e);
        }
    }

    @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
    public final void onInitializedTaskbarNavigationBar() {
        NavBarStateManager navBarStateManager;
        Bundle bundle;
        boolean z = BasicRune.NAVBAR_TASKBAR;
        if (z && this.mInitialized) {
            Log.d("TaskbarDelegate", "onInitializedTaskbarNavigationBar()");
            updateTaskbarButtonIconsAndHints();
            if (z && this.mNavBarRemoteViewManager != null) {
                for (int i = 0; i <= 1; i++) {
                    NavBarRemoteView remoteView = this.mNavBarRemoteViewManager.getRemoteView(i);
                    if (remoteView != null) {
                        bundle = new Bundle();
                        bundle.putString("requestClass", remoteView.requestClass);
                        bundle.putParcelable("remoteViews", remoteView.remoteViews);
                        bundle.putInt(SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION, i);
                        bundle.putInt(SystemUIAnalytics.QPNE_VID_PRIORITY, remoteView.priority);
                    } else {
                        bundle = null;
                    }
                    if (bundle != null) {
                        NavBarEvents navBarEvents = new NavBarEvents();
                        navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_NAVBAR_REMOTEVIEWS;
                        navBarEvents.remoteViewBundle = bundle;
                        sendNavbarEvent(navBarEvents);
                    }
                }
            }
            boolean z2 = BasicRune.NAVBAR_TASKBAR;
            if (z2) {
                NavBarEvents navBarEvents2 = new NavBarEvents();
                navBarEvents2.eventType = NavBarEvents.EventType.ON_ROTATION_LOCKED_CHANGED;
                navBarEvents2.rotationLocked = ((RotationLockController) Dependency.sDependency.getDependencyInner(RotationLockController.class)).isRotationLocked();
                sendNavbarEvent(navBarEvents2);
            }
            if (z2 && (navBarStateManager = this.mNavBarStateManager) != null) {
                boolean isNavBarHiddenByKnox = ((NavBarStateManagerImpl) navBarStateManager).isNavBarHiddenByKnox();
                NavBarEvents navBarEvents3 = new NavBarEvents();
                navBarEvents3.eventType = NavBarEvents.EventType.ON_UPDATE_TASKBAR_VIS_BY_KNOX;
                navBarEvents3.hiddenByKnox = isNavBarHiddenByKnox;
                sendNavbarEvent(navBarEvents3);
                ((SysUiStateImpl) this.mSysUiState.setFlag(1099511627776L, isNavBarHiddenByKnox)).commitUpdate();
            }
            this.mLauncherProxyService.onNavButtonsDarkIntensityChanged(this.mLightBarTransitionsController.mDarkIntensity);
            this.mPluginTaskbar.updatePluginBundle();
            EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
            this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnUpdateSideBackGestureInsets(edgeBackGestureHandler.mEdgeWidthLeft, edgeBackGestureHandler.mEdgeWidthRight), this.mDefaultDisplayId);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavigationMode = i;
        this.mEdgeBackGestureHandler.onNavigationModeChanged(i);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onRotationProposal(int i, boolean z) {
        LauncherProxyService launcherProxyService = this.mLauncherProxyService;
        launcherProxyService.getClass();
        try {
            if (launcherProxyService.mLauncherProxy == null) {
                Log.e("LauncherProxyService", "Failed to get launcher proxy for proposing rotation.");
            } else {
                if (BasicRune.NAVBAR_TASKBAR && ((NavBarStateManagerImpl) launcherProxyService.mNavBarStateManager).rotateDisabledByPolicy()) {
                    return;
                }
                ((ILauncherProxy.Stub.Proxy) launcherProxyService.mLauncherProxy).onRotationProposal(i, z);
            }
        } catch (RemoteException e) {
            Log.e("LauncherProxyService", "Failed to call onRotationProposal()", e);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        boolean z2;
        if (!BasicRune.NAVBAR_ENABLED || this.mDefaultDisplayId == i) {
            this.mLauncherProxyService.onSystemBarAttributesChanged(i, i3);
            boolean z3 = BasicRune.NAVBAR_TASKBAR;
            if (z3) {
                NavBarEvents navBarEvents = new NavBarEvents();
                navBarEvents.eventType = NavBarEvents.EventType.ON_APPEARANCE_CHANGED;
                navBarEvents.appearance = (-2097153) & i2;
                navBarEvents.displayId = i;
                sendNavbarEvent(navBarEvents);
                loggingAttributesChanged(str, i2, i, z);
            }
            if (this.mAppearance != i2) {
                this.mAppearance = i2;
                z2 = updateTransitionMode$1(NavBarHelper.transitionMode(i2, this.mTaskbarTransientShowing));
            } else {
                z2 = false;
            }
            if (i == this.mDefaultDisplayId) {
                ((LightBarControllerImpl) this.mLightBarController).onNavigationBarAppearanceChanged(i2, z3 ? this.mTransitionMode : 0, z2, z, str);
            }
            if (this.mBehavior != i3) {
                this.mBehavior = i3;
                updateSysuiFlags();
            }
        }
    }

    @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
    public final void onTaskbarAutohideSuspend(boolean z) {
        if (z) {
            ((AutoHideControllerImpl) this.mAutoHideController).suspendAutoHide();
        } else {
            ((AutoHideControllerImpl) this.mAutoHideController).resumeSuspendedAutoHide();
        }
    }

    @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
    public final void onTaskbarSPluginButtonClicked() {
        this.mPluginTaskbar.buttonDispatcherProxy.pinButton.view.performClick();
    }

    public final void onTransientStateChanged() {
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        boolean z = this.mTaskbarTransientShowing;
        edgeBackGestureHandler.mIsNavBarShownTransiently = z;
        int transitionMode = NavBarHelper.transitionMode(this.mAppearance, z);
        if (updateTransitionMode$1(transitionMode)) {
            LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) this.mLightBarController;
            lightBarControllerImpl.mHasLightNavigationBar = LightBarControllerImpl.isLight(lightBarControllerImpl.mAppearance, transitionMode, 16);
            if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
                lightBarControllerImpl.mNavigationBarMode = transitionMode;
                lightBarControllerImpl.reevaluate();
            }
        }
        if (BasicRune.NAVBAR_TASKBAR) {
            if (this.mTaskbarTransientShowing) {
                ((AutoHideControllerImpl) this.mAutoHideController).touchAutoHide();
            }
            NavBarEvents navBarEvents = new NavBarEvents();
            navBarEvents.eventType = NavBarEvents.EventType.ON_TRANSIENT_SHOWING_CHANGED;
            navBarEvents.transientShowing = this.mTaskbarTransientShowing;
            navBarEvents.displayId = this.mDefaultDisplayId;
            sendNavbarEvent(navBarEvents);
        }
    }

    public final void putButtonBitmapsToBundle(IconType iconType, Bundle bundle) {
        KeyButtonDrawable buttonDrawable = this.mIconResourceMapper.getButtonDrawable(iconType);
        Drawable mutate = buttonDrawable.mLayerDrawable.getDrawable(0).mutate();
        Drawable mutate2 = buttonDrawable.mLayerDrawable.getDrawable(1).mutate();
        mutate.setAlpha(255);
        mutate2.setAlpha(255);
        Bitmap[] bitmapArr = {IconDrawableUtil.getBitmap(mutate), IconDrawableUtil.getBitmap(mutate2)};
        bundle.putParcelable(iconType.name() + "_LIGHT", bitmapArr[0]);
        bundle.putParcelable(iconType.name() + "_DARK", bitmapArr[1]);
    }

    public final void sendNavbarEvent(NavBarEvents navBarEvents) {
        if (!this.mInitialized) {
            Log.d("TaskbarDelegate", "handleNavigationBarEvent() TaskbarDelegate is not initialized.");
            return;
        }
        try {
            ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
            if (iLauncherProxy != null) {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).handleNavigationBarEvent(navBarEvents);
            } else if (BasicRune.NAVBAR_TASKBAR) {
                this.shouldInitializeAgain = true;
            }
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "Failed to call handleNavigationBarEvent()", e);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setImeWindowStatus(int i, int i2, int i3, boolean z) {
        boolean isImeVisible = this.mNavBarHelper.isImeVisible(i2);
        int updateNavbarFlagsFromIme = Utilities.updateNavbarFlagsFromIme(this.mNavbarFlags, i3, isImeVisible, z);
        if (updateNavbarFlagsFromIme == this.mNavbarFlags) {
            return;
        }
        this.mNavbarFlags = updateNavbarFlagsFromIme;
        updateSysuiFlags();
        if (BasicRune.NAVBAR_TASKBAR) {
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "setImeWindowStatus displayId=", " vis=", " backDisposition=");
            m.append(i3);
            m.append(" showImeSwitcher=");
            m.append(z);
            m.append(" imeVisible=");
            ActionBarContextView$$ExternalSyntheticOutline0.m(m, isImeVisible, "TaskbarDelegate");
            this.mNavBarHelper.mLastIMEhints = this.mNavbarFlags;
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        LauncherProxyService launcherProxyService = this.mLauncherProxyService;
        launcherProxyService.getClass();
        try {
            ILauncherProxy iLauncherProxy = launcherProxyService.mLauncherProxy;
            if (iLauncherProxy != null) {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onNavigationBarLumaSamplingEnabled(i, z);
            } else {
                Log.e("LauncherProxyService", "Failed to get launcher proxy to enable/disable nav bar lumasampling");
            }
        } catch (RemoteException e) {
            Log.e("LauncherProxyService", "Failed to call onNavigationBarLumaSamplingEnabled()", e);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setWindowState(int i, int i2, int i3) {
        if (i == this.mDefaultDisplayId && i2 == 2 && this.mTaskBarWindowState != i3) {
            this.mTaskBarWindowState = i3;
            updateSysuiFlags();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showPinningEnterExitToast(boolean z) {
        updateSysuiFlags();
        ScreenPinningNotify screenPinningNotify = this.mScreenPinningNotify;
        if (screenPinningNotify == null) {
            return;
        }
        if (z) {
            SysUIToast.makeText(screenPinningNotify.mContext, com.android.systemui.R.string.sec_screen_pinning_start, 1).show();
        } else {
            SysUIToast.makeText(screenPinningNotify.mContext, com.android.systemui.R.string.sec_screen_pinning_exit, 1).show();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showPinningEscapeToast() {
        updateSysuiFlags();
        ScreenPinningNotify screenPinningNotify = this.mScreenPinningNotify;
        if (screenPinningNotify == null) {
            return;
        }
        screenPinningNotify.showEscapeToast(QuickStepContract.isGesturalMode(this.mNavigationMode), !QuickStepContract.isGesturalMode(this.mNavigationMode));
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showTransient(int i, int i2, boolean z) {
        if (i != this.mDefaultDisplayId || (WindowInsets.Type.navigationBars() & i2) == 0 || this.mTaskbarTransientShowing) {
            return;
        }
        this.mTaskbarTransientShowing = true;
        onTransientStateChanged();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void toggleTaskbar() {
        ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
        if (iLauncherProxy == null) {
            return;
        }
        try {
            ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onTaskbarToggled();
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "onTaskbarToggled() failed", e);
        }
    }

    public final void updateSysuiFlags() {
        long j = this.mNavBarHelper.mA11yButtonState;
        ((SysUiStateImpl) this.mSysUiState.setFlag(16L, (j & 16) != 0).setFlag(32L, (j & 32) != 0).setFlag(262144L, (this.mNavbarFlags & 2) != 0).setFlag(1048576L, (this.mNavbarFlags & 4) != 0).setFlag(68719476736L, (this.mNavbarFlags & 1) != 0).setFlag(128L, (this.mDisabledFlags & 16777216) != 0).setFlag(256L, (this.mDisabledFlags & 2097152) != 0).setFlag(4194304L, (this.mDisabledFlags & 4194304) != 0).setFlag(2L, !(this.mTaskBarWindowState == 0)).setFlag(131072L, this.mBehavior != 2)).commitUpdate();
    }

    public final void updateTaskbarButtonIconsAndHints() {
        int i = 1;
        boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.mContext) == 1;
        NavBarIconResourceMapper navBarIconResourceMapper = this.mIconResourceMapper;
        navBarIconResourceMapper.isRTL = z;
        Bundle bundle = new Bundle();
        bundle.putBoolean("defaultIcon", !navBarIconResourceMapper.themeIcon);
        putButtonBitmapsToBundle(IconType.TYPE_RECENT, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_HOME, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_BACK, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_BACK_ALT, bundle);
        Context context = this.mContext;
        IconType iconType = IconType.TYPE_GESTURE_HANDLE_HINT;
        Context context2 = navBarIconResourceMapper.context;
        IconResource iconResource = navBarIconResourceMapper.getIconResource(iconType);
        KeyButtonDrawableProvider keyButtonDrawableProvider = navBarIconResourceMapper.buttonDrawableProvider;
        Bitmap[] bitmapFromDrawable = IconDrawableUtil.getBitmapFromDrawable(context, ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getGestureHintDrawable(context2, iconResource, 0));
        Bitmap[] bitmapFromDrawable2 = IconDrawableUtil.getBitmapFromDrawable(this.mContext, ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper.context, navBarIconResourceMapper.getIconResource(iconType), 0));
        Bitmap[] bitmapFromDrawable3 = IconDrawableUtil.getBitmapFromDrawable(this.mContext, ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper.context, navBarIconResourceMapper.getIconResource(iconType), 0));
        StringBuilder sb = new StringBuilder();
        NavBarEvents.IconType iconType2 = NavBarEvents.IconType.TYPE_GESTURE_HANDLE_HINT;
        sb.append(iconType2.name());
        sb.append("_LIGHT");
        bundle.putParcelable(sb.toString(), bitmapFromDrawable[0]);
        bundle.putParcelable(iconType2.name() + "_DARK", bitmapFromDrawable[1]);
        StringBuilder sb2 = new StringBuilder();
        NavBarEvents.IconType iconType3 = NavBarEvents.IconType.TYPE_GESTURE_HINT;
        sb2.append(iconType3.name());
        sb2.append("_LIGHT");
        bundle.putParcelable(sb2.toString(), bitmapFromDrawable2[0]);
        bundle.putParcelable(iconType3.name() + "_DARK", bitmapFromDrawable2[1]);
        StringBuilder sb3 = new StringBuilder();
        NavBarEvents.IconType iconType4 = NavBarEvents.IconType.TYPE_GESTURE_HINT_VI;
        sb3.append(iconType4.name());
        sb3.append("_LIGHT");
        bundle.putParcelable(sb3.toString(), bitmapFromDrawable3[0]);
        bundle.putParcelable(iconType4.name() + "_DARK", bitmapFromDrawable3[1]);
        while (true) {
            SamsungPluginTaskBar samsungPluginTaskBar = this.mPluginTaskbar;
            if (i >= 6) {
                samsungPluginTaskBar.getClass();
                samsungPluginTaskBar.parseAndUpdateBundle();
                NavBarEvents navBarEvents = new NavBarEvents();
                navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_ICON_BITMAP;
                navBarEvents.iconBitmapBundle = bundle;
                sendNavbarEvent(navBarEvents);
                return;
            }
            Bitmap bitmap = (Bitmap) samsungPluginTaskBar.pluginBundle.getParcelable("extra" + i + "_LIGHT");
            if (bitmap != null) {
                bundle.putParcelable("extra" + i + "_LIGHT", bitmap);
            }
            Bitmap bitmap2 = (Bitmap) samsungPluginTaskBar.pluginBundle.getParcelable("extra" + i + "_DARK");
            if (bitmap2 != null) {
                bundle.putParcelable("extra" + i + "_DARK", bitmap2);
            }
            i++;
        }
    }

    public final boolean updateTransitionMode$1(int i) {
        if (this.mTransitionMode == i) {
            return false;
        }
        this.mTransitionMode = i;
        ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
        if (iLauncherProxy != null) {
            try {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onTransitionModeUpdated(i);
            } catch (RemoteException e) {
                Log.e("TaskbarDelegate", "onTransitionModeUpdated() failed, barMode: " + i, e);
            }
        }
        AutoHideController autoHideController = this.mAutoHideController;
        if (autoHideController != null) {
            ((AutoHideControllerImpl) autoHideController).touchAutoHide();
        }
        if (!BasicRune.NAVBAR_TASKBAR) {
            return true;
        }
        this.mNavBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarTransitionModeChanged(this.mTransitionMode), this.mDefaultDisplayId);
        return true;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionPending(int i, boolean z) {
        appTransitionPending(true);
    }
}
