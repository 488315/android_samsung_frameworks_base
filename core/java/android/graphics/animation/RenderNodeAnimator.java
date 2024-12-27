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

    public RenderNodeAnimator(int property, float finalValue) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        this.mRenderProperty = property;
        this.mFinalValue = finalValue;
        this.mUiThreadHandlesDelay = true;
        init(nCreateAnimator(property, finalValue));
    }

    public RenderNodeAnimator(CanvasProperty<Float> property, float finalValue) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(nCreateCanvasPropertyFloatAnimator(property.getNativeContainer(), finalValue));
        this.mUiThreadHandlesDelay = false;
    }

    public RenderNodeAnimator(CanvasProperty<Paint> property, int paintField, float finalValue) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(
                nCreateCanvasPropertyPaintAnimator(
                        property.getNativeContainer(), paintField, finalValue));
        this.mUiThreadHandlesDelay = false;
    }

    public RenderNodeAnimator(int x, int y, float startRadius, float endRadius) {
        this.mRenderProperty = -1;
        this.mState = 0;
        this.mUnscaledDuration = 300L;
        this.mUnscaledStartDelay = 0L;
        this.mStartDelay = 0L;
        init(nCreateRevealAnimator(x, y, startRadius, endRadius));
        this.mUiThreadHandlesDelay = true;
    }

    private void init(long ptr) {
        this.mNativePtr = new VirtualRefBasePtr(ptr);
    }

    private void checkMutable() {
        if (this.mState != 0) {
            throw new IllegalStateException("Animator has already started, cannot change it now!");
        }
        if (this.mNativePtr == null) {
            throw new IllegalStateException(
                    "Animator's target has been destroyed (trying to modify an animation after"
                            + " activity destroy?)");
        }
    }

    static boolean isNativeInterpolator(TimeInterpolator interpolator) {
        return interpolator.getClass().isAnnotationPresent(HasNativeInterpolator.class);
    }

    private void applyInterpolator() {
        long duration;
        if (this.mInterpolator == null || this.mNativePtr == null) {
            return;
        }
        if (isNativeInterpolator(this.mInterpolator)) {
            duration = ((NativeInterpolator) this.mInterpolator).createNativeInterpolator();
        } else {
            long duration2 = nGetDuration(this.mNativePtr.get());
            duration =
                    FallbackLUTInterpolator.createNativeInterpolator(this.mInterpolator, duration2);
        }
        nSetInterpolator(this.mNativePtr.get(), duration);
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
        if (this.mNativePtr == null) {
            cancel();
        } else if (this.mStartDelay <= 0 || !this.mUiThreadHandlesDelay) {
            nSetStartDelay(this.mNativePtr.get(), this.mStartDelay);
            doStart();
        } else {
            getHelper().addDelayedAnimation(this);
        }
    }

    private void doStart() {
        if (this.mRenderProperty == 11 && this.mViewListener != null) {
            this.mViewListener.onAlphaAnimationStart(this.mFinalValue);
        }
        moveToRunningState();
        if (this.mViewListener != null) {
            this.mViewListener.invalidateParent(false);
        }
    }

    private void moveToRunningState() {
        this.mState = 2;
        if (this.mNativePtr != null) {
            nStart(this.mNativePtr.get());
        }
        notifyStartListeners();
    }

    private void notifyStartListeners() {
        ArrayList<Animator.AnimatorListener> listeners = cloneListeners();
        int numListeners = listeners == null ? 0 : listeners.size();
        for (int i = 0; i < numListeners; i++) {
            listeners.get(i).onAnimationStart(this);
        }
    }

    @Override // android.animation.Animator
    public void cancel() {
        if (this.mState != 0 && this.mState != 3) {
            if (this.mState == 1) {
                getHelper().removeDelayedAnimation(this);
                moveToRunningState();
            }
            ArrayList<Animator.AnimatorListener> listeners = cloneListeners();
            int numListeners = listeners == null ? 0 : listeners.size();
            for (int i = 0; i < numListeners; i++) {
                listeners.get(i).onAnimationCancel(this);
            }
            end();
        }
    }

    @Override // android.animation.Animator
    public void end() {
        if (this.mState != 3) {
            if (this.mState < 2) {
                getHelper().removeDelayedAnimation(this);
                doStart();
            }
            if (this.mNativePtr != null) {
                nEnd(this.mNativePtr.get());
                if (this.mViewListener != null) {
                    this.mViewListener.invalidateParent(false);
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

    public void setViewListener(ViewListener listener) {
        this.mViewListener = listener;
    }

    public final void setTarget(RecordingCanvas canvas) {
        setTarget(canvas.mNode);
    }

    protected void setTarget(RenderNode node) {
        checkMutable();
        if (this.mTarget != null) {
            throw new IllegalStateException("Target already set!");
        }
        nSetListener(this.mNativePtr.get(), this);
        this.mTarget = node;
        this.mTarget.addAnimator(this);
    }

    public void setStartValue(float startValue) {
        checkMutable();
        nSetStartValue(this.mNativePtr.get(), startValue);
    }

    @Override // android.animation.Animator
    public void setStartDelay(long startDelay) {
        checkMutable();
        if (startDelay < 0) {
            throw new IllegalArgumentException("startDelay must be positive; " + startDelay);
        }
        this.mUnscaledStartDelay = startDelay;
        this.mStartDelay = (long) (ValueAnimator.getDurationScale() * startDelay);
    }

    @Override // android.animation.Animator
    public long getStartDelay() {
        return this.mUnscaledStartDelay;
    }

    @Override // android.animation.Animator
    public RenderNodeAnimator setDuration(long duration) {
        checkMutable();
        if (duration < 0) {
            throw new IllegalArgumentException("duration must be positive; " + duration);
        }
        this.mUnscaledDuration = duration;
        nSetDuration(this.mNativePtr.get(), (long) (duration * ValueAnimator.getDurationScale()));
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
        return this.mState == 1 || this.mState == 2;
    }

    @Override // android.animation.Animator
    public boolean isStarted() {
        return this.mState != 0;
    }

    @Override // android.animation.Animator
    public void setInterpolator(TimeInterpolator interpolator) {
        checkMutable();
        this.mInterpolator = interpolator;
    }

    @Override // android.animation.Animator
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    protected void onFinished() {
        if (this.mState == 0) {
            releaseNativePtr();
            return;
        }
        if (this.mState == 1) {
            getHelper().removeDelayedAnimation(this);
            notifyStartListeners();
        }
        this.mState = 3;
        ArrayList<Animator.AnimatorListener> listeners = cloneListeners();
        int numListeners = listeners == null ? 0 : listeners.size();
        for (int i = 0; i < numListeners; i++) {
            listeners.get(i).onAnimationEnd(this);
        }
        releaseNativePtr();
    }

    private void releaseNativePtr() {
        if (this.mNativePtr != null) {
            this.mNativePtr.release();
            this.mNativePtr = null;
        }
    }

    private ArrayList<Animator.AnimatorListener> cloneListeners() {
        ArrayList<Animator.AnimatorListener> listeners = getListeners();
        if (listeners != null) {
            return (ArrayList) listeners.clone();
        }
        return listeners;
    }

    public long getNativeAnimator() {
        return this.mNativePtr.get();
    }

    public boolean processDelayed(long frameTimeMs) {
        if (this.mStartTime == 0) {
            this.mStartTime = frameTimeMs;
            return false;
        }
        if (frameTimeMs - this.mStartTime >= this.mStartDelay) {
            doStart();
            return true;
        }
        return false;
    }

    private static DelayedAnimationHelper getHelper() {
        DelayedAnimationHelper helper = sAnimationHelper.get();
        if (helper == null) {
            DelayedAnimationHelper helper2 = new DelayedAnimationHelper();
            sAnimationHelper.set(helper2);
            return helper2;
        }
        return helper;
    }

    private static class DelayedAnimationHelper implements Runnable {
        private boolean mCallbackScheduled;
        private ArrayList<RenderNodeAnimator> mDelayedAnims = new ArrayList<>();
        private final Choreographer mChoreographer = Choreographer.getInstance();

        DelayedAnimationHelper() {}

        public void addDelayedAnimation(RenderNodeAnimator animator) {
            this.mDelayedAnims.add(animator);
            scheduleCallback();
        }

        public void removeDelayedAnimation(RenderNodeAnimator animator) {
            this.mDelayedAnims.remove(animator);
        }

        private void scheduleCallback() {
            if (!this.mCallbackScheduled) {
                this.mCallbackScheduled = true;
                this.mChoreographer.postCallback(1, this, null);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            long frameTimeMs = this.mChoreographer.getFrameTime();
            this.mCallbackScheduled = false;
            int end = 0;
            for (int i = 0; i < this.mDelayedAnims.size(); i++) {
                RenderNodeAnimator animator = this.mDelayedAnims.get(i);
                if (!animator.processDelayed(frameTimeMs)) {
                    if (end != i) {
                        this.mDelayedAnims.set(end, animator);
                    }
                    end++;
                }
            }
            while (this.mDelayedAnims.size() > end) {
                this.mDelayedAnims.remove(this.mDelayedAnims.size() - 1);
            }
            if (this.mDelayedAnims.size() > 0) {
                scheduleCallback();
            }
        }
    }

    private static void callOnFinished(final RenderNodeAnimator animator) {
        if (animator.mHandler != null) {
            Handler handler = animator.mHandler;
            Objects.requireNonNull(animator);
            handler.post(
                    new Runnable() { // from class:
                        // android.graphics.animation.RenderNodeAnimator$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RenderNodeAnimator.this.onFinished();
                        }
                    });
        } else {
            Handler handler2 = new Handler(Looper.getMainLooper(), null, true);
            Objects.requireNonNull(animator);
            handler2.post(
                    new Runnable() { // from class:
                        // android.graphics.animation.RenderNodeAnimator$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RenderNodeAnimator.this.onFinished();
                        }
                    });
        }
    }

    @Override // android.animation.Animator
    /* renamed from: clone */
    public Animator mo77clone() {
        throw new IllegalStateException("Cannot clone this animator");
    }

    @Override // android.animation.Animator
    public void setAllowRunningAsynchronously(boolean mayRunAsync) {
        checkMutable();
        nSetAllowRunningAsync(this.mNativePtr.get(), mayRunAsync);
    }
}
