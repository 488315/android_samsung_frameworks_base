package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowUtils;

/* loaded from: classes3.dex */
public class FreeformStashState {
    public int mAnimType;
    public boolean mAnimating;
    public FreeformColorOverlay mStashDimOverlay;
    public int mStashType;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public float mScale = 1.0f;
    public float mCurrentAlpha = 1.0f;
    public final Rect mLastFreeformBoundsBeforeStash = new Rect();
    public float mFreeformStashYFraction = 0.0f;

    public final void createStashDimOverlay(SurfaceControl surfaceControl, Context context, ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mStashDimOverlay == null && surfaceControl != null && surfaceControl.isValid()) {
            this.mStashDimOverlay = new FreeformColorOverlay();
            this.mTaskInfo = runningTaskInfo;
            Color colorValueOf = Color.valueOf(context.getColor(R.color.freeform_stash_dim_overlay));
            int freeformRoundedCornerRadius = (int) MultiWindowUtils.getFreeformRoundedCornerRadius(context);
            FreeformColorOverlay freeformColorOverlay = this.mStashDimOverlay;
            Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
            synchronized (freeformColorOverlay.mLock) {
                try {
                    if (freeformColorOverlay.isLeashValidLocked()) {
                        freeformColorOverlay.mTransaction.show(freeformColorOverlay.mLeash);
                        freeformColorOverlay.mTransaction.setLayer(freeformColorOverlay.mLeash, 30002);
                        freeformColorOverlay.mTransaction.setColor(freeformColorOverlay.mLeash, colorValueOf.getComponents());
                        freeformColorOverlay.mTransaction.setAlpha(freeformColorOverlay.mLeash, 0.0f);
                        freeformColorOverlay.mTransaction.reparent(freeformColorOverlay.mLeash, surfaceControl);
                        freeformColorOverlay.mTransaction.setPosition(freeformColorOverlay.mLeash, 0.0f, 0.0f);
                        freeformColorOverlay.mCropRect.set(0, 0, bounds.width(), bounds.height());
                        freeformColorOverlay.mTransaction.setCrop(freeformColorOverlay.mLeash, freeformColorOverlay.mCropRect);
                        freeformColorOverlay.mTransaction.setCornerRadius(freeformColorOverlay.mLeash, freeformRoundedCornerRadius);
                        freeformColorOverlay.mTransaction.apply();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void destroyStashDimOverlay() {
        FreeformColorOverlay freeformColorOverlay = this.mStashDimOverlay;
        if (freeformColorOverlay != null) {
            synchronized (freeformColorOverlay.mLock) {
                try {
                    if (freeformColorOverlay.isLeashValidLocked()) {
                        freeformColorOverlay.mTransaction.remove(freeformColorOverlay.mLeash);
                        freeformColorOverlay.mTransaction.apply();
                        freeformColorOverlay.mLeash = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mStashDimOverlay = null;
        }
    }

    public final boolean isLeftStashed() {
        return this.mStashType == 1;
    }

    public final boolean isStashed() {
        return isLeftStashed() || this.mStashType == 2;
    }

    public final void setDimOverlayAlpha(float f) {
        this.mCurrentAlpha = f;
        FreeformColorOverlay freeformColorOverlay = this.mStashDimOverlay;
        if (freeformColorOverlay != null) {
            synchronized (freeformColorOverlay.mLock) {
                try {
                    if (freeformColorOverlay.isLeashValidLocked()) {
                        freeformColorOverlay.mTransaction.show(freeformColorOverlay.mLeash);
                        freeformColorOverlay.mTransaction.setAlpha(freeformColorOverlay.mLeash, f);
                        freeformColorOverlay.mTransaction.apply();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void setStashed(int i) {
        if (this.mStashType != i) {
            this.mStashType = i;
            if (i == 0) {
                destroyStashDimOverlay();
            }
        }
    }
}
