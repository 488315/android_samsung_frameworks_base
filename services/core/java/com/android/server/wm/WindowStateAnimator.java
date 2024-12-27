package com.android.server.wm;

import android.content.Context;
import android.graphics.Rect;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.Slog;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceEffects;
import android.view.WindowManager;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.window.flags.Flags;

import com.samsung.android.knox.localservice.RemoteInjectionInternal;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WindowStateAnimator {
    public boolean mAnimationIsEntrance;
    public final WindowAnimator mAnimator;
    public final int mAttrType;
    public final Context mContext;
    public int mDrawState;
    public boolean mEnterAnimationPending;
    public boolean mEnteringAnimation;
    public final boolean mIsWallpaper;
    public boolean mLastHidden;
    public RemoteInjectionInternal mRemoteInjection;
    public final WindowManagerService mService;
    public final Session mSession;
    public WindowSurfaceController mSurfaceController;
    public final WallpaperController mWallpaperControllerLocked;
    public final WindowState mWin;
    public float mShownAlpha = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public float mAlpha = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public float mLastAlpha = FullScreenMagnificationGestureHandler.MAX_SCALE;
    public float mPopOverAlpha = -1.0f;
    public final Rect mSystemDecorRect = new Rect();

    public WindowStateAnimator(WindowState windowState) {
        WindowManagerService windowManagerService = windowState.mWmService;
        this.mService = windowManagerService;
        this.mAnimator = windowManagerService.mAnimator;
        this.mContext = windowManagerService.mContext;
        this.mWin = windowState;
        this.mSession = windowState.mSession;
        this.mAttrType = windowState.mAttrs.type;
        this.mIsWallpaper = windowState.mIsWallpaper;
        this.mWallpaperControllerLocked = windowState.getDisplayContent().mWallpaperController;
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x01b8, code lost:

       if (r24 != 2) goto L132;
    */
    /* JADX WARN: Removed duplicated region for block: B:48:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x012f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean applyAnimationLocked(int r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowStateAnimator.applyAnimationLocked(int,"
                    + " boolean):boolean");
    }

    public final void applyEnterAnimationLocked() {
        int i;
        ActivityRecord activityRecord;
        if (this.mEnterAnimationPending) {
            this.mEnterAnimationPending = false;
            i = 1;
        } else {
            i = 3;
        }
        int i2 = this.mAttrType;
        WindowState windowState = this.mWin;
        if (i2 != 1
                && !this.mIsWallpaper
                && ((activityRecord = windowState.mActivityRecord) == null
                        || !activityRecord.hasStartingWindow())) {
            applyAnimationLocked(i, true);
        }
        WindowManagerService windowManagerService = this.mService;
        if (windowManagerService.mAccessibilityController.hasCallbacks()) {
            windowManagerService.mAccessibilityController.onWindowTransition(windowState, i);
        }
    }

    public final boolean commitFinishDrawingLocked() {
        int i = this.mDrawState;
        if (i != 2 && i != 3) {
            return false;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_DEBUG_ANIM,
                    -3490933626936411542L,
                    0,
                    null,
                    String.valueOf(this.mSurfaceController));
        }
        this.mDrawState = 3;
        WindowState windowState = this.mWin;
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord == null
                || activityRecord.canShowWindows()
                || windowState.mAttrs.type == 3) {
            return windowState.performShowLocked();
        }
        return false;
    }

    public final WindowSurfaceController createSurfaceLocked() {
        boolean z;
        int i;
        Session session = this.mSession;
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        if (windowSurfaceController != null) {
            return windowSurfaceController;
        }
        WindowState windowState = this.mWin;
        windowState.mHasSurface = false;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_DEBUG_ANIM,
                    -6088246515441976339L,
                    0,
                    null,
                    String.valueOf(this));
        }
        resetDrawState();
        WindowManagerService windowManagerService = this.mService;
        windowManagerService.makeWindowFreezingScreenIfNeededLocked(windowState);
        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
        int i2 = (Flags.secureWindowState() || !windowState.isSecureLocked()) ? 4 : 132;
        try {
            if (this.mRemoteInjection == null) {
                this.mRemoteInjection =
                        (RemoteInjectionInternal)
                                LocalServices.getService(RemoteInjectionInternal.class);
            }
            RemoteInjectionInternal remoteInjectionInternal = this.mRemoteInjection;
            if (remoteInjectionInternal != null
                    && (i = windowState.mAttrs.type) != 2000
                    && i != 2014
                    && i != 2017
                    && i != 2024
                    && i != 2095
                    && i != 2226
                    && i != 2621
                    && i != 2019
                    && i != 2020) {
                switch (i) {
                    case 2401:
                    case 2402:
                    case 2403:
                        break;
                    default:
                        if (remoteInjectionInternal.isRemoteControlDisabled(
                                UserHandle.getUserId(windowState.mOwnerUid))) {
                            i2 |= 15728640;
                            break;
                        }
                        break;
                }
            }
        } catch (Exception unused) {
            Slog.e(
                    "WindowManager",
                    "Exception occurred while checking for isRemoteControlDisabled");
        }
        if ((windowState.mAttrs.privateFlags & 1048576) != 0) {
            i2 |= 64;
        }
        int i3 = i2;
        try {
            try {
                z = true;
            } catch (Surface.OutOfResourcesException unused2) {
                z = true;
            }
            try {
                this.mSurfaceController =
                        new WindowSurfaceController(
                                layoutParams.getTitle().toString(),
                                (layoutParams.flags & 16777216) != 0 ? -3 : layoutParams.format,
                                i3,
                                this,
                                layoutParams.type);
                if (!Flags.setScPropertiesInClient()) {
                    this.mSurfaceController.setColorSpaceAgnostic(
                            windowState.getPendingTransaction(),
                            (layoutParams.privateFlags & 16777216) != 0);
                }
                SurfaceControl surfaceControl = this.mSurfaceController.mSurfaceControl;
                SurfaceControl.Transaction pendingTransaction = windowState.getPendingTransaction();
                pendingTransaction.setMetadata(surfaceControl, 30, windowState.mAttrs.surfaceType);
                pendingTransaction.setMetadata(
                        surfaceControl, 31, layoutParams.type == 2601 ? 1 : 0);
                this.mSurfaceController.setInternalPresentationOnly(
                        windowState.getPendingTransaction(),
                        (layoutParams.samsungFlags & Integer.MIN_VALUE) != 0);
                windowState.mHasSurface = true;
                windowState.mLastBlurRadius = 0;
                windowState.mInputWindowHandle.mChanged = true;
                if (ProtoLogImpl_54989576.Cache.WM_SHOW_SURFACE_ALLOC_enabled[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_SHOW_SURFACE_ALLOC,
                            2353125758087345363L,
                            336,
                            null,
                            String.valueOf(this.mSurfaceController),
                            String.valueOf(session.mSurfaceSession),
                            Long.valueOf(session.mPid),
                            Long.valueOf(layoutParams.format),
                            Long.valueOf(i3),
                            String.valueOf(this));
                }
                this.mLastHidden = true;
                return this.mSurfaceController;
            } catch (Surface.OutOfResourcesException unused3) {
                Slog.w("WindowManager", "OutOfResourcesException creating surface");
                windowManagerService.mRoot.reclaimSomeSurfaceMemory(this, "create", z);
                this.mDrawState = 0;
                return null;
            }
        } catch (Exception e) {
            Slog.e("WindowManager", "Exception creating surface (parent dead?)", e);
            this.mDrawState = 0;
            return null;
        }
    }

    public final void destroySurface(SurfaceControl.Transaction transaction) {
        WindowState windowState = this.mWin;
        try {
            try {
                WindowSurfaceController windowSurfaceController = this.mSurfaceController;
                if (windowSurfaceController != null) {
                    windowSurfaceController.destroy(transaction);
                }
            } catch (RuntimeException e) {
                Slog.w(
                        "WindowManager",
                        "Exception thrown when destroying surface "
                                + this
                                + " surface "
                                + this.mSurfaceController
                                + " session "
                                + this.mSession
                                + ": "
                                + e);
            }
        } finally {
            windowState.mHasSurface = false;
            this.mSurfaceController = null;
            this.mDrawState = 0;
        }
    }

    public final void destroySurfaceLocked(SurfaceControl.Transaction transaction) {
        if (this.mSurfaceController == null) {
            return;
        }
        WindowState windowState = this.mWin;
        windowState.mHidden = true;
        try {
            if (ProtoLogImpl_54989576.Cache.WM_SHOW_SURFACE_ALLOC_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_SHOW_SURFACE_ALLOC,
                        -4491856282178275074L,
                        0,
                        null,
                        String.valueOf(windowState),
                        String.valueOf(new RuntimeException().fillInStackTrace()));
            }
            destroySurface(transaction);
            boolean ensureWallpaperInTransitions = Flags.ensureWallpaperInTransitions();
            WallpaperController wallpaperController = this.mWallpaperControllerLocked;
            if (!ensureWallpaperInTransitions) {
                wallpaperController.hideWallpapers(windowState);
            } else if (wallpaperController.isWallpaperTarget(windowState)) {
                windowState.requestUpdateWallpaperIfNeeded();
            }
        } catch (RuntimeException e) {
            Slog.w(
                    "WindowManager",
                    "Exception thrown when destroying Window "
                            + this
                            + " surface "
                            + this.mSurfaceController
                            + " session "
                            + this.mSession
                            + ": "
                            + e.toString());
        }
        windowState.mHasSurface = false;
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        if (windowSurfaceController != null) {
            windowSurfaceController.setShown(false);
        }
        this.mSurfaceController = null;
        this.mDrawState = 0;
    }

    public final String drawStateToString() {
        int i = this.mDrawState;
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 4 ? Integer.toString(i) : "HAS_DRAWN"
                                        : "READY_TO_SHOW"
                                : "COMMIT_DRAW_PENDING"
                        : "DRAW_PENDING"
                : "NO_SURFACE";
    }

    public final boolean getShown() {
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        if (windowSurfaceController != null) {
            return windowSurfaceController.mSurfaceShown;
        }
        return false;
    }

    public final boolean hasSurface() {
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        return (windowSurfaceController == null || windowSurfaceController.mSurfaceControl == null)
                ? false
                : true;
    }

    public final void hide(SurfaceControl.Transaction transaction, String str) {
        if (this.mLastHidden) {
            return;
        }
        this.mLastHidden = true;
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        if (windowSurfaceController != null) {
            if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                        -2055407587764455051L,
                        0,
                        null,
                        str,
                        String.valueOf(windowSurfaceController.title));
            }
            if (!windowSurfaceController.mSurfaceShown
                    || windowSurfaceController.mSurfaceControl == null) {
                return;
            }
            windowSurfaceController.setShown(false);
            try {
                transaction.hide(windowSurfaceController.mSurfaceControl);
                WindowStateAnimator windowStateAnimator = windowSurfaceController.mAnimator;
                if (windowStateAnimator.mIsWallpaper) {
                    DisplayContent displayContent = windowStateAnimator.mWin.getDisplayContent();
                    EventLog.writeEvent(
                            33001,
                            Integer.valueOf(displayContent.mDisplayId),
                            0,
                            String.valueOf(displayContent.mWallpaperController.mWallpaperTarget));
                }
            } catch (RuntimeException unused) {
                Slog.w("WindowManager", "Exception hiding surface in " + windowSurfaceController);
            }
        }
    }

    public final void prepareSurfaceLocked(SurfaceControl.Transaction transaction) {
        boolean hasSurface = hasSurface();
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled;
        WindowState windowState = this.mWin;
        if (!hasSurface) {
            if (windowState.getOrientationChanging() && windowState.isGoneForLayout()) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_ORIENTATION,
                            8602950884833508970L,
                            0,
                            null,
                            String.valueOf(windowState));
                }
                windowState.setOrientationChanging(false);
                return;
            }
            return;
        }
        if ((!this.mIsWallpaper || !this.mService.mRoot.mWallpaperActionPending)
                && windowState.mDragResizing == windowState.computeDragResizing()) {
            this.mShownAlpha = this.mAlpha;
            float f = this.mPopOverAlpha;
            if (f != -1.0f) {
                this.mShownAlpha = f;
            }
        }
        if (windowState.mWinAnimator != null) {
            WindowManager.LayoutParams layoutParams = windowState.mAttrs;
            int i =
                    (layoutParams.flags & 2) != 0 && (layoutParams.samsungFlags & 64) != 0
                            ? (int) ((layoutParams.dimAmount / 0.4f) * 352.0f)
                            : 0;
            if (windowState.mLastBlurRadius != i) {
                long j = layoutParams.dimDuration;
                if (j == -1) {
                    j = 200;
                }
                SurfaceEffects.Effect.Builder pixelEffectType =
                        SurfaceEffects.newBuilder()
                                .setPixelEffectType(SurfaceEffects.PixEffectType.BLUR);
                WindowManager.LayoutParams layoutParams2 = windowState.mAttrs;
                if (layoutParams2.height == -2 || layoutParams2.width == -2) {
                    pixelEffectType.makeFullscreen();
                }
                boolean z =
                        CoreRune.IS_DEBUG_LEVEL_MID
                                && windowState.mAttrs.type == 2011
                                && windowState.getDisplayContent().isDefaultDisplay;
                if (i > 0) {
                    SurfaceEffects.Effect.Builder animationMode =
                            pixelEffectType.setAnimationMode(
                                    SurfaceEffects.AnimationMode.ONCE_STAY_END);
                    SurfaceEffects.AnimParam animParam = SurfaceEffects.AnimParam.BLUR_RADIUS;
                    animationMode
                            .addPixAnimation(
                                    animParam,
                                    0,
                                    windowState.mLastBlurRadius,
                                    SurfaceEffects.InterpMode.HOLD)
                            .addPixAnimation(
                                    animParam, (int) j, i, SurfaceEffects.InterpMode.SMOOTH_IN);
                    if (z) {
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                i,
                                "applyBlurEffectInTransaction: Set ONCE_STAY_END blurRadius=",
                                "WindowManager");
                    }
                } else {
                    SurfaceEffects.Effect.Builder animationMode2 =
                            pixelEffectType.setAnimationMode(
                                    SurfaceEffects.AnimationMode.ONCE_DESTROY);
                    SurfaceEffects.AnimParam animParam2 = SurfaceEffects.AnimParam.BLUR_RADIUS;
                    animationMode2
                            .addPixAnimation(
                                    animParam2,
                                    0,
                                    windowState.mLastBlurRadius,
                                    SurfaceEffects.InterpMode.HOLD)
                            .addPixAnimation(
                                    animParam2, (int) j, i, SurfaceEffects.InterpMode.SMOOTH_OUT);
                    if (z) {
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                i,
                                "applyBlurEffectInTransaction: Set ONCE_DESTROY blurRadius=",
                                "WindowManager");
                    }
                }
                String bytes = pixelEffectType.build().getBytes();
                SurfaceControl surfaceControl =
                        windowState.mWinAnimator.mSurfaceController.mSurfaceControl;
                if (surfaceControl != null) {
                    transaction.startSurfaceAnimation(surfaceControl, bytes);
                }
                windowState.mLastBlurRadius = i;
            }
        }
        if (windowState.isOnScreen()) {
            float f2 = this.mLastAlpha;
            float f3 = this.mShownAlpha;
            if (f2 != f3 || this.mLastHidden) {
                this.mLastAlpha = f3;
                boolean[] zArr2 = ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled;
                if (zArr2[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                            -5079712802591263622L,
                            168,
                            null,
                            String.valueOf(this.mSurfaceController),
                            Double.valueOf(this.mShownAlpha),
                            Double.valueOf(windowState.mHScale),
                            Double.valueOf(windowState.mVScale),
                            String.valueOf(windowState));
                }
                WindowSurfaceController windowSurfaceController = this.mSurfaceController;
                float f4 = this.mShownAlpha;
                SurfaceControl surfaceControl2 = windowSurfaceController.mSurfaceControl;
                if (surfaceControl2 != null) {
                    transaction.setAlpha(surfaceControl2, f4);
                    if (this.mDrawState == 4 && this.mLastHidden) {
                        WindowSurfaceController windowSurfaceController2 = this.mSurfaceController;
                        if (zArr2[2]) {
                            ProtoLogImpl_54989576.i(
                                    ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                                    -8398940245851553814L,
                                    0,
                                    null,
                                    String.valueOf(windowSurfaceController2.title));
                        }
                        if (!windowSurfaceController2.mSurfaceShown) {
                            windowSurfaceController2.setShown(true);
                            transaction.show(windowSurfaceController2.mSurfaceControl);
                            WindowStateAnimator windowStateAnimator =
                                    windowSurfaceController2.mAnimator;
                            if (windowStateAnimator.mIsWallpaper) {
                                DisplayContent displayContent =
                                        windowStateAnimator.mWin.getDisplayContent();
                                EventLog.writeEvent(
                                        33001,
                                        Integer.valueOf(displayContent.mDisplayId),
                                        1,
                                        String.valueOf(
                                                displayContent
                                                        .mWallpaperController
                                                        .mWallpaperTarget));
                            }
                        }
                        this.mLastHidden = false;
                        DisplayContent displayContent2 = windowState.getDisplayContent();
                        if (!displayContent2.mLastHasContent) {
                            displayContent2.pendingLayoutChanges |= 8;
                        }
                    }
                }
            }
        } else {
            hide(transaction, "prepareSurfaceLocked");
            if (!windowState.mIsWallpaper || !Flags.ensureWallpaperInTransitions()) {
                this.mWallpaperControllerLocked.hideWallpapers(windowState);
            }
            if (windowState.getOrientationChanging() && windowState.isGoneForLayout()) {
                windowState.setOrientationChanging(false);
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_ORIENTATION,
                            8602950884833508970L,
                            0,
                            null,
                            String.valueOf(windowState));
                }
            }
        }
        if (windowState.getOrientationChanging()) {
            if (windowState.isDrawn()) {
                windowState.setOrientationChanging(false);
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_ORIENTATION,
                            7457181879495900576L,
                            0,
                            null,
                            String.valueOf(windowState));
                    return;
                }
                return;
            }
            if (windowState.mDisplayContent.shouldSyncRotationChange(windowState)) {
                windowState.mWmService.mRoot.mOrientationChangeComplete = false;
                this.mAnimator.mLastWindowFreezeSource = windowState;
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_ORIENTATION,
                        -2824875917893878016L,
                        0,
                        null,
                        String.valueOf(windowState));
            }
        }
    }

    public final void resetDrawState() {
        this.mDrawState = 1;
        WindowState windowState = this.mWin;
        ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord == null || activityRecord.isAnimating(1)) {
            return;
        }
        ActivityRecord activityRecord2 = windowState.mActivityRecord;
        activityRecord2.allDrawn = false;
        activityRecord2.mLastAllDrawn = false;
    }

    public final void setColorSpaceAgnosticLocked(boolean z) {
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        if (windowSurfaceController == null) {
            return;
        }
        windowSurfaceController.setColorSpaceAgnostic(this.mWin.getPendingTransaction(), z);
    }

    public final void setInternalPresentationOnly(boolean z) {
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        if (windowSurfaceController == null) {
            return;
        }
        windowSurfaceController.setInternalPresentationOnly(this.mWin.getPendingTransaction(), z);
    }

    public final void setOpaqueLocked(boolean z) {
        WindowSurfaceController windowSurfaceController = this.mSurfaceController;
        if (windowSurfaceController == null) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_SHOW_TRANSACTIONS,
                    7813672046338784579L,
                    3,
                    null,
                    Boolean.valueOf(z),
                    String.valueOf(windowSurfaceController.title));
        }
        if (windowSurfaceController.mSurfaceControl == null) {
            return;
        }
        windowSurfaceController
                .mAnimator
                .mWin
                .getPendingTransaction()
                .setOpaque(windowSurfaceController.mSurfaceControl, z);
        windowSurfaceController.mService.scheduleAnimationLocked();
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer("WindowStateAnimator{");
        stringBuffer.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuffer.append(' ');
        stringBuffer.append(this.mWin.mAttrs.getTitle());
        stringBuffer.append('}');
        return stringBuffer.toString();
    }
}
