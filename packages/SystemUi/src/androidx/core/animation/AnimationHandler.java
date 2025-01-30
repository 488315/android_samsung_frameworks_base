package androidx.core.animation;

import android.view.Choreographer;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AnimationHandler {
    public static final ThreadLocal sAnimationHandler = new ThreadLocal();
    public final ArrayList mAnimationCallbacks = new ArrayList();
    public boolean mListDirty = false;
    public final AnimationFrameCallbackProvider mProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface AnimationFrameCallback {
        boolean doAnimationFrame(long j);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface AnimationFrameCallbackProvider {
        void onNewCallbackAdded();

        void postFrameCallback();
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

    public final void onAnimationFrame(long j) {
        ArrayList arrayList;
        int i = 0;
        while (true) {
            arrayList = this.mAnimationCallbacks;
            if (i >= arrayList.size()) {
                break;
            }
            AnimationFrameCallback animationFrameCallback = (AnimationFrameCallback) arrayList.get(i);
            if (animationFrameCallback != null) {
                animationFrameCallback.doAnimationFrame(j);
            }
            i++;
        }
        if (this.mListDirty) {
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else if (arrayList.get(size) == null) {
                    arrayList.remove(size);
                }
            }
            this.mListDirty = false;
        }
        if (arrayList.size() > 0) {
            this.mProvider.postFrameCallback();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FrameCallbackProvider16 implements AnimationFrameCallbackProvider, Choreographer.FrameCallback {
        public FrameCallbackProvider16() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            AnimationHandler.this.onAnimationFrame(j / 1000000);
        }

        @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallbackProvider
        public final void postFrameCallback() {
            Choreographer.getInstance().postFrameCallback(this);
        }

        @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallbackProvider
        public final void onNewCallbackAdded() {
        }
    }
}
