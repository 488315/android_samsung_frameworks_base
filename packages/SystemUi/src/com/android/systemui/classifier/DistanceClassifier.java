package com.android.systemui.classifier;

import android.provider.DeviceConfig;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DistanceClassifier extends FalsingClassifier {
    public DistanceVectors mCachedDistance;
    public boolean mDistanceDirty;
    public final float mHorizontalFlingThresholdPx;
    public final float mHorizontalSwipeThresholdPx;
    public final float mVelocityToDistanceMultiplier;
    public final float mVerticalFlingThresholdPx;
    public final float mVerticalSwipeThresholdPx;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DistanceVectors {
        public final float mDx;
        public final float mDy;
        public final float mVx;
        public final float mVy;

        public DistanceVectors(DistanceClassifier distanceClassifier, float f, float f2, float f3, float f4) {
            this.mDx = f;
            this.mDy = f2;
            this.mVx = f3;
            this.mVy = f4;
        }

        public final String toString() {
            return String.format(null, "{dx=%f, vx=%f, dy=%f, vy=%f}", Float.valueOf(this.mDx), Float.valueOf(this.mVx), Float.valueOf(this.mDy), Float.valueOf(this.mVy));
        }
    }

    public DistanceClassifier(FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        super(falsingDataProvider);
        deviceConfigProxy.getClass();
        this.mVelocityToDistanceMultiplier = DeviceConfig.getFloat("systemui", "brightline_falsing_distance_velcoity_to_distance", 30.0f);
        float f = DeviceConfig.getFloat("systemui", "brightline_falsing_distance_horizontal_fling_threshold_in", 1.0f);
        float f2 = DeviceConfig.getFloat("systemui", "brightline_falsing_distance_vertical_fling_threshold_in", 1.5f);
        float f3 = DeviceConfig.getFloat("systemui", "brightline_falsing_distance_horizontal_swipe_threshold_in", 3.0f);
        float f4 = DeviceConfig.getFloat("systemui", "brightline_falsing_distance_horizontal_swipe_threshold_in", 3.0f);
        float f5 = DeviceConfig.getFloat("systemui", "brightline_falsing_distance_screen_fraction_max_distance", 0.8f);
        this.mHorizontalFlingThresholdPx = Math.min(r2.mWidthPixels * f5, f * this.mDataProvider.mXdpi);
        this.mVerticalFlingThresholdPx = Math.min(r7.mHeightPixels * f5, f2 * this.mDataProvider.mYdpi);
        this.mHorizontalSwipeThresholdPx = Math.min(r7.mWidthPixels * f5, f3 * this.mDataProvider.mXdpi);
        this.mVerticalSwipeThresholdPx = Math.min(r7.mHeightPixels * f5, f4 * this.mDataProvider.mYdpi);
        this.mDistanceDirty = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0041, code lost:
    
        if (java.lang.Math.abs(r1) >= r3.mHorizontalFlingThresholdPx) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0052, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0050, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x004e, code lost:
    
        if (java.lang.Math.abs(r0) >= r3.mVerticalFlingThresholdPx) goto L24;
     */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        boolean z;
        if (i == 10 || i == 18 || i == 11 || i == 12 || i == 13 || i == 15 || i == 17) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        DistanceVectors distances = getDistances();
        float f = distances.mDx;
        float f2 = distances.mVx;
        float f3 = this.mVelocityToDistanceMultiplier;
        float f4 = (f2 * f3) + f;
        float f5 = (distances.mVy * f3) + distances.mDy;
        if (this.mDataProvider.isHorizontal()) {
            boolean z2 = BrightLineFalsingManager.DEBUG;
        } else {
            boolean z3 = BrightLineFalsingManager.DEBUG;
        }
        return !z ? falsed(0.5d, getReason()) : FalsingClassifier.Result.passed(0.5d);
    }

    public final DistanceVectors getDistances() {
        DistanceVectors distanceVectors;
        if (this.mDistanceDirty) {
            List recentMotionEvents = getRecentMotionEvents();
            if (recentMotionEvents.size() < 3) {
                recentMotionEvents.size();
                boolean z = BrightLineFalsingManager.DEBUG;
                distanceVectors = new DistanceVectors(this, 0.0f, 0.0f, 0.0f, 0.0f);
            } else {
                VelocityTracker obtain = VelocityTracker.obtain();
                Iterator it = recentMotionEvents.iterator();
                while (it.hasNext()) {
                    obtain.addMovement((MotionEvent) it.next());
                }
                obtain.computeCurrentVelocity(1);
                float xVelocity = obtain.getXVelocity();
                float yVelocity = obtain.getYVelocity();
                obtain.recycle();
                FalsingDataProvider falsingDataProvider = this.mDataProvider;
                falsingDataProvider.recalculateData();
                float x = falsingDataProvider.mLastMotionEvent.getX();
                falsingDataProvider.recalculateData();
                float x2 = x - falsingDataProvider.mFirstRecentMotionEvent.getX();
                falsingDataProvider.recalculateData();
                float y = falsingDataProvider.mLastMotionEvent.getY();
                falsingDataProvider.recalculateData();
                distanceVectors = new DistanceVectors(this, x2, y - falsingDataProvider.mFirstRecentMotionEvent.getY(), xVelocity, yVelocity);
            }
            this.mCachedDistance = distanceVectors;
            this.mDistanceDirty = false;
        }
        return this.mCachedDistance;
    }

    public final String getReason() {
        return String.format(null, "{distanceVectors=%s, isHorizontal=%s, velocityToDistanceMultiplier=%f, horizontalFlingThreshold=%f, verticalFlingThreshold=%f, horizontalSwipeThreshold=%f, verticalSwipeThreshold=%s}", getDistances(), Boolean.valueOf(this.mDataProvider.isHorizontal()), Float.valueOf(this.mVelocityToDistanceMultiplier), Float.valueOf(this.mHorizontalFlingThresholdPx), Float.valueOf(this.mVerticalFlingThresholdPx), Float.valueOf(this.mHorizontalSwipeThresholdPx), Float.valueOf(this.mVerticalSwipeThresholdPx));
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final void onTouchEvent(MotionEvent motionEvent) {
        this.mDistanceDirty = true;
    }
}
