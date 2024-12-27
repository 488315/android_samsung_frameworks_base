package com.android.server.wm;

import android.R;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.InsetsController;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.android.internal.statusbar.IStatusBar;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;

import com.samsung.android.rune.CoreRune;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InsetsPolicy {
    public static final int CONTROLLABLE_TYPES =
            (WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars())
                    | WindowInsets.Type.ime();
    public final ImmersiveControlTarget mDexForceImmersiveModeControlTarget;
    public final DisplayContent mDisplayContent;
    public InsetsControlTarget mFakeNavControlTarget;
    public InsetsControlTarget mFakeStatusControlTarget;
    public WindowState mFocusedWin;
    public int mForcedShowingTypes;
    public final boolean mHideNavBarForKeyboard;
    public boolean mLastTransientRequestedByPolicyControl;
    public final ControlTarget mPermanentControlTarget;
    public final DisplayPolicy mPolicy;
    public int mShowingTransientTypes;
    public final InsetsStateController mStateController;
    public final ControlTarget mTransientControlTarget;
    public final BarWindow mStatusBar = new BarWindow(1);
    public final BarWindow mNavBar = new BarWindow(2);
    public final PolicyControlTarget mPolicyControlTarget = new PolicyControlTarget();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BarWindow {
        public final int mId;
        public int mState = 0;

        /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /* renamed from: -$$Nest$mupdateVisibility, reason: not valid java name */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static void m1063$$Nest$mupdateVisibility(
                com.android.server.wm.InsetsPolicy.BarWindow r1,
                com.android.server.wm.InsetsControlTarget r2,
                int r3) {
            /*
                if (r2 == 0) goto Le
                r1.getClass()
                boolean r2 = r2.isRequestedVisible(r3)
                if (r2 == 0) goto Lc
                goto Le
            Lc:
                r2 = 2
                goto Lf
            Le:
                r2 = 0
            Lf:
                int r3 = r1.mState
                if (r3 == r2) goto L32
                r1.mState = r2
                com.android.server.wm.InsetsPolicy r3 = com.android.server.wm.InsetsPolicy.this
                com.android.server.wm.DisplayPolicy r3 = r3.mPolicy
                com.android.server.statusbar.StatusBarManagerInternal r3 = r3.getStatusBarManagerInternal()
                if (r3 == 0) goto L32
                com.android.server.wm.InsetsPolicy r0 = com.android.server.wm.InsetsPolicy.this
                com.android.server.wm.DisplayContent r0 = r0.mDisplayContent
                int r0 = r0.mDisplayId
                int r1 = r1.mId
                com.android.server.statusbar.StatusBarManagerService$2 r3 = (com.android.server.statusbar.StatusBarManagerService.AnonymousClass2) r3
                com.android.server.statusbar.StatusBarManagerService r3 = com.android.server.statusbar.StatusBarManagerService.this
                com.android.internal.statusbar.IStatusBar r3 = r3.mBar
                if (r3 == 0) goto L32
                r3.setWindowState(r0, r1, r2)     // Catch: android.os.RemoteException -> L32
            L32:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.wm.InsetsPolicy.BarWindow.m1063$$Nest$mupdateVisibility(com.android.server.wm.InsetsPolicy$BarWindow,"
                        + " com.android.server.wm.InsetsControlTarget, int):void");
        }

        public BarWindow(int i) {
            this.mId = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ControlTarget implements InsetsControlTarget, Runnable {
        public final WindowManagerGlobalLock mGlobalLock;
        public final WindowManagerService.H mHandler;
        public final InsetsController mInsetsController;
        public final String mName;
        public final InsetsState mState = new InsetsState();
        public final InsetsStateController mStateController;

        public ControlTarget(DisplayContent displayContent, String str) {
            WindowManagerService windowManagerService = displayContent.mWmService;
            WindowManagerService.H h = windowManagerService.mH;
            this.mHandler = h;
            this.mGlobalLock = windowManagerService.mGlobalLock;
            this.mStateController = displayContent.mInsetsStateController;
            this.mInsetsController = new InsetsController(new Host(h, str));
            this.mName = str;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final void notifyInsetsControlChanged(int i) {
            this.mHandler.post(this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (this.mGlobalLock) {
                this.mState.set(this.mStateController.mState, true);
                this.mInsetsController.onStateChanged(this.mState);
                this.mInsetsController.onControlsChanged(
                        this.mStateController.getControlsForDispatch(this));
            }
        }

        public final String toString() {
            return this.mName;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Host implements InsetsController.Host {
        public final Handler mHandler;
        public final String mName;
        public final float[] mTmpFloat9 = new float[9];

        public Host(Handler handler, String str) {
            this.mHandler = handler;
            this.mName = str;
        }

        public final void addOnPreDrawRunnable(Runnable runnable) {}

        public final void applySurfaceParams(
                SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            for (int length = surfaceParamsArr.length - 1; length >= 0; length--) {
                SyncRtSurfaceTransactionApplier.applyParams(
                        transaction, surfaceParamsArr[length], this.mTmpFloat9);
            }
            transaction.apply();
            transaction.close();
        }

        public final int dipToPx(int i) {
            return 0;
        }

        public final void dispatchWindowInsetsAnimationEnd(
                WindowInsetsAnimation windowInsetsAnimation) {}

        public final void dispatchWindowInsetsAnimationPrepare(
                WindowInsetsAnimation windowInsetsAnimation) {}

        public final WindowInsets dispatchWindowInsetsAnimationProgress(
                WindowInsets windowInsets, List list) {
            return windowInsets;
        }

        public final WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(
                WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
            return bounds;
        }

        public final Handler getHandler() {
            return this.mHandler;
        }

        public final InputMethodManager getInputMethodManager() {
            return null;
        }

        public final String getRootViewTitle() {
            return this.mName;
        }

        public final int getSystemBarsAppearance() {
            return 0;
        }

        public final int getSystemBarsBehavior() {
            return 2;
        }

        public final IBinder getWindowToken() {
            return null;
        }

        public final boolean hasAnimationCallbacks() {
            return false;
        }

        public final void notifyAnimationRunningStateChanged(boolean z) {}

        public final void notifyInsetsChanged() {}

        public final void postInsetsAnimationCallback(Runnable runnable) {}

        public final void releaseSurfaceControlFromRt(SurfaceControl surfaceControl) {
            surfaceControl.release();
        }

        public final void setSystemBarsAppearance(int i, int i2) {}

        public final void setSystemBarsBehavior(int i) {}

        public final void updateRequestedVisibleTypes(int i) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ImmersiveControlTarget extends ControlTarget {
        public final int VISIBLE_TYPES;

        public ImmersiveControlTarget(DisplayContent displayContent) {
            super(displayContent, "DexForceImmersiveModeControlTarget");
            int defaultVisible =
                    WindowInsets.Type.defaultVisible() & (~WindowInsets.Type.navigationBars());
            this.VISIBLE_TYPES = defaultVisible;
            this.mInsetsController.setRequestedVisibleTypes(
                    defaultVisible, WindowInsets.Type.navigationBars());
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final int getRequestedVisibleTypes() {
            return this.VISIBLE_TYPES;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final boolean isRequestedVisible(int i) {
            return (this.VISIBLE_TYPES & i) != 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PolicyControlTarget implements InsetsControlTarget {
        public PolicyControlTarget() {}

        @Override // com.android.server.wm.InsetsControlTarget
        public final boolean canShowTransient() {
            return true;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final int getRequestedVisibleTypes() {
            return 0;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final boolean isRequestedVisible(int i) {
            return false;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final void notifyInsetsControlChanged(int i) {
            WindowContainer windowContainer;
            InsetsPolicy insetsPolicy = InsetsPolicy.this;
            InsetsSourceControl[] controlsForDispatch =
                    insetsPolicy.mStateController.getControlsForDispatch(this);
            if (controlsForDispatch == null) {
                return;
            }
            int length = controlsForDispatch.length;
            int i2 = 0;
            while (true) {
                DisplayContent displayContent = insetsPolicy.mDisplayContent;
                if (i2 >= length) {
                    displayContent.scheduleAnimation();
                    return;
                }
                InsetsSourceControl insetsSourceControl = controlsForDispatch[i2];
                SurfaceControl leash = insetsSourceControl.getLeash();
                if (leash != null && leash.isValid()) {
                    InsetsSourceProvider insetsSourceProvider =
                            (InsetsSourceProvider)
                                    insetsPolicy.mStateController.mProviders.get(
                                            insetsSourceControl.getId());
                    SurfaceControl.Transaction syncTransaction =
                            (insetsSourceProvider == null
                                            || (windowContainer =
                                                            insetsSourceProvider.mWindowContainer)
                                                    == null)
                                    ? displayContent.getSyncTransaction()
                                    : windowContainer.getSyncTransaction();
                    syncTransaction.setAlpha(
                            leash, FullScreenMagnificationGestureHandler.MAX_SCALE);
                    syncTransaction.hide(leash);
                }
                i2++;
            }
        }
    }

    public InsetsPolicy(
            InsetsStateController insetsStateController, DisplayContent displayContent) {
        this.mStateController = insetsStateController;
        this.mDisplayContent = displayContent;
        DisplayPolicy displayPolicy = displayContent.mDisplayPolicy;
        this.mPolicy = displayPolicy;
        this.mHideNavBarForKeyboard =
                displayPolicy
                        .getContext()
                        .getResources()
                        .getBoolean(
                                R.bool
                                        .config_isWindowManagerCameraCompatSplitScreenAspectRatioEnabled);
        this.mTransientControlTarget = new ControlTarget(displayContent, "TransientControlTarget");
        this.mPermanentControlTarget = new ControlTarget(displayContent, "PermanentControlTarget");
        this.mDexForceImmersiveModeControlTarget = new ImmersiveControlTarget(displayContent);
    }

    public static InsetsState adjustInsetsForRoundedCorners(
            WindowToken windowToken, InsetsState insetsState, boolean z) {
        if (windowToken != null) {
            ActivityRecord asActivityRecord = windowToken.asActivityRecord();
            Task task = asActivityRecord != null ? asActivityRecord.task : null;
            if (task != null && !task.getWindowConfiguration().tasksAreFloating()) {
                if (z) {
                    insetsState = new InsetsState(insetsState);
                }
                insetsState.setRoundedCornerFrame(
                        windowToken.isFixedRotationTransforming()
                                ? windowToken.getFixedRotationTransformDisplayBounds()
                                : task.getBounds());
            }
        }
        return insetsState;
    }

    public static InsetsState adjustVisibilityForFakeControllingSource(
            InsetsState insetsState,
            int i,
            InsetsSource insetsSource,
            InsetsControlTarget insetsControlTarget) {
        boolean isRequestedVisible;
        if (insetsSource.getType() != i
                || insetsControlTarget == null
                || insetsSource.isVisible()
                        == (isRequestedVisible = insetsControlTarget.isRequestedVisible(i))) {
            return insetsState;
        }
        InsetsState insetsState2 = new InsetsState(insetsState);
        InsetsSource insetsSource2 = new InsetsSource(insetsSource);
        insetsSource2.setVisible(isRequestedVisible);
        insetsState2.addSource(insetsSource2);
        return insetsState2;
    }

    public static boolean canBeTopFullscreenOpaqueWindow(WindowState windowState) {
        WindowManager.LayoutParams layoutParams;
        int i;
        return (windowState == null
                        || (i = (layoutParams = windowState.mAttrs).type) < 1
                        || i > 99
                        || !layoutParams.isFullscreen()
                        || windowState.isFullyTransparent()
                        || windowState.inMultiWindowMode()
                        || windowState.isPopOver())
                ? false
                : true;
    }

    public final void abortTransient() {
        if (this.mShowingTransientTypes == 0) {
            return;
        }
        DisplayPolicy displayPolicy = this.mPolicy;
        StatusBarManagerInternal statusBarManagerInternal =
                displayPolicy.getStatusBarManagerInternal();
        DisplayContent displayContent = this.mDisplayContent;
        if (statusBarManagerInternal != null) {
            if (displayContent.isDexMode()) {
                ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                        .abortTransientToType(
                                displayContent.mDisplayId,
                                this.mShowingTransientTypes,
                                StatusBarManager.getNaturalBarTypeByDisplayId(
                                        displayPolicy.getContext(), displayContent.mDisplayId));
            } else {
                int i = displayContent.mDisplayId;
                int i2 = this.mShowingTransientTypes;
                StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                        (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
                StatusBarManagerService.this.getUiState(i, 0).mTransientBarTypes &= ~i2;
                IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                if (iStatusBar != null) {
                    try {
                        iStatusBar.abortTransient(i, i2);
                    } catch (RemoteException unused) {
                    }
                }
            }
            if (CoreRune.CARLIFE_NAVBAR && displayContent.isCarLifeDisplay()) {
                ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                        .abortTransientToType(
                                displayContent.mDisplayId, this.mShowingTransientTypes, 2);
            }
        }
        this.mShowingTransientTypes = 0;
        displayContent.setLayoutNeeded();
        displayContent.mWmService.requestTraversal();
        dispatchTransientSystemBarsVisibilityChanged(this.mFocusedWin, false, false);
    }

    public final InsetsState adjustInsetsForWindow(
            WindowState windowState, InsetsState insetsState, boolean z) {
        InsetsState insetsState2;
        InsetsSource peekSource;
        WindowState windowState2;
        if (z
                || ((this.mFakeStatusControlTarget == null && this.mFakeNavControlTarget == null)
                        || ((windowState2 = this.mPolicy.mNotificationShade) != null
                                && this.mFocusedWin == windowState2
                                && windowState2.isVisible()))) {
            insetsState2 = insetsState;
        } else {
            insetsState2 = insetsState;
            for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                InsetsSource sourceAt = insetsState2.sourceAt(sourceSize);
                insetsState2 =
                        adjustVisibilityForFakeControllingSource(
                                adjustVisibilityForFakeControllingSource(
                                        insetsState2,
                                        WindowInsets.Type.statusBars(),
                                        sourceAt,
                                        this.mFakeStatusControlTarget),
                                WindowInsets.Type.navigationBars(),
                                sourceAt,
                                this.mFakeNavControlTarget);
            }
        }
        boolean z2 = insetsState2 == insetsState;
        if (windowState.mIsImWindow) {
            boolean z3 = !this.mHideNavBarForKeyboard;
            InsetsState insetsState3 = insetsState2;
            for (int sourceSize2 = insetsState2.sourceSize() - 1; sourceSize2 >= 0; sourceSize2--) {
                InsetsSource sourceAt2 = insetsState2.sourceAt(sourceSize2);
                if (sourceAt2.getType() == WindowInsets.Type.navigationBars()
                        && windowState.isDexMode()
                        && windowState
                                .mWmService
                                .mAtmService
                                .mDexController
                                .mIsDexForceImmersiveModeEnabled) {
                    sourceAt2.setFrame(0, 0, 0, 0);
                }
                if (sourceAt2.getType() == WindowInsets.Type.navigationBars()
                        && sourceAt2.isVisible() != z3) {
                    if (insetsState3 == insetsState2 && z2) {
                        insetsState3 = new InsetsState(insetsState2);
                    }
                    InsetsSource insetsSource = new InsetsSource(sourceAt2);
                    insetsSource.setVisible(z3);
                    insetsState3.addSource(insetsSource);
                }
            }
            insetsState2 = insetsState3;
        } else {
            ActivityRecord activityRecord = windowState.mActivityRecord;
            if (activityRecord != null && activityRecord.mImeInsetsFrozenUntilStartInput) {
                InsetsSource peekSource2 = insetsState2.peekSource(InsetsSource.ID_IME);
                if (peekSource2 != null) {
                    boolean isRequestedVisible =
                            windowState.inFreeformWindowingMode()
                                    ? windowState.mActivityRecord.mLastImeShown
                                            && windowState.isRequestedVisible(
                                                    WindowInsets.Type.ime(), false)
                                    : windowState.isRequestedVisible(
                                            WindowInsets.Type.ime(), false);
                    if (z2) {
                        insetsState2 = new InsetsState(insetsState2);
                    }
                    InsetsSource insetsSource2 = new InsetsSource(peekSource2);
                    insetsSource2.setVisible(isRequestedVisible);
                    insetsState2.addSource(insetsSource2);
                }
            } else if (windowState.mImeInsetsConsumed
                    && (peekSource = insetsState2.peekSource(InsetsSource.ID_IME)) != null
                    && peekSource.isVisible()) {
                if (z2) {
                    insetsState2 = new InsetsState(insetsState2);
                }
                InsetsSource insetsSource3 = new InsetsSource(peekSource);
                insetsSource3.setVisible(false);
                insetsState2.addSource(insetsSource3);
            }
        }
        return adjustInsetsForRoundedCorners(
                windowState.mToken, insetsState2, insetsState2 == insetsState);
    }

    public final void dispatchTransientSystemBarsVisibilityChanged(
            WindowState windowState, final boolean z, final boolean z2) {
        Task task;
        final int i;
        if (windowState == null
                || (task = windowState.getTask()) == null
                || (i = task.mTaskId) == -1) {
            return;
        }
        TaskSystemBarsListenerController taskSystemBarsListenerController =
                this.mDisplayContent.mWmService.mTaskSystemBarsListenerController;
        taskSystemBarsListenerController.getClass();
        final HashSet hashSet = new HashSet(taskSystemBarsListenerController.mListeners);
        taskSystemBarsListenerController.mBackgroundExecutor.execute(
                new Runnable() { // from class:
                                 // com.android.server.wm.TaskSystemBarsListenerController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        HashSet hashSet2 = hashSet;
                        int i2 = i;
                        boolean z3 = z;
                        boolean z4 = z2;
                        Iterator it = hashSet2.iterator();
                        while (it.hasNext()) {
                            ((WindowManagerInternal.TaskSystemBarsListener) it.next())
                                    .onTransientSystemBarsVisibilityChanged(i2, z3, z4);
                        }
                    }
                });
    }

    public final void getInsetsForWindowMetrics(WindowToken windowToken, InsetsState insetsState) {
        insetsState.set(
                (windowToken == null || !windowToken.isFixedRotationTransforming())
                        ? this.mStateController.mState
                        : windowToken.isFixedRotationTransforming()
                                ? windowToken
                                        .mFixedRotationTransformState
                                        .mDisplayFrames
                                        .mInsetsState
                                : null,
                true);
        if (windowToken != null && windowToken.isLayoutNeededInUdcCutout()) {
            this.mDisplayContent.mUdcCutoutPolicy.adjustInsetsForUdc(windowToken, insetsState);
        }
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            if (isTransient(sourceAt.getType())) {
                sourceAt.setVisible(false);
            }
        }
        adjustInsetsForRoundedCorners(windowToken, insetsState, false);
        if (windowToken == null || !windowToken.hasSizeCompatBounds()) {
            return;
        }
        insetsState.scale(1.0f / windowToken.getCompatScale());
    }

    public final InsetsControlTarget getNavControlTarget(WindowState windowState, boolean z) {
        WindowManager.LayoutParams layoutParams;
        int i;
        InsetsSourceProvider controllableInsetProvider;
        WindowState windowState2 = this.mDisplayContent.mInputMethodWindow;
        if (windowState2 != null
                && windowState2.isVisible()
                && !this.mHideNavBarForKeyboard
                && !this.mDisplayContent.isDexMode()) {
            return this.mPermanentControlTarget;
        }
        if (!z && isTransient(WindowInsets.Type.navigationBars())) {
            return this.mTransientControlTarget;
        }
        if (this.mDisplayContent.mDisplayId == 2 && windowState != null) {
            WindowManager.LayoutParams layoutParams2 = windowState.mAttrs;
            if (layoutParams2.type == 2009 && (layoutParams2.insetsFlags.behavior & 2) != 0) {
                return this.mPermanentControlTarget;
            }
        }
        if (windowState != null
                && windowState.isDexMode()
                && !this.mDisplayContent.mWmService.isKeyguardShowingAndNotOccluded()
                && this.mDisplayContent
                        .mAtmService
                        .mDexController
                        .mIsDexForceImmersiveModeEnabled) {
            return this.mDexForceImmersiveModeControlTarget;
        }
        if (PolicyControl.shouldApplyImmersiveNavigation(windowState, false)) {
            return this.mPolicyControlTarget;
        }
        if (windowState == this.mPolicy.mNotificationShade) {
            return windowState;
        }
        if (windowState != null
                && (controllableInsetProvider = windowState.getControllableInsetProvider()) != null
                && controllableInsetProvider.mSource.getType()
                        == WindowInsets.Type.navigationBars()) {
            return windowState;
        }
        if (remoteInsetsControllerControlsSystemBars(windowState)) {
            ActivityRecord activityRecord = windowState.mActivityRecord;
            ComponentName componentName =
                    activityRecord != null ? activityRecord.mActivityComponent : null;
            DisplayContent.RemoteInsetsControlTarget remoteInsetsControlTarget =
                    this.mDisplayContent.mRemoteInsetsControlTarget;
            int i2 = windowState.mRequestedVisibleTypes;
            remoteInsetsControlTarget.getClass();
            try {
                remoteInsetsControlTarget.mRemoteInsetsController.topFocusedWindowChanged(
                        componentName, i2);
            } catch (RemoteException e) {
                Slog.w(
                        "WindowManager",
                        "Failed to deliver package in top focused window change",
                        e);
            }
            return this.mDisplayContent.mRemoteInsetsControlTarget;
        }
        int navigationBars = WindowInsets.Type.navigationBars();
        if ((this.mForcedShowingTypes & navigationBars) == navigationBars) {
            return this.mPermanentControlTarget;
        }
        WindowState windowState3 = this.mFocusedWin;
        if (windowState3 == null || !windowState3.inFreeformWindowingMode()) {
            DisplayContent displayContent = this.mDisplayContent;
            if (!displayContent.isDefaultDisplay
                    || displayContent.mAtmService.mDexController.getDexModeLocked() != 2
                    || !this.mDisplayContent
                            .mAtmService
                            .mDexController
                            .mDexImeWindowVisibleInDefaultDisplay) {
                DisplayPolicy displayPolicy = this.mPolicy;
                int navigationBars2 = WindowInsets.Type.navigationBars();
                if ((displayPolicy.mForciblyShownTypes & navigationBars2) == navigationBars2
                        && !z) {
                    return this.mTransientControlTarget;
                }
                WindowState windowState4 = this.mPolicy.mNotificationShade;
                return (canBeTopFullscreenOpaqueWindow(windowState)
                                || !this.mPolicy.topAppHidesSystemBar(
                                        WindowInsets.Type.navigationBars())
                                || (windowState4 != null && windowState4.canReceiveKeys(false))
                                || (windowState != null
                                        && windowState.isDexMode()
                                        && windowState.inFullscreenWindowingMode()
                                        && !windowState.isFullyTransparent()
                                        && ((i = (layoutParams = windowState.mAttrs).type) == 2009
                                                || (i == 2008 && layoutParams.isFullscreen()))))
                        ? windowState
                        : this.mPolicy.mTopFullscreenOpaqueWindowState;
            }
        }
        return this.mPermanentControlTarget;
    }

    public InsetsControlTarget getPermanentControlTarget() {
        return this.mPermanentControlTarget;
    }

    public final InsetsControlTarget getStatusControlTarget(WindowState windowState, boolean z) {
        if (!z && isTransient(WindowInsets.Type.statusBars())) {
            return this.mTransientControlTarget;
        }
        if (PolicyControl.shouldApplyImmersiveStatus(windowState)) {
            return this.mPolicyControlTarget;
        }
        WindowState windowState2 = this.mPolicy.mNotificationShade;
        if (windowState == windowState2) {
            return windowState;
        }
        if (!remoteInsetsControllerControlsSystemBars(windowState)) {
            int statusBars = WindowInsets.Type.statusBars();
            if ((this.mForcedShowingTypes & statusBars) == statusBars) {
                return this.mPermanentControlTarget;
            }
            DisplayPolicy displayPolicy = this.mPolicy;
            int statusBars2 = WindowInsets.Type.statusBars();
            return ((displayPolicy.mForciblyShownTypes & statusBars2) != statusBars2 || z)
                    ? (canBeTopFullscreenOpaqueWindow(windowState)
                                    || !this.mPolicy.topAppHidesSystemBar(
                                            WindowInsets.Type.statusBars())
                                    || (windowState2 != null && windowState2.canReceiveKeys(false)))
                            ? (windowState == null || !windowState.inFreeformWindowingMode())
                                    ? (windowState == null || !windowState.isPopOver())
                                            ? windowState
                                            : this.mPolicy.mTopFullscreenOpaqueWindowState
                                    : this.mPolicy.mTopFullscreenOpaqueWindowState
                            : this.mPolicy.mTopFullscreenOpaqueWindowState
                    : this.mTransientControlTarget;
        }
        ActivityRecord activityRecord = windowState.mActivityRecord;
        ComponentName componentName =
                activityRecord != null ? activityRecord.mActivityComponent : null;
        DisplayContent.RemoteInsetsControlTarget remoteInsetsControlTarget =
                this.mDisplayContent.mRemoteInsetsControlTarget;
        int i = windowState.mRequestedVisibleTypes;
        remoteInsetsControlTarget.getClass();
        try {
            remoteInsetsControlTarget.mRemoteInsetsController.topFocusedWindowChanged(
                    componentName, i);
        } catch (RemoteException e) {
            Slog.w("WindowManager", "Failed to deliver package in top focused window change", e);
        }
        return this.mDisplayContent.mRemoteInsetsControlTarget;
    }

    public InsetsControlTarget getTransientControlTarget() {
        return this.mTransientControlTarget;
    }

    public final boolean hasHiddenSources(int i) {
        InsetsState insetsState = this.mStateController.mState;
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            if ((sourceAt.getType() & i) != 0
                    && !sourceAt.getFrame().isEmpty()
                    && !sourceAt.isVisible()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isTransient(int i) {
        return (this.mShowingTransientTypes & i) != 0;
    }

    public final void onRequestedVisibleTypesChanged(WindowState windowState) {
        InsetsStateController insetsStateController = this.mStateController;
        insetsStateController.onRequestedVisibleTypesChanged(windowState);
        if (this.mShowingTransientTypes != 0) {
            boolean z = insetsStateController.getImeSourceProvider().mClientVisible;
            int i = 0;
            for (int size = insetsStateController.mProviders.size() - 1; size >= 0; size--) {
                InsetsSourceProvider insetsSourceProvider =
                        (InsetsSourceProvider) insetsStateController.mProviders.valueAt(size);
                if (windowState == insetsSourceProvider.mFakeControlTarget) {
                    i |= insetsSourceProvider.mSource.getType();
                }
            }
            int navigationBars =
                    (windowState.mRequestedVisibleTypes & i)
                            | (z ? WindowInsets.Type.navigationBars() : 0);
            int i2 = this.mShowingTransientTypes;
            int i3 = ~navigationBars;
            this.mShowingTransientTypes = i2 & i3;
            if (navigationBars != 0) {
                DisplayContent displayContent = this.mDisplayContent;
                displayContent.setLayoutNeeded();
                displayContent.mWmService.requestTraversal();
                DisplayPolicy displayPolicy = this.mPolicy;
                StatusBarManagerInternal statusBarManagerInternal =
                        displayPolicy.getStatusBarManagerInternal();
                if (statusBarManagerInternal != null) {
                    if (displayContent.isDexMode()) {
                        ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                                .abortTransientToType(
                                        displayContent.mDisplayId,
                                        navigationBars,
                                        StatusBarManager.getNaturalBarTypeByDisplayId(
                                                displayPolicy.getContext(),
                                                displayContent.mDisplayId));
                    } else {
                        int i4 = displayContent.mDisplayId;
                        StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                                (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
                        StatusBarManagerService.UiState uiState =
                                StatusBarManagerService.this.getUiState(i4, 0);
                        uiState.mTransientBarTypes = i3 & uiState.mTransientBarTypes;
                        IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                        if (iStatusBar != null) {
                            try {
                                iStatusBar.abortTransient(i4, navigationBars);
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                    if (CoreRune.CARLIFE_NAVBAR && displayContent.isCarLifeDisplay()) {
                        ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                                .abortTransientToType(displayContent.mDisplayId, navigationBars, 2);
                    }
                }
            }
        }
        updateBarControlTarget(this.mFocusedWin);
    }

    public final boolean remoteInsetsControllerControlsSystemBars(WindowState windowState) {
        DisplayContent displayContent;
        int i;
        return windowState != null
                && this.mPolicy.mRemoteInsetsControllerControlsSystemBars
                && (displayContent = this.mDisplayContent) != null
                && displayContent.mRemoteInsetsControlTarget != null
                && (i = windowState.mAttrs.type) >= 1
                && i <= 99;
    }

    public final void showTransient(int i, boolean z) {
        int i2 = this.mShowingTransientTypes;
        InsetsState insetsState = this.mStateController.mState;
        if ((WindowInsets.Type.navigationBars() & i) != 0) {
            TaskbarController taskbarController =
                    this.mDisplayContent.mDisplayPolicy.mExt.mTaskbarController;
            WindowState windowState = taskbarController.mTaskbarWin;
            InsetsSourceProvider insetsSourceProvider = null;
            if (windowState != null && windowState.mHasSurface) {
                WindowState windowState2 = taskbarController.mDisplayPolicy.mNavigationBar;
                if (windowState2 != null) {
                    insetsSourceProvider = windowState2.getControllableInsetProvider();
                }
            } else if (windowState != null) {
                insetsSourceProvider = windowState.getControllableInsetProvider();
            }
            if (insetsSourceProvider != null
                    && insetsState.peekSource(insetsSourceProvider.mSource.getId()) != null) {
                InsetsState insetsState2 = new InsetsState(insetsState);
                insetsState2.removeSource(insetsSourceProvider.mSource.getId());
                insetsState = insetsState2;
            }
        }
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            if (!sourceAt.isVisible()) {
                int type = sourceAt.getType();
                if ((sourceAt.getType() & i) != 0) {
                    i2 |= type;
                }
            }
        }
        if (this.mShowingTransientTypes != i2) {
            this.mShowingTransientTypes = i2;
            StatusBarManagerInternal statusBarManagerInternal =
                    this.mPolicy.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                if (this.mDisplayContent.isDexMode()) {
                    ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                            .showTransientToType(
                                    this.mDisplayContent.mDisplayId,
                                    i2,
                                    StatusBarManager.getNaturalBarTypeByDisplayId(
                                            this.mPolicy.getContext(),
                                            this.mDisplayContent.mDisplayId),
                                    z);
                } else {
                    int i3 = this.mDisplayContent.mDisplayId;
                    StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                            (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
                    StatusBarManagerService.this.getUiState(i3, 0).mTransientBarTypes |= i2;
                    IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                    if (iStatusBar != null) {
                        try {
                            iStatusBar.showTransient(i3, i2, z);
                        } catch (RemoteException unused) {
                        }
                    }
                }
                if (CoreRune.CARLIFE_NAVBAR && this.mDisplayContent.isCarLifeDisplay()) {
                    ((StatusBarManagerService.AnonymousClass2) statusBarManagerInternal)
                            .showTransientToType(this.mDisplayContent.mDisplayId, i2, 2, z);
                }
            }
            updateBarControlTarget(this.mFocusedWin);
            dispatchTransientSystemBarsVisibilityChanged(
                    this.mFocusedWin,
                    (i2 & (WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars()))
                            != 0,
                    z);
        }
    }

    public final void updateBarControlTarget(WindowState windowState) {
        boolean z = windowState != null && windowState.isDexMode();
        if ((windowState == this.mPolicy.mNotificationShade && !z)
                || (!PolicyControl.shouldApplyImmersiveStatus(windowState)
                        && !PolicyControl.shouldApplyImmersiveNavigation(windowState, false))) {
            if (this.mFocusedWin != windowState) {
                abortTransient();
            } else if (this.mShowingTransientTypes <= 0
                    && this.mLastTransientRequestedByPolicyControl
                    && windowState != null
                    && !PolicyControl.shouldApplyImmersiveStatus(windowState)
                    && !PolicyControl.shouldApplyImmersiveNavigation(windowState, false)
                    && (!windowState.canShowTransient()
                            || windowState.isRequestedVisible(
                                    WindowInsets.Type.systemBars(), false))) {
                abortTransient();
            }
        }
        this.mFocusedWin = windowState;
        WindowState windowState2 = this.mPolicy.mNotificationShade;
        WindowState windowState3 = this.mPolicy.mTopFullscreenOpaqueWindowState;
        InsetsControlTarget statusControlTarget = getStatusControlTarget(windowState, false);
        InsetsControlTarget insetsControlTarget = null;
        this.mFakeStatusControlTarget =
                statusControlTarget == this.mTransientControlTarget
                        ? getStatusControlTarget(windowState, true)
                        : statusControlTarget == windowState2
                                ? getStatusControlTarget(windowState3, true)
                                : null;
        InsetsControlTarget navControlTarget = getNavControlTarget(windowState, false);
        if (navControlTarget == this.mTransientControlTarget) {
            insetsControlTarget = getNavControlTarget(windowState, true);
        } else if (navControlTarget == windowState2) {
            insetsControlTarget = getNavControlTarget(windowState3, true);
        }
        this.mFakeNavControlTarget = insetsControlTarget;
        InsetsStateController insetsStateController = this.mStateController;
        InsetsControlTarget insetsControlTarget2 = this.mFakeStatusControlTarget;
        for (int size = insetsStateController.mProviders.size() - 1; size >= 0; size--) {
            InsetsSourceProvider insetsSourceProvider =
                    (InsetsSourceProvider) insetsStateController.mProviders.valueAt(size);
            int type = insetsSourceProvider.mSource.getType();
            if (type == WindowInsets.Type.statusBars()) {
                insetsStateController.onControlTargetChanged(
                        statusControlTarget, insetsSourceProvider, false);
                insetsStateController.onControlTargetChanged(
                        insetsControlTarget2, insetsSourceProvider, true);
            } else if (type == WindowInsets.Type.navigationBars()) {
                insetsStateController.onControlTargetChanged(
                        navControlTarget, insetsSourceProvider, false);
                insetsStateController.onControlTargetChanged(
                        insetsControlTarget, insetsSourceProvider, true);
            }
        }
        insetsStateController.notifyPendingInsetsControlChanged();
        BarWindow.m1063$$Nest$mupdateVisibility(
                this.mStatusBar, statusControlTarget, WindowInsets.Type.statusBars());
        BarWindow.m1063$$Nest$mupdateVisibility(
                this.mNavBar, navControlTarget, WindowInsets.Type.navigationBars());
    }
}
