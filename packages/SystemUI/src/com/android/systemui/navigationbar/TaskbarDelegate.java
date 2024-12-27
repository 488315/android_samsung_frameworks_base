package com.android.systemui.navigationbar;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowInsets;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.SysUIToast;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.navigationbar.NavBarEvents;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.wm.shell.back.BackAnimationController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class TaskbarDelegate implements CommandQueue.Callbacks, OverviewProxyService.OverviewProxyListener, NavigationModeController.ModeChangedListener, Dumpable {
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
    public boolean mInitialized;
    public LightBarController mLightBarController;
    public LightBarTransitionsController mLightBarTransitionsController;
    public final LightBarTransitionsController.Factory mLightBarTransitionsControllerFactory;
    public NavBarHelper mNavBarHelper;
    public int mNavigationIconHints;
    public NavigationModeController mNavigationModeController;
    public OverviewProxyService mOverviewProxyService;
    public Optional mPipOptional;
    public ScreenPinningNotify mScreenPinningNotify;
    public SysUiState mSysUiState;
    public TaskStackChangeListeners mTaskStackChangeListeners;
    public boolean mTaskbarTransientShowing;
    public int mTransitionMode;
    public final AnonymousClass1 mNavbarTaskbarStateUpdater = new NavBarHelper.NavbarTaskbarStateUpdater() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.1
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
    public final AnonymousClass2 mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.2
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public final void onLockTaskModeChanged(int i) {
            TaskbarDelegate taskbarDelegate = TaskbarDelegate.this;
            SysUiState sysUiState = taskbarDelegate.mSysUiState;
            sysUiState.setFlag(1L, i == 2);
            sysUiState.commitUpdate(taskbarDelegate.mDisplayId);
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
        }
    };
    public final TaskbarDelegate$$ExternalSyntheticLambda0 mPipListener = new TaskbarDelegate$$ExternalSyntheticLambda0(this, 0);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.navigationbar.TaskbarDelegate$4, reason: invalid class name */
    public final class AnonymousClass4 implements LightBarTransitionsController.DarkIntensityApplier {
        public AnonymousClass4() {
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
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void abortTransient(int i, int i2) {
        if (i == this.mDisplayId && (WindowInsets.Type.navigationBars() & i2) != 0 && this.mTaskbarTransientShowing) {
            this.mTaskbarTransientShowing = false;
            onTransientStateChanged();
        }
    }

    public final void destroy() {
        if (this.mInitialized) {
            if (BasicRune.NAVBAR_TASKBAR) {
                if (this.mTaskbarTransientShowing) {
                    this.mTaskbarTransientShowing = false;
                    onTransientStateChanged();
                }
                onDestroy();
            }
            this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
            ((ArrayList) this.mOverviewProxyService.mConnectionCallbacks).remove(this);
            this.mNavigationModeController.removeListener(this);
            this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
            this.mScreenPinningNotify = null;
            this.mAutoHideController.mNavigationBar = null;
            LightBarTransitionsController lightBarTransitionsController = this.mLightBarTransitionsController;
            CommandQueue commandQueue = lightBarTransitionsController.mCommandQueue;
            LightBarTransitionsController.Callback callback = lightBarTransitionsController.mCallback;
            commandQueue.removeCallback((CommandQueue.Callbacks) callback);
            lightBarTransitionsController.mStatusBarStateController.removeCallback(callback);
            lightBarTransitionsController.mGestureNavigationSettingsObserver.unregister();
            LightBarController lightBarController = this.mLightBarController;
            lightBarController.mNavigationBarController = null;
            lightBarController.updateNavigation();
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
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mNavigationIconHints="), this.mNavigationIconHints, printWriter, "  mNavigationMode="), this.mNavigationMode, printWriter, "  mDisabledFlags="), this.mDisabledFlags, printWriter, "  mTaskBarWindowState="), this.mTaskBarWindowState, printWriter, "  mBehavior="), this.mBehavior, printWriter, "  mTaskbarTransientShowing="), this.mTaskbarTransientShowing, printWriter);
        this.mEdgeBackGestureHandler.dump(printWriter);
    }

    public int getDisabledFlags() {
        return -1;
    }

    public EdgeBackGestureHandler getEdgeBackGestureHandler() {
        return null;
    }

    public LightBarTransitionsController getLightBarTransitionsController() {
        return null;
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public final void init(int i) {
        Trace.beginSection("TaskbarDelegate#init");
        try {
            if (this.mInitialized) {
                Trace.endSection();
                return;
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
            this.mScreenPinningNotify = new ScreenPinningNotify(this.mContext.createWindowContext(this.mDisplayManager.getDisplay(i), 2, null));
            updateSysuiFlags();
            if (BasicRune.NAVBAR_TASKBAR) {
                initialize();
            }
            this.mAutoHideController.mNavigationBar = this.mAutoHideUiElement;
            LightBarController lightBarController = this.mLightBarController;
            lightBarController.mNavigationBarController = this.mLightBarTransitionsController;
            lightBarController.updateNavigation();
            this.mPipOptional.ifPresent(new TaskbarDelegate$$ExternalSyntheticLambda0(this, 2));
            this.mEdgeBackGestureHandler.setBackAnimation(this.mBackAnimation);
            this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackListener);
            this.mInitialized = true;
            if (BasicRune.NAVBAR_ADDITIONAL_LOG) {
                Log.d("TaskbarDelegate", "TaskbarDelegate#init");
            }
            Trace.endSection();
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    public boolean isBackGestureAllowed(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public final void onNavigationModeChanged(int i) {
        this.mNavigationMode = i;
        this.mEdgeBackGestureHandler.onNavigationModeChanged(i);
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onRotationProposal(int i, boolean z) {
        OverviewProxyService overviewProxyService = this.mOverviewProxyService;
        overviewProxyService.getClass();
        try {
            if (overviewProxyService.mOverviewProxy == null) {
                Log.e("OverviewProxyService", "Failed to get overview proxy for proposing rotation.");
            } else if (!BasicRune.NAVBAR_TASKBAR || !((NavBarStateManagerImpl) overviewProxyService.mNavBarStateManager).rotateDisabledByPolicy()) {
                ((IOverviewProxy.Stub.Proxy) overviewProxyService.mOverviewProxy).onRotationProposal(i, z);
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onRotationProposal()", e);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) {
        boolean z2;
        boolean z3;
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
            boolean z4 = BasicRune.NAVBAR_TASKBAR;
            if (z4) {
                attributesChanged(i2, str, z);
            }
            if (this.mAppearance != i2) {
                this.mAppearance = i2;
                int transitionMode = NavBarHelper.transitionMode(i2, this.mTaskbarTransientShowing);
                if (this.mTransitionMode != transitionMode) {
                    this.mTransitionMode = transitionMode;
                    AutoHideController autoHideController = this.mAutoHideController;
                    if (autoHideController != null) {
                        autoHideController.touchAutoHide();
                    }
                    if (z4) {
                        transitionModeChanged(this.mTransitionMode);
                    }
                    z3 = true;
                } else {
                    z3 = false;
                }
                z2 = z3;
            } else {
                z2 = false;
            }
            if (i == this.mDisplayId) {
                this.mLightBarController.onNavigationBarAppearanceChanged(i2, z4 ? this.mTransitionMode : 0, z2, z, str);
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

    public final void onTransientStateChanged() {
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        boolean z = this.mTaskbarTransientShowing;
        edgeBackGestureHandler.mIsNavBarShownTransiently = z;
        int transitionMode = NavBarHelper.transitionMode(this.mAppearance, z);
        if (this.mTransitionMode != transitionMode) {
            this.mTransitionMode = transitionMode;
            AutoHideController autoHideController = this.mAutoHideController;
            if (autoHideController != null) {
                autoHideController.touchAutoHide();
            }
            if (BasicRune.NAVBAR_TASKBAR) {
                transitionModeChanged(this.mTransitionMode);
            }
            LightBarController lightBarController = this.mLightBarController;
            lightBarController.mHasLightNavigationBar = LightBarController.isLight(lightBarController.mAppearance, transitionMode, 16);
            if (BasicRune.NAVBAR_AOSP_BUG_FIX) {
                lightBarController.mNavigationBarMode = transitionMode;
                lightBarController.reevaluate();
            }
        }
        if (BasicRune.NAVBAR_TASKBAR) {
            transitionStateChanged(this.mTaskbarTransientShowing);
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        boolean isImeShown = this.mNavBarHelper.isImeShown(i2);
        boolean z2 = false;
        if (!isImeShown) {
            isImeShown = (i2 & 8) != 0;
        }
        if (BasicRune.NAVBAR_ENABLED) {
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "setImeWindowStatus displayId=", " vis=", " backDisposition=");
            m.append(i3);
            m.append(" showImeSwitcher=");
            m.append(z);
            m.append(" imeShown=");
            ActionBarContextView$$ExternalSyntheticOutline0.m(m, isImeShown, "TaskbarDelegate");
        }
        if (isImeShown && z) {
            z2 = true;
        }
        int calculateBackDispositionHints = Utilities.calculateBackDispositionHints(this.mNavigationIconHints, i3, isImeShown, z2);
        if (calculateBackDispositionHints != this.mNavigationIconHints) {
            this.mNavigationIconHints = calculateBackDispositionHints;
            updateSysuiFlags();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        OverviewProxyService overviewProxyService = this.mOverviewProxyService;
        overviewProxyService.getClass();
        try {
            IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
            if (iOverviewProxy != null) {
                ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onNavigationBarLumaSamplingEnabled(i, z);
            } else {
                Log.e("OverviewProxyService", "Failed to get overview proxy to enable/disable nav bar lumasampling");
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onNavigationBarLumaSamplingEnabled()", e);
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
        if (z) {
            SysUIToast.makeText(screenPinningNotify.mContext, R.string.sec_screen_pinning_start, 1).show();
        } else {
            SysUIToast.makeText(screenPinningNotify.mContext, R.string.sec_screen_pinning_exit, 1).show();
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
        if (i != this.mDisplayId || (WindowInsets.Type.navigationBars() & i2) == 0 || this.mTaskbarTransientShowing) {
            return;
        }
        this.mTaskbarTransientShowing = true;
        onTransientStateChanged();
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
        sysUiState.commitUpdate(this.mDisplayId);
    }

    public void initialize() {
    }

    public void onDestroy() {
    }

    public void updateTaskbarButtonIconsAndHints() {
    }

    public void handleNavigationBarEvent(NavBarEvents navBarEvents) {
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void onRecentsAnimationStateChanged(boolean z) {
    }

    public void transitionModeChanged(int i) {
    }

    public void transitionStateChanged(boolean z) {
    }

    public void updateActiveIndicatorSpringParams(float f, float f2) {
    }

    public void attributesChanged(int i, String str, boolean z) {
    }
}
