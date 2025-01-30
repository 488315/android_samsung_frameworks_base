package com.android.systemui.navigationbar;

import android.animation.ValueAnimator;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.widget.RemoteViews;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.pip.Pip;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.SysPropBooleanFlag;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarComponent;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.TaskbarDelegate.C18674;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ContextualButton;
import com.android.systemui.navigationbar.buttons.ContextualButtonGroup;
import com.android.systemui.navigationbar.buttons.RotationContextButton;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.plugin.SamsungPluginTaskBar;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
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
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationBarController implements CommandQueue.Callbacks, ConfigurationController.ConfigurationListener, NavigationModeController.ModeChangedListener, Dumpable {
    public final InterestingConfigChanges mConfigChanges;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final DisplayTracker mDisplayTracker;
    public final FeatureFlags mFeatureFlags;
    public final Handler mHandler;
    boolean mIsLargeScreen;
    public final NavBarHelper mNavBarHelper;
    public final NavBarStateManager mNavBarStateManager;
    public final NavBarStore mNavBarStore;
    public int mNavMode;
    public final NavigationBarComponent.Factory mNavigationBarComponentFactory;
    SparseArray<NavigationBar> mNavigationBars = new SparseArray<>();
    public final OverviewProxyService mOverviewProxyService;
    public final ScreenPinningNotify mScreenPinningNotify;
    public final SecureSettings mSecureSettings;
    public final TaskbarDelegate mTaskbarDelegate;

    public NavigationBarController(Context context, OverviewProxyService overviewProxyService, NavigationModeController navigationModeController, SysUiState sysUiState, CommandQueue commandQueue, Handler handler, ConfigurationController configurationController, NavBarHelper navBarHelper, TaskbarDelegate taskbarDelegate, NavigationBarComponent.Factory factory, DumpManager dumpManager, AutoHideController autoHideController, LightBarController lightBarController, TaskStackChangeListeners taskStackChangeListeners, Optional<Pip> optional, Optional<BackAnimationController.BackAnimationImpl> optional2, FeatureFlags featureFlags, SecureSettings secureSettings, DisplayTracker displayTracker, NavBarStore navBarStore) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        this.mConfigChanges = interestingConfigChanges;
        this.mContext = context;
        this.mHandler = handler;
        this.mNavigationBarComponentFactory = factory;
        this.mFeatureFlags = featureFlags;
        this.mSecureSettings = secureSettings;
        this.mDisplayTracker = displayTracker;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        commandQueue.addCallback((CommandQueue.Callbacks) this);
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
        taskbarDelegate.mLightBarTransitionsController = taskbarDelegate.mLightBarTransitionsControllerFactory.create(taskbarDelegate.new C18674());
        taskbarDelegate.mTaskStackChangeListeners = taskStackChangeListeners;
        Context context2 = navBarHelper.mContext;
        boolean z = true;
        taskbarDelegate.mEdgeBackGestureHandler = (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && context2.getDisplayId() == 1) ? navBarHelper.mEdgeBackGestureHandlerFactory.create(context2) : navBarHelper.mEdgeBackGestureHandler;
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
            SamsungPluginTaskBar samsungPluginTaskBar = taskbarDelegate.mPluginTaskBar;
            samsungPluginTaskBar.getClass();
            TaskbarDelegate taskbarDelegate2 = (TaskbarDelegate) Dependency.get(TaskbarDelegate.class);
            samsungPluginTaskBar.taskbarDelegate = taskbarDelegate2;
            samsungPluginTaskBar.iconResourceMapper = taskbarDelegate2 != null ? taskbarDelegate2.mIconResourceMapper : null;
        }
        boolean z2 = BasicRune.NAVBAR_ENABLED;
        if (!z2) {
            z = Utilities.isLargeScreen(context);
        } else if (context.getResources().getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider)) {
            z = false;
        }
        this.mIsLargeScreen = z;
        dumpManager.registerDumpable(this);
        if (z2) {
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
        boolean z = false;
        boolean z2 = displayId == 0;
        boolean z3 = displayId == 2;
        if (z3) {
            AbstractC0731x5bb8a836.m72m("Skip createNavigationBar displayId=", displayId, " isDexDisplay=", z3, "NavigationBarController");
            return;
        }
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            boolean z4 = (display.getFlags() & 524288) != 0;
            if (z4) {
                AbstractC0731x5bb8a836.m72m("Skip createNavigationBar displayId=", displayId, " isCoverDisplay=", z4, "NavigationBarController");
                return;
            }
        }
        try {
            z = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(displayId);
        } catch (RemoteException unused) {
            Log.w("NavigationBarController", "Cannot get WindowManager.");
        }
        if (z) {
            if (z2 && initializeTaskbarIfNecessary() && !BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
                return;
            }
            Context context = this.mContext;
            if (!z2) {
                context = context.createDisplayContext(display);
            }
            if (BasicRune.NAVBAR_ENABLED) {
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) this.mNavBarStore;
                navBarStoreImpl.initDisplayDependenciesIfNeeded(displayId, context);
                NavBarStateManager navStateManager = navBarStoreImpl.getNavStateManager(displayId);
                TaskbarDelegate taskbarDelegate = (TaskbarDelegate) Dependency.get(TaskbarDelegate.class);
                if (BasicRune.NAVBAR_REMOTEVIEW && taskbarDelegate != null) {
                    taskbarDelegate.mNavBarRemoteViewManager = navStateManager.navBarRemoteViewManager;
                }
            }
            final NavigationBar navigationBar = this.mNavigationBarComponentFactory.create(context, bundle).getNavigationBar();
            navigationBar.init();
            this.mNavigationBars.put(displayId, navigationBar);
            View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener(this) { // from class: com.android.systemui.navigationbar.NavigationBarController.1
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
            };
            View view = navigationBar.mView;
            if (view != null) {
                view.addOnAttachStateChangeListener(onAttachStateChangeListener);
            }
        }
    }

    public final void disableAnimationsDuringHide(int i, long j) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            NavigationBarView navigationBarView = (NavigationBarView) navigationBar.mView;
            navigationBarView.mLayoutTransitionsEnabled = false;
            navigationBarView.updateLayoutTransitionsEnabled();
            navigationBar.mHandler.postDelayed(navigationBar.mEnableLayoutTransitions, j + 448);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("mIsLargeScreen="), this.mIsLargeScreen, printWriter, "mNavMode=");
        m64m.append(this.mNavMode);
        printWriter.println(m64m.toString());
        if (BasicRune.NAVBAR_ENABLED) {
            StringBuilder m64m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("isSimplifiedGesture="), BasicRune.NAVBAR_SIMPLIFIED_GESTURE, printWriter, "isSupportSearcle="), BasicRune.NAVBAR_SUPPORT_SEARCLE, printWriter, "isSupportLegacyGestureOptions=");
            m64m2.append(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_NAVIGATION_BAR_THEME").contains("SupportLegacyGestureOptions"));
            printWriter.println(m64m2.toString());
        }
        for (int i = 0; i < this.mNavigationBars.size(); i++) {
            if (i > 0) {
                printWriter.println();
            }
            NavigationBar valueAt = this.mNavigationBars.valueAt(i);
            printWriter.println("NavigationBar (displayId=" + valueAt.mDisplayId + "):");
            StringBuilder m77m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("  mStartingQuickSwitchRotation="), valueAt.mStartingQuickSwitchRotation, printWriter, "  mCurrentRotation="), valueAt.mCurrentRotation, printWriter, "  mHomeButtonLongPressDurationMs=");
            m77m.append(valueAt.mHomeButtonLongPressDurationMs);
            printWriter.println(m77m.toString());
            StringBuilder m64m3 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mLongPressHomeEnabled="), valueAt.mLongPressHomeEnabled, printWriter, "  mNavigationBarWindowState=");
            m64m3.append(StatusBarManager.windowStateToString(valueAt.mNavigationBarWindowState));
            printWriter.println(m64m3.toString());
            printWriter.println("  mTransitionMode=".concat(BarTransitions.modeToString(valueAt.mTransitionMode)));
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mTransientShown="), valueAt.mTransientShown, printWriter, "  mTransientShownFromGestureOnSystemBar="), valueAt.mTransientShownFromGestureOnSystemBar, printWriter, "  mScreenPinningActive="), valueAt.mScreenPinningActive, printWriter);
            CentralSurfaces.dumpBarTransitions(printWriter, "mNavigationBarView", valueAt.mNavigationBarTransitions);
            printWriter.println("  mOrientedHandleSamplingRegion: " + valueAt.mOrientedHandleSamplingRegion);
            NavigationBarView navigationBarView = (NavigationBarView) valueAt.mView;
            navigationBarView.getClass();
            Rect rect = new Rect();
            Point point = new Point();
            navigationBarView.getContext().getDisplay().getRealSize(point);
            printWriter.println("NavigationBarView:");
            printWriter.println(String.format("      this: " + CentralSurfaces.viewInfo(navigationBarView) + " " + NavigationBarView.visibilityToString(navigationBarView.getVisibility()), new Object[0]));
            navigationBarView.getWindowVisibleDisplayFrame(rect);
            boolean z = rect.right > point.x || rect.bottom > point.y;
            StringBuilder sb = new StringBuilder("      window: ");
            sb.append(rect.toShortString());
            sb.append(" ");
            sb.append(NavigationBarView.visibilityToString(navigationBarView.getWindowVisibility()));
            KeyboardUI$$ExternalSyntheticOutline0.m134m(sb, z ? " OFFSCREEN!" : "", printWriter);
            Object[] objArr = new Object[5];
            int id = navigationBarView.mCurrentView.getId();
            if (id != 0) {
                try {
                    str = navigationBarView.getContext().getResources().getResourceName(id);
                } catch (Resources.NotFoundException unused) {
                    str = "(unknown)";
                }
            } else {
                str = "(null)";
            }
            objArr[0] = str;
            objArr[1] = Integer.valueOf(navigationBarView.mCurrentView.getWidth());
            objArr[2] = Integer.valueOf(navigationBarView.mCurrentView.getHeight());
            objArr[3] = NavigationBarView.visibilityToString(navigationBarView.mCurrentView.getVisibility());
            objArr[4] = Float.valueOf(navigationBarView.mCurrentView.getAlpha());
            printWriter.println(String.format("      mCurrentView: id=%s (%dx%d) %s %f", objArr));
            Object[] objArr2 = new Object[3];
            objArr2[0] = Integer.valueOf(navigationBarView.mDisabledFlags);
            objArr2[1] = navigationBarView.mIsVertical ? "true" : "false";
            objArr2[2] = Float.valueOf(navigationBarView.mBarTransitions.mLightTransitionsController.mDarkIntensity);
            printWriter.println(String.format("      disabled=0x%08x vertical=%s darkIntensity=%.2f", objArr2));
            printWriter.println("    mScreenOn: " + navigationBarView.mScreenOn);
            NavigationBarView.dumpButton(printWriter, "back", navigationBarView.getBackButton());
            NavigationBarView.dumpButton(printWriter, BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN, navigationBarView.getHomeButton());
            NavigationBarView.dumpButton(printWriter, "handle", navigationBarView.getHomeHandle());
            NavigationBarView.dumpButton(printWriter, "rcnt", navigationBarView.getRecentsButton());
            NavigationBarView.dumpButton(printWriter, "rota", (RotationContextButton) navigationBarView.mButtonDispatchers.get(R.id.rotate_suggestion));
            NavigationBarView.dumpButton(printWriter, "a11y", navigationBarView.getAccessibilityButton());
            NavigationBarView.dumpButton(printWriter, "ime", (ButtonDispatcher) navigationBarView.mButtonDispatchers.get(R.id.ime_switcher));
            NavigationBarInflaterView navigationBarInflaterView = navigationBarView.mNavigationInflaterView;
            if (navigationBarInflaterView != null) {
                KeyboardUI$$ExternalSyntheticOutline0.m134m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "NavigationBarInflaterView", "  mCurrentLayout: "), navigationBarInflaterView.mCurrentLayout, printWriter);
            }
            NavigationBarTransitions navigationBarTransitions = navigationBarView.mBarTransitions;
            navigationBarTransitions.getClass();
            printWriter.println("NavigationBarTransitions:");
            printWriter.println("  mMode: " + navigationBarTransitions.mMode);
            printWriter.println("  mAlwaysOpaque: false");
            StringBuilder m77m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mAllowAutoDimWallpaperNotVisible: "), navigationBarTransitions.mAllowAutoDimWallpaperNotVisible, printWriter, "  mWallpaperVisible: "), navigationBarTransitions.mWallpaperVisible, printWriter, "  mLightsOut: "), navigationBarTransitions.mLightsOut, printWriter, "  mAutoDim: "), navigationBarTransitions.mAutoDim, printWriter, "  bg overrideAlpha: "), navigationBarTransitions.mBarBackground.mOverrideAlpha, printWriter, "  bg color: "), navigationBarTransitions.mBarBackground.mColor, printWriter, "  bg frame: ");
            m77m2.append(navigationBarTransitions.mBarBackground.mFrame);
            printWriter.println(m77m2.toString());
            ContextualButtonGroup contextualButtonGroup = navigationBarView.mContextualButtonGroup;
            View view = contextualButtonGroup.mCurrentView;
            StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "ContextualButtonGroup", "  getVisibleContextButton(): ");
            m75m.append(contextualButtonGroup.getVisibleContextButton());
            printWriter.println(m75m.toString());
            printWriter.println("  isVisible(): " + contextualButtonGroup.isVisible());
            StringBuilder sb2 = new StringBuilder("  attached(): ");
            sb2.append(view != null && view.isAttachedToWindow());
            printWriter.println(sb2.toString());
            printWriter.println("  mButtonData [ ");
            ArrayList arrayList = (ArrayList) contextualButtonGroup.mButtonData;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ContextualButtonGroup.ButtonData buttonData = (ContextualButtonGroup.ButtonData) arrayList.get(size);
                View view2 = buttonData.button.mCurrentView;
                StringBuilder m1m = AbstractC0000x2c234b15.m1m("    ", size, ": markedVisible=");
                m1m.append(buttonData.markedVisible);
                m1m.append(" visible=");
                ContextualButton contextualButton = buttonData.button;
                m1m.append(contextualButton.getVisibility());
                m1m.append(" attached=");
                m1m.append(view2 != null && view2.isAttachedToWindow());
                m1m.append(" alpha=");
                m1m.append(contextualButton.getAlpha());
                printWriter.println(m1m.toString());
            }
            printWriter.println("  ]");
            navigationBarView.mEdgeBackGestureHandler.dump(printWriter);
            valueAt.mRegionSamplingHelper.dump(printWriter);
            AutoHideController autoHideController = valueAt.mAutoHideController;
            if (autoHideController != null) {
                StringBuilder m64m4 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "AutoHideController:", "\tmAutoHideSuspended="), autoHideController.mAutoHideSuspended, printWriter, "\tisAnyTransientBarShown=");
                m64m4.append(autoHideController.isAnyTransientBarShown());
                printWriter.println(m64m4.toString());
                printWriter.println("\thasPendingAutoHide=" + autoHideController.mHandler.hasCallbacks(autoHideController.mAutoHide));
                StringBuilder sb3 = new StringBuilder("\tgetAutoHideTimeout=");
                AccessibilityManager accessibilityManager = autoHideController.mAccessibilityManager;
                sb3.append(accessibilityManager.getRecommendedTimeoutMillis(2250, 4));
                printWriter.println(sb3.toString());
                printWriter.println("\tgetUserAutoHideTimeout=" + accessibilityManager.getRecommendedTimeoutMillis(350, 4));
            }
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
        return (NavigationBarView) navigationBar.mView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x007e, code lost:
    
        if (r8.isEnabledInternal(r9, android.os.SystemProperties.getBoolean(r9, r0)) != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0096, code lost:
    
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0097, code lost:
    
        if (r3 == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0099, code lost:
    
        android.os.Trace.beginSection("NavigationBarController#initializeTaskbarIfNecessary");
        r0 = r4.getDisplayId();
        r5.mTogglingNavbarTaskbar = r11.mNavigationBars.contains(r0);
        removeNavigationBar(r0);
        r6.init(r0);
        r5.mTogglingNavbarTaskbar = false;
        android.os.Trace.endSection();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b7, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b4, code lost:
    
        r6.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0093, code lost:
    
        if (r0 == false) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean initializeTaskbarIfNecessary() {
        boolean z;
        boolean z2 = BasicRune.NAVBAR_ENABLED;
        Context context = this.mContext;
        NavBarHelper navBarHelper = this.mNavBarHelper;
        TaskbarDelegate taskbarDelegate = this.mTaskbarDelegate;
        if (z2) {
            int displayId = context.getDisplayId();
            boolean z3 = BasicRune.NAVBAR_SUPPORT_TASKBAR;
            r3 = z3 && this.mNavBarStateManager.isTaskBarEnabled(true);
            if (r3) {
                Trace.beginSection("NavigationBarController#initializeTaskbarIfNecessary");
                navBarHelper.mTogglingNavbarTaskbar = this.mNavigationBars.contains(displayId);
                if (!BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
                    removeNavigationBar(displayId);
                }
                taskbarDelegate.init(displayId);
                navBarHelper.mTogglingNavbarTaskbar = false;
                Trace.endSection();
            } else {
                taskbarDelegate.destroy();
            }
            if (z3) {
                try {
                    IOverviewProxy iOverviewProxy = this.mOverviewProxyService.mOverviewProxy;
                    if (iOverviewProxy != null) {
                        ((IOverviewProxy.Stub.Proxy) iOverviewProxy).isTaskbarEnabled(r3);
                    }
                } catch (Exception e) {
                    Log.e("NavigationBarController", "An error occurred in initializeTaskbarIfNecessary(): ");
                    e.printStackTrace();
                }
            }
            return r3;
        }
        if (!this.mIsLargeScreen) {
            SysPropBooleanFlag sysPropBooleanFlag = Flags.HIDE_NAVBAR_WINDOW;
            FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) this.mFeatureFlags;
            featureFlagsRelease.getClass();
            String str = sysPropBooleanFlag.name;
            SystemPropertiesHelper systemPropertiesHelper = featureFlagsRelease.mSystemProperties;
            boolean booleanValue = sysPropBooleanFlag.getDefault().booleanValue();
            systemPropertiesHelper.getClass();
        }
        try {
            z = WindowManagerGlobal.getWindowManagerService().hasNavigationBar(context.getDisplayId());
        } catch (RemoteException unused) {
            Log.w("NavigationBarController", "Cannot get WindowManager.");
            z = false;
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean z = BasicRune.NAVBAR_ENABLED;
        Context context = this.mContext;
        if (z && context.getUserId() != 0) {
            Log.d("NavigationBarController", "Skip onConfigChanged for userId=" + context.getUserId());
            return;
        }
        boolean z2 = this.mIsLargeScreen;
        this.mIsLargeScreen = z ? !context.getResources().getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider) : Utilities.isLargeScreen(context);
        if (z) {
            for (int i = 0; i < this.mNavigationBars.size(); i++) {
                Context context2 = this.mNavigationBars.valueAt(i).mContext;
                Resources resources = context2.getResources();
                EventTypeFactory.EventType.OnConfigChanged onConfigChanged = new EventTypeFactory.EventType.OnConfigChanged(configuration);
                int displayId = context2.getDisplayId();
                NavBarStore navBarStore = this.mNavBarStore;
                ((NavBarStoreImpl) navBarStore).handleEvent(this, onConfigChanged, displayId);
                ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnNavBarConfigChanged(resources.getBoolean(android.R.bool.config_letterboxIsEducationEnabled), resources.getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider), resources.getBoolean(android.R.bool.config_letterboxIsPolicyForIgnoringRequestedOrientationEnabled), resources.getInteger(android.R.integer.config_notificationsBatteryFullARGB)), context2.getDisplayId());
            }
        }
        boolean applyNewConfig = this.mConfigChanges.applyNewConfig(context.getResources());
        boolean z3 = this.mIsLargeScreen != z2;
        StringBuilder sb = new StringBuilder("NavbarController: newConfig=");
        sb.append(configuration);
        sb.append(" mTaskbarDelegate initialized=");
        TaskbarDelegate taskbarDelegate = this.mTaskbarDelegate;
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, taskbarDelegate.mInitialized, " willApplyConfigToNavbars=", applyNewConfig, " navBarCount=");
        sb.append(this.mNavigationBars.size());
        Log.i("NoBackGesture", sb.toString());
        if (taskbarDelegate.mInitialized) {
            taskbarDelegate.mEdgeBackGestureHandler.onConfigurationChanged(configuration);
            if (!BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
                Resources resources2 = taskbarDelegate.mWindowContext.getResources();
                EventTypeFactory.EventType.OnConfigChanged onConfigChanged2 = new EventTypeFactory.EventType.OnConfigChanged(configuration);
                int i2 = taskbarDelegate.mDisplayId;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) taskbarDelegate.mNavBarStore;
                navBarStoreImpl.handleEvent(taskbarDelegate, onConfigChanged2, i2);
                navBarStoreImpl.handleEvent(taskbarDelegate, new EventTypeFactory.EventType.OnNavBarConfigChanged(resources2.getBoolean(android.R.bool.config_letterboxIsEducationEnabled), resources2.getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider), resources2.getBoolean(android.R.bool.config_letterboxIsPolicyForIgnoringRequestedOrientationEnabled), resources2.getInteger(android.R.integer.config_notificationsBatteryFullARGB)), taskbarDelegate.mDisplayId);
            }
            if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
                taskbarDelegate.updateTaskbarButtonIconsAndHints();
            }
        }
        if (z3 && updateNavbarForTaskbar() && !BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            return;
        }
        if (applyNewConfig) {
            for (int i3 = 0; i3 < this.mNavigationBars.size(); i3++) {
                int keyAt = this.mNavigationBars.keyAt(i3);
                Bundle bundle = new Bundle();
                NavigationBar navigationBar = this.mNavigationBars.get(keyAt);
                if (navigationBar != null) {
                    bundle.putInt("disabled_state", navigationBar.mDisabledFlags1);
                    bundle.putInt("disabled2_state", navigationBar.mDisabledFlags2);
                    bundle.putInt("appearance", navigationBar.mAppearance);
                    bundle.putInt("behavior", navigationBar.mBehavior);
                    bundle.putBoolean("transient_state", navigationBar.mTransientShown);
                    LightBarTransitionsController lightBarTransitionsController = navigationBar.mNavigationBarTransitions.mLightTransitionsController;
                    ValueAnimator valueAnimator = lightBarTransitionsController.mTintAnimator;
                    bundle.putFloat("dark_intensity", (valueAnimator == null || !valueAnimator.isRunning()) ? lightBarTransitionsController.mDarkIntensity : lightBarTransitionsController.mNextDarkIntensity);
                    if (BasicRune.NAVBAR_ENABLED) {
                        bundle.putInt("icon_hints", navigationBar.mNavigationIconHints);
                    }
                }
                removeNavigationBar(keyAt);
                createNavigationBar(this.mDisplayManager.getDisplay(keyAt), bundle, null);
            }
            return;
        }
        for (int i4 = 0; i4 < this.mNavigationBars.size(); i4++) {
            NavigationBar valueAt = this.mNavigationBars.valueAt(i4);
            valueAt.getClass();
            int rotation = configuration.windowConfiguration.getRotation();
            Context context3 = valueAt.mContext;
            Locale locale = context3.getResources().getConfiguration().locale;
            int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(locale);
            if (!locale.equals(valueAt.mLocale) || layoutDirectionFromLocale != valueAt.mLayoutDirection) {
                if (BasicRune.NAVBAR_ENABLED && valueAt.mLocale != null) {
                    View view = valueAt.mView;
                    if (view != null && ((NavigationBarView) view).isAttachedToWindow()) {
                        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) ((View) ((NavigationBarView) valueAt.mView).getParent()).getLayoutParams();
                        layoutParams.accessibilityTitle = context3.getString(R.string.samsung_nav_bar);
                        valueAt.mWindowManager.updateViewLayout((View) ((NavigationBarView) valueAt.mView).getParent(), layoutParams);
                    }
                    View view2 = valueAt.mView;
                    if (view2 != null) {
                        ((NavigationBarView) view2).reInflateNavBarLayout();
                    }
                }
                valueAt.mLocale = locale;
                valueAt.mLayoutDirection = layoutDirectionFromLocale;
                ((NavigationBarView) valueAt.mView).setLayoutDirection(layoutDirectionFromLocale);
            }
            valueAt.repositionNavigationBar(rotation);
            valueAt.mEdgeBackGestureHandler.onConfigurationChanged(configuration);
            if (valueAt.canShowSecondaryHandle()) {
                if (rotation != valueAt.mCurrentRotation) {
                    valueAt.mCurrentRotation = rotation;
                    valueAt.orientSecondaryHomeHandle();
                }
            } else if (BasicRune.NAVBAR_GESTURE) {
                valueAt.resetSecondaryHandle();
            }
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onDisplayReady(int i) {
        Display display = this.mDisplayManager.getDisplay(i);
        boolean z = BasicRune.NAVBAR_ENABLED;
        Context context = this.mContext;
        this.mIsLargeScreen = z ? !context.getResources().getBoolean(R.bool.config_navBarSupportPhoneLayoutProvider) : Utilities.isLargeScreen(context);
        createNavigationBar(display, null, null);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onDisplayRemoved(int i) {
        removeNavigationBar(i);
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        final int i2 = this.mNavMode;
        if (i2 == i) {
            return;
        }
        this.mNavMode = i;
        updateAccessibilityButtonModeIfNeeded();
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarController navigationBarController = NavigationBarController.this;
                if (i2 != navigationBarController.mNavMode) {
                    navigationBarController.updateNavbarForTaskbar();
                }
                for (int i3 = 0; i3 < navigationBarController.mNavigationBars.size(); i3++) {
                    NavigationBar valueAt = navigationBarController.mNavigationBars.valueAt(i3);
                    if (valueAt != null) {
                        ((NavigationBarView) valueAt.mView).updateStates();
                    }
                }
            }
        });
    }

    public final void removeNavigationBar(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.setAutoHideController(null);
            navigationBar.mCommandQueue.removeCallback((CommandQueue.Callbacks) navigationBar);
            navigationBar.mWindowManager.removeViewImmediate(((NavigationBarView) navigationBar.mView).getRootView());
            navigationBar.mNavigationModeController.removeListener(navigationBar.mModeChangedListener);
            EdgeBackGestureHandler edgeBackGestureHandler = navigationBar.mEdgeBackGestureHandler;
            edgeBackGestureHandler.mStateChangeCallback = null;
            navigationBar.mNavBarHelper.removeNavTaskStateUpdater(navigationBar.mNavbarTaskbarStateUpdater);
            ((ArrayList) navigationBar.mNotificationShadeDepthController.listeners).remove(navigationBar.mDepthListener);
            navigationBar.mDeviceConfigProxy.getClass();
            DeviceConfig.removeOnPropertiesChangedListener(navigationBar.mOnPropertiesChangedListener);
            navigationBar.mTaskStackChangeListeners.unregisterTaskStackListener(navigationBar.mTaskStackListener);
            if (BasicRune.NAVBAR_SUPPORT_COVER_DISPLAY && navigationBar.mDisplayId == 1) {
                LightBarController lightBarController = navigationBar.mLightBarController;
                if (lightBarController != null) {
                    ((BatteryControllerImpl) lightBarController.mBatteryController).removeCallback(lightBarController);
                    NavigationModeController navigationModeController = lightBarController.mNavigationModeController;
                    if (navigationModeController != null) {
                        navigationModeController.removeListener(lightBarController.mModeChangedListener);
                    }
                }
                edgeBackGestureHandler.onNavBarDetached();
            }
            if (BasicRune.NAVBAR_ENABLED) {
                int i2 = navigationBar.mDisplayId;
                NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navigationBar.mNavBarStore;
                if (i2 != 0) {
                    navBarStoreImpl.navDependencies.put(Integer.valueOf(i2), null);
                    navBarStoreImpl.navStateManager.put(Integer.valueOf(i2), null);
                } else {
                    navBarStoreImpl.getClass();
                }
            }
            this.mNavigationBars.remove(i);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void resetScheduleAutoHide() {
        for (int i = 0; i < this.mNavigationBars.size(); i++) {
            NavigationBar valueAt = this.mNavigationBars.valueAt(i);
            valueAt.getClass();
            Log.d("NavigationBar", "resetAutoHide()");
            valueAt.mAutoHideController.touchAutoHide();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
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
        Log.d("NavigationBarController", "setNavigationBarShortcut requestClass : " + str + ", remoteViews : " + remoteViews + ", position : " + i + ", priority : " + i2);
        ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnSetRemoteView(str, remoteViews, i, i2));
    }

    public final void touchAutoDim(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.mNavigationBarTransitions.setAutoDim(false);
            Handler handler = navigationBar.mHandler;
            NavigationBar$$ExternalSyntheticLambda9 navigationBar$$ExternalSyntheticLambda9 = navigationBar.mAutoDim;
            handler.removeCallbacks(navigationBar$$ExternalSyntheticLambda9);
            int state = navigationBar.mStatusBarStateController.getState();
            if (state == 1 || state == 2) {
                return;
            }
            handler.postDelayed(navigationBar$$ExternalSyntheticLambda9, 2250L);
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
        int intForUser = secureSettings.getIntForUser(0, -2, "accessibility_button_mode");
        if (intForUser == 1) {
            return;
        }
        if (QuickStepContract.isGesturalMode(this.mNavMode) && intForUser == 0) {
            secureSettings.putIntForUser(2, -2, "accessibility_button_mode");
        } else {
            if (QuickStepContract.isGesturalMode(this.mNavMode) || intForUser != 2) {
                return;
            }
            secureSettings.putIntForUser(0, -2, "accessibility_button_mode");
        }
    }

    public final boolean updateNavbarForTaskbar() {
        boolean initializeTaskbarIfNecessary = initializeTaskbarIfNecessary();
        Context context = this.mContext;
        if (initializeTaskbarIfNecessary || this.mNavigationBars.get(context.getDisplayId()) != null) {
            if (BasicRune.NAVBAR_ENABLED_HARD_KEY && !initializeTaskbarIfNecessary && !QuickStepContract.isGesturalMode(this.mNavMode) && this.mNavigationBars.get(context.getDisplayId()) != null) {
                removeNavigationBar(context.getDisplayId());
            }
        } else if (!BasicRune.NAVBAR_SUPPORT_POLICY_VISIBILITY) {
            createNavigationBar(context.getDisplay(), null, null);
        }
        return initializeTaskbarIfNecessary;
    }
}
