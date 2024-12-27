package com.android.server.wm;

import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.WindowManager;

import com.android.internal.os.logging.MetricsLoggerWrapper;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

public final class WindowSurfaceController {
    public final WindowStateAnimator mAnimator;
    public final WindowManagerService mService;
    public SurfaceControl mSurfaceControl;
    public boolean mSurfaceShown = false;
    public final Session mWindowSession;
    public final int mWindowType;
    public final String title;

    public WindowSurfaceController(
            String str, int i, int i2, WindowStateAnimator windowStateAnimator, int i3) {
        this.mAnimator = windowStateAnimator;
        this.title = str;
        this.mService = windowStateAnimator.mService;
        this.mWindowType = i3;
        WindowState windowState = windowStateAnimator.mWin;
        Session session = windowState.mSession;
        this.mWindowSession = session;
        if (session.mPid != Process.myPid()) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "$_");
            m.append(windowStateAnimator.mWin.mSession.mPid);
            str = m.toString();
        }
        Trace.traceBegin(32L, "new SurfaceControl");
        SurfaceControl.Builder bLASTLayer =
                windowState
                        .makeSurface()
                        .setParent(windowState.mSurfaceControl)
                        .setName(str)
                        .setFormat(i)
                        .setFlags(i2)
                        .setMetadata(2, i3)
                        .setMetadata(1, session.mUid)
                        .setMetadata(6, session.mPid)
                        .setCallsite("WindowSurfaceController")
                        .setBLASTLayer();
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mSurfaceControl = bLASTLayer.build();
        Slog.d(
                "WindowManager",
                "makeSurface duration="
                        + (SystemClock.uptimeMillis() - uptimeMillis)
                        + " name="
                        + str);
        Trace.traceEnd(32L);
    }

    public final void destroy(SurfaceControl.Transaction transaction) {
        WindowStateAnimator windowStateAnimator = this.mAnimator;
        if (ProtoLogImpl_54989576.Cache.WM_FORCE_SHOW_SURFACE_ALLOC_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_FORCE_SHOW_SURFACE_ALLOC,
                    -4246370289472752588L,
                    0,
                    "Destroying surface %s called by %s",
                    String.valueOf(this),
                    String.valueOf(Debug.getCallers(8)));
        }
        try {
            try {
                if (this.mSurfaceControl != null) {
                    boolean z = windowStateAnimator.mIsWallpaper;
                    WindowState windowState = windowStateAnimator.mWin;
                    if (z && !windowState.mWindowRemovalAllowed && !windowState.mRemoveOnExit) {
                        Slog.e(
                                "WindowManager",
                                "Unexpected removing wallpaper surface of "
                                        + windowState
                                        + " by "
                                        + Debug.getCallers(8));
                    }
                    transaction.remove(this.mSurfaceControl);
                }
            } catch (RuntimeException e) {
                Slog.w("WindowManager", "Error destroying surface in: " + this, e);
            }
            setShown(false);
            this.mSurfaceControl = null;
        } catch (Throwable th) {
            setShown(false);
            this.mSurfaceControl = null;
            throw th;
        }
    }

    public final void setColorSpaceAgnostic(SurfaceControl.Transaction transaction, boolean z) {
        if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                    -8864150640874799238L,
                    3,
                    null,
                    Boolean.valueOf(z),
                    String.valueOf(this.title));
        }
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl == null) {
            return;
        }
        transaction.setColorSpaceAgnostic(surfaceControl, z);
    }

    public final void setInternalPresentationOnly(
            SurfaceControl.Transaction transaction, boolean z) {
        if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                    -1964029552496398161L,
                    3,
                    null,
                    Boolean.valueOf(z),
                    String.valueOf(this.title));
        }
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl == null) {
            return;
        }
        transaction.setMetadata(surfaceControl, 32, z ? 1 : 0);
    }

    public final void setShown(boolean z) {
        boolean remove;
        boolean z2 = this.mSurfaceShown;
        this.mSurfaceShown = z;
        this.mService.updateNonSystemOverlayWindowsVisibilityIfNeeded(this.mAnimator.mWin, z);
        WindowState windowState = this.mAnimator.mWin;
        if (windowState.mLastShownChangedReported != z) {
            windowState.mLastShownChangedReported = z;
            if (z) {
                long uptimeMillis = SystemClock.uptimeMillis();
                long[] jArr = windowState.mLastExclusionLogUptimeMillis;
                jArr[0] = uptimeMillis;
                jArr[1] = uptimeMillis;
            } else {
                windowState.logExclusionRestrictions(0);
                windowState.logExclusionRestrictions(1);
                windowState.getDisplayContent().removeImeSurfaceByTarget(windowState);
            }
            int i = windowState.mAttrs.type;
            if (i >= 2000
                    && i != 2005
                    && i != 2030
                    && (i != 2037 || windowState.getDisplayContent().mDisplay.getType() != 5)) {
                MirrorActiveUids mirrorActiveUids = windowState.mWmService.mAtmService.mActiveUids;
                int i2 = windowState.mOwnerUid;
                synchronized (mirrorActiveUids) {
                    try {
                        int indexOfKey = mirrorActiveUids.mNumNonAppVisibleWindowMap.indexOfKey(i2);
                        if (indexOfKey >= 0) {
                            int valueAt =
                                    mirrorActiveUids.mNumNonAppVisibleWindowMap.valueAt(indexOfKey)
                                            + (z ? 1 : -1);
                            if (valueAt > 0) {
                                mirrorActiveUids.mNumNonAppVisibleWindowMap.setValueAt(
                                        indexOfKey, valueAt);
                            } else {
                                mirrorActiveUids.mNumNonAppVisibleWindowMap.removeAt(indexOfKey);
                            }
                        } else if (z) {
                            mirrorActiveUids.mNumNonAppVisibleWindowMap.append(i2, 1);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
        Session session = this.mWindowSession;
        if (session != null) {
            boolean z3 = this.mSurfaceShown;
            int i3 = this.mWindowType;
            if (z3 != z2) {
                if (z3) {
                    session.mWinSurfaceVisibleCount++;
                } else {
                    int i4 = session.mWinSurfaceVisibleCount;
                    if (i4 > 0) {
                        session.mWinSurfaceVisibleCount = i4 - 1;
                    }
                }
            }
            if (WindowManager.LayoutParams.isSystemAlertWindowType(i3)) {
                boolean z4 =
                        (session.mCanAddInternalSystemWindow
                                        || session.mCanCreateSystemApplicationOverlay)
                                ? false
                                : true;
                if (z3) {
                    remove = session.mAlertWindowSurfaces.add(this);
                    if (i3 == 2038) {
                        MetricsLoggerWrapper.logAppOverlayEnter(
                                session.mUid, session.mPackageName, remove, i3, false);
                    } else if (z4) {
                        MetricsLoggerWrapper.logAppOverlayEnter(
                                session.mUid, session.mPackageName, remove, i3, true);
                    }
                } else {
                    remove = session.mAlertWindowSurfaces.remove(this);
                    if (i3 == 2038) {
                        MetricsLoggerWrapper.logAppOverlayExit(
                                session.mUid, session.mPackageName, remove, i3, false);
                    } else if (z4) {
                        MetricsLoggerWrapper.logAppOverlayExit(
                                session.mUid, session.mPackageName, remove, i3, true);
                    }
                }
                if (remove && z4) {
                    if (session.mAlertWindowSurfaces.isEmpty()) {
                        AlertWindowNotification alertWindowNotification =
                                session.mAlertWindowNotification;
                        if (alertWindowNotification != null) {
                            alertWindowNotification.mService.mH.post(
                                    new AlertWindowNotification$$ExternalSyntheticLambda1(
                                            alertWindowNotification, true));
                            session.mAlertWindowNotification = null;
                        }
                    } else if (session.mAlertWindowNotification == null
                            && !session.isSatellitePointingUiPackage()) {
                        WindowManagerService windowManagerService = session.mService;
                        AlertWindowNotification alertWindowNotification2 =
                                new AlertWindowNotification(
                                        windowManagerService, session.mPackageName);
                        session.mAlertWindowNotification = alertWindowNotification2;
                        if (session.mShowingAlertWindowNotificationAllowed) {
                            windowManagerService.mH.post(
                                    new AlertWindowNotification$$ExternalSyntheticLambda0(
                                            alertWindowNotification2));
                        }
                    }
                }
                if (!remove || session.mPid == WindowManagerService.MY_PID) {
                    return;
                }
                session.setHasOverlayUi(!session.mAlertWindowSurfaces.isEmpty());
            }
        }
    }

    public final String toString() {
        return this.mSurfaceControl.toString();
    }
}
