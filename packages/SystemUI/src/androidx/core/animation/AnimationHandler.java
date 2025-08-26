package androidx.core.animation;

import android.view.Choreographer;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AnimationHandler {
    public static final ThreadLocal sAnimationHandler = new ThreadLocal();
    public final ArrayList mAnimationCallbacks = new ArrayList();
    public boolean mListDirty = false;
    public final AnimationFrameCallbackProvider mProvider;

    public interface AnimationFrameCallback {
        boolean doAnimationFrame(long j);
    }

    public interface AnimationFrameCallbackProvider {
    }

    public class FrameCallbackProvider16 implements AnimationFrameCallbackProvider, Choreographer.FrameCallback {
        public FrameCallbackProvider16() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            AnimationHandler animationHandler = AnimationHandler.this;
            long j2 = j / 1000000;
            for (int i = 0; i < animationHandler.mAnimationCallbacks.size(); i++) {
                AnimationFrameCallback animationFrameCallback = (AnimationFrameCallback) animationHandler.mAnimationCallbacks.get(i);
                if (animationFrameCallback != null) {
                    animationFrameCallback.doAnimationFrame(j2);
                }
            }
            if (animationHandler.mListDirty) {
                for (int size = animationHandler.mAnimationCallbacks.size() - 1; size >= 0; size--) {
                    if (animationHandler.mAnimationCallbacks.get(size) == null) {
                        animationHandler.mAnimationCallbacks.remove(size);
                    }
                }
                animationHandler.mListDirty = false;
            }
            if (animationHandler.mAnimationCallbacks.size() > 0) {
                FrameCallbackProvider16 frameCallbackProvider16 = (FrameCallbackProvider16) animationHandler.mProvider;
                frameCallbackProvider16.getClass();
                Choreographer.getInstance().postFrameCallback(frameCallbackProvider16);
            }
        }
    }

    public AnimationHandler(AnimationFrameCallbackProvider animationFrameCallbackProvider) {
        if (animationFrameCallbackProvider == null) {
            this.mProvider = new FrameCallbackProvider16();
        } else {
            this.mProvider = animationFrameCallbackProvider;
        }
    }

    public static AnimationHandler getInstance() {
        ThreadLocal threadLocal = sAnimationHandler;
        if (threadLocal.get() == null) {
            threadLocal.set(new AnimationHandler(null));
        }
        return (AnimationHandler) threadLocal.get();
    }
}
