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
    /* JADX WARN: Removed duplicated region for block: B:60:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x012d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getObserverConstructorType(Class cls) throws NoSuchMethodException, SecurityException {
        Constructor declaredConstructor;
        boolean zBooleanValue;
        int length;
        int i;
        Integer num = (Integer) ((HashMap) callbackCache).get(cls);
        if (num != null) {
            return num.intValue();
        }
        int i2 = 1;
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
                declaredConstructor = Class.forName(adapterName).getDeclaredConstructor(cls);
                if (!declaredConstructor.isAccessible()) {
                    declaredConstructor.setAccessible(true);
                }
            } catch (ClassNotFoundException unused) {
                declaredConstructor = null;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            if (declaredConstructor != null) {
                ((HashMap) classToAdapters).put(cls, Collections.singletonList(declaredConstructor));
            } else {
                ClassesInfoCache classesInfoCache = ClassesInfoCache.sInstance;
                Boolean bool = (Boolean) ((HashMap) classesInfoCache.mHasLifecycleMethods).get(cls);
                if (bool != null) {
                    zBooleanValue = bool.booleanValue();
                } else {
                    try {
                        Method[] declaredMethods = cls.getDeclaredMethods();
                        int length2 = declaredMethods.length;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= length2) {
                                ((HashMap) classesInfoCache.mHasLifecycleMethods).put(cls, Boolean.FALSE);
                                zBooleanValue = false;
                                break;
                            }
                            if (((OnLifecycleEvent) declaredMethods[i3].getAnnotation(OnLifecycleEvent.class)) != null) {
                                classesInfoCache.createInfo(cls, declaredMethods);
                                zBooleanValue = true;
                                break;
                            }
                            i3++;
                        }
                    } catch (NoClassDefFoundError e2) {
                        throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e2);
                    }
                }
                if (!zBooleanValue) {
                    Class superclass = cls.getSuperclass();
                    if (!(superclass != null && LifecycleObserver.class.isAssignableFrom(superclass))) {
                        Class<?>[] interfaces = cls.getInterfaces();
                        length = interfaces.length;
                        i = 0;
                        while (true) {
                            if (i < length) {
                                Class<?> cls2 = interfaces[i];
                                if (cls2 != null && LifecycleObserver.class.isAssignableFrom(cls2)) {
                                    if (getObserverConstructorType(cls2) == 1) {
                                        break;
                                    }
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                    }
                                    Object obj = ((HashMap) classToAdapters).get(cls2);
                                    obj.getClass();
                                    arrayList.addAll((Collection) obj);
                                }
                                i++;
                            } else if (arrayList != null) {
                                ((HashMap) classToAdapters).put(cls, arrayList);
                            }
                        }
                    } else if (getObserverConstructorType(superclass) != 1) {
                        Object obj2 = ((HashMap) classToAdapters).get(superclass);
                        obj2.getClass();
                        arrayList = new ArrayList((Collection) obj2);
                        Class<?>[] interfaces2 = cls.getInterfaces();
                        length = interfaces2.length;
                        i = 0;
                        while (true) {
                            if (i < length) {
                            }
                            i++;
                        }
                    }
                }
            }
            i2 = 2;
        }
        ((HashMap) callbackCache).put(cls, Integer.valueOf(i2));
        return i2;
    }
}
