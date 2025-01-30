package com.android.systemui.navigationbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import android.view.WindowInsets;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.back.BackAnimationController;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteView;
import com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.IconDrawableUtil;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import com.samsung.systemui.splugins.navigationbar.IconType;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TaskbarDelegate implements CommandQueue.Callbacks, OverviewProxyService.OverviewProxyListener, NavigationModeController.ModeChangedListener, Dumpable {
    public int mAppearance;
    public AutoHideController mAutoHideController;
    public BackAnimationController.BackAnimationImpl mBackAnimation;
    public int mBehavior;
    public CommandQueue mCommandQueue;
    public final Context mContext;
    public int mDisabledFlags;
    public int mDisplayId;
    public final DisplayManager mDisplayManager;
    public EdgeBackGestureHandler mEdgeBackGestureHandler;
    public final NavBarIconResourceMapper mIconResourceMapper;
    public boolean mInitialized;
    public LightBarController mLightBarController;
    public LightBarTransitionsController mLightBarTransitionsController;
    public final LightBarTransitionsController.Factory mLightBarTransitionsControllerFactory;
    public NavBarHelper mNavBarHelper;
    public NavBarRemoteViewManager mNavBarRemoteViewManager;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public int mNavigationIconHints;
    public NavigationModeController mNavigationModeController;
    public OverviewProxyService mOverviewProxyService;
    public Optional mPipOptional;
    public final SamsungPluginTaskBar mPluginTaskBar;
    public ScreenPinningNotify mScreenPinningNotify;
    public boolean mShouldInitializeAgain;
    public SysUiState mSysUiState;
    public TaskStackChangeListeners mTaskStackChangeListeners;
    public boolean mTaskbarTransientShowing;
    public int mTransitionMode;
    public Context mWindowContext;
    public final C18641 mNavbarTaskbarStateUpdater = new NavBarHelper.NavbarTaskbarStateUpdater() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.1
        @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
        public final void updateAccessibilityServicesState() {
            TaskbarDelegate.this.updateSysuiFlags();
        }

        @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
        public final void updateAssistantAvailable(boolean z, boolean z2) {
            IOverviewProxy iOverviewProxy = TaskbarDelegate.this.mOverviewProxyService.mOverviewProxy;
            if (iOverviewProxy == null) {
                return;
            }
            try {
                ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onAssistantAvailable(z, z2);
            } catch (RemoteException e) {
                Log.e("TaskbarDelegate", "onAssistantAvailable() failed, available: " + z, e);
            }
        }
    };
    public int mTaskBarWindowState = 0;
    public final C18652 mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.2
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onLockTaskModeChanged(int i) {
            TaskbarDelegate taskbarDelegate = TaskbarDelegate.this;
            SysUiState sysUiState = taskbarDelegate.mSysUiState;
            sysUiState.setFlag(1L, i == 2);
            sysUiState.commitUpdate(taskbarDelegate.mDisplayId);
        }
    };
    public int mNavigationMode = -1;
    public final C18663 mAutoHideUiElement = new AutoHideUiElement() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.3
        @Override // com.android.systemui.statusbar.AutoHideUiElement
        public final void hide() {
            TaskbarDelegate taskbarDelegate = TaskbarDelegate.this;
            taskbarDelegate.getClass();
            if (BasicRune.NAVBAR_SUPPORT_TASKBAR || taskbarDelegate.mTaskbarTransientShowing) {
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
        }
    };
    public final TaskbarDelegate$$ExternalSyntheticLambda0 mPipListener = new TaskbarDelegate$$ExternalSyntheticLambda0(this, 2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.navigationbar.TaskbarDelegate$4 */
    public final class C18674 implements LightBarTransitionsController.DarkIntensityApplier {
        public C18674() {
        }

        @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
        public final void applyDarkIntensity(float f) {
            TaskbarDelegate.this.mOverviewProxyService.onNavButtonsDarkIntensityChanged(f);
        }

        @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
        public final int getTintAnimationDuration() {
            return 120;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.navigationbar.TaskbarDelegate$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.navigationbar.TaskbarDelegate$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.navigationbar.TaskbarDelegate$3] */
    public TaskbarDelegate(Context context, LightBarTransitionsController.Factory factory, StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        this.mLightBarTransitionsControllerFactory = factory;
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        statusBarKeyguardViewManager.setTaskbarDelegate(this);
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
            NavBarStore navBarStore = (NavBarStore) Dependency.get(NavBarStore.class);
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(context.getDisplayId());
            NavBarButtonDrawableProvider.Companion.getClass();
            NavBarButtonDrawableProvider navBarButtonDrawableProvider = NavBarButtonDrawableProvider.INSTANCE;
            if (navBarButtonDrawableProvider == null) {
                navBarButtonDrawableProvider = new NavBarButtonDrawableProvider();
                NavBarButtonDrawableProvider.INSTANCE = navBarButtonDrawableProvider;
            }
            this.mIconResourceMapper = new NavBarIconResourceMapper(navBarButtonDrawableProvider, navBarStore, context, new LogWrapper(ModuleType.NAVBAR, null));
            this.mPluginTaskBar = new SamsungPluginTaskBar(navBarStore, context);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void abortTransient(int i, int i2) {
        if (i == this.mDisplayId && (WindowInsets.Type.navigationBars() & i2) != 0) {
            if (BasicRune.NAVBAR_SUPPORT_TASKBAR || this.mTaskbarTransientShowing) {
                this.mTaskbarTransientShowing = false;
                onTransientStateChanged();
            }
        }
    }

    public final void destroy() {
        if (this.mInitialized) {
            boolean z = BasicRune.NAVBAR_SUPPORT_TASKBAR;
            if (z && (z || this.mTaskbarTransientShowing)) {
                this.mTaskbarTransientShowing = false;
                onTransientStateChanged();
            }
            this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
            ((ArrayList) this.mOverviewProxyService.mConnectionCallbacks).remove(this);
            this.mNavigationModeController.removeListener(this);
            this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
            this.mScreenPinningNotify = null;
            this.mWindowContext = null;
            this.mAutoHideController.mNavigationBar = null;
            LightBarTransitionsController lightBarTransitionsController = this.mLightBarTransitionsController;
            CommandQueue commandQueue = lightBarTransitionsController.mCommandQueue;
            LightBarTransitionsController.Callback callback = lightBarTransitionsController.mCallback;
            commandQueue.removeCallback((CommandQueue.Callbacks) callback);
            lightBarTransitionsController.mStatusBarStateController.removeCallback(callback);
            LightBarController lightBarController = this.mLightBarController;
            lightBarController.mNavigationBarController = null;
            lightBarController.updateNavigation();
            this.mPipOptional.ifPresent(new TaskbarDelegate$$ExternalSyntheticLambda0(this, 1));
            this.mTaskStackChangeListeners.unregisterTaskStackListener(this.mTaskStackListener);
            if (z) {
                ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnTaskbarDetachedFromWindow());
            }
            if (BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
                AutoHideController.AutoHideUiElementObserver autoHideUiElementObserver = this.mAutoHideController.mObserver;
                autoHideUiElementObserver.getClass();
                ((ArrayList) autoHideUiElementObserver.mList).remove(this.mAutoHideUiElement);
                LightBarController lightBarController2 = this.mLightBarController;
                lightBarController2.mObserver.mList.remove(this.mLightBarTransitionsController);
            }
            this.mInitialized = false;
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void disable(int i, int i2, int i3, boolean z) {
        this.mDisabledFlags = i2;
        updateSysuiFlags();
        OverviewProxyService overviewProxyService = this.mOverviewProxyService;
        overviewProxyService.getClass();
        try {
            IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
            if (iOverviewProxy != null) {
                ((IOverviewProxy.Stub.Proxy) iOverviewProxy).disable(i, i2, i3, z);
            } else {
                Log.e("OverviewProxyService", "Failed to get overview proxy for disable flags.");
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call disable()", e);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("TaskbarDelegate (displayId=" + this.mDisplayId + "):");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("  mNavigationIconHints="), this.mNavigationIconHints, printWriter, "  mNavigationMode="), this.mNavigationMode, printWriter, "  mDisabledFlags="), this.mDisabledFlags, printWriter, "  mTaskBarWindowState="), this.mTaskBarWindowState, printWriter, "  mBehavior="), this.mBehavior, printWriter, "  mTaskbarTransientShowing="), this.mTaskbarTransientShowing, printWriter);
        this.mEdgeBackGestureHandler.dump(printWriter);
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public final void handleNavigationBarEvent(NavBarEvents navBarEvents) {
        if (!this.mInitialized) {
            Log.d("TaskbarDelegate", "handleNavigationBarEvent() TaskbarDelegate is not initialized.");
            return;
        }
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxyService.mOverviewProxy;
            if (iOverviewProxy != null) {
                ((IOverviewProxy.Stub.Proxy) iOverviewProxy).handleNavigationBarEvent(navBarEvents);
            } else {
                this.mShouldInitializeAgain = true;
            }
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "Failed to call handleNavigationBarEvent()", e);
        }
    }

    public final void init(int i) {
        if (this.mInitialized) {
            return;
        }
        boolean z = BasicRune.NAVBAR_SUPPORT_TASKBAR;
        int i2 = 0;
        if (z) {
            this.mNavigationIconHints = this.mNavBarHelper.mLastIMEhints;
            this.mLightBarTransitionsController = this.mLightBarTransitionsControllerFactory.create(new C18674());
            this.mShouldInitializeAgain = false;
        }
        boolean z2 = BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY;
        C18663 c18663 = this.mAutoHideUiElement;
        if (z2) {
            AutoHideController.AutoHideUiElementObserver autoHideUiElementObserver = this.mAutoHideController.mObserver;
            autoHideUiElementObserver.getClass();
            ArrayList arrayList = (ArrayList) autoHideUiElementObserver.mList;
            arrayList.remove(c18663);
            if (c18663 != null) {
                arrayList.add(c18663);
            }
            LightBarController lightBarController = this.mLightBarController;
            LightBarTransitionsController lightBarTransitionsController = this.mLightBarTransitionsController;
            ArrayList arrayList2 = lightBarController.mObserver.mList;
            arrayList2.remove(lightBarTransitionsController);
            if (lightBarTransitionsController != null) {
                arrayList2.add(lightBarTransitionsController);
            }
        }
        this.mDisplayId = i;
        NavBarHelper navBarHelper = this.mNavBarHelper;
        navBarHelper.getClass();
        NavBarHelper.CurrentSysuiState currentSysuiState = new NavBarHelper.CurrentSysuiState(navBarHelper);
        if (currentSysuiState.mWindowStateDisplayId == this.mDisplayId) {
            this.mTaskBarWindowState = currentSysuiState.mWindowState;
        }
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mOverviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this);
        onNavigationModeChanged(this.mNavigationModeController.addListener(this));
        this.mNavBarHelper.registerNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        Display display = this.mDisplayManager.getDisplay(i);
        Context context = this.mContext;
        Context createWindowContext = context.createWindowContext(display, 2, null);
        this.mWindowContext = createWindowContext;
        this.mScreenPinningNotify = new ScreenPinningNotify(createWindowContext);
        updateSysuiFlags();
        this.mAutoHideController.mNavigationBar = c18663;
        LightBarController lightBarController2 = this.mLightBarController;
        lightBarController2.mNavigationBarController = this.mLightBarTransitionsController;
        lightBarController2.updateNavigation();
        this.mPipOptional.ifPresent(new TaskbarDelegate$$ExternalSyntheticLambda0(this, i2));
        this.mEdgeBackGestureHandler.setBackAnimation(this.mBackAnimation);
        this.mEdgeBackGestureHandler.onConfigurationChanged(context.getResources().getConfiguration());
        this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackListener);
        this.mInitialized = true;
        if (z) {
            this.mOverviewProxyService.onNavButtonsDarkIntensityChanged(this.mLightBarTransitionsController.mDarkIntensity);
            context.getResources().getConfiguration().windowConfiguration.getRotation();
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnTaskbarAttachedToWindow());
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedGameToolsWin(boolean z) {
        if (!BasicRune.NAVBAR_SUPPORT_TASKBAR || this.mAutoHideController == null) {
            return;
        }
        Log.d("TaskbarDelegate", String.format("notifyRequestedGameToolsWin visible : %s", Boolean.valueOf(z)));
        this.mAutoHideController.notifyRequestedGameToolsWin(z);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifyRequestedSystemKey(boolean z, boolean z2) {
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
            SysUiState sysUiState = this.mSysUiState;
            sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_LOCK_ICON, z);
            sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_CLOCK, z2);
            sysUiState.commitUpdate(this.mDisplayId);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void notifySamsungPayInfo(int i, boolean z, Rect rect) {
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR && this.mDisplayId == i) {
            Log.d("TaskbarDelegate", String.format("notifySamsungPayInfo displayId : %d, visible: %s", Integer.valueOf(i), Boolean.valueOf(z)));
            if (i == 0) {
                OverviewProxyService overviewProxyService = this.mOverviewProxyService;
                int width = rect.width();
                overviewProxyService.getClass();
                try {
                    IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).notifyPayInfo(width, z);
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to notify pay info.", e);
                }
            }
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateSpayVisibility(z, rect.width()));
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onConnectionChanged(boolean z) {
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR && z && this.mShouldInitializeAgain) {
            this.mShouldInitializeAgain = false;
            onInitializedTaskbarNavigationBar();
            try {
                ((IOverviewProxy.Stub.Proxy) this.mOverviewProxyService.mOverviewProxy).isTaskbarEnabled(this.mNavBarStateManager.isTaskBarEnabled(false));
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onInitializedTaskbarNavigationBar() {
        Bundle bundle;
        if (this.mInitialized) {
            Log.d("TaskbarDelegate", "onInitializedTaskbarNavigationBar()");
            updateTaskbarButtonIconsAndHints();
            if (this.mNavBarRemoteViewManager != null) {
                for (int i = 0; i < 2; i++) {
                    NavBarRemoteView remoteView = this.mNavBarRemoteViewManager.getRemoteView(i, 0);
                    if (remoteView != null) {
                        bundle = new Bundle();
                        bundle.putString("requestClass", remoteView.requestClass);
                        bundle.putParcelable("remoteViews", remoteView.remoteViews);
                        bundle.putInt("position", i);
                        bundle.putInt("priority", remoteView.priority);
                    } else {
                        bundle = null;
                    }
                    if (bundle != null) {
                        NavBarEvents navBarEvents = new NavBarEvents();
                        navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_NAVBAR_REMOTEVIEWS;
                        navBarEvents.remoteViewBundle = bundle;
                        handleNavigationBarEvent(navBarEvents);
                    }
                }
            }
            NavBarEvents navBarEvents2 = new NavBarEvents();
            navBarEvents2.eventType = NavBarEvents.EventType.ON_ROTATION_LOCKED_CHANGED;
            navBarEvents2.rotationLocked = ((RotationLockController) Dependency.get(RotationLockController.class)).isRotationLocked();
            handleNavigationBarEvent(navBarEvents2);
            boolean isNavBarHiddenByKnox = this.mNavBarStateManager.isNavBarHiddenByKnox();
            NavBarEvents navBarEvents3 = new NavBarEvents();
            navBarEvents3.eventType = NavBarEvents.EventType.ON_UPDATE_TASKBAR_VIS_BY_KNOX;
            navBarEvents3.hiddenByKnox = isNavBarHiddenByKnox;
            handleNavigationBarEvent(navBarEvents3);
            SysUiState sysUiState = this.mSysUiState;
            sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_NIO, isNavBarHiddenByKnox);
            sysUiState.commitUpdate(this.mDisplayId);
            this.mOverviewProxyService.onNavButtonsDarkIntensityChanged(this.mLightBarTransitionsController.mDarkIntensity);
            this.mPluginTaskBar.updatePluginBundle();
            EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateSideBackGestureInsets(edgeBackGestureHandler.mEdgeWidthLeft, edgeBackGestureHandler.mEdgeWidthRight), this.mDisplayId);
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavigationMode = i;
        this.mEdgeBackGestureHandler.onNavigationModeChanged(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0025  */
    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onRotationProposal(int i, boolean z) {
        boolean z2;
        OverviewProxyService overviewProxyService = this.mOverviewProxyService;
        overviewProxyService.getClass();
        try {
            if (overviewProxyService.mOverviewProxy == null) {
                Log.e("OverviewProxyService", "Failed to get overview proxy for proposing rotation.");
                return;
            }
            if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
                SettingsHelper settingsHelper = overviewProxyService.mNavBarStateManager.settingsHelper;
                if (settingsHelper.isNavigationBarRotateSuggestionEnabled() && !settingsHelper.isEmergencyMode()) {
                    z2 = false;
                    if (z2) {
                        return;
                    }
                }
                z2 = true;
                if (z2) {
                }
            }
            ((IOverviewProxy.Stub.Proxy) overviewProxyService.mOverviewProxy).onRotationProposal(i, z);
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onRotationProposal()", e);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        boolean z2;
        if (!BasicRune.NAVBAR_ENABLED || this.mDisplayId == i) {
            OverviewProxyService overviewProxyService = this.mOverviewProxyService;
            overviewProxyService.getClass();
            try {
                IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                if (iOverviewProxy != null) {
                    ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onSystemBarAttributesChanged(i, i3);
                } else {
                    Log.e("OverviewProxyService", "Failed to get overview proxy for system bar attr change.");
                }
            } catch (RemoteException e) {
                Log.e("OverviewProxyService", "Failed to call onSystemBarAttributesChanged()", e);
            }
            boolean z3 = BasicRune.NAVBAR_SUPPORT_TASKBAR;
            if (z3) {
                NavBarEvents navBarEvents = new NavBarEvents();
                navBarEvents.eventType = NavBarEvents.EventType.ON_APPEARANCE_CHANGED;
                navBarEvents.appearance = i2;
                handleNavigationBarEvent(navBarEvents);
                int i5 = this.mDisplayId;
                if (z3) {
                    StringBuilder sb = new StringBuilder("onSystemBarAttributesChanged() -");
                    sb.append("  displayId:" + i5);
                    sb.append(", appearance:" + i2);
                    if (i2 != 0) {
                        sb.append(" (");
                        sb.append((i2 & 16) != 0 ? "APPEARANCE_LIGHT_NAVIGATION_BARS " : "");
                        sb.append((i2 & 2) != 0 ? "APPEARANCE_OPAQUE_NAVIGATION_BARS " : "");
                        sb.append((i2 & 64) != 0 ? "APPEARANCE_SEMI_TRANSPARENT_NAVIGATION_BARS " : "");
                        sb.append((i2 & 128) != 0 ? "APPEARANCE_LIGHT_SEMI_TRANSPARENT_NAVIGATION_BARS " : "");
                        sb.append(")");
                    }
                    sb.append(", navbarColorManagedByIme: " + z);
                    if (!str.contains("com.att")) {
                        sb.append(", packageName: ".concat(str));
                    }
                    Log.d("TaskbarDelegate", sb.toString());
                }
            }
            if (this.mAppearance != i2) {
                this.mAppearance = i2;
                z2 = updateTransitionMode(NavBarHelper.transitionMode(i2, this.mTaskbarTransientShowing));
            } else {
                z2 = false;
            }
            if (i == this.mDisplayId) {
                this.mLightBarController.onNavigationBarAppearanceChanged(i2, z3 ? this.mTransitionMode : 0, str, z2, z);
            }
            if (this.mBehavior != i3) {
                this.mBehavior = i3;
                updateSysuiFlags();
            }
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onTaskbarAutohideSuspend(boolean z) {
        if (z) {
            this.mAutoHideController.suspendAutoHide();
        } else {
            this.mAutoHideController.resumeSuspendedAutoHide();
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public final void onTaskbarSPluginButtonClicked() {
        this.mPluginTaskBar.buttonDispatcherProxy.pinButton.view.performClick();
    }

    public final void onTransientStateChanged() {
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        boolean z = this.mTaskbarTransientShowing;
        edgeBackGestureHandler.mIsNavBarShownTransiently = z;
        int transitionMode = NavBarHelper.transitionMode(this.mAppearance, z);
        if (updateTransitionMode(transitionMode)) {
            LightBarController lightBarController = this.mLightBarController;
            lightBarController.mHasLightNavigationBar = LightBarController.isLight(lightBarController.mAppearance, transitionMode, 16);
            if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
                lightBarController.mNavigationBarMode = transitionMode;
                lightBarController.reevaluate();
            }
        }
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
            if (this.mTaskbarTransientShowing) {
                this.mAutoHideController.touchAutoHide();
            }
            NavBarEvents navBarEvents = new NavBarEvents();
            navBarEvents.eventType = NavBarEvents.EventType.ON_TRANSIENT_SHOWING_CHANGED;
            navBarEvents.transientShowing = this.mTaskbarTransientShowing;
            handleNavigationBarEvent(navBarEvents);
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

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        ShadeViewController shadeViewController;
        boolean isImeShown = this.mNavBarHelper.isImeShown(i2);
        boolean z2 = false;
        if (!isImeShown) {
            isImeShown = (i2 & 8) != 0;
        }
        if (BasicRune.NAVBAR_ENABLED) {
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("setImeWindowStatus displayId=", i, " vis=", i2, " backDisposition=");
            m45m.append(i3);
            m45m.append(" showImeSwitcher=");
            m45m.append(z);
            m45m.append(" imeShown=");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(m45m, isImeShown, "TaskbarDelegate");
        }
        int calculateBackDispositionHints = Utilities.calculateBackDispositionHints(this.mNavigationIconHints, i3, isImeShown, isImeShown && z);
        if (calculateBackDispositionHints != this.mNavigationIconHints) {
            this.mNavigationIconHints = calculateBackDispositionHints;
            updateSysuiFlags();
        }
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
            this.mNavBarHelper.mLastIMEhints = calculateBackDispositionHints;
            boolean z3 = (calculateBackDispositionHints & 1) != 0;
            NavBarStateManager navBarStateManager = this.mNavBarStateManager;
            navBarStateManager.getClass();
            boolean z4 = !navBarStateManager.isGestureMode() || (navBarStateManager.settingsHelper.isNavigationBarHideKeyboardButtonEnabled() && navBarStateManager.canPlaceKeyboardButton(navBarStateManager.states.rotation));
            if (z3 && z4) {
                z2 = true;
            }
            if (!QuickStepContract.isGesturalMode(this.mNavigationMode) || (shadeViewController = ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).getShadeViewController()) == null) {
                return;
            }
            ((NotificationPanelViewController) shadeViewController).mNavBarKeyboardButtonShowing = z2;
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setWindowState(int i, int i2, int i3) {
        if (i == this.mDisplayId && i2 == 2 && this.mTaskBarWindowState != i3) {
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
        Context context = screenPinningNotify.mContext;
        if (z) {
            SysUIToast.makeText(R.string.sec_screen_pinning_start, context, 1).show();
        } else {
            SysUIToast.makeText(R.string.sec_screen_pinning_exit, context, 1).show();
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
        if (i == this.mDisplayId && (WindowInsets.Type.navigationBars() & i2) != 0) {
            if (BasicRune.NAVBAR_SUPPORT_TASKBAR || !this.mTaskbarTransientShowing) {
                this.mTaskbarTransientShowing = true;
                onTransientStateChanged();
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void toggleTaskbar() {
        IOverviewProxy iOverviewProxy = this.mOverviewProxyService.mOverviewProxy;
        if (iOverviewProxy == null) {
            return;
        }
        try {
            ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onTaskbarToggled();
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "onTaskbarToggled() failed", e);
        }
    }

    public final void updateSysuiFlags() {
        long j = this.mNavBarHelper.mA11yButtonState;
        boolean z = (j & 16) != 0;
        boolean z2 = (j & 32) != 0;
        SysUiState sysUiState = this.mSysUiState;
        sysUiState.setFlag(16L, z);
        sysUiState.setFlag(32L, z2);
        sysUiState.setFlag(262144L, (this.mNavigationIconHints & 1) != 0);
        sysUiState.setFlag(1048576L, (this.mNavigationIconHints & 4) != 0);
        sysUiState.setFlag(128L, (this.mDisabledFlags & 16777216) != 0);
        sysUiState.setFlag(256L, (this.mDisabledFlags & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0);
        sysUiState.setFlag(4194304L, (this.mDisabledFlags & com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0);
        sysUiState.setFlag(2L, !(this.mTaskBarWindowState == 0));
        sysUiState.setFlag(131072L, this.mBehavior != 2);
        sysUiState.setFlag(16777216L, this.mBehavior == 2);
        sysUiState.commitUpdate(this.mDisplayId);
    }

    public final void updateTaskbarButtonIconsAndHints() {
        Context context = this.mContext;
        boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(context) == 1;
        NavBarIconResourceMapper navBarIconResourceMapper = this.mIconResourceMapper;
        navBarIconResourceMapper.isRTL = z;
        Bundle bundle = new Bundle();
        bundle.putBoolean("defaultIcon", !navBarIconResourceMapper.themeIcon);
        putButtonBitmapsToBundle(IconType.TYPE_RECENT, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_HOME, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_BACK, bundle);
        putButtonBitmapsToBundle(IconType.TYPE_BACK_ALT, bundle);
        IconType iconType = IconType.TYPE_GESTURE_HANDLE_HINT;
        Bitmap[] bitmapFromDrawable = IconDrawableUtil.getBitmapFromDrawable(context, navBarIconResourceMapper.getGestureHandleDrawable(iconType, 0));
        Bitmap[] bitmapFromDrawable2 = IconDrawableUtil.getBitmapFromDrawable(context, navBarIconResourceMapper.getGestureHandleDrawable(iconType, 0));
        Bitmap[] bitmapFromDrawable3 = IconDrawableUtil.getBitmapFromDrawable(context, navBarIconResourceMapper.getGestureHandleDrawable(iconType, 0));
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
        SamsungPluginTaskBar samsungPluginTaskBar = this.mPluginTaskBar;
        samsungPluginTaskBar.getClass();
        for (int i = 1; i < 6; i++) {
            String m31m = LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("extra", i, "_LIGHT");
            Bundle bundle2 = samsungPluginTaskBar.pluginBundle;
            Bitmap bitmap = (Bitmap) bundle2.getParcelable(m31m);
            if (bitmap != null) {
                bundle.putParcelable("extra" + i + "_LIGHT", bitmap);
            }
            Bitmap bitmap2 = (Bitmap) bundle2.getParcelable("extra" + i + "_DARK");
            if (bitmap2 != null) {
                bundle.putParcelable("extra" + i + "_DARK", bitmap2);
            }
        }
        samsungPluginTaskBar.parseAndUpdateBundle();
        NavBarEvents navBarEvents = new NavBarEvents();
        navBarEvents.eventType = NavBarEvents.EventType.ON_UPDATE_ICON_BITMAP;
        navBarEvents.iconBitmapBundle = bundle;
        handleNavigationBarEvent(navBarEvents);
    }

    public final boolean updateTransitionMode(int i) {
        if (this.mTransitionMode == i) {
            return false;
        }
        this.mTransitionMode = i;
        AutoHideController autoHideController = this.mAutoHideController;
        if (autoHideController != null) {
            autoHideController.touchAutoHide();
        }
        if (!BasicRune.NAVBAR_SUPPORT_TASKBAR) {
            return true;
        }
        ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarTransitionModeChanged(this.mTransitionMode));
        return true;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onRecentsAnimationStateChanged(boolean z) {
    }
}
