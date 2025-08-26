package com.samsung.android.content.smartclip;

import android.app.slice.SliceItem;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.util.Log;
import android.widget.SemRemoteViewsValueAnimation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: SmartClipDataCropperImpl.java */
/* loaded from: classes6.dex */
class ReflectionUtils {
    public static final int MATCH_TYPE_CLASS_NAME_ONLY = 1;
    public static final int MATCH_TYPE_FULL_NAME = 0;
    private static final String TAG = "ReflectionUtils";

    ReflectionUtils() {
    }

    protected static int getArraySize(Object obj, String str) {
        if (str.startsWith("[I")) {
            return ((int[]) obj).length;
        }
        if (str.startsWith("[Z")) {
            return ((boolean[]) obj).length;
        }
        if (str.startsWith("[J")) {
            return ((long[]) obj).length;
        }
        if (str.startsWith("[B")) {
            return ((byte[]) obj).length;
        }
        if (str.startsWith("[F")) {
            return ((float[]) obj).length;
        }
        if (str.startsWith("[C")) {
            return ((char[]) obj).length;
        }
        if (str.startsWith("[S")) {
            return ((short[]) obj).length;
        }
        if (str.startsWith("[D")) {
            return ((double[]) obj).length;
        }
        if (str.startsWith("[L")) {
            return ((Object[]) obj).length;
        }
        return 0;
    }

    protected static Object getArrayValueObject(Object obj, int i) {
        String name = obj.getClass().getName();
        if (name.startsWith("[I")) {
            return Integer.valueOf(((int[]) obj)[i]);
        }
        if (name.startsWith("[Z")) {
            return Boolean.valueOf(((boolean[]) obj)[i]);
        }
        if (name.startsWith("[J")) {
            return Long.valueOf(((long[]) obj)[i]);
        }
        if (name.startsWith("[B")) {
            return Byte.valueOf(((byte[]) obj)[i]);
        }
        if (name.startsWith("[F")) {
            return Float.valueOf(((float[]) obj)[i]);
        }
        if (name.startsWith("[C")) {
            return Integer.valueOf(((char[]) obj)[i]);
        }
        if (name.startsWith("[S")) {
            return Short.valueOf(((short[]) obj)[i]);
        }
        if (name.startsWith("[D")) {
            return Double.valueOf(((double[]) obj)[i]);
        }
        if (name.startsWith("[L")) {
            return ((Object[]) obj)[i];
        }
        return "Unknown(" + name + NavigationBarInflaterView.KEY_CODE_END;
    }

    protected static boolean isPrimitiveDataType(String str) {
        return str.equals("short") || str.equals("int") || str.equals(SliceItem.FORMAT_LONG) || str.equals("char") || str.equals("byte") || str.equals(SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT) || str.equals("double") || str.equals("boolean");
    }

    protected static int findObjFromArrayList(ArrayList<Object> arrayList, Object obj) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (obj == arrayList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    protected static String getIndentString(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    protected static String extractClassNameFromFullClassPath(String str) {
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length == 0) {
            return "";
        }
        return strArrSplit[strArrSplit.length - 1];
    }

    public static void dumpObjectFieldsWithClassTypeFilter(Object obj, String str, int i, String str2) throws IllegalAccessException, IllegalArgumentException {
        ArrayList arrayList = new ArrayList();
        Log.e(TAG, "-------- Field list dump start : " + obj.toString() + " / Object type filter : " + str2 + " ----------");
        dumpObjectFields(obj, arrayList, str, null, "", 0, i, str2, null);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    public static void dumpObjectFieldsWithValueFilter(Object obj, String str, int i, String str2) throws IllegalAccessException, IllegalArgumentException {
        ArrayList arrayList = new ArrayList();
        Log.e(TAG, "-------- Field list dump start : " + obj.toString() + " / Value filter : " + str2 + " ----------");
        dumpObjectFields(obj, arrayList, str, null, "", 0, i, null, str2);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    public static void dumpObjectFields(Object obj, String str, int i) throws IllegalAccessException, IllegalArgumentException {
        ArrayList arrayList = new ArrayList();
        Log.e(TAG, "-------- Field list dump start : " + obj.toString() + " ----------");
        dumpObjectFields(obj, arrayList, str, null, "", 0, i, null, null);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0218  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected static void dumpObjectFields(Object obj, ArrayList<Object> arrayList, String str, Field field, String str2, int i, int i2, String str3, String str4) throws IllegalAccessException, IllegalArgumentException {
        String string;
        boolean z;
        CharSequence charSequence;
        boolean z2;
        Object obj2;
        ArrayList<Object> arrayList2 = arrayList;
        int i3 = i2;
        if (obj == null) {
            return;
        }
        Class<?> cls = obj.getClass();
        String name = cls.getName();
        boolean z3 = findObjFromArrayList(arrayList2, obj) != -1;
        if (cls.isPrimitive() || name.contains("java.lang.")) {
            string = obj.toString();
        } else {
            string = "@" + Integer.toHexString(obj.hashCode());
        }
        if (cls.isArray()) {
            string = string + " [arraySize = " + getArraySize(obj, name) + NavigationBarInflaterView.SIZE_MOD_END;
        }
        String indentString = getIndentString(i);
        String strReplace = (field != null ? field.getType().getName() : "").replace("[L", "");
        String str5 = str == null ? "" : str;
        boolean z4 = true;
        StringBuilder sb = new StringBuilder();
        sb.append(str5);
        sb.append(field != null ? field.getName() : "");
        String string2 = sb.toString();
        String str6 = str2 == null ? "" : str2;
        Class<?> superclass = cls;
        if ((str3 == null || str3.equals(name)) && (str4 == null || str4.equals(string))) {
            z = z3;
            if (superclass.isPrimitive() || strReplace.equals(name)) {
                charSequence = "java.lang.";
                Log.e(TAG, indentString + string2 + " = " + string + " (" + strReplace + ") : " + str6);
            } else {
                charSequence = "java.lang.";
                Log.e(TAG, indentString + string2 + " = " + string + " (" + strReplace + " / " + name + ") : " + str6);
            }
        } else {
            z = z3;
            charSequence = "java.lang.";
        }
        if (!str6.equals("")) {
            str6 = str6 + MediaMetrics.SEPARATOR;
        }
        String str7 = str6 + string2 + NavigationBarInflaterView.KEY_CODE_START + extractClassNameFromFullClassPath(name) + NavigationBarInflaterView.KEY_CODE_END;
        if (z) {
            return;
        }
        int i4 = i + 1;
        if (i4 < i3) {
            arrayList2.add(obj);
        }
        if (superclass.isArray()) {
            int arraySize = getArraySize(obj, name);
            int i5 = 0;
            while (i5 < arraySize && i5 < 100) {
                Object arrayValueObject = getArrayValueObject(obj, i5);
                if (arrayValueObject != null && (!arrayValueObject.getClass().isPrimitive() || !arrayValueObject.toString().equals("0"))) {
                    dumpObjectFields(arrayValueObject, arrayList2, NavigationBarInflaterView.SIZE_MOD_START + i5 + NavigationBarInflaterView.SIZE_MOD_END, null, str7, i4, i3, str3, str4);
                }
                i5++;
                arrayList2 = arrayList;
            }
            if (arraySize > 100) {
                Log.e(TAG, indentString + "\t[Dumped until index 100]");
                return;
            }
            return;
        }
        if (isPrimitiveDataType(name) || name.contains(charSequence)) {
            return;
        }
        while (superclass != null) {
            Field[] declaredFields = superclass.getDeclaredFields();
            int length = declaredFields.length;
            int i6 = 0;
            while (i6 < length) {
                Field field2 = declaredFields[i6];
                try {
                    boolean zIsAccessible = field2.isAccessible();
                    z2 = z4;
                    try {
                        field2.setAccessible(z2);
                        obj2 = field2.get(obj);
                        field2.setAccessible(zIsAccessible);
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        e = e;
                        e.printStackTrace();
                        obj2 = null;
                        if ((field2.getModifiers() & 16) == 0) {
                        }
                        if ((field2.getModifiers() & 8) == 0) {
                        }
                        if (field2.isEnumConstant()) {
                        }
                        i6++;
                        i3 = i2;
                        z4 = z2;
                    }
                } catch (IllegalAccessException | IllegalArgumentException e2) {
                    e = e2;
                    z2 = z4;
                }
                boolean z5 = (field2.getModifiers() & 16) == 0 ? z2 : false;
                boolean z6 = (field2.getModifiers() & 8) == 0 ? z2 : false;
                if (field2.isEnumConstant() && ((!z6 || !z5) && i4 < i3)) {
                    dumpObjectFields(obj2, arrayList, null, field2, str7, i4, i3, str3, str4);
                }
                i6++;
                i3 = i2;
                z4 = z2;
            }
            superclass = superclass.getSuperclass();
            i3 = i2;
        }
    }

    public static void dumpObjectMethods(Object obj) throws SecurityException {
        Log.d(TAG, "-------- Method list dump start : " + obj.toString() + " ----------");
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            Log.d(TAG, " -- Methods of " + superclass.getName() + " class --");
            for (Method method : superclass.getDeclaredMethods()) {
                Log.d(TAG, method.toGenericString());
            }
        }
        Log.d(TAG, "-------- Method list dump finished ----------");
    }

    public static void dumpClassHierarchy(Object obj) {
        Log.d(TAG, "-------- Class hierarchy dump start : " + obj.toString() + " ----------");
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            Log.d(TAG, "-- Class name : " + superclass.getName());
            for (Class<?> cls : superclass.getInterfaces()) {
                Log.d(TAG, "   + interfaces : " + cls.getName());
            }
        }
        Log.d(TAG, "-------- Class hierarchy dump finished ----------");
    }

    protected static void getFieldObjectByObjectType(Object obj, int i, String str, int i2, ArrayList<Object> arrayList, int i3, int i4, boolean z) throws IllegalAccessException, IllegalArgumentException {
        int i5;
        boolean zEndsWith;
        int i6;
        String name;
        String str2 = str;
        if (obj == null || str2 == null || i3 == (i5 = i4)) {
            return;
        }
        Class<?> superclass = obj.getClass();
        while (superclass != null) {
            if (z && (name = superclass.getName()) != null && (name.startsWith("android.view.") || name.startsWith("java."))) {
                return;
            }
            Field[] declaredFields = superclass.getDeclaredFields();
            int length = declaredFields.length;
            int i7 = 0;
            while (i7 < length) {
                Field field = declaredFields[i7];
                String name2 = field.getType().getName();
                try {
                    boolean zIsAccessible = field.isAccessible();
                    boolean z2 = true;
                    field.setAccessible(true);
                    Object obj2 = field.get(obj);
                    field.setAccessible(zIsAccessible);
                    if (obj2 != null) {
                        if (i == 1) {
                            zEndsWith = name2.endsWith(MediaMetrics.SEPARATOR + str2);
                        } else {
                            zEndsWith = name2.equals(str2);
                        }
                        if (zEndsWith) {
                            try {
                                Iterator<Object> it = arrayList.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        z2 = false;
                                        break;
                                    } else if (it.next() == obj2) {
                                        break;
                                    }
                                }
                                if (!z2) {
                                    arrayList.add(obj2);
                                }
                                i6 = i2;
                            } catch (IllegalAccessException | IllegalArgumentException e) {
                                e = e;
                                Log.e(TAG, "Exception occurred in getFieldObjectByObjectType : " + e.toString());
                                i7++;
                                str2 = str;
                                i5 = i4;
                            }
                        } else {
                            i6 = i2;
                            getFieldObjectByObjectType(obj2, i, str2, i6, arrayList, i3 + 1, i5, z);
                        }
                        if (i6 > 0) {
                            try {
                                if (arrayList.size() >= i6) {
                                    return;
                                }
                            } catch (IllegalAccessException | IllegalArgumentException e2) {
                                e = e2;
                                Log.e(TAG, "Exception occurred in getFieldObjectByObjectType : " + e.toString());
                                i7++;
                                str2 = str;
                                i5 = i4;
                            }
                        } else {
                            continue;
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException e3) {
                    e = e3;
                }
                i7++;
                str2 = str;
                i5 = i4;
            }
            superclass = superclass.getSuperclass();
            str2 = str;
            i5 = i4;
        }
    }

    public static Object[] getFieldObjectByObjectType(Object obj, int i, String str, int i2, boolean z) {
        return getFieldObjectByObjectType(obj, i, str, i2, 1, z);
    }

    public static Object[] getFieldObjectByObjectType(Object obj, int i, String str, int i2, int i3, boolean z) throws IllegalAccessException, IllegalArgumentException {
        ArrayList arrayList = new ArrayList();
        if (obj == null || str == null) {
            return arrayList.toArray();
        }
        getFieldObjectByObjectType(obj, i, str, i2, arrayList, 0, i3, z);
        return arrayList.toArray();
    }

    public static Object getFieldObjectByFieldName(Object obj, String str) throws IllegalAccessException, IllegalArgumentException {
        if (obj != null && str != null) {
            for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                for (Field field : superclass.getDeclaredFields()) {
                    if (str.equals(field.getName())) {
                        try {
                            boolean zIsAccessible = field.isAccessible();
                            field.setAccessible(true);
                            Object obj2 = field.get(obj);
                            field.setAccessible(zIsAccessible);
                            return obj2;
                        } catch (IllegalAccessException | IllegalArgumentException e) {
                            Log.e(TAG, "Exception occurred in getFieldObjectByFieldName : " + e.toString());
                        }
                    }
                }
            }
        }
        return null;
    }
}
