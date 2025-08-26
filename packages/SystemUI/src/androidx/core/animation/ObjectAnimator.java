package androidx.core.animation;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Property;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.PropertyValuesHolder;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: classes.dex */
public final class ObjectAnimator extends ValueAnimator {
    public final Property mProperty;
    public final String mPropertyName;
    public WeakReference mTarget;

    public ObjectAnimator() {
    }

    public static ObjectAnimator ofFloat(Object obj, String str, float... fArr) {
        ObjectAnimator objectAnimator = new ObjectAnimator(obj, str);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void animateValue(float f) {
        Object target = getTarget();
        if (this.mTarget != null && target == null) {
            cancel();
            return;
        }
        super.animateValue(f);
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].setAnimatedValue(target);
        }
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    /* renamed from: clone */
    public final Animator mo892clone() {
        return (ObjectAnimator) super.mo892clone();
    }

    public final Object getTarget() {
        WeakReference weakReference = this.mTarget;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @Override // androidx.core.animation.ValueAnimator
    public final void initAnimation$1() {
        if (this.mInitialized) {
            return;
        }
        Object target = getTarget();
        if (target != null) {
            int length = this.mValues.length;
            for (int i = 0; i < length; i++) {
                PropertyValuesHolder propertyValuesHolder = this.mValues[i];
                if (propertyValuesHolder.mProperty != null) {
                    try {
                        List list = ((KeyframeSet) propertyValuesHolder.mKeyframes).mKeyframes;
                        int size = list == null ? 0 : list.size();
                        Object obj = null;
                        for (int i2 = 0; i2 < size; i2++) {
                            Keyframe keyframe = (Keyframe) list.get(i2);
                            if (!keyframe.mHasValue || keyframe.mValueWasSetOnStart) {
                                if (obj == null) {
                                    obj = propertyValuesHolder.mProperty.get(target);
                                }
                                keyframe.setValue(obj);
                                keyframe.mValueWasSetOnStart = true;
                            }
                        }
                    } catch (ClassCastException unused) {
                        Log.w("PropertyValuesHolder", "No such property (" + propertyValuesHolder.mProperty.getName() + ") on target object " + target + ". Trying reflection instead");
                        propertyValuesHolder.mProperty = null;
                    }
                } else if (propertyValuesHolder.mProperty == null) {
                    Class<?> cls = target.getClass();
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
                                keyframe2.setValue(propertyValuesHolder.mGetter.invoke(target, null));
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
        super.initAnimation$1();
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

    public final void setTarget(Object obj) {
        if (getTarget() != obj) {
            if (this.mStarted) {
                cancel();
            }
            this.mTarget = obj == null ? null : new WeakReference(obj);
            this.mInitialized = false;
        }
    }

    @Override // androidx.core.animation.ValueAnimator, androidx.core.animation.Animator
    public final void start() {
        AnimationHandler animationHandler = AnimationHandler.getInstance();
        for (int size = animationHandler.mAnimationCallbacks.size() - 1; size >= 0; size--) {
            AnimationHandler.AnimationFrameCallback animationFrameCallback = (AnimationHandler.AnimationFrameCallback) animationHandler.mAnimationCallbacks.get(size);
            if (animationFrameCallback != null && (animationFrameCallback instanceof ObjectAnimator)) {
            }
        }
        start(false);
    }

    @Override // androidx.core.animation.ValueAnimator
    public final String toString() {
        String string = "ObjectAnimator@" + Integer.toHexString(hashCode()) + ", target " + getTarget();
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n    ");
                sbM.append(this.mValues[i].toString());
                string = sbM.toString();
            }
        }
        return string;
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
    public final ValueAnimator mo892clone() {
        return (ObjectAnimator) super.mo892clone();
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
    public final Object mo892clone() {
        return (ObjectAnimator) super.mo892clone();
    }

    /* renamed from: setDuration, reason: collision with other method in class */
    public final void m896setDuration(long j) {
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
