package com.android.systemui.edgelighting.reflection;

import android.util.Slog;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes2.dex */
public abstract class AbstractBaseReflection {
    public Class mBaseClass;
    public final ArrayList mNameList;
    public final ArrayList mReflectionList;

    public AbstractBaseReflection() throws ClassNotFoundException {
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
    /* JADX WARN: Removed duplicated region for block: B:34:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00a9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object createInstance(Class[] clsArr, Object... objArr) throws NoSuchMethodException, SecurityException {
        Constructor declaredConstructor;
        String baseClassName = getBaseClassName();
        if (clsArr == null) {
            baseClassName = baseClassName.concat("_EMPTY");
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
            declaredConstructor = (Constructor) reflectionInstance;
        } else if (this.mBaseClass == null || baseClassName == null || baseClassName.isEmpty()) {
            declaredConstructor = null;
        } else {
            if (clsArr == null) {
                clsArr = new Class[0];
            }
            try {
                declaredConstructor = this.mBaseClass.getConstructor(clsArr);
            } catch (NoSuchMethodException unused) {
                declaredConstructor = null;
            }
            try {
                addReflectionInstance(declaredConstructor, baseClassName);
            } catch (NoSuchMethodException unused2) {
                try {
                    declaredConstructor = this.mBaseClass.getDeclaredConstructor(clsArr);
                    declaredConstructor.setAccessible(true);
                    addReflectionInstance(declaredConstructor, baseClassName);
                } catch (NoSuchMethodException e2) {
                    System.err.println(getBaseClassName() + " No method " + e2);
                }
                if (declaredConstructor != null) {
                }
            }
        }
        if (declaredConstructor != null) {
            Slog.i(getBaseClassName(), "Cannot invoke there's no constructor.");
            return null;
        }
        try {
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(objArr);
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
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Object invokeNormalMethod(Object obj, String str, Class[] clsArr, Object... objArr) throws NoSuchMethodException, SecurityException {
        String string;
        Method declaredMethod;
        if (obj == null || str.isEmpty()) {
            Slog.i(getBaseClassName(), "Cannot invoke ".concat(str));
            return null;
        }
        if (clsArr == null) {
            string = str;
        } else {
            StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str);
            for (Class cls : clsArr) {
                if (cls != null) {
                    sbM.append(cls.getName());
                }
            }
            string = sbM.toString();
        }
        Object reflectionInstance = getReflectionInstance(string);
        if (reflectionInstance != null) {
            declaredMethod = (Method) reflectionInstance;
        } else if (this.mBaseClass == null || str.isEmpty()) {
            declaredMethod = null;
        } else {
            if (clsArr == null) {
                clsArr = new Class[0];
            }
            try {
                try {
                    declaredMethod = this.mBaseClass.getMethod(str, clsArr);
                    addReflectionInstance(declaredMethod, string);
                } catch (NoSuchMethodException e) {
                    System.err.println(getBaseClassName() + " No method " + e);
                }
            } catch (NoSuchMethodException unused) {
                declaredMethod = this.mBaseClass.getDeclaredMethod(str, clsArr);
                declaredMethod.setAccessible(true);
                addReflectionInstance(declaredMethod, string);
            }
        }
        if (declaredMethod == null) {
            Slog.i(getBaseClassName(), "Cannot invoke there's no method reflection : ".concat(str));
            return null;
        }
        try {
            return declaredMethod.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            System.err.println(this.getBaseClassName() + " IllegalAccessException encountered invoking " + str + e2);
            return null;
        } catch (InvocationTargetException e3) {
            System.err.println(this.getBaseClassName() + " InvocationTargetException encountered invoking " + str + e3);
            e3.printStackTrace();
            return null;
        }
    }

    public final void loadReflection(String str) throws ClassNotFoundException {
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

    public AbstractBaseReflection(String str) throws ClassNotFoundException {
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
