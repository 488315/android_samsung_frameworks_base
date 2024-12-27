package com.android.server.wm;

import android.R;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ShortcutServiceInternal;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.view.IWindow;
import android.view.IWindowId;
import android.view.IWindowSession;
import android.view.IWindowSessionCallback;
import android.view.InputChannel;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowRelayoutResult;
import android.window.ClientWindowFrames;
import android.window.InputTransferToken;
import android.window.OnBackInvokedCallbackInfo;
import android.window.WindowContainerToken;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.window.flags.Flags;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Session extends IWindowSession.Stub implements IBinder.DeathRecipient {
    public AlertWindowNotification mAlertWindowNotification;
    public final IWindowSessionCallback mCallback;
    public final boolean mCanAddInternalSystemWindow;
    public final boolean mCanAlwaysUpdateWallpaper;
    public final boolean mCanCreateSystemApplicationOverlay;
    public final boolean mCanDeliverTouchToKeyguardWallpaper;
    public final boolean mCanForceShowingInsets;
    public final boolean mCanHideNonSystemOverlayWindows;
    public final boolean mCanSetUnrestrictedGestureExclusion;
    public final boolean mCanStartTasksFromRecents;
    public boolean mClientDead;
    public final DragDropController mDragDropController;
    public final float mLastReportedAnimatorScale;
    public String mPackageName;
    public final int mPid;
    public final WindowProcessController mProcess;
    public String mRelayoutTag;
    public final WindowManagerService mService;
    public final boolean mSetsUnrestrictedKeepClearAreas;
    public boolean mShowingAlertWindowNotificationAllowed;
    public final String mStringName;
    public SurfaceSession mSurfaceSession;
    public final int mUid;
    public final ArrayList mAddedWindows = new ArrayList();
    public final ArraySet mAlertWindowSurfaces = new ArraySet();
    public final InsetsSourceControl.Array mDummyControls = new InsetsSourceControl.Array();
    public int mWinSurfaceVisibleCount = 0;

    public Session(
            WindowManagerService windowManagerService,
            IWindowSessionCallback iWindowSessionCallback,
            int i,
            int i2) {
        WindowProcessController process;
        this.mClientDead = false;
        this.mCanDeliverTouchToKeyguardWallpaper = false;
        this.mService = windowManagerService;
        this.mCallback = iWindowSessionCallback;
        this.mPid = i;
        this.mUid = i2;
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mLastReportedAnimatorScale = windowManagerService.getCurrentAnimatorScale();
                process = windowManagerService.mAtmService.mProcessMap.getProcess(i);
                this.mProcess = process;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (process == null) {
            throw new IllegalStateException(
                    ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "Unknown pid=", " uid="));
        }
        this.mCanAddInternalSystemWindow =
                windowManagerService.mContext.checkCallingOrSelfPermission(
                                "android.permission.INTERNAL_SYSTEM_WINDOW")
                        == 0;
        this.mCanForceShowingInsets =
                windowManagerService.mAtmService.mRecentTasks.isCallerRecents(i2)
                        || windowManagerService.mContext.checkCallingOrSelfPermission(
                                        "android.permission.STATUS_BAR_SERVICE")
                                == 0;
        this.mCanHideNonSystemOverlayWindows =
                windowManagerService.mContext.checkCallingOrSelfPermission(
                                        "android.permission.HIDE_NON_SYSTEM_OVERLAY_WINDOWS")
                                == 0
                        || windowManagerService.mContext.checkCallingOrSelfPermission(
                                        "android.permission.HIDE_OVERLAY_WINDOWS")
                                == 0;
        this.mCanCreateSystemApplicationOverlay =
                windowManagerService.mContext.checkCallingOrSelfPermission(
                                "android.permission.SYSTEM_APPLICATION_OVERLAY")
                        == 0;
        this.mCanStartTasksFromRecents =
                windowManagerService.mContext.checkCallingOrSelfPermission(
                                "android.permission.START_TASKS_FROM_RECENTS")
                        == 0;
        this.mSetsUnrestrictedKeepClearAreas =
                windowManagerService.mContext.checkCallingOrSelfPermission(
                                "android.permission.SET_UNRESTRICTED_KEEP_CLEAR_AREAS")
                        == 0;
        this.mCanSetUnrestrictedGestureExclusion =
                windowManagerService.mContext.checkCallingOrSelfPermission(
                                "android.permission.SET_UNRESTRICTED_GESTURE_EXCLUSION")
                        == 0;
        this.mCanAlwaysUpdateWallpaper =
                Flags.alwaysUpdateWallpaperPermission()
                        && windowManagerService.mContext.checkCallingOrSelfPermission(
                                        "android.permission.ALWAYS_UPDATE_WALLPAPER")
                                == 0;
        this.mShowingAlertWindowNotificationAllowed =
                windowManagerService.mShowAlertWindowNotifications;
        this.mDragDropController = windowManagerService.mDragDropController;
        if (CoreRune.FW_ALLOW_TOUCH_TO_KEYGUARD_WALLPAPER) {
            this.mCanDeliverTouchToKeyguardWallpaper =
                    windowManagerService.mContext.checkCallingOrSelfPermission(
                                    "android.permission.READ_WALLPAPER_INTERNAL")
                            == 0;
        }
        StringBuilder sb = new StringBuilder("Session{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(i);
        if (i2 < 10000) {
            sb.append(":");
            sb.append(i2);
        } else {
            sb.append(":u");
            sb.append(UserHandle.getUserId(i2));
            sb.append('a');
            sb.append(UserHandle.getAppId(i2));
        }
        sb.append("}");
        this.mStringName = sb.toString();
        try {
            iWindowSessionCallback.asBinder().linkToDeath(this, 0);
        } catch (RemoteException unused) {
            this.mClientDead = true;
        }
    }

    public final void actionOnWallpaper(IBinder iBinder, BiConsumer biConsumer) {
        WindowState windowForClientLocked =
                this.mService.windowForClientLocked(this, iBinder, true);
        biConsumer.accept(
                windowForClientLocked.getDisplayContent().mWallpaperController,
                windowForClientLocked);
    }

    public final int addToDisplay(
            IWindow iWindow,
            WindowManager.LayoutParams layoutParams,
            int i,
            int i2,
            int i3,
            InputChannel inputChannel,
            InsetsState insetsState,
            InsetsSourceControl.Array array,
            Rect rect,
            float[] fArr) {
        return this.mService.addWindow(
                this,
                iWindow,
                layoutParams,
                i,
                i2,
                UserHandle.getUserId(this.mUid),
                i3,
                inputChannel,
                insetsState,
                array,
                rect,
                fArr);
    }

    public final int addToDisplayAsUser(
            IWindow iWindow,
            WindowManager.LayoutParams layoutParams,
            int i,
            int i2,
            int i3,
            int i4,
            InputChannel inputChannel,
            InsetsState insetsState,
            InsetsSourceControl.Array array,
            Rect rect,
            float[] fArr) {
        return this.mService.addWindow(
                this,
                iWindow,
                layoutParams,
                i,
                i2,
                i3,
                i4,
                inputChannel,
                insetsState,
                array,
                rect,
                fArr);
    }

    public final int addToDisplayWithoutInputChannel(
            IWindow iWindow,
            WindowManager.LayoutParams layoutParams,
            int i,
            int i2,
            InsetsState insetsState,
            Rect rect,
            float[] fArr) {
        return this.mService.addWindow(
                this,
                iWindow,
                layoutParams,
                i,
                i2,
                UserHandle.getUserId(this.mUid),
                WindowInsets.Type.defaultVisible(),
                null,
                insetsState,
                this.mDummyControls,
                rect,
                fArr);
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mCallback.asBinder().unlinkToDeath(this, 0);
                this.mClientDead = true;
                try {
                    for (int size = this.mAddedWindows.size() - 1; size >= 0; size--) {
                        WindowState windowState = (WindowState) this.mAddedWindows.get(size);
                        Slog.i("WindowManager", "WIN DEATH: " + windowState);
                        ActivityRecord activityRecord = windowState.mActivityRecord;
                        if (activityRecord != null
                                && activityRecord.findMainWindow(true) == windowState) {
                            SnapshotController snapshotController =
                                    this.mService.mSnapshotController;
                            ActivityRecord activityRecord2 = windowState.mActivityRecord;
                            SnapshotCache snapshotCache =
                                    snapshotController.mTaskSnapshotController.mCache;
                            synchronized (snapshotCache.mLock) {
                                try {
                                    Integer num =
                                            (Integer) snapshotCache.mAppIdMap.get(activityRecord2);
                                    if (num != null) {
                                        snapshotCache.removeRunningEntry(num);
                                    }
                                } finally {
                                }
                            }
                            ActivitySnapshotController activitySnapshotController =
                                    snapshotController.mActivitySnapshotController;
                            if (!activitySnapshotController.shouldDisableSnapshots()) {
                                activitySnapshotController.removeIfUserSavedFileExist(
                                        activityRecord2);
                            }
                        }
                        windowState.removeIfPossible();
                    }
                    killSessionLocked();
                } catch (Throwable th) {
                    killSessionLocked();
                    throw th;
                }
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void cancelDragAndDrop(IBinder iBinder, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDragDropController.cancelDragAndDrop(iBinder, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean cancelDraw(IWindow iWindow) {
        return this.mService.cancelDraw(this, iWindow);
    }

    public final void clearTouchableRegion(IWindow iWindow) {
        WindowManagerService windowManagerService = this.mService;
        windowManagerService.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked =
                            windowManagerService.windowForClientLocked(this, iWindow, false);
                    windowForClientLocked.mTouchableInsets = 0;
                    windowForClientLocked.mGivenTouchableRegion.setEmpty();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void clearTspDeadzone(IWindow iWindow) {
        this.mService.clearTspDeadzone(this, iWindow);
    }

    public final void dragRecipientEntered(IWindow iWindow) {
        DragDropController dragDropController = this.mDragDropController;
        dragDropController.getClass();
        Slog.d("WindowManager", "Drag into new candidate view @ " + iWindow.asBinder());
        ((WindowManagerInternal.IDragDropCallback) dragDropController.mCallback.get()).getClass();
    }

    public final void dragRecipientExited(IWindow iWindow) {
        DragDropController dragDropController = this.mDragDropController;
        dragDropController.getClass();
        Slog.d("WindowManager", "Drag from old candidate view @ " + iWindow.asBinder());
        ((WindowManagerInternal.IDragDropCallback) dragDropController.mCallback.get()).getClass();
    }

    public final boolean dropForAccessibility(IWindow iWindow, int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mDragDropController.dropForAccessibility(iWindow, i, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void finishDrawing(
            IWindow iWindow, SurfaceControl.Transaction transaction, int i) {
        if (Trace.isTagEnabled(32L)) {
            Trace.traceBegin(32L, "finishDrawing: " + this.mPackageName);
        }
        WindowManagerService windowManagerService = this.mService;
        windowManagerService.getClass();
        if (transaction != null) {
            transaction.sanitize(Binder.getCallingPid(), Binder.getCallingUid());
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked =
                            windowManagerService.windowForClientLocked(this, iWindow, false);
                    if (ProtoLogImpl_54989576.Cache.WM_FORCE_DEBUG_ADD_REMOVE_enabled[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_FORCE_DEBUG_ADD_REMOVE,
                                7561682476338461067L,
                                16,
                                "finishDrawingWindow: %s mDrawState=%s seqId=%d",
                                String.valueOf(windowForClientLocked),
                                String.valueOf(
                                        windowForClientLocked != null
                                                ? windowForClientLocked.mWinAnimator
                                                        .drawStateToString()
                                                : "null"),
                                Long.valueOf(i));
                    }
                    if (transaction != null && !TextUtils.isEmpty(transaction.mDebugName)) {
                        Slog.d(
                                "WindowManager",
                                "finishDrawingWindow: syncBuffer=" + transaction.mDebugName);
                    }
                    if (windowForClientLocked != null
                            && windowForClientLocked.finishDrawing(transaction, i)) {
                        if (windowForClientLocked.hasWallpaper()) {
                            windowForClientLocked.getDisplayContent().pendingLayoutChanges |= 4;
                        }
                        windowForClientLocked.setDisplayLayoutNeeded();
                        windowManagerService.mWindowPlacerLocked.requestTraversal();
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Trace.traceEnd(32L);
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public final void finishMovingTask(IWindow iWindow) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            TaskPositioningController taskPositioningController =
                    this.mService.mTaskPositioningController;
            TaskPositioner taskPositioner = taskPositioningController.mTaskPositioner;
            if (taskPositioner != null && taskPositioner.mClientCallback == iWindow.asBinder()) {
                taskPositioningController.finishTaskPositioning$1();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void generateDisplayHash(
            IWindow iWindow, Rect rect, String str, RemoteCallback remoteCallback) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.generateDisplayHash(this, iWindow, rect, str, remoteCallback);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getDragDeviceId() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            DragState dragState = this.mDragDropController.mDragState;
            return dragState != null ? dragState.mDeviceId : -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getDragPointerId() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            DragState dragState = this.mDragDropController.mDragState;
            return dragState != null ? dragState.mPointerId : -1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IBinder getDragStateInputToken() {
        DragState.InputInterceptor inputInterceptor;
        InputChannel inputChannel;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            DragState dragState = this.mDragDropController.mDragState;
            IBinder iBinder = null;
            if (dragState != null
                    && (inputInterceptor = dragState.mInputInterceptor) != null
                    && (inputChannel = inputInterceptor.mClientChannel) != null) {
                iBinder = inputChannel.getToken();
            }
            return iBinder;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IWindowId getWindowId(IBinder iBinder) {
        return this.mService.getWindowId(iBinder);
    }

    public final void grantEmbeddedWindowFocus(
            IWindow iWindow, InputTransferToken inputTransferToken, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (iWindow != null) {
                this.mService.grantEmbeddedWindowFocus(this, iWindow, inputTransferToken, z);
            } else {
                if (!this.mCanAddInternalSystemWindow) {
                    throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
                }
                this.mService.grantEmbeddedWindowFocus(this, inputTransferToken, z);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void grantInputChannel(
            int i,
            SurfaceControl surfaceControl,
            IBinder iBinder,
            InputTransferToken inputTransferToken,
            int i2,
            int i3,
            int i4,
            int i5,
            IBinder iBinder2,
            InputTransferToken inputTransferToken2,
            String str,
            InputChannel inputChannel) {
        if (inputTransferToken == null && !this.mCanAddInternalSystemWindow) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.grantInputChannel(
                    this,
                    this.mUid,
                    this.mPid,
                    i,
                    surfaceControl,
                    iBinder,
                    inputTransferToken,
                    i2,
                    this.mCanAddInternalSystemWindow ? i3 : 0,
                    i4,
                    i5,
                    iBinder2,
                    inputTransferToken2,
                    str,
                    inputChannel);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void grantInputChannelWithTaskToken(
            int i,
            SurfaceControl surfaceControl,
            IBinder iBinder,
            InputTransferToken inputTransferToken,
            int i2,
            int i3,
            int i4,
            int i5,
            IBinder iBinder2,
            InputTransferToken inputTransferToken2,
            String str,
            InputChannel inputChannel,
            int i6,
            WindowContainerToken windowContainerToken) {
        if (inputTransferToken == null && !this.mCanAddInternalSystemWindow) {
            throw new SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.grantInputChannelWithTaskToken(
                    this,
                    this.mUid,
                    this.mPid,
                    i,
                    surfaceControl,
                    iBinder,
                    inputTransferToken,
                    i2,
                    this.mCanAddInternalSystemWindow ? i3 : 0,
                    i4,
                    i5,
                    iBinder2,
                    inputTransferToken2,
                    str,
                    inputChannel,
                    i6,
                    windowContainerToken);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isClientDead() {
        return this.mClientDead;
    }

    public final boolean isSatellitePointingUiPackage() {
        String str = this.mPackageName;
        if (str == null
                || !str.equals(this.mService.mContext.getString(R.string.ext_media_move_title))) {
            return false;
        }
        int i = this.mPid;
        int i2 = this.mUid;
        Boolean bool = ActivityTaskManagerService.sIsPip2ExperimentEnabled;
        return ActivityManagerService.checkComponentPermission(
                        i, i2, "android.permission.SATELLITE_COMMUNICATION", 0, -1, true)
                == 0;
    }

    public final void killSessionLocked() {
        if (this.mClientDead) {
            this.mService.mSessions.remove(this);
            SurfaceSession surfaceSession = this.mSurfaceSession;
            if (surfaceSession == null) {
                return;
            }
            if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                        2638961674625826260L,
                        0,
                        null,
                        String.valueOf(surfaceSession));
            }
            try {
                this.mSurfaceSession.kill();
            } catch (Exception e) {
                Slog.w(
                        "WindowManager",
                        "Exception thrown when killing surface session "
                                + this.mSurfaceSession
                                + " in session "
                                + this
                                + ": "
                                + e.toString());
            }
            this.mSurfaceSession = null;
            this.mAddedWindows.clear();
            this.mAlertWindowSurfaces.clear();
            setHasOverlayUi(false);
            AlertWindowNotification alertWindowNotification = this.mAlertWindowNotification;
            if (alertWindowNotification == null) {
                return;
            }
            alertWindowNotification.mService.mH.post(
                    new AlertWindowNotification$$ExternalSyntheticLambda1(
                            alertWindowNotification, true));
            this.mAlertWindowNotification = null;
        }
    }

    public final boolean moveFocusToAdjacentWindow(IWindow iWindow, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked =
                            this.mService.windowForClientLocked(this, iWindow, false);
                    if (windowForClientLocked == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    boolean moveFocusToAdjacentWindow =
                            this.mService.moveFocusToAdjacentWindow(windowForClientLocked, i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return moveFocusToAdjacentWindow;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mService.onRectangleOnScreenRequested(iBinder, rect);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            if (!(e instanceof SecurityException)) {
                Slog.wtf("WindowManager", "Window Session Crash", e);
            }
            throw e;
        }
    }

    public final void onWindowAdded(WindowState windowState) {
        if (this.mPackageName == null) {
            this.mPackageName = this.mProcess.mInfo.packageName;
            this.mRelayoutTag = "relayoutWindow: " + this.mPackageName;
        }
        if (this.mSurfaceSession == null) {
            SurfaceSession surfaceSession = new SurfaceSession();
            this.mSurfaceSession = surfaceSession;
            if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                        -1594708154257031561L,
                        0,
                        null,
                        String.valueOf(surfaceSession));
            }
            this.mService.mSessions.add(this);
            if (this.mLastReportedAnimatorScale != this.mService.getCurrentAnimatorScale()) {
                this.mService.mH.obtainMessage(34, this).sendToTarget();
            }
            this.mProcess.mWindowSession = this;
        }
        this.mAddedWindows.add(windowState);
    }

    public final boolean outOfMemory(IWindow iWindow) {
        return this.mService.outOfMemoryWindow(this, iWindow);
    }

    public final void performClipDataUpdate(ClipData clipData) {
        int callingUid = Binder.getCallingUid();
        DragDropController dragDropController = this.mDragDropController;
        dragDropController.getClass();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    dragDropController.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DragState dragState = dragDropController.mDragState;
                    if (dragState == null) {
                        Slog.w(
                                "WindowManager",
                                "updateClipData: there is no clipdata to be updated.");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (dragState.mUid == callingUid) {
                        ClipData clipData2 = dragState.mData;
                        if (clipData2 == null || clipData == null) {
                            Slog.w(
                                    "WindowManager",
                                    "updateClipData: wrong clipdata mData="
                                            + dragDropController.mDragState.mData
                                            + " data="
                                            + clipData);
                        } else {
                            int itemCount = clipData2.getItemCount();
                            int itemCount2 = clipData.getItemCount();
                            DragState dragState2 = dragDropController.mDragState;
                            dragState2.mData = clipData;
                            if (itemCount2 > itemCount) {
                                dragState2.notifyUpdateClipDataLocked();
                            }
                            dragDropController.mDragState.restartDragLocked(clipData);
                        }
                    } else {
                        Slog.w("WindowManager", "updateClipData: caller's uid is not valid.");
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (Exception e) {
            WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(
                    e, "updateClipData: exception e=", "WindowManager");
        }
    }

    public final IBinder performDrag(
            IWindow iWindow,
            int i,
            SurfaceControl surfaceControl,
            int i2,
            int i3,
            int i4,
            float f,
            float f2,
            float f3,
            float f4,
            ClipData clipData) {
        int callingUid = Binder.getCallingUid();
        validateAndResolveDragMimeTypeExtras(
                clipData, callingUid, Binder.getCallingPid(), this.mPackageName);
        validateDragFlags(i, callingUid);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mDragDropController.performDragWithArea(
                    this.mPid,
                    this.mUid,
                    iWindow,
                    i,
                    surfaceControl,
                    i2,
                    i3,
                    i4,
                    f,
                    f2,
                    f3,
                    f4,
                    clipData,
                    null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IBinder performDragWithArea(
            IWindow iWindow,
            int i,
            SurfaceControl surfaceControl,
            int i2,
            int i3,
            int i4,
            float f,
            float f2,
            float f3,
            float f4,
            ClipData clipData,
            RectF rectF,
            Point point) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mDragDropController.performDragWithArea(
                    this.mPid,
                    this.mUid,
                    iWindow,
                    i,
                    surfaceControl,
                    i2,
                    i3,
                    i4,
                    f,
                    f2,
                    f3,
                    f4,
                    clipData,
                    rectF);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean performHapticFeedback(int i, boolean z, boolean z2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerPolicy windowManagerPolicy = this.mService.mPolicy;
            return ((PhoneWindowManager) windowManagerPolicy)
                    .mExt.performHapticFeedback(this.mUid, i, this.mPackageName, null, z, z2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void performHapticFeedbackAsync(int i, boolean z, boolean z2) {
        performHapticFeedback(i, z, z2);
    }

    public final void pokeDrawLock(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.pokeDrawLock(this, iBinder);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int relayout(
            IWindow iWindow,
            WindowManager.LayoutParams layoutParams,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            WindowRelayoutResult windowRelayoutResult) {
        Trace.traceBegin(32L, this.mRelayoutTag);
        int relayoutWindow =
                this.mService.relayoutWindow(
                        this, iWindow, layoutParams, i, i2, i3, i4, i5, i6, windowRelayoutResult);
        Trace.traceEnd(32L);
        return relayoutWindow;
    }

    public final void relayoutAsync(
            IWindow iWindow,
            WindowManager.LayoutParams layoutParams,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6) {
        if (Flags.windowSessionRelayoutInfo()) {
            relayout(iWindow, layoutParams, i, i2, i3, i4, i5, i6, null);
        } else {
            relayoutLegacy(
                    iWindow,
                    layoutParams,
                    i,
                    i2,
                    i3,
                    i4,
                    i5,
                    i6,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        }
    }

    public final int relayoutLegacy(
            IWindow iWindow,
            WindowManager.LayoutParams layoutParams,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            ClientWindowFrames clientWindowFrames,
            MergedConfiguration mergedConfiguration,
            SurfaceControl surfaceControl,
            InsetsState insetsState,
            InsetsSourceControl.Array array,
            Bundle bundle) {
        Trace.traceBegin(32L, this.mRelayoutTag);
        int relayoutWindowInner =
                this.mService.relayoutWindowInner(
                        this,
                        iWindow,
                        layoutParams,
                        i,
                        i2,
                        i3,
                        i4,
                        i5,
                        i6,
                        clientWindowFrames,
                        mergedConfiguration,
                        surfaceControl,
                        insetsState,
                        array,
                        bundle,
                        null);
        Trace.traceEnd(32L);
        return relayoutWindowInner;
    }

    public final void remove(IBinder iBinder) {
        WindowManagerService windowManagerService = this.mService;
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked =
                        windowManagerService.windowForClientLocked(this, iBinder, false);
                if (windowForClientLocked != null) {
                    windowForClientLocked.removeIfPossible();
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    windowManagerService.mEmbeddedWindowController.remove(iBinder);
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void removeWithTaskToken(
            IBinder iBinder, WindowContainerToken windowContainerToken) {
        WindowManagerService windowManagerService = this.mService;
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowManagerService.mEmbeddedWindowController.remove(iBinder);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void reportDecorViewGestureInterceptionChanged(IWindow iWindow, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.reportDecorViewGestureChanged(this, iWindow, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reportDropResult(IWindow iWindow, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDragDropController.reportDropResult(iWindow, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reportKeepClearAreasChanged(IWindow iWindow, List list, List list2) {
        if (!this.mSetsUnrestrictedKeepClearAreas && !list2.isEmpty()) {
            list2 = Collections.emptyList();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.reportKeepClearAreasChanged(this, iWindow, list, list2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reportSystemGestureExclusionChanged(IWindow iWindow, List list) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.reportSystemGestureExclusionChanged(this, iWindow, list);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendWallpaperCommand(
            IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    WindowState windowForClientLocked =
                            this.mService.windowForClientLocked(this, iBinder, true);
                    WallpaperController wallpaperController =
                            windowForClientLocked.getDisplayContent().mWallpaperController;
                    if (this.mCanAlwaysUpdateWallpaper
                            || windowForClientLocked == wallpaperController.mWallpaperTarget
                            || windowForClientLocked == wallpaperController.mPrevWallpaperTarget) {
                        wallpaperController.sendWindowWallpaperCommand(str, i, i2, i3, bundle, z);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setHasOverlayUi(boolean z) {
        this.mService.mH.obtainMessage(58, this.mPid, z ? 1 : 0).sendToTarget();
    }

    public final void setInsets(
            IWindow iWindow, int i, Rect rect, Rect rect2, Region region, Rect rect3) {
        String str;
        WindowManagerService windowManagerService = this.mService;
        windowManagerService.getClass();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowState windowForClientLocked =
                            windowManagerService.windowForClientLocked(this, iWindow, false);
                    if (windowForClientLocked != null && windowForClientLocked.mIsImWindow) {
                        StringBuilder sb = new StringBuilder("setInsetsWindow ");
                        sb.append(windowForClientLocked);
                        sb.append(", contentInsets=");
                        sb.append(windowForClientLocked.mGivenContentInsets);
                        sb.append(" -> ");
                        sb.append(rect);
                        sb.append(", visibleInsets=");
                        sb.append(windowForClientLocked.mGivenVisibleInsets);
                        sb.append(" -> ");
                        sb.append(rect2);
                        sb.append(", touchableRegion=");
                        sb.append(windowForClientLocked.mGivenTouchableRegion);
                        sb.append(" -> ");
                        sb.append(region);
                        sb.append(", touchableInsets ");
                        sb.append(windowForClientLocked.mTouchableInsets);
                        sb.append(" -> ");
                        sb.append(i);
                        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
                            str =
                                    ", minimizedInsets="
                                            + windowForClientLocked.mMinimizedInsets
                                            + " -> "
                                            + rect3;
                        } else {
                            str = "";
                        }
                        sb.append(str);
                        Slog.d("WindowManager", sb.toString());
                    }
                    if (windowForClientLocked != null) {
                        boolean z = windowForClientLocked.mGivenInsetsPending;
                        windowForClientLocked.mGivenInsetsPending = false;
                        if (!(z && windowForClientLocked.hasInsetsSourceProvider())
                                && windowForClientLocked.mTouchableInsets == i
                                && windowForClientLocked.mGivenContentInsets.equals(rect)
                                && windowForClientLocked.mGivenVisibleInsets.equals(rect2)
                                && windowForClientLocked.mGivenTouchableRegion.equals(region)) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                        } else {
                            windowForClientLocked.mGivenContentInsets.set(rect);
                            windowForClientLocked.mGivenVisibleInsets.set(rect2);
                            windowForClientLocked.mGivenTouchableRegion.set(region);
                            windowForClientLocked.mTouchableInsets = i;
                            float f = windowForClientLocked.mGlobalScale;
                            if (f != 1.0f) {
                                windowForClientLocked.mGivenContentInsets.scale(f);
                                windowForClientLocked.mGivenVisibleInsets.scale(
                                        windowForClientLocked.mGlobalScale);
                                windowForClientLocked.mGivenTouchableRegion.scale(
                                        windowForClientLocked.mGlobalScale);
                            }
                            if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
                                windowForClientLocked.mMinimizedInsets.set(rect3);
                            }
                            windowForClientLocked.setDisplayLayoutNeeded();
                            windowForClientLocked.updateSourceFrame(
                                    windowForClientLocked.mWindowFrames.mFrame);
                            windowManagerService.mWindowPlacerLocked.performSurfacePlacement(false);
                            windowForClientLocked
                                    .getDisplayContent()
                                    .mInputMonitor
                                    .updateInputWindowsLw(true);
                            if (windowManagerService.mAccessibilityController.hasCallbacks()) {
                                windowManagerService.mAccessibilityController
                                        .onSomeWindowResizedOrMovedWithCallingUid(
                                                callingUid,
                                                windowForClientLocked.getDisplayContent()
                                                        .mDisplayId);
                            }
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setKeyguardWallpaperTouchAllowed(IWindow iWindow, boolean z) {
        if (!this.mCanDeliverTouchToKeyguardWallpaper) {
            throw new SecurityException("Requires READ_WALLPAPER_INTERNAL permission");
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mService.windowForClientLocked(this, iWindow, true)
                                .mKeyguardWallpaperTouchAllowed =
                        z;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setOnBackInvokedCallbackInfo(
            IWindow iWindow, OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked =
                        this.mService.windowForClientLocked(this, iWindow, false);
                if (windowForClientLocked == null) {
                    Slog.i(
                            "WindowManager",
                            "setOnBackInvokedCallback(): No window state for package:"
                                    + this.mPackageName);
                } else {
                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BACK_PREVIEW_enabled[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_BACK_PREVIEW,
                                -7237767461056267619L,
                                0,
                                "%s: Setting back callback %s",
                                String.valueOf(windowForClientLocked),
                                String.valueOf(onBackInvokedCallbackInfo));
                    }
                    windowForClientLocked.mOnBackInvokedCallbackInfo = onBackInvokedCallbackInfo;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setShouldZoomOutWallpaper(IBinder iBinder, final boolean z) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                actionOnWallpaper(
                        iBinder,
                        new BiConsumer() { // from class:
                                           // com.android.server.wm.Session$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                boolean z2 = z;
                                WallpaperController wallpaperController = (WallpaperController) obj;
                                WindowState windowState = (WindowState) obj2;
                                wallpaperController.getClass();
                                if (z2 != windowState.mShouldScaleWallpaper) {
                                    windowState.mShouldScaleWallpaper = z2;
                                    wallpaperController.updateWallpaperOffsetLocked(
                                            windowState, false);
                                }
                            }
                        });
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setTspDeadzone(IWindow iWindow, Bundle bundle) {
        this.mService.setTspDeadzone(this, iWindow, bundle);
    }

    public final void setTspNoteMode(IWindow iWindow, boolean z) {
        this.mService.setTspNoteMode(this, iWindow, z);
    }

    public final void setWallpaperDisplayOffset(IBinder iBinder, final int i, final int i2) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    actionOnWallpaper(
                            iBinder,
                            new BiConsumer() { // from class:
                                               // com.android.server.wm.Session$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    int i3 = i;
                                    int i4 = i2;
                                    WallpaperController wallpaperController =
                                            (WallpaperController) obj;
                                    WindowState windowState = (WindowState) obj2;
                                    wallpaperController.getClass();
                                    if (windowState.mWallpaperDisplayOffsetX == i3
                                            && windowState.mWallpaperDisplayOffsetY == i4) {
                                        return;
                                    }
                                    windowState.mWallpaperDisplayOffsetX = i3;
                                    windowState.mWallpaperDisplayOffsetY = i4;
                                    wallpaperController.updateWallpaperOffsetLocked(
                                            windowState,
                                            !wallpaperController
                                                    .mService
                                                    .mFlags
                                                    .mWallpaperOffsetAsync);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setWallpaperPosition(
            IBinder iBinder, final float f, final float f2, final float f3, final float f4) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    actionOnWallpaper(
                            iBinder,
                            new BiConsumer() { // from class:
                                               // com.android.server.wm.Session$$ExternalSyntheticLambda5
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    float f5 = f;
                                    float f6 = f2;
                                    float f7 = f3;
                                    float f8 = f4;
                                    WallpaperController wallpaperController =
                                            (WallpaperController) obj;
                                    WindowState windowState = (WindowState) obj2;
                                    wallpaperController.getClass();
                                    if (windowState.mWallpaperX == f5
                                            && windowState.mWallpaperY == f6) {
                                        return;
                                    }
                                    windowState.mWallpaperX = f5;
                                    windowState.mWallpaperY = f6;
                                    windowState.mWallpaperXStep = f7;
                                    windowState.mWallpaperYStep = f8;
                                    wallpaperController.updateWallpaperOffsetLocked(
                                            windowState,
                                            !wallpaperController
                                                    .mService
                                                    .mFlags
                                                    .mWallpaperOffsetAsync);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setWallpaperZoomOut(IBinder iBinder, final float f) {
        if (Float.compare(FullScreenMagnificationGestureHandler.MAX_SCALE, f) > 0
                || Float.compare(1.0f, f) < 0
                || Float.isNaN(f)) {
            throw new IllegalArgumentException("Zoom must be a valid float between 0 and 1: " + f);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    actionOnWallpaper(
                            iBinder,
                            new BiConsumer() { // from class:
                                               // com.android.server.wm.Session$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    float f2 = f;
                                    WallpaperController wallpaperController =
                                            (WallpaperController) obj;
                                    WindowState windowState = (WindowState) obj2;
                                    wallpaperController.getClass();
                                    if (Float.compare(windowState.mWallpaperZoomOut, f2) != 0) {
                                        windowState.mWallpaperZoomOut = f2;
                                        wallpaperController.mLastWallpaperZoomOut =
                                                FullScreenMagnificationGestureHandler.MAX_SCALE;
                                        wallpaperController.mDisplayContent.forAllWindows(
                                                (Consumer)
                                                        wallpaperController
                                                                .mComputeMaxZoomOutFunction,
                                                true);
                                        for (int size =
                                                        wallpaperController.mWallpaperTokens.size()
                                                                - 1;
                                                size >= 0;
                                                size--) {
                                            ((WallpaperWindowToken)
                                                            wallpaperController.mWallpaperTokens
                                                                    .get(size))
                                                    .updateWallpaperOffset(false);
                                        }
                                    }
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final boolean startMovingTask(IWindow iWindow, float f, float f2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mService.mTaskPositioningController.startMovingTask(iWindow, f, f2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String toString() {
        return this.mStringName;
    }

    public final void updateInputChannel(
            IBinder iBinder,
            int i,
            SurfaceControl surfaceControl,
            int i2,
            int i3,
            int i4,
            Region region) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.updateInputChannel(
                    iBinder,
                    i,
                    surfaceControl,
                    i2,
                    this.mCanAddInternalSystemWindow ? i3 : 0,
                    i4,
                    region,
                    null);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateInputChannelWithPointerRegion(
            IBinder iBinder,
            int i,
            SurfaceControl surfaceControl,
            int i2,
            int i3,
            int i4,
            Region region,
            Region region2) {
        if (CoreRune.MW_FREEFORM_RESIZE_TOUCHABLE_REGION) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mService.updateInputChannel(
                        iBinder,
                        i,
                        surfaceControl,
                        i2,
                        this.mCanAddInternalSystemWindow ? i3 : 0,
                        i4,
                        region,
                        region2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void updateRequestedVisibleTypes(IWindow iWindow, int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                WindowState windowForClientLocked =
                        this.mService.windowForClientLocked(this, iWindow, false);
                if (windowForClientLocked != null) {
                    if (windowForClientLocked.mRequestedVisibleTypes != i) {
                        windowForClientLocked.mRequestedVisibleTypes = i;
                    }
                    windowForClientLocked
                            .getDisplayContent()
                            .mInsetsPolicy
                            .onRequestedVisibleTypesChanged(windowForClientLocked);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void updateTapExcludeRegion(IWindow iWindow, Region region) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mService.updateTapExcludeRegion(iWindow, region);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void validateAndResolveDragMimeTypeExtras(ClipData clipData, int i, int i2, String str) {
        Task anyTaskForId;
        ActivityRecord rootActivity;
        ClipData.Item item;
        int i3;
        ActivityInfo resolveActivityInfoForIntent;
        ClipDescription description = clipData != null ? clipData.getDescription() : null;
        if (description == null) {
            return;
        }
        boolean hasMimeType = description.hasMimeType("application/vnd.android.activity");
        boolean hasMimeType2 = description.hasMimeType("application/vnd.android.shortcut");
        boolean hasMimeType3 = description.hasMimeType("application/vnd.android.task");
        int i4 = (hasMimeType ? 1 : 0) + (hasMimeType2 ? 1 : 0) + (hasMimeType3 ? 1 : 0);
        if (i4 == 0) {
            return;
        }
        if (i4 > 1) {
            throw new IllegalArgumentException(
                    "Can not specify more than one of activity, shortcut, or task mime types");
        }
        if (clipData.getItemCount() == 0) {
            throw new IllegalArgumentException("Unexpected number of items (none)");
        }
        for (int i5 = 0; i5 < clipData.getItemCount(); i5++) {
            if (clipData.getItemAt(i5).getIntent() == null) {
                throw new IllegalArgumentException("Unexpected item, expected an intent");
            }
        }
        MultiTaskingController multiTaskingController =
                this.mService.mAtmService.mMultiTaskingController;
        if (multiTaskingController.mSystemUIUid == -1) {
            multiTaskingController.mSystemUIUid =
                    multiTaskingController
                            .mAtm
                            .getPackageManagerInternalLocked()
                            .getPackageUid(Constants.SYSTEMUI_PACKAGE_NAME, 1048576L, 0);
        }
        int i6 =
                (multiTaskingController.mSystemUIUid == i
                                || UserHandle.isSameApp(
                                        i, this.mService.mAtmService.mRecentTasks.mRecentsUid)
                                || UserHandle.isSameApp(
                                        i, this.mService.mAtmService.mRecentTasks.mLauncherInfo))
                        ? 1000
                        : i;
        if (hasMimeType) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            for (int i7 = 0; i7 < clipData.getItemCount(); i7++) {
                try {
                    ClipData.Item itemAt = clipData.getItemAt(i7);
                    Intent intent = itemAt.getIntent();
                    PendingIntent pendingIntent =
                            (PendingIntent)
                                    intent.getParcelableExtra(
                                            "android.intent.extra.PENDING_INTENT");
                    UserHandle userHandle =
                            (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
                    if (pendingIntent == null || userHandle == null) {
                        throw new IllegalArgumentException(
                                "Clip data must include the pending intent to launch and its"
                                    + " associated user to launch for.");
                    }
                    itemAt.setActivityInfo(
                            this.mService.mAtmService.resolveActivityInfoForIntent(
                                    userHandle.getIdentifier(),
                                    i6,
                                    i2,
                                    this.mService.mAmInternal.getIntentForIntentSender(
                                            pendingIntent.getIntentSender().getTarget()),
                                    null));
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return;
        }
        if (!hasMimeType2) {
            int i8 = i6;
            if (hasMimeType3) {
                if (!this.mCanStartTasksFromRecents) {
                    throw new SecurityException("Requires START_TASKS_FROM_RECENTS permission");
                }
                if (clipData.getItemCount() > 0) {
                    ClipData.Item itemAt2 = clipData.getItemAt(0);
                    int intExtra =
                            itemAt2.getIntent().getIntExtra("android.intent.extra.TASK_ID", -1);
                    if (intExtra == -1) {
                        throw new IllegalArgumentException("Clip item must include the task id.");
                    }
                    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            anyTaskForId = this.mService.mRoot.anyTaskForId(intExtra);
                            if (anyTaskForId == null) {
                                throw new IllegalArgumentException("Invalid task id.");
                            }
                            rootActivity = anyTaskForId.getRootActivity(true, false);
                        } catch (Throwable th2) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th2;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    if (rootActivity != null) {
                        itemAt2.setActivityInfo(rootActivity.info);
                        return;
                    } else {
                        itemAt2.setActivityInfo(
                                this.mService.mAtmService.resolveActivityInfoForIntent(
                                        anyTaskForId.mUserId, i8, i2, anyTaskForId.intent, null));
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (!this.mCanStartTasksFromRecents) {
            throw new SecurityException("Requires START_TASKS_FROM_RECENTS permission");
        }
        int i9 = 0;
        while (i9 < clipData.getItemCount()) {
            ClipData.Item itemAt3 = clipData.getItemAt(i9);
            Intent intent2 = itemAt3.getIntent();
            String stringExtra = intent2.getStringExtra("android.intent.extra.shortcut.ID");
            String stringExtra2 = intent2.getStringExtra("android.intent.extra.PACKAGE_NAME");
            UserHandle userHandle2 =
                    (UserHandle) intent2.getParcelableExtra("android.intent.extra.USER");
            if (TextUtils.isEmpty(stringExtra)
                    || TextUtils.isEmpty(stringExtra2)
                    || userHandle2 == null) {
                throw new IllegalArgumentException(
                        "Clip item must include the package name, shortcut id, and the user to"
                            + " launch for.");
            }
            int i10 = i6;
            Intent[] createShortcutIntents =
                    ((ShortcutServiceInternal)
                                    LocalServices.getService(ShortcutServiceInternal.class))
                            .createShortcutIntents(
                                    UserHandle.getUserId(i),
                                    str,
                                    stringExtra2,
                                    stringExtra,
                                    userHandle2.getIdentifier(),
                                    i2,
                                    i);
            if (createShortcutIntents == null || createShortcutIntents.length == 0) {
                throw new IllegalArgumentException("Invalid shortcut id");
            }
            if (i10 == 1000) {
                item = itemAt3;
                i3 = i9;
                resolveActivityInfoForIntent =
                        this.mService.mAtmService.resolveActivityInfoForIntent(
                                userHandle2.getIdentifier(),
                                i10,
                                i2,
                                createShortcutIntents[0],
                                createShortcutIntents[0].resolveTypeIfNeeded(
                                        this.mService.mContext.getContentResolver()));
            } else {
                item = itemAt3;
                i3 = i9;
                resolveActivityInfoForIntent =
                        this.mService.mAtmService.resolveActivityInfoForIntent(
                                userHandle2.getIdentifier(), i, i2, createShortcutIntents[0], null);
            }
            item.setActivityInfo(resolveActivityInfoForIntent);
            i9 = i3 + 1;
            i6 = i10;
        }
    }

    public void validateDragFlags(int i, int i2) {
        if ((i & 2048) != 0 && !this.mCanStartTasksFromRecents) {
            throw new SecurityException("Requires START_TASKS_FROM_RECENTS permission");
        }
        if ((i & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) != 0
                && !SafeActivityOptions.isAssistant(i2, this.mService.mAtmService)) {
            throw new SecurityException("Caller is not the assistant");
        }
    }

    public final void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                actionOnWallpaper(iBinder, new Session$$ExternalSyntheticLambda2(1, iBinder));
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void wallpaperOffsetsComplete(IBinder iBinder) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                actionOnWallpaper(iBinder, new Session$$ExternalSyntheticLambda2(0, iBinder));
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
