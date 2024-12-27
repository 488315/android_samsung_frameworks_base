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
class ReflectionUtils {
    public static final int MATCH_TYPE_CLASS_NAME_ONLY = 1;
    public static final int MATCH_TYPE_FULL_NAME = 0;
    private static final String TAG = "ReflectionUtils";

    ReflectionUtils() {}

    protected static int getArraySize(Object array, String objectTypeStr) {
        if (objectTypeStr.startsWith("[I")) {
            int arrayLength = ((int[]) array).length;
            return arrayLength;
        }
        if (objectTypeStr.startsWith("[Z")) {
            int arrayLength2 = ((boolean[]) array).length;
            return arrayLength2;
        }
        if (objectTypeStr.startsWith("[J")) {
            int arrayLength3 = ((long[]) array).length;
            return arrayLength3;
        }
        if (objectTypeStr.startsWith("[B")) {
            int arrayLength4 = ((byte[]) array).length;
            return arrayLength4;
        }
        if (objectTypeStr.startsWith("[F")) {
            int arrayLength5 = ((float[]) array).length;
            return arrayLength5;
        }
        if (objectTypeStr.startsWith("[C")) {
            int arrayLength6 = ((char[]) array).length;
            return arrayLength6;
        }
        if (objectTypeStr.startsWith("[S")) {
            int arrayLength7 = ((short[]) array).length;
            return arrayLength7;
        }
        if (objectTypeStr.startsWith("[D")) {
            int arrayLength8 = ((double[]) array).length;
            return arrayLength8;
        }
        if (objectTypeStr.startsWith("[L")) {
            int arrayLength9 = ((Object[]) array).length;
            return arrayLength9;
        }
        return 0;
    }

    protected static Object getArrayValueObject(Object array, int index) {
        Class<?> cls = array.getClass();
        String dataTypeStr = cls.getName();
        if (dataTypeStr.startsWith("[I")) {
            return Integer.valueOf(((int[]) array)[index]);
        }
        if (dataTypeStr.startsWith("[Z")) {
            return Boolean.valueOf(((boolean[]) array)[index]);
        }
        if (dataTypeStr.startsWith("[J")) {
            return Long.valueOf(((long[]) array)[index]);
        }
        if (dataTypeStr.startsWith("[B")) {
            return Byte.valueOf(((byte[]) array)[index]);
        }
        if (dataTypeStr.startsWith("[F")) {
            return Float.valueOf(((float[]) array)[index]);
        }
        if (dataTypeStr.startsWith("[C")) {
            return Integer.valueOf(((char[]) array)[index]);
        }
        if (dataTypeStr.startsWith("[S")) {
            return Short.valueOf(((short[]) array)[index]);
        }
        if (dataTypeStr.startsWith("[D")) {
            return Double.valueOf(((double[]) array)[index]);
        }
        if (dataTypeStr.startsWith("[L")) {
            return ((Object[]) array)[index];
        }
        return "Unknown(" + dataTypeStr + NavigationBarInflaterView.KEY_CODE_END;
    }

    protected static boolean isPrimitiveDataType(String dataType) {
        return dataType.equals("short")
                || dataType.equals("int")
                || dataType.equals(SliceItem.FORMAT_LONG)
                || dataType.equals("char")
                || dataType.equals("byte")
                || dataType.equals(SemRemoteViewsValueAnimation.VALUE_TYPE_FLOAT)
                || dataType.equals("double")
                || dataType.equals("boolean");
    }

    protected static int findObjFromArrayList(ArrayList<Object> arrayList, Object objToFind) {
        int arraySize = arrayList.size();
        for (int i = 0; i < arraySize; i++) {
            if (objToFind == arrayList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    protected static String getIndentString(int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("\t");
        }
        return indent.toString();
    }

    protected static String extractClassNameFromFullClassPath(String classPath) {
        String[] strs = classPath.split("\\.");
        if (strs.length == 0) {
            return "";
        }
        return strs[strs.length - 1];
    }

    public static void dumpObjectFieldsWithClassTypeFilter(
            Object objToDump, String objName, int maxDepth, String classTypeFilter) {
        ArrayList<Object> objList = new ArrayList<>();
        Log.e(
                TAG,
                "-------- Field list dump start : "
                        + objToDump.toString()
                        + " / Object type filter : "
                        + classTypeFilter
                        + " ----------");
        dumpObjectFields(objToDump, objList, objName, null, "", 0, maxDepth, classTypeFilter, null);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    public static void dumpObjectFieldsWithValueFilter(
            Object objToDump, String objName, int maxDepth, String valueFilter) {
        ArrayList<Object> objList = new ArrayList<>();
        Log.e(
                TAG,
                "-------- Field list dump start : "
                        + objToDump.toString()
                        + " / Value filter : "
                        + valueFilter
                        + " ----------");
        dumpObjectFields(objToDump, objList, objName, null, "", 0, maxDepth, null, valueFilter);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    public static void dumpObjectFields(Object objToDump, String objName, int maxDepth) {
        ArrayList<Object> objList = new ArrayList<>();
        Log.e(TAG, "-------- Field list dump start : " + objToDump.toString() + " ----------");
        dumpObjectFields(objToDump, objList, objName, null, "", 0, maxDepth, null, null);
        Log.e(TAG, "-------- Field list dump finished ----------");
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0335  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x035b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static void dumpObjectFields(
            java.lang.Object r31,
            java.util.ArrayList<java.lang.Object> r32,
            java.lang.String r33,
            java.lang.reflect.Field r34,
            java.lang.String r35,
            int r36,
            int r37,
            java.lang.String r38,
            java.lang.String r39) {
        /*
            Method dump skipped, instructions count: 860
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.content.smartclip.ReflectionUtils.dumpObjectFields(java.lang.Object,"
                    + " java.util.ArrayList, java.lang.String, java.lang.reflect.Field,"
                    + " java.lang.String, int, int, java.lang.String, java.lang.String):void");
    }

    public static void dumpObjectMethods(Object objToDump) {
        Log.d(TAG, "-------- Method list dump start : " + objToDump.toString() + " ----------");
        for (Class<?> curObjClass = objToDump.getClass();
                curObjClass != null;
                curObjClass = curObjClass.getSuperclass()) {
            Log.d(TAG, " -- Methods of " + curObjClass.getName() + " class --");
            Method[] methods = curObjClass.getDeclaredMethods();
            for (Method method : methods) {
                String curMethodName = method.toGenericString();
                Log.d(TAG, curMethodName);
            }
        }
        Log.d(TAG, "-------- Method list dump finished ----------");
    }

    public static void dumpClassHierarchy(Object objToDump) {
        Class<?> objClass = objToDump.getClass();
        Log.d(TAG, "-------- Class hierarchy dump start : " + objToDump.toString() + " ----------");
        for (Class<?> c = objClass; c != null; c = c.getSuperclass()) {
            Log.d(TAG, "-- Class name : " + c.getName());
            Class<?>[] clz = c.getInterfaces();
            for (Class<?> cls : clz) {
                Log.d(TAG, "   + interfaces : " + cls.getName());
            }
        }
        Log.d(TAG, "-------- Class hierarchy dump finished ----------");
    }

    protected static void getFieldObjectByObjectType(
            Object srcObj,
            int matchType,
            String fieldObjectType,
            int maxSearchResultCount,
            ArrayList<Object> searchResult,
            int curDepth,
            int maxDepth,
            boolean skipWellKnownClass) {
        int i;
        int i2;
        Field[] fields;
        boolean accessable;
        String className;
        if (srcObj == null || fieldObjectType == null) {
            return;
        }
        if (curDepth == maxDepth) {
            return;
        }
        for (Class<?> curObjClass = srcObj.getClass();
                curObjClass != null;
                curObjClass = curObjClass.getSuperclass()) {
            boolean z = true;
            if (!skipWellKnownClass
                    || (className = curObjClass.getName()) == null
                    || (!className.startsWith("android.view.") && !className.startsWith("java."))) {
                Field[] fields2 = curObjClass.getDeclaredFields();
                int length = fields2.length;
                int i3 = 0;
                while (i3 < length) {
                    Field field = fields2[i3];
                    Class<?> classType = field.getType();
                    String fieldType = classType.getName();
                    try {
                        boolean accessable2 = field.isAccessible();
                        field.setAccessible(z);
                        Object memberObj = field.get(srcObj);
                        field.setAccessible(accessable2);
                        if (memberObj != null) {
                            switch (matchType) {
                                case 1:
                                    accessable =
                                            fieldType.endsWith(
                                                    MediaMetrics.SEPARATOR + fieldObjectType);
                                    break;
                                default:
                                    accessable = fieldType.equals(fieldObjectType);
                                    break;
                            }
                            if (accessable) {
                                try {
                                    boolean haveSameObject = false;
                                    Iterator<Object> it = searchResult.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            Object o = it.next();
                                            if (o == memberObj) {
                                                haveSameObject = true;
                                            }
                                        }
                                    }
                                    if (!haveSameObject) {
                                        try {
                                            searchResult.add(memberObj);
                                        } catch (IllegalAccessException
                                                | IllegalArgumentException e) {
                                            e = e;
                                            i = i3;
                                            i2 = length;
                                            fields = fields2;
                                            Log.e(
                                                    TAG,
                                                    "Exception occurred in"
                                                        + " getFieldObjectByObjectType : "
                                                            + e.toString());
                                            i3 = i + 1;
                                            fields2 = fields;
                                            length = i2;
                                            z = true;
                                        }
                                    }
                                    i = i3;
                                    i2 = length;
                                    fields = fields2;
                                } catch (IllegalAccessException | IllegalArgumentException e2) {
                                    e = e2;
                                }
                            } else {
                                i = i3;
                                i2 = length;
                                fields = fields2;
                                getFieldObjectByObjectType(
                                        memberObj,
                                        matchType,
                                        fieldObjectType,
                                        maxSearchResultCount,
                                        searchResult,
                                        curDepth + 1,
                                        maxDepth,
                                        skipWellKnownClass);
                            }
                            if (maxSearchResultCount > 0) {
                                try {
                                    if (searchResult.size() >= maxSearchResultCount) {
                                        return;
                                    }
                                } catch (IllegalAccessException | IllegalArgumentException e3) {
                                    e = e3;
                                    Log.e(
                                            TAG,
                                            "Exception occurred in getFieldObjectByObjectType : "
                                                    + e.toString());
                                    i3 = i + 1;
                                    fields2 = fields;
                                    length = i2;
                                    z = true;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            i = i3;
                            i2 = length;
                            fields = fields2;
                        }
                    } catch (IllegalAccessException | IllegalArgumentException e4) {
                        e = e4;
                        i = i3;
                        i2 = length;
                        fields = fields2;
                    }
                    i3 = i + 1;
                    fields2 = fields;
                    length = i2;
                    z = true;
                }
            }
            return;
        }
    }

    public static Object[] getFieldObjectByObjectType(
            Object srcObj,
            int matchType,
            String fieldObjectType,
            int maxSearchResultCount,
            boolean skipWellKnownClass) {
        return getFieldObjectByObjectType(
                srcObj, matchType, fieldObjectType, maxSearchResultCount, 1, skipWellKnownClass);
    }

    public static Object[] getFieldObjectByObjectType(
            Object srcObj,
            int matchType,
            String fieldObjectType,
            int maxSearchResultCount,
            int maxDepth,
            boolean skipWellKnownClass) {
        ArrayList<Object> searchResult = new ArrayList<>();
        if (srcObj == null || fieldObjectType == null) {
            return searchResult.toArray();
        }
        getFieldObjectByObjectType(
                srcObj,
                matchType,
                fieldObjectType,
                maxSearchResultCount,
                searchResult,
                0,
                maxDepth,
                skipWellKnownClass);
        return searchResult.toArray();
    }

    public static Object getFieldObjectByFieldName(Object srcObj, String fieldName) {
        if (srcObj == null || fieldName == null) {
            return null;
        }
        for (Class<?> curObjClass = srcObj.getClass();
                curObjClass != null;
                curObjClass = curObjClass.getSuperclass()) {
            Field[] fields = curObjClass.getDeclaredFields();
            for (Field field : fields) {
                String curFieldName = field.getName();
                if (fieldName.equals(curFieldName)) {
                    try {
                        boolean accessable = field.isAccessible();
                        field.setAccessible(true);
                        Object fieldObject = field.get(srcObj);
                        field.setAccessible(accessable);
                        return fieldObject;
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        Log.e(
                                TAG,
                                "Exception occurred in getFieldObjectByFieldName : "
                                        + e.toString());
                    }
                }
            }
        }
        return null;
    }
}
