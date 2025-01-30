package com.android.systemui.edgelighting.reflection;

import android.util.Slog;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbstractBaseReflection {
    public Class mBaseClass;
    public final ArrayList mNameList;
    public final ArrayList mReflectionList;

    public AbstractBaseReflection() {
        this.mBaseClass = null;
        this.mNameList = new ArrayList();
        this.mReflectionList = new ArrayList();
        new HashMap();
        loadReflection(getBaseClassName());
    }

    public final void addReflectionInstance(Object obj, String str) {
        synchronized (this.mNameList) {
            this.mNameList.add(str);
            this.mReflectionList.add(obj);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x00a9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object createInstance(Class[] clsArr, Object... objArr) {
        Constructor constructor;
        String baseClassName = getBaseClassName();
        if (clsArr == null) {
            baseClassName = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(baseClassName, "_EMPTY");
        } else {
            for (Class cls : clsArr) {
                try {
                    baseClassName = baseClassName + cls.getName();
                } catch (NullPointerException e) {
                    System.err.println(getBaseClassName() + " getUniqueConstructorName " + e);
                }
            }
        }
        Object reflectionInstance = getReflectionInstance(baseClassName);
        if (reflectionInstance != null) {
            constructor = (Constructor) reflectionInstance;
        } else if (this.mBaseClass == null || baseClassName == null || baseClassName.isEmpty()) {
            constructor = null;
        } else {
            if (clsArr == null) {
                clsArr = new Class[0];
            }
            try {
                constructor = this.mBaseClass.getConstructor(clsArr);
            } catch (NoSuchMethodException unused) {
                constructor = null;
            }
            try {
                addReflectionInstance(constructor, baseClassName);
            } catch (NoSuchMethodException unused2) {
                try {
                    constructor = this.mBaseClass.getDeclaredConstructor(clsArr);
                    constructor.setAccessible(true);
                    addReflectionInstance(constructor, baseClassName);
                } catch (NoSuchMethodException e2) {
                    System.err.println(getBaseClassName() + " No method " + e2);
                }
                if (constructor != null) {
                }
            }
        }
        if (constructor != null) {
            Slog.i(getBaseClassName(), "Cannot invoke there's no constructor.");
            return null;
        }
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(objArr);
        } catch (IllegalAccessException e3) {
            System.err.println(this.getBaseClassName() + " IllegalAccessException encountered invoking constructor " + e3);
            return null;
        } catch (InstantiationException e4) {
            e4.printStackTrace();
            System.err.println(this.getBaseClassName() + " InstantiationException encountered invoking constructor " + e4);
            return null;
        } catch (InvocationTargetException e5) {
            System.err.println(this.getBaseClassName() + " InvocationTargetException encountered invoking constructor " + e5);
            return null;
        }
    }

    public abstract String getBaseClassName();

    public final Object getReflectionInstance(String str) {
        synchronized (this.mNameList) {
            if (str == null) {
                return null;
            }
            int size = this.mNameList.size();
            for (int i = 0; i < size; i++) {
                String str2 = (String) this.mNameList.get(i);
                int length = str2.length();
                if (length == str.length()) {
                    int i2 = length - 1;
                    char[] charArray = str2.toCharArray();
                    char[] charArray2 = str.toCharArray();
                    for (int i3 = 0; i3 < length; i3++) {
                        char c = charArray[i3];
                        if ((charArray2[i3] & c) != c) {
                            break;
                        }
                        if (i3 == i2) {
                            return this.mReflectionList.get(i);
                        }
                    }
                }
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Object invokeNormalMethod(Object obj, String str, Class[] clsArr, Object... objArr) {
        String sb;
        Method method;
        if (obj == null || str.isEmpty()) {
            Slog.i(getBaseClassName(), "Cannot invoke ".concat(str));
            return null;
        }
        if (clsArr == null) {
            sb = str;
        } else {
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str);
            for (Class cls : clsArr) {
                if (cls != null) {
                    m18m.append(cls.getName());
                }
            }
            sb = m18m.toString();
        }
        Object reflectionInstance = getReflectionInstance(sb);
        if (reflectionInstance != null) {
            method = (Method) reflectionInstance;
        } else {
            if (this.mBaseClass != null && !str.isEmpty()) {
                if (clsArr == null) {
                    clsArr = new Class[0];
                }
                try {
                    try {
                        method = this.mBaseClass.getMethod(str, clsArr);
                        addReflectionInstance(method, sb);
                    } catch (NoSuchMethodException e) {
                        System.err.println(getBaseClassName() + " No method " + e);
                    }
                } catch (NoSuchMethodException unused) {
                    method = this.mBaseClass.getDeclaredMethod(str, clsArr);
                    method.setAccessible(true);
                    addReflectionInstance(method, sb);
                }
            }
            method = null;
        }
        if (method == null) {
            Slog.i(getBaseClassName(), "Cannot invoke there's no method reflection : ".concat(str));
            return null;
        }
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            System.err.println(this.getBaseClassName() + " IllegalAccessException encountered invoking " + str + e2);
            return null;
        } catch (InvocationTargetException e3) {
            System.err.println(this.getBaseClassName() + " InvocationTargetException encountered invoking " + str + e3);
            e3.printStackTrace();
            return null;
        }
    }

    public final void loadReflection(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str);
        } catch (ClassNotFoundException e) {
            System.err.println(str + " Unable to load class " + e);
            cls = null;
        }
        this.mBaseClass = cls;
        if (cls == null) {
            Slog.i("AbstractBaseReflection", "There's no class.");
        }
    }

    public AbstractBaseReflection(String str) {
        this.mBaseClass = null;
        this.mNameList = new ArrayList();
        this.mReflectionList = new ArrayList();
        new HashMap();
        loadReflection(str);
    }

    public AbstractBaseReflection(Class<?> cls) {
        this.mBaseClass = null;
        this.mNameList = new ArrayList();
        this.mReflectionList = new ArrayList();
        new HashMap();
        this.mBaseClass = cls;
        if (cls == null) {
            Slog.i("AbstractBaseReflection", "There's no class.");
        }
    }
}
