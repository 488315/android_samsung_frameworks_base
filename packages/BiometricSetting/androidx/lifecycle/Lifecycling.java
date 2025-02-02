package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class Lifecycling {
    private static Map<Class<?>, Integer> sCallbackCache = new HashMap();
    private static Map<Class<?>, List<Constructor<? extends GeneratedAdapter>>> sClassToAdapters = new HashMap();

    /* renamed from: androidx.lifecycle.Lifecycling$1 */
    class C01431 implements LifecycleEventObserver {
        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            throw null;
        }
    }

    private static GeneratedAdapter createGeneratedAdapter(Constructor<? extends GeneratedAdapter> constructor, Object obj) {
        try {
            return constructor.newInstance(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static String getAdapterName(String str) {
        return str.replace(".", "_") + "_LifecycleAdapter";
    }

    private static int getObserverConstructorType(Class<?> cls) {
        Constructor<?> constructor;
        ArrayList arrayList;
        Integer num = (Integer) ((HashMap) sCallbackCache).get(cls);
        if (num != null) {
            return num.intValue();
        }
        int i = 1;
        if (cls.getCanonicalName() != null) {
            try {
                Package r2 = cls.getPackage();
                String canonicalName = cls.getCanonicalName();
                String name = r2 != null ? r2.getName() : "";
                if (!name.isEmpty()) {
                    canonicalName = canonicalName.substring(name.length() + 1);
                }
                String adapterName = getAdapterName(canonicalName);
                if (!name.isEmpty()) {
                    adapterName = name + "." + adapterName;
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
                ((HashMap) sClassToAdapters).put(cls, Collections.singletonList(constructor));
            } else if (!ClassesInfoCache.sInstance.hasLifecycleMethods(cls)) {
                Class<? super Object> superclass = cls.getSuperclass();
                if (superclass != null && LifecycleObserver.class.isAssignableFrom(superclass)) {
                    arrayList = getObserverConstructorType(superclass) != 1 ? new ArrayList((Collection) ((HashMap) sClassToAdapters).get(superclass)) : null;
                }
                Class<?>[] interfaces = cls.getInterfaces();
                int length = interfaces.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        Class<?> cls2 = interfaces[i2];
                        if (cls2 != null && LifecycleObserver.class.isAssignableFrom(cls2)) {
                            if (getObserverConstructorType(cls2) == 1) {
                                break;
                            }
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.addAll((Collection) ((HashMap) sClassToAdapters).get(cls2));
                        }
                        i2++;
                    } else if (arrayList != null) {
                        ((HashMap) sClassToAdapters).put(cls, arrayList);
                    }
                }
            }
            i = 2;
        }
        ((HashMap) sCallbackCache).put(cls, Integer.valueOf(i));
        return i;
    }

    static LifecycleEventObserver lifecycleEventObserver(Object obj) {
        boolean z = obj instanceof LifecycleEventObserver;
        boolean z2 = obj instanceof FullLifecycleObserver;
        if (z && z2) {
            return new FullLifecycleObserverAdapter((FullLifecycleObserver) obj, (LifecycleEventObserver) obj);
        }
        if (z2) {
            return new FullLifecycleObserverAdapter((FullLifecycleObserver) obj, null);
        }
        if (z) {
            return (LifecycleEventObserver) obj;
        }
        Class<?> cls = obj.getClass();
        if (getObserverConstructorType(cls) != 2) {
            return new ReflectiveGenericLifecycleObserver(obj);
        }
        List list = (List) ((HashMap) sClassToAdapters).get(cls);
        if (list.size() == 1) {
            return new SingleGeneratedAdapterObserver(createGeneratedAdapter((Constructor) list.get(0), obj));
        }
        GeneratedAdapter[] generatedAdapterArr = new GeneratedAdapter[list.size()];
        for (int i = 0; i < list.size(); i++) {
            generatedAdapterArr[i] = createGeneratedAdapter((Constructor) list.get(i), obj);
        }
        return new CompositeGeneratedAdaptersObserver(generatedAdapterArr);
    }
}
