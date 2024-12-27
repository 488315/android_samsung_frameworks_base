package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemoteAnimationController implements IBinder.DeathRecipient {
    public boolean mCanceled;
    public final DisplayContent mDisplayContent;
    public FinishedCallback mFinishedCallback;
    public final Handler mHandler;
    public final boolean mIsActivityEmbedding;
    public boolean mIsFinishing;
    public boolean mLinkedToDeathOfRunner;
    public Runnable mOnRemoteAnimationReady;
    public final RemoteAnimationAdapter mRemoteAnimationAdapter;
    public final WindowManagerService mService;
    public final ArrayList mPendingAnimations = new ArrayList();
    public final ArrayList mPendingWallpaperAnimations = new ArrayList();
    final ArrayList mPendingNonAppAnimations = new ArrayList();
    public final RemoteAnimationController$$ExternalSyntheticLambda1 mTimeoutRunnable =
            new Runnable() { // from class:
                             // com.android.server.wm.RemoteAnimationController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteAnimationController.this.cancelAnimation("timeoutRunnable");
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FinishedCallback extends IRemoteAnimationFinishedCallback.Stub {
        public RemoteAnimationController mOuter;

        public final void onAnimationFinished() {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                        -7169244688499657832L,
                        0,
                        null,
                        String.valueOf(this.mOuter));
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                RemoteAnimationController remoteAnimationController = this.mOuter;
                if (remoteAnimationController != null) {
                    remoteAnimationController.onAnimationFinished();
                    this.mOuter = null;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteAnimationAdapterWrapper implements AnimationAdapter {
        public int mAnimationType;
        public SurfaceAnimator.OnAnimationFinishedCallback mCapturedFinishCallback;
        public SurfaceControl mCapturedLeash;
        public final Rect mEndBounds;
        public final Rect mLocalBounds;
        public final Point mPosition;
        public final RemoteAnimationRecord mRecord;
        public final boolean mShowBackdrop;
        public final Rect mStartBounds;

        public RemoteAnimationAdapterWrapper(
                RemoteAnimationRecord remoteAnimationRecord,
                Point point,
                Rect rect,
                Rect rect2,
                Rect rect3,
                boolean z) {
            Point point2 = new Point();
            this.mPosition = point2;
            Rect rect4 = new Rect();
            this.mEndBounds = rect4;
            Rect rect5 = new Rect();
            this.mStartBounds = rect5;
            this.mRecord = remoteAnimationRecord;
            point2.set(point.x, point.y);
            this.mLocalBounds = rect;
            rect4.set(rect2);
            rect5.set(rect3);
            this.mShowBackdrop = z;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.print("container=");
            RemoteAnimationRecord remoteAnimationRecord = this.mRecord;
            printWriter.println(remoteAnimationRecord.mWindowContainer);
            if (remoteAnimationRecord.mTarget == null) {
                printWriter.print(str);
                printWriter.println("Target: null");
                return;
            }
            printWriter.print(str);
            printWriter.println("Target:");
            remoteAnimationRecord.mTarget.dump(printWriter, str + "  ");
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void dumpDebug$1(ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268034L);
            RemoteAnimationTarget remoteAnimationTarget = this.mRecord.mTarget;
            if (remoteAnimationTarget != null) {
                remoteAnimationTarget.dumpDebug(protoOutputStream, 1146756268033L);
            }
            protoOutputStream.end(start);
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final int getBackgroundColor() {
            return this.mRecord.mBackdropColor;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final long getDurationHint() {
            return RemoteAnimationController.this.mRemoteAnimationAdapter.getDuration();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final boolean getShowBackground() {
            return this.mShowBackdrop;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final boolean getShowWallpaper() {
            return false;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final long getStatusBarTransitionsStartTime() {
            return RemoteAnimationController.this.mRemoteAnimationAdapter
                            .getStatusBarTransitionDelay()
                    + SystemClock.uptimeMillis();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void onAnimationCancelled(SurfaceControl surfaceControl) {
            RemoteAnimationController remoteAnimationController = RemoteAnimationController.this;
            if (remoteAnimationController.mIsFinishing) {
                return;
            }
            RemoteAnimationRecord remoteAnimationRecord = this.mRecord;
            if (remoteAnimationRecord.mAdapter == this) {
                remoteAnimationRecord.mAdapter = null;
            } else {
                remoteAnimationRecord.mThumbnailAdapter = null;
            }
            if (remoteAnimationRecord.mAdapter == null
                    && remoteAnimationRecord.mThumbnailAdapter == null) {
                remoteAnimationController.mPendingAnimations.remove(remoteAnimationRecord);
            }
            if (remoteAnimationController.mPendingAnimations.isEmpty()) {
                remoteAnimationController.cancelAnimation("allAppAnimationsCanceled");
            }
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void startAnimation(
                SurfaceControl surfaceControl,
                SurfaceControl.Transaction transaction,
                int i,
                SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                        8918152561092803537L,
                        0,
                        null,
                        null);
            }
            if (this.mStartBounds.isEmpty()) {
                Point point = this.mPosition;
                transaction.setPosition(surfaceControl, point.x, point.y);
                transaction.setWindowCrop(
                        surfaceControl, this.mEndBounds.width(), this.mEndBounds.height());
            } else {
                int i2 = this.mPosition.x + this.mStartBounds.left;
                Rect rect = this.mEndBounds;
                transaction.setPosition(surfaceControl, i2 - rect.left, (r0.y + r2.top) - rect.top);
                transaction.setWindowCrop(
                        surfaceControl, this.mStartBounds.width(), this.mStartBounds.height());
            }
            this.mCapturedLeash = surfaceControl;
            this.mCapturedFinishCallback = onAnimationFinishedCallback;
            this.mAnimationType = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteAnimationRecord {
        public RemoteAnimationAdapterWrapper mAdapter;
        public int mBackdropColor = 0;
        public int mMode = 2;
        public final boolean mShowBackdrop;
        public final Rect mStartBounds;
        public RemoteAnimationTarget mTarget;
        public RemoteAnimationAdapterWrapper mThumbnailAdapter;
        public final WindowContainer mWindowContainer;

        public RemoteAnimationRecord(
                WindowContainer windowContainer,
                Point point,
                Rect rect,
                Rect rect2,
                Rect rect3,
                boolean z,
                boolean z2) {
            this.mThumbnailAdapter = null;
            this.mWindowContainer = windowContainer;
            this.mShowBackdrop = z;
            if (rect3 == null) {
                this.mAdapter =
                        RemoteAnimationController.this
                        .new RemoteAnimationAdapterWrapper(this, point, rect, rect2, new Rect(), z);
                this.mStartBounds = null;
                return;
            }
            Rect rect4 = new Rect(rect3);
            this.mStartBounds = rect4;
            this.mAdapter =
                    RemoteAnimationController.this
                    .new RemoteAnimationAdapterWrapper(this, point, rect, rect2, rect4, z);
            if (z2
                    && RemoteAnimationController.this.mRemoteAnimationAdapter
                            .getChangeNeedsSnapshot()) {
                Rect rect5 = new Rect(rect3);
                rect5.offsetTo(0, 0);
                this.mThumbnailAdapter =
                        RemoteAnimationController.this
                        .new RemoteAnimationAdapterWrapper(
                                this, new Point(0, 0), rect5, rect3, new Rect(), z);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.wm.RemoteAnimationController$$ExternalSyntheticLambda1] */
    public RemoteAnimationController(
            WindowManagerService windowManagerService,
            DisplayContent displayContent,
            RemoteAnimationAdapter remoteAnimationAdapter,
            Handler handler,
            boolean z) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mRemoteAnimationAdapter = remoteAnimationAdapter;
        this.mHandler = handler;
        this.mIsActivityEmbedding = z;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        cancelAnimation("binderDied");
    }

    public final void cancelAnimation(String str) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 7501495587927045391L, 0, null, str);
        }
        synchronized (this.mService.mGlobalLock) {
            try {
                if (this.mCanceled) {
                    return;
                }
                this.mCanceled = true;
                onAnimationFinished();
                invokeAnimationCancelled(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final RemoteAnimationRecord createRemoteAnimationRecord(
            WindowContainer windowContainer,
            Point point,
            Rect rect,
            Rect rect2,
            Rect rect3,
            boolean z,
            boolean z2) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                    -5444412205083968021L,
                    0,
                    null,
                    String.valueOf(windowContainer));
        }
        RemoteAnimationRecord remoteAnimationRecord =
                new RemoteAnimationRecord(windowContainer, point, rect, rect2, rect3, z, z2);
        this.mPendingAnimations.add(remoteAnimationRecord);
        return remoteAnimationRecord;
    }

    public final void invokeAnimationCancelled(String str) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 7501495587927045391L, 0, null, str);
        }
        try {
            this.mRemoteAnimationAdapter.getRunner().onAnimationCancelled();
        } catch (RemoteException e) {
            Slog.e("WindowManager", "Failed to notify cancel", e);
        }
        this.mOnRemoteAnimationReady = null;
    }

    public final void onAnimationFinished() {
        WindowContainer topChild;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled;
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                    -2716313493239418198L,
                    1,
                    null,
                    Long.valueOf(this.mPendingAnimations.size()));
        }
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mIsFinishing = true;
                if (this.mLinkedToDeathOfRunner) {
                    this.mRemoteAnimationAdapter.getRunner().asBinder().unlinkToDeath(this, 0);
                    this.mLinkedToDeathOfRunner = false;
                }
                FinishedCallback finishedCallback = this.mFinishedCallback;
                if (finishedCallback != null) {
                    if (zArr[0]) {
                        ProtoLogImpl_54989576.d(
                                ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                                3923111589554171989L,
                                0,
                                null,
                                String.valueOf(finishedCallback.mOuter));
                    }
                    finishedCallback.mOuter = null;
                    this.mFinishedCallback = null;
                }
                try {
                    try {
                        if (zArr[0]) {
                            ProtoLogImpl_54989576.d(
                                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                                    7221400292415257709L,
                                    0,
                                    null,
                                    null);
                        }
                        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
                            RemoteAnimationRecord remoteAnimationRecord =
                                    (RemoteAnimationRecord) this.mPendingAnimations.get(size);
                            RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper =
                                    remoteAnimationRecord.mAdapter;
                            if (remoteAnimationAdapterWrapper != null) {
                                remoteAnimationAdapterWrapper.mCapturedFinishCallback
                                        .onAnimationFinished(
                                                remoteAnimationAdapterWrapper.mAnimationType,
                                                remoteAnimationAdapterWrapper);
                            }
                            RemoteAnimationAdapterWrapper remoteAnimationAdapterWrapper2 =
                                    remoteAnimationRecord.mThumbnailAdapter;
                            if (remoteAnimationAdapterWrapper2 != null) {
                                remoteAnimationAdapterWrapper2.mCapturedFinishCallback
                                        .onAnimationFinished(
                                                remoteAnimationAdapterWrapper2.mAnimationType,
                                                remoteAnimationAdapterWrapper2);
                            }
                            if (!CoreRune.MW_MULTI_SPLIT
                                    && remoteAnimationRecord.mWindowContainer.asTask() != null
                                    && remoteAnimationRecord.mWindowContainer
                                            .inSplitScreenWindowingMode()
                                    && (topChild =
                                                    remoteAnimationRecord.mWindowContainer
                                                            .getTopChild())
                                            != null
                                    && topChild.asTask() != null
                                    && !topChild.asTask().mCanAffectSystemUiFlags) {
                                topChild.asTask().mCanAffectSystemUiFlags = true;
                            }
                            this.mPendingAnimations.remove(size);
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
                                ProtoLogImpl_54989576.d(
                                        ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                                        7483194715776694698L,
                                        0,
                                        null,
                                        String.valueOf(remoteAnimationRecord.mWindowContainer));
                            }
                        }
                        for (int size2 = this.mPendingWallpaperAnimations.size() - 1;
                                size2 >= 0;
                                size2--) {
                            WallpaperAnimationAdapter wallpaperAnimationAdapter =
                                    (WallpaperAnimationAdapter)
                                            this.mPendingWallpaperAnimations.get(size2);
                            wallpaperAnimationAdapter.mCapturedLeashFinishCallback
                                    .onAnimationFinished(
                                            wallpaperAnimationAdapter.mLastAnimationType,
                                            wallpaperAnimationAdapter);
                            this.mPendingWallpaperAnimations.remove(size2);
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
                                ProtoLogImpl_54989576.d(
                                        ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                                        6697982664439247822L,
                                        0,
                                        null,
                                        String.valueOf(wallpaperAnimationAdapter.mWallpaperToken));
                            }
                        }
                        for (int size3 = this.mPendingNonAppAnimations.size() - 1;
                                size3 >= 0;
                                size3--) {
                            NonAppWindowAnimationAdapter nonAppWindowAnimationAdapter =
                                    (NonAppWindowAnimationAdapter)
                                            this.mPendingNonAppAnimations.get(size3);
                            nonAppWindowAnimationAdapter.mCapturedLeashFinishCallback
                                    .onAnimationFinished(
                                            nonAppWindowAnimationAdapter.mLastAnimationType,
                                            nonAppWindowAnimationAdapter);
                            this.mPendingNonAppAnimations.remove(size3);
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
                                ProtoLogImpl_54989576.d(
                                        ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                                        6938838346517131964L,
                                        0,
                                        null,
                                        String.valueOf(
                                                nonAppWindowAnimationAdapter.mWindowContainer));
                            }
                        }
                        this.mIsFinishing = false;
                        this.mDisplayContent.forAllActivities(
                                new RemoteAnimationController$$ExternalSyntheticLambda0());
                    } catch (Exception e) {
                        Slog.e("WindowManager", "Failed to finish remote animation", e);
                        throw e;
                    }
                } catch (Throwable th) {
                    this.mIsFinishing = false;
                    throw th;
                }
            } catch (Throwable th2) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        setRunningRemoteAnimation(false);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, -3880290251819699866L, 0, null, null);
        }
    }

    public final void setRunningRemoteAnimation(boolean z) {
        int callingPid = this.mRemoteAnimationAdapter.getCallingPid();
        int callingUid = this.mRemoteAnimationAdapter.getCallingUid();
        if (callingPid == 0) {
            throw new RuntimeException("Calling pid of remote animation was null");
        }
        WindowProcessController processController =
                this.mService.mAtmService.getProcessController(callingPid, callingUid);
        if (processController == null) {
            PendingIntentController$$ExternalSyntheticOutline0.m(
                    callingPid,
                    callingUid,
                    "Unable to find process with pid=",
                    " uid=",
                    "WindowManager");
            return;
        }
        if (!z) {
            processController.removeAnimatingReason(1);
            return;
        }
        int i = processController.mAnimatingReasons;
        processController.mAnimatingReasons = i | 1;
        if (i == 0) {
            processController.mAtm.mH.post(
                    new WindowProcessController$$ExternalSyntheticLambda1(processController, true));
        }
    }
}
