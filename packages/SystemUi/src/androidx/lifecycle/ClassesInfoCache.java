package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClassesInfoCache {
    public static final ClassesInfoCache sInstance = new ClassesInfoCache();
    public final Map mCallbackMap = new HashMap();
    public final Map mHasLifecycleMethods = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CallbackInfo {
        public final Map mEventToHandlers = new HashMap();
        public final Map mHandlerToEvent;

        public CallbackInfo(Map<MethodReference, Lifecycle.Event> map) {
            this.mHandlerToEvent = map;
            for (Map.Entry<MethodReference, Lifecycle.Event> entry : map.entrySet()) {
                Lifecycle.Event value = entry.getValue();
                List list = (List) this.mEventToHandlers.get(value);
                if (list == null) {
                    list = new ArrayList();
                    this.mEventToHandlers.put(value, list);
                }
                list.add(entry.getKey());
            }
        }

        public static void invokeMethodsForEvent(List list, LifecycleOwner lifecycleOwner, Lifecycle.Event event, Object obj) {
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    MethodReference methodReference = (MethodReference) list.get(size);
                    methodReference.getClass();
                    try {
                        int i = methodReference.mCallType;
                        Method method = methodReference.mMethod;
                        if (i == 0) {
                            method.invoke(obj, new Object[0]);
                        } else if (i == 1) {
                            method.invoke(obj, lifecycleOwner);
                        } else if (i == 2) {
                            method.invoke(obj, lifecycleOwner, event);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e2) {
                        throw new RuntimeException("Failed to call observer method", e2.getCause());
                    }
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MethodReference {
        public final int mCallType;
        public final Method mMethod;

        public MethodReference(int i, Method method) {
            this.mCallType = i;
            this.mMethod = method;
            method.setAccessible(true);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MethodReference)) {
                return false;
            }
            MethodReference methodReference = (MethodReference) obj;
            return this.mCallType == methodReference.mCallType && this.mMethod.getName().equals(methodReference.mMethod.getName());
        }

        public final int hashCode() {
            return this.mMethod.getName().hashCode() + (this.mCallType * 31);
        }
    }

    public static void verifyAndPutHandler(Map map, MethodReference methodReference, Lifecycle.Event event, Class cls) {
        HashMap hashMap = (HashMap) map;
        Lifecycle.Event event2 = (Lifecycle.Event) hashMap.get(methodReference);
        if (event2 == null || event == event2) {
            if (event2 == null) {
                hashMap.put(methodReference, event);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Method " + methodReference.mMethod.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous value " + event2 + ", new value " + event);
    }

    public final CallbackInfo createInfo(Class cls, Method[] methodArr) {
        int i;
        Class superclass = cls.getSuperclass();
        HashMap hashMap = new HashMap();
        if (superclass != null) {
            hashMap.putAll(getInfo(superclass).mHandlerToEvent);
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            for (Map.Entry entry : getInfo(cls2).mHandlerToEvent.entrySet()) {
                verifyAndPutHandler(hashMap, (MethodReference) entry.getKey(), (Lifecycle.Event) entry.getValue(), cls);
            }
        }
        if (methodArr == null) {
            try {
                methodArr = cls.getDeclaredMethods();
            } catch (NoClassDefFoundError e) {
                throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e);
            }
        }
        boolean z = false;
        for (Method method : methodArr) {
            OnLifecycleEvent onLifecycleEvent = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class);
            if (onLifecycleEvent != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length <= 0) {
                    i = 0;
                } else {
                    if (!LifecycleOwner.class.isAssignableFrom(parameterTypes[0])) {
                        throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    }
                    i = 1;
                }
                Lifecycle.Event value = onLifecycleEvent.value();
                if (parameterTypes.length > 1) {
                    if (!Lifecycle.Event.class.isAssignableFrom(parameterTypes[1])) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    }
                    if (value != Lifecycle.Event.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                    i = 2;
                }
                if (parameterTypes.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                verifyAndPutHandler(hashMap, new MethodReference(i, method), value, cls);
                z = true;
            }
        }
        CallbackInfo callbackInfo = new CallbackInfo(hashMap);
        ((HashMap) this.mCallbackMap).put(cls, callbackInfo);
        ((HashMap) this.mHasLifecycleMethods).put(cls, Boolean.valueOf(z));
        return callbackInfo;
    }

    public final CallbackInfo getInfo(Class cls) {
        CallbackInfo callbackInfo = (CallbackInfo) ((HashMap) this.mCallbackMap).get(cls);
        return callbackInfo != null ? callbackInfo : createInfo(cls, null);
    }
}
