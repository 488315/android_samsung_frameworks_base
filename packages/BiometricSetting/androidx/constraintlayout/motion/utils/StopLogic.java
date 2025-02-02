package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.core.motion.utils.SpringStopEngine;
import androidx.constraintlayout.core.motion.utils.StopEngine;
import androidx.constraintlayout.core.motion.utils.StopLogicEngine;
import androidx.constraintlayout.motion.widget.MotionInterpolator;

/* loaded from: classes.dex */
public final class StopLogic extends MotionInterpolator {
    private StopEngine mEngine;
    private SpringStopEngine mSpringStopEngine;
    private StopLogicEngine mStopLogicEngine;

    public StopLogic() {
        StopLogicEngine stopLogicEngine = new StopLogicEngine();
        this.mStopLogicEngine = stopLogicEngine;
        this.mEngine = stopLogicEngine;
    }

    public final void config(float f, float f2, float f3, float f4, float f5, float f6) {
        StopLogicEngine stopLogicEngine = this.mStopLogicEngine;
        this.mEngine = stopLogicEngine;
        stopLogicEngine.config(f, f2, f3, f4, f5, f6);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        return this.mEngine.getInterpolation(f);
    }

    @Override // androidx.constraintlayout.motion.widget.MotionInterpolator
    public final float getVelocity() {
        return this.mEngine.getVelocity();
    }

    public final boolean isStopped() {
        return this.mEngine.isStopped();
    }

    public final void springConfig(float f, float f2, float f3, float f4, float f5, float f6, float f7, int i) {
        if (this.mSpringStopEngine == null) {
            this.mSpringStopEngine = new SpringStopEngine();
        }
        SpringStopEngine springStopEngine = this.mSpringStopEngine;
        this.mEngine = springStopEngine;
        springStopEngine.springConfig(f, f2, f4, f5, f6, f7, i);
    }
}
