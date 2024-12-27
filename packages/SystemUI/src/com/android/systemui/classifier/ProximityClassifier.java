package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.DeviceConfigProxy;

public final class ProximityClassifier extends FalsingClassifier {
    public final DistanceClassifier mDistanceClassifier;
    public long mGestureStartTimeNs;
    public boolean mNear;
    public long mNearDurationNs;
    public final float mPercentCoveredThreshold;
    public float mPercentNear;
    public long mPrevNearTimeNs;

    public ProximityClassifier(DistanceClassifier distanceClassifier, FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        super(falsingDataProvider);
        this.mDistanceClassifier = distanceClassifier;
        this.mPercentCoveredThreshold = deviceConfigProxy.getFloat("systemui", "brightline_falsing_proximity_percent_covered_threshold", 0.1f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003d, code lost:
    
        if (java.lang.Math.abs(r3.mDx) >= r6.mHorizontalSwipeThresholdPx) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0058, code lost:
    
        r6 = r6.falsed(0.5d, r6.getReason$1());
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0053, code lost:
    
        r6 = com.android.systemui.classifier.FalsingClassifier.Result.passed(0.5d);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0051, code lost:
    
        if (java.lang.Math.abs(r3.mDy) >= r6.mVerticalSwipeThresholdPx) goto L21;
     */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.classifier.FalsingClassifier.Result calculateFalsingResult(int r6) {
        /*
            r5 = this;
            if (r6 == 0) goto L8d
            r0 = 10
            if (r6 == r0) goto L8d
            r0 = 12
            if (r6 == r0) goto L8d
            r0 = 15
            if (r6 == r0) goto L8d
            r0 = 18
            if (r6 != r0) goto L14
            goto L8d
        L14:
            float r6 = r5.mPercentNear
            float r0 = r5.mPercentCoveredThreshold
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            r1 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            if (r6 <= 0) goto L88
            com.android.systemui.classifier.DistanceClassifier r6 = r5.mDistanceClassifier
            com.android.systemui.classifier.DistanceClassifier$DistanceVectors r3 = r6.getDistances()
            com.android.systemui.classifier.FalsingDataProvider r4 = r6.mDataProvider
            boolean r4 = r4.isHorizontal()
            if (r4 == 0) goto L40
            float r4 = r3.mDx
            java.lang.Math.abs(r4)
            boolean r4 = com.android.systemui.classifier.BrightLineFalsingManager.DEBUG
            float r3 = r3.mDx
            float r3 = java.lang.Math.abs(r3)
            float r4 = r6.mHorizontalSwipeThresholdPx
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 < 0) goto L58
            goto L53
        L40:
            float r4 = r3.mDy
            java.lang.Math.abs(r4)
            boolean r4 = com.android.systemui.classifier.BrightLineFalsingManager.DEBUG
            float r3 = r3.mDy
            float r3 = java.lang.Math.abs(r3)
            float r4 = r6.mVerticalSwipeThresholdPx
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 < 0) goto L58
        L53:
            com.android.systemui.classifier.FalsingClassifier$Result r6 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r1)
            goto L60
        L58:
            java.lang.String r3 = r6.getReason$1()
            com.android.systemui.classifier.FalsingClassifier$Result r6 = r6.falsed(r1, r3)
        L60:
            boolean r3 = r6.mFalsed
            if (r3 == 0) goto L83
            float r3 = r5.mPercentNear
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            java.lang.String r6 = r6.getReason()
            java.lang.Object[] r6 = new java.lang.Object[]{r3, r0, r6}
            r0 = 0
            java.lang.String r3 = "{percentInProximity=%f, threshold=%f, distanceClassifier=%s}"
            java.lang.String r6 = java.lang.String.format(r0, r3, r6)
            com.android.systemui.classifier.FalsingClassifier$Result r5 = r5.falsed(r1, r6)
            goto L87
        L83:
            com.android.systemui.classifier.FalsingClassifier$Result r5 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r1)
        L87:
            return r5
        L88:
            com.android.systemui.classifier.FalsingClassifier$Result r5 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r1)
            return r5
        L8d:
            r5 = 0
            com.android.systemui.classifier.FalsingClassifier$Result r5 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.classifier.ProximityClassifier.calculateFalsingResult(int):com.android.systemui.classifier.FalsingClassifier$Result");
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
        boolean covered = proximityEvent.getCovered();
        long timestampNs = proximityEvent.getTimestampNs();
        boolean z = BrightLineFalsingManager.DEBUG;
        update(timestampNs, covered);
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final void onSessionEnded() {
        this.mPrevNearTimeNs = 0L;
        this.mPercentNear = 0.0f;
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final void onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mGestureStartTimeNs = motionEvent.getEventTimeNanos();
            if (this.mPrevNearTimeNs > 0) {
                this.mPrevNearTimeNs = motionEvent.getEventTimeNanos();
            }
            boolean z = BrightLineFalsingManager.DEBUG;
            this.mNearDurationNs = 0L;
        }
        if (actionMasked == 1 || actionMasked == 3) {
            update(motionEvent.getEventTimeNanos(), this.mNear);
            long eventTimeNanos = motionEvent.getEventTimeNanos() - this.mGestureStartTimeNs;
            long j = this.mNearDurationNs;
            boolean z2 = BrightLineFalsingManager.DEBUG;
            if (eventTimeNanos == 0) {
                this.mPercentNear = this.mNear ? 1.0f : 0.0f;
            } else {
                this.mPercentNear = j / eventTimeNanos;
            }
        }
    }

    public final void update(long j, boolean z) {
        long j2 = this.mPrevNearTimeNs;
        if (j2 != 0 && j > j2 && this.mNear) {
            this.mNearDurationNs = (j - j2) + this.mNearDurationNs;
            boolean z2 = BrightLineFalsingManager.DEBUG;
        }
        if (z) {
            boolean z3 = BrightLineFalsingManager.DEBUG;
            this.mPrevNearTimeNs = j;
        }
        this.mNear = z;
    }
}
