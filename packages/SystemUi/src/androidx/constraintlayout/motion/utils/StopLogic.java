package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.core.motion.utils.SpringStopEngine;
import androidx.constraintlayout.core.motion.utils.StopEngine;
import androidx.constraintlayout.core.motion.utils.StopLogicEngine;
import androidx.constraintlayout.motion.widget.MotionInterpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StopLogic extends MotionInterpolator {
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
}
