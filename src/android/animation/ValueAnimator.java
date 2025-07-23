package android.animation;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ValueAnimator extends Animator implements AnimationHandler.AnimationFrameCallback {
    private static final boolean DEBUG = false;
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final String TAG = "ValueAnimator";
    private AnimationHandler mAnimationHandler;
    private long mPauseTime;
    private boolean mReversing;
    boolean mStartTimeCommitted;
    PropertyValuesHolder[] mValues;
    HashMap<String, PropertyValuesHolder> mValuesMap;
    private static final boolean TRACE_ANIMATION_FRACTION = SystemProperties.getBoolean("persist.debug.animator.trace_fraction", false);
    private static float sDurationScale = 1.0f;
    private static final ArrayList<WeakReference<DurationScaleChangeListener>> sDurationScaleChangeListeners = new ArrayList<>();
    private static final TimeInterpolator sDefaultInterpolator = new AccelerateDecelerateInterpolator();
    long mStartTime = -1;
    float mSeekFraction = -1.0f;
    private boolean mResumed = false;
    private float mOverallFraction = 0.0f;
    private float mCurrentFraction = 0.0f;
    private long mLastFrameTime = -1;
    private long mFirstFrameTime = -1;
    private boolean mRunning = false;
    private boolean mStarted = false;
    boolean mInitialized = false;
    private boolean mAnimationEndRequested = false;
    private long mDuration = 300;
    private long mStartDelay = 0;
    private int mRepeatCount = 0;
    private int mRepeatMode = 1;
    private boolean mSelfPulse = true;
    private boolean mSuppressSelfPulseRequested = false;
    private TimeInterpolator mInterpolator = sDefaultInterpolator;
    ArrayList<AnimatorUpdateListener> mUpdateListeners = null;
    private float mDurationScale = -1.0f;

    public interface AnimatorUpdateListener {
        void onAnimationUpdate(ValueAnimator valueAnimator);
    }

    public interface DurationScaleChangeListener {
        void onChanged(float f);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    @Override // android.animation.Animator
    public boolean canReverse() {
        return true;
    }

    @Override // android.animation.Animator
    public void setAllowRunningAsynchronously(boolean z) {
    }

    public static void setDurationScale(float f) {
        ArrayList arrayList;
        sDurationScale = f;
        ArrayList<WeakReference<DurationScaleChangeListener>> arrayList2 = sDurationScaleChangeListeners;
        synchronized (arrayList2) {
            arrayList = new ArrayList(arrayList2);
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            DurationScaleChangeListener durationScaleChangeListener = (DurationScaleChangeListener) ((WeakReference) arrayList.get(i)).get();
            if (durationScaleChangeListener != null) {
                durationScaleChangeListener.onChanged(f);
            }
        }
    }

    public static float getDurationScale() {
        return sDurationScale;
    }

    public static boolean registerDurationScaleChangeListener(DurationScaleChangeListener durationScaleChangeListener) {
        synchronized (sDurationScaleChangeListeners) {
            int i = 0;
            int i2 = -1;
            while (true) {
                ArrayList<WeakReference<DurationScaleChangeListener>> arrayList = sDurationScaleChangeListeners;
                if (i >= arrayList.size()) {
                    if (i2 != -1) {
                        arrayList.set(i2, new WeakReference<>(durationScaleChangeListener));
                        return true;
                    }
                    return arrayList.add(new WeakReference<>(durationScaleChangeListener));
                }
                WeakReference<DurationScaleChangeListener> weakReference = arrayList.get(i);
                if (weakReference.get() == null) {
                    if (i2 == -1) {
                        i2 = i;
                    }
                } else if (weakReference.get() == durationScaleChangeListener) {
                    return false;
                }
                i++;
            }
        }
    }

    public static boolean unregisterDurationScaleChangeListener(DurationScaleChangeListener durationScaleChangeListener) {
        WeakReference<DurationScaleChangeListener> weakReference;
        boolean remove;
        ArrayList<WeakReference<DurationScaleChangeListener>> arrayList = sDurationScaleChangeListeners;
        synchronized (arrayList) {
            Iterator<WeakReference<DurationScaleChangeListener>> it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    weakReference = null;
                    break;
                }
                weakReference = it.next();
                if (weakReference.get() == durationScaleChangeListener) {
                    break;
                }
            }
            remove = sDurationScaleChangeListeners.remove(weakReference);
        }
        return remove;
    }

    public static boolean areAnimatorsEnabled() {
        return sDurationScale != 0.0f;
    }

    public static ValueAnimator ofInt(int... iArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(iArr);
        return valueAnimator;
    }

    public static ValueAnimator ofArgb(int... iArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(iArr);
        valueAnimator.setEvaluator(ArgbEvaluator.getInstance());
        return valueAnimator;
    }

    public static ValueAnimator ofFloat(float... fArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(fArr);
        return valueAnimator;
    }

    public static ValueAnimator ofPropertyValuesHolder(PropertyValuesHolder... propertyValuesHolderArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(propertyValuesHolderArr);
        return valueAnimator;
    }

    public static ValueAnimator ofObject(TypeEvaluator typeEvaluator, Object... objArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(objArr);
        valueAnimator.setEvaluator(typeEvaluator);
        return valueAnimator;
    }

    public void setIntValues(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length == 0) {
            setValues(PropertyValuesHolder.ofInt("", iArr));
        } else {
            propertyValuesHolderArr[0].setIntValues(iArr);
        }
        this.mInitialized = false;
    }

    public void setFloatValues(float... fArr) {
        if (fArr == null || fArr.length == 0) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length == 0) {
            setValues(PropertyValuesHolder.ofFloat("", fArr));
        } else {
            propertyValuesHolderArr[0].setFloatValues(fArr);
        }
        this.mInitialized = false;
    }

    public void setObjectValues(Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length == 0) {
            setValues(PropertyValuesHolder.ofObject("", (TypeEvaluator) null, objArr));
        } else {
            propertyValuesHolderArr[0].setObjectValues(objArr);
        }
        this.mInitialized = false;
    }

    public void setValues(PropertyValuesHolder... propertyValuesHolderArr) {
        int length = propertyValuesHolderArr.length;
        this.mValues = propertyValuesHolderArr;
        this.mValuesMap = new HashMap<>(length);
        for (PropertyValuesHolder propertyValuesHolder : propertyValuesHolderArr) {
            this.mValuesMap.put(propertyValuesHolder.getPropertyName(), propertyValuesHolder);
        }
        this.mInitialized = false;
    }

    public PropertyValuesHolder[] getValues() {
        return this.mValues;
    }

    void initAnimation() {
        if (this.mInitialized) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            int length = propertyValuesHolderArr.length;
            for (int i = 0; i < length; i++) {
                this.mValues[i].init();
            }
        }
        this.mInitialized = true;
    }

    @Override // android.animation.Animator
    public ValueAnimator setDuration(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + j);
        }
        this.mDuration = j;
        return this;
    }

    public void overrideDurationScale(float f) {
        this.mDurationScale = f;
    }

    private float resolveDurationScale() {
        float f = this.mDurationScale;
        return f >= 0.0f ? f : sDurationScale;
    }

    private long getScaledDuration() {
        return (long) (this.mDuration * resolveDurationScale());
    }

    @Override // android.animation.Animator
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.animation.Animator
    public long getTotalDuration() {
        if (this.mRepeatCount == -1) {
            return -1L;
        }
        return this.mStartDelay + (this.mDuration * (r0 + 1));
    }

    public void setCurrentPlayTime(long j) {
        long j2 = this.mDuration;
        setCurrentFraction(j2 > 0 ? j / j2 : 1.0f);
    }

    public void setCurrentFraction(float f) {
        initAnimation();
        float clampFraction = clampFraction(f);
        this.mStartTimeCommitted = true;
        if (isPulsingInternal()) {
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis() - ((long) (getScaledDuration() * clampFraction));
        } else {
            this.mSeekFraction = clampFraction;
        }
        this.mOverallFraction = clampFraction;
        animateValue(getCurrentIterationFraction(clampFraction, this.mReversing));
    }

    private int getCurrentIteration(float f) {
        float clampFraction = clampFraction(f);
        double d = clampFraction;
        double floor = Math.floor(d);
        if (d == floor && clampFraction > 0.0f) {
            floor -= 1.0d;
        }
        return (int) floor;
    }

    private float getCurrentIterationFraction(float f, boolean z) {
        float clampFraction = clampFraction(f);
        int currentIteration = getCurrentIteration(clampFraction);
        float f2 = clampFraction - currentIteration;
        return shouldPlayBackward(currentIteration, z) ? 1.0f - f2 : f2;
    }

    private float clampFraction(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        return this.mRepeatCount != -1 ? Math.min(f, r2 + 1) : f;
    }

    private boolean shouldPlayBackward(int i, boolean z) {
        if (i > 0 && this.mRepeatMode == 2) {
            int i2 = this.mRepeatCount;
            if (i < i2 + 1 || i2 == -1) {
                return z ? i % 2 == 0 : i % 2 != 0;
            }
        }
        return z;
    }

    public long getCurrentPlayTime() {
        float currentAnimationTimeMillis;
        if (!this.mInitialized) {
            return 0L;
        }
        if (!this.mStarted && this.mSeekFraction < 0.0f) {
            return 0L;
        }
        float f = this.mSeekFraction;
        if (f >= 0.0f) {
            currentAnimationTimeMillis = this.mDuration * f;
        } else {
            float resolveDurationScale = resolveDurationScale();
            if (resolveDurationScale == 0.0f) {
                resolveDurationScale = 1.0f;
            }
            currentAnimationTimeMillis = (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) / resolveDurationScale;
        }
        return (long) currentAnimationTimeMillis;
    }

    @Override // android.animation.Animator
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // android.animation.Animator
    public void setStartDelay(long j) {
        if (j < 0) {
            Log.w(TAG, "Start delay should always be non-negative");
            j = 0;
        }
        this.mStartDelay = j;
    }

    public static long getFrameDelay() {
        AnimationHandler.getInstance();
        return AnimationHandler.getFrameDelay();
    }

    public static void setFrameDelay(long j) {
        AnimationHandler.getInstance();
        AnimationHandler.setFrameDelay(j);
    }

    public Object getAnimatedValue() {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length <= 0) {
            return null;
        }
        return propertyValuesHolderArr[0].getAnimatedValue();
    }

    public Object getAnimatedValue(String str) {
        PropertyValuesHolder propertyValuesHolder = this.mValuesMap.get(str);
        if (propertyValuesHolder != null) {
            return propertyValuesHolder.getAnimatedValue();
        }
        return null;
    }

    public void setRepeatCount(int i) {
        this.mRepeatCount = i;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public void setRepeatMode(int i) {
        this.mRepeatMode = i;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    public void addUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList<>();
        }
        this.mUpdateListeners.add(animatorUpdateListener);
    }

    public void removeAllUpdateListeners() {
        ArrayList<AnimatorUpdateListener> arrayList = this.mUpdateListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.clear();
        this.mUpdateListeners = null;
    }

    public void removeUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        ArrayList<AnimatorUpdateListener> arrayList = this.mUpdateListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorUpdateListener);
        if (this.mUpdateListeners.size() == 0) {
            this.mUpdateListeners = null;
        }
    }

    @Override // android.animation.Animator
    public void setInterpolator(TimeInterpolator timeInterpolator) {
        if (timeInterpolator != null) {
            this.mInterpolator = timeInterpolator;
        } else {
            this.mInterpolator = new LinearInterpolator();
        }
    }

    @Override // android.animation.Animator
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setEvaluator(TypeEvaluator typeEvaluator) {
        PropertyValuesHolder[] propertyValuesHolderArr;
        if (typeEvaluator == null || (propertyValuesHolderArr = this.mValues) == null || propertyValuesHolderArr.length <= 0) {
            return;
        }
        propertyValuesHolderArr[0].setEvaluator(typeEvaluator);
    }

    private void start(boolean z) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mReversing = z;
        this.mSelfPulse = !this.mSuppressSelfPulseRequested;
        if (z) {
            float f = this.mSeekFraction;
            if (f != -1.0f && f != 0.0f) {
                if (this.mRepeatCount == -1) {
                    this.mSeekFraction = 1.0f - ((float) (f - Math.floor(f)));
                } else {
                    this.mSeekFraction = (r3 + 1) - f;
                }
            }
        }
        this.mStarted = true;
        this.mPaused = false;
        this.mRunning = false;
        this.mAnimationEndRequested = false;
        this.mLastFrameTime = -1L;
        this.mFirstFrameTime = -1L;
        this.mStartTime = -1L;
        addAnimationCallback(0L);
        if (this.mStartDelay == 0 || this.mSeekFraction >= 0.0f || this.mReversing) {
            startAnimation();
            float f2 = this.mSeekFraction;
            if (f2 == -1.0f) {
                setCurrentPlayTime(0L);
            } else {
                setCurrentFraction(f2);
            }
        }
    }

    @Override // android.animation.Animator
    void startWithoutPulsing(boolean z) {
        this.mSuppressSelfPulseRequested = true;
        if (z) {
            reverse();
        } else {
            start();
        }
        this.mSuppressSelfPulseRequested = false;
    }

    @Override // android.animation.Animator
    public void start() {
        start(false);
    }

    @Override // android.animation.Animator
    public void cancel() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mAnimationEndRequested) {
            consumePendingEndListeners(true);
            return;
        }
        if ((this.mStarted || this.mRunning || this.mStartListenersCalled) && this.mListeners != null) {
            if (!this.mRunning) {
                notifyStartListeners(this.mReversing);
            }
            notifyListeners(Animator.AnimatorCaller.ON_CANCEL, false);
        }
        endAnimation();
    }

    @Override // android.animation.Animator
    public void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (!this.mRunning) {
            startAnimation();
            this.mStarted = true;
        } else if (!this.mInitialized) {
            initAnimation();
        }
        animateValue(shouldPlayBackward(this.mRepeatCount, this.mReversing) ? 0.0f : 1.0f);
        if (this.mAnimationEndRequested) {
            consumePendingEndListeners(true);
        } else {
            endAnimation();
        }
    }

    @Override // android.animation.Animator
    public void resume() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be resumed from the same thread that the animator was started on");
        }
        if (this.mPaused && !this.mResumed) {
            this.mResumed = true;
            if (this.mPauseTime > 0) {
                addAnimationCallback(0L);
            }
        }
        super.resume();
    }

    @Override // android.animation.Animator
    public void pause() {
        boolean z = this.mPaused;
        super.pause();
        if (z || !this.mPaused) {
            return;
        }
        this.mPauseTime = -1L;
        this.mResumed = false;
    }

    @Override // android.animation.Animator
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override // android.animation.Animator
    public boolean isStarted() {
        return this.mStarted;
    }

    @Override // android.animation.Animator
    public void reverse() {
        if (isPulsingInternal()) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.mStartTime = currentAnimationTimeMillis - (getScaledDuration() - (currentAnimationTimeMillis - this.mStartTime));
            this.mStartTimeCommitted = true;
            this.mReversing = !this.mReversing;
            return;
        }
        if (this.mStarted) {
            this.mReversing = !this.mReversing;
            end();
        } else {
            start(true);
        }
    }

    private void endAnimation() {
        endAnimation(false);
    }

    private void endAnimation(boolean z) {
        if (this.mAnimationEndRequested) {
            return;
        }
        boolean z2 = sPostNotifyEndListenerEnabled && this.mListeners != null && z && getScaledDuration() > 0;
        removeAnimationCallback();
        this.mAnimationEndRequested = true;
        this.mPaused = false;
        if ((this.mStarted || this.mRunning) && this.mListeners != null && !this.mRunning) {
            notifyStartListeners(this.mReversing);
        }
        this.mLastFrameTime = -1L;
        this.mFirstFrameTime = -1L;
        this.mStartTime = -1L;
        notifyEndListenersFromEndAnimation(this.mReversing, z2);
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceEnd(8L, getNameForTrace(), System.identityHashCode(this));
        }
    }

    @Override // android.animation.Animator
    void completeEndAnimation(boolean z, String str) {
        this.mRunning = false;
        this.mStarted = false;
        super.completeEndAnimation(z, str);
        this.mReversing = false;
    }

    private void startAnimation() {
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceBegin(8L, getNameForTrace(), System.identityHashCode(this));
        }
        this.mAnimationEndRequested = false;
        initAnimation();
        this.mRunning = true;
        float f = this.mSeekFraction;
        if (f >= 0.0f) {
            this.mOverallFraction = f;
        } else {
            this.mOverallFraction = 0.0f;
        }
        notifyStartListeners(this.mReversing);
    }

    private boolean isPulsingInternal() {
        return this.mLastFrameTime >= 0;
    }

    String getNameForTrace() {
        return "animator";
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public void commitAnimationFrame(long j) {
        if (this.mStartTimeCommitted) {
            return;
        }
        this.mStartTimeCommitted = true;
        long j2 = j - this.mLastFrameTime;
        if (j2 > 0) {
            this.mStartTime += j2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003f, code lost:
    
        if (r2 != false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean animateBasedOnTime(long r7) {
        /*
            r6 = this;
            boolean r0 = r6.mRunning
            r1 = 0
            if (r0 == 0) goto L51
            long r2 = r6.getScaledDuration()
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L16
            long r4 = r6.mStartTime
            long r7 = r7 - r4
            float r7 = (float) r7
            float r8 = (float) r2
            float r7 = r7 / r8
            goto L18
        L16:
            r7 = 1065353216(0x3f800000, float:1.0)
        L18:
            float r8 = r6.mOverallFraction
            int r2 = (int) r7
            int r8 = (int) r8
            r3 = 1
            if (r2 <= r8) goto L21
            r8 = r3
            goto L22
        L21:
            r8 = r1
        L22:
            int r2 = r6.mRepeatCount
            int r4 = r2 + 1
            float r4 = (float) r4
            int r4 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r4 < 0) goto L30
            r4 = -1
            if (r2 == r4) goto L30
            r2 = r3
            goto L31
        L30:
            r2 = r1
        L31:
            if (r0 != 0) goto L35
        L33:
            r1 = r3
            goto L42
        L35:
            if (r8 == 0) goto L3f
            if (r2 != 0) goto L3f
            android.animation.Animator$AnimatorCaller<android.animation.Animator$AnimatorListener, android.animation.Animator> r8 = android.animation.Animator.AnimatorCaller.ON_REPEAT
            r6.notifyListeners(r8, r1)
            goto L42
        L3f:
            if (r2 == 0) goto L42
            goto L33
        L42:
            float r7 = r6.clampFraction(r7)
            r6.mOverallFraction = r7
            boolean r8 = r6.mReversing
            float r7 = r6.getCurrentIterationFraction(r7, r8)
            r6.animateValue(r7)
        L51:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.animation.ValueAnimator.animateBasedOnTime(long):boolean");
    }

    @Override // android.animation.Animator
    void animateValuesInRange(long j, long j2) {
        if (j < 0 || j2 < -1) {
            throw new UnsupportedOperationException("Error: Play time should never be negative.");
        }
        initAnimation();
        long totalDuration = getTotalDuration();
        if (j2 < 0 || (j2 == 0 && j > 0)) {
            notifyStartListeners(false);
        } else if (j2 > totalDuration || (j2 == totalDuration && j < totalDuration)) {
            notifyStartListeners(true);
        }
        if (totalDuration >= 0) {
            j2 = Math.min(totalDuration, j2);
        }
        long j3 = this.mStartDelay;
        long j4 = j2 - j3;
        long j5 = j - j3;
        if (this.mRepeatCount > 0) {
            if (Math.min(Math.max(0, (int) (j5 / this.mDuration)), this.mRepeatCount) != Math.min(Math.max(0, (int) (j4 / this.mDuration)), this.mRepeatCount)) {
                notifyListeners(Animator.AnimatorCaller.ON_REPEAT, false);
            }
        }
        if (this.mRepeatCount != -1 && j5 > (r11 + 1) * this.mDuration) {
            throw new IllegalStateException("Can't animate a value outside of the duration");
        }
        animateValue(getCurrentIterationFraction(Math.max(0L, j5) / this.mDuration, false));
    }

    @Override // android.animation.Animator
    void animateSkipToEnds(long j, long j2) {
        boolean z = j < j2;
        if (j > 0 || j2 <= 0) {
            long totalDuration = getTotalDuration();
            if (totalDuration < 0 || j < totalDuration || j2 >= totalDuration) {
                return;
            }
        }
        notifyStartListeners(z);
        skipToEndValue(z);
        notifyEndListeners(z);
    }

    @Override // android.animation.Animator
    void skipToEndValue(boolean z) {
        initAnimation();
        animateValue((this.mRepeatCount % 2 == 1 && this.mRepeatMode == 2) ? 0.0f : z ? 0.0f : 1.0f);
    }

    @Override // android.animation.Animator
    boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public final boolean doAnimationFrame(long j) {
        if (this.mStartTime < 0) {
            this.mStartTime = this.mReversing ? j : ((long) (this.mStartDelay * resolveDurationScale())) + j;
        }
        if (this.mPaused) {
            this.mPauseTime = j;
            removeAnimationCallback();
            return false;
        }
        if (this.mResumed) {
            this.mResumed = false;
            long j2 = this.mPauseTime;
            if (j2 > 0) {
                this.mStartTime += j - j2;
            }
        }
        if (!this.mRunning) {
            if (this.mStartTime > j && this.mSeekFraction == -1.0f) {
                return false;
            }
            this.mRunning = true;
            startAnimation();
        }
        if (this.mLastFrameTime < 0) {
            if (this.mSeekFraction >= 0.0f) {
                this.mStartTime = j - ((long) (getScaledDuration() * this.mSeekFraction));
                this.mSeekFraction = -1.0f;
            }
            this.mStartTimeCommitted = false;
        }
        this.mLastFrameTime = j;
        boolean animateBasedOnTime = animateBasedOnTime(Math.max(j, this.mStartTime));
        if (animateBasedOnTime) {
            endAnimation(true);
        }
        return animateBasedOnTime;
    }

    @Override // android.animation.Animator
    boolean pulseAnimationFrame(long j) {
        if (this.mSelfPulse) {
            return false;
        }
        return doAnimationFrame(j);
    }

    private void addOneShotCommitCallback() {
        if (this.mSelfPulse) {
            getAnimationHandler().addOneShotCommitCallback(this);
        }
    }

    private void removeAnimationCallback() {
        if (this.mSelfPulse) {
            getAnimationHandler().removeCallback(this);
        }
    }

    private void addAnimationCallback(long j) {
        if (this.mSelfPulse) {
            getAnimationHandler().addAnimationFrameCallback(this, j);
        }
    }

    public float getAnimatedFraction() {
        return this.mCurrentFraction;
    }

    void animateValue(float f) {
        if (TRACE_ANIMATION_FRACTION) {
            Trace.traceCounter(8L, getNameForTrace() + hashCode(), (int) (1000.0f * f));
        }
        if (this.mValues == null) {
            return;
        }
        float interpolation = this.mInterpolator.getInterpolation(f);
        this.mCurrentFraction = interpolation;
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].calculateValue(interpolation);
        }
        if (this.mSeekFraction >= 0.0f || this.mStartListenersCalled) {
            callOnList(this.mUpdateListeners, Animator.AnimatorCaller.ON_UPDATE, this, false);
        }
    }

    @Override // android.animation.Animator
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ValueAnimator mo76clone() {
        ValueAnimator valueAnimator = (ValueAnimator) super.mo76clone();
        if (this.mUpdateListeners != null) {
            valueAnimator.mUpdateListeners = new ArrayList<>(this.mUpdateListeners);
        }
        valueAnimator.mSeekFraction = -1.0f;
        valueAnimator.mReversing = false;
        valueAnimator.mInitialized = false;
        valueAnimator.mStarted = false;
        valueAnimator.mRunning = false;
        valueAnimator.mPaused = false;
        valueAnimator.mResumed = false;
        valueAnimator.mStartTime = -1L;
        valueAnimator.mStartTimeCommitted = false;
        valueAnimator.mAnimationEndRequested = false;
        valueAnimator.mPauseTime = -1L;
        valueAnimator.mLastFrameTime = -1L;
        valueAnimator.mFirstFrameTime = -1L;
        valueAnimator.mOverallFraction = 0.0f;
        valueAnimator.mCurrentFraction = 0.0f;
        valueAnimator.mSelfPulse = true;
        valueAnimator.mSuppressSelfPulseRequested = false;
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            int length = propertyValuesHolderArr.length;
            valueAnimator.mValues = new PropertyValuesHolder[length];
            valueAnimator.mValuesMap = new HashMap<>(length);
            for (int i = 0; i < length; i++) {
                PropertyValuesHolder mo121clone = propertyValuesHolderArr[i].mo121clone();
                valueAnimator.mValues[i] = mo121clone;
                valueAnimator.mValuesMap.put(mo121clone.getPropertyName(), mo121clone);
            }
        }
        return valueAnimator;
    }

    public static int getCurrentAnimationsCount() {
        return AnimationHandler.getAnimationCount();
    }

    public String toString() {
        String str = "ValueAnimator@" + Integer.toHexString(hashCode());
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                str = str + "\n    " + this.mValues[i].toString();
            }
        }
        return str;
    }

    public AnimationHandler getAnimationHandler() {
        AnimationHandler animationHandler = this.mAnimationHandler;
        return animationHandler != null ? animationHandler : AnimationHandler.getInstance();
    }

    public void setAnimationHandler(AnimationHandler animationHandler) {
        this.mAnimationHandler = animationHandler;
    }
}
