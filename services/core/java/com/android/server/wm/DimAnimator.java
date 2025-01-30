package com.android.server.wm;

import android.graphics.Point;
import android.os.Debug;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.TransitionInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class DimAnimator {
    public final WindowContainer mContainer;
    SurfaceControl mDimAnimationLayer;

    public DimAnimator(WindowContainer windowContainer) {
        this.mContainer = windowContainer;
    }

    public void createDimAnimationLayerIfNeeded(int i, boolean z, WindowManager.LayoutParams layoutParams) {
        if (canCreateDimAnimationLayer(i, z, layoutParams, null)) {
            createDimAnimationLayer();
        }
    }

    public boolean canCreateDimAnimationLayer(int i, boolean z, WindowManager.LayoutParams layoutParams, TransitionInfo.Change change) {
        if (this.mContainer.mWmService.getTransitionAnimationScaleLocked() <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            return false;
        }
        if (this.mContainer.asActivityRecord() == null && this.mContainer.asTask() == null) {
            return false;
        }
        if (change != null && change.getMode() == 6) {
            return false;
        }
        if (layoutParams != null) {
            float f = layoutParams.dimAmount;
            if ((f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f < 1.0f) || !TransitionAnimation.isDefaultPackageAnimRes(layoutParams.windowAnimations)) {
                return false;
            }
        }
        if (this.mContainer.getDisplayContent().isDefaultDisplay) {
            return (i == 1 || i == 3) && z && !this.mContainer.inMultiWindowMode() && this.mContainer.fillsParent() && this.mContainer.getActivity(new Predicate() { // from class: com.android.server.wm.DimAnimator$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$canCreateDimAnimationLayer$0;
                    lambda$canCreateDimAnimationLayer$0 = DimAnimator.lambda$canCreateDimAnimationLayer$0((ActivityRecord) obj);
                    return lambda$canCreateDimAnimationLayer$0;
                }
            }) == null;
        }
        return false;
    }

    public static /* synthetic */ boolean lambda$canCreateDimAnimationLayer$0(ActivityRecord activityRecord) {
        return activityRecord.isVisibleRequested() && (!activityRecord.fillsParent() || activityRecord.showWallpaper());
    }

    public void createDimAnimationLayer() {
        finishDimAnimation(0);
        if (this.mDimAnimationLayer == null) {
            this.mDimAnimationLayer = this.mContainer.makeChildSurface(null).setName("DimAnimationLayer for " + this.mContainer.getName()).setColorLayer().setParent(this.mContainer.getDisplayContent().getSurfaceControl()).setCallsite("WindowContainer#createAnimatingDimLayer").build();
            if (CoreRune.SAFE_DEBUG) {
                Slog.d(StartingSurfaceController.TAG, "createDimAnimationLayer, mDimAnimationLayer=" + this.mDimAnimationLayer + ", caller=" + Debug.getCallers(6));
            }
        }
    }

    public void startDimAnimation(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = this.mDimAnimationLayer;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        if (CoreRune.SAFE_DEBUG) {
            Slog.d(StartingSurfaceController.TAG, "startDimAnimation, mDimAnimationLayer=" + this.mDimAnimationLayer + ", caller=" + Debug.getCallers(6));
        }
        WindowAnimationSpec createDimAnimationSpec = createDimAnimationSpec();
        transaction.setRelativeLayer(this.mDimAnimationLayer, this.mContainer.getSurfaceControl(), -1);
        transaction.setAlpha(this.mDimAnimationLayer, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        transaction.show(this.mDimAnimationLayer);
        WindowContainer windowContainer = this.mContainer;
        windowContainer.mWmService.mSurfaceAnimationRunner.startAnimation(createDimAnimationSpec, this.mDimAnimationLayer, windowContainer.getSyncTransaction(), null);
        this.mContainer.scheduleAnimation();
    }

    public void finishDimAnimation(int i) {
        SurfaceControl surfaceControl = this.mDimAnimationLayer;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        if (CoreRune.SAFE_DEBUG) {
            Slog.d(StartingSurfaceController.TAG, "finishDimAnimation, mDimAnimationLayer=" + this.mDimAnimationLayer + ", caller=" + Debug.getCallers(6));
        }
        this.mContainer.mWmService.mSurfaceAnimationRunner.onAnimationCancelled(this.mDimAnimationLayer);
        this.mContainer.getSyncTransaction().reparent(this.mDimAnimationLayer, null);
        this.mDimAnimationLayer = null;
        this.mContainer.scheduleAnimation();
        if ((i & 2) != 0) {
            for (WindowContainer parent = this.mContainer.getParent(); parent != null; parent = parent.getParent()) {
                parent.mDimAnimator.finishDimAnimation(2);
            }
        }
    }

    public final WindowAnimationSpec createDimAnimationSpec() {
        return new WindowAnimationSpec(this.mContainer.getDisplayContent().mAppTransition.mTransitionAnimation.loadDimAnimation(), new Point(0, 0), false, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    }
}
