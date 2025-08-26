package android.animation;

import android.animation.Keyframes;
import android.animation.PathKeyframes;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.Log;
import android.util.PathParser;
import android.util.Property;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class PropertyValuesHolder implements Cloneable {
    private Object mAnimatedValue;
    private TypeConverter mConverter;
    private TypeEvaluator mEvaluator;
    private Method mGetter;
    Keyframes mKeyframes;
    protected Property mProperty;
    String mPropertyName;
    Method mSetter;
    final Object[] mTmpValueArray;
    Class mValueType;
    private static final TypeEvaluator sIntEvaluator = new IntEvaluator();
    private static final TypeEvaluator sFloatEvaluator = new FloatEvaluator();
    private static Class[] FLOAT_VARIANTS = {Float.TYPE, Float.class, Double.TYPE, Integer.TYPE, Double.class, Integer.class};
    private static Class[] INTEGER_VARIANTS = {Integer.TYPE, Integer.class, Float.TYPE, Double.TYPE, Float.class, Double.class};
    private static Class[] DOUBLE_VARIANTS = {Double.TYPE, Double.class, Float.TYPE, Integer.TYPE, Float.class, Integer.class};
    private static final HashMap<Class, HashMap<String, Method>> sSetterPropertyMap = new HashMap<>();
    private static final HashMap<Class, HashMap<String, Method>> sGetterPropertyMap = new HashMap<>();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallFloatMethod(Object obj, long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallFourFloatMethod(Object obj, long j, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallFourIntMethod(Object obj, long j, int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallIntMethod(Object obj, long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallMultipleFloatMethod(Object obj, long j, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallMultipleIntMethod(Object obj, long j, int[] iArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallTwoFloatMethod(Object obj, long j, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallTwoIntMethod(Object obj, long j, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetFloatMethod(Class cls, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetIntMethod(Class cls, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetMultipleFloatMethod(Class cls, String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetMultipleIntMethod(Class cls, String str, int i);

    private PropertyValuesHolder(String str) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new Object[1];
        this.mPropertyName = str;
    }

    private PropertyValuesHolder(Property property) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new Object[1];
        this.mProperty = property;
        if (property != null) {
            this.mPropertyName = property.getName();
        }
    }

    public static PropertyValuesHolder ofInt(String str, int... iArr) {
        return new IntPropertyValuesHolder(str, iArr);
    }

    public static PropertyValuesHolder ofInt(Property<?, Integer> property, int... iArr) {
        return new IntPropertyValuesHolder(property, iArr);
    }

    public static PropertyValuesHolder ofMultiInt(String str, int[][] iArr) {
        if (iArr.length < 2) {
            throw new IllegalArgumentException("At least 2 values must be supplied");
        }
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int[] iArr2 = iArr[i2];
            if (iArr2 == null) {
                throw new IllegalArgumentException("values must not be null");
            }
            int length = iArr2.length;
            if (i2 == 0) {
                i = length;
            } else if (length != i) {
                throw new IllegalArgumentException("Values must all have the same length");
            }
        }
        return new MultiIntValuesHolder(str, (TypeConverter) null, new IntArrayEvaluator(new int[i]), iArr);
    }

    public static PropertyValuesHolder ofMultiInt(String str, Path path) {
        return new MultiIntValuesHolder(str, new PointFToIntArray(), (TypeEvaluator) null, KeyframeSet.ofPath(path));
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofMultiInt(String str, TypeConverter<V, int[]> typeConverter, TypeEvaluator<V> typeEvaluator, V... vArr) {
        return new MultiIntValuesHolder(str, typeConverter, typeEvaluator, vArr);
    }

    public static <T> PropertyValuesHolder ofMultiInt(String str, TypeConverter<T, int[]> typeConverter, TypeEvaluator<T> typeEvaluator, Keyframe... keyframeArr) {
        return new MultiIntValuesHolder(str, typeConverter, typeEvaluator, KeyframeSet.ofKeyframe(keyframeArr));
    }

    public static PropertyValuesHolder ofFloat(String str, float... fArr) {
        return new FloatPropertyValuesHolder(str, fArr);
    }

    public static PropertyValuesHolder ofFloat(Property<?, Float> property, float... fArr) {
        return new FloatPropertyValuesHolder(property, fArr);
    }

    public static PropertyValuesHolder ofMultiFloat(String str, float[][] fArr) {
        if (fArr.length < 2) {
            throw new IllegalArgumentException("At least 2 values must be supplied");
        }
        int i = 0;
        for (int i2 = 0; i2 < fArr.length; i2++) {
            float[] fArr2 = fArr[i2];
            if (fArr2 == null) {
                throw new IllegalArgumentException("values must not be null");
            }
            int length = fArr2.length;
            if (i2 == 0) {
                i = length;
            } else if (length != i) {
                throw new IllegalArgumentException("Values must all have the same length");
            }
        }
        return new MultiFloatValuesHolder(str, (TypeConverter) null, new FloatArrayEvaluator(new float[i]), fArr);
    }

    public static PropertyValuesHolder ofMultiFloat(String str, Path path) {
        return new MultiFloatValuesHolder(str, new PointFToFloatArray(), (TypeEvaluator) null, KeyframeSet.ofPath(path));
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofMultiFloat(String str, TypeConverter<V, float[]> typeConverter, TypeEvaluator<V> typeEvaluator, V... vArr) {
        return new MultiFloatValuesHolder(str, typeConverter, typeEvaluator, vArr);
    }

    public static <T> PropertyValuesHolder ofMultiFloat(String str, TypeConverter<T, float[]> typeConverter, TypeEvaluator<T> typeEvaluator, Keyframe... keyframeArr) {
        return new MultiFloatValuesHolder(str, typeConverter, typeEvaluator, KeyframeSet.ofKeyframe(keyframeArr));
    }

    public static PropertyValuesHolder ofObject(String str, TypeEvaluator typeEvaluator, Object... objArr) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(str);
        propertyValuesHolder.setObjectValues(objArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    public static PropertyValuesHolder ofObject(String str, TypeConverter<PointF, ?> typeConverter, Path path) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(str);
        propertyValuesHolder.mKeyframes = KeyframeSet.ofPath(path);
        propertyValuesHolder.mValueType = PointF.class;
        propertyValuesHolder.setConverter(typeConverter);
        return propertyValuesHolder;
    }

    @SafeVarargs
    public static <V> PropertyValuesHolder ofObject(Property property, TypeEvaluator<V> typeEvaluator, V... vArr) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(property);
        propertyValuesHolder.setObjectValues(vArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    @SafeVarargs
    public static <T, V> PropertyValuesHolder ofObject(Property<?, V> property, TypeConverter<T, V> typeConverter, TypeEvaluator<T> typeEvaluator, T... tArr) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(property);
        propertyValuesHolder.setConverter(typeConverter);
        propertyValuesHolder.setObjectValues(tArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    public static <V> PropertyValuesHolder ofObject(Property<?, V> property, TypeConverter<PointF, V> typeConverter, Path path) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(property);
        propertyValuesHolder.mKeyframes = KeyframeSet.ofPath(path);
        propertyValuesHolder.mValueType = PointF.class;
        propertyValuesHolder.setConverter(typeConverter);
        return propertyValuesHolder;
    }

    public static PropertyValuesHolder ofKeyframe(String str, Keyframe... keyframeArr) {
        return ofKeyframes(str, KeyframeSet.ofKeyframe(keyframeArr));
    }

    public static PropertyValuesHolder ofKeyframe(Property property, Keyframe... keyframeArr) {
        return ofKeyframes(property, KeyframeSet.ofKeyframe(keyframeArr));
    }

    static PropertyValuesHolder ofKeyframes(String str, Keyframes keyframes) {
        if (keyframes instanceof Keyframes.IntKeyframes) {
            return new IntPropertyValuesHolder(str, (Keyframes.IntKeyframes) keyframes);
        }
        if (keyframes instanceof Keyframes.FloatKeyframes) {
            return new FloatPropertyValuesHolder(str, (Keyframes.FloatKeyframes) keyframes);
        }
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(str);
        propertyValuesHolder.mKeyframes = keyframes;
        propertyValuesHolder.mValueType = keyframes.getType();
        return propertyValuesHolder;
    }

    static PropertyValuesHolder ofKeyframes(Property property, Keyframes keyframes) {
        if (keyframes instanceof Keyframes.IntKeyframes) {
            return new IntPropertyValuesHolder(property, (Keyframes.IntKeyframes) keyframes);
        }
        if (keyframes instanceof Keyframes.FloatKeyframes) {
            return new FloatPropertyValuesHolder(property, (Keyframes.FloatKeyframes) keyframes);
        }
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(property);
        propertyValuesHolder.mKeyframes = keyframes;
        propertyValuesHolder.mValueType = keyframes.getType();
        return propertyValuesHolder;
    }

    public void setIntValues(int... iArr) {
        this.mValueType = Integer.TYPE;
        this.mKeyframes = KeyframeSet.ofInt(iArr);
    }

    public void setFloatValues(float... fArr) {
        this.mValueType = Float.TYPE;
        this.mKeyframes = KeyframeSet.ofFloat(fArr);
    }

    public void setKeyframes(Keyframe... keyframeArr) {
        int length = keyframeArr.length;
        Keyframe[] keyframeArr2 = new Keyframe[Math.max(length, 2)];
        this.mValueType = keyframeArr[0].getType();
        for (int i = 0; i < length; i++) {
            keyframeArr2[i] = keyframeArr[i];
        }
        this.mKeyframes = new KeyframeSet(keyframeArr2);
    }

    public void setObjectValues(Object... objArr) {
        this.mValueType = objArr[0].getClass();
        KeyframeSet keyframeSetOfObject = KeyframeSet.ofObject(objArr);
        this.mKeyframes = keyframeSetOfObject;
        TypeEvaluator typeEvaluator = this.mEvaluator;
        if (typeEvaluator != null) {
            keyframeSetOfObject.setEvaluator(typeEvaluator);
        }
    }

    public void setConverter(TypeConverter typeConverter) {
        this.mConverter = typeConverter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Method getPropertyFunction(Class cls, String str, Class cls2) throws NoSuchMethodException, SecurityException {
        Class[] clsArr;
        String methodName = getMethodName(str, this.mPropertyName);
        Method method = null;
        if (cls2 == null) {
            try {
                method = cls.getMethod(methodName, null);
            } catch (NoSuchMethodException unused) {
            }
        } else {
            Class[] clsArr2 = new Class[1];
            if (cls2.equals(Float.class)) {
                clsArr = FLOAT_VARIANTS;
            } else if (cls2.equals(Integer.class)) {
                clsArr = INTEGER_VARIANTS;
            } else if (cls2.equals(Double.class)) {
                clsArr = DOUBLE_VARIANTS;
            } else {
                clsArr = new Class[]{cls2};
            }
            for (Class cls3 : clsArr) {
                clsArr2[0] = cls3;
                try {
                    method = cls.getMethod(methodName, clsArr2);
                    if (this.mConverter == null) {
                        this.mValueType = cls3;
                    }
                    return method;
                } catch (NoSuchMethodException unused2) {
                }
            }
        }
        if (method == null) {
            Log.w("PropertyValuesHolder", "Method " + getMethodName(str, this.mPropertyName) + "() with type " + cls2 + " not found on target class " + cls);
        }
        return method;
    }

    private Method setupSetterOrGetter(Class cls, HashMap<Class, HashMap<String, Method>> map, String str, Class cls2) {
        Method propertyFunction;
        boolean zContainsKey;
        synchronized (map) {
            HashMap<String, Method> map2 = map.get(cls);
            propertyFunction = null;
            if (map2 != null) {
                zContainsKey = map2.containsKey(this.mPropertyName);
                if (zContainsKey) {
                    propertyFunction = map2.get(this.mPropertyName);
                }
            } else {
                zContainsKey = false;
            }
            if (!zContainsKey) {
                propertyFunction = getPropertyFunction(cls, str, cls2);
                if (map2 == null) {
                    map2 = new HashMap<>();
                    map.put(cls, map2);
                }
                map2.put(this.mPropertyName, propertyFunction);
            }
        }
        return propertyFunction;
    }

    void setupSetter(Class cls) {
        TypeConverter typeConverter = this.mConverter;
        this.mSetter = setupSetterOrGetter(cls, sSetterPropertyMap, "set", typeConverter == null ? this.mValueType : typeConverter.getTargetType());
    }

    private void setupGetter(Class cls) {
        this.mGetter = setupSetterOrGetter(cls, sGetterPropertyMap, "get", null);
    }

    void setupSetterAndGetter(Object obj) {
        if (this.mProperty != null) {
            try {
                List<Keyframe> keyframes = this.mKeyframes.getKeyframes();
                int size = keyframes == null ? 0 : keyframes.size();
                Object objConvertBack = null;
                for (int i = 0; i < size; i++) {
                    Keyframe keyframe = keyframes.get(i);
                    if (!keyframe.hasValue() || keyframe.valueWasSetOnStart()) {
                        if (objConvertBack == null) {
                            objConvertBack = convertBack(this.mProperty.get(obj));
                        }
                        keyframe.setValue(objConvertBack);
                        keyframe.setValueWasSetOnStart(true);
                    }
                }
                return;
            } catch (ClassCastException unused) {
                Log.w("PropertyValuesHolder", "No such property (" + this.mProperty.getName() + ") on target object " + obj + ". Trying reflection instead");
                this.mProperty = null;
            }
        }
        if (this.mProperty == null) {
            Class<?> cls = obj.getClass();
            if (this.mSetter == null) {
                setupSetter(cls);
            }
            List<Keyframe> keyframes2 = this.mKeyframes.getKeyframes();
            int size2 = keyframes2 == null ? 0 : keyframes2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Keyframe keyframe2 = keyframes2.get(i2);
                if (!keyframe2.hasValue() || keyframe2.valueWasSetOnStart()) {
                    if (this.mGetter == null) {
                        setupGetter(cls);
                        if (this.mGetter == null) {
                            return;
                        }
                    }
                    try {
                        keyframe2.setValue(convertBack(this.mGetter.invoke(obj, null)));
                        keyframe2.setValueWasSetOnStart(true);
                    } catch (IllegalAccessException e) {
                        Log.e("PropertyValuesHolder", e.toString());
                    } catch (InvocationTargetException e2) {
                        Log.e("PropertyValuesHolder", e2.toString());
                    }
                }
            }
        }
    }

    private Object convertBack(Object obj) {
        TypeConverter typeConverter = this.mConverter;
        if (typeConverter == null) {
            return obj;
        }
        if (!(typeConverter instanceof BidirectionalTypeConverter)) {
            throw new IllegalArgumentException("Converter " + this.mConverter.getClass().getName() + " must be a BidirectionalTypeConverter");
        }
        return ((BidirectionalTypeConverter) typeConverter).convertBack(obj);
    }

    private void setupValue(Object obj, Keyframe keyframe) {
        Property property = this.mProperty;
        if (property != null) {
            keyframe.setValue(convertBack(property.get(obj)));
            return;
        }
        try {
            if (this.mGetter == null) {
                setupGetter(obj.getClass());
                if (this.mGetter == null) {
                    return;
                }
            }
            keyframe.setValue(convertBack(this.mGetter.invoke(obj, null)));
        } catch (IllegalAccessException e) {
            Log.e("PropertyValuesHolder", e.toString());
        } catch (InvocationTargetException e2) {
            Log.e("PropertyValuesHolder", e2.toString());
        }
    }

    void setupStartValue(Object obj) {
        List<Keyframe> keyframes = this.mKeyframes.getKeyframes();
        if (keyframes.isEmpty()) {
            return;
        }
        setupValue(obj, keyframes.get(0));
    }

    void setupEndValue(Object obj) {
        List<Keyframe> keyframes = this.mKeyframes.getKeyframes();
        if (keyframes.isEmpty()) {
            return;
        }
        setupValue(obj, keyframes.get(keyframes.size() - 1));
    }

    @Override // 
    /* renamed from: clone */
    public PropertyValuesHolder mo121clone() {
        try {
            PropertyValuesHolder propertyValuesHolder = (PropertyValuesHolder) super.clone();
            propertyValuesHolder.mPropertyName = this.mPropertyName;
            propertyValuesHolder.mProperty = this.mProperty;
            propertyValuesHolder.mKeyframes = this.mKeyframes.mo83clone();
            propertyValuesHolder.mEvaluator = this.mEvaluator;
            return propertyValuesHolder;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    void setAnimatedValue(Object obj) {
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

    void init() {
        TypeEvaluator typeEvaluator;
        if (this.mEvaluator == null) {
            Class cls = this.mValueType;
            if (cls == Integer.class) {
                typeEvaluator = sIntEvaluator;
            } else {
                typeEvaluator = cls == Float.class ? sFloatEvaluator : null;
            }
            this.mEvaluator = typeEvaluator;
        }
        TypeEvaluator typeEvaluator2 = this.mEvaluator;
        if (typeEvaluator2 != null) {
            this.mKeyframes.setEvaluator(typeEvaluator2);
        }
    }

    public void setEvaluator(TypeEvaluator typeEvaluator) {
        this.mEvaluator = typeEvaluator;
        this.mKeyframes.setEvaluator(typeEvaluator);
    }

    void calculateValue(float f) {
        Object value = this.mKeyframes.getValue(f);
        TypeConverter typeConverter = this.mConverter;
        if (typeConverter != null) {
            value = typeConverter.convert(value);
        }
        this.mAnimatedValue = value;
    }

    public void setPropertyName(String str) {
        this.mPropertyName = str;
    }

    public void setProperty(Property property) {
        this.mProperty = property;
    }

    public String getPropertyName() {
        return this.mPropertyName;
    }

    Object getAnimatedValue() {
        return this.mAnimatedValue;
    }

    public void getPropertyValues(PropertyValues propertyValues) {
        init();
        propertyValues.propertyName = this.mPropertyName;
        propertyValues.type = this.mValueType;
        propertyValues.startValue = this.mKeyframes.getValue(0.0f);
        if (propertyValues.startValue instanceof PathParser.PathData) {
            propertyValues.startValue = new PathParser.PathData((PathParser.PathData) propertyValues.startValue);
        }
        propertyValues.endValue = this.mKeyframes.getValue(1.0f);
        if (propertyValues.endValue instanceof PathParser.PathData) {
            propertyValues.endValue = new PathParser.PathData((PathParser.PathData) propertyValues.endValue);
        }
        Keyframes keyframes = this.mKeyframes;
        if ((keyframes instanceof PathKeyframes.FloatKeyframesBase) || (keyframes instanceof PathKeyframes.IntKeyframesBase) || (keyframes.getKeyframes() != null && this.mKeyframes.getKeyframes().size() > 2)) {
            propertyValues.dataSource = new PropertyValues.DataSource() { // from class: android.animation.PropertyValuesHolder.1
                @Override // android.animation.PropertyValuesHolder.PropertyValues.DataSource
                public Object getValueAtFraction(float f) {
                    return PropertyValuesHolder.this.mKeyframes.getValue(f);
                }
            };
        } else {
            propertyValues.dataSource = null;
        }
    }

    public Class getValueType() {
        return this.mValueType;
    }

    public String toString() {
        return this.mPropertyName + ": " + this.mKeyframes.toString();
    }

    static String getMethodName(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        return str + Character.toUpperCase(str2.charAt(0)) + str2.substring(1);
    }

    static class IntPropertyValuesHolder extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap<>();
        int mIntAnimatedValue;
        Keyframes.IntKeyframes mIntKeyframes;
        private IntProperty mIntProperty;
        long mJniSetter;

        public IntPropertyValuesHolder(String str, Keyframes.IntKeyframes intKeyframes) {
            super(str);
            this.mValueType = Integer.TYPE;
            this.mKeyframes = intKeyframes;
            this.mIntKeyframes = intKeyframes;
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

        @Override // android.animation.PropertyValuesHolder
        public void setProperty(Property property) {
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) property;
            } else {
                super.setProperty(property);
            }
        }

        @Override // android.animation.PropertyValuesHolder
        public void setIntValues(int... iArr) {
            super.setIntValues(iArr);
            this.mIntKeyframes = (Keyframes.IntKeyframes) this.mKeyframes;
        }

        @Override // android.animation.PropertyValuesHolder
        void calculateValue(float f) {
            this.mIntAnimatedValue = this.mIntKeyframes.getIntValue(f);
        }

        @Override // android.animation.PropertyValuesHolder
        Object getAnimatedValue() {
            return Integer.valueOf(this.mIntAnimatedValue);
        }

        @Override // android.animation.PropertyValuesHolder
        /* renamed from: clone */
        public IntPropertyValuesHolder mo121clone() {
            IntPropertyValuesHolder intPropertyValuesHolder = (IntPropertyValuesHolder) super.mo121clone();
            intPropertyValuesHolder.mIntKeyframes = (Keyframes.IntKeyframes) intPropertyValuesHolder.mKeyframes;
            return intPropertyValuesHolder;
        }

        @Override // android.animation.PropertyValuesHolder
        void setAnimatedValue(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            IntProperty intProperty = this.mIntProperty;
            if (intProperty != null) {
                intProperty.setValue(obj, this.mIntAnimatedValue);
                return;
            }
            if (this.mProperty != null) {
                this.mProperty.set(obj, Integer.valueOf(this.mIntAnimatedValue));
                return;
            }
            long j = this.mJniSetter;
            if (j != 0) {
                PropertyValuesHolder.nCallIntMethod(obj, j, this.mIntAnimatedValue);
                return;
            }
            if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = Integer.valueOf(this.mIntAnimatedValue);
                    this.mSetter.invoke(obj, this.mTmpValueArray);
                } catch (IllegalAccessException e) {
                    Log.e("PropertyValuesHolder", e.toString());
                } catch (InvocationTargetException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetter(Class cls) {
            boolean zContainsKey;
            Long l;
            if (this.mProperty != null) {
                return;
            }
            HashMap<Class, HashMap<String, Long>> map = sJNISetterPropertyMap;
            synchronized (map) {
                HashMap<String, Long> map2 = map.get(cls);
                if (map2 != null) {
                    zContainsKey = map2.containsKey(this.mPropertyName);
                    if (zContainsKey && (l = map2.get(this.mPropertyName)) != null) {
                        this.mJniSetter = l.longValue();
                    }
                } else {
                    zContainsKey = false;
                }
                if (!zContainsKey) {
                    try {
                        this.mJniSetter = PropertyValuesHolder.nGetIntMethod(cls, getMethodName("set", this.mPropertyName));
                    } catch (NoSuchMethodError unused) {
                    }
                    if (map2 == null) {
                        map2 = new HashMap<>();
                        sJNISetterPropertyMap.put(cls, map2);
                    }
                    map2.put(this.mPropertyName, Long.valueOf(this.mJniSetter));
                }
            }
            if (this.mJniSetter == 0) {
                super.setupSetter(cls);
            }
        }
    }

    static class FloatPropertyValuesHolder extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap<>();
        float mFloatAnimatedValue;
        Keyframes.FloatKeyframes mFloatKeyframes;
        private FloatProperty mFloatProperty;
        long mJniSetter;

        public FloatPropertyValuesHolder(String str, Keyframes.FloatKeyframes floatKeyframes) {
            super(str);
            this.mValueType = Float.TYPE;
            this.mKeyframes = floatKeyframes;
            this.mFloatKeyframes = floatKeyframes;
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

        @Override // android.animation.PropertyValuesHolder
        public void setProperty(Property property) {
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) property;
            } else {
                super.setProperty(property);
            }
        }

        @Override // android.animation.PropertyValuesHolder
        public void setFloatValues(float... fArr) {
            super.setFloatValues(fArr);
            this.mFloatKeyframes = (Keyframes.FloatKeyframes) this.mKeyframes;
        }

        @Override // android.animation.PropertyValuesHolder
        void calculateValue(float f) {
            this.mFloatAnimatedValue = this.mFloatKeyframes.getFloatValue(f);
        }

        @Override // android.animation.PropertyValuesHolder
        Object getAnimatedValue() {
            return Float.valueOf(this.mFloatAnimatedValue);
        }

        @Override // android.animation.PropertyValuesHolder
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public FloatPropertyValuesHolder mo121clone() {
            FloatPropertyValuesHolder floatPropertyValuesHolder = (FloatPropertyValuesHolder) super.mo121clone();
            floatPropertyValuesHolder.mFloatKeyframes = (Keyframes.FloatKeyframes) floatPropertyValuesHolder.mKeyframes;
            return floatPropertyValuesHolder;
        }

        @Override // android.animation.PropertyValuesHolder
        void setAnimatedValue(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            FloatProperty floatProperty = this.mFloatProperty;
            if (floatProperty != null) {
                floatProperty.setValue(obj, this.mFloatAnimatedValue);
                return;
            }
            if (this.mProperty != null) {
                this.mProperty.set(obj, Float.valueOf(this.mFloatAnimatedValue));
                return;
            }
            long j = this.mJniSetter;
            if (j != 0) {
                PropertyValuesHolder.nCallFloatMethod(obj, j, this.mFloatAnimatedValue);
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

        @Override // android.animation.PropertyValuesHolder
        void setupSetter(Class cls) {
            boolean zContainsKey;
            Long l;
            if (this.mProperty != null) {
                return;
            }
            HashMap<Class, HashMap<String, Long>> map = sJNISetterPropertyMap;
            synchronized (map) {
                HashMap<String, Long> map2 = map.get(cls);
                if (map2 != null) {
                    zContainsKey = map2.containsKey(this.mPropertyName);
                    if (zContainsKey && (l = map2.get(this.mPropertyName)) != null) {
                        this.mJniSetter = l.longValue();
                    }
                } else {
                    zContainsKey = false;
                }
                if (!zContainsKey) {
                    try {
                        this.mJniSetter = PropertyValuesHolder.nGetFloatMethod(cls, getMethodName("set", this.mPropertyName));
                    } catch (NoSuchMethodError unused) {
                    }
                    if (map2 == null) {
                        map2 = new HashMap<>();
                        sJNISetterPropertyMap.put(cls, map2);
                    }
                    map2.put(this.mPropertyName, Long.valueOf(this.mJniSetter));
                }
            }
            if (this.mJniSetter == 0) {
                super.setupSetter(cls);
            }
        }
    }

    static class MultiFloatValuesHolder extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap<>();
        private long mJniSetter;

        public MultiFloatValuesHolder(String str, TypeConverter typeConverter, TypeEvaluator typeEvaluator, Object... objArr) {
            super(str);
            setConverter(typeConverter);
            setObjectValues(objArr);
            setEvaluator(typeEvaluator);
        }

        public MultiFloatValuesHolder(String str, TypeConverter typeConverter, TypeEvaluator typeEvaluator, Keyframes keyframes) {
            super(str);
            setConverter(typeConverter);
            this.mKeyframes = keyframes;
            setEvaluator(typeEvaluator);
        }

        @Override // android.animation.PropertyValuesHolder
        void setAnimatedValue(Object obj) {
            float[] fArr = (float[]) getAnimatedValue();
            int length = fArr.length;
            long j = this.mJniSetter;
            if (j != 0) {
                if (length == 1) {
                    PropertyValuesHolder.nCallFloatMethod(obj, j, fArr[0]);
                    return;
                }
                if (length == 2) {
                    PropertyValuesHolder.nCallTwoFloatMethod(obj, j, fArr[0], fArr[1]);
                } else if (length == 4) {
                    PropertyValuesHolder.nCallFourFloatMethod(obj, j, fArr[0], fArr[1], fArr[2], fArr[3]);
                } else {
                    PropertyValuesHolder.nCallMultipleFloatMethod(obj, j, fArr);
                }
            }
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetterAndGetter(Object obj) {
            setupSetter(obj.getClass());
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetter(Class cls) {
            boolean zContainsKey;
            Long l;
            if (this.mJniSetter != 0) {
                return;
            }
            HashMap<Class, HashMap<String, Long>> map = sJNISetterPropertyMap;
            synchronized (map) {
                HashMap<String, Long> map2 = map.get(cls);
                if (map2 != null) {
                    zContainsKey = map2.containsKey(this.mPropertyName);
                    if (zContainsKey && (l = map2.get(this.mPropertyName)) != null) {
                        this.mJniSetter = l.longValue();
                    }
                } else {
                    zContainsKey = false;
                }
                if (!zContainsKey) {
                    String methodName = getMethodName("set", this.mPropertyName);
                    calculateValue(0.0f);
                    int length = ((float[]) getAnimatedValue()).length;
                    try {
                        try {
                            this.mJniSetter = PropertyValuesHolder.nGetMultipleFloatMethod(cls, methodName, length);
                        } catch (NoSuchMethodError unused) {
                        }
                    } catch (NoSuchMethodError unused2) {
                        this.mJniSetter = PropertyValuesHolder.nGetMultipleFloatMethod(cls, this.mPropertyName, length);
                    }
                    if (map2 == null) {
                        map2 = new HashMap<>();
                        sJNISetterPropertyMap.put(cls, map2);
                    }
                    map2.put(this.mPropertyName, Long.valueOf(this.mJniSetter));
                }
            }
        }
    }

    static class MultiIntValuesHolder extends PropertyValuesHolder {
        private static final HashMap<Class, HashMap<String, Long>> sJNISetterPropertyMap = new HashMap<>();
        private long mJniSetter;

        public MultiIntValuesHolder(String str, TypeConverter typeConverter, TypeEvaluator typeEvaluator, Object... objArr) {
            super(str);
            setConverter(typeConverter);
            setObjectValues(objArr);
            setEvaluator(typeEvaluator);
        }

        public MultiIntValuesHolder(String str, TypeConverter typeConverter, TypeEvaluator typeEvaluator, Keyframes keyframes) {
            super(str);
            setConverter(typeConverter);
            this.mKeyframes = keyframes;
            setEvaluator(typeEvaluator);
        }

        @Override // android.animation.PropertyValuesHolder
        void setAnimatedValue(Object obj) {
            int[] iArr = (int[]) getAnimatedValue();
            int length = iArr.length;
            long j = this.mJniSetter;
            if (j != 0) {
                if (length == 1) {
                    PropertyValuesHolder.nCallIntMethod(obj, j, iArr[0]);
                    return;
                }
                if (length == 2) {
                    PropertyValuesHolder.nCallTwoIntMethod(obj, j, iArr[0], iArr[1]);
                } else if (length == 4) {
                    PropertyValuesHolder.nCallFourIntMethod(obj, j, iArr[0], iArr[1], iArr[2], iArr[3]);
                } else {
                    PropertyValuesHolder.nCallMultipleIntMethod(obj, j, iArr);
                }
            }
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetterAndGetter(Object obj) {
            setupSetter(obj.getClass());
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetter(Class cls) {
            boolean zContainsKey;
            Long l;
            if (this.mJniSetter != 0) {
                return;
            }
            HashMap<Class, HashMap<String, Long>> map = sJNISetterPropertyMap;
            synchronized (map) {
                HashMap<String, Long> map2 = map.get(cls);
                if (map2 != null) {
                    zContainsKey = map2.containsKey(this.mPropertyName);
                    if (zContainsKey && (l = map2.get(this.mPropertyName)) != null) {
                        this.mJniSetter = l.longValue();
                    }
                } else {
                    zContainsKey = false;
                }
                if (!zContainsKey) {
                    String methodName = getMethodName("set", this.mPropertyName);
                    calculateValue(0.0f);
                    int length = ((int[]) getAnimatedValue()).length;
                    try {
                        try {
                            this.mJniSetter = PropertyValuesHolder.nGetMultipleIntMethod(cls, methodName, length);
                        } catch (NoSuchMethodError unused) {
                        }
                    } catch (NoSuchMethodError unused2) {
                        this.mJniSetter = PropertyValuesHolder.nGetMultipleIntMethod(cls, this.mPropertyName, length);
                    }
                    if (map2 == null) {
                        map2 = new HashMap<>();
                        sJNISetterPropertyMap.put(cls, map2);
                    }
                    map2.put(this.mPropertyName, Long.valueOf(this.mJniSetter));
                }
            }
        }
    }

    private static class PointFToFloatArray extends TypeConverter<PointF, float[]> {
        private float[] mCoordinates;

        public PointFToFloatArray() {
            super(PointF.class, float[].class);
            this.mCoordinates = new float[2];
        }

        @Override // android.animation.TypeConverter
        public float[] convert(PointF pointF) {
            this.mCoordinates[0] = pointF.x;
            this.mCoordinates[1] = pointF.y;
            return this.mCoordinates;
        }
    }

    private static class PointFToIntArray extends TypeConverter<PointF, int[]> {
        private int[] mCoordinates;

        public PointFToIntArray() {
            super(PointF.class, int[].class);
            this.mCoordinates = new int[2];
        }

        @Override // android.animation.TypeConverter
        public int[] convert(PointF pointF) {
            this.mCoordinates[0] = Math.round(pointF.x);
            this.mCoordinates[1] = Math.round(pointF.y);
            return this.mCoordinates;
        }
    }

    public static class PropertyValues {
        public DataSource dataSource = null;
        public Object endValue;
        public String propertyName;
        public Object startValue;
        public Class type;

        public interface DataSource {
            Object getValueAtFraction(float f);
        }

        public String toString() {
            return "property name: " + this.propertyName + ", type: " + this.type + ", startValue: " + this.startValue.toString() + ", endValue: " + this.endValue.toString();
        }
    }
}
