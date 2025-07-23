package androidx.lifecycle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Lifecycling {
    public static final Lifecycling INSTANCE = new Lifecycling();
    public static final Map callbackCache = new HashMap();
    public static final Map classToAdapters = new HashMap();

    private Lifecycling() {
    }

    public static void createGeneratedAdapter(Constructor constructor, LifecycleObserver lifecycleObserver) {
        try {
            if (constructor.newInstance(lifecycleObserver) == null) {
            } else {
                throw new ClassCastException();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static final String getAdapterName(String str) {
        return StringsKt__StringsJVMKt.replace$default(str, ".", "_") + "_LifecycleAdapter";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static int getObserverConstructorType(Class cls) {
        Constructor constructor;
        boolean z;
        Integer num = (Integer) ((HashMap) callbackCache).get(cls);
        if (num != null) {
            return num.intValue();
        }
        int i = 1;
        if (cls.getCanonicalName() != null) {
            ArrayList arrayList = null;
            try {
                Package r3 = cls.getPackage();
                String canonicalName = cls.getCanonicalName();
                String name = r3 != null ? r3.getName() : "";
                if (name.length() != 0) {
                    canonicalName = canonicalName.substring(name.length() + 1);
                }
                String adapterName = getAdapterName(canonicalName);
                if (name.length() != 0) {
                    adapterName = name + '.' + adapterName;
                }
                constructor = Class.forName(adapterName).getDeclaredConstructor(cls);
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
            } catch (ClassNotFoundException unused) {
                constructor = null;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            if (constructor != null) {
                ((HashMap) classToAdapters).put(cls, Collections.singletonList(constructor));
            } else {
                ClassesInfoCache classesInfoCache = ClassesInfoCache.sInstance;
                Boolean bool = (Boolean) ((HashMap) classesInfoCache.mHasLifecycleMethods).get(cls);
                if (bool != null) {
                    z = bool.booleanValue();
                } else {
                    try {
                        Method[] declaredMethods = cls.getDeclaredMethods();
                        int length = declaredMethods.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                ((HashMap) classesInfoCache.mHasLifecycleMethods).put(cls, Boolean.FALSE);
                                z = false;
                                break;
                            }
                            if (((OnLifecycleEvent) declaredMethods[i2].getAnnotation(OnLifecycleEvent.class)) != null) {
                                classesInfoCache.createInfo(cls, declaredMethods);
                                z = true;
                                break;
                            }
                            i2++;
                        }
                    } catch (NoClassDefFoundError e2) {
                        throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e2);
                    }
                }
                if (!z) {
                    Class superclass = cls.getSuperclass();
                    if (superclass != null && LifecycleObserver.class.isAssignableFrom(superclass)) {
                        if (getObserverConstructorType(superclass) != 1) {
                            Object obj = ((HashMap) classToAdapters).get(superclass);
                            obj.getClass();
                            arrayList = new ArrayList((Collection) obj);
                        }
                    }
                    Class<?>[] interfaces = cls.getInterfaces();
                    int length2 = interfaces.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 < length2) {
                            Class<?> cls2 = interfaces[i3];
                            if (cls2 != null && LifecycleObserver.class.isAssignableFrom(cls2)) {
                                if (getObserverConstructorType(cls2) == 1) {
                                    break;
                                }
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                Object obj2 = ((HashMap) classToAdapters).get(cls2);
                                obj2.getClass();
                                arrayList.addAll((Collection) obj2);
                            }
                            i3++;
                        } else if (arrayList != null) {
                            ((HashMap) classToAdapters).put(cls, arrayList);
                        }
                    }
                }
            }
            i = 2;
        }
        ((HashMap) callbackCache).put(cls, Integer.valueOf(i));
        return i;
    }
}
