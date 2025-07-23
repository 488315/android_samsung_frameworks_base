package androidx.dynamicanimation.animation;

import android.os.Looper;
import android.view.Choreographer;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AnimationHandler {
    public static final ThreadLocal sAnimatorHandler = new ThreadLocal();
    public DurationScaleChangeListener33 mDurationScaleChangeListener;
    public final FrameCallbackScheduler mScheduler;
    public final SimpleArrayMap mDelayedCallbackStartTime = new SimpleArrayMap();
    public final ArrayList mAnimationCallbacks = new ArrayList();
    public final AnimationCallbackDispatcher mCallbackDispatcher = new AnimationCallbackDispatcher();
    public final AnimationHandler$$ExternalSyntheticLambda0 mRunnable = new AnimationHandler$$ExternalSyntheticLambda0(this);
    public boolean mListDirty = false;
    public float mDurationScale = 1.0f;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AnimationCallbackDispatcher {
        private AnimationCallbackDispatcher() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface AnimationFrameCallback {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DurationScaleChangeListener33 {
        public AnimationHandler$DurationScaleChangeListener33$$ExternalSyntheticLambda0 mListener;

        public DurationScaleChangeListener33() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FrameCallbackScheduler16 implements FrameCallbackScheduler {
        public final Choreographer mChoreographer = Choreographer.getInstance();
        public final Looper mLooper = Looper.myLooper();

        @Override // androidx.dynamicanimation.animation.FrameCallbackScheduler
        public final boolean isCurrentThread() {
            return Thread.currentThread() == this.mLooper.getThread();
        }

        @Override // androidx.dynamicanimation.animation.FrameCallbackScheduler
        public final void postFrameCallback(final AnimationHandler$$ExternalSyntheticLambda0 animationHandler$$ExternalSyntheticLambda0) {
            this.mChoreographer.postFrameCallback(new Choreographer.FrameCallback() { // from class: androidx.dynamicanimation.animation.AnimationHandler$FrameCallbackScheduler16$$ExternalSyntheticLambda0
                @Override // android.view.Choreographer.FrameCallback
                public final void doFrame(long j) {
                    AnimationHandler$$ExternalSyntheticLambda0.this.run();
                }
            });
        }
    }

    public AnimationHandler(FrameCallbackScheduler frameCallbackScheduler) {
        this.mScheduler = frameCallbackScheduler;
    }
}
