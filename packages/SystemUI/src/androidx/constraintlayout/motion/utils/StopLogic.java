package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.core.motion.utils.SpringStopEngine;
import androidx.constraintlayout.core.motion.utils.StopEngine;
import androidx.constraintlayout.core.motion.utils.StopLogicEngine;
import androidx.constraintlayout.motion.widget.MotionInterpolator;

/* loaded from: classes.dex */
public class StopLogic extends MotionInterpolator {
    public StopEngine mEngine;
    public SpringStopEngine mSpringStopEngine;
    public final StopLogicEngine mStopLogicEngine;

    public StopLogic() {
        StopLogicEngine stopLogicEngine = new StopLogicEngine();
        this.mStopLogicEngine = stopLogicEngine;
        this.mEngine = stopLogicEngine;
    }

    public final void config(float f, float f2, float f3, float f4, float f5, float f6) {
        StopLogicEngine stopLogicEngine = this.mStopLogicEngine;
        this.mEngine = stopLogicEngine;
        stopLogicEngine.mStartPosition = f;
        boolean z = f > f2;
        stopLogicEngine.mBackwards = z;
        if (z) {
            stopLogicEngine.setup(-f3, f - f2, f5, f6, f4);
        } else {
            stopLogicEngine.setup(f3, f2 - f, f5, f6, f4);
        }
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return this.mEngine.getInterpolation(f);
    }

    @Override // androidx.constraintlayout.motion.widget.MotionInterpolator
    public final float getVelocity() {
        return this.mEngine.getVelocity();
    }

    public final void springConfig(int i, float f, float f2, float f3, float f4, float f5, float f6) {
        if (this.mSpringStopEngine == null) {
            this.mSpringStopEngine = new SpringStopEngine();
        }
        SpringStopEngine springStopEngine = this.mSpringStopEngine;
        this.mEngine = springStopEngine;
        springStopEngine.mTargetPos = f2;
        springStopEngine.mDamping = f5;
        springStopEngine.mPos = f;
        springStopEngine.mStiffness = f4;
        springStopEngine.mMass = f3;
        springStopEngine.mStopThreshold = f6;
        springStopEngine.mBoundaryMode = i;
        springStopEngine.mLastTime = 0.0f;
    }
}
