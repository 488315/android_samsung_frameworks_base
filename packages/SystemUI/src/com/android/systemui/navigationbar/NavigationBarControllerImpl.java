package com.android.systemui.navigationbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import android.view.View;
import android.view.WindowManagerGlobal;
import android.widget.RemoteViews;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
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
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.android.wm.shell.Flags;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.pip.Pip;
import com.samsung.android.feature.SemFloatingFeature;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.concurrent.Executor;

public final class NavigationBarControllerImpl implements ConfigurationController.ConfigurationListener, NavigationModeController.ModeChangedListener, Dumpable, NavigationBarController {
    public final AnonymousClass1 mCommandQueueCallbacks;
    public final InterestingConfigChanges mConfigChanges;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    public final Executor mExecutor;
    boolean mIsLargeScreen;
    boolean mIsPhone;
    public final NavBarHelper mNavBarHelper;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public int mNavMode;
    public final NavigationBarComponent.Factory mNavigationBarComponentFactory;
    public final OverviewProxyService mOverviewProxyService;
    public final ScreenPinningNotify mScreenPinningNotify;
    public final SecureSettings mSecureSettings;
    public final TaskbarDelegate mTaskbarDelegate;
    SparseArray<NavigationBar> mNavigationBars = new SparseArray<>();
    public final SparseBooleanArray mHasNavBar = new SparseBooleanArray();

    /* renamed from: com.android.systemui.navigationbar.NavigationBarControllerImpl$1, reason: invalid class name */
    public final class AnonymousClass1 implements CommandQueue.Callbacks {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onDisplayReady(int i) {
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            Display display = navigationBarControllerImpl.mDisplayManager.getDisplay(i);
            navigationBarControllerImpl.mIsLargeScreen = BasicRune.NAVBAR_ENABLED ? !navigationBarControllerImpl.mContext.getResources().getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider) : Utilities.isLargeScreen(navigationBarControllerImpl.mContext);
            navigationBarControllerImpl.createNavigationBar(display, null, null);
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void onDisplayRemoved(int i) {
            NavigationBarControllerImpl navigationBarControllerImpl = NavigationBarControllerImpl.this;
            navigationBarControllerImpl.removeNavigationBar(i);
            navigationBarControllerImpl.mHasNavBar.delete(i);
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
            NavigationBar navigationBar = NavigationBarControllerImpl.this.mNavigationBars.get(i);
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
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, i, ", priority : ", i2, "NavigationBarControllerImpl");
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
            if (navigationBarView != null && !navigationBarControllerImpl.mTaskbarDelegate.mInitialized) {
                navigationBarView.showPinningEscapeToast();
                return;
            }
            if (!BasicRune.NAVBAR_AOSP_BUG_FIX && displayId == 0) {
                TaskbarDelegate taskbarDelegate = navigationBarControllerImpl.mTaskbarDelegate;
                if (taskbarDelegate.mInitialized) {
                    taskbarDelegate.showPinningEscapeToast();
                    return;
                }
            }
            if (!BasicRune.NAVBAR_ENABLED_HARD_KEY || navigationBarControllerImpl.mTaskbarDelegate.mInitialized) {
                return;
            }
            navigationBarControllerImpl.mScreenPinningNotify.showEscapeToast(false, true);
        }
    }

    public NavigationBarControllerImpl(Context context, OverviewProxyService overviewProxyService, NavigationModeController navigationModeController, SysUiState sysUiState, CommandQueue commandQueue, Executor executor, ConfigurationController configurationController, NavBarHelper navBarHelper, TaskbarDelegate taskbarDelegate, NavigationBarComponent.Factory factory, DumpManager dumpManager, AutoHideController autoHideController, LightBarController lightBarController, TaskStackChangeListeners taskStackChangeListeners, Optional<Pip> optional, Optional<BackAnimationController.BackAnimationImpl> optional2, SecureSettings secureSettings, DisplayTracker displayTracker, NavBarStore navBarStore) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(1073741824);
        this.mConfigChanges = interestingConfigChanges;
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
        BackAnimationController.BackAnimationImpl orElse = optional2.orElse(null);
        taskbarDelegate.mCommandQueue = commandQueue;
        taskbarDelegate.mOverviewProxyService = overviewProxyService;
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
        taskbarDelegate.mEdgeBackGestureHandler = navBarHelper.getEdgeBackGestureHandler(navBarHelper.mContext);
        boolean z = BasicRune.NAVBAR_ENABLED;
        this.mIsLargeScreen = z ? !context.getResources().getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider) : Utilities.isLargeScreen(context);
        this.mIsPhone = context.getResources().getIntArray(android.R.array.preloaded_freeform_multi_window_drawables).length == 0;
        dumpManager.registerDumpable(this);
        if (z) {
            this.mNavBarStore = navBarStore;
            this.mNavBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(context.getDisplayId());
            this.mOverviewProxyService = overviewProxyService;
        }
        if (BasicRune.NAVBAR_ENABLED_HARD_KEY) {
            this.mScreenPinningNotify = new ScreenPinningNotify(context);
        }
    }

    public final void checkNavBarModes(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.checkNavBarModes();
        }
    }

    public void createNavigationBar(final Display display, Bundle bundle, final RegisterStatusBarResult registerStatusBarResult) {
        if (display == null) {
            return;
        }
        int displayId = display.getDisplayId();
        this.mDisplayTracker.getClass();
        boolean z = displayId == 0;
        boolean z2 = displayId == 2;
        if (z2) {
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("Skip createNavigationBar displayId=", displayId, " isDexDisplay=", z2, "NavigationBarControllerImpl");
            return;
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            boolean z3 = (display.getFlags() & 524288) != 0;
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
            if (BasicRune.NAVBAR_ENABLED) {
                ((NavBarStoreImpl) this.mNavBarStore).initDisplayDependenciesIfNeeded(displayId, createDisplayContext);
            }
            final NavigationBar navigationBar = this.mNavigationBarComponentFactory.create(createDisplayContext, bundle).getNavigationBar();
            navigationBar.init();
            this.mNavigationBars.put(displayId, navigationBar);
            navigationBar.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this) { // from class: com.android.systemui.navigationbar.NavigationBarControllerImpl.2
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    if (registerStatusBarResult != null) {
                        NavigationBar navigationBar2 = navigationBar;
                        int displayId2 = display.getDisplayId();
                        RegisterStatusBarResult registerStatusBarResult2 = registerStatusBarResult;
                        navigationBar2.setImeWindowStatus(displayId2, registerStatusBarResult2.mImeToken, registerStatusBarResult2.mImeWindowVis, registerStatusBarResult2.mImeBackDisposition, registerStatusBarResult2.mShowImeSwitcher);
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    view.removeOnAttachStateChangeListener(this);
                }
            });
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
            StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("isSimplifiedGesture="), BasicRune.NAVBAR_SIMPLIFIED_GESTURE, printWriter, "isSupportSearcle="), BasicRune.NAVBAR_SUPPORT_SEARCLE, printWriter, "isSupportLegacyGestureOptions=");
            m.append(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME").contains("SupportLegacyGestureOptions"));
            printWriter.println(m.toString());
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("isSupportLegacyGestureOptions(by NavStar)="), (Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATIONBAR_SPLUGIN_FLAGS, 0) & 4) != 0, printWriter);
        }
        StringBuilder m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("mIsLargeScreen="), this.mIsLargeScreen, printWriter, "mNavMode=");
        m2.append(this.mNavMode);
        printWriter.println(m2.toString());
        for (int i = 0; i < this.mNavigationBars.size(); i++) {
            if (i > 0) {
                printWriter.println();
            }
            this.mNavigationBars.valueAt(i).dump(printWriter);
        }
    }

    public final void finishBarAnimations(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            BarTransitions.BarBackgroundDrawable barBackgroundDrawable = navigationBar.mNavigationBarTransitions.mBarBackground;
            if (barBackgroundDrawable.mAnimating) {
                barBackgroundDrawable.mAnimating = false;
                barBackgroundDrawable.invalidateSelf();
            }
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

    public final NavigationBarView getNavigationBarView(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
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
            if (z) {
                Trace.beginSection("NavigationBarController#initializeTaskbarIfNecessary");
                int displayId = this.mContext.getDisplayId();
                navBarHelper.mTogglingNavbarTaskbar = this.mNavigationBars.contains(displayId);
                removeNavigationBar(displayId);
                taskbarDelegate.init(displayId);
                navBarHelper.mTogglingNavbarTaskbar = false;
                Trace.endSection();
            } else {
                taskbarDelegate.destroy();
            }
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
                IOverviewProxy iOverviewProxy = this.mOverviewProxyService.mOverviewProxy;
                if (iOverviewProxy != null) {
                    ((IOverviewProxy.Stub.Proxy) iOverviewProxy).isTaskbarEnabled(z);
                }
            } catch (Exception e) {
                Log.e("NavigationBarControllerImpl", "An error occurred in initializeTaskbarIfNecessary(): ");
                e.printStackTrace();
            }
        }
        return z;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean z = BasicRune.NAVBAR_ENABLED;
        if (z && this.mContext.getUserId() != 0) {
            Log.d("NavigationBarControllerImpl", "Skip onConfigChanged for userId=" + this.mContext.getUserId());
            return;
        }
        boolean z2 = this.mIsLargeScreen;
        int i = 0;
        this.mIsLargeScreen = z ? !this.mContext.getResources().getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider) : Utilities.isLargeScreen(this.mContext);
        if (z) {
            for (int i2 = 0; i2 < this.mNavigationBars.size(); i2++) {
                Context context = this.mNavigationBars.valueAt(i2).mContext;
                Resources resources = context.getResources();
                EventTypeFactory.EventType.OnConfigChanged onConfigChanged = new EventTypeFactory.EventType.OnConfigChanged(configuration);
                int displayId = context.getDisplayId();
                NavBarStore navBarStore = this.mNavBarStore;
                navBarStore.handleEvent(this, onConfigChanged, displayId);
                navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnNavBarConfigChanged(resources.getBoolean(android.R.bool.config_orderUnlockAndWake), resources.getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider), resources.getBoolean(android.R.bool.config_pdp_reject_enable_retry), resources.getInteger(android.R.integer.config_pinnerWebviewPinBytes)), context.getDisplayId());
            }
        }
        boolean applyNewConfig = this.mConfigChanges.applyNewConfig(this.mContext.getResources());
        boolean z3 = this.mIsLargeScreen != z2;
        StringBuilder sb = new StringBuilder("NavbarController: newConfig=");
        sb.append(configuration);
        sb.append(" mTaskbarDelegate initialized=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, this.mTaskbarDelegate.mInitialized, " willApplyConfigToNavbars=", applyNewConfig, " navBarCount=");
        sb.append(this.mNavigationBars.size());
        Log.i("NoBackGesture", sb.toString());
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
                    bundle.putInt("icon_hints", navigationBar.mNavigationIconHints);
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

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        final int i2 = this.mNavMode;
        if (i2 == i) {
            return;
        }
        this.mNavMode = i;
        updateAccessibilityButtonModeIfNeeded();
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

    public final void removeNavigationBar(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.destroyView();
            this.mNavigationBars.remove(i);
        }
    }

    public final boolean shouldCreateNavBarAndTaskBar(int i) {
        if (this.mHasNavBar.indexOfKey(i) > -1) {
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
        if (!this.mIsLargeScreen) {
            if (this.mIsPhone) {
                return false;
            }
            Flags.FEATURE_FLAGS.getClass();
        }
        return true;
    }

    public final void touchAutoDim(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.mNavigationBarTransitions.setAutoDim(false);
            Handler handler = navigationBar.mHandler;
            NavigationBar$$ExternalSyntheticLambda13 navigationBar$$ExternalSyntheticLambda13 = navigationBar.mAutoDim;
            handler.removeCallbacks(navigationBar$$ExternalSyntheticLambda13);
            int state = navigationBar.mStatusBarStateController.getState();
            if (state == 1 || state == 2) {
                return;
            }
            handler.postDelayed(navigationBar$$ExternalSyntheticLambda13, 2250L);
        }
    }

    public final void transitionTo(int i, int i2) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.mNavigationBarTransitions.transitionTo(i2, true);
        }
    }

    public final void updateAccessibilityButtonModeIfNeeded() {
        SecureSettings secureSettings = this.mSecureSettings;
        int intForUser = secureSettings.getIntForUser("accessibility_button_mode", 0, -2);
        if (intForUser == 1) {
            return;
        }
        if (QuickStepContract.isGesturalMode(this.mNavMode) && intForUser == 0) {
            secureSettings.putIntForUser("accessibility_button_mode", 2, -2);
        } else {
            if (QuickStepContract.isGesturalMode(this.mNavMode) || intForUser != 2) {
                return;
            }
            secureSettings.putIntForUser("accessibility_button_mode", 0, -2);
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
        }
        return initializeTaskbarIfNecessary;
    }
}
