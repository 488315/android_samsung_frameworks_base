package com.android.systemui.classifier;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class DistanceClassifier extends FalsingClassifier {
    public DistanceVectors mCachedDistance;
    public boolean mDistanceDirty;
    public final float mHorizontalFlingThresholdPx;
    public final float mHorizontalSwipeThresholdPx;
    public final float mVelocityToDistanceMultiplier;
    public final float mVerticalFlingThresholdPx;
    public final float mVerticalSwipeThresholdPx;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0047, code lost:
    
        if (java.lang.Math.abs(r1) >= r4.mHorizontalFlingThresholdPx) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:
    
        return falsed(0.5d, getReason$1());
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:?, code lost:
    
        return com.android.systemui.classifier.FalsingClassifier.Result.passed(0.5d);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0054, code lost:
    
        if (java.lang.Math.abs(r0) >= r4.mVerticalFlingThresholdPx) goto L26;
     */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.classifier.FalsingClassifier.Result calculateFalsingResult(int r5) {
        /*
            r4 = this;
            r0 = 10
            if (r5 == r0) goto L64
            r0 = 18
            if (r5 == r0) goto L64
            r0 = 11
            if (r5 == r0) goto L64
            r0 = 12
            if (r5 == r0) goto L64
            r0 = 13
            if (r5 == r0) goto L64
            r0 = 15
            if (r5 == r0) goto L64
            r0 = 17
            if (r5 == r0) goto L64
            r0 = 19
            if (r5 != r0) goto L21
            goto L64
        L21:
            com.android.systemui.classifier.DistanceClassifier$DistanceVectors r5 = r4.getDistances()
            float r0 = r5.mDx
            float r1 = r5.mVx
            float r2 = r4.mVelocityToDistanceMultiplier
            float r1 = r1 * r2
            float r1 = r1 + r0
            float r0 = r5.mVy
            float r0 = r0 * r2
            float r5 = r5.mDy
            float r0 = r0 + r5
            com.android.systemui.classifier.FalsingDataProvider r5 = r4.mDataProvider
            boolean r5 = r5.isHorizontal()
            r2 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            if (r5 == 0) goto L4a
            boolean r5 = com.android.systemui.classifier.BrightLineFalsingManager.DEBUG
            float r5 = java.lang.Math.abs(r1)
            float r0 = r4.mHorizontalFlingThresholdPx
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 < 0) goto L5b
            goto L56
        L4a:
            boolean r5 = com.android.systemui.classifier.BrightLineFalsingManager.DEBUG
            float r5 = java.lang.Math.abs(r0)
            float r0 = r4.mVerticalFlingThresholdPx
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 < 0) goto L5b
        L56:
            com.android.systemui.classifier.FalsingClassifier$Result r4 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r2)
            goto L63
        L5b:
            java.lang.String r5 = r4.getReason$1()
            com.android.systemui.classifier.FalsingClassifier$Result r4 = r4.falsed(r2, r5)
        L63:
            return r4
        L64:
            r4 = 0
            com.android.systemui.classifier.FalsingClassifier$Result r4 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.classifier.DistanceClassifier.calculateFalsingResult(int):com.android.systemui.classifier.FalsingClassifier$Result");
    }

    public final DistanceVectors getDistances() {
        DistanceVectors distanceVectors;
        if (this.mDistanceDirty) {
            FalsingDataProvider falsingDataProvider = this.mDataProvider;
            List recentMotionEvents = falsingDataProvider.getRecentMotionEvents();
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

    public final String getReason$1() {
        return String.format(null, "{distanceVectors=%s, isHorizontal=%s, velocityToDistanceMultiplier=%f, horizontalFlingThreshold=%f, verticalFlingThreshold=%f, horizontalSwipeThreshold=%f, verticalSwipeThreshold=%s}", getDistances(), Boolean.valueOf(this.mDataProvider.isHorizontal()), Float.valueOf(this.mVelocityToDistanceMultiplier), Float.valueOf(this.mHorizontalFlingThresholdPx), Float.valueOf(this.mVerticalFlingThresholdPx), Float.valueOf(this.mHorizontalSwipeThresholdPx), Float.valueOf(this.mVerticalSwipeThresholdPx));
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final void onTouchEvent(MotionEvent motionEvent) {
        this.mDistanceDirty = true;
    }
}
