package androidx.reflect;

import android.util.Log;
import androidx.appcompat.widget.SeslCheckedTextView;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslBaseReflector {
    private SeslBaseReflector() {
    }

    public static Object get(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            Log.e("SeslBaseReflector", field.getName() + " IllegalAccessException", e);
            return null;
        } catch (IllegalArgumentException e2) {
            Log.e("SeslBaseReflector", field.getName() + " IllegalArgumentException", e2);
            return null;
        }
    }

    public static Class getClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            Log.w("SeslBaseReflector", "Fail to get class = ".concat(str));
            return null;
        }
    }

    public static Field getDeclaredField(Class cls, String str) {
        Field field;
        try {
            field = cls.getDeclaredField(str);
            if (field != null) {
                try {
                    field.setAccessible(true);
                } catch (NoSuchFieldException unused) {
                    Log.w("SeslBaseReflector", "Reflector did not find field = ".concat(str));
                    return field;
                }
            }
            return field;
        } catch (NoSuchFieldException unused2) {
            field = null;
        }
    }

    public static Method getDeclaredMethod(String str, String str2, Class... clsArr) {
        Class cls = getClass(str);
        Method method = null;
        if (cls != null) {
            try {
                method = cls.getDeclaredMethod(str2, clsArr);
                if (method != null) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException unused) {
                Log.w("SeslBaseReflector", "Reflector did not find method = ".concat(str2));
            }
        }
        return method;
    }

    public static Method getMethod(String str, String str2, Class... clsArr) {
        Class cls = getClass(str);
        if (cls == null) {
            return null;
        }
        try {
            return cls.getMethod(str2, clsArr);
        } catch (NoSuchMethodException unused) {
            Log.w("SeslBaseReflector", "Reflector did not find method = ".concat(str2));
            return null;
        }
    }

    public static Object invoke(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            Log.e("SeslBaseReflector", method.getName() + " IllegalAccessException", e);
            return null;
        } catch (IllegalArgumentException e2) {
            Log.e("SeslBaseReflector", method.getName() + " IllegalArgumentException", e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.e("SeslBaseReflector", method.getName() + " InvocationTargetException", e3);
            return null;
        }
    }

    public static void set(SeslCheckedTextView seslCheckedTextView, Field field, Object obj) {
        try {
            field.set(seslCheckedTextView, obj);
        } catch (IllegalAccessException e) {
            Log.e("SeslBaseReflector", field.getName() + " IllegalAccessException", e);
        } catch (IllegalArgumentException e2) {
            Log.e("SeslBaseReflector", field.getName() + " IllegalArgumentException", e2);
        }
    }

    public static Method getMethod(Class cls, String str, Class... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException unused) {
            Log.w("SeslBaseReflector", "Reflector did not find method = ".concat(str));
            return null;
        }
    }

    public static Method getDeclaredMethod(Class cls, String str, Class... clsArr) {
        Method method = null;
        if (cls != null) {
            try {
                method = cls.getDeclaredMethod(str, clsArr);
                if (method != null) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException unused) {
                Log.w("SeslBaseReflector", "Reflector did not find method = ".concat(str));
                return method;
            }
        }
        Log.d("SeslBaseReflector", "classT = " + cls + ", methodName = " + str);
        return null;
    }
}
