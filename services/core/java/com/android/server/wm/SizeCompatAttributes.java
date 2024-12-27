package com.android.server.wm;

import android.graphics.Rect;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SizeCompatAttributes {
    public final ActivityRecord mActivityRecord;
    public Rect mBounds;
    public boolean mEnabled;
    public final DexSizeCompatController.DexSizeCompatPolicy mReason;
    public Rect mReturnBounds;
    public float mScale = 1.0f;

    public SizeCompatAttributes(
            ActivityRecord activityRecord,
            DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy) {
        this.mActivityRecord = activityRecord;
        this.mReason = dexSizeCompatPolicy;
    }

    public final void cleanUp(DexSizeCompatController.DexSizeCompatPolicy dexSizeCompatPolicy) {
        if (dexSizeCompatPolicy != null) {
            this.mReason.getClass();
        }
        boolean z = this.mScale != 1.0f;
        this.mEnabled = false;
        this.mScale = 1.0f;
        this.mBounds = null;
        this.mReturnBounds = null;
        ActivityRecord activityRecord = this.mActivityRecord;
        activityRecord.mSizeCompatAttributes = null;
        if (z) {
            activityRecord.forAllWindows(
                    (Consumer) new AppCompatSizeCompatModePolicy$$ExternalSyntheticLambda0(),
                    false);
        }
    }

    public final Rect getBounds() {
        if (!hasBounds()) {
            return null;
        }
        if (this.mReturnBounds == null) {
            this.mReturnBounds = new Rect();
        }
        this.mReturnBounds.set(this.mBounds);
        return this.mReturnBounds;
    }

    public final boolean hasBounds() {
        return this.mEnabled && this.mBounds != null;
    }
}
