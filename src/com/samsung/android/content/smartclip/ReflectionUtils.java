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
        String[] split = str.split("\\.");
        if (split.length == 0) {
            return "";
        }
        return split[split.length - 1];
    }

    public static void dumpObjectFieldsWithClassTypeFilter(Object obj, String str, int i, String str2) {
        ArrayList arrayList = new ArrayList();
        Log.e(TAG, "-------- Field list dump start : " + obj.toString() + " / Object type filter : " + str2 + " ----------");
        dumpObjectFields(obj, arrayList, str, null, "", 0, i, str2, null);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    public static void dumpObjectFieldsWithValueFilter(Object obj, String str, int i, String str2) {
        ArrayList arrayList = new ArrayList();
        Log.e(TAG, "-------- Field list dump start : " + obj.toString() + " / Value filter : " + str2 + " ----------");
        dumpObjectFields(obj, arrayList, str, null, "", 0, i, null, str2);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    public static void dumpObjectFields(Object obj, String str, int i) {
        ArrayList arrayList = new ArrayList();
        Log.e(TAG, "-------- Field list dump start : " + obj.toString() + " ----------");
        dumpObjectFields(obj, arrayList, str, null, "", 0, i, null, null);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x020f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static void dumpObjectFields(java.lang.Object r20, java.util.ArrayList<java.lang.Object> r21, java.lang.String r22, java.lang.reflect.Field r23, java.lang.String r24, int r25, int r26, java.lang.String r27, java.lang.String r28) {
        /*
            Method dump skipped, instructions count: 578
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.content.smartclip.ReflectionUtils.dumpObjectFields(java.lang.Object, java.util.ArrayList, java.lang.String, java.lang.reflect.Field, java.lang.String, int, int, java.lang.String, java.lang.String):void");
    }

    public static void dumpObjectMethods(Object obj) {
        Log.d(TAG, "-------- Method list dump start : " + obj.toString() + " ----------");
        for (Class<?> cls = obj.getClass(); cls != null; cls = cls.getSuperclass()) {
            Log.d(TAG, " -- Methods of " + cls.getName() + " class --");
            for (Method method : cls.getDeclaredMethods()) {
                Log.d(TAG, method.toGenericString());
            }
        }
        Log.d(TAG, "-------- Method list dump finished ----------");
    }

    public static void dumpClassHierarchy(Object obj) {
        Log.d(TAG, "-------- Class hierarchy dump start : " + obj.toString() + " ----------");
        for (Class<?> cls = obj.getClass(); cls != null; cls = cls.getSuperclass()) {
            Log.d(TAG, "-- Class name : " + cls.getName());
            for (Class<?> cls2 : cls.getInterfaces()) {
                Log.d(TAG, "   + interfaces : " + cls2.getName());
            }
        }
        Log.d(TAG, "-------- Class hierarchy dump finished ----------");
    }

    protected static void getFieldObjectByObjectType(Object obj, int i, String str, int i2, ArrayList<Object> arrayList, int i3, int i4, boolean z) {
        int i5;
        boolean endsWith;
        int i6;
        String name;
        String str2 = str;
        if (obj == null || str2 == null || i3 == (i5 = i4)) {
            return;
        }
        Class<?> cls = obj.getClass();
        while (cls != null) {
            if (z && (name = cls.getName()) != null && (name.startsWith("android.view.") || name.startsWith("java."))) {
                return;
            }
            Field[] declaredFields = cls.getDeclaredFields();
            int length = declaredFields.length;
            int i7 = 0;
            while (i7 < length) {
                Field field = declaredFields[i7];
                String name2 = field.getType().getName();
                try {
                    boolean isAccessible = field.isAccessible();
                    boolean z2 = true;
                    field.setAccessible(true);
                    Object obj2 = field.get(obj);
                    field.setAccessible(isAccessible);
                    if (obj2 != null) {
                        if (i == 1) {
                            endsWith = name2.endsWith(MediaMetrics.SEPARATOR + str2);
                        } else {
                            endsWith = name2.equals(str2);
                        }
                        if (endsWith) {
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
            cls = cls.getSuperclass();
            str2 = str;
            i5 = i4;
        }
    }

    public static Object[] getFieldObjectByObjectType(Object obj, int i, String str, int i2, boolean z) {
        return getFieldObjectByObjectType(obj, i, str, i2, 1, z);
    }

    public static Object[] getFieldObjectByObjectType(Object obj, int i, String str, int i2, int i3, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (obj == null || str == null) {
            return arrayList.toArray();
        }
        getFieldObjectByObjectType(obj, i, str, i2, arrayList, 0, i3, z);
        return arrayList.toArray();
    }

    public static Object getFieldObjectByFieldName(Object obj, String str) {
        if (obj != null && str != null) {
            for (Class<?> cls = obj.getClass(); cls != null; cls = cls.getSuperclass()) {
                for (Field field : cls.getDeclaredFields()) {
                    if (str.equals(field.getName())) {
                        try {
                            boolean isAccessible = field.isAccessible();
                            field.setAccessible(true);
                            Object obj2 = field.get(obj);
                            field.setAccessible(isAccessible);
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
