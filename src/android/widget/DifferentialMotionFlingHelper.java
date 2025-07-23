package android.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.flags.FeatureFlags;
import android.widget.flags.FeatureFlagsImpl;

/* loaded from: classes5.dex */
public class DifferentialMotionFlingHelper {
    private final Context mContext;
    private final int[] mFlingVelocityThresholds;
    private float mLastFlingVelocity;
    private int mLastProcessedAxis;
    private int mLastProcessedDeviceId;
    private int mLastProcessedSource;
    private final DifferentialMotionFlingTarget mTarget;
    private final DifferentialVelocityProvider mVelocityProvider;
    private final FlingVelocityThresholdCalculator mVelocityThresholdCalculator;
    private VelocityTracker mVelocityTracker;
    private final FeatureFlags mWidgetFeatureFlags;

    public interface DifferentialMotionFlingTarget {
        float getScaledScrollFactor();

        boolean startDifferentialMotionFling(float f);

        void stopDifferentialMotionFling();
    }

    public interface DifferentialVelocityProvider {
        float getCurrentVelocity(VelocityTracker velocityTracker, MotionEvent motionEvent, int i);
    }

    public interface FlingVelocityThresholdCalculator {
        void calculateFlingVelocityThresholds(Context context, int[] iArr, MotionEvent motionEvent, int i);
    }

    public DifferentialMotionFlingHelper(Context context, DifferentialMotionFlingTarget differentialMotionFlingTarget) {
        this(context, differentialMotionFlingTarget, new FlingVelocityThresholdCalculator() { // from class: android.widget.DifferentialMotionFlingHelper$$ExternalSyntheticLambda0
            @Override // android.widget.DifferentialMotionFlingHelper.FlingVelocityThresholdCalculator
            public final void calculateFlingVelocityThresholds(Context context2, int[] iArr, MotionEvent motionEvent, int i) {
                DifferentialMotionFlingHelper.calculateFlingVelocityThresholds(context2, iArr, motionEvent, i);
            }
        }, new DifferentialVelocityProvider() { // from class: android.widget.DifferentialMotionFlingHelper$$ExternalSyntheticLambda1
            @Override // android.widget.DifferentialMotionFlingHelper.DifferentialVelocityProvider
            public final float getCurrentVelocity(VelocityTracker velocityTracker, MotionEvent motionEvent, int i) {
                float currentVelocity;
                currentVelocity = DifferentialMotionFlingHelper.getCurrentVelocity(velocityTracker, motionEvent, i);
                return currentVelocity;
            }
        }, new FeatureFlagsImpl());
    }

    public DifferentialMotionFlingHelper(Context context, DifferentialMotionFlingTarget differentialMotionFlingTarget, FlingVelocityThresholdCalculator flingVelocityThresholdCalculator, DifferentialVelocityProvider differentialVelocityProvider, FeatureFlags featureFlags) {
        this.mLastProcessedAxis = -1;
        this.mLastProcessedSource = -1;
        this.mLastProcessedDeviceId = -1;
        this.mFlingVelocityThresholds = new int[]{Integer.MAX_VALUE, 0};
        this.mContext = context;
        this.mTarget = differentialMotionFlingTarget;
        this.mVelocityThresholdCalculator = flingVelocityThresholdCalculator;
        this.mVelocityProvider = differentialVelocityProvider;
        this.mWidgetFeatureFlags = featureFlags;
    }

    public void onMotionEvent(MotionEvent motionEvent, int i) {
        if (this.mWidgetFeatureFlags.enablePlatformWidgetDifferentialMotionFling()) {
            boolean calculateFlingVelocityThresholds = calculateFlingVelocityThresholds(motionEvent, i);
            if (this.mFlingVelocityThresholds[0] == Integer.MAX_VALUE) {
                recycleVelocityTracker();
                return;
            }
            float currentVelocity = getCurrentVelocity(motionEvent, i) * this.mTarget.getScaledScrollFactor();
            float signum = Math.signum(currentVelocity);
            if (calculateFlingVelocityThresholds || (signum != Math.signum(this.mLastFlingVelocity) && signum != 0.0f)) {
                this.mTarget.stopDifferentialMotionFling();
            }
            float abs = Math.abs(currentVelocity);
            int[] iArr = this.mFlingVelocityThresholds;
            if (abs < iArr[0]) {
                return;
            }
            float max = Math.max(-r6, Math.min(currentVelocity, iArr[1]));
            this.mLastFlingVelocity = this.mTarget.startDifferentialMotionFling(max) ? max : 0.0f;
        }
    }

    private boolean calculateFlingVelocityThresholds(MotionEvent motionEvent, int i) {
        int source = motionEvent.getSource();
        int deviceId = motionEvent.getDeviceId();
        if (this.mLastProcessedSource == source && this.mLastProcessedDeviceId == deviceId && this.mLastProcessedAxis == i) {
            return false;
        }
        this.mVelocityThresholdCalculator.calculateFlingVelocityThresholds(this.mContext, this.mFlingVelocityThresholds, motionEvent, i);
        this.mLastProcessedSource = source;
        this.mLastProcessedDeviceId = deviceId;
        this.mLastProcessedAxis = i;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void calculateFlingVelocityThresholds(Context context, int[] iArr, MotionEvent motionEvent, int i) {
        int source = motionEvent.getSource();
        int deviceId = motionEvent.getDeviceId();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        iArr[0] = viewConfiguration.getScaledMinimumFlingVelocity(deviceId, i, source);
        iArr[1] = viewConfiguration.getScaledMaximumFlingVelocity(deviceId, i, source);
    }

    private float getCurrentVelocity(MotionEvent motionEvent, int i) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        return this.mVelocityProvider.getCurrentVelocity(this.mVelocityTracker, motionEvent, i);
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float getCurrentVelocity(VelocityTracker velocityTracker, MotionEvent motionEvent, int i) {
        velocityTracker.addMovement(motionEvent);
        velocityTracker.computeCurrentVelocity(1000);
        return velocityTracker.getAxisVelocity(i);
    }
}
