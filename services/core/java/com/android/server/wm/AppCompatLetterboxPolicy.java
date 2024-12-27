package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.DisplayCutout;
import android.view.SurfaceControl;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.Letterbox;
import com.android.server.wm.MultiTaskingAppCompatConfiguration;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class AppCompatLetterboxPolicy {
    public final ActivityRecord mActivityRecord;
    public final AppCompatConfiguration mAppCompatConfiguration;
    public final AppCompatRoundedCorners mAppCompatRoundedCorners;
    public boolean mLastShouldShowLetterboxUi;
    public final LetterboxPolicyState mLetterboxPolicyState = new LetterboxPolicyState();

    public final class LetterboxPolicyState {
        public Letterbox mLetterbox;

        public LetterboxPolicyState() {
        }

        public final void getLetterboxInnerBounds(Rect rect) {
            if (!isRunning()) {
                rect.setEmpty();
                return;
            }
            rect.set(this.mLetterbox.mInner);
            WindowState findMainWindow = AppCompatLetterboxPolicy.this.mActivityRecord.findMainWindow(true);
            if (findMainWindow != null) {
                AppCompatUtils.adjustBoundsForTaskbar(findMainWindow, rect);
            }
        }

        public final boolean isRunning() {
            return this.mLetterbox != null;
        }

        public final void updateLetterboxSurfaceIfNeeded(WindowState windowState, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            WindowState windowState2;
            WindowState windowState3;
            if (windowState == null) {
                return;
            }
            int i = windowState.mAttrs.type;
            boolean z = true;
            if ((i == 1 || i == 3) && !windowState.mAnimatingExit) {
                AppCompatLetterboxPolicy appCompatLetterboxPolicy = AppCompatLetterboxPolicy.this;
                appCompatLetterboxPolicy.start(windowState);
                WindowState findMainWindow = appCompatLetterboxPolicy.mActivityRecord.findMainWindow(true);
                if (findMainWindow != null) {
                    if ((findMainWindow == windowState || findMainWindow.areAppWindowBoundsLetterboxed() == windowState.areAppWindowBoundsLetterboxed()) && isRunning()) {
                        Letterbox letterbox = this.mLetterbox;
                        int i2 = 0;
                        if (letterbox.mLastUseFullWindowSurface == letterbox.useFullWindowSurface()) {
                            if (!letterbox.useFullWindowSurface()) {
                                Letterbox.LetterboxSurface[] letterboxSurfaceArr = letterbox.mSurfaces;
                                int length = letterboxSurfaceArr.length;
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= length) {
                                        z = false;
                                        break;
                                    } else if (letterboxSurfaceArr[i3].needsApplySurfaceChanges()) {
                                        break;
                                    } else {
                                        i3++;
                                    }
                                }
                            } else {
                                z = letterbox.mFullWindowSurface.needsApplySurfaceChanges();
                            }
                        }
                        if (z) {
                            Letterbox letterbox2 = this.mLetterbox;
                            boolean useFullWindowSurface = letterbox2.useFullWindowSurface();
                            boolean z2 = letterbox2.mLastUseFullWindowSurface;
                            Letterbox.LetterboxSurface[] letterboxSurfaceArr2 = letterbox2.mSurfaces;
                            Letterbox.LetterboxSurface letterboxSurface = letterbox2.mFullWindowSurface;
                            if (z2 == useFullWindowSurface) {
                                if (letterbox2.useFullWindowSurface()) {
                                    letterboxSurface.applySurfaceChanges(transaction, transaction2);
                                    int length2 = letterboxSurfaceArr2.length;
                                    while (i2 < length2) {
                                        letterboxSurfaceArr2[i2].remove();
                                        i2++;
                                    }
                                    return;
                                }
                                int length3 = letterboxSurfaceArr2.length;
                                while (i2 < length3) {
                                    letterboxSurfaceArr2[i2].applySurfaceChanges(transaction, transaction2);
                                    i2++;
                                }
                                letterboxSurface.remove();
                                return;
                            }
                            letterbox2.mLastUseFullWindowSurface = useFullWindowSurface;
                            if (useFullWindowSurface) {
                                letterboxSurface.mSurfaceFrameRelative.setEmpty();
                                if (letterboxSurface.mInputInterceptor == null && (windowState3 = letterbox2.mInputWindow) != null) {
                                    letterboxSurface.attachInput(windowState3);
                                }
                                letterboxSurface.applySurfaceChanges(transaction, transaction2);
                                int length4 = letterboxSurfaceArr2.length;
                                while (i2 < length4) {
                                    Letterbox.LetterboxSurface letterboxSurface2 = letterboxSurfaceArr2[i2];
                                    letterboxSurface2.mSurfaceFrameRelative.setEmpty();
                                    SurfaceControl surfaceControl = letterboxSurface2.mSurface;
                                    if (surfaceControl != null) {
                                        transaction.remove(surfaceControl);
                                        letterboxSurface2.mSurface = null;
                                    }
                                    letterboxSurface2.remove();
                                    i2++;
                                }
                                return;
                            }
                            int length5 = letterboxSurfaceArr2.length;
                            while (i2 < length5) {
                                Letterbox.LetterboxSurface letterboxSurface3 = letterboxSurfaceArr2[i2];
                                letterboxSurface3.mSurfaceFrameRelative.setEmpty();
                                if (letterboxSurface3.mInputInterceptor == null && (windowState2 = letterbox2.mInputWindow) != null) {
                                    letterboxSurface3.attachInput(windowState2);
                                }
                                letterboxSurface3.applySurfaceChanges(transaction, transaction2);
                                i2++;
                            }
                            letterboxSurface.mSurfaceFrameRelative.setEmpty();
                            SurfaceControl surfaceControl2 = letterboxSurface.mSurface;
                            if (surfaceControl2 != null) {
                                transaction.remove(surfaceControl2);
                                letterboxSurface.mSurface = null;
                            }
                            letterboxSurface.remove();
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.wm.AppCompatLetterboxPolicy$$ExternalSyntheticLambda1] */
    public AppCompatLetterboxPolicy(ActivityRecord activityRecord, AppCompatConfiguration appCompatConfiguration) {
        this.mActivityRecord = activityRecord;
        this.mAppCompatRoundedCorners = new AppCompatRoundedCorners(activityRecord, new Predicate() { // from class: com.android.server.wm.AppCompatLetterboxPolicy$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                WindowState windowState = (WindowState) obj;
                return AppCompatLetterboxPolicy.this.shouldShowLetterboxUi(windowState) && !windowState.isLetterboxedForDisplayCutout();
            }
        });
        this.mAppCompatConfiguration = appCompatConfiguration;
    }

    public Rect getCropBoundsIfNeeded(WindowState windowState) {
        return this.mAppCompatRoundedCorners.getCropBoundsIfNeeded(windowState);
    }

    public final int getLetterboxDirection() {
        LetterboxPolicyState letterboxPolicyState = this.mLetterboxPolicyState;
        AppCompatLetterboxPolicy appCompatLetterboxPolicy = AppCompatLetterboxPolicy.this;
        DisplayContent displayContent = appCompatLetterboxPolicy.mActivityRecord.mDisplayContent;
        if (!letterboxPolicyState.isRunning() || displayContent == null) {
            return 0;
        }
        DisplayCutout calculateDisplayCutoutForRotation = displayContent.calculateDisplayCutoutForRotation(displayContent.mDisplayRotation.mRotation, appCompatLetterboxPolicy.mActivityRecord.isLayoutNeededInUdcCutout());
        Letterbox letterbox = letterboxPolicyState.mLetterbox;
        int safeInsetLeft = calculateDisplayCutoutForRotation.getSafeInsetLeft();
        int safeInsetRight = calculateDisplayCutoutForRotation.getSafeInsetRight();
        if (safeInsetLeft > 0) {
            Letterbox.LetterboxSurface letterboxSurface = letterbox.mLeft;
            if (!letterboxSurface.mSurfaceFrameRelative.isEmpty() && safeInsetLeft == Math.max(0, letterboxSurface.mLayoutFrameGlobal.width())) {
                return 1;
            }
        }
        if (safeInsetRight > 0) {
            Letterbox.LetterboxSurface letterboxSurface2 = letterbox.mRight;
            return (letterboxSurface2.mSurfaceFrameRelative.isEmpty() || safeInsetRight != Math.max(0, letterboxSurface2.mLayoutFrameGlobal.width())) ? 0 : 2;
        }
        letterbox.getClass();
        return 0;
    }

    public boolean shouldShowLetterboxUi(WindowState windowState) {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord.mAppCompatController.mAppCompatOverrides.mAppCompatOrientationOverrides.mOrientationOverridesState.mIsRelaunchingAfterRequestedOrientationChanged) {
            return this.mLastShouldShowLetterboxUi;
        }
        boolean z = (activityRecord.isInLetterboxAnimation() || activityRecord.mVisible || activityRecord.isVisibleRequested()) && windowState.areAppWindowBoundsLetterboxed() && (windowState.mAttrs.flags & 1048576) == 0;
        this.mLastShouldShowLetterboxUi = z;
        return z;
    }

    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.server.wm.AppCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.android.server.wm.AppCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1] */
    public final void start(final WindowState windowState) {
        final DisplayContent displayContent;
        MultiTaskingAppCompatConfiguration.BlackLetterboxConfig blackLetterboxConfig;
        if (windowState == null) {
            return;
        }
        int i = windowState.mAttrs.type;
        if ((i == 1 || i == 3) && !windowState.mAnimatingExit) {
            AppCompatRoundedCorners appCompatRoundedCorners = this.mAppCompatRoundedCorners;
            appCompatRoundedCorners.getClass();
            SurfaceControl surfaceControl = windowState.mSurfaceControl;
            if (surfaceControl != null && surfaceControl.isValid()) {
                appCompatRoundedCorners.mActivityRecord.getSyncTransaction().setCrop(surfaceControl, appCompatRoundedCorners.getCropBoundsIfNeeded(windowState)).setCornerRadius(surfaceControl, appCompatRoundedCorners.getRoundedCornersRadius(windowState));
            }
            ActivityRecord activityRecord = this.mActivityRecord;
            WindowState findMainWindow = activityRecord.findMainWindow(true);
            if (findMainWindow != null) {
                if (findMainWindow == windowState || findMainWindow.areAppWindowBoundsLetterboxed() == windowState.areAppWindowBoundsLetterboxed()) {
                    final AppCompatLetterboxOverrides appCompatLetterboxOverrides = activityRecord.mAppCompatController.mAppCompatOverrides.mAppCompatLetterboxOverrides;
                    boolean z = appCompatLetterboxOverrides.getLetterboxBackgroundType() == 3 && shouldShowLetterboxUi(windowState) && !windowState.isLetterboxedForDisplayCutout() && (appCompatLetterboxOverrides.getLetterboxWallpaperBlurRadiusPx() > 0 || appCompatLetterboxOverrides.getLetterboxWallpaperDarkScrimAlpha() > FullScreenMagnificationGestureHandler.MAX_SCALE) && (appCompatLetterboxOverrides.getLetterboxWallpaperBlurRadiusPx() <= 0 || appCompatLetterboxOverrides.isLetterboxWallpaperBlurSupported());
                    if (appCompatLetterboxOverrides.mShowWallpaperForLetterboxBackground != z) {
                        appCompatLetterboxOverrides.mShowWallpaperForLetterboxBackground = z;
                        activityRecord.requestUpdateWallpaperIfNeeded();
                        if (!z && (displayContent = activityRecord.mDisplayContent) != null && displayContent.mWallpaperController.mWallpaperTarget == windowState) {
                            if (CoreRune.MT_APP_COMPAT_CONFIGURATION && (blackLetterboxConfig = displayContent.mMultiTaskingAppCompatConfiguration) != null) {
                                blackLetterboxConfig.onAdjustWallpaperWindows(false);
                            }
                            displayContent.mWmService.mH.post(new Runnable() { // from class: com.android.server.wm.AppCompatLetterboxPolicy$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DisplayContent displayContent2 = DisplayContent.this;
                                    AppCompatLetterboxOverrides appCompatLetterboxOverrides2 = appCompatLetterboxOverrides;
                                    WindowState windowState2 = windowState;
                                    WindowManagerGlobalLock windowManagerGlobalLock = displayContent2.mWmService.mGlobalLock;
                                    WindowManagerService.boostPriorityForLockedSection();
                                    synchronized (windowManagerGlobalLock) {
                                        try {
                                            if (!appCompatLetterboxOverrides2.hasWallpaperBackgroundForLetterbox() && displayContent2.mWallpaperController.mWallpaperTarget == windowState2) {
                                                displayContent2.pendingLayoutChanges |= 4;
                                                displayContent2.setLayoutNeeded();
                                                displayContent2.mWmService.mWindowPlacerLocked.requestTraversal();
                                                WindowManagerService.resetPriorityAfterLockedSection();
                                                return;
                                            }
                                            WindowManagerService.resetPriorityAfterLockedSection();
                                        } catch (Throwable th) {
                                            WindowManagerService.resetPriorityAfterLockedSection();
                                            throw th;
                                        }
                                    }
                                }
                            });
                        }
                    }
                    boolean shouldShowLetterboxUi = shouldShowLetterboxUi(windowState);
                    final LetterboxPolicyState letterboxPolicyState = this.mLetterboxPolicyState;
                    if (!shouldShowLetterboxUi) {
                        if (letterboxPolicyState.isRunning()) {
                            Letterbox letterbox = letterboxPolicyState.mLetterbox;
                            Rect rect = Letterbox.EMPTY_RECT;
                            letterbox.layout(rect, rect, Letterbox.ZERO_POINT);
                            return;
                        }
                        return;
                    }
                    boolean isRunning = letterboxPolicyState.isRunning();
                    AppCompatLetterboxPolicy appCompatLetterboxPolicy = AppCompatLetterboxPolicy.this;
                    if (!isRunning) {
                        AppCompatController appCompatController = appCompatLetterboxPolicy.mActivityRecord.mAppCompatController;
                        AppCompatLetterboxOverrides appCompatLetterboxOverrides2 = appCompatController.mAppCompatOverrides.mAppCompatLetterboxOverrides;
                        final int i2 = 1;
                        ?? r7 = new Supplier() { // from class: com.android.server.wm.AppCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i3 = i2;
                                Object obj = letterboxPolicyState;
                                switch (i3) {
                                    case 0:
                                        AppCompatLetterboxPolicy appCompatLetterboxPolicy2 = AppCompatLetterboxPolicy.this;
                                        boolean isInLetterboxAnimation = appCompatLetterboxPolicy2.mActivityRecord.isInLetterboxAnimation();
                                        ActivityRecord activityRecord2 = appCompatLetterboxPolicy2.mActivityRecord;
                                        return isInLetterboxAnimation ? activityRecord2.task.mSurfaceControl : activityRecord2.mSurfaceControl;
                                    case 1:
                                        return AppCompatLetterboxPolicy.this.mActivityRecord.makeChildSurface(null);
                                    default:
                                        return ((Letterbox) obj).mInner;
                                }
                            }
                        };
                        ActivityRecord activityRecord2 = appCompatLetterboxPolicy.mActivityRecord;
                        final int i3 = 0;
                        Letterbox letterbox2 = new Letterbox(r7, activityRecord2.mWmService.mTransactionFactory, appCompatController.mAppCompatReachabilityPolicy, appCompatLetterboxOverrides2, new Supplier() { // from class: com.android.server.wm.AppCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i32 = i3;
                                Object obj = letterboxPolicyState;
                                switch (i32) {
                                    case 0:
                                        AppCompatLetterboxPolicy appCompatLetterboxPolicy2 = AppCompatLetterboxPolicy.this;
                                        boolean isInLetterboxAnimation = appCompatLetterboxPolicy2.mActivityRecord.isInLetterboxAnimation();
                                        ActivityRecord activityRecord22 = appCompatLetterboxPolicy2.mActivityRecord;
                                        return isInLetterboxAnimation ? activityRecord22.task.mSurfaceControl : activityRecord22.mSurfaceControl;
                                    case 1:
                                        return AppCompatLetterboxPolicy.this.mActivityRecord.makeChildSurface(null);
                                    default:
                                        return ((Letterbox) obj).mInner;
                                }
                            }
                        });
                        letterboxPolicyState.mLetterbox = letterbox2;
                        letterbox2.mInputWindow = windowState;
                        letterbox2.mLastUseFullWindowSurface = letterbox2.useFullWindowSurface();
                        if (letterbox2.useFullWindowSurface()) {
                            letterbox2.mFullWindowSurface.attachInput(windowState);
                        } else {
                            for (Letterbox.LetterboxSurface letterboxSurface : letterbox2.mSurfaces) {
                                letterboxSurface.attachInput(windowState);
                            }
                        }
                        AppCompatReachabilityPolicy appCompatReachabilityPolicy = activityRecord2.mAppCompatController.mAppCompatReachabilityPolicy;
                        final Letterbox letterbox3 = letterboxPolicyState.mLetterbox;
                        Objects.requireNonNull(letterbox3);
                        final int i4 = 2;
                        appCompatReachabilityPolicy.mLetterboxInnerBoundsSupplier = new Supplier() { // from class: com.android.server.wm.AppCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i32 = i4;
                                Object obj = letterbox3;
                                switch (i32) {
                                    case 0:
                                        AppCompatLetterboxPolicy appCompatLetterboxPolicy2 = AppCompatLetterboxPolicy.this;
                                        boolean isInLetterboxAnimation = appCompatLetterboxPolicy2.mActivityRecord.isInLetterboxAnimation();
                                        ActivityRecord activityRecord22 = appCompatLetterboxPolicy2.mActivityRecord;
                                        return isInLetterboxAnimation ? activityRecord22.task.mSurfaceControl : activityRecord22.mSurfaceControl;
                                    case 1:
                                        return AppCompatLetterboxPolicy.this.mActivityRecord.makeChildSurface(null);
                                    default:
                                        return ((Letterbox) obj).mInner;
                                }
                            }
                        };
                    }
                    Point point = new Point();
                    boolean isInLetterboxAnimation = appCompatLetterboxPolicy.mActivityRecord.isInLetterboxAnimation();
                    ActivityRecord activityRecord3 = appCompatLetterboxPolicy.mActivityRecord;
                    if (isInLetterboxAnimation) {
                        activityRecord3.task.getPosition(point);
                    } else {
                        activityRecord3.getPosition(point);
                    }
                    Rect fixedRotationTransformDisplayBounds = activityRecord3.getFixedRotationTransformDisplayBounds();
                    if (fixedRotationTransformDisplayBounds == null) {
                        fixedRotationTransformDisplayBounds = activityRecord3.inMultiWindowMode() ? activityRecord3.getTaskFragment().getBounds() : activityRecord3.getRootTask().getParent().getBounds();
                    }
                    if (CoreRune.MW_EMBED_ACTIVITY && activityRecord3.isEmbedded()) {
                        fixedRotationTransformDisplayBounds.set(activityRecord3.getTaskFragment().getBounds());
                    }
                    letterboxPolicyState.mLetterbox.layout(fixedRotationTransformDisplayBounds, activityRecord3.mAppCompatController.mTransparentPolicy.mTransparentPolicyState.isRunning() ? activityRecord3.getBounds() : windowState.mWindowFrames.mFrame, point);
                    if (activityRecord3.mAppCompatController.mAppCompatOverrides.mAppCompatReachabilityOverrides.mReachabilityState.mIsDoubleTapEvent) {
                        activityRecord3.task.dispatchTaskInfoChangedIfNeeded(true);
                    }
                }
            }
        }
    }
}
