package com.android.systemui.navigationbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManagerGlobal;
import android.widget.RemoteViews;
import android.window.DesktopExperienceFlags;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarComponent;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate.AnonymousClass4;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationBarLargeScreenUtil;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBar$$ExternalSyntheticLambda0;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.android.systemui.shared.recents.ILauncherProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.statusbar.phone.BarTransitions;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.AutoHideControllerImpl;
import com.android.systemui.statusbar.phone.AutoHideControllerStore;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.android.systemui.util.settings.SecureSettings;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class NavigationBarControllerImpl implements ConfigurationController.ConfigurationListener, NavigationModeController.ModeChangedListener, LauncherProxyService.LauncherProxyListener, Dumpable, NavigationBarController {
    public final AutoHideControllerImpl.Factory mAutoHideControllerFactory;
    public final Map mAutoHideControllers;
    public final Map mAutoHideUiElements;
    public final AnonymousClass1 mCommandQueueCallbacks;
    public final InterestingConfigChanges mConfigChanges;
    public final Context mContext;
    public final ArrayList mDesktopDisplayIds;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    public final Executor mExecutor;
    boolean mIsLargeScreen;
    boolean mIsPhone;
    public final LauncherProxyService mLauncherProxyService;
    public final NavBarHelper mNavBarHelper;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public int mNavMode;
    public final NavigationBarComponent.Factory mNavigationBarComponentFactory;
    public final ScreenPinningNotify mScreenPinningNotify;
    public final SecureSettings mSecureSettings;
    public final TaskbarDelegate mTaskbarDelegate;
    public final Map mTransientShowing;
    SparseArray<NavigationBar> mNavigationBars = new SparseArray<>();
    public final SparseBooleanArray mHasNavBar = new SparseBooleanArray();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.navigationbar.NavigationBarControllerImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements CommandQueue.Callbacks {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void abortTransient(int i, int i2) {
            boolean z = BasicRune.NAVBAR_DESKTOP;
            if (z) {
                NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
                if (navigationBarControllerImpl.mDesktopDisplayIds.contains(Integer.valueOf(i)) && (i2 & WindowInsets.Type.navigationBars()) != 0 && z && navigationBarControllerImpl.isTransientShowingForDisplay(i)) {
                    navigationBarControllerImpl.setTransientShowingForDisplay(i, false);
                    navigationBarControllerImpl.onTransientStateChangedForDisplay(i);
                }
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onDisplayAddSystemDecorations(int i) {
            boolean isTrue = DesktopExperienceFlags.ENABLE_DISPLAY_CONTENT_MODE_MANAGEMENT.isTrue();
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            if (isTrue) {
                navigationBarControllerImpl.mHasNavBar.put(i, true);
            }
            Display display = navigationBarControllerImpl.mDisplayManager.getDisplay(i);
            navigationBarControllerImpl.mIsLargeScreen = BasicRune.NAVBAR_ENABLED ? NavigationBarLargeScreenUtil.isLargeScreen(navigationBarControllerImpl.mContext) : Utilities.isLargeScreen(navigationBarControllerImpl.mContext);
            navigationBarControllerImpl.createNavigationBar(display, null, null);
            if (BasicRune.NAVBAR_DESKTOP) {
                DisplayInfo displayInfo = new DisplayInfo();
                display.getDisplayInfo(displayInfo);
                if ((displayInfo.flags & 131072) != 0) {
                    navigationBarControllerImpl.mDesktopDisplayIds.add(Integer.valueOf(i));
                    Context createDisplayContext = navigationBarControllerImpl.mContext.createDisplayContext(display);
                    int i2 = StatusBarConnectedDisplays.$r8$clinit;
                    AutoHideControllerImpl.Factory factory = navigationBarControllerImpl.mAutoHideControllerFactory;
                    AutoHideControllerImpl autoHideControllerImpl = new AutoHideControllerImpl(createDisplayContext, factory.mHandler, factory.mIWindowManager);
                    AutoHideUiElementPerDisplay autoHideUiElementPerDisplay = navigationBarControllerImpl.new AutoHideUiElementPerDisplay(i);
                    if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                        autoHideControllerImpl.registerElementToObserver(autoHideUiElementPerDisplay);
                    } else {
                        autoHideControllerImpl.mNavigationBar = autoHideUiElementPerDisplay;
                    }
                    ((HashMap) navigationBarControllerImpl.mAutoHideUiElements).put(Integer.valueOf(i), autoHideUiElementPerDisplay);
                    ((HashMap) navigationBarControllerImpl.mAutoHideControllers).put(Integer.valueOf(i), autoHideControllerImpl);
                    ((HashMap) navigationBarControllerImpl.mTransientShowing).put(Integer.valueOf(i), Optional.of(Boolean.FALSE));
                }
                if (navigationBarControllerImpl.mTaskbarDelegate.mInitialized) {
                    return;
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onDisplayAddSystemDecorations id=", "NavigationBarControllerImpl");
                try {
                    ILauncherProxy iLauncherProxy = navigationBarControllerImpl.mLauncherProxyService.mLauncherProxy;
                    if (iLauncherProxy != null) {
                        ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onDisplayAddSystemDecorations(i);
                    }
                } catch (RemoteException e) {
                    Log.e("NavigationBarControllerImpl", "onDisplayAddSystemDecorations() failed", e);
                }
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onDisplayRemoveSystemDecorations(int i) {
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            navigationBarControllerImpl.removeNavigationBar(i);
            navigationBarControllerImpl.mHasNavBar.delete(i);
            if (BasicRune.NAVBAR_DESKTOP) {
                if (navigationBarControllerImpl.mDesktopDisplayIds.contains(Integer.valueOf(i))) {
                    navigationBarControllerImpl.mDesktopDisplayIds.remove(Integer.valueOf(i));
                    navigationBarControllerImpl.removeAutoHideControllerForDisplay(i);
                }
                if (navigationBarControllerImpl.mTaskbarDelegate.mInitialized) {
                    return;
                }
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onDisplayRemoveSystemDecorations id=", "NavigationBarControllerImpl");
                try {
                    ILauncherProxy iLauncherProxy = navigationBarControllerImpl.mLauncherProxyService.mLauncherProxy;
                    if (iLauncherProxy != null) {
                        ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onDisplayRemoveSystemDecorations(i);
                    }
                } catch (RemoteException e) {
                    Log.e("NavigationBarControllerImpl", "onDisplayRemoveSystemDecorations() failed", e);
                }
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onDisplayRemoved(int i) {
            onDisplayRemoveSystemDecorations(i);
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
            if (BasicRune.NAVBAR_DESKTOP) {
                NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
                if (navigationBarControllerImpl.mDesktopDisplayIds.contains(Integer.valueOf(i))) {
                    navigationBarControllerImpl.mLauncherProxyService.onSystemBarAttributesChanged(i, i3);
                    TaskbarDelegate taskbarDelegate = navigationBarControllerImpl.mTaskbarDelegate;
                    taskbarDelegate.getClass();
                    NavBarEvents navBarEvents = new NavBarEvents();
                    navBarEvents.eventType = NavBarEvents.EventType.ON_APPEARANCE_CHANGED;
                    navBarEvents.appearance = (-2097153) & i2;
                    navBarEvents.displayId = i;
                    try {
                        ILauncherProxy iLauncherProxy = taskbarDelegate.mLauncherProxyService.mLauncherProxy;
                        if (iLauncherProxy != null) {
                            ((ILauncherProxy.Stub.Proxy) iLauncherProxy).handleNavigationBarEvent(navBarEvents);
                        }
                    } catch (RemoteException e) {
                        Log.e("TaskbarDelegate", "Failed to call handleNavigationBarEvent()", e);
                    }
                    TaskbarDelegate.loggingAttributesChanged(str, i2, i, z);
                }
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void resetScheduleAutoHide() {
            int i = 0;
            while (true) {
                NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
                if (i >= navigationBarControllerImpl.mNavigationBars.size()) {
                    return;
                }
                NavigationBar valueAt = navigationBarControllerImpl.mNavigationBars.valueAt(i);
                valueAt.getClass();
                Log.d("NavigationBar", "resetAutoHide()");
                valueAt.mAutoHideController.touchAutoHide();
                i++;
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
            NavigationBar navigationBar = NavigationBarControllerImpl.this.getNavigationBar(i);
            if (navigationBar != null) {
                RegionSamplingHelper regionSamplingHelper = navigationBar.mRegionSamplingHelper;
                if (z) {
                    regionSamplingHelper.start(navigationBar.mSamplingBounds);
                } else {
                    regionSamplingHelper.stop();
                }
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) {
            StringBuilder sb = new StringBuilder("setNavigationBarShortcut requestClass : ");
            sb.append(str);
            sb.append(", remoteViews : ");
            sb.append(remoteViews);
            sb.append(", position : ");
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(sb, i, ", priority : ", i2, "NavigationBarControllerImpl");
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            ((NavBarStoreImpl) navigationBarControllerImpl.mNavBarStore).handleEvent(navigationBarControllerImpl, new EventTypeFactory.EventType.OnSetRemoteView(str, remoteViews, i, i2));
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void showPinningEnterExitToast(boolean z) {
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            int displayId = navigationBarControllerImpl.mContext.getDisplayId();
            NavigationBarView navigationBarView = navigationBarControllerImpl.getNavigationBarView(displayId);
            if (navigationBarView != null) {
                if (z) {
                    SysUIToast.makeText(navigationBarView.mScreenPinningNotify.mContext, R.string.sec_screen_pinning_start, 1).show();
                    return;
                } else {
                    SysUIToast.makeText(navigationBarView.mScreenPinningNotify.mContext, R.string.sec_screen_pinning_exit, 1).show();
                    return;
                }
            }
            boolean z2 = BasicRune.NAVBAR_AOSP_BUG_FIX;
            TaskbarDelegate taskbarDelegate = navigationBarControllerImpl.mTaskbarDelegate;
            if (!z2 && displayId == 0 && taskbarDelegate.mInitialized) {
                taskbarDelegate.showPinningEnterExitToast(z);
                return;
            }
            if (!BasicRune.NAVBAR_ENABLED_HARD_KEY || taskbarDelegate.mInitialized) {
                return;
            }
            if (z) {
                SysUIToast.makeText(navigationBarControllerImpl.mScreenPinningNotify.mContext, R.string.sec_screen_pinning_start, 1).show();
            } else {
                SysUIToast.makeText(navigationBarControllerImpl.mScreenPinningNotify.mContext, R.string.sec_screen_pinning_exit, 1).show();
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void showPinningEscapeToast() {
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            int displayId = navigationBarControllerImpl.mContext.getDisplayId();
            NavigationBarView navigationBarView = navigationBarControllerImpl.getNavigationBarView(displayId);
            TaskbarDelegate taskbarDelegate = navigationBarControllerImpl.mTaskbarDelegate;
            if (navigationBarView != null && !taskbarDelegate.mInitialized) {
                navigationBarView.showPinningEscapeToast();
                return;
            }
            if (!BasicRune.NAVBAR_AOSP_BUG_FIX && displayId == 0 && taskbarDelegate.mInitialized) {
                taskbarDelegate.showPinningEscapeToast();
            } else {
                if (!BasicRune.NAVBAR_ENABLED_HARD_KEY || taskbarDelegate.mInitialized) {
                    return;
                }
                navigationBarControllerImpl.mScreenPinningNotify.showEscapeToast(false, true);
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void showTransient(int i, int i2, boolean z) {
            if (BasicRune.NAVBAR_DESKTOP) {
                NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
                if (!navigationBarControllerImpl.mDesktopDisplayIds.contains(Integer.valueOf(i)) || (i2 & WindowInsets.Type.navigationBars()) == 0 || navigationBarControllerImpl.isTransientShowingForDisplay(i)) {
                    return;
                }
                navigationBarControllerImpl.setTransientShowingForDisplay(i, true);
                navigationBarControllerImpl.onTransientStateChangedForDisplay(i);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AutoHideUiElementPerDisplay implements AutoHideUiElement {
        public final int displayId;

        public AutoHideUiElementPerDisplay(int i) {
            this.displayId = i;
        }

        @Override // com.android.systemui.statusbar.AutoHideUiElement
        public final void hide() {
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            navigationBarControllerImpl.getClass();
            if (BasicRune.NAVBAR_DESKTOP) {
                int i = this.displayId;
                if (navigationBarControllerImpl.isTransientShowingForDisplay(i)) {
                    navigationBarControllerImpl.setTransientShowingForDisplay(i, false);
                    navigationBarControllerImpl.onTransientStateChangedForDisplay(i);
                }
            }
        }

        @Override // com.android.systemui.statusbar.AutoHideUiElement
        public final boolean isVisible() {
            return NavigationBarControllerImpl.this.isTransientShowingForDisplay(this.displayId);
        }

        @Override // com.android.systemui.statusbar.AutoHideUiElement
        public final void synchronizeState() {
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            for (Display display : ((DisplayTrackerImpl) navigationBarControllerImpl.mDisplayTracker).displayManager.getDisplays()) {
                navigationBarControllerImpl.checkNavBarModes(display.getDisplayId());
            }
        }
    }

    public NavigationBarControllerImpl(Context context, LauncherProxyService launcherProxyService, NavigationModeController navigationModeController, SysUiState sysUiState, CommandQueue commandQueue, Executor executor, ConfigurationController configurationController, NavBarHelper navBarHelper, TaskbarDelegate taskbarDelegate, NavigationBarComponent.Factory factory, DumpManager dumpManager, AutoHideControllerStore autoHideControllerStore, AutoHideControllerImpl.Factory factory2, LightBarController lightBarController, TaskStackChangeListeners taskStackChangeListeners, Optional<Pip> optional, Optional<BackAnimationController.BackAnimationImpl> optional2, SecureSettings secureSettings, DisplayTracker displayTracker, DeviceStateManager deviceStateManager, NavBarStore navBarStore) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(1073741824);
        this.mConfigChanges = interestingConfigChanges;
        this.mDesktopDisplayIds = new ArrayList();
        this.mAutoHideControllers = new HashMap();
        this.mAutoHideUiElements = new HashMap();
        this.mTransientShowing = new HashMap();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mCommandQueueCallbacks = anonymousClass1;
        this.mContext = context;
        this.mExecutor = executor;
        this.mNavigationBarComponentFactory = factory;
        this.mSecureSettings = secureSettings;
        this.mDisplayTracker = displayTracker;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        commandQueue.addCallback((CommandQueue.Callbacks) anonymousClass1);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        interestingConfigChanges.applyNewConfig(context.getResources());
        this.mNavMode = navigationModeController.addListener(this);
        this.mNavBarHelper = navBarHelper;
        this.mTaskbarDelegate = taskbarDelegate;
        AutoHideController autoHideController = (AutoHideController) autoHideControllerStore.forDisplay(context.getDisplayId());
        BackAnimationController.BackAnimationImpl orElse = optional2.orElse(null);
        taskbarDelegate.mCommandQueue = commandQueue;
        taskbarDelegate.mLauncherProxyService = launcherProxyService;
        taskbarDelegate.mNavBarHelper = navBarHelper;
        taskbarDelegate.mNavigationModeController = navigationModeController;
        taskbarDelegate.mSysUiState = sysUiState;
        dumpManager.registerDumpable(taskbarDelegate);
        taskbarDelegate.mAutoHideController = autoHideController;
        taskbarDelegate.mLightBarController = lightBarController;
        taskbarDelegate.mPipOptional = optional;
        taskbarDelegate.mBackAnimation = orElse;
        taskbarDelegate.mLightBarTransitionsController = taskbarDelegate.mLightBarTransitionsControllerFactory.create(taskbarDelegate.new AnonymousClass4());
        taskbarDelegate.mTaskStackChangeListeners = taskStackChangeListeners;
        Context context2 = navBarHelper.mContext;
        taskbarDelegate.mEdgeBackGestureHandler = (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && context2.getDisplayId() == 1) ? navBarHelper.mEdgeBackGestureHandlerFactory.create(context2, navBarHelper.mWindowManager) : navBarHelper.mEdgeBackGestureHandler;
        taskbarDelegate.mDisplayTracker = displayTracker;
        boolean z = BasicRune.NAVBAR_ENABLED;
        this.mIsLargeScreen = z ? NavigationBarLargeScreenUtil.isLargeScreen(context) : Utilities.isLargeScreen(context);
        this.mIsPhone = !Utils.isDeviceFoldable(context.getResources(), deviceStateManager);
        dumpManager.registerDumpable(this);
        if (z) {
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(context.getDisplayId());
            this.mLauncherProxyService = launcherProxyService;
        }
        if (BasicRune.NAVBAR_DESKTOP) {
            this.mAutoHideControllerFactory = factory2;
            this.mLauncherProxyService.addCallback((LauncherProxyService.LauncherProxyListener) this);
        }
        if (BasicRune.NAVBAR_ENABLED_HARD_KEY) {
            this.mScreenPinningNotify = new ScreenPinningNotify(context);
        }
    }

    public final void checkNavBarModes(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.checkNavBarModes();
            return;
        }
        ILauncherProxy iLauncherProxy = this.mTaskbarDelegate.mLauncherProxyService.mLauncherProxy;
        if (iLauncherProxy == null) {
            return;
        }
        try {
            ((ILauncherProxy.Stub.Proxy) iLauncherProxy).checkNavBarModes(i);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "checkNavBarModes() failed", e);
        }
    }

    public void createNavigationBar(final Display display, Bundle bundle, final RegisterStatusBarResult registerStatusBarResult) {
        if (display == null) {
            return;
        }
        int displayId = display.getDisplayId();
        this.mDisplayTracker.getClass();
        boolean z = displayId == 0;
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        boolean z2 = (displayInfo.flags & 131072) != 0;
        if (z2) {
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("Skip createNavigationBar displayId=", displayId, " isDexDisplay=", z2, "NavigationBarControllerImpl");
            return;
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            boolean z3 = (display.getFlags() & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0;
            if (z3) {
                KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("Skip createNavigationBar displayId=", displayId, " isCoverDisplay=", z3, "NavigationBarControllerImpl");
                return;
            }
        }
        if (shouldCreateNavBarAndTaskBar(displayId)) {
            if (z && initializeTaskbarIfNecessary() && !BasicRune.NAVBAR_POLICY_VISIBILITY) {
                return;
            }
            Context createDisplayContext = z ? this.mContext : this.mContext.createDisplayContext(display);
            boolean z4 = BasicRune.NAVBAR_ENABLED;
            if (z4) {
                ((NavBarStoreImpl) this.mNavBarStore).initDisplayDependenciesIfNeeded(displayId, createDisplayContext);
            }
            final NavigationBar navigationBar = ((DaggerReferenceGlobalRootComponent.NavigationBarComponentImpl) this.mNavigationBarComponentFactory.create(createDisplayContext, bundle)).getNavigationBar();
            navigationBar.init();
            this.mNavigationBars.put(displayId, navigationBar);
            navigationBar.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this) { // from class: com.android.systemui.navigationbar.NavigationBarControllerImpl.2
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    if (registerStatusBarResult != null) {
                        NavigationBar navigationBar2 = navigationBar;
                        int displayId2 = display.getDisplayId();
                        RegisterStatusBarResult registerStatusBarResult2 = registerStatusBarResult;
                        navigationBar2.setImeWindowStatus(displayId2, registerStatusBarResult2.mImeWindowVis, registerStatusBarResult2.mImeBackDisposition, registerStatusBarResult2.mShowImeSwitcher);
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    view.removeOnAttachStateChangeListener(this);
                }
            });
            if (z4) {
                sendConfigChangeEvent(this.mContext.getResources().getConfiguration());
            }
        }
    }

    public final void disableAnimationsDuringHide(int i, long j) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.disableAnimationsDuringHide(j);
        }
    }

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
            StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("isSimplifiedGesture="), BasicRune.NAVBAR_SIMPLIFIED_GESTURE, printWriter, "isSupportSearcle="), BasicRune.NAVBAR_SUPPORT_SEARCLE, printWriter, "isSupportLegacyGestureOptions=");
            m.append(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME").contains("SupportLegacyGestureOptions"));
            printWriter.println(m.toString());
            StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("isSupportLegacyGestureOptions(by NavStar)="), (Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATIONBAR_SPLUGIN_FLAGS, 0) & 4) != 0, printWriter, "the number of connected displays=");
            m2.append(this.mDesktopDisplayIds.size());
            printWriter.println(m2.toString());
            ArrayList arrayList = this.mDesktopDisplayIds;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  displayId=", ((Integer) obj).intValue(), printWriter);
            }
        }
        MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("mIsLargeScreen="), this.mIsLargeScreen, printWriter, "mNavMode="), this.mNavMode, printWriter);
        for (int i2 = 0; i2 < this.mNavigationBars.size(); i2++) {
            if (i2 > 0) {
                printWriter.println();
            }
            this.mNavigationBars.valueAt(i2).dump(printWriter);
        }
    }

    public final void finishBarAnimations(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            BarTransitions.BarBackgroundDrawable barBackgroundDrawable = navigationBar.mNavigationBarTransitions.mBarBackground;
            if (barBackgroundDrawable.mAnimating) {
                barBackgroundDrawable.mAnimating = false;
                barBackgroundDrawable.invalidateSelf();
                return;
            }
            return;
        }
        ILauncherProxy iLauncherProxy = this.mTaskbarDelegate.mLauncherProxyService.mLauncherProxy;
        if (iLauncherProxy == null) {
            return;
        }
        try {
            ((ILauncherProxy.Stub.Proxy) iLauncherProxy).finishBarAnimations(i);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "finishBarAnimations() failed", e);
        }
    }

    public final void forceRepositionCoverNavigationBar(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(1);
        if (navigationBar != null) {
            navigationBar.repositionNavigationBar(i);
        }
    }

    public final NavigationBar getDefaultNavigationBar() {
        SparseArray<NavigationBar> sparseArray = this.mNavigationBars;
        this.mDisplayTracker.getClass();
        return sparseArray.get(0);
    }

    public final NavigationBar getNavigationBar(int i) {
        return this.mNavigationBars.get(i);
    }

    public final NavigationBarView getNavigationBarView(int i) {
        NavigationBar navigationBar = getNavigationBar(i);
        if (navigationBar == null) {
            return null;
        }
        return navigationBar.getView();
    }

    public final boolean initializeTaskbarIfNecessary() {
        boolean z;
        boolean z2 = BasicRune.NAVBAR_ENABLED;
        TaskbarDelegate taskbarDelegate = this.mTaskbarDelegate;
        NavBarHelper navBarHelper = this.mNavBarHelper;
        if (!z2) {
            z = supportsTaskbar() && shouldCreateNavBarAndTaskBar(this.mContext.getDisplayId());
            if (!z) {
                taskbarDelegate.destroy();
                return z;
            }
            Trace.beginSection("NavigationBarController#initializeTaskbarIfNecessary");
            int displayId = this.mContext.getDisplayId();
            navBarHelper.mTogglingNavbarTaskbar = this.mNavigationBars.contains(displayId);
            removeNavigationBar(displayId);
            taskbarDelegate.init(displayId);
            navBarHelper.mTogglingNavbarTaskbar = false;
            Trace.endSection();
            return z;
        }
        int displayId2 = this.mContext.getDisplayId();
        boolean z3 = BasicRune.NAVBAR_TASKBAR;
        z = z3 && ((NavBarStateManagerImpl) this.mNavBarStateManager).isTaskBarEnabled(true);
        if (z) {
            Trace.beginSection("NavigationBarController#initializeTaskbarIfNecessary");
            navBarHelper.mTogglingNavbarTaskbar = this.mNavigationBars.contains(displayId2);
            if (!BasicRune.NAVBAR_POLICY_VISIBILITY) {
                removeNavigationBar(displayId2);
            }
            taskbarDelegate.init(displayId2);
            navBarHelper.mTogglingNavbarTaskbar = false;
            Trace.endSection();
        } else {
            taskbarDelegate.destroy();
        }
        if (z3) {
            try {
                ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
                if (iLauncherProxy != null) {
                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy).isTaskbarEnabled(z);
                    return z;
                }
            } catch (Exception e) {
                Log.e("NavigationBarControllerImpl", "An error occurred in initializeTaskbarIfNecessary(): ");
                e.printStackTrace();
            }
        }
        return z;
    }

    public final boolean isTransientShowingForDisplay(int i) {
        Optional optional;
        if (BasicRune.NAVBAR_DESKTOP && this.mTransientShowing.containsKey(Integer.valueOf(i)) && (optional = (Optional) this.mTransientShowing.get(Integer.valueOf(i))) != null) {
            return ((Boolean) optional.orElse(Boolean.FALSE)).booleanValue();
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean z = BasicRune.NAVBAR_ENABLED;
        if (z && this.mContext.getUserId() != 0) {
            Log.d("NavigationBarControllerImpl", "Skip onConfigChanged for userId=" + this.mContext.getUserId());
            return;
        }
        boolean z2 = this.mIsLargeScreen;
        Context context = this.mContext;
        this.mIsLargeScreen = z ? NavigationBarLargeScreenUtil.isLargeScreen(context) : Utilities.isLargeScreen(context);
        if (z) {
            sendConfigChangeEvent(configuration);
        }
        boolean applyNewConfig = this.mConfigChanges.applyNewConfig(this.mContext.getResources());
        int i = 0;
        boolean z3 = this.mIsLargeScreen != z2;
        if (BasicRune.NAVBAR_ADDITIONAL_LOG && z3) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("NavbarController: largeScreenChanged: old=", ", new=", z2), this.mIsLargeScreen, "NoBackGesture");
        }
        StringBuilder sb = new StringBuilder("NavbarController: newConfig=");
        sb.append(configuration);
        sb.append(" mTaskbarDelegate initialized=");
        TaskbarDelegate taskbarDelegate = this.mTaskbarDelegate;
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, taskbarDelegate.mInitialized, " willApplyConfigToNavbars=", applyNewConfig, " navBarCount=");
        sb.append(this.mNavigationBars.size());
        sb.append(" largeScreenChanged=");
        sb.append(z3);
        Log.i("NoBackGesture", sb.toString());
        boolean z4 = BasicRune.NAVBAR_TASKBAR;
        if (z4 && taskbarDelegate.mInitialized) {
            if (!BasicRune.NAVBAR_POLICY_VISIBILITY) {
                taskbarDelegate.configurationChanged(taskbarDelegate.mWindowContext.getResources(), configuration);
            }
            if (z4) {
                taskbarDelegate.updateTaskbarButtonIconsAndHints();
            }
        }
        if (z3 && updateNavbarForTaskbar() && !BasicRune.NAVBAR_POLICY_VISIBILITY) {
            return;
        }
        if (!applyNewConfig) {
            while (i < this.mNavigationBars.size()) {
                this.mNavigationBars.valueAt(i).onConfigurationChanged(configuration);
                i++;
            }
            return;
        }
        while (i < this.mNavigationBars.size()) {
            int keyAt = this.mNavigationBars.keyAt(i);
            Bundle bundle = new Bundle();
            NavigationBar navigationBar = this.mNavigationBars.get(keyAt);
            if (navigationBar != null) {
                bundle.putInt("disabled_state", navigationBar.mDisabledFlags1);
                bundle.putInt("disabled2_state", navigationBar.mDisabledFlags2);
                bundle.putInt("appearance", navigationBar.mAppearance);
                bundle.putInt("behavior", navigationBar.mBehavior);
                bundle.putBoolean("transient_state", navigationBar.mTransientShown);
                if (BasicRune.NAVBAR_ENABLED) {
                    bundle.putInt("icon_hints", navigationBar.mNavbarFlags);
                }
                LightBarTransitionsController lightBarTransitionsController = navigationBar.mNavigationBarTransitions.mLightTransitionsController;
                ValueAnimator valueAnimator = lightBarTransitionsController.mTintAnimator;
                bundle.putFloat("dark_intensity", (valueAnimator == null || !valueAnimator.isRunning()) ? lightBarTransitionsController.mDarkIntensity : lightBarTransitionsController.mNextDarkIntensity);
            }
            removeNavigationBar(keyAt);
            createNavigationBar(this.mDisplayManager.getDisplay(keyAt), bundle, null);
            i++;
        }
    }

    @Override // com.android.systemui.recents.LauncherProxyService.LauncherProxyListener
    public final void onConnectionChanged(boolean z) {
        boolean z2 = BasicRune.NAVBAR_DESKTOP;
        if (!z2 || this.mDisplayManager == null) {
            return;
        }
        if (z2 && !this.mDesktopDisplayIds.isEmpty()) {
            ArrayList arrayList = this.mDesktopDisplayIds;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                removeAutoHideControllerForDisplay(((Integer) obj).intValue());
            }
            this.mDesktopDisplayIds.clear();
        }
        Display[] displays = this.mDisplayManager.getDisplays();
        DisplayInfo displayInfo = new DisplayInfo();
        for (Display display : displays) {
            display.getDisplayInfo(displayInfo);
            if ((displayInfo.flags & 131072) != 0) {
                this.mCommandQueueCallbacks.onDisplayAddSystemDecorations(display.getDisplayId());
            }
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        final int i2 = this.mNavMode;
        if (i2 == i) {
            return;
        }
        this.mNavMode = i;
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarControllerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
                if (i2 != navigationBarControllerImpl.mNavMode) {
                    navigationBarControllerImpl.updateNavbarForTaskbar();
                }
                for (int i3 = 0; i3 < navigationBarControllerImpl.mNavigationBars.size(); i3++) {
                    NavigationBar valueAt = navigationBarControllerImpl.mNavigationBars.valueAt(i3);
                    if (valueAt != null) {
                        valueAt.getView().updateStates();
                    }
                }
            }
        });
    }

    public final void onTransientStateChangedForDisplay(int i) {
        if (BasicRune.NAVBAR_DESKTOP) {
            boolean isTransientShowingForDisplay = isTransientShowingForDisplay(i);
            AutoHideController autoHideController = (AutoHideController) ((HashMap) this.mAutoHideControllers).get(Integer.valueOf(i));
            if (isTransientShowingForDisplay && autoHideController != null) {
                ((AutoHideControllerImpl) autoHideController).touchAutoHide();
            }
            NavBarEvents navBarEvents = new NavBarEvents();
            navBarEvents.eventType = NavBarEvents.EventType.ON_TRANSIENT_SHOWING_CHANGED;
            navBarEvents.transientShowing = isTransientShowingForDisplay;
            navBarEvents.displayId = i;
            try {
                ILauncherProxy iLauncherProxy = this.mLauncherProxyService.mLauncherProxy;
                if (iLauncherProxy != null) {
                    ((ILauncherProxy.Stub.Proxy) iLauncherProxy).handleNavigationBarEvent(navBarEvents);
                }
            } catch (RemoteException e) {
                Log.e("NavigationBarControllerImpl", "Failed to call sendNavBarEvent()", e);
            }
        }
    }

    public final void removeAutoHideControllerForDisplay(int i) {
        AutoHideController autoHideController = (AutoHideController) ((HashMap) this.mAutoHideControllers).get(Integer.valueOf(i));
        if (autoHideController != null) {
            AutoHideUiElement autoHideUiElement = (AutoHideUiElement) ((HashMap) this.mAutoHideUiElements).get(Integer.valueOf(i));
            if (autoHideUiElement != null) {
                if (BasicRune.NAVBAR_POLICY_VISIBILITY) {
                    AutoHideControllerImpl.AutoHideUiElementObserver autoHideUiElementObserver = ((AutoHideControllerImpl) autoHideController).mObserver;
                    autoHideUiElementObserver.getClass();
                    ((ArrayList) autoHideUiElementObserver.mList).remove(autoHideUiElement);
                } else {
                    ((AutoHideControllerImpl) autoHideController).mNavigationBar = null;
                }
            }
            ((HashMap) this.mAutoHideUiElements).remove(Integer.valueOf(i));
            ((HashMap) this.mAutoHideControllers).remove(Integer.valueOf(i));
            ((HashMap) this.mTransientShowing).remove(Integer.valueOf(i));
        }
    }

    public final void removeNavigationBar(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.destroyView();
            this.mNavigationBars.remove(i);
        }
    }

    public final void sendConfigChangeEvent(Configuration configuration) {
        for (int i = 0; i < this.mNavigationBars.size(); i++) {
            Context context = this.mNavigationBars.valueAt(i).mContext;
            Resources resources = context.getResources();
            EventTypeFactory.EventType.OnConfigChanged onConfigChanged = new EventTypeFactory.EventType.OnConfigChanged(configuration);
            int displayId = context.getDisplayId();
            NavBarStore navBarStore = this.mNavBarStore;
            navBarStore.handleEvent(this, onConfigChanged, displayId);
            navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarConfigChanged(this.mIsLargeScreen ? false : resources.getBoolean(android.R.bool.config_safe_media_volume_enabled), true ^ this.mIsLargeScreen, this.mIsLargeScreen ? true : resources.getBoolean(android.R.bool.config_secondaryBuiltInDisplayIsRound), resources.getInteger(android.R.integer.config_screenTimeoutOverride)), context.getDisplayId());
        }
    }

    public final void setTransientShowingForDisplay(int i, boolean z) {
        if (BasicRune.NAVBAR_DESKTOP) {
            if (((HashMap) this.mTransientShowing).containsKey(Integer.valueOf(i))) {
                KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("setTransientShowingForDisplay:", i, " : ", z, "NavigationBarControllerImpl");
                ((HashMap) this.mTransientShowing).put(Integer.valueOf(i), Optional.of(Boolean.valueOf(z)));
            }
        }
    }

    public final boolean shouldCreateNavBarAndTaskBar(int i) {
        if (!BasicRune.NAVBAR_ENABLED_HARD_KEY && this.mHasNavBar.indexOfKey(i) > -1) {
            return this.mHasNavBar.get(i);
        }
        try {
            boolean hasNavigationBar = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(i);
            this.mHasNavBar.put(i, hasNavigationBar);
            return hasNavigationBar;
        } catch (RemoteException unused) {
            Log.w("NavigationBarControllerImpl", "Cannot get WindowManager.");
            return false;
        }
    }

    public boolean supportsTaskbar() {
        return true;
    }

    public final void touchAutoDim(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        boolean z = true;
        if (navigationBar != null) {
            navigationBar.mNavigationBarTransitions.setAutoDim(false);
            Handler handler = navigationBar.mHandler;
            NavigationBar$$ExternalSyntheticLambda0 navigationBar$$ExternalSyntheticLambda0 = navigationBar.mAutoDim;
            handler.removeCallbacks(navigationBar$$ExternalSyntheticLambda0);
            int state = navigationBar.mStatusBarStateController.getState();
            if (state == 1 || state == 2) {
                return;
            }
            handler.postDelayed(navigationBar$$ExternalSyntheticLambda0, 2250L);
            return;
        }
        TaskbarDelegate taskbarDelegate = this.mTaskbarDelegate;
        if (taskbarDelegate.mLauncherProxyService.mLauncherProxy == null) {
            return;
        }
        try {
            int state2 = taskbarDelegate.mStatusBarStateController.getState();
            if (state2 == 1 || state2 == 2) {
                z = false;
            }
            ((ILauncherProxy.Stub.Proxy) taskbarDelegate.mLauncherProxyService.mLauncherProxy).touchAutoDim(i, z);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "touchAutoDim() failed", e);
        }
    }

    public final void transitionTo(int i, int i2) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.mNavigationBarTransitions.transitionTo(i2, true);
            return;
        }
        ILauncherProxy iLauncherProxy = this.mTaskbarDelegate.mLauncherProxyService.mLauncherProxy;
        if (iLauncherProxy == null) {
            return;
        }
        try {
            ((ILauncherProxy.Stub.Proxy) iLauncherProxy).transitionTo(i, i2);
        } catch (RemoteException e) {
            Log.e("TaskbarDelegate", "transitionTo() failed, barMode: " + i2, e);
        }
    }

    public final boolean updateNavbarForTaskbar() {
        boolean initializeTaskbarIfNecessary = initializeTaskbarIfNecessary();
        if (initializeTaskbarIfNecessary || this.mNavigationBars.get(this.mContext.getDisplayId()) != null) {
            if (BasicRune.NAVBAR_ENABLED_HARD_KEY && !initializeTaskbarIfNecessary && !QuickStepContract.isGesturalMode(this.mNavMode) && this.mNavigationBars.get(this.mContext.getDisplayId()) != null) {
                removeNavigationBar(this.mContext.getDisplayId());
            }
        } else if (!BasicRune.NAVBAR_POLICY_VISIBILITY) {
            createNavigationBar(this.mContext.getDisplay(), null, null);
            return initializeTaskbarIfNecessary;
        }
        return initializeTaskbarIfNecessary;
    }
}
