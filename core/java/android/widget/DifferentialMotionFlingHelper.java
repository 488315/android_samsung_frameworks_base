package android.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.flags.FeatureFlags;
import android.widget.flags.FeatureFlagsImpl;

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
        void calculateFlingVelocityThresholds(
                Context context, int[] iArr, MotionEvent motionEvent, int i);
    }

    public DifferentialMotionFlingHelper(Context context, DifferentialMotionFlingTarget target) {
        this(
                context,
                target,
                new FlingVelocityThresholdCalculator() { // from class:
                                                         // android.widget.DifferentialMotionFlingHelper$$ExternalSyntheticLambda0
                    @Override // android.widget.DifferentialMotionFlingHelper.FlingVelocityThresholdCalculator
                    public final void calculateFlingVelocityThresholds(
                            Context context2, int[] iArr, MotionEvent motionEvent, int i) {
                        DifferentialMotionFlingHelper.calculateFlingVelocityThresholds(
                                context2, iArr, motionEvent, i);
                    }
                },
                new DifferentialVelocityProvider() { // from class:
                                                     // android.widget.DifferentialMotionFlingHelper$$ExternalSyntheticLambda1
                    @Override // android.widget.DifferentialMotionFlingHelper.DifferentialVelocityProvider
                    public final float getCurrentVelocity(
                            VelocityTracker velocityTracker, MotionEvent motionEvent, int i) {
                        float currentVelocity;
                        currentVelocity =
                                DifferentialMotionFlingHelper.getCurrentVelocity(
                                        velocityTracker, motionEvent, i);
                        return currentVelocity;
                    }
                },
                new FeatureFlagsImpl());
    }

    public DifferentialMotionFlingHelper(
            Context context,
            DifferentialMotionFlingTarget target,
            FlingVelocityThresholdCalculator velocityThresholdCalculator,
            DifferentialVelocityProvider velocityProvider,
            FeatureFlags widgetFeatureFlags) {
        this.mLastProcessedAxis = -1;
        this.mLastProcessedSource = -1;
        this.mLastProcessedDeviceId = -1;
        this.mFlingVelocityThresholds = new int[] {Integer.MAX_VALUE, 0};
        this.mContext = context;
        this.mTarget = target;
        this.mVelocityThresholdCalculator = velocityThresholdCalculator;
        this.mVelocityProvider = velocityProvider;
        this.mWidgetFeatureFlags = widgetFeatureFlags;
    }

    public void onMotionEvent(MotionEvent event, int axis) {
        if (!this.mWidgetFeatureFlags.enablePlatformWidgetDifferentialMotionFling()) {
            return;
        }
        boolean flingParamsChanged = calculateFlingVelocityThresholds(event, axis);
        if (this.mFlingVelocityThresholds[0] == Integer.MAX_VALUE) {
            recycleVelocityTracker();
            return;
        }
        float scaledVelocity =
                getCurrentVelocity(event, axis) * this.mTarget.getScaledScrollFactor();
        float velocityDirection = Math.signum(scaledVelocity);
        if (flingParamsChanged
                || (velocityDirection != Math.signum(this.mLastFlingVelocity)
                        && velocityDirection != 0.0f)) {
            this.mTarget.stopDifferentialMotionFling();
        }
        if (Math.abs(scaledVelocity) < this.mFlingVelocityThresholds[0]) {
            return;
        }
        float scaledVelocity2 =
                Math.max(
                        -this.mFlingVelocityThresholds[1],
                        Math.min(scaledVelocity, this.mFlingVelocityThresholds[1]));
        boolean flung = this.mTarget.startDifferentialMotionFling(scaledVelocity2);
        this.mLastFlingVelocity = flung ? scaledVelocity2 : 0.0f;
    }

    private boolean calculateFlingVelocityThresholds(MotionEvent event, int axis) {
        int source = event.getSource();
        int deviceId = event.getDeviceId();
        if (this.mLastProcessedSource != source
                || this.mLastProcessedDeviceId != deviceId
                || this.mLastProcessedAxis != axis) {
            this.mVelocityThresholdCalculator.calculateFlingVelocityThresholds(
                    this.mContext, this.mFlingVelocityThresholds, event, axis);
            this.mLastProcessedSource = source;
            this.mLastProcessedDeviceId = deviceId;
            this.mLastProcessedAxis = axis;
            return true;
        }
        return false;
    }

    public static void calculateFlingVelocityThresholds(
            Context context, int[] buffer, MotionEvent event, int axis) {
        int source = event.getSource();
        int deviceId = event.getDeviceId();
        ViewConfiguration vc = ViewConfiguration.get(context);
        buffer[0] = vc.getScaledMinimumFlingVelocity(deviceId, axis, source);
        buffer[1] = vc.getScaledMaximumFlingVelocity(deviceId, axis, source);
    }

    private float getCurrentVelocity(MotionEvent event, int axis) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        return this.mVelocityProvider.getCurrentVelocity(this.mVelocityTracker, event, axis);
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public static float getCurrentVelocity(VelocityTracker vt, MotionEvent event, int axis) {
        vt.addMovement(event);
        vt.computeCurrentVelocity(1000);
        return vt.getAxisVelocity(axis);
    }
}
