package com.google.gson.internal;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ConstructorConstructor {
    public final Map instanceCreators;
    public final boolean useJdkUnsafe;

    public ConstructorConstructor(Map<Type, Object> map, boolean z) {
        this.instanceCreators = map;
        this.useJdkUnsafe = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x00a0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ObjectConstructor get(TypeToken typeToken) {
        final String sb;
        ObjectConstructor objectConstructor;
        final Type type = typeToken.type;
        Map map = this.instanceCreators;
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(map.get(type));
        final Class cls = typeToken.rawType;
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(map.get(cls));
        ObjectConstructor objectConstructor2 = null;
        if (!Modifier.isAbstract(cls.getModifiers())) {
            try {
                final Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
                try {
                    declaredConstructor.setAccessible(true);
                    sb = null;
                } catch (Exception e) {
                    StringBuilder sb2 = new StringBuilder("Failed making constructor '");
                    StringBuilder sb3 = new StringBuilder(declaredConstructor.getDeclaringClass().getName());
                    sb3.append('#');
                    sb3.append(declaredConstructor.getDeclaringClass().getSimpleName());
                    sb3.append('(');
                    Class<?>[] parameterTypes = declaredConstructor.getParameterTypes();
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (i > 0) {
                            sb3.append(", ");
                        }
                        sb3.append(parameterTypes[i].getSimpleName());
                    }
                    sb3.append(')');
                    sb2.append(sb3.toString());
                    sb2.append("' accessible; either change its visibility or write a custom InstanceCreator or TypeAdapter for its declaring type: ");
                    sb2.append(e.getMessage());
                    sb = sb2.toString();
                }
                objectConstructor = sb != null ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.3
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        throw new JsonIOException(sb);
                    }
                } : new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.4
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        Constructor constructor = declaredConstructor;
                        try {
                            return constructor.newInstance(new Object[0]);
                        } catch (IllegalAccessException e2) {
                            throw new AssertionError(e2);
                        } catch (InstantiationException e3) {
                            throw new RuntimeException("Failed to invoke " + constructor + " with no args", e3);
                        } catch (InvocationTargetException e4) {
                            throw new RuntimeException("Failed to invoke " + constructor + " with no args", e4.getTargetException());
                        }
                    }
                };
            } catch (NoSuchMethodException unused) {
            }
            if (objectConstructor == null) {
                return objectConstructor;
            }
            if (Collection.class.isAssignableFrom(cls)) {
                objectConstructor2 = SortedSet.class.isAssignableFrom(cls) ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.5
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new TreeSet();
                    }
                } : EnumSet.class.isAssignableFrom(cls) ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.6
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        Type type2 = type;
                        if (!(type2 instanceof ParameterizedType)) {
                            throw new JsonIOException("Invalid EnumSet type: " + type2.toString());
                        }
                        Type type3 = ((ParameterizedType) type2).getActualTypeArguments()[0];
                        if (type3 instanceof Class) {
                            return EnumSet.noneOf((Class) type3);
                        }
                        throw new JsonIOException("Invalid EnumSet type: " + type2.toString());
                    }
                } : Set.class.isAssignableFrom(cls) ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.7
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new LinkedHashSet();
                    }
                } : Queue.class.isAssignableFrom(cls) ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.8
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new ArrayDeque();
                    }
                } : new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.9
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new ArrayList();
                    }
                };
            } else if (Map.class.isAssignableFrom(cls)) {
                objectConstructor2 = cls == EnumMap.class ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.10
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        Type type2 = type;
                        if (!(type2 instanceof ParameterizedType)) {
                            throw new JsonIOException("Invalid EnumMap type: " + type2.toString());
                        }
                        Type type3 = ((ParameterizedType) type2).getActualTypeArguments()[0];
                        if (type3 instanceof Class) {
                            return new EnumMap((Class) type3);
                        }
                        throw new JsonIOException("Invalid EnumMap type: " + type2.toString());
                    }
                } : ConcurrentNavigableMap.class.isAssignableFrom(cls) ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.11
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new ConcurrentSkipListMap();
                    }
                } : ConcurrentMap.class.isAssignableFrom(cls) ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.12
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new ConcurrentHashMap();
                    }
                } : SortedMap.class.isAssignableFrom(cls) ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.13
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new TreeMap();
                    }
                } : (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(new TypeToken(((ParameterizedType) type).getActualTypeArguments()[0]).rawType)) ? new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.15
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new LinkedTreeMap();
                    }
                } : new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.14
                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        return new LinkedHashMap();
                    }
                };
            }
            if (objectConstructor2 != null) {
                return objectConstructor2;
            }
            if (this.useJdkUnsafe) {
                return new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.16
                    public final UnsafeAllocator unsafeAllocator;

                    {
                        UnsafeAllocator c44674;
                        try {
                            Class<?> cls2 = Class.forName("sun.misc.Unsafe");
                            Field declaredField = cls2.getDeclaredField("theUnsafe");
                            declaredField.setAccessible(true);
                            c44674 = new UnsafeAllocator() { // from class: com.google.gson.internal.UnsafeAllocator.1
                                public final /* synthetic */ Method val$allocateInstance;
                                public final /* synthetic */ Object val$unsafe;

                                public C44641(Method method, Object obj) {
                                    r1 = method;
                                    r2 = obj;
                                }

                                @Override // com.google.gson.internal.UnsafeAllocator
                                public final Object newInstance(Class cls3) {
                                    UnsafeAllocator.assertInstantiable(cls3);
                                    return r1.invoke(r2, cls3);
                                }
                            };
                        } catch (Exception unused2) {
                            try {
                                try {
                                    Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
                                    declaredMethod.setAccessible(true);
                                    int intValue = ((Integer) declaredMethod.invoke(null, Object.class)).intValue();
                                    Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
                                    declaredMethod2.setAccessible(true);
                                    c44674 = new UnsafeAllocator() { // from class: com.google.gson.internal.UnsafeAllocator.2
                                        public final /* synthetic */ int val$constructorId;
                                        public final /* synthetic */ Method val$newInstance;

                                        public C44652(Method declaredMethod22, int intValue2) {
                                            r1 = declaredMethod22;
                                            r2 = intValue2;
                                        }

                                        @Override // com.google.gson.internal.UnsafeAllocator
                                        public final Object newInstance(Class cls3) {
                                            UnsafeAllocator.assertInstantiable(cls3);
                                            return r1.invoke(null, cls3, Integer.valueOf(r2));
                                        }
                                    };
                                } catch (Exception unused3) {
                                    Method declaredMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
                                    declaredMethod3.setAccessible(true);
                                    c44674 = new UnsafeAllocator() { // from class: com.google.gson.internal.UnsafeAllocator.3
                                        public final /* synthetic */ Method val$newInstance;

                                        public C44663(Method declaredMethod32) {
                                            r1 = declaredMethod32;
                                        }

                                        @Override // com.google.gson.internal.UnsafeAllocator
                                        public final Object newInstance(Class cls3) {
                                            UnsafeAllocator.assertInstantiable(cls3);
                                            return r1.invoke(null, cls3, Object.class);
                                        }
                                    };
                                }
                            } catch (Exception unused4) {
                                c44674 = new UnsafeAllocator() { // from class: com.google.gson.internal.UnsafeAllocator.4
                                    @Override // com.google.gson.internal.UnsafeAllocator
                                    public final Object newInstance(Class cls3) {
                                        throw new UnsupportedOperationException("Cannot allocate " + cls3 + ". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
                                    }
                                };
                            }
                        }
                        this.unsafeAllocator = c44674;
                    }

                    @Override // com.google.gson.internal.ObjectConstructor
                    public final Object construct() {
                        Class cls2 = cls;
                        try {
                            return this.unsafeAllocator.newInstance(cls2);
                        } catch (Exception e2) {
                            throw new RuntimeException("Unable to create instance of " + cls2 + ". Registering an InstanceCreator or a TypeAdapter for this type, or adding a no-args constructor may fix this problem.", e2);
                        }
                    }
                };
            }
            final String str = "Unable to create instance of " + cls + "; usage of JDK Unsafe is disabled. Registering an InstanceCreator or a TypeAdapter for this type, adding a no-args constructor, or enabling usage of JDK Unsafe may fix this problem.";
            return new ObjectConstructor(this) { // from class: com.google.gson.internal.ConstructorConstructor.17
                @Override // com.google.gson.internal.ObjectConstructor
                public final Object construct() {
                    throw new JsonIOException(str);
                }
            };
        }
        objectConstructor = null;
        if (objectConstructor == null) {
        }
    }

    public final String toString() {
        return this.instanceCreators.toString();
    }
}
