package android.graphics.animation;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import com.android.internal.util.VirtualRefBasePtr;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class RenderNodeAnimator extends Animator {
    public static final int ALPHA = 11;
    public static final int LAST_VALUE = 11;
    public static final int PAINT_ALPHA = 1;
    public static final int PAINT_STROKE_WIDTH = 0;
    public static final int ROTATION = 5;
    public static final int ROTATION_X = 6;
    public static final int ROTATION_Y = 7;
    public static final int SCALE_X = 3;
    public static final int SCALE_Y = 4;
    private static final int STATE_DELAYED = 1;
    private static final int STATE_FINISHED = 3;
    private static final int STATE_PREPARE = 0;
    private static final int STATE_RUNNING = 2;
    public static final int TRANSLATION_X = 0;
    public static final int TRANSLATION_Y = 1;
    public static final int TRANSLATION_Z = 2;
    public static final int X = 8;
    public static final int Y = 9;
    public static final int Z = 10;
    private static ThreadLocal<DelayedAnimationHelper> sAnimationHelper = new ThreadLocal<>();
    private float mFinalValue;
    private Handler mHandler;
    private TimeInterpolator mInterpolator;
    private VirtualRefBasePtr mNativePtr;
    private int mRenderProperty;
    private long mStartDelay;
    private long mStartTime;
    private int mState;
    private RenderNode mTarget;
    private final boolean mUiThreadHandlesDelay;
    private long mUnscaledDuration;
    private long mUnscaledStartDelay;
    private ViewListener mViewListener;

    public interface ViewListener {
        void invalidateParent(boolean z);

        void onAlphaAnimationStart(float f);
    }

    private static native long nCreateAnimator(int i, float f);

    private static native long nCreateCanvasPropertyFloatAnimator(long j, float f);

    private static native long nCreateCanvasPropertyPaintAnimator(long j, int i, float f);

    private static native long nCreateRevealAnimator(int i, int i2, float f, float f2);

    private static native void nEnd(long j);

    private static native long nGetDuration(long j);

    private static native void nSetAllowRunningAsync(long j, boolean z);

    private static native void nSetDuration(long j, long j2);

    private static native void nSetInterpolator(long j, long j2);

    private static native void nSetListener(long j, RenderNodeAnimator renderNodeAnimator);

    private static native void nSetStartDelay(long j, long j2);

    private static native void nSetStartValue(long j, float f);

    private static native void nStart(long j);

    public RenderNodeAnimator(int i, float f) {
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        this.mRenderProperty = i;
        this.mFinalValue = f;
        this.mUiThreadHandlesDelay = true;
        init(nCreateAnimator(i, f));
    }

    public RenderNodeAnimator(CanvasProperty<Float> canvasProperty, float f) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(nCreateCanvasPropertyFloatAnimator(canvasProperty.getNativeContainer(), f));
        this.mUiThreadHandlesDelay = false;
    }

    public RenderNodeAnimator(CanvasProperty<Paint> canvasProperty, int i, float f) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(nCreateCanvasPropertyPaintAnimator(canvasProperty.getNativeContainer(), i, f));
        this.mUiThreadHandlesDelay = false;
    }

    public RenderNodeAnimator(int i, int i2, float f, float f2) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(nCreateRevealAnimator(i, i2, f, f2));
        this.mUiThreadHandlesDelay = true;
    }

    private void init(long j) {
        this.mNativePtr = new VirtualRefBasePtr(j);
    }

    private void checkMutable() {
        if (this.mState != 0) {
            throw new IllegalStateException("Animator has already started, cannot change it now!");
        }
        if (this.mNativePtr == null) {
            throw new IllegalStateException("Animator's target has been destroyed (trying to modify an animation after activity destroy?)");
        }
    }

    static boolean isNativeInterpolator(TimeInterpolator timeInterpolator) {
        return timeInterpolator.getClass().isAnnotationPresent(HasNativeInterpolator.class);
    }

    private void applyInterpolator() {
        long createNativeInterpolator;
        TimeInterpolator timeInterpolator = this.mInterpolator;
        if (timeInterpolator == null || this.mNativePtr == null) {
            return;
        }
        if (isNativeInterpolator(timeInterpolator)) {
            createNativeInterpolator = ((NativeInterpolator) this.mInterpolator).createNativeInterpolator();
        } else {
            createNativeInterpolator = FallbackLUTInterpolator.createNativeInterpolator(this.mInterpolator, nGetDuration(this.mNativePtr.get()));
        }
        nSetInterpolator(this.mNativePtr.get(), createNativeInterpolator);
    }

    @Override // android.animation.Animator
    public void start() {
        if (this.mTarget == null) {
            throw new IllegalStateException("Missing target!");
        }
        if (this.mState != 0) {
            throw new IllegalStateException("Already started!");
        }
        this.mState = 1;
        if (this.mHandler == null) {
            this.mHandler = new Handler(true);
        }
        applyInterpolator();
        VirtualRefBasePtr virtualRefBasePtr = this.mNativePtr;
        if (virtualRefBasePtr == null) {
            cancel();
        } else if (this.mStartDelay <= 0 || !this.mUiThreadHandlesDelay) {
            nSetStartDelay(virtualRefBasePtr.get(), this.mStartDelay);
            doStart();
        } else {
            getHelper().addDelayedAnimation(this);
        }
    }

    private void doStart() {
        ViewListener viewListener;
        if (this.mRenderProperty == 11 && (viewListener = this.mViewListener) != null) {
            viewListener.onAlphaAnimationStart(this.mFinalValue);
        }
        moveToRunningState();
        ViewListener viewListener2 = this.mViewListener;
        if (viewListener2 != null) {
            viewListener2.invalidateParent(false);
        }
    }

    private void moveToRunningState() {
        this.mState = 2;
        VirtualRefBasePtr virtualRefBasePtr = this.mNativePtr;
        if (virtualRefBasePtr != null) {
            nStart(virtualRefBasePtr.get());
        }
        notifyStartListeners();
    }

    private void notifyStartListeners() {
        ArrayList<Animator.AnimatorListener> cloneListeners = cloneListeners();
        int size = cloneListeners == null ? 0 : cloneListeners.size();
        for (int i = 0; i < size; i++) {
            cloneListeners.get(i).onAnimationStart(this);
        }
    }

    @Override // android.animation.Animator
    public void cancel() {
        int i = this.mState;
        if (i == 0 || i == 3) {
            return;
        }
        if (i == 1) {
            getHelper().removeDelayedAnimation(this);
            moveToRunningState();
        }
        ArrayList<Animator.AnimatorListener> cloneListeners = cloneListeners();
        int size = cloneListeners == null ? 0 : cloneListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            cloneListeners.get(i2).onAnimationCancel(this);
        }
        end();
    }

    @Override // android.animation.Animator
    public void end() {
        int i = this.mState;
        if (i != 3) {
            if (i < 2) {
                getHelper().removeDelayedAnimation(this);
                doStart();
            }
            VirtualRefBasePtr virtualRefBasePtr = this.mNativePtr;
            if (virtualRefBasePtr != null) {
                nEnd(virtualRefBasePtr.get());
                ViewListener viewListener = this.mViewListener;
                if (viewListener != null) {
                    viewListener.invalidateParent(false);
                    return;
                }
                return;
            }
            onFinished();
        }
    }

    @Override // android.animation.Animator
    public void pause() {
        throw new UnsupportedOperationException();
    }

    @Override // android.animation.Animator
    public void resume() {
        throw new UnsupportedOperationException();
    }

    public void setViewListener(ViewListener viewListener) {
        this.mViewListener = viewListener;
    }

    public final void setTarget(RecordingCanvas recordingCanvas) {
        setTarget(recordingCanvas.mNode);
    }

    protected void setTarget(RenderNode renderNode) {
        checkMutable();
        if (this.mTarget != null) {
            throw new IllegalStateException("Target already set!");
        }
        nSetListener(this.mNativePtr.get(), this);
        this.mTarget = renderNode;
        renderNode.addAnimator(this);
    }

    public void setStartValue(float f) {
        checkMutable();
        nSetStartValue(this.mNativePtr.get(), f);
    }

    @Override // android.animation.Animator
    public void setStartDelay(long j) {
        checkMutable();
        if (j < 0) {
            throw new IllegalArgumentException("startDelay must be positive; " + j);
        }
        this.mUnscaledStartDelay = j;
        this.mStartDelay = (long) (ValueAnimator.getDurationScale() * j);
    }

    @Override // android.animation.Animator
    public long getStartDelay() {
        return this.mUnscaledStartDelay;
    }

    @Override // android.animation.Animator
    public RenderNodeAnimator setDuration(long j) {
        checkMutable();
        if (j < 0) {
            throw new IllegalArgumentException("duration must be positive; " + j);
        }
        this.mUnscaledDuration = j;
        nSetDuration(this.mNativePtr.get(), (long) (j * ValueAnimator.getDurationScale()));
        return this;
    }

    @Override // android.animation.Animator
    public long getDuration() {
        return this.mUnscaledDuration;
    }

    @Override // android.animation.Animator
    public long getTotalDuration() {
        return this.mUnscaledDuration + this.mUnscaledStartDelay;
    }

    @Override // android.animation.Animator
    public boolean isRunning() {
        int i = this.mState;
        return i == 1 || i == 2;
    }

    @Override // android.animation.Animator
    public boolean isStarted() {
        return this.mState != 0;
    }

    @Override // android.animation.Animator
    public void setInterpolator(TimeInterpolator timeInterpolator) {
        checkMutable();
        this.mInterpolator = timeInterpolator;
    }

    @Override // android.animation.Animator
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    protected void onFinished() {
        int i = this.mState;
        if (i == 0) {
            releaseNativePtr();
            return;
        }
        if (i == 1) {
            getHelper().removeDelayedAnimation(this);
            notifyStartListeners();
        }
        this.mState = 3;
        ArrayList<Animator.AnimatorListener> cloneListeners = cloneListeners();
        int size = cloneListeners == null ? 0 : cloneListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            cloneListeners.get(i2).onAnimationEnd(this);
        }
        releaseNativePtr();
    }

    private void releaseNativePtr() {
        VirtualRefBasePtr virtualRefBasePtr = this.mNativePtr;
        if (virtualRefBasePtr != null) {
            virtualRefBasePtr.release();
            this.mNativePtr = null;
        }
    }

    private ArrayList<Animator.AnimatorListener> cloneListeners() {
        ArrayList<Animator.AnimatorListener> listeners = getListeners();
        return listeners != null ? (ArrayList) listeners.clone() : listeners;
    }

    public long getNativeAnimator() {
        return this.mNativePtr.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean processDelayed(long j) {
        long j2 = this.mStartTime;
        if (j2 == 0) {
            this.mStartTime = j;
            return false;
        }
        if (j - j2 < this.mStartDelay) {
            return false;
        }
        doStart();
        return true;
    }

    private static DelayedAnimationHelper getHelper() {
        DelayedAnimationHelper delayedAnimationHelper = sAnimationHelper.get();
        if (delayedAnimationHelper != null) {
            return delayedAnimationHelper;
        }
        DelayedAnimationHelper delayedAnimationHelper2 = new DelayedAnimationHelper();
        sAnimationHelper.set(delayedAnimationHelper2);
        return delayedAnimationHelper2;
    }

    private static class DelayedAnimationHelper implements Runnable {
        private boolean mCallbackScheduled;
        private ArrayList<RenderNodeAnimator> mDelayedAnims = new ArrayList<>();
        private final Choreographer mChoreographer = Choreographer.getInstance();

        DelayedAnimationHelper() {
        }

        public void addDelayedAnimation(RenderNodeAnimator renderNodeAnimator) {
            this.mDelayedAnims.add(renderNodeAnimator);
            scheduleCallback();
        }

        public void removeDelayedAnimation(RenderNodeAnimator renderNodeAnimator) {
            this.mDelayedAnims.remove(renderNodeAnimator);
        }

        private void scheduleCallback() {
            if (this.mCallbackScheduled) {
                return;
            }
            this.mCallbackScheduled = true;
            this.mChoreographer.postCallback(1, this, null);
        }

        @Override // java.lang.Runnable
        public void run() {
            long frameTime = this.mChoreographer.getFrameTime();
            this.mCallbackScheduled = false;
            int i = 0;
            for (int i2 = 0; i2 < this.mDelayedAnims.size(); i2++) {
                RenderNodeAnimator renderNodeAnimator = this.mDelayedAnims.get(i2);
                if (!renderNodeAnimator.processDelayed(frameTime)) {
                    if (i != i2) {
                        this.mDelayedAnims.set(i, renderNodeAnimator);
                    }
                    i++;
                }
            }
            while (this.mDelayedAnims.size() > i) {
                this.mDelayedAnims.remove(r0.size() - 1);
            }
            if (this.mDelayedAnims.size() > 0) {
                scheduleCallback();
            }
        }
    }

    private static void callOnFinished(final RenderNodeAnimator renderNodeAnimator) {
        Handler handler = renderNodeAnimator.mHandler;
        if (handler != null) {
            Objects.requireNonNull(renderNodeAnimator);
            handler.post(new Runnable() { // from class: android.graphics.animation.RenderNodeAnimator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RenderNodeAnimator.this.onFinished();
                }
            });
        } else {
            Handler handler2 = new Handler(Looper.getMainLooper(), null, true);
            Objects.requireNonNull(renderNodeAnimator);
            handler2.post(new Runnable() { // from class: android.graphics.animation.RenderNodeAnimator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RenderNodeAnimator.this.onFinished();
                }
            });
        }
    }

    @Override // android.animation.Animator
    /* renamed from: clone */
    public Animator mo76clone() {
        throw new IllegalStateException("Cannot clone this animator");
    }

    @Override // android.animation.Animator
    public void setAllowRunningAsynchronously(boolean z) {
        checkMutable();
        nSetAllowRunningAsync(this.mNativePtr.get(), z);
    }
}
