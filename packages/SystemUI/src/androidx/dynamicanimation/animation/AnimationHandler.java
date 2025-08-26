package androidx.dynamicanimation.animation;

import android.os.Looper;
import android.view.Choreographer;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;

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

    public class AnimationCallbackDispatcher {
        private AnimationCallbackDispatcher() {
        }
    }

    public interface AnimationFrameCallback {
    }

    public class DurationScaleChangeListener33 {
        public AnimationHandler$DurationScaleChangeListener33$$ExternalSyntheticLambda0 mListener;

        public DurationScaleChangeListener33() {
        }
    }

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
                    animationHandler$$ExternalSyntheticLambda0.run();
                }
            });
        }
    }

    public AnimationHandler(FrameCallbackScheduler frameCallbackScheduler) {
        this.mScheduler = frameCallbackScheduler;
    }
}
