package androidx.core.animation;

import android.os.Looper;
import android.os.Trace;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.animation.AnimationUtils;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.Animator;
import androidx.core.animation.PropertyValuesHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ValueAnimator extends Animator implements AnimationHandler.AnimationFrameCallback {
    public static final AccelerateDecelerateInterpolator sDefaultInterpolator = new AccelerateDecelerateInterpolator();
    public long mPauseTime;
    public boolean mReversing;
    public PropertyValuesHolder[] mValues;
    public HashMap mValuesMap;
    public long mStartTime = -1;
    public float mSeekFraction = -1.0f;
    public float mOverallFraction = 0.0f;
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
        valueAnimator.setIntValues(iArr);
        return valueAnimator;
    }

    public void animateValue(float f) {
        float interpolation = this.mInterpolator.getInterpolation(f);
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
            Iterator it = ((ArrayList) this.mListeners.clone()).iterator();
            while (it.hasNext()) {
                ((Animator.AnimatorListener) it.next()).onAnimationCancel();
            }
        }
        endAnimation();
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b2, code lost:
    
        if (r13 != false) goto L59;
     */
    @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean doAnimationFrame(long j) {
        long j2;
        if (this.mStartTime < 0) {
            if (this.mReversing) {
                j2 = j;
            } else {
                float f = this.mStartDelay;
                float f2 = this.mDurationScale;
                if (f2 < 0.0f) {
                    f2 = 1.0f;
                }
                j2 = ((long) (f2 * f)) + j;
            }
            this.mStartTime = j2;
        }
        boolean z = true;
        boolean z2 = false;
        if (!this.mRunning) {
            if (this.mStartTime > j && this.mSeekFraction == -1.0f) {
                return false;
            }
            this.mRunning = true;
            startAnimation();
        }
        if (this.mLastFrameTime < 0) {
            float f3 = this.mSeekFraction;
            if (f3 >= 0.0f) {
                float f4 = this.mDuration;
                float f5 = this.mDurationScale;
                if (f5 < 0.0f) {
                    f5 = 1.0f;
                }
                this.mStartTime = j - ((long) (((long) (f5 * f4)) * f3));
                this.mSeekFraction = -1.0f;
            }
        }
        this.mLastFrameTime = j;
        long max = Math.max(j, this.mStartTime);
        if (this.mRunning) {
            float f6 = this.mDuration;
            float f7 = this.mDurationScale;
            if (f7 < 0.0f) {
                f7 = 1.0f;
            }
            long j3 = (long) (f7 * f6);
            float f8 = j3 > 0 ? (max - this.mStartTime) / j3 : 1.0f;
            boolean z3 = ((int) f8) > ((int) this.mOverallFraction);
            boolean z4 = f8 >= ((float) 1);
            if (j3 != 0) {
                if (z3 && !z4) {
                    ArrayList arrayList = this.mListeners;
                    if (arrayList != null) {
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            ((Animator.AnimatorListener) this.mListeners.get(i)).onAnimationRepeat();
                        }
                    }
                }
                z = false;
            }
            float clampFraction = clampFraction(f8);
            this.mOverallFraction = clampFraction;
            animateValue(getCurrentIterationFraction(clampFraction, this.mReversing));
            z2 = z;
        }
        if (z2) {
            endAnimation();
        }
        return z2;
    }

    @Override // androidx.core.animation.Animator
    public final void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (!this.mRunning) {
            startAnimation();
            this.mStarted = true;
        } else if (!this.mInitialized) {
            initAnimation();
        }
        animateValue(this.mReversing ? 0.0f : 1.0f);
        endAnimation();
    }

    public final void endAnimation() {
        ArrayList arrayList;
        AnimationHandler animationHandler;
        ArrayList arrayList2;
        int indexOf;
        if (this.mAnimationEndRequested) {
            return;
        }
        if (this.mSelfPulse && (indexOf = (arrayList2 = (animationHandler = AnimationHandler.getInstance()).mAnimationCallbacks).indexOf(this)) >= 0) {
            arrayList2.set(indexOf, null);
            animationHandler.mListDirty = true;
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
            ArrayList arrayList3 = (ArrayList) arrayList.clone();
            int size = arrayList3.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList3.get(i)).onAnimationEnd(this);
            }
        }
        this.mReversing = false;
        Trace.endSection();
    }

    public final Object getAnimatedValue() {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length <= 0) {
            return null;
        }
        return propertyValuesHolderArr[0].getAnimatedValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002d, code lost:
    
        r7 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float getCurrentIterationFraction(float f, boolean z) {
        float clampFraction = clampFraction(f);
        float clampFraction2 = clampFraction(clampFraction);
        double d = clampFraction2;
        double floor = Math.floor(d);
        if (d == floor && clampFraction2 > 0.0f) {
            floor -= 1.0d;
        }
        int i = (int) floor;
        float f2 = clampFraction - i;
        if (i > 0 && this.mRepeatMode == 2 && i < 1) {
            z = z ? false : false;
        }
        return z ? 1.0f - f2 : f2;
    }

    @Override // androidx.core.animation.Animator
    public final long getDuration() {
        return this.mDuration;
    }

    public String getNameForTrace() {
        return "animator";
    }

    @Override // androidx.core.animation.Animator
    public final long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // androidx.core.animation.Animator
    public final long getTotalDuration() {
        return (this.mDuration * 1) + this.mStartDelay;
    }

    public void initAnimation() {
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
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationStart$1();
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
        initAnimation();
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

    public void setIntValues(int... iArr) {
        if (iArr.length == 0) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length == 0) {
            Class[] clsArr = PropertyValuesHolder.FLOAT_VARIANTS;
            setValues(new PropertyValuesHolder.IntPropertyValuesHolder("", iArr));
        } else {
            propertyValuesHolderArr[0].setIntValues(iArr);
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
        initAnimation();
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
            startAnimation();
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

    public final void startAnimation() {
        Trace.beginSection(getNameForTrace());
        this.mAnimationEndRequested = false;
        initAnimation();
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
                StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "\n    ");
                m2m.append(this.mValues[i].toString());
                str = m2m.toString();
            }
        }
        return str;
    }

    @Override // androidx.core.animation.Animator
    public ValueAnimator setDuration(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("Animators cannot have negative duration: ", j));
        }
        this.mDuration = j;
        return this;
    }

    @Override // androidx.core.animation.Animator
    /* renamed from: clone */
    public ValueAnimator mo305clone() {
        ValueAnimator valueAnimator = (ValueAnimator) super.mo305clone();
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
        valueAnimator.mPauseTime = -1L;
        valueAnimator.mLastFrameTime = -1L;
        valueAnimator.mOverallFraction = 0.0f;
        valueAnimator.mSelfPulse = true;
        valueAnimator.mSuppressSelfPulseRequested = false;
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            int length = propertyValuesHolderArr.length;
            valueAnimator.mValues = new PropertyValuesHolder[length];
            valueAnimator.mValuesMap = new HashMap(length);
            for (int i = 0; i < length; i++) {
                PropertyValuesHolder mo310clone = propertyValuesHolderArr[i].mo310clone();
                valueAnimator.mValues[i] = mo310clone;
                valueAnimator.mValuesMap.put(mo310clone.mPropertyName, mo310clone);
            }
        }
        return valueAnimator;
    }

    @Override // androidx.core.animation.Animator
    public void start() {
        start(false);
    }
}
