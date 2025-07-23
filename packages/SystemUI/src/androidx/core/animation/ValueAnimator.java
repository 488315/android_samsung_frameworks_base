package androidx.core.animation;

import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.animation.AnimationUtils;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.Animator;
import androidx.core.animation.PropertyValuesHolder;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ValueAnimator extends Animator implements AnimationHandler.AnimationFrameCallback {
    public static final AccelerateDecelerateInterpolator sDefaultInterpolator = new AccelerateDecelerateInterpolator();
    public boolean mReversing;
    public PropertyValuesHolder[] mValues;
    public HashMap mValuesMap;
    public long mStartTime = -1;
    public float mSeekFraction = -1.0f;
    public float mOverallFraction = 0.0f;
    public float mCurrentFraction = 0.0f;
    public long mLastFrameTime = -1;
    public boolean mRunning = false;
    public boolean mStarted = false;
    public boolean mStartListenersCalled = false;
    public boolean mInitialized = false;
    public boolean mAnimationEndRequested = false;
    public long mDuration = 300;
    public long mStartDelay = 0;
    public final int mRepeatMode = 1;
    public boolean mSelfPulse = true;
    public boolean mSuppressSelfPulseRequested = false;
    public Interpolator mInterpolator = sDefaultInterpolator;
    public final float mDurationScale = -1.0f;

    public static float clampFraction(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        return Math.min(f, 1);
    }

    public static ValueAnimator ofFloat(float... fArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(fArr);
        return valueAnimator;
    }

    public static ValueAnimator ofInt(int... iArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        if (iArr.length == 0) {
            return valueAnimator;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = valueAnimator.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length == 0) {
            Class[] clsArr = PropertyValuesHolder.FLOAT_VARIANTS;
            valueAnimator.setValues(new PropertyValuesHolder.IntPropertyValuesHolder("", iArr));
        } else {
            propertyValuesHolderArr[0].setIntValues(iArr);
        }
        valueAnimator.mInitialized = false;
        return valueAnimator;
    }

    public void animateValue(float f) {
        float interpolation = this.mInterpolator.getInterpolation(f);
        this.mCurrentFraction = interpolation;
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].calculateValue(interpolation);
        }
        ArrayList arrayList = this.mUpdateListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((Animator.AnimatorUpdateListener) this.mUpdateListeners.get(i2)).onAnimationUpdate(this);
            }
        }
    }

    @Override // androidx.core.animation.Animator
    public final void cancel() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mAnimationEndRequested) {
            return;
        }
        if ((this.mStarted || this.mRunning) && this.mListeners != null) {
            if (!this.mRunning) {
                notifyStartListeners();
            }
            ArrayList arrayList = (ArrayList) this.mListeners.clone();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Animator.AnimatorListener) obj).onAnimationCancel(this);
            }
        }
        endAnimation$1();
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b2, code lost:
    
        if (r13 != false) goto L59;
     */
    @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean doAnimationFrame(long r12) {
        /*
            Method dump skipped, instructions count: 204
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.animation.ValueAnimator.doAnimationFrame(long):boolean");
    }

    @Override // androidx.core.animation.Animator
    public final void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (!this.mRunning) {
            startAnimation$1();
            this.mStarted = true;
        } else if (!this.mInitialized) {
            initAnimation$1();
        }
        animateValue(this.mReversing ? 0.0f : 1.0f);
        endAnimation$1();
    }

    public final void endAnimation$1() {
        ArrayList arrayList;
        if (this.mAnimationEndRequested) {
            return;
        }
        if (this.mSelfPulse) {
            AnimationHandler animationHandler = AnimationHandler.getInstance();
            int indexOf = animationHandler.mAnimationCallbacks.indexOf(this);
            if (indexOf >= 0) {
                animationHandler.mAnimationCallbacks.set(indexOf, null);
                animationHandler.mListDirty = true;
            }
        }
        this.mAnimationEndRequested = true;
        boolean z = (this.mStarted || this.mRunning) && this.mListeners != null;
        if (z && !this.mRunning) {
            notifyStartListeners();
        }
        this.mRunning = false;
        this.mStarted = false;
        this.mStartListenersCalled = false;
        this.mLastFrameTime = -1L;
        this.mStartTime = -1L;
        if (z && (arrayList = this.mListeners) != null) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationEnd(this, this.mReversing);
            }
        }
        this.mReversing = false;
    }

    public final Object getAnimatedValue() {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length <= 0) {
            return null;
        }
        return propertyValuesHolderArr[0].getAnimatedValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0030, code lost:
    
        r7 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float getCurrentIterationFraction(float r6, boolean r7) {
        /*
            r5 = this;
            float r6 = clampFraction(r6)
            float r0 = clampFraction(r6)
            double r1 = (double) r0
            double r3 = java.lang.Math.floor(r1)
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 != 0) goto L19
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L19
            r0 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r3 = r3 - r0
        L19:
            int r0 = (int) r3
            float r1 = (float) r0
            float r6 = r6 - r1
            if (r0 <= 0) goto L33
            int r5 = r5.mRepeatMode
            r1 = 2
            if (r5 != r1) goto L33
            r5 = 1
            if (r0 < r5) goto L27
            goto L33
        L27:
            if (r7 == 0) goto L2d
            int r0 = r0 % r1
            if (r0 != 0) goto L32
            goto L30
        L2d:
            int r0 = r0 % r1
            if (r0 == 0) goto L32
        L30:
            r7 = r5
            goto L33
        L32:
            r7 = 0
        L33:
            if (r7 == 0) goto L39
            r5 = 1065353216(0x3f800000, float:1.0)
            float r5 = r5 - r6
            return r5
        L39:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.animation.ValueAnimator.getCurrentIterationFraction(float, boolean):float");
    }

    @Override // androidx.core.animation.Animator
    public final long getDuration() {
        return this.mDuration;
    }

    @Override // androidx.core.animation.Animator
    public final long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // androidx.core.animation.Animator
    public final long getTotalDuration() {
        return (this.mDuration * 1) + this.mStartDelay;
    }

    public void initAnimation$1() {
        if (this.mInitialized) {
            return;
        }
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            PropertyValuesHolder propertyValuesHolder = this.mValues[i];
            if (propertyValuesHolder.mEvaluator == null) {
                Class cls = propertyValuesHolder.mValueType;
                propertyValuesHolder.mEvaluator = cls == Integer.class ? IntEvaluator.sInstance : cls == Float.class ? FloatEvaluator.sInstance : null;
            }
            TypeEvaluator typeEvaluator = propertyValuesHolder.mEvaluator;
            if (typeEvaluator != null) {
                ((KeyframeSet) propertyValuesHolder.mKeyframes).mEvaluator = typeEvaluator;
            }
        }
        this.mInitialized = true;
    }

    @Override // androidx.core.animation.Animator
    public boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // androidx.core.animation.Animator
    public final boolean isRunning() {
        return this.mRunning;
    }

    @Override // androidx.core.animation.Animator
    public final boolean isStarted() {
        return this.mStarted;
    }

    public final void notifyStartListeners() {
        ArrayList arrayList = this.mListeners;
        if (arrayList != null && !this.mStartListenersCalled) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationStart(this, this.mReversing);
            }
        }
        this.mStartListenersCalled = true;
    }

    @Override // androidx.core.animation.Animator
    public final boolean pulseAnimationFrame(long j) {
        if (this.mSelfPulse) {
            return false;
        }
        return doAnimationFrame(j);
    }

    @Override // androidx.core.animation.Animator
    public final void reverse() {
        if (!(this.mLastFrameTime >= 0)) {
            if (!this.mStarted) {
                start(true);
                return;
            } else {
                this.mReversing = !this.mReversing;
                end();
                return;
            }
        }
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        long j = currentAnimationTimeMillis - this.mStartTime;
        float f = this.mDuration;
        float f2 = this.mDurationScale;
        if (f2 < 0.0f) {
            f2 = 1.0f;
        }
        this.mStartTime = currentAnimationTimeMillis - (((long) (f2 * f)) - j);
        this.mReversing = !this.mReversing;
    }

    public final void setCurrentFraction(float f) {
        initAnimation$1();
        float clampFraction = clampFraction(f);
        if (this.mLastFrameTime >= 0) {
            float f2 = this.mDuration;
            float f3 = this.mDurationScale;
            if (f3 < 0.0f) {
                f3 = 1.0f;
            }
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis() - ((long) (((long) (f3 * f2)) * clampFraction));
        } else {
            this.mSeekFraction = clampFraction;
        }
        this.mOverallFraction = clampFraction;
        animateValue(getCurrentIterationFraction(clampFraction, this.mReversing));
    }

    public void setFloatValues(float... fArr) {
        if (fArr.length == 0) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length == 0) {
            Class[] clsArr = PropertyValuesHolder.FLOAT_VARIANTS;
            setValues(new PropertyValuesHolder.FloatPropertyValuesHolder("", fArr));
        } else {
            propertyValuesHolderArr[0].setFloatValues(fArr);
        }
        this.mInitialized = false;
    }

    @Override // androidx.core.animation.Animator
    public final void setInterpolator(Interpolator interpolator) {
        if (interpolator != null) {
            this.mInterpolator = interpolator;
        } else {
            this.mInterpolator = new LinearInterpolator();
        }
    }

    public final void setStartDelay(long j) {
        if (j < 0) {
            Log.w("ValueAnimator", "Start delay should always be non-negative");
            j = 0;
        }
        this.mStartDelay = j;
    }

    public final void setValues(PropertyValuesHolder... propertyValuesHolderArr) {
        int length = propertyValuesHolderArr.length;
        this.mValues = propertyValuesHolderArr;
        this.mValuesMap = new HashMap(length);
        for (PropertyValuesHolder propertyValuesHolder : propertyValuesHolderArr) {
            this.mValuesMap.put(propertyValuesHolder.mPropertyName, propertyValuesHolder);
        }
        this.mInitialized = false;
    }

    @Override // androidx.core.animation.Animator
    public final void skipToEndValue(boolean z) {
        initAnimation$1();
        animateValue(z ? 0.0f : 1.0f);
    }

    public final void start(boolean z) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mReversing = z;
        this.mSelfPulse = !this.mSuppressSelfPulseRequested;
        if (z) {
            float f = this.mSeekFraction;
            if (f != -1.0f && f != 0.0f) {
                this.mSeekFraction = 1 - f;
            }
        }
        this.mStarted = true;
        this.mRunning = false;
        this.mAnimationEndRequested = false;
        this.mLastFrameTime = -1L;
        this.mStartTime = -1L;
        if (this.mStartDelay == 0 || this.mSeekFraction >= 0.0f || z) {
            startAnimation$1();
            float f2 = this.mSeekFraction;
            if (f2 == -1.0f) {
                long j = this.mDuration;
                setCurrentFraction(j > 0 ? 0 / j : 1.0f);
            } else {
                setCurrentFraction(f2);
            }
        }
        if (this.mSelfPulse) {
            Animator.addAnimationCallback(this);
        }
    }

    public final void startAnimation$1() {
        this.mAnimationEndRequested = false;
        initAnimation$1();
        this.mRunning = true;
        float f = this.mSeekFraction;
        if (f >= 0.0f) {
            this.mOverallFraction = f;
        } else {
            this.mOverallFraction = 0.0f;
        }
        if (this.mListeners != null) {
            notifyStartListeners();
        }
    }

    @Override // androidx.core.animation.Animator
    public final void startWithoutPulsing(boolean z) {
        this.mSuppressSelfPulseRequested = true;
        if (z) {
            reverse();
        } else {
            start();
        }
        this.mSuppressSelfPulseRequested = false;
    }

    public String toString() {
        String str = "ValueAnimator@" + Integer.toHexString(hashCode());
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "\n    ");
                m.append(this.mValues[i].toString());
                str = m.toString();
            }
        }
        return str;
    }

    @Override // androidx.core.animation.Animator
    public ValueAnimator setDuration(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("Animators cannot have negative duration: ", j));
        }
        this.mDuration = j;
        return this;
    }

    @Override // androidx.core.animation.Animator
    /* renamed from: clone */
    public ValueAnimator mo890clone() {
        ValueAnimator valueAnimator = (ValueAnimator) super.mo890clone();
        if (this.mUpdateListeners != null) {
            valueAnimator.mUpdateListeners = new ArrayList(this.mUpdateListeners);
        }
        valueAnimator.mSeekFraction = -1.0f;
        valueAnimator.mReversing = false;
        valueAnimator.mInitialized = false;
        valueAnimator.mStarted = false;
        valueAnimator.mRunning = false;
        valueAnimator.mStartListenersCalled = false;
        valueAnimator.mStartTime = -1L;
        valueAnimator.mAnimationEndRequested = false;
        valueAnimator.mLastFrameTime = -1L;
        valueAnimator.mOverallFraction = 0.0f;
        valueAnimator.mCurrentFraction = 0.0f;
        valueAnimator.mSelfPulse = true;
        valueAnimator.mSuppressSelfPulseRequested = false;
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            int length = propertyValuesHolderArr.length;
            valueAnimator.mValues = new PropertyValuesHolder[length];
            valueAnimator.mValuesMap = new HashMap(length);
            for (int i = 0; i < length; i++) {
                PropertyValuesHolder mo895clone = propertyValuesHolderArr[i].mo895clone();
                valueAnimator.mValues[i] = mo895clone;
                valueAnimator.mValuesMap.put(mo895clone.mPropertyName, mo895clone);
            }
        }
        return valueAnimator;
    }

    @Override // androidx.core.animation.Animator
    public void start() {
        start(false);
    }
}
