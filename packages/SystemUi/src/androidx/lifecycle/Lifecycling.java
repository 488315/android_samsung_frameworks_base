package androidx.lifecycle;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.lifecycle.Lifecycle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Lifecycling {
    public static final Map sCallbackCache = new HashMap();
    public static final Map sClassToAdapters = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.lifecycle.Lifecycling$1 */
    class C03011 implements LifecycleEventObserver {
        public final /* synthetic */ LifecycleEventObserver val$observer;

        public C03011(LifecycleEventObserver lifecycleEventObserver) {
            this.val$observer = lifecycleEventObserver;
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            this.val$observer.onStateChanged(lifecycleOwner, event);
        }
    }

    private Lifecycling() {
    }

    public static void createGeneratedAdapter(Constructor constructor, Object obj) {
        try {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(constructor.newInstance(obj));
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

    public static int getObserverConstructorType(Class cls) {
        Constructor<?> constructor;
        boolean z;
        ArrayList arrayList;
        HashMap hashMap = (HashMap) sCallbackCache;
        Integer num = (Integer) hashMap.get(cls);
        if (num != null) {
            return num.intValue();
        }
        int i = 1;
        if (cls.getCanonicalName() != null) {
            try {
                Package r3 = cls.getPackage();
                String canonicalName = cls.getCanonicalName();
                String name = r3 != null ? r3.getName() : "";
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
            Map map = sClassToAdapters;
            if (constructor != null) {
                ((HashMap) map).put(cls, Collections.singletonList(constructor));
            } else {
                ClassesInfoCache classesInfoCache = ClassesInfoCache.sInstance;
                HashMap hashMap2 = (HashMap) classesInfoCache.mHasLifecycleMethods;
                Boolean bool = (Boolean) hashMap2.get(cls);
                if (bool != null) {
                    z = bool.booleanValue();
                } else {
                    try {
                        Method[] declaredMethods = cls.getDeclaredMethods();
                        int length = declaredMethods.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                hashMap2.put(cls, Boolean.FALSE);
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
                        arrayList = getObserverConstructorType(superclass) != 1 ? new ArrayList((Collection) ((HashMap) map).get(superclass)) : null;
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
                                arrayList.addAll((Collection) ((HashMap) map).get(cls2));
                            }
                            i3++;
                        } else if (arrayList != null) {
                            ((HashMap) map).put(cls, arrayList);
                        }
                    }
                }
            }
            i = 2;
        }
        hashMap.put(cls, Integer.valueOf(i));
        return i;
    }
}
