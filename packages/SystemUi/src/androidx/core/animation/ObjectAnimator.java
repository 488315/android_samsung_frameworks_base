package androidx.core.animation;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Property;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.PropertyValuesHolder;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ObjectAnimator extends ValueAnimator {
    public Property mProperty;
    public String mPropertyName;
    public WeakReference mTarget;

    public ObjectAnimator() {
    }

    public static ObjectAnimator ofFloat(Object obj, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, "translationX");
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void animateValue(float f) {
        WeakReference weakReference = this.mTarget;
        Object obj = weakReference == null ? null : weakReference.get();
        if (this.mTarget != null && obj == null) {
            cancel();
            return;
        }
        super.animateValue(f);
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].setAnimatedValue(obj);
        }
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    /* renamed from: clone */
    public final Animator mo305clone() {
        return (ObjectAnimator) super.mo305clone();
    }

    @Override // androidx.core.animation.ValueAnimator
    public final String getNameForTrace() {
        StringBuilder sb = new StringBuilder("animator:");
        String str = this.mPropertyName;
        if (str == null) {
            Property property = this.mProperty;
            if (property != null) {
                str = property.getName();
            } else {
                PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
                String str2 = null;
                if (propertyValuesHolderArr != null && propertyValuesHolderArr.length > 0) {
                    int i = 0;
                    while (i < this.mValues.length) {
                        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(i == 0 ? "" : AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, ","));
                        m18m.append(this.mValues[i].mPropertyName);
                        str2 = m18m.toString();
                        i++;
                    }
                }
                str = str2;
            }
        }
        sb.append(str);
        return sb.toString();
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void initAnimation() {
        if (this.mInitialized) {
            return;
        }
        WeakReference weakReference = this.mTarget;
        Object obj = weakReference == null ? null : weakReference.get();
        if (obj != null) {
            int length = this.mValues.length;
            for (int i = 0; i < length; i++) {
                PropertyValuesHolder propertyValuesHolder = this.mValues[i];
                if (propertyValuesHolder.mProperty != null) {
                    try {
                        List list = ((KeyframeSet) propertyValuesHolder.mKeyframes).mKeyframes;
                        int size = list == null ? 0 : list.size();
                        Object obj2 = null;
                        for (int i2 = 0; i2 < size; i2++) {
                            Keyframe keyframe = (Keyframe) list.get(i2);
                            if (!keyframe.mHasValue || keyframe.mValueWasSetOnStart) {
                                if (obj2 == null) {
                                    obj2 = propertyValuesHolder.mProperty.get(obj);
                                }
                                keyframe.setValue(obj2);
                                keyframe.mValueWasSetOnStart = true;
                            }
                        }
                    } catch (ClassCastException unused) {
                        Log.w("PropertyValuesHolder", "No such property (" + propertyValuesHolder.mProperty.getName() + ") on target object " + obj + ". Trying reflection instead");
                        propertyValuesHolder.mProperty = null;
                    }
                }
                if (propertyValuesHolder.mProperty == null) {
                    Class<?> cls = obj.getClass();
                    if (propertyValuesHolder.mSetter == null) {
                        propertyValuesHolder.mSetter = propertyValuesHolder.setupSetterOrGetter(cls, PropertyValuesHolder.sSetterPropertyMap, "set", propertyValuesHolder.mValueType);
                    }
                    List list2 = ((KeyframeSet) propertyValuesHolder.mKeyframes).mKeyframes;
                    int size2 = list2 == null ? 0 : list2.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        Keyframe keyframe2 = (Keyframe) list2.get(i3);
                        if (!keyframe2.mHasValue || keyframe2.mValueWasSetOnStart) {
                            if (propertyValuesHolder.mGetter == null) {
                                Method method = propertyValuesHolder.setupSetterOrGetter(cls, PropertyValuesHolder.sGetterPropertyMap, "get", null);
                                propertyValuesHolder.mGetter = method;
                                if (method == null) {
                                    break;
                                }
                            }
                            try {
                                keyframe2.setValue(propertyValuesHolder.mGetter.invoke(obj, new Object[0]));
                                keyframe2.mValueWasSetOnStart = true;
                            } catch (IllegalAccessException e) {
                                Log.e("PropertyValuesHolder", e.toString());
                            } catch (InvocationTargetException e2) {
                                Log.e("PropertyValuesHolder", e2.toString());
                            }
                        }
                    }
                }
            }
        }
        super.initAnimation();
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final Animator setDuration(long j) {
        super.setDuration(j);
        return this;
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void setFloatValues(float... fArr) {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null && propertyValuesHolderArr.length != 0) {
            super.setFloatValues(fArr);
            return;
        }
        Property property = this.mProperty;
        if (property != null) {
            Class[] clsArr = PropertyValuesHolder.FLOAT_VARIANTS;
            setValues(new PropertyValuesHolder.FloatPropertyValuesHolder(property, fArr));
        } else {
            String str = this.mPropertyName;
            Class[] clsArr2 = PropertyValuesHolder.FLOAT_VARIANTS;
            setValues(new PropertyValuesHolder.FloatPropertyValuesHolder(str, fArr));
        }
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void setIntValues(int... iArr) {
        throw null;
    }

    public final void setTarget(Object obj) {
        WeakReference weakReference = this.mTarget;
        if ((weakReference == null ? null : weakReference.get()) != obj) {
            if (this.mStarted) {
                cancel();
            }
            this.mTarget = obj != null ? new WeakReference(obj) : null;
            this.mInitialized = false;
        }
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final void start() {
        ArrayList arrayList = AnimationHandler.getInstance().mAnimationCallbacks;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            AnimationHandler.AnimationFrameCallback animationFrameCallback = (AnimationHandler.AnimationFrameCallback) arrayList.get(size);
            if (animationFrameCallback != null && (animationFrameCallback instanceof ObjectAnimator)) {
            }
        }
        start(false);
    }

    @Override // androidx.core.animation.ValueAnimator
    public final String toString() {
        StringBuilder sb = new StringBuilder("ObjectAnimator@");
        sb.append(Integer.toHexString(hashCode()));
        sb.append(", target ");
        WeakReference weakReference = this.mTarget;
        sb.append(weakReference == null ? null : weakReference.get());
        String sb2 = sb.toString();
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                StringBuilder m2m = AbstractC0000x2c234b15.m2m(sb2, "\n    ");
                m2m.append(this.mValues[i].toString());
                sb2 = m2m.toString();
            }
        }
        return sb2;
    }

    private ObjectAnimator(Object obj, String str) {
        setTarget(obj);
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            PropertyValuesHolder propertyValuesHolder = propertyValuesHolderArr[0];
            String str2 = propertyValuesHolder.mPropertyName;
            propertyValuesHolder.mPropertyName = str;
            this.mValuesMap.remove(str2);
            this.mValuesMap.put(str, propertyValuesHolder);
        }
        this.mPropertyName = str;
        this.mInitialized = false;
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    /* renamed from: clone */
    public final ValueAnimator mo305clone() {
        return (ObjectAnimator) super.mo305clone();
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final ValueAnimator setDuration(long j) {
        super.setDuration(j);
        return this;
    }

    public static ObjectAnimator ofFloat(Object obj, Property property, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, (Property<Object, ?>) property);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    /* renamed from: clone */
    public final Object mo305clone() {
        return (ObjectAnimator) super.mo305clone();
    }

    /* renamed from: setDuration, reason: collision with other method in class */
    public final void m309setDuration(long j) {
        super.setDuration(j);
    }

    private <T> ObjectAnimator(T t, Property<T, ?> property) {
        setTarget(t);
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            PropertyValuesHolder propertyValuesHolder = propertyValuesHolderArr[0];
            String str = propertyValuesHolder.mPropertyName;
            propertyValuesHolder.setProperty(property);
            this.mValuesMap.remove(str);
            this.mValuesMap.put(this.mPropertyName, propertyValuesHolder);
        }
        if (this.mProperty != null) {
            this.mPropertyName = property.getName();
        }
        this.mProperty = property;
        this.mInitialized = false;
    }
}
