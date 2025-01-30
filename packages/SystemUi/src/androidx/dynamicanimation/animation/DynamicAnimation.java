package androidx.dynamicanimation.animation;

import android.animation.ValueAnimator;
import android.util.AndroidRuntimeException;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.dynamicanimation.animation.AnimationHandler;
import androidx.dynamicanimation.animation.AnimationHandler.DurationScaleChangeListener33;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class DynamicAnimation implements AnimationHandler.AnimationFrameCallback {
    public static final C018712 ALPHA;
    public static final C018813 SCROLL_X;
    public static final C018914 SCROLL_Y;
    public final ArrayList mEndListeners;
    public long mLastFrameTime;
    public float mMaxValue;
    public float mMinValue;
    public float mMinVisibleChange;
    public final FloatPropertyCompat mProperty;
    public boolean mRunning;
    public boolean mStartValueIsSet;
    public final Object mTarget;
    public final ArrayList mUpdateListeners;
    public float mValue;
    public float mVelocity;
    public static final C01841 TRANSLATION_X = new ViewProperty("translationX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.1
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((View) obj).getTranslationX();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((View) obj).setTranslationX(f);
        }
    };
    public static final C01912 TRANSLATION_Y = new ViewProperty("translationY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.2
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((View) obj).getTranslationY();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((View) obj).setTranslationY(f);
        }
    };
    public static final C01923 TRANSLATION_Z = new ViewProperty("translationZ") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.3
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api21Impl.getTranslationZ((View) obj);
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setTranslationZ((View) obj, f);
        }
    };
    public static final C01934 SCALE_X = new ViewProperty("scaleX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.4
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((View) obj).getScaleX();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((View) obj).setScaleX(f);
        }
    };
    public static final C01945 SCALE_Y = new ViewProperty("scaleY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.5
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((View) obj).getScaleY();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((View) obj).setScaleY(f);
        }
    };
    public static final C01956 ROTATION = new ViewProperty("rotation") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.6
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((View) obj).getRotation();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((View) obj).setRotation(f);
        }
    };
    public static final C01967 ROTATION_X = new ViewProperty("rotationX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.7
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((View) obj).getRotationX();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((View) obj).setRotationX(f);
        }
    };
    public static final C01978 ROTATION_Y = new ViewProperty("rotationY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.8
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((View) obj).getRotationY();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            ((View) obj).setRotationY(f);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MassState {
        public float mValue;
        public float mVelocity;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnAnimationEndListener {
        void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnAnimationUpdateListener {
        void onAnimationUpdate(float f, float f2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ViewProperty extends FloatPropertyCompat {
        private ViewProperty(String str) {
            super(str);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.dynamicanimation.animation.DynamicAnimation$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.dynamicanimation.animation.DynamicAnimation$2] */
    /* JADX WARN: Type inference failed for: r0v11, types: [androidx.dynamicanimation.animation.DynamicAnimation$12] */
    /* JADX WARN: Type inference failed for: r0v12, types: [androidx.dynamicanimation.animation.DynamicAnimation$13] */
    /* JADX WARN: Type inference failed for: r0v13, types: [androidx.dynamicanimation.animation.DynamicAnimation$14] */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.dynamicanimation.animation.DynamicAnimation$3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.dynamicanimation.animation.DynamicAnimation$4] */
    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.dynamicanimation.animation.DynamicAnimation$5] */
    /* JADX WARN: Type inference failed for: r0v5, types: [androidx.dynamicanimation.animation.DynamicAnimation$6] */
    /* JADX WARN: Type inference failed for: r0v6, types: [androidx.dynamicanimation.animation.DynamicAnimation$7] */
    /* JADX WARN: Type inference failed for: r0v7, types: [androidx.dynamicanimation.animation.DynamicAnimation$8] */
    static {
        new ViewProperty("x") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.9
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((View) obj).getX();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                ((View) obj).setX(f);
            }
        };
        new ViewProperty("y") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.10
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((View) obj).getY();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                ((View) obj).setY(f);
            }
        };
        new ViewProperty("z") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.11
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                return ViewCompat.Api21Impl.getZ((View) obj);
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api21Impl.setZ((View) obj, f);
            }
        };
        ALPHA = new ViewProperty("alpha") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.12
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((View) obj).getAlpha();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                ((View) obj).setAlpha(f);
            }
        };
        SCROLL_X = new ViewProperty("scrollX") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.13
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((View) obj).getScrollX();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                ((View) obj).setScrollX((int) f);
            }
        };
        SCROLL_Y = new ViewProperty("scrollY") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.14
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((View) obj).getScrollY();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                ((View) obj).setScrollY((int) f);
            }
        };
    }

    public DynamicAnimation(final FloatValueHolder floatValueHolder) {
        this.mVelocity = 0.0f;
        this.mValue = Float.MAX_VALUE;
        this.mStartValueIsSet = false;
        this.mRunning = false;
        this.mMaxValue = Float.MAX_VALUE;
        this.mMinValue = -Float.MAX_VALUE;
        this.mLastFrameTime = 0L;
        this.mEndListeners = new ArrayList();
        this.mUpdateListeners = new ArrayList();
        this.mTarget = null;
        this.mProperty = new FloatPropertyCompat(this, "FloatValueHolder") { // from class: androidx.dynamicanimation.animation.DynamicAnimation.15
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return floatValueHolder.mValue;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                floatValueHolder.mValue = f;
            }
        };
        this.mMinVisibleChange = 1.0f;
    }

    public static AnimationHandler getAnimationHandler() {
        ThreadLocal threadLocal = AnimationHandler.sAnimatorHandler;
        if (threadLocal.get() == null) {
            threadLocal.set(new AnimationHandler(new AnimationHandler.FrameCallbackScheduler16()));
        }
        return (AnimationHandler) threadLocal.get();
    }

    public final void addEndListener(OnAnimationEndListener onAnimationEndListener) {
        ArrayList arrayList = this.mEndListeners;
        if (arrayList.contains(onAnimationEndListener)) {
            return;
        }
        arrayList.add(onAnimationEndListener);
    }

    public final void addUpdateListener(OnAnimationUpdateListener onAnimationUpdateListener) {
        if (this.mRunning) {
            throw new UnsupportedOperationException("Error: Update listeners must be added beforethe animation.");
        }
        ArrayList arrayList = this.mUpdateListeners;
        if (arrayList.contains(onAnimationUpdateListener)) {
            return;
        }
        arrayList.add(onAnimationUpdateListener);
    }

    public void cancel() {
        if (!getAnimationHandler().mScheduler.isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be canceled from the same thread as the animation handler");
        }
        if (this.mRunning) {
            endAnimationInternal(true);
        }
    }

    public final void endAnimationInternal(boolean z) {
        ArrayList arrayList;
        int i = 0;
        this.mRunning = false;
        AnimationHandler animationHandler = getAnimationHandler();
        animationHandler.mDelayedCallbackStartTime.remove(this);
        ArrayList arrayList2 = animationHandler.mAnimationCallbacks;
        int indexOf = arrayList2.indexOf(this);
        if (indexOf >= 0) {
            arrayList2.set(indexOf, null);
            animationHandler.mListDirty = true;
        }
        this.mLastFrameTime = 0L;
        this.mStartValueIsSet = false;
        while (true) {
            arrayList = this.mEndListeners;
            if (i >= arrayList.size()) {
                break;
            }
            if (arrayList.get(i) != null) {
                ((OnAnimationEndListener) arrayList.get(i)).onAnimationEnd(this, z, this.mValue, this.mVelocity);
            }
            i++;
        }
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    public final void removeEndListener(OnAnimationEndListener onAnimationEndListener) {
        ArrayList arrayList = this.mEndListeners;
        int indexOf = arrayList.indexOf(onAnimationEndListener);
        if (indexOf >= 0) {
            arrayList.set(indexOf, null);
        }
    }

    public final void setMinimumVisibleChange(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Minimum visible change must be positive.");
        }
        this.mMinVisibleChange = f;
        setValueThreshold(f * 0.75f);
    }

    public final void setPropertyValue(float f) {
        ArrayList arrayList;
        this.mProperty.setValue(this.mTarget, f);
        int i = 0;
        while (true) {
            arrayList = this.mUpdateListeners;
            if (i >= arrayList.size()) {
                break;
            }
            if (arrayList.get(i) != null) {
                ((OnAnimationUpdateListener) arrayList.get(i)).onAnimationUpdate(this.mValue, this.mVelocity);
            }
            i++;
        }
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    public final void setStartValue(float f) {
        this.mValue = f;
        this.mStartValueIsSet = true;
    }

    public abstract void setValueThreshold(float f);

    /* JADX WARN: Type inference failed for: r2v5, types: [android.animation.ValueAnimator$DurationScaleChangeListener, androidx.dynamicanimation.animation.AnimationHandler$DurationScaleChangeListener33$$ExternalSyntheticLambda0] */
    public void start() {
        if (!getAnimationHandler().mScheduler.isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
        }
        boolean z = this.mRunning;
        if (z || z) {
            return;
        }
        this.mRunning = true;
        if (!this.mStartValueIsSet) {
            this.mValue = this.mProperty.getValue(this.mTarget);
        }
        float f = this.mValue;
        if (f > this.mMaxValue || f < this.mMinValue) {
            throw new IllegalArgumentException("Starting value need to be in between min value and max value");
        }
        AnimationHandler animationHandler = getAnimationHandler();
        ArrayList arrayList = animationHandler.mAnimationCallbacks;
        if (arrayList.size() == 0) {
            animationHandler.mScheduler.postFrameCallback(animationHandler.mRunnable);
            animationHandler.mDurationScale = ValueAnimator.getDurationScale();
            if (animationHandler.mDurationScaleChangeListener == null) {
                animationHandler.mDurationScaleChangeListener = animationHandler.new DurationScaleChangeListener33();
            }
            final AnimationHandler.DurationScaleChangeListener33 durationScaleChangeListener33 = animationHandler.mDurationScaleChangeListener;
            if (durationScaleChangeListener33.mListener == null) {
                ?? r2 = new ValueAnimator.DurationScaleChangeListener() { // from class: androidx.dynamicanimation.animation.AnimationHandler$DurationScaleChangeListener33$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.DurationScaleChangeListener
                    public final void onChanged(float f2) {
                        AnimationHandler.this.mDurationScale = f2;
                    }
                };
                durationScaleChangeListener33.mListener = r2;
                ValueAnimator.registerDurationScaleChangeListener(r2);
            }
        }
        if (arrayList.contains(this)) {
            return;
        }
        arrayList.add(this);
    }

    public abstract boolean updateValueAndVelocity(long j);

    public <K> DynamicAnimation(K k, FloatPropertyCompat floatPropertyCompat) {
        this.mVelocity = 0.0f;
        this.mValue = Float.MAX_VALUE;
        this.mStartValueIsSet = false;
        this.mRunning = false;
        this.mMaxValue = Float.MAX_VALUE;
        this.mMinValue = -3.4028235E38f;
        this.mLastFrameTime = 0L;
        this.mEndListeners = new ArrayList();
        this.mUpdateListeners = new ArrayList();
        this.mTarget = k;
        this.mProperty = floatPropertyCompat;
        if (floatPropertyCompat != ROTATION && floatPropertyCompat != ROTATION_X && floatPropertyCompat != ROTATION_Y) {
            if (floatPropertyCompat == ALPHA) {
                this.mMinVisibleChange = 0.00390625f;
                return;
            } else if (floatPropertyCompat != SCALE_X && floatPropertyCompat != SCALE_Y) {
                this.mMinVisibleChange = 1.0f;
                return;
            } else {
                this.mMinVisibleChange = 0.002f;
                return;
            }
        }
        this.mMinVisibleChange = 0.1f;
    }
}
