package com.android.server.wm;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;

import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.inputmethod.ImfLock;
import com.android.server.inputmethod.InputMethodManagerService;
import com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda5;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ImeInsetsSourceProvider extends InsetsSourceProvider {
    public boolean mFrozen;
    public boolean mGivenInsetsReady;
    public InsetsControlTarget mImeRequester;
    public boolean mImeShowing;
    public final InsetsSource mLastSource;
    public boolean mServerVisible;
    public ImeTracker.Token mStatsToken;

    public ImeInsetsSourceProvider(
            InsetsSource insetsSource,
            InsetsStateController insetsStateController,
            DisplayContent displayContent) {
        super(insetsSource, insetsStateController, displayContent);
        this.mLastSource = new InsetsSource(InsetsSource.ID_IME, WindowInsets.Type.ime());
        this.mGivenInsetsReady = false;
    }

    public static boolean isAboveImeLayeringTarget(
            InsetsControlTarget insetsControlTarget, InsetsControlTarget insetsControlTarget2) {
        return insetsControlTarget.getWindow() != null
                && insetsControlTarget2.getWindow().getParentWindow() == insetsControlTarget
                && insetsControlTarget2.getWindow().mSubLayer
                        > insetsControlTarget.getWindow().mSubLayer;
    }

    public static boolean isImeTargetWindowClosing(WindowState windowState) {
        ActivityRecord activityRecord;
        return windowState.mAnimatingExit
                || ((activityRecord = windowState.mActivityRecord) != null
                        && ((activityRecord.inTransitionSelfOrParent()
                                        && !windowState.mActivityRecord.isVisibleRequested())
                                || windowState.mActivityRecord.mWillCloseOrEnterPip));
    }

    public final void abortShowImePostLayout() {
        if (this.mImeRequester == null) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_IME, -6529782994356455131L, 0, null, null);
        }
        Trace.asyncTraceEnd(32L, "WMS.showImePostLayout", 0);
        logIsScheduledAndReadyToShowIme(true);
        ImeTracker.forLogging().onFailed(this.mStatsToken, 43);
        this.mImeRequester = null;
        this.mStatsToken = null;
    }

    public final void checkAndStartShowImePostLayout() {
        if (isScheduledAndReadyToShowIme()) {
            if (Flags.refactorInsetsController()) {
                abortShowImePostLayout();
                InsetsSourceControl insetsSourceControl = this.mControl;
                if (insetsSourceControl == null
                        || insetsSourceControl.getLeash() == null
                        || this.mControlTarget.getWindow() == null
                        || this.mControlTarget.getWindow().mGivenInsetsPending) {
                    return;
                }
                this.mControlTarget.notifyInsetsControlChanged(this.mDisplayContent.mDisplayId);
                return;
            }
            ImeTracker.forLogging().onProgress(this.mStatsToken, 18);
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled;
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_IME, 8923821958256605927L, 0, null, null);
            }
            InsetsControlTarget insetsControlTarget = this.mControlTarget;
            if (zArr[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_DEBUG_IME,
                        -3529253275087521638L,
                        0,
                        null,
                        String.valueOf(
                                insetsControlTarget.getWindow() != null
                                        ? insetsControlTarget.getWindow().getName()
                                        : ""));
            }
            this.mImeShowing = true;
            insetsControlTarget.showInsets(WindowInsets.Type.ime(), true, this.mStatsToken);
            Trace.asyncTraceEnd(32L, "WMS.showImePostLayout", 0);
            InsetsControlTarget insetsControlTarget2 = this.mImeRequester;
            if (insetsControlTarget != insetsControlTarget2 && zArr[3]) {
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_IME,
                        7927729210300708186L,
                        0,
                        null,
                        String.valueOf(
                                insetsControlTarget2.getWindow() != null
                                        ? this.mImeRequester.getWindow().getName()
                                        : ""));
            }
            this.mImeRequester = null;
            this.mStatsToken = null;
        }
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final void dump(PrintWriter printWriter, String str) {
        super.dump(printWriter, str);
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mImeShowing=");
        printWriter.print(this.mImeShowing);
        if (this.mImeRequester != null) {
            printWriter.print(str2);
            printWriter.print("showImePostLayout pending for mImeRequester=");
            printWriter.print(this.mImeRequester);
            printWriter.println();
        } else {
            printWriter.print(str2);
            printWriter.print("showImePostLayout not scheduled, mImeRequester=null");
            printWriter.println();
        }
        printWriter.println();
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        InsetsControlTarget insetsControlTarget = this.mImeRequester;
        WindowState window = insetsControlTarget != null ? insetsControlTarget.getWindow() : null;
        if (window != null) {
            window.dumpDebug(protoOutputStream, 1146756268034L, i);
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final InsetsSourceControl getControl(InsetsControlTarget insetsControlTarget) {
        StartingData startingData;
        WindowState window;
        InsetsSourceControl control = super.getControl(insetsControlTarget);
        if (control != null
                && insetsControlTarget != null
                && insetsControlTarget.getWindow() != null) {
            WindowState window2 = insetsControlTarget.getWindow();
            Task task = window2.getTask();
            if (task != null) {
                startingData = window2.mActivityRecord.mStartingData;
                if (startingData == null
                        && (window = task.getWindow(new Task$$ExternalSyntheticLambda0(6)))
                                != null) {
                    startingData = window.mStartingData;
                }
            } else {
                startingData = null;
            }
            control.setSkipAnimationOnce(startingData != null && startingData.hasImeSurface());
        }
        return control;
    }

    public final void invokeOnImeRequestedChangedListener(final WindowState windowState) {
        WindowManagerService windowManagerService = this.mDisplayContent.mWmService;
        final WindowManagerInternal.OnImeRequestedChangedListener onImeRequestedChangedListener =
                windowManagerService.mOnImeRequestedChangedListener;
        if (onImeRequestedChangedListener == null || windowState == null) {
            return;
        }
        windowManagerService.mH.post(
                new Runnable() { // from class:
                                 // com.android.server.wm.ImeInsetsSourceProvider$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WindowManagerInternal.OnImeRequestedChangedListener
                                onImeRequestedChangedListener2 =
                                        WindowManagerInternal.OnImeRequestedChangedListener.this;
                        WindowState windowState2 = windowState;
                        IBinder asBinder = windowState2.mClient.asBinder();
                        boolean isRequestedVisible =
                                windowState2.isRequestedVisible(WindowInsets.Type.ime(), false);
                        InputMethodManagerService inputMethodManagerService =
                                ((InputMethodManagerService$$ExternalSyntheticLambda5)
                                                onImeRequestedChangedListener2)
                                        .f$0;
                        inputMethodManagerService.getClass();
                        if (Flags.refactorInsetsController()) {
                            if (isRequestedVisible) {
                                inputMethodManagerService.showSoftInputInternal(asBinder);
                                return;
                            }
                            Trace.traceBegin(32L, "IMMS.hideSoftInputInternal");
                            ImeTracing.getInstance()
                                    .triggerManagerServiceDump(
                                            "InputMethodManagerService#hideSoftInput",
                                            inputMethodManagerService.mDumper);
                            synchronized (ImfLock.class) {
                                try {
                                    long clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        inputMethodManagerService.hideCurrentInputLocked(
                                                asBinder, null, 0, null, 4);
                                    } finally {
                                        Binder.restoreCallingIdentity(clearCallingIdentity);
                                        Trace.traceEnd(32L);
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                });
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final boolean isLeashReadyForDispatching() {
        if (!Flags.refactorInsetsController()) {
            return this.mIsLeashReadyForDispatching;
        }
        WindowContainer windowContainer = this.mWindowContainer;
        WindowState asWindowState =
                windowContainer != null ? windowContainer.asWindowState() : null;
        return this.mIsLeashReadyForDispatching
                && this.mServerVisible
                && (asWindowState != null && asWindowState.isDrawn())
                && this.mGivenInsetsReady;
    }

    public boolean isScheduledAndReadyToShowIme() {
        WindowContainer windowContainer;
        DisplayContent displayContent;
        InsetsControlTarget imeTarget;
        InsetsControlTarget insetsControlTarget;
        InsetsControlTarget insetsControlTarget2;
        InsetsSourceControl insetsSourceControl;
        if (this.mImeRequester == null
                || !this.mServerVisible
                || this.mFrozen
                || (windowContainer = this.mWindowContainer) == null) {
            return false;
        }
        WindowState asWindowState = windowContainer.asWindowState();
        if (asWindowState == null) {
            throw new IllegalArgumentException("IME insets must be provided by a window.");
        }
        if (!asWindowState.isDrawn()
                || asWindowState.mGivenInsetsPending
                || (imeTarget = (displayContent = this.mDisplayContent).getImeTarget(0)) == null
                || (insetsControlTarget = this.mControlTarget) == null
                || insetsControlTarget != displayContent.getImeTarget(2)
                || this.mStateController.mPendingControlChanged.contains(insetsControlTarget)) {
            return false;
        }
        if (((insetsControlTarget == this.mControlTarget
                                && this.mIsLeashReadyForDispatching
                                && (insetsSourceControl = this.mControl) != null)
                        ? insetsSourceControl.getLeash()
                        : null)
                == null) {
            return false;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_IME,
                    -6629998049460863403L,
                    0,
                    null,
                    String.valueOf(imeTarget.getWindow().getName()),
                    String.valueOf(
                            this.mImeRequester.getWindow() == null
                                    ? this.mImeRequester
                                    : this.mImeRequester.getWindow().getName()));
        }
        InsetsControlTarget insetsControlTarget3 = this.mImeRequester;
        if ((isImeTargetWindowClosing(imeTarget.getWindow()) || insetsControlTarget3 != imeTarget)
                && !isAboveImeLayeringTarget(this.mImeRequester, imeTarget)
                && this.mImeRequester != displayContent.getImeFallback()
                && (insetsControlTarget2 = this.mImeRequester) != displayContent.mImeInputTarget) {
            if (this.mControlTarget != insetsControlTarget2) {
                return false;
            }
            if (insetsControlTarget2.getWindow() != null
                    && isImeTargetWindowClosing(insetsControlTarget2.getWindow())) {
                return false;
            }
        }
        return true;
    }

    public final void logIsScheduledAndReadyToShowIme(boolean z) {
        InsetsSourceControl insetsSourceControl;
        WindowContainer windowContainer = this.mWindowContainer;
        SurfaceControl surfaceControl = null;
        WindowState asWindowState =
                windowContainer != null ? windowContainer.asWindowState() : null;
        DisplayContent displayContent = this.mDisplayContent;
        boolean z2 = false;
        InsetsControlTarget imeTarget = displayContent.getImeTarget(0);
        InsetsControlTarget insetsControlTarget = this.mControlTarget;
        StringBuilder sb = new StringBuilder("showImePostLayout ");
        sb.append(z ? "aborted" : "cancelled");
        sb.append(", isScheduledAndReadyToShowIme: ");
        sb.append(isScheduledAndReadyToShowIme());
        sb.append(", mImeRequester: ");
        sb.append(this.mImeRequester);
        sb.append(", serverVisible: ");
        sb.append(this.mServerVisible);
        sb.append(", frozen: ");
        sb.append(this.mFrozen);
        sb.append(", mWindowContainer is: ");
        sb.append(this.mWindowContainer != null ? "non-null" : "null");
        sb.append(", windowState: ");
        sb.append(asWindowState);
        if (asWindowState != null) {
            sb.append(", isDrawn: ");
            sb.append(asWindowState.isDrawn());
            sb.append(", mGivenInsetsPending: ");
            sb.append(asWindowState.mGivenInsetsPending);
        }
        sb.append(", dcTarget: ");
        sb.append(imeTarget);
        sb.append(", controlTarget: ");
        sb.append(insetsControlTarget);
        if (this.mImeRequester != null && imeTarget != null && insetsControlTarget != null) {
            sb.append("\ncontrolTarget == DisplayContent.controlTarget: ");
            sb.append(insetsControlTarget == displayContent.getImeTarget(2));
            sb.append(", hasPendingControls: ");
            sb.append(this.mStateController.mPendingControlChanged.contains(insetsControlTarget));
            if (insetsControlTarget == this.mControlTarget
                    && this.mIsLeashReadyForDispatching
                    && (insetsSourceControl = this.mControl) != null) {
                surfaceControl = insetsSourceControl.getLeash();
            }
            boolean z3 = surfaceControl != null;
            sb.append(", leash is: ");
            sb.append(z3 ? "non-null" : "null");
            if (!z3) {
                sb.append(", control is: ");
                sb.append(this.mControl != null ? "non-null" : "null");
                sb.append(", mIsLeashReadyForDispatching: ");
                sb.append(this.mIsLeashReadyForDispatching);
            }
            sb.append(", isImeLayeringTarget: ");
            sb.append(
                    !isImeTargetWindowClosing(imeTarget.getWindow())
                            && this.mImeRequester == imeTarget);
            sb.append(", isAboveImeLayeringTarget: ");
            sb.append(isAboveImeLayeringTarget(this.mImeRequester, imeTarget));
            sb.append(", isImeFallbackTarget: ");
            sb.append(this.mImeRequester == displayContent.getImeFallback());
            sb.append(", isImeInputTarget: ");
            sb.append(this.mImeRequester == displayContent.mImeInputTarget);
            sb.append(", sameAsImeControlTarget: ");
            InsetsControlTarget insetsControlTarget2 = this.mImeRequester;
            if (this.mControlTarget == insetsControlTarget2
                    && (insetsControlTarget2.getWindow() == null
                            || !isImeTargetWindowClosing(insetsControlTarget2.getWindow()))) {
                z2 = true;
            }
            sb.append(z2);
        }
        Slog.d("ImeInsetsSourceProvider", sb.toString());
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final void onPostLayout() {
        super.onPostLayout();
        if (Flags.refactorInsetsController()) {
            WindowContainer windowContainer = this.mWindowContainer;
            WindowState asWindowState =
                    windowContainer != null ? windowContainer.asWindowState() : null;
            boolean z = asWindowState != null && asWindowState.mGivenInsetsPending;
            if (this.mGivenInsetsReady || !this.mServerVisible || z) {
                return;
            }
            this.mGivenInsetsReady = true;
            this.mStateController.notifyControlChanged(this.mControlTarget);
        }
    }

    public final void onSourceChanged() {
        if (this.mLastSource.equals(this.mSource)) {
            return;
        }
        this.mLastSource.set(this.mSource);
        DisplayContent displayContent = this.mDisplayContent;
        displayContent.mWmService.mH.obtainMessage(41, displayContent).sendToTarget();
    }

    public final void reportImeDrawnForOrganizer(InsetsControlTarget insetsControlTarget) {
        WindowState window = insetsControlTarget.getWindow();
        if (window == null || window.getTask() == null || !window.getTask().isOrganized()) {
            return;
        }
        TaskOrganizerController taskOrganizerController =
                this.mWindowContainer.mWmService.mAtmService.mTaskOrganizerController;
        Task task = insetsControlTarget.getWindow().getTask();
        TaskOrganizerController.TaskOrganizerState taskOrganizerState =
                (TaskOrganizerController.TaskOrganizerState)
                        taskOrganizerController.mTaskOrganizerStates.get(
                                task.mTaskOrganizer.asBinder());
        if (taskOrganizerState != null) {
            try {
                taskOrganizerState.mOrganizer.mTaskOrganizer.onImeDrawnOnTask(task.mTaskId);
            } catch (RemoteException e) {
                Slog.e("TaskOrganizerController", "Exception sending onImeDrawnOnTask callback", e);
            }
        }
    }

    public final void reportImeDrawnForOrganizerIfNeeded(InsetsControlTarget insetsControlTarget) {
        if (insetsControlTarget.getWindow() == null) {
            return;
        }
        WindowToken windowToken =
                this.mWindowContainer.asWindowState() != null
                        ? this.mWindowContainer.asWindowState().mToken
                        : null;
        AsyncRotationController asyncRotationController =
                this.mDisplayContent.getAsyncRotationController();
        if (asyncRotationController == null
                || !asyncRotationController.mTargetWindowTokens.containsKey(windowToken)) {
            if (windowToken == null || !windowToken.isSelfAnimating(0, 64)) {
                reportImeDrawnForOrganizer(insetsControlTarget);
            }
        }
    }

    public final void scheduleShowImePostLayout(
            InsetsControlTarget insetsControlTarget, ImeTracker.Token token) {
        InsetsControlTarget insetsControlTarget2;
        if (this.mImeRequester == null) {
            Trace.asyncTraceBegin(32L, "WMS.showImePostLayout", 0);
        } else {
            logIsScheduledAndReadyToShowIme(false);
            ImeTracker.forLogging().onCancelled(this.mStatsToken, 18);
        }
        boolean z =
                (insetsControlTarget.getWindow() == null
                                || (insetsControlTarget2 = this.mImeRequester)
                                        == insetsControlTarget
                                || insetsControlTarget2 == null
                                || insetsControlTarget2.getWindow() == null
                                || this.mImeRequester.getWindow().mActivityRecord
                                        != insetsControlTarget.getWindow().mActivityRecord)
                        ? false
                        : true;
        this.mImeRequester = insetsControlTarget;
        this.mStatsToken = token;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled;
        if (z) {
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_IME, -786355099910065121L, 0, null, null);
            }
            checkAndStartShowImePostLayout();
        } else {
            if (insetsControlTarget.getWindow() != null
                    && this.mImeRequester.getWindow().inFreeformWindowingMode()
                    && this.mImeRequester.getWindow().isGoneForLayout()) {
                abortShowImePostLayout();
                return;
            }
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_IME,
                        2634707843050913730L,
                        0,
                        null,
                        String.valueOf(
                                this.mImeRequester.getWindow() == null
                                        ? this.mImeRequester
                                        : this.mImeRequester.getWindow().getName()));
            }
            this.mDisplayContent.mWmService.requestTraversal();
        }
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final void setClientVisible(boolean z) {
        InsetsControlTarget insetsControlTarget;
        boolean z2 = this.mClientVisible;
        super.setClientVisible(z);
        DisplayContent displayContent = this.mDisplayContent;
        if (!z2
                && this.mClientVisible
                && (insetsControlTarget = this.mControlTarget) != null
                && insetsControlTarget.getWindow() != null
                && insetsControlTarget.getWindow().mActivityRecord == null) {
            displayContent.assignWindowLayers(false);
        }
        if (displayContent.isDefaultDisplay
                && displayContent.mAtmService.mDexController.getDexModeLocked() == 2) {
            DexController dexController = displayContent.mAtmService.mDexController;
            if (z == dexController.mDexImeWindowVisibleInDefaultDisplay) {
                return;
            }
            if (z) {
                dexController.updateDexImeWindowStateIfNeededLocked();
                return;
            }
            DexController.H h = dexController.mH;
            DexController.AnonymousClass1 anonymousClass1 =
                    dexController.mUpdateDexImeStateRunnable;
            h.removeCallbacks(anonymousClass1);
            dexController.mH.postDelayed(
                    anonymousClass1, DexController.UPDATE_DEX_IME_STATE_DELAY_MS);
        }
    }

    public final void setFrozen(boolean z) {
        if (this.mFrozen == z) {
            return;
        }
        this.mFrozen = z;
        if (z) {
            return;
        }
        super.setServerVisible(this.mServerVisible);
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final void setServerVisible(boolean z) {
        if (this.mServerVisible != z) {
            this.mServerVisible = z;
            if (Flags.refactorInsetsController() && !z && !this.mFrozen) {
                this.mGivenInsetsReady = false;
                updateControlForTarget(this.mControlTarget, true);
            }
        }
        if (this.mFrozen) {
            return;
        }
        super.setServerVisible(z);
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final boolean updateClientVisibility(InsetsControlTarget insetsControlTarget) {
        InsetsControlTarget insetsControlTarget2 = this.mControlTarget;
        DisplayContent displayContent = this.mDisplayContent;
        if (insetsControlTarget != insetsControlTarget2) {
            if (Flags.refactorInsetsController()
                    && insetsControlTarget == displayContent.mImeInputTarget) {
                boolean isRequestedVisible =
                        insetsControlTarget.isRequestedVisible(WindowInsets.Type.ime());
                if (insetsControlTarget2 != null) {
                    insetsControlTarget2.setImeInputTargetRequestedVisibility(isRequestedVisible);
                } else {
                    InsetsControlTarget imeHostOrFallback =
                            displayContent.getImeHostOrFallback(insetsControlTarget.getWindow());
                    if (imeHostOrFallback != insetsControlTarget) {
                        imeHostOrFallback.setImeInputTargetRequestedVisibility(isRequestedVisible);
                    }
                }
                invokeOnImeRequestedChangedListener(insetsControlTarget.getWindow());
            }
            return false;
        }
        boolean updateClientVisibility = super.updateClientVisibility(insetsControlTarget);
        if (!Flags.refactorInsetsController()
                && updateClientVisibility
                && insetsControlTarget.isRequestedVisible(this.mSource.getType())) {
            reportImeDrawnForOrganizerIfNeeded(insetsControlTarget);
        }
        boolean[] zArr = new boolean[1];
        InputTarget inputTarget = displayContent.mImeInputTarget;
        WindowState windowState = null;
        ActivityRecord activityRecord =
                inputTarget != null ? inputTarget.getActivityRecord() : null;
        if (displayContent.mImeInputTarget != displayContent.mLastImeInputTarget
                || (activityRecord != null
                        && activityRecord.isVisibleRequested()
                        && activityRecord.mImeInsetsFrozenUntilStartInput)) {
            displayContent.forAllActivities(new DisplayContent$$ExternalSyntheticLambda20(2, zArr));
        }
        boolean z = updateClientVisibility | zArr[0];
        if (Flags.refactorInsetsController() && z) {
            if (insetsControlTarget.getWindow() != null) {
                windowState = insetsControlTarget.getWindow();
            } else {
                InputTarget inputTarget2 = displayContent.mImeInputTarget;
                if (inputTarget2 != null) {
                    windowState = inputTarget2.getWindowState();
                }
            }
            invokeOnImeRequestedChangedListener(windowState);
        }
        return z;
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final void updateControlForTarget(InsetsControlTarget insetsControlTarget, boolean z) {
        if (insetsControlTarget != null && insetsControlTarget.getWindow() != null) {
            insetsControlTarget = insetsControlTarget.getWindow().getImeControlTarget();
        }
        super.updateControlForTarget(insetsControlTarget, z);
        if (!Flags.refactorInsetsController() || insetsControlTarget == null) {
            return;
        }
        invokeOnImeRequestedChangedListener(insetsControlTarget.getWindow());
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final void updateSourceFrame(Rect rect) {
        super.updateSourceFrame(rect);
        onSourceChanged();
    }

    @Override // com.android.server.wm.InsetsSourceProvider
    public final void updateVisibility() {
        InsetsControlTarget insetsControlTarget;
        boolean isVisible = this.mSource.isVisible();
        super.updateVisibility();
        if (Flags.refactorInsetsController()
                && this.mSource.isVisible()
                && !isVisible
                && (insetsControlTarget = this.mImeRequester) != null) {
            reportImeDrawnForOrganizerIfNeeded(insetsControlTarget);
        }
        onSourceChanged();
    }
}
