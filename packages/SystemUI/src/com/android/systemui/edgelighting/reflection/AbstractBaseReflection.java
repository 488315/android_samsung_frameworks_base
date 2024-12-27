package com.android.systemui.edgelighting.reflection;

import android.util.Slog;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object createInstance(java.lang.Class[] r9, java.lang.Object... r10) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.reflection.AbstractBaseReflection.createInstance(java.lang.Class[], java.lang.Object[]):java.lang.Object");
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
            StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
            for (Class cls : clsArr) {
                if (cls != null) {
                    m.append(cls.getName());
                }
            }
            sb = m.toString();
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
                    } catch (NoSuchMethodException unused) {
                        method = this.mBaseClass.getDeclaredMethod(str, clsArr);
                        method.setAccessible(true);
                        addReflectionInstance(method, sb);
                    }
                } catch (NoSuchMethodException e) {
                    System.err.println(getBaseClassName() + " No method " + e);
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
