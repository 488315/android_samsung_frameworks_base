package com.android.server.wm;

import android.graphics.Rect;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.window.flags.Flags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Dimmer {
    public final DimmerAnimationHelper.AnimationAdapterFactory mAnimationAdapterFactory;
    public DimState mDimState;
    public final WindowContainer mHost;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DimState {
        public final DimmerAnimationHelper mAnimationHelper;
        public final SurfaceControl mDimSurface;
        public final WindowContainer mHostContainer;
        public WindowState mLastDimmingWindow;
        public boolean mSurfaceLayoutAdjusted;
        public boolean mSkipAnimation = false;
        public boolean mAnimateExit = true;
        public boolean mIsVisible = false;
        public final Rect mDimBounds = new Rect();

        public DimState() {
            this.mHostContainer = Dimmer.this.mHost;
            this.mAnimationHelper = new DimmerAnimationHelper(Dimmer.this.mAnimationAdapterFactory);
            try {
                this.mDimSurface = makeDimLayer();
            } catch (Surface.OutOfResourcesException unused) {
                Log.w("WindowManager", "OutOfResourcesException creating dim surface");
            }
        }

        public final void adjustSurfaceLayout(SurfaceControl.Transaction transaction) {
            SurfaceControl surfaceControl = this.mDimSurface;
            Rect rect = this.mDimBounds;
            transaction.setPosition(surfaceControl, rect.left, rect.top);
            transaction.setWindowCrop(
                    this.mDimSurface, this.mDimBounds.width(), this.mDimBounds.height());
            this.mSurfaceLayoutAdjusted = true;
        }

        public final boolean isDimming() {
            return this.mLastDimmingWindow != null
                    && (this.mHostContainer.isVisibleRequested() || !Flags.useTasksDimOnly());
        }

        public final SurfaceControl makeDimLayer() {
            Dimmer dimmer = Dimmer.this;
            return dimmer.mHost
                    .makeChildSurface(null)
                    .setParent(dimmer.mHost.getSurfaceControl())
                    .setColorLayer()
                    .setName("Dim Layer for - " + dimmer.mHost.getName())
                    .setCallsite("DimLayer.makeDimLayer")
                    .build();
        }

        public final void remove(SurfaceControl.Transaction transaction) {
            this.mAnimationHelper.stopCurrentAnimation(this.mDimSurface);
            if (!this.mDimSurface.isValid()) {
                Log.w("WindowManager", "Tried to remove " + this.mDimSurface + " multiple times\n");
                return;
            }
            transaction.remove(this.mDimSurface);
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_DIMMER_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_DIMMER,
                        7945918822134922801L,
                        0,
                        null,
                        String.valueOf(this),
                        String.valueOf(transaction));
            }
        }

        public final void setReady(SurfaceControl.Transaction transaction) {
            long j;
            final DimmerAnimationHelper dimmerAnimationHelper = this.mAnimationHelper;
            DimmerAnimationHelper.Change change = dimmerAnimationHelper.mCurrentProperties;
            float f = change.mAlpha;
            int i = change.mBlurRadius;
            WindowState windowState = change.mDimmingContainer;
            WindowContainer windowContainer = change.mGeometryParent;
            DimmerAnimationHelper.Change change2 = dimmerAnimationHelper.mRequestedProperties;
            change.mAlpha = change2.mAlpha;
            change.mBlurRadius = change2.mBlurRadius;
            change.mDimmingContainer = change2.mDimmingContainer;
            change.mGeometryParent = change2.mGeometryParent;
            WindowState windowState2 = change2.mDimmingContainer;
            if (windowState2 == null) {
                Log.e(
                        "WindowManager",
                        dimmerAnimationHelper
                                + " does not have a dimming container. Have you forgotten to call"
                                + " adjustRelativeLayer?");
                return;
            }
            if (windowState2.mSurfaceControl == null) {
                Log.w(
                        "WindowManager",
                        "container " + change2.mDimmingContainer + "does not have a surface");
                remove(transaction);
                return;
            }
            if (!this.mIsVisible) {
                transaction.show(this.mDimSurface);
                transaction.setAlpha(
                        this.mDimSurface, FullScreenMagnificationGestureHandler.MAX_SCALE);
                this.mIsVisible = true;
            }
            SurfaceControl surfaceControl = this.mDimSurface;
            WindowContainer windowContainer2 = change2.mGeometryParent;
            SurfaceControl surfaceControl2 =
                    windowContainer != windowContainer2
                            ? windowContainer2.getSurfaceControl()
                            : null;
            WindowState windowState3 = change2.mDimmingContainer;
            SurfaceControl surfaceControl3 =
                    windowState3 != windowState ? windowState3.mSurfaceControl : null;
            if (surfaceControl2 != null) {
                try {
                    transaction.reparent(surfaceControl, surfaceControl2);
                } catch (NullPointerException e) {
                    Log.w(
                            "WindowManager",
                            "Tried to change parent of dim " + surfaceControl + " after remove",
                            e);
                }
            }
            if (surfaceControl3 != null) {
                transaction.setRelativeLayer(surfaceControl, surfaceControl3, -1);
            }
            if (Math.abs(f - change2.mAlpha) < 1.0E-4f && i == change2.mBlurRadius) {
                if (isDimming()) {
                    return;
                }
                remove(transaction);
                return;
            }
            dimmerAnimationHelper.stopCurrentAnimation(this.mDimSurface);
            boolean z = this.mSkipAnimation;
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_DIMMER_enabled;
            if (z
                    || (windowState != null
                            && windowState == change2.mDimmingContainer
                            && isDimming())) {
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(
                            ProtoLogGroup.WM_DEBUG_DIMMER,
                            3778139410556664218L,
                            24,
                            null,
                            String.valueOf(this),
                            Double.valueOf(f),
                            Long.valueOf(change2.mBlurRadius));
                }
                dimmerAnimationHelper.setCurrentAlphaBlur(transaction, this.mDimSurface);
                this.mSkipAnimation = false;
                return;
            }
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_DIMMER,
                        -6357087772993832060L,
                        0,
                        null,
                        String.valueOf(this));
            }
            float max = Math.max(f, FullScreenMagnificationGestureHandler.MAX_SCALE);
            int max2 = Math.max(i, 0);
            WindowState windowState4 = change2.mDimmingContainer;
            SurfaceAnimator surfaceAnimator = windowState4.mSurfaceAnimator;
            if (surfaceAnimator != null) {
                AnimationAdapter animationAdapter = surfaceAnimator.mAnimation;
                j =
                        animationAdapter == null
                                ? (long)
                                        (windowState4.mWmService.getTransitionAnimationScaleLocked()
                                                * 200.0f)
                                : animationAdapter.getDurationHint();
            } else {
                j = 0;
            }
            DimmerAnimationHelper.AnimationSpec animationSpec =
                    new DimmerAnimationHelper.AnimationSpec(
                            new DimmerAnimationHelper.AnimationSpec.AnimationExtremes(
                                    Float.valueOf(max), Float.valueOf(change2.mAlpha)),
                            new DimmerAnimationHelper.AnimationSpec.AnimationExtremes(
                                    Integer.valueOf(max2), Integer.valueOf(change2.mBlurRadius)),
                            j);
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_DIMMER,
                        -1187783168730646350L,
                        0,
                        null,
                        String.valueOf(animationSpec));
            }
            dimmerAnimationHelper.mAlphaAnimationSpec = animationSpec;
            SurfaceAnimationRunner surfaceAnimationRunner =
                    this.mHostContainer.mWmService.mSurfaceAnimationRunner;
            dimmerAnimationHelper.mAnimationAdapterFactory.getClass();
            LocalAnimationAdapter localAnimationAdapter =
                    new LocalAnimationAdapter(animationSpec, surfaceAnimationRunner);
            dimmerAnimationHelper.mLocalAnimationAdapter = localAnimationAdapter;
            final float f2 = change2.mAlpha;
            localAnimationAdapter.startAnimation(
                    this.mDimSurface,
                    transaction,
                    4,
                    new SurfaceAnimator
                            .OnAnimationFinishedCallback() { // from class:
                                                             // com.android.server.wm.DimmerAnimationHelper$$ExternalSyntheticLambda0
                        @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                        public final void onAnimationFinished(
                                int i2, AnimationAdapter animationAdapter2) {
                            DisplayContent defaultDisplayContentLocked;
                            DimmerAnimationHelper dimmerAnimationHelper2 =
                                    DimmerAnimationHelper.this;
                            Dimmer.DimState dimState = this;
                            float f3 = f2;
                            dimmerAnimationHelper2.getClass();
                            WindowManagerGlobalLock windowManagerGlobalLock =
                                    dimState.mHostContainer.mWmService.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    SurfaceControl.Transaction syncTransaction =
                                            dimState.mHostContainer.getSyncTransaction();
                                    WindowContainer windowContainer3 = dimState.mHostContainer;
                                    if (windowContainer3.getDisplayContent() == null
                                            && windowContainer3.getPendingTransaction()
                                                    == syncTransaction
                                            && (defaultDisplayContentLocked =
                                                            windowContainer3.mWmService
                                                                    .getDefaultDisplayContentLocked())
                                                    != null) {
                                        syncTransaction =
                                                defaultDisplayContentLocked.getPendingTransaction();
                                    }
                                    dimmerAnimationHelper2.setCurrentAlphaBlur(
                                            syncTransaction, dimState.mDimSurface);
                                    if (f3 == FullScreenMagnificationGestureHandler.MAX_SCALE
                                            && !dimState.isDimming()) {
                                        dimState.remove(syncTransaction);
                                    }
                                    dimmerAnimationHelper2.mLocalAnimationAdapter = null;
                                    dimmerAnimationHelper2.mAlphaAnimationSpec = null;
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    });
        }

        public final String toString() {
            return "Dimmer#DimState with host="
                    + this.mHostContainer
                    + ", surface="
                    + this.mDimSurface;
        }
    }

    public Dimmer(WindowContainer windowContainer) {
        this(windowContainer, new DimmerAnimationHelper.AnimationAdapterFactory());
    }

    public Dimmer(
            WindowContainer windowContainer,
            DimmerAnimationHelper.AnimationAdapterFactory animationAdapterFactory) {
        this.mHost = windowContainer;
        this.mAnimationAdapterFactory = animationAdapterFactory;
    }

    public SurfaceControl getDimLayer() {
        DimState dimState = this.mDimState;
        if (dimState != null) {
            return dimState.mDimSurface;
        }
        return null;
    }

    public final boolean updateDims(SurfaceControl.Transaction transaction) {
        float intValue;
        WindowState windowState;
        ActivityRecord activityRecord;
        DimState dimState = this.mDimState;
        if (dimState == null) {
            return false;
        }
        if (!dimState.isDimming()) {
            DimState dimState2 = this.mDimState;
            if (dimState2.mAnimateExit) {
                DimmerAnimationHelper.Change change =
                        dimState2.mAnimationHelper.mRequestedProperties;
                change.mDimmingContainer = change.mDimmingContainer;
                change.mAlpha = FullScreenMagnificationGestureHandler.MAX_SCALE;
                change.mBlurRadius = 0;
                dimState2.setReady(transaction);
            } else {
                dimState2.remove(transaction);
            }
            this.mDimState = null;
            return false;
        }
        boolean useTasksDimOnly = Flags.useTasksDimOnly();
        WindowContainer windowContainer = this.mHost;
        if (!useTasksDimOnly) {
            this.mDimState.adjustSurfaceLayout(transaction);
        } else if (windowContainer.asTask() == null || !windowContainer.inFreeformWindowingMode()) {
            DimState dimState3 = this.mDimState;
            if (dimState3.mSurfaceLayoutAdjusted) {
                dimState3.mSurfaceLayoutAdjusted = false;
                if (dimState3.mDimSurface.isValid()) {
                    transaction.setPosition(
                            dimState3.mDimSurface,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE);
                    transaction.setWindowCrop(dimState3.mDimSurface, 0, 0);
                } else {
                    Log.e(
                            "WindowManager",
                            "resetSurfaceLayoutIfNeeded: failed, " + dimState3.mDimSurface);
                }
            }
        } else {
            this.mDimState.adjustSurfaceLayout(transaction);
        }
        if (windowContainer.mDisplayContent != null && windowContainer.inFreeformWindowingMode()) {
            intValue =
                    ((Integer)
                                    windowContainer.mWmService.mAtmService.mFreeformController
                                            .mFreeformCornerRadius.get(
                                            windowContainer.mDisplayContent.mDisplayId, 0))
                            .intValue();
        } else {
            intValue = 0.0f;
        }
        if (intValue > FullScreenMagnificationGestureHandler.MAX_SCALE) {
            transaction.setCornerRadius(this.mDimState.mDimSurface, intValue);
        }
        DimState dimState4 = this.mDimState;
        if (!dimState4.mIsVisible
                && (windowState = dimState4.mLastDimmingWindow) != null
                && (activityRecord = windowState.mActivityRecord) != null
                && activityRecord.mStartingData != null) {
            dimState4.mSkipAnimation = true;
        }
        dimState4.setReady(transaction);
        return true;
    }
}
