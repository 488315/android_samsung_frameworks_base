package android.util;

import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class DebugUtils {
    public static boolean isObjectSelected(Object obj) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method declaredMethod;
        String str = System.getenv("ANDROID_OBJECT_FILTER");
        if (str != null && str.length() > 0) {
            String[] strArrSplit = str.split("@");
            if (obj.getClass().getSimpleName().matches(strArrSplit[0])) {
                boolean zMatches = false;
                for (int i = 1; i < strArrSplit.length; i++) {
                    String[] strArrSplit2 = strArrSplit[i].split("=");
                    Class<?> cls = obj.getClass();
                    Class<?> cls2 = cls;
                    while (true) {
                        try {
                            declaredMethod = cls2.getDeclaredMethod("get" + strArrSplit2[0].substring(0, 1).toUpperCase(Locale.ROOT) + strArrSplit2[0].substring(1), null);
                            Class<? super Object> superclass = cls.getSuperclass();
                            if (superclass == null || declaredMethod != null) {
                                break;
                            }
                            cls2 = superclass;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e2) {
                            e2.printStackTrace();
                        } catch (InvocationTargetException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (declaredMethod != null) {
                        Object objInvoke = declaredMethod.invoke(obj, null);
                        zMatches |= (objInvoke != null ? objInvoke.toString() : PerfettoProtoLogImpl.NULL_STRING).matches(strArrSplit2[1]);
                    }
                }
                return zMatches;
            }
        }
        return false;
    }

    public static void buildShortClassTag(Object obj, StringBuilder sb) {
        int iLastIndexOf;
        if (obj == null) {
            sb.append(PerfettoProtoLogImpl.NULL_STRING);
            return;
        }
        String simpleName = obj.getClass().getSimpleName();
        if ((simpleName == null || simpleName.isEmpty()) && (iLastIndexOf = (simpleName = obj.getClass().getName()).lastIndexOf(46)) > 0) {
            simpleName = simpleName.substring(iLastIndexOf + 1);
        }
        sb.append(simpleName);
        sb.append('{');
        sb.append(Integer.toHexString(System.identityHashCode(obj)));
    }

    public static void printSizeValue(PrintWriter printWriter, long j) {
        String str;
        String str2;
        float f = j;
        if (f <= 900.0f) {
            str = "";
        } else {
            f /= 1024.0f;
            str = "KB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "MB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "GB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "TB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "PB";
        }
        if (f < 1.0f) {
            str2 = String.format("%.2f", Float.valueOf(f));
        } else if (f < 10.0f) {
            str2 = String.format("%.1f", Float.valueOf(f));
        } else {
            str2 = f < 100.0f ? String.format("%.0f", Float.valueOf(f)) : String.format("%.0f", Float.valueOf(f));
        }
        printWriter.print(str2);
        printWriter.print(str);
    }

    public static String sizeValueToString(long j, StringBuilder sb) {
        String str;
        String str2;
        if (sb == null) {
            sb = new StringBuilder(32);
        }
        float f = j;
        if (f <= 900.0f) {
            str = "";
        } else {
            f /= 1024.0f;
            str = "KB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "MB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "GB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "TB";
        }
        if (f > 900.0f) {
            f /= 1024.0f;
            str = "PB";
        }
        if (f < 1.0f) {
            str2 = String.format("%.2f", Float.valueOf(f));
        } else if (f < 10.0f) {
            str2 = String.format("%.1f", Float.valueOf(f));
        } else {
            str2 = f < 100.0f ? String.format("%.0f", Float.valueOf(f)) : String.format("%.0f", Float.valueOf(f));
        }
        sb.append(str2);
        sb.append(str);
        return sb.toString();
    }

    public static String valueToString(Class<?> cls, String str, int i) {
        Field[] declaredFields = cls.getDeclaredFields();
        int length = declaredFields.length;
        for (int i2 = 0; i2 < length; i2++) {
            Field field = declaredFields[i2];
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && field.getType().equals(Integer.TYPE) && field.getName().startsWith(str)) {
                try {
                    if (i == field.getInt(null)) {
                        return constNameWithoutPrefix(str, field);
                    }
                    continue;
                } catch (IllegalAccessException unused) {
                    continue;
                }
            }
        }
        return Integer.toString(i);
    }

    public static String flagsToString(Class<?> cls, String str, long j) throws IllegalAccessException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        boolean z = j == 0;
        for (Field field : cls.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && ((field.getType().equals(Integer.TYPE) || field.getType().equals(Long.TYPE)) && field.getName().startsWith(str))) {
                long fieldValue = getFieldValue(field);
                if (fieldValue == 0 && z) {
                    return constNameWithoutPrefix(str, field);
                }
                if (fieldValue != 0 && (j & fieldValue) == fieldValue) {
                    j &= ~fieldValue;
                    sb.append(constNameWithoutPrefix(str, field));
                    sb.append('|');
                }
            }
        }
        if (j != 0 || sb.length() == 0) {
            sb.append(Long.toHexString(j));
        } else {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static long getFieldValue(Field field) throws IllegalAccessException, IllegalArgumentException {
        try {
            long j = field.getLong(null);
            if (j != 0) {
                return j;
            }
            int i = field.getInt(null);
            if (i != 0) {
                return i;
            }
            return 0L;
        } catch (IllegalAccessException unused) {
            return 0L;
        }
    }

    public static String constantToString(Class<?> cls, String str, int i) {
        Field[] declaredFields = cls.getDeclaredFields();
        int length = declaredFields.length;
        for (int i2 = 0; i2 < length; i2++) {
            Field field = declaredFields[i2];
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && field.getType().equals(Integer.TYPE) && field.getName().startsWith(str) && field.getInt(null) == i) {
                return constNameWithoutPrefix(str, field);
            }
        }
        return str + Integer.toString(i);
    }

    private static String constNameWithoutPrefix(String str, Field field) {
        return field.getName().substring(str.length());
    }

    public static List<String> callersWithin(final Class<?> cls, int i) {
        List<String> list = (List) Arrays.stream(Thread.currentThread().getStackTrace()).skip(i + 3).filter(new Predicate() { // from class: android.util.DebugUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((StackTraceElement) obj).getClassName().startsWith(cls.getName());
            }
        }).map(new Function() { // from class: android.util.DebugUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((StackTraceElement) obj).getMethodName();
            }
        }).collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }
}
