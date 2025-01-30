package androidx.core.animation;

import android.util.Log;
import android.util.Property;
import androidx.core.animation.Keyframe;
import androidx.core.animation.Keyframes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class PropertyValuesHolder implements Cloneable {
    public static final Class[] DOUBLE_VARIANTS;
    public static final Class[] FLOAT_VARIANTS;
    public static final Class[] INTEGER_VARIANTS;
    public static final HashMap sGetterPropertyMap;
    public static final HashMap sSetterPropertyMap;
    public Object mAnimatedValue;
    public TypeEvaluator mEvaluator;
    public Method mGetter;
    public Keyframes mKeyframes;
    public Property mProperty;
    public String mPropertyName;
    public Method mSetter;
    public final Object[] mTmpValueArray;
    public Class mValueType;

    static {
        Class cls = Float.TYPE;
        Class cls2 = Double.TYPE;
        Class cls3 = Integer.TYPE;
        FLOAT_VARIANTS = new Class[]{cls, Float.class, cls2, cls3, Double.class, Integer.class};
        INTEGER_VARIANTS = new Class[]{cls3, Integer.class, cls, cls2, Float.class, Double.class};
        DOUBLE_VARIANTS = new Class[]{cls2, Double.class, cls, cls3, Float.class, Integer.class};
        sSetterPropertyMap = new HashMap();
        sGetterPropertyMap = new HashMap();
    }

    public PropertyValuesHolder(String str) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new Object[1];
        this.mPropertyName = str;
    }

    public static String getMethodName(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        return str + Character.toUpperCase(str2.charAt(0)) + str2.substring(1);
    }

    public void calculateValue(float f) {
        this.mAnimatedValue = this.mKeyframes.getValue(f);
    }

    public Object getAnimatedValue() {
        return this.mAnimatedValue;
    }

    public final Method getPropertyFunction(Class cls, String str, Class cls2) {
        String methodName = getMethodName(str, this.mPropertyName);
        Method method = null;
        if (cls2 == null) {
            try {
                method = cls.getMethod(methodName, null);
            } catch (NoSuchMethodException unused) {
            }
        } else {
            Class<?>[] clsArr = new Class[1];
            for (Class<?> cls3 : cls2.equals(Float.class) ? FLOAT_VARIANTS : cls2.equals(Integer.class) ? INTEGER_VARIANTS : cls2.equals(Double.class) ? DOUBLE_VARIANTS : new Class[]{cls2}) {
                clsArr[0] = cls3;
                try {
                    try {
                        Method method2 = cls.getMethod(methodName, clsArr);
                        this.mValueType = cls3;
                        return method2;
                    } catch (NoSuchMethodException unused2) {
                    }
                } catch (NoSuchMethodException unused3) {
                    method = cls.getDeclaredMethod(methodName, clsArr);
                    method.setAccessible(true);
                    this.mValueType = cls3;
                    return method;
                }
            }
        }
        if (method == null) {
            Log.w("PropertyValuesHolder", "Method " + getMethodName(str, this.mPropertyName) + "() with type " + cls2 + " not found on target class " + cls);
        }
        return method;
    }

    public void setAnimatedValue(Object obj) {
        Property property = this.mProperty;
        if (property != null) {
            property.set(obj, getAnimatedValue());
        }
        if (this.mSetter != null) {
            try {
                this.mTmpValueArray[0] = getAnimatedValue();
                this.mSetter.invoke(obj, this.mTmpValueArray);
            } catch (IllegalAccessException e) {
                Log.e("PropertyValuesHolder", e.toString());
            } catch (InvocationTargetException e2) {
                Log.e("PropertyValuesHolder", e2.toString());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0029, code lost:
    
        if (java.lang.Float.isNaN(r9[0]) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setFloatValues(float... fArr) {
        this.mValueType = Float.TYPE;
        int length = fArr.length;
        Keyframe.FloatKeyframe[] floatKeyframeArr = new Keyframe.FloatKeyframe[Math.max(length, 2)];
        boolean z = true;
        boolean z2 = false;
        if (length == 1) {
            floatKeyframeArr[0] = new Keyframe.FloatKeyframe(0.0f);
            floatKeyframeArr[1] = new Keyframe.FloatKeyframe(1.0f, fArr[0]);
        } else {
            floatKeyframeArr[0] = new Keyframe.FloatKeyframe(0.0f, fArr[0]);
            for (int i = 1; i < length; i++) {
                floatKeyframeArr[i] = new Keyframe.FloatKeyframe(i / (length - 1), fArr[i]);
                if (Float.isNaN(fArr[i])) {
                    z2 = true;
                }
            }
        }
        z = z2;
        if (z) {
            Log.w("Animator", "Bad value (NaN) in float animator");
        }
        this.mKeyframes = new FloatKeyframeSet(floatKeyframeArr);
    }

    public void setIntValues(int... iArr) {
        this.mValueType = Integer.TYPE;
        int length = iArr.length;
        Keyframe.IntKeyframe[] intKeyframeArr = new Keyframe.IntKeyframe[Math.max(length, 2)];
        if (length == 1) {
            intKeyframeArr[0] = new Keyframe.IntKeyframe(0.0f);
            intKeyframeArr[1] = new Keyframe.IntKeyframe(1.0f, iArr[0]);
        } else {
            intKeyframeArr[0] = new Keyframe.IntKeyframe(0.0f, iArr[0]);
            for (int i = 1; i < length; i++) {
                intKeyframeArr[i] = new Keyframe.IntKeyframe(i / (length - 1), iArr[i]);
            }
        }
        this.mKeyframes = new IntKeyframeSet(intKeyframeArr);
    }

    public void setProperty(Property property) {
        this.mProperty = property;
    }

    public final Method setupSetterOrGetter(Class cls, HashMap hashMap, String str, Class cls2) {
        Method method;
        boolean z;
        synchronized (hashMap) {
            HashMap hashMap2 = (HashMap) hashMap.get(cls);
            method = null;
            if (hashMap2 != null) {
                z = hashMap2.containsKey(this.mPropertyName);
                if (z) {
                    method = (Method) hashMap2.get(this.mPropertyName);
                }
            } else {
                z = false;
            }
            if (!z) {
                method = getPropertyFunction(cls, str, cls2);
                if (hashMap2 == null) {
                    hashMap2 = new HashMap();
                    hashMap.put(cls, hashMap2);
                }
                hashMap2.put(this.mPropertyName, method);
            }
        }
        return method;
    }

    public final String toString() {
        return this.mPropertyName + ": " + this.mKeyframes.toString();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FloatPropertyValuesHolder extends PropertyValuesHolder {
        public float mFloatAnimatedValue;
        public Keyframes.FloatKeyframes mFloatKeyframes;
        public FloatProperty mFloatProperty;

        public FloatPropertyValuesHolder(String str, Keyframes.FloatKeyframes floatKeyframes) {
            super(str);
            this.mValueType = Float.TYPE;
            this.mKeyframes = floatKeyframes;
            this.mFloatKeyframes = floatKeyframes;
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final void calculateValue(float f) {
            this.mFloatAnimatedValue = ((FloatKeyframeSet) this.mFloatKeyframes).getFloatValue(f);
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        /* renamed from: clone */
        public final PropertyValuesHolder mo310clone() {
            FloatPropertyValuesHolder floatPropertyValuesHolder = (FloatPropertyValuesHolder) super.mo310clone();
            floatPropertyValuesHolder.mFloatKeyframes = (Keyframes.FloatKeyframes) floatPropertyValuesHolder.mKeyframes;
            return floatPropertyValuesHolder;
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final Object getAnimatedValue() {
            return Float.valueOf(this.mFloatAnimatedValue);
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final void setAnimatedValue(Object obj) {
            FloatProperty floatProperty = this.mFloatProperty;
            if (floatProperty != null) {
                floatProperty.setValue();
                return;
            }
            Property property = this.mProperty;
            if (property != null) {
                property.set(obj, Float.valueOf(this.mFloatAnimatedValue));
                return;
            }
            if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = Float.valueOf(this.mFloatAnimatedValue);
                    this.mSetter.invoke(obj, this.mTmpValueArray);
                } catch (IllegalAccessException e) {
                    Log.e("PropertyValuesHolder", e.toString());
                } catch (InvocationTargetException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final void setFloatValues(float... fArr) {
            super.setFloatValues(fArr);
            this.mFloatKeyframes = (Keyframes.FloatKeyframes) this.mKeyframes;
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final void setProperty(Property property) {
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) property;
            } else {
                this.mProperty = property;
            }
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        /* renamed from: clone */
        public final Object mo310clone() {
            FloatPropertyValuesHolder floatPropertyValuesHolder = (FloatPropertyValuesHolder) super.mo310clone();
            floatPropertyValuesHolder.mFloatKeyframes = (Keyframes.FloatKeyframes) floatPropertyValuesHolder.mKeyframes;
            return floatPropertyValuesHolder;
        }

        public FloatPropertyValuesHolder(Property property, Keyframes.FloatKeyframes floatKeyframes) {
            super(property);
            this.mValueType = Float.TYPE;
            this.mKeyframes = floatKeyframes;
            this.mFloatKeyframes = floatKeyframes;
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) this.mProperty;
            }
        }

        public FloatPropertyValuesHolder(String str, float... fArr) {
            super(str);
            setFloatValues(fArr);
        }

        public FloatPropertyValuesHolder(Property property, float... fArr) {
            super(property);
            setFloatValues(fArr);
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) this.mProperty;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IntPropertyValuesHolder extends PropertyValuesHolder {
        public int mIntAnimatedValue;
        public Keyframes.IntKeyframes mIntKeyframes;
        public IntProperty mIntProperty;

        public IntPropertyValuesHolder(String str, Keyframes.IntKeyframes intKeyframes) {
            super(str);
            this.mValueType = Integer.TYPE;
            this.mKeyframes = intKeyframes;
            this.mIntKeyframes = intKeyframes;
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final void calculateValue(float f) {
            this.mIntAnimatedValue = ((IntKeyframeSet) this.mIntKeyframes).getIntValue(f);
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        /* renamed from: clone */
        public final PropertyValuesHolder mo310clone() {
            IntPropertyValuesHolder intPropertyValuesHolder = (IntPropertyValuesHolder) super.mo310clone();
            intPropertyValuesHolder.mIntKeyframes = (Keyframes.IntKeyframes) intPropertyValuesHolder.mKeyframes;
            return intPropertyValuesHolder;
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final Object getAnimatedValue() {
            return Integer.valueOf(this.mIntAnimatedValue);
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final void setAnimatedValue(Object obj) {
            IntProperty intProperty = this.mIntProperty;
            if (intProperty != null) {
                intProperty.setValue();
                return;
            }
            Property property = this.mProperty;
            if (property != null) {
                property.set(obj, Integer.valueOf(this.mIntAnimatedValue));
                return;
            }
            try {
                this.mTmpValueArray[0] = Integer.valueOf(this.mIntAnimatedValue);
                this.mSetter.invoke(obj, this.mTmpValueArray);
            } catch (IllegalAccessException e) {
                Log.e("PropertyValuesHolder", e.toString());
            } catch (InvocationTargetException e2) {
                Log.e("PropertyValuesHolder", e2.toString());
            }
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final void setIntValues(int... iArr) {
            super.setIntValues(iArr);
            this.mIntKeyframes = (Keyframes.IntKeyframes) this.mKeyframes;
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public final void setProperty(Property property) {
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) property;
            } else {
                this.mProperty = property;
            }
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        /* renamed from: clone */
        public final Object mo310clone() {
            IntPropertyValuesHolder intPropertyValuesHolder = (IntPropertyValuesHolder) super.mo310clone();
            intPropertyValuesHolder.mIntKeyframes = (Keyframes.IntKeyframes) intPropertyValuesHolder.mKeyframes;
            return intPropertyValuesHolder;
        }

        public IntPropertyValuesHolder(Property property, Keyframes.IntKeyframes intKeyframes) {
            super(property);
            this.mValueType = Integer.TYPE;
            this.mKeyframes = intKeyframes;
            this.mIntKeyframes = intKeyframes;
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) this.mProperty;
            }
        }

        public IntPropertyValuesHolder(String str, int... iArr) {
            super(str);
            setIntValues(iArr);
        }

        public IntPropertyValuesHolder(Property property, int... iArr) {
            super(property);
            setIntValues(iArr);
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) this.mProperty;
            }
        }
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public PropertyValuesHolder mo310clone() {
        try {
            PropertyValuesHolder propertyValuesHolder = (PropertyValuesHolder) super.clone();
            propertyValuesHolder.mPropertyName = this.mPropertyName;
            propertyValuesHolder.mProperty = this.mProperty;
            propertyValuesHolder.mKeyframes = this.mKeyframes.mo307clone();
            propertyValuesHolder.mEvaluator = this.mEvaluator;
            return propertyValuesHolder;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public PropertyValuesHolder(Property property) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new Object[1];
        this.mProperty = property;
        if (property != null) {
            this.mPropertyName = property.getName();
        }
    }
}
