package com.android.systemui.classifier;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class DistanceClassifier extends FalsingClassifier {
    public DistanceVectors mCachedDistance;
    public boolean mDistanceDirty;
    public final float mHorizontalFlingThresholdPx;
    public final float mHorizontalSwipeThresholdPx;
    public final float mVelocityToDistanceMultiplier;
    public final float mVerticalFlingThresholdPx;
    public final float mVerticalSwipeThresholdPx;

    public class DistanceVectors {
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
        this.mVelocityToDistanceMultiplier = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_velcoity_to_distance", 30.0f);
        float f = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_horizontal_fling_threshold_in", 1.0f);
        float f2 = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_vertical_fling_threshold_in", 1.5f);
        float f3 = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_horizontal_swipe_threshold_in", 3.0f);
        float f4 = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_horizontal_swipe_threshold_in", 3.0f);
        float f5 = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_screen_fraction_max_distance", 0.8f);
        this.mHorizontalFlingThresholdPx = Math.min(r1.mWidthPixels * f5, f * this.mDataProvider.mXdpi);
        this.mVerticalFlingThresholdPx = Math.min(r7.mHeightPixels * f5, f2 * this.mDataProvider.mYdpi);
        this.mHorizontalSwipeThresholdPx = Math.min(r7.mWidthPixels * f5, f3 * this.mDataProvider.mXdpi);
        this.mVerticalSwipeThresholdPx = Math.min(r7.mHeightPixels * f5, f4 * this.mDataProvider.mYdpi);
        this.mDistanceDirty = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0047, code lost:
    
        if (java.lang.Math.abs(r1) >= r4.mHorizontalFlingThresholdPx) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0054, code lost:
    
        if (java.lang.Math.abs(r0) >= r4.mVerticalFlingThresholdPx) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005a, code lost:
    
        return com.android.systemui.classifier.FalsingClassifier.Result.passed(0.5d);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0063, code lost:
    
        return falsed(0.5d, getReason$1());
     */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        if (i == 10 || i == 18 || i == 11 || i == 12 || i == 13 || i == 15 || i == 17 || i == 19) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        DistanceVectors distances = getDistances();
        float f = distances.mDx;
        float f2 = distances.mVx;
        float f3 = this.mVelocityToDistanceMultiplier;
        float f4 = (f2 * f3) + f;
        float f5 = (distances.mVy * f3) + distances.mDy;
        if (this.mDataProvider.isHorizontal()) {
            boolean z = BrightLineFalsingManager.DEBUG;
        } else {
            boolean z2 = BrightLineFalsingManager.DEBUG;
        }
    }

    public final DistanceVectors getDistances() {
        DistanceClassifier distanceClassifier;
        DistanceVectors distanceVectors;
        if (this.mDistanceDirty) {
            FalsingDataProvider falsingDataProvider = this.mDataProvider;
            List recentMotionEvents = falsingDataProvider.getRecentMotionEvents();
            if (recentMotionEvents.size() < 3) {
                recentMotionEvents.size();
                boolean z = BrightLineFalsingManager.DEBUG;
                distanceVectors = new DistanceVectors(this, 0.0f, 0.0f, 0.0f, 0.0f);
                distanceClassifier = this;
            } else {
                VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
                Iterator it = recentMotionEvents.iterator();
                while (it.hasNext()) {
                    velocityTrackerObtain.addMovement((MotionEvent) it.next());
                }
                velocityTrackerObtain.computeCurrentVelocity(1);
                float xVelocity = velocityTrackerObtain.getXVelocity();
                float yVelocity = velocityTrackerObtain.getYVelocity();
                velocityTrackerObtain.recycle();
                falsingDataProvider.recalculateData();
                float x = falsingDataProvider.mLastMotionEvent.getX();
                falsingDataProvider.recalculateData();
                float x2 = x - falsingDataProvider.mFirstRecentMotionEvent.getX();
                falsingDataProvider.recalculateData();
                float y = falsingDataProvider.mLastMotionEvent.getY();
                falsingDataProvider.recalculateData();
                distanceClassifier = this;
                distanceVectors = new DistanceVectors(distanceClassifier, x2, y - falsingDataProvider.mFirstRecentMotionEvent.getY(), xVelocity, yVelocity);
            }
            distanceClassifier.mCachedDistance = distanceVectors;
            distanceClassifier.mDistanceDirty = false;
        } else {
            distanceClassifier = this;
        }
        return distanceClassifier.mCachedDistance;
    }

    public final String getReason$1() {
        return String.format(null, "{distanceVectors=%s, isHorizontal=%s, velocityToDistanceMultiplier=%f, horizontalFlingThreshold=%f, verticalFlingThreshold=%f, horizontalSwipeThreshold=%f, verticalSwipeThreshold=%s}", getDistances(), Boolean.valueOf(this.mDataProvider.isHorizontal()), Float.valueOf(this.mVelocityToDistanceMultiplier), Float.valueOf(this.mHorizontalFlingThresholdPx), Float.valueOf(this.mVerticalFlingThresholdPx), Float.valueOf(this.mHorizontalSwipeThresholdPx), Float.valueOf(this.mVerticalSwipeThresholdPx));
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final void onTouchEvent(MotionEvent motionEvent) {
        this.mDistanceDirty = true;
    }
}
