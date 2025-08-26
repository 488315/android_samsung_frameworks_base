package androidx.core.view;

import android.content.Context;
import android.view.VelocityTracker;

/* loaded from: classes.dex */
public class DifferentialMotionFlingController {
    public final Context mContext;
    public final int[] mFlingVelocityThresholds;
    public float mLastFlingVelocity;
    public int mLastProcessedAxis;
    public int mLastProcessedDeviceId;
    public int mLastProcessedSource;
    public final DifferentialMotionFlingTarget mTarget;
    public final DifferentialVelocityProvider mVelocityProvider;
    public final FlingVelocityThresholdCalculator mVelocityThresholdCalculator;
    public VelocityTracker mVelocityTracker;

    public interface DifferentialVelocityProvider {
    }

    public interface FlingVelocityThresholdCalculator {
    }

    public DifferentialMotionFlingController(Context context, DifferentialMotionFlingTarget differentialMotionFlingTarget) {
        this(context, differentialMotionFlingTarget, new DifferentialMotionFlingController$$ExternalSyntheticLambda0(), new DifferentialMotionFlingController$$ExternalSyntheticLambda0());
    }

    public DifferentialMotionFlingController(Context context, DifferentialMotionFlingTarget differentialMotionFlingTarget, FlingVelocityThresholdCalculator flingVelocityThresholdCalculator, DifferentialVelocityProvider differentialVelocityProvider) {
        this.mLastProcessedAxis = -1;
        this.mLastProcessedSource = -1;
        this.mLastProcessedDeviceId = -1;
        this.mFlingVelocityThresholds = new int[]{Integer.MAX_VALUE, 0};
        this.mContext = context;
        this.mTarget = differentialMotionFlingTarget;
        this.mVelocityThresholdCalculator = flingVelocityThresholdCalculator;
        this.mVelocityProvider = differentialVelocityProvider;
    }
}
