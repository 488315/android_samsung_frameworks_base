package com.android.systemui.classifier;

import android.provider.DeviceConfig;
import android.view.MotionEvent;
import com.android.systemui.classifier.DistanceClassifier;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.DeviceConfigProxy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        deviceConfigProxy.getClass();
        this.mPercentCoveredThreshold = DeviceConfig.getFloat("systemui", "brightline_falsing_proximity_percent_covered_threshold", 0.1f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003d, code lost:
    
        if (java.lang.Math.abs(r3.mDx) >= r6.mHorizontalSwipeThresholdPx) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0055, code lost:
    
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0053, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0051, code lost:
    
        if (java.lang.Math.abs(r3.mDy) >= r6.mVerticalSwipeThresholdPx) goto L21;
     */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        boolean z;
        if (i == 0 || i == 10 || i == 12 || i == 15 || i == 18) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        float f = this.mPercentNear;
        float f2 = this.mPercentCoveredThreshold;
        if (f <= f2) {
            return FalsingClassifier.Result.passed(0.5d);
        }
        DistanceClassifier distanceClassifier = this.mDistanceClassifier;
        DistanceClassifier.DistanceVectors distances = distanceClassifier.getDistances();
        if (distanceClassifier.mDataProvider.isHorizontal()) {
            Math.abs(distances.mDx);
            boolean z2 = BrightLineFalsingManager.DEBUG;
        } else {
            Math.abs(distances.mDy);
            boolean z3 = BrightLineFalsingManager.DEBUG;
        }
        FalsingClassifier.Result passed = z ? FalsingClassifier.Result.passed(0.5d) : distanceClassifier.falsed(0.5d, distanceClassifier.getReason());
        return passed.mFalsed ? falsed(0.5d, String.format(null, "{percentInProximity=%f, threshold=%f, distanceClassifier=%s}", Float.valueOf(this.mPercentNear), Float.valueOf(f2), passed.getReason())) : FalsingClassifier.Result.passed(0.5d);
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
    public final void onSessionStarted() {
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
