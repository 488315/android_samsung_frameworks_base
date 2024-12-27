package com.android.systemui.qs;

import android.util.FloatProperty;
import android.util.MathUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.List;

public final class TouchAnimator {
    public static final AnonymousClass1 POSITION = new FloatProperty(SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION) { // from class: com.android.systemui.qs.TouchAnimator.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((TouchAnimator) obj).mLastT);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            ((TouchAnimator) obj).setPosition(f);
        }
    };
    public final Interpolator mInterpolator;
    public final KeyframeSet[] mKeyframeSets;
    public float mLastT;
    public final Listener mListener;
    public final float mSpan;
    public final float mStartDelay;
    public final Object[] mTargets;

    public final class Builder {
        public float mEndDelay;
        public Interpolator mInterpolator;
        public Listener mListener;
        public float mStartDelay;
        public final List mTargets = new ArrayList();
        public final List mValues = new ArrayList();

        public final void addFloat(Object obj, String str, float... fArr) {
            Property of;
            Class cls = Float.TYPE;
            if (obj instanceof View) {
                switch (str) {
                    case "translationX":
                        of = View.TRANSLATION_X;
                        break;
                    case "translationY":
                        of = View.TRANSLATION_Y;
                        break;
                    case "translationZ":
                        of = View.TRANSLATION_Z;
                        break;
                    case "scaleX":
                        of = View.SCALE_X;
                        break;
                    case "scaleY":
                        of = View.SCALE_Y;
                        break;
                    case "rotation":
                        of = View.ROTATION;
                        break;
                    case "x":
                        of = View.X;
                        break;
                    case "y":
                        of = View.Y;
                        break;
                    case "alpha":
                        of = View.ALPHA;
                        break;
                }
                FloatKeyframeSet floatKeyframeSet = new FloatKeyframeSet(of, fArr);
                ((ArrayList) this.mTargets).add(obj);
                ((ArrayList) this.mValues).add(floatKeyframeSet);
            }
            of = ((obj instanceof TouchAnimator) && SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION.equals(str)) ? TouchAnimator.POSITION : Property.of(obj.getClass(), cls, str);
            FloatKeyframeSet floatKeyframeSet2 = new FloatKeyframeSet(of, fArr);
            ((ArrayList) this.mTargets).add(obj);
            ((ArrayList) this.mValues).add(floatKeyframeSet2);
        }

        public final TouchAnimator build() {
            ArrayList arrayList = (ArrayList) this.mTargets;
            Object[] array = arrayList.toArray(new Object[arrayList.size()]);
            ArrayList arrayList2 = (ArrayList) this.mValues;
            return new TouchAnimator(array, (KeyframeSet[]) arrayList2.toArray(new KeyframeSet[arrayList2.size()]), this.mStartDelay, this.mEndDelay, this.mInterpolator, this.mListener, 0);
        }
    }

    public final class FloatKeyframeSet extends KeyframeSet {
        public final Property mProperty;
        public final float[] mValues;

        public FloatKeyframeSet(Property<Object, Float> property, float[] fArr) {
            super(fArr.length);
            this.mProperty = property;
            this.mValues = fArr;
        }

        @Override // com.android.systemui.qs.TouchAnimator.KeyframeSet
        public final void interpolate(int i, float f, Object obj) {
            float[] fArr = this.mValues;
            float f2 = fArr[i - 1];
            this.mProperty.set(obj, Float.valueOf(((fArr[i] - f2) * f) + f2));
        }
    }

    public abstract class KeyframeSet {
        public final float mFrameWidth;
        public final int mSize;

        public KeyframeSet(int i) {
            this.mSize = i;
            this.mFrameWidth = 1.0f / (i - 1);
        }

        public abstract void interpolate(int i, float f, Object obj);
    }

    public interface Listener {
        void onAnimationAtEnd();

        void onAnimationAtStart();

        void onAnimationStarted();
    }

    public /* synthetic */ TouchAnimator(Object[] objArr, KeyframeSet[] keyframeSetArr, float f, float f2, Interpolator interpolator, Listener listener, int i) {
        this(objArr, keyframeSetArr, f, f2, interpolator, listener);
    }

    public final void setPosition(float f) {
        if (Float.isNaN(f)) {
            return;
        }
        float constrain = MathUtils.constrain((f - this.mStartDelay) / this.mSpan, 0.0f, 1.0f);
        Interpolator interpolator = this.mInterpolator;
        if (interpolator != null) {
            constrain = interpolator.getInterpolation(constrain);
        }
        float f2 = this.mLastT;
        if (constrain == f2) {
            return;
        }
        Listener listener = this.mListener;
        if (listener != null) {
            if (constrain == 1.0f) {
                listener.onAnimationAtEnd();
            } else if (constrain == 0.0f) {
                listener.onAnimationAtStart();
            } else if (f2 <= 0.0f || f2 == 1.0f) {
                listener.onAnimationStarted();
            }
            this.mLastT = constrain;
        }
        int i = 0;
        while (true) {
            Object[] objArr = this.mTargets;
            if (i >= objArr.length) {
                return;
            }
            KeyframeSet keyframeSet = this.mKeyframeSets[i];
            Object obj = objArr[i];
            float f3 = keyframeSet.mFrameWidth;
            keyframeSet.interpolate(MathUtils.constrain((int) Math.ceil(constrain / f3), 1, keyframeSet.mSize - 1), (constrain - ((r4 - 1) * f3)) / f3, obj);
            i++;
        }
    }

    private TouchAnimator(Object[] objArr, KeyframeSet[] keyframeSetArr, float f, float f2, Interpolator interpolator, Listener listener) {
        this.mLastT = -1.0f;
        this.mTargets = objArr;
        this.mKeyframeSets = keyframeSetArr;
        this.mStartDelay = f;
        this.mSpan = (1.0f - f2) - f;
        this.mInterpolator = interpolator;
        this.mListener = listener;
    }
}
