package androidx.core.view;

import android.content.Context;
import android.view.VelocityTracker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DifferentialVelocityProvider {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
