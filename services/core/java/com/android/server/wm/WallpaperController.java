package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.ScreenCapture;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.ToBooleanFunction;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.wallpaper.WallpaperCropper;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WallpaperController {
    public final WallpaperController$$ExternalSyntheticLambda2 mComputeMaxZoomOutFunction;
    public final DisplayContent mDisplayContent;
    public final FindWallpaperTargetResult mFindResults;
    public final WallpaperController$$ExternalSyntheticLambda1 mFindWallpaperTargetFunction;
    public volatile boolean mIsWallpaperNotifiedOnDisplaySwitch;
    public long mLastWallpaperTimeoutTime;
    public float mMaxWallpaperScale;
    public float mMinWallpaperScale;
    public final HashMap mRemoteWallpaperAnimAreaMap;
    public final WindowManagerService mService;
    public boolean mShouldOffsetWallpaperCenter;
    public WindowState mWaitingOnWallpaper;
    public WallpaperCropper.WallpaperCropUtils mWallpaperCropUtils = null;
    public final ArrayList mWallpaperTokens = new ArrayList();
    public WindowState mWallpaperTarget = null;
    public WindowState mPrevWallpaperTarget = null;
    public float mLastWallpaperZoomOut = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public boolean mLastFrozen = false;
    public int mWallpaperDrawState = 0;
    public Point mLargestDisplaySize = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FindWallpaperTargetResult {
        public boolean isWallpaperTargetForLetterbox;
        public boolean mNeedsShowWhenLockedWallpaper;
        public TopWallpaper mTopWallpaper;
        public boolean useTopWallpaperAsTarget;
        public WindowState wallpaperTarget;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class TopWallpaper {
            public WindowState mTopHideWhenLockedWallpaper;
            public WindowState mTopShowWhenLockedWallpaper;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteWallpaperAnimationAdapter
            implements AnimationAdapter, IBinder.DeathRecipient {
        public final int mCallingPid;
        public final DisplayArea mRemoteWallpaperAnimArea;
        public final IRemoteAnimationRunner mRunner;
        public final IBinder mRunnerToken;

        public RemoteWallpaperAnimationAdapter(
                IBinder iBinder,
                DisplayArea displayArea,
                int i,
                IRemoteAnimationRunner iRemoteAnimationRunner) {
            this.mRunner = iRemoteAnimationRunner;
            this.mRunnerToken = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException unused) {
            }
            this.mRemoteWallpaperAnimArea = displayArea;
            this.mCallingPid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    WallpaperController.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mRemoteWallpaperAnimArea.cancelAnimation();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str + "RemoteWallpaperAnimAdapter callingPid=");
            printWriter.print(this.mCallingPid);
            printWriter.println();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void dumpDebug$1(ProtoOutputStream protoOutputStream) {}

        @Override // com.android.server.wm.AnimationAdapter
        public final long getDurationHint() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final boolean getShowWallpaper() {
            return false;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final long getStatusBarTransitionsStartTime() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void onAnimationCancelled(SurfaceControl surfaceControl) {
            try {
                Slog.d(
                        "WindowManager",
                        "mRunner#onAnimationCancelled, leash="
                                + surfaceControl
                                + ", caller="
                                + Debug.getCallers(5));
                this.mRunner.onAnimationCancelled();
            } catch (RemoteException unused) {
            }
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void startAnimation(
                SurfaceControl surfaceControl,
                SurfaceControl.Transaction transaction,
                int i,
                SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            Slog.d("WindowManager", "startAnimation for remote wallpaper, leash=" + surfaceControl);
            final RemoteAnimationTarget[] remoteAnimationTargetArr = {
                new RemoteAnimationTarget(
                        -1,
                        -1,
                        surfaceControl,
                        false,
                        (Rect) null,
                        (Rect) null,
                        -1,
                        new Point(),
                        (Rect) null,
                        (Rect) null,
                        (WindowConfiguration) null,
                        true,
                        (SurfaceControl) null,
                        (Rect) null,
                        (ActivityManager.RunningTaskInfo) null,
                        false)
            };
            transaction.show(surfaceControl);
            transaction.setAlpha(surfaceControl, 1.0f);
            final String str = "startRemoteWallpaperAnimation_" + this.mRunner.hashCode();
            transaction.addLowDebugName(str);
            transaction.addTransactionCommittedListener(
                    new HandlerExecutor(WallpaperController.this.mService.mH),
                    new SurfaceControl
                            .TransactionCommittedListener() { // from class:
                                                              // com.android.server.wm.WallpaperController$RemoteWallpaperAnimationAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.SurfaceControl.TransactionCommittedListener
                        public final void onTransactionCommitted() {
                            WallpaperController.RemoteWallpaperAnimationAdapter
                                    remoteWallpaperAnimationAdapter =
                                            WallpaperController.RemoteWallpaperAnimationAdapter
                                                    .this;
                            RemoteAnimationTarget[] remoteAnimationTargetArr2 =
                                    remoteAnimationTargetArr;
                            String str2 = str;
                            remoteWallpaperAnimationAdapter.getClass();
                            try {
                                Slog.d(
                                        "WindowManager",
                                        "mRunner#onAnimationStart for remote wallpaper="
                                                + Arrays.toString(remoteAnimationTargetArr2)
                                                + ", transaction="
                                                + str2);
                                remoteWallpaperAnimationAdapter.mRunner.onAnimationStart(
                                        -1,
                                        (RemoteAnimationTarget[]) null,
                                        remoteAnimationTargetArr2,
                                        (RemoteAnimationTarget[]) null,
                                        (IRemoteAnimationFinishedCallback) null);
                            } catch (RemoteException unused) {
                            }
                        }
                    });
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.wm.WallpaperController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.wm.WallpaperController$$ExternalSyntheticLambda2] */
    public WallpaperController(
            WindowManagerService windowManagerService, DisplayContent displayContent) {
        FindWallpaperTargetResult findWallpaperTargetResult = new FindWallpaperTargetResult();
        FindWallpaperTargetResult.TopWallpaper topWallpaper =
                new FindWallpaperTargetResult.TopWallpaper();
        topWallpaper.mTopHideWhenLockedWallpaper = null;
        topWallpaper.mTopShowWhenLockedWallpaper = null;
        findWallpaperTargetResult.mTopWallpaper = topWallpaper;
        findWallpaperTargetResult.useTopWallpaperAsTarget = false;
        findWallpaperTargetResult.wallpaperTarget = null;
        findWallpaperTargetResult.isWallpaperTargetForLetterbox = false;
        this.mFindResults = findWallpaperTargetResult;
        this.mFindWallpaperTargetFunction =
                new ToBooleanFunction() { // from class:
                    // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda1
                    /* JADX WARN: Code restructure failed: missing block: B:115:0x0198, code lost:

                       if (r13.isOnScreen() != false) goto L147;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ba, code lost:

                       if (r13.inTransition() != false) goto L60;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:50:0x00cd, code lost:

                       if (r0.mWindowManagerFuncs.isAppTransitionStateIdle() == false) goto L60;
                    */
                    /* JADX WARN: Removed duplicated region for block: B:102:0x016c  */
                    /* JADX WARN: Removed duplicated region for block: B:112:0x018c  */
                    /* JADX WARN: Removed duplicated region for block: B:114:0x0194  */
                    /* JADX WARN: Removed duplicated region for block: B:119:0x018e  */
                    /* JADX WARN: Removed duplicated region for block: B:124:0x01a5  */
                    /* JADX WARN: Removed duplicated region for block: B:133:0x01bf  */
                    /* JADX WARN: Removed duplicated region for block: B:164:? A[RETURN, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:69:0x0103  */
                    /* JADX WARN: Removed duplicated region for block: B:91:0x0144  */
                    /* JADX WARN: Removed duplicated region for block: B:98:0x015d A[ADDED_TO_REGION] */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final boolean apply(java.lang.Object r13) {
                        /*
                            Method dump skipped, instructions count: 529
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.wm.WallpaperController$$ExternalSyntheticLambda1.apply(java.lang.Object):boolean");
                    }
                };
        this.mComputeMaxZoomOutFunction =
                new Consumer() { // from class:
                                 // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        WallpaperController wallpaperController = WallpaperController.this;
                        WindowState windowState = (WindowState) obj;
                        wallpaperController.getClass();
                        if (windowState.mIsWallpaper
                                || Float.compare(
                                                windowState.mWallpaperZoomOut,
                                                wallpaperController.mLastWallpaperZoomOut)
                                        <= 0) {
                            return;
                        }
                        wallpaperController.mLastWallpaperZoomOut = windowState.mWallpaperZoomOut;
                    }
                };
        this.mRemoteWallpaperAnimAreaMap = new HashMap();
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        Resources resources = windowManagerService.mContext.getResources();
        this.mMinWallpaperScale = resources.getFloat(R.dimen.date_picker_day_of_week_height);
        this.mMaxWallpaperScale = resources.getFloat(R.dimen.date_picker_day_height);
        this.mShouldOffsetWallpaperCenter =
                resources.getBoolean(R.bool.config_preferKeepClearForFocus);
    }

    public static void dumpValue(PrintWriter printWriter, String str, float f) {
        printWriter.print("  ");
        printWriter.print("  " + str + "=");
        printWriter.println(
                f >= FullScreenMagnificationGestureHandler.MAX_SCALE ? Float.valueOf(f) : "NA");
    }

    public final void adjustWallpaperWindows() {
        boolean[] zArr;
        WindowState windowState;
        MultiTaskingAppCompatConfiguration.BlackLetterboxConfig blackLetterboxConfig;
        ActivityRecord activityRecord;
        WindowState windowState2;
        WindowState windowState3;
        WindowState windowState4;
        WindowState windowState5;
        DisplayContent displayContent = this.mDisplayContent;
        displayContent.mWallpaperMayChange = false;
        FindWallpaperTargetResult findWallpaperTargetResult = this.mFindResults;
        FindWallpaperTargetResult.TopWallpaper topWallpaper =
                findWallpaperTargetResult.mTopWallpaper;
        topWallpaper.mTopHideWhenLockedWallpaper = null;
        topWallpaper.mTopShowWhenLockedWallpaper = null;
        findWallpaperTargetResult.mNeedsShowWhenLockedWallpaper = false;
        findWallpaperTargetResult.wallpaperTarget = null;
        findWallpaperTargetResult.useTopWallpaperAsTarget = false;
        findWallpaperTargetResult.isWallpaperTargetForLetterbox = false;
        WindowManagerService windowManagerService = this.mService;
        ActivityTaskManagerService activityTaskManagerService = windowManagerService.mAtmService;
        boolean z = activityTaskManagerService.mSupportsFreeformWindowManagement;
        if (activityTaskManagerService.mNaturalSwitchingController.mNaturalSwitchingRunning
                && (!activityTaskManagerService.mMultiTaskingController.mIsMinimalBatteryUse)) {
            findWallpaperTargetResult.useTopWallpaperAsTarget = true;
        }
        int size = this.mWallpaperTokens.size() - 1;
        while (true) {
            zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_WALLPAPER_enabled;
            if (size < 0) {
                break;
            }
            WallpaperWindowToken wallpaperWindowToken =
                    (WallpaperWindowToken) this.mWallpaperTokens.get(size);
            boolean z2 = wallpaperWindowToken.mShowWhenLocked;
            if (!CoreRune.FW_FOLD_WALLPAPER_POLICY
                    || !displayContent.isDefaultDisplay
                    || wallpaperWindowToken.canShowInCurrentDevice()) {
                for (int childCount = wallpaperWindowToken.getChildCount() - 1;
                        childCount >= 0;
                        childCount--) {
                    WindowState windowState6 =
                            (WindowState) wallpaperWindowToken.getChildAt(childCount);
                    if (windowState6.mIsWallpaper) {
                        FindWallpaperTargetResult.TopWallpaper topWallpaper2 =
                                findWallpaperTargetResult.mTopWallpaper;
                        if (z2
                                && (windowState5 = topWallpaper2.mTopShowWhenLockedWallpaper)
                                        == null) {
                            if (windowState5 != windowState6 && zArr[0]) {
                                ProtoLogImpl_54989576.d(
                                        ProtoLogGroup.WM_DEBUG_WALLPAPER,
                                        6943105284590482059L,
                                        0,
                                        null,
                                        String.valueOf(windowState6),
                                        String.valueOf(topWallpaper2.mTopShowWhenLockedWallpaper));
                            }
                            topWallpaper2.mTopShowWhenLockedWallpaper = windowState6;
                        } else if (!z2
                                && (windowState4 = topWallpaper2.mTopHideWhenLockedWallpaper)
                                        == null) {
                            if (windowState4 != windowState6 && zArr[0]) {
                                ProtoLogImpl_54989576.d(
                                        ProtoLogGroup.WM_DEBUG_WALLPAPER,
                                        4151327328872447804L,
                                        0,
                                        null,
                                        String.valueOf(windowState6),
                                        String.valueOf(topWallpaper2.mTopHideWhenLockedWallpaper));
                            }
                            topWallpaper2.mTopHideWhenLockedWallpaper = windowState6;
                        }
                    }
                }
            }
            size--;
        }
        displayContent.forAllWindows((ToBooleanFunction) this.mFindWallpaperTargetFunction, true);
        if (findWallpaperTargetResult.mNeedsShowWhenLockedWallpaper) {
            findWallpaperTargetResult.useTopWallpaperAsTarget = true;
        }
        if (findWallpaperTargetResult.wallpaperTarget == null
                && findWallpaperTargetResult.useTopWallpaperAsTarget) {
            boolean z3 =
                    displayContent.isKeyguardLocked()
                            || (displayContent.isAodShowing()
                                    && !windowManagerService
                                            .mAtmService
                                            .mDexController
                                            .mDexDisplayActivated);
            FindWallpaperTargetResult.TopWallpaper topWallpaper3 =
                    findWallpaperTargetResult.mTopWallpaper;
            if (z3 || (windowState3 = topWallpaper3.mTopHideWhenLockedWallpaper) == null) {
                windowState3 = topWallpaper3.mTopShowWhenLockedWallpaper;
            }
            findWallpaperTargetResult.wallpaperTarget = windowState3;
        }
        WindowState windowState7 = findWallpaperTargetResult.wallpaperTarget;
        if (this.mWallpaperTarget == windowState7
                || ((windowState2 = this.mPrevWallpaperTarget) != null
                        && windowState2 == windowState7)) {
            WindowState windowState8 = this.mPrevWallpaperTarget;
            if (windowState8 != null && !windowState8.isAnimating(3)) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WALLPAPER, -3477087868568520027L, 0, null, null);
                }
                this.mPrevWallpaperTarget = null;
                this.mWallpaperTarget = windowState7;
            }
        } else {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_WALLPAPER,
                        -3751289048117070874L,
                        0,
                        null,
                        String.valueOf(windowState7),
                        String.valueOf(this.mWallpaperTarget),
                        String.valueOf(Debug.getCallers(5)));
            }
            this.mPrevWallpaperTarget = null;
            final WindowState windowState9 = this.mWallpaperTarget;
            this.mWallpaperTarget = windowState7;
            if (windowState9 == null && windowState7 != null) {
                updateWallpaperOffsetLocked(windowState7, false);
            }
            if (windowState7 != null && windowState9 != null) {
                boolean isAnimating = windowState9.isAnimating(3);
                boolean isAnimating2 = windowState7.isAnimating(3);
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WALLPAPER,
                            5625223922466895079L,
                            0,
                            null,
                            String.valueOf(isAnimating2),
                            String.valueOf(isAnimating));
                }
                if (isAnimating2
                        && isAnimating
                        && displayContent.getWindow(
                                        new Predicate() { // from class:
                                                          // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((WindowState) obj) == WindowState.this;
                                            }
                                        })
                                != null) {
                    ActivityRecord activityRecord2 = windowState7.mActivityRecord;
                    boolean z4 =
                            (activityRecord2 == null || activityRecord2.isVisibleRequested())
                                    ? false
                                    : true;
                    ActivityRecord activityRecord3 = windowState9.mActivityRecord;
                    boolean z5 =
                            (activityRecord3 == null || activityRecord3.isVisibleRequested())
                                    ? false
                                    : true;
                    if (zArr[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_WALLPAPER,
                                7634524672408826188L,
                                204,
                                null,
                                String.valueOf(windowState9),
                                Boolean.valueOf(z5),
                                String.valueOf(windowState7),
                                Boolean.valueOf(z4));
                    }
                    this.mPrevWallpaperTarget = windowState9;
                    if (z4 && !z5) {
                        if (zArr[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_WALLPAPER,
                                    -4345077332231178044L,
                                    0,
                                    null,
                                    null);
                        }
                        this.mWallpaperTarget = windowState9;
                    } else if (z4 == z5
                            && !displayContent.mOpeningApps.contains(windowState7.mActivityRecord)
                            && (displayContent.mOpeningApps.contains(windowState9.mActivityRecord)
                                    || displayContent.mClosingApps.contains(
                                            windowState9.mActivityRecord))) {
                        this.mWallpaperTarget = windowState9;
                    }
                    findWallpaperTargetResult.wallpaperTarget = windowState7;
                }
            }
        }
        WallpaperWindowToken tokenForTarget = getTokenForTarget(this.mWallpaperTarget);
        boolean z6 = tokenForTarget != null;
        if (z6) {
            WindowState windowState10 = this.mWallpaperTarget;
            float f = windowState10.mWallpaperX;
            if (f >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                tokenForTarget.mWallpaperX = f;
                tokenForTarget.mWallpaperXStep = windowState10.mWallpaperXStep;
            }
            float f2 = windowState10.mWallpaperY;
            if (f2 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                tokenForTarget.mWallpaperY = f2;
                tokenForTarget.mWallpaperYStep = windowState10.mWallpaperYStep;
            }
            int i = windowState10.mWallpaperDisplayOffsetX;
            if (i != Integer.MIN_VALUE) {
                tokenForTarget.mWallpaperDisplayOffsetX = i;
            }
            int i2 = windowState10.mWallpaperDisplayOffsetY;
            if (i2 != Integer.MIN_VALUE) {
                tokenForTarget.mWallpaperDisplayOffsetY = i2;
            }
        }
        boolean z7 =
                displayContent.isKeyguardLocked()
                        || (displayContent.isAodShowing()
                                && !windowManagerService
                                        .mAtmService
                                        .mDexController
                                        .mDexDisplayActivated);
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WALLPAPER,
                    257349083882992098L,
                    15,
                    null,
                    Boolean.valueOf(z6),
                    Boolean.valueOf(z7));
        }
        FindWallpaperTargetResult.TopWallpaper topWallpaper4 =
                findWallpaperTargetResult.mTopWallpaper;
        if (z7 || (windowState = topWallpaper4.mTopHideWhenLockedWallpaper) == null) {
            windowState = topWallpaper4.mTopShowWhenLockedWallpaper;
        }
        WallpaperWindowToken asWallpaperToken =
                windowState != null ? windowState.mToken.asWallpaperToken() : null;
        for (int size2 = this.mWallpaperTokens.size() - 1; size2 >= 0; size2--) {
            WallpaperWindowToken wallpaperWindowToken2 =
                    (WallpaperWindowToken) this.mWallpaperTokens.get(size2);
            boolean z8 = z6 && wallpaperWindowToken2 == asWallpaperToken;
            if (wallpaperWindowToken2.mVisibleRequested != z8) {
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(
                            ProtoLogGroup.WM_DEBUG_WALLPAPER,
                            -7936547457136708587L,
                            12,
                            null,
                            String.valueOf(wallpaperWindowToken2.token),
                            Boolean.valueOf(z8));
                }
                wallpaperWindowToken2.setVisibility(z8);
            }
            WindowState windowState11 =
                    wallpaperWindowToken2.mDisplayContent.mWallpaperController.mWallpaperTarget;
            if (z8 && windowState11 != null) {
                RecentsAnimationController recentsAnimationController =
                        wallpaperWindowToken2.mWmService.mRecentsAnimationController;
                if (recentsAnimationController == null
                        || !recentsAnimationController.isAnimatingTask(windowState11.getTask())) {
                    ActivityRecord activityRecord4 = windowState11.mActivityRecord;
                    if ((activityRecord4 == null || activityRecord4.isVisibleRequested())
                            && windowState11.mToken.hasFixedRotationTransform()) {
                        wallpaperWindowToken2.linkFixedRotationTransform(windowState11.mToken);
                    } else if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX
                            && !wallpaperWindowToken2
                                    .mWmService
                                    .mFlags
                                    .mRespectNonTopVisibleFixedOrientation
                            && wallpaperWindowToken2.mDisplayContent.isKeyguardGoingAway()) {
                        if (((PhoneWindowManager) wallpaperWindowToken2.mWmService.mPolicy)
                                        .isKeyguardHostWindow(windowState11.mAttrs)
                                && (activityRecord =
                                                wallpaperWindowToken2.mDisplayContent.mFocusedApp)
                                        != null
                                && activityRecord.windowsCanBeWallpaperTarget()
                                && wallpaperWindowToken2.mDisplayContent
                                        .isFixedRotationLaunchingApp(activityRecord)) {
                            wallpaperWindowToken2.linkFixedRotationTransform(activityRecord);
                        }
                    }
                } else {
                    ActivityRecord activityRecord5 =
                            recentsAnimationController.mTargetActivityRecord;
                    if (activityRecord5 != null) {
                        wallpaperWindowToken2.linkFixedRotationTransform(activityRecord5);
                    }
                }
            }
            if (!wallpaperWindowToken2.mTransitionController.inTransition(wallpaperWindowToken2)) {
                wallpaperWindowToken2.setVisible$1(z8);
            }
        }
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WALLPAPER,
                    7408402065665963407L,
                    61,
                    null,
                    Long.valueOf(displayContent.mDisplayId),
                    Boolean.valueOf(z6),
                    Boolean.valueOf(
                            displayContent.isKeyguardLocked() || displayContent.isAodShowing()));
        }
        if (z6) {
            boolean z9 = this.mLastFrozen;
            boolean z10 = findWallpaperTargetResult.isWallpaperTargetForLetterbox;
            if (z9 != z10) {
                this.mLastFrozen = z10;
                sendWindowWallpaperCommand(
                        z10 ? "android.wallpaper.freeze" : "android.wallpaper.unfreeze",
                        0,
                        0,
                        0,
                        null,
                        false);
            }
        }
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION
                && (blackLetterboxConfig = displayContent.mMultiTaskingAppCompatConfiguration)
                        != null) {
            blackLetterboxConfig.onAdjustWallpaperWindows(
                    z6 && findWallpaperTargetResult.isWallpaperTargetForLetterbox);
        }
        if (zArr[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_WALLPAPER,
                    -8598497865499265448L,
                    0,
                    null,
                    String.valueOf(this.mWallpaperTarget),
                    String.valueOf(this.mPrevWallpaperTarget));
        }
    }

    public boolean canScreenshotWallpaper() {
        return canScreenshotWallpaper(getTopVisibleWallpaper());
    }

    public final boolean canScreenshotWallpaper(WindowState windowState) {
        if (!((PhoneWindowManager) this.mService.mPolicy).mDefaultDisplayPolicy.mScreenOnEarly) {
            Slog.i("WindowManager", "Attempted to take screenshot while display was off.");
            return false;
        }
        if (windowState != null) {
            return true;
        }
        Slog.i("WindowManager", "No visible wallpaper to screenshot");
        return false;
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.print("  ");
        printWriter.print("displayId=");
        BroadcastStats$$ExternalSyntheticOutline0.m(
                this.mDisplayContent.mDisplayId, printWriter, "  ", "mWallpaperTarget=");
        printWriter.println(this.mWallpaperTarget);
        printWriter.print("  ");
        printWriter.print("mLastWallpaperZoomOut=");
        printWriter.println(this.mLastWallpaperZoomOut);
        if (this.mPrevWallpaperTarget != null) {
            printWriter.print("  ");
            printWriter.print("mPrevWallpaperTarget=");
            printWriter.println(this.mPrevWallpaperTarget);
        }
        printWriter.print("  ");
        printWriter.println("mFindResults");
        printWriter.print("  ");
        printWriter.print("  mTopHideWhenLockedWallpaper=");
        FindWallpaperTargetResult findWallpaperTargetResult = this.mFindResults;
        printWriter.println(findWallpaperTargetResult.mTopWallpaper.mTopHideWhenLockedWallpaper);
        if (findWallpaperTargetResult.mTopWallpaper.mTopHideWhenLockedWallpaper != null) {
            printWriter.print("  ");
            printWriter.print("    mTopHideWhenLockedWallpaper.Token=");
            printWriter.println(
                    findWallpaperTargetResult.mTopWallpaper.mTopHideWhenLockedWallpaper.mToken);
        }
        printWriter.print("  ");
        printWriter.print("  mTopShowWhenLockedWallpaper=");
        printWriter.println(findWallpaperTargetResult.mTopWallpaper.mTopShowWhenLockedWallpaper);
        if (findWallpaperTargetResult.mTopWallpaper.mTopShowWhenLockedWallpaper != null) {
            printWriter.print("  ");
            printWriter.print("    mTopShowWhenLockedWallpaper.Token=");
            printWriter.println(
                    findWallpaperTargetResult.mTopWallpaper.mTopShowWhenLockedWallpaper.mToken);
        }
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            WallpaperWindowToken wallpaperWindowToken =
                    (WallpaperWindowToken) this.mWallpaperTokens.get(size);
            printWriter.print("  ");
            printWriter.println("token " + wallpaperWindowToken + ":");
            printWriter.print("  ");
            printWriter.print("  canShowWhenLocked=");
            printWriter.println(wallpaperWindowToken.mShowWhenLocked);
            if (CoreRune.FW_FOLD_WALLPAPER_POLICY) {
                printWriter.print("  ");
                printWriter.print("  isForPrimaryDevice=");
                printWriter.println(wallpaperWindowToken.isForPrimaryDevice());
                printWriter.print("  ");
                printWriter.print("  canShowInCurrentDevice=");
                printWriter.println(wallpaperWindowToken.canShowInCurrentDevice());
            }
            dumpValue(printWriter, "mWallpaperX", wallpaperWindowToken.mWallpaperX);
            dumpValue(printWriter, "mWallpaperY", wallpaperWindowToken.mWallpaperY);
            dumpValue(printWriter, "mWallpaperXStep", wallpaperWindowToken.mWallpaperXStep);
            dumpValue(printWriter, "mWallpaperYStep", wallpaperWindowToken.mWallpaperYStep);
            dumpValue(
                    printWriter,
                    "mWallpaperDisplayOffsetX",
                    wallpaperWindowToken.mWallpaperDisplayOffsetX);
            dumpValue(
                    printWriter,
                    "mWallpaperDisplayOffsetY",
                    wallpaperWindowToken.mWallpaperDisplayOffsetY);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0024, code lost:

       if (r3.mDisplayContent.isAodShowing() == false) goto L16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002c, code lost:

       if (r2.mAtmService.mDexController.mDexDisplayActivated != false) goto L16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:

       r4 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:

       r3 = r3.mFindResults.mTopWallpaper;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0036, code lost:

       if (r4 != false) goto L23;
    */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0038, code lost:

       r4 = r3.mTopHideWhenLockedWallpaper;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003a, code lost:

       if (r4 == null) goto L23;
    */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003f, code lost:

       if (r4 != null) goto L26;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0048, code lost:

       return r4.mToken.asWallpaperToken();
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003d, code lost:

       r4 = r3.mTopShowWhenLockedWallpaper;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x002f, code lost:

       r4 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x001c, code lost:

       if (r2.isKeyguardLocked() == false) goto L11;
    */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0016, code lost:

       if (((com.android.server.policy.PhoneWindowManager) r2.mPolicy).isKeyguardHostWindow(r4.mAttrs) != false) goto L9;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.WallpaperWindowToken getTokenForTarget(
            com.android.server.wm.WindowState r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L4
            return r0
        L4:
            boolean r1 = r4.canShowWhenLocked()
            com.android.server.wm.WindowManagerService r2 = r3.mService
            if (r1 != 0) goto L18
            com.android.server.policy.WindowManagerPolicy r1 = r2.mPolicy
            android.view.WindowManager$LayoutParams r4 = r4.mAttrs
            com.android.server.policy.PhoneWindowManager r1 = (com.android.server.policy.PhoneWindowManager) r1
            boolean r4 = r1.isKeyguardHostWindow(r4)
            if (r4 == 0) goto L1e
        L18:
            boolean r4 = r2.isKeyguardLocked()
            if (r4 != 0) goto L31
        L1e:
            com.android.server.wm.DisplayContent r4 = r3.mDisplayContent
            boolean r4 = r4.isAodShowing()
            if (r4 == 0) goto L2f
            com.android.server.wm.ActivityTaskManagerService r4 = r2.mAtmService
            com.android.server.wm.DexController r4 = r4.mDexController
            boolean r4 = r4.mDexDisplayActivated
            if (r4 != 0) goto L2f
            goto L31
        L2f:
            r4 = 0
            goto L32
        L31:
            r4 = 1
        L32:
            com.android.server.wm.WallpaperController$FindWallpaperTargetResult r3 = r3.mFindResults
            com.android.server.wm.WallpaperController$FindWallpaperTargetResult$TopWallpaper r3 = r3.mTopWallpaper
            if (r4 != 0) goto L3d
            com.android.server.wm.WindowState r4 = r3.mTopHideWhenLockedWallpaper
            if (r4 == 0) goto L3d
            goto L3f
        L3d:
            com.android.server.wm.WindowState r4 = r3.mTopShowWhenLockedWallpaper
        L3f:
            if (r4 != 0) goto L42
            goto L48
        L42:
            com.android.server.wm.WindowToken r3 = r4.mToken
            com.android.server.wm.WallpaperWindowToken r0 = r3.asWallpaperToken()
        L48:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WallpaperController.getTokenForTarget(com.android.server.wm.WindowState):com.android.server.wm.WallpaperWindowToken");
    }

    public final WindowState getTopVisibleWallpaper() {
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            WallpaperWindowToken wallpaperWindowToken =
                    (WallpaperWindowToken) this.mWallpaperTokens.get(size);
            for (int childCount = wallpaperWindowToken.getChildCount() - 1;
                    childCount >= 0;
                    childCount--) {
                WindowState windowState = (WindowState) wallpaperWindowToken.getChildAt(childCount);
                if (windowState.mWinAnimator.getShown()
                        && windowState.mWinAnimator.mLastAlpha
                                > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    return windowState;
                }
            }
        }
        return null;
    }

    public final void hideWallpapers(WindowState windowState) {
        WindowState windowState2 = this.mWallpaperTarget;
        if ((windowState2 == null
                        || (windowState2 == windowState && this.mPrevWallpaperTarget == null))
                && !this.mFindResults.useTopWallpaperAsTarget) {
            for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
                WallpaperWindowToken wallpaperWindowToken =
                        (WallpaperWindowToken) this.mWallpaperTokens.get(size);
                wallpaperWindowToken.setVisibility(false);
                if (wallpaperWindowToken.mClientVisible
                        && ProtoLogImpl_54989576.Cache.WM_DEBUG_WALLPAPER_enabled[0]) {
                    ProtoLogImpl_54989576.d(
                            ProtoLogGroup.WM_DEBUG_WALLPAPER,
                            -5254364639040552989L,
                            0,
                            null,
                            String.valueOf(wallpaperWindowToken),
                            String.valueOf(windowState),
                            String.valueOf(this.mWallpaperTarget),
                            String.valueOf(this.mPrevWallpaperTarget),
                            String.valueOf(Debug.getCallers(5)));
                }
            }
        }
    }

    public final boolean isBelowWallpaperTarget(WindowState windowState) {
        return this.mWallpaperTarget != null && windowState.mBaseLayer <= 0;
    }

    public final boolean isWallpaperTarget(WindowState windowState) {
        return windowState == this.mWallpaperTarget;
    }

    public final boolean isWallpaperVisible() {
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            if (((WallpaperWindowToken) this.mWallpaperTokens.get(size)).mClientVisible) {
                return true;
            }
        }
        return false;
    }

    public final boolean notifyDisplaySwitch(boolean z) {
        boolean z2 = false;
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            WallpaperWindowToken wallpaperWindowToken =
                    (WallpaperWindowToken) this.mWallpaperTokens.get(size);
            for (int childCount = wallpaperWindowToken.getChildCount() - 1;
                    childCount >= 0;
                    childCount--) {
                WindowState windowState = (WindowState) wallpaperWindowToken.getChildAt(childCount);
                if (!z || windowState.mWinAnimator.getShown()) {
                    try {
                        windowState.mClient.dispatchWallpaperCommand(
                                "android.wallpaper.displayswitch",
                                0,
                                0,
                                z ? 1 : 0,
                                (Bundle) null,
                                false);
                    } catch (RemoteException e) {
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                "Failed to dispatch COMMAND_DISPLAY_SWITCH ", e, "WindowManager");
                    }
                    z2 = true;
                }
            }
        }
        return z2;
    }

    public final Bitmap screenshotWallpaperLocked(Rect rect) {
        Rect bounds;
        WindowState topVisibleWallpaper = getTopVisibleWallpaper();
        if (!canScreenshotWallpaper(topVisibleWallpaper)) {
            return null;
        }
        if (rect != null) {
            bounds = new Rect(rect);
            bounds.offset(-topVisibleWallpaper.mXOffset, -topVisibleWallpaper.mYOffset);
        } else {
            bounds = topVisibleWallpaper.getBounds();
            bounds.offsetTo(0, 0);
        }
        WindowToken windowToken = topVisibleWallpaper.mToken;
        ScreenCapture.ScreenshotHardwareBuffer captureLayers =
                (windowToken == null || !windowToken.mIsPortraitWindowToken)
                        ? ScreenCapture.captureLayers(
                                topVisibleWallpaper.mSurfaceControl, bounds, 1.0f)
                        : ScreenCapture.captureLayers(windowToken.mSurfaceControl, bounds, 1.0f);
        if (captureLayers != null) {
            return Bitmap.wrapHardwareBuffer(
                    captureLayers.getHardwareBuffer(), captureLayers.getColorSpace());
        }
        Slog.w("WindowManager", "Failed to screenshot wallpaper");
        return null;
    }

    public final void sendWindowWallpaperCommand(
            String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            WallpaperWindowToken wallpaperWindowToken =
                    (WallpaperWindowToken) this.mWallpaperTokens.get(size);
            boolean z2 = z;
            for (int size2 = wallpaperWindowToken.mChildren.size() - 1; size2 >= 0; size2--) {
                try {
                    ((WindowState) wallpaperWindowToken.mChildren.get(size2))
                            .mClient.dispatchWallpaperCommand(str, i, i2, i3, bundle, z2);
                    z2 = false;
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public void setMaxWallpaperScale(float f) {
        this.mMaxWallpaperScale = f;
    }

    public void setMinWallpaperScale(float f) {
        this.mMinWallpaperScale = f;
    }

    public void setShouldOffsetWallpaperCenter(boolean z) {
        this.mShouldOffsetWallpaperCenter = z;
    }

    public final SurfaceControl startRemoteWallpaperAnimation(
            final IBinder iBinder, int i, IRemoteAnimationRunner iRemoteAnimationRunner) {
        if (this.mRemoteWallpaperAnimAreaMap.containsKey(iBinder)) {
            return null;
        }
        DisplayContent displayContent = this.mDisplayContent;
        DisplayAreaPolicyBuilder.Result result =
                (DisplayAreaPolicyBuilder.Result) displayContent.mDisplayAreaPolicy;
        result.getClass();
        ArrayList arrayList = new ArrayList();
        DisplayAreaPolicyBuilder.Result.getDisplayAreas(result.mRoot, arrayList);
        for (int i2 = 0; i2 < result.mDisplayAreaGroupRoots.size(); i2++) {
            DisplayAreaPolicyBuilder.Result.getDisplayAreas(
                    (RootDisplayArea) result.mDisplayAreaGroupRoots.get(i2), arrayList);
        }
        DisplayArea displayArea = arrayList.size() == 1 ? (DisplayArea) arrayList.get(0) : null;
        if (displayArea == null) {
            return null;
        }
        this.mRemoteWallpaperAnimAreaMap.put(iBinder, displayArea);
        displayArea.startAnimation(
                displayContent.getPendingTransaction(),
                new RemoteWallpaperAnimationAdapter(
                        iBinder, displayArea, i, iRemoteAnimationRunner),
                false,
                512,
                new SurfaceAnimator
                        .OnAnimationFinishedCallback() { // from class:
                                                         // com.android.server.wm.WallpaperController$$ExternalSyntheticLambda0
                    @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                    public final void onAnimationFinished(
                            int i3, AnimationAdapter animationAdapter) {
                        WallpaperController wallpaperController = WallpaperController.this;
                        IBinder iBinder2 = iBinder;
                        WindowManagerGlobalLock windowManagerGlobalLock =
                                wallpaperController.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                wallpaperController.mRemoteWallpaperAnimAreaMap.remove(iBinder2);
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                });
        this.mService.scheduleAnimationLocked();
        return displayArea.getAnimationLeash();
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0268  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateWallpaperOffset(com.android.server.wm.WindowState r29, boolean r30) {
        /*
            Method dump skipped, instructions count: 805
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WallpaperController.updateWallpaperOffset(com.android.server.wm.WindowState,"
                    + " boolean):boolean");
    }

    public final void updateWallpaperOffsetLocked(WindowState windowState, boolean z) {
        WindowState windowState2 = this.mWallpaperTarget;
        if (windowState2 == null
                && windowState.mToken.isVisible()
                && windowState.mTransitionController.inTransition()) {
            windowState2 = windowState;
        }
        WallpaperWindowToken tokenForTarget = getTokenForTarget(windowState2);
        if (tokenForTarget == null) {
            return;
        }
        float f = windowState2.mWallpaperX;
        if (f >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            tokenForTarget.mWallpaperX = f;
        } else {
            float f2 = windowState.mWallpaperX;
            if (f2 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                tokenForTarget.mWallpaperX = f2;
            }
        }
        float f3 = windowState2.mWallpaperY;
        if (f3 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            tokenForTarget.mWallpaperY = f3;
        } else {
            float f4 = windowState.mWallpaperY;
            if (f4 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                tokenForTarget.mWallpaperY = f4;
            }
        }
        int i = windowState2.mWallpaperDisplayOffsetX;
        if (i != Integer.MIN_VALUE) {
            tokenForTarget.mWallpaperDisplayOffsetX = i;
        } else {
            int i2 = windowState.mWallpaperDisplayOffsetX;
            if (i2 != Integer.MIN_VALUE) {
                tokenForTarget.mWallpaperDisplayOffsetX = i2;
            }
        }
        int i3 = windowState2.mWallpaperDisplayOffsetY;
        if (i3 != Integer.MIN_VALUE) {
            tokenForTarget.mWallpaperDisplayOffsetY = i3;
        } else {
            int i4 = windowState.mWallpaperDisplayOffsetY;
            if (i4 != Integer.MIN_VALUE) {
                tokenForTarget.mWallpaperDisplayOffsetY = i4;
            }
        }
        float f5 = windowState2.mWallpaperXStep;
        if (f5 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            tokenForTarget.mWallpaperXStep = f5;
        } else {
            float f6 = windowState.mWallpaperXStep;
            if (f6 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                tokenForTarget.mWallpaperXStep = f6;
            }
        }
        float f7 = windowState2.mWallpaperYStep;
        if (f7 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            tokenForTarget.mWallpaperYStep = f7;
        } else {
            float f8 = windowState.mWallpaperYStep;
            if (f8 >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                tokenForTarget.mWallpaperYStep = f8;
            }
        }
        tokenForTarget.updateWallpaperOffset(z);
    }

    public final boolean wallpaperTransitionReady() {
        WindowManagerService windowManagerService;
        boolean z;
        boolean z2 = true;
        int size = this.mWallpaperTokens.size() - 1;
        while (true) {
            windowManagerService = this.mService;
            if (size < 0) {
                z = true;
                break;
            }
            if (((WallpaperWindowToken) this.mWallpaperTokens.get(size))
                    .hasVisibleNotDrawnWallpaper()) {
                int i = this.mWallpaperDrawState;
                z = i == 2;
                if (i == 0) {
                    this.mWallpaperDrawState = 1;
                    windowManagerService.mH.removeMessages(39, this);
                    WindowManagerService.H h = windowManagerService.mH;
                    h.sendMessageDelayed(h.obtainMessage(39, this), 500L);
                }
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WALLPAPER_enabled[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_WALLPAPER,
                            -5402010429724738603L,
                            1,
                            null,
                            Long.valueOf(this.mWallpaperDrawState));
                }
                z2 = false;
            } else {
                size--;
            }
        }
        if (z2) {
            this.mWallpaperDrawState = 0;
            windowManagerService.mH.removeMessages(39, this);
        }
        return z;
    }
}
