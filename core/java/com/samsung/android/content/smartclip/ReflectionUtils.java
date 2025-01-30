package com.samsung.android.content.smartclip;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.util.Log;
import android.widget.SemRemoteViewsValueAnimation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: SmartClipDataCropperImpl.java */
/* loaded from: classes5.dex */
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
        || dataType.equals("long")
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
    Log.m96e(
        TAG,
        "-------- Field list dump start : "
            + objToDump.toString()
            + " / Object type filter : "
            + classTypeFilter
            + " ----------");
    dumpObjectFields(objToDump, objList, objName, null, "", 0, maxDepth, classTypeFilter, null);
    Log.m96e(TAG, "-------- Field list dump finished ----------");
  }

  public static void dumpObjectFieldsWithValueFilter(
      Object objToDump, String objName, int maxDepth, String valueFilter) {
    ArrayList<Object> objList = new ArrayList<>();
    Log.m96e(
        TAG,
        "-------- Field list dump start : "
            + objToDump.toString()
            + " / Value filter : "
            + valueFilter
            + " ----------");
    dumpObjectFields(objToDump, objList, objName, null, "", 0, maxDepth, null, valueFilter);
    Log.m96e(TAG, "-------- Field list dump finished ----------");
  }

  public static void dumpObjectFields(Object objToDump, String objName, int maxDepth) {
    ArrayList<Object> objList = new ArrayList<>();
    Log.m96e(TAG, "-------- Field list dump start : " + objToDump.toString() + " ----------");
    dumpObjectFields(objToDump, objList, objName, null, "", 0, maxDepth, null, null);
    Log.m96e(TAG, "-------- Field list dump finished ----------");
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
  */
  protected static void dumpObjectFields(
      Object objToDump,
      ArrayList<Object> dumpedObj,
      String fieldNamePrefix,
      Field fieldInfo,
      String fullPath,
      int depth,
      int maxDepth,
      String classTypeFilter,
      String valueFilter) {
    String objectValueStr;
    String objectValueStr2;
    String fieldNamePrefix2;
    String fullPath2;
    CharSequence charSequence;
    boolean z;
    String fieldTypeStr;
    Object obj;
    Class<?> curObjClass;
    boolean z2;
    Object fieldObject;
    boolean isEnumField;
    int i;
    int i2;
    String indent;
    boolean accessable;
    String indent2;
    String fieldNamePrefix3;
    Class<?> curObjClass2;
    String str;
    String indent3;
    String objectValueStr3;
    int i3;
    String fieldName;
    String str2;
    String fieldTypeStr2;
    if (objToDump == null) {
      return;
    }
    Class<?> curObjClass3 = objToDump.getClass();
    String objectTypeStr = curObjClass3.getName();
    boolean alreadyDumpedObj = findObjFromArrayList(dumpedObj, objToDump) != -1;
    if (curObjClass3.isPrimitive() || objectTypeStr.contains("java.lang.")) {
      objectValueStr = objToDump.toString();
    } else {
      objectValueStr = "@" + Integer.toHexString(objToDump.hashCode());
    }
    if (curObjClass3.isArray()) {
      objectValueStr2 =
          objectValueStr
              + " [arraySize = "
              + getArraySize(objToDump, objectTypeStr)
              + NavigationBarInflaterView.SIZE_MOD_END;
    } else {
      objectValueStr2 = objectValueStr;
    }
    String indent4 = getIndentString(depth);
    String fieldTypeStr3 =
        (fieldInfo != null ? fieldInfo.getType().getName() : "").replace("[L", "");
    if (fieldNamePrefix != null) {
      fieldNamePrefix2 = fieldNamePrefix;
    } else {
      fieldNamePrefix2 = "";
    }
    String fieldName2 = fieldNamePrefix2 + (fieldInfo != null ? fieldInfo.getName() : "");
    if (fullPath != null) {
      fullPath2 = fullPath;
    } else {
      fullPath2 = "";
    }
    String fieldNamePrefix4 = fieldNamePrefix2;
    String fieldNamePrefix5 = TAG;
    if (classTypeFilter != null) {
      charSequence = "java.lang.";
      z = true;
      if (!classTypeFilter.equals(objectTypeStr)) {
        fieldTypeStr = NavigationBarInflaterView.SIZE_MOD_END;
        if (!fullPath2.equals("")) {
          fullPath2 = fullPath2 + MediaMetrics.SEPARATOR;
        }
        String fullPath3 =
            fullPath2
                + fieldName2
                + NavigationBarInflaterView.KEY_CODE_START
                + extractClassNameFromFullClassPath(objectTypeStr)
                + NavigationBarInflaterView.KEY_CODE_END;
        if (alreadyDumpedObj) {
          if (depth + 1 >= maxDepth) {
            obj = objToDump;
          } else {
            obj = objToDump;
            dumpedObj.add(obj);
          }
          if (curObjClass3.isArray()) {
            int arrayLength = getArraySize(obj, objectTypeStr);
            int i4 = 0;
            while (i4 < arrayLength && i4 < 100) {
              Object o = getArrayValueObject(obj, i4);
              if (o == null) {
                fieldNamePrefix3 = fieldNamePrefix4;
                curObjClass2 = curObjClass3;
                str = fieldNamePrefix5;
                indent3 = indent4;
                objectValueStr3 = objectValueStr2;
                i3 = i4;
                fieldName = fieldName2;
                str2 = fieldTypeStr;
                fieldTypeStr2 = fieldTypeStr3;
              } else if (o.getClass().isPrimitive() && o.toString().equals("0")) {
                fieldNamePrefix3 = fieldNamePrefix4;
                curObjClass2 = curObjClass3;
                str = fieldNamePrefix5;
                indent3 = indent4;
                objectValueStr3 = objectValueStr2;
                i3 = i4;
                fieldName = fieldName2;
                str2 = fieldTypeStr;
                fieldTypeStr2 = fieldTypeStr3;
              } else {
                String str3 = fieldTypeStr;
                fieldNamePrefix3 = fieldNamePrefix4;
                curObjClass2 = curObjClass3;
                str = fieldNamePrefix5;
                fieldTypeStr2 = fieldTypeStr3;
                indent3 = indent4;
                objectValueStr3 = objectValueStr2;
                i3 = i4;
                str2 = str3;
                fieldName = fieldName2;
                dumpObjectFields(
                    o,
                    dumpedObj,
                    NavigationBarInflaterView.SIZE_MOD_START + i4 + str3,
                    null,
                    fullPath3,
                    depth + 1,
                    maxDepth,
                    classTypeFilter,
                    valueFilter);
              }
              i4 = i3 + 1;
              fieldNamePrefix5 = str;
              fieldTypeStr3 = fieldTypeStr2;
              objectValueStr2 = objectValueStr3;
              fieldName2 = fieldName;
              fieldNamePrefix4 = fieldNamePrefix3;
              curObjClass3 = curObjClass2;
              indent4 = indent3;
              fieldTypeStr = str2;
            }
            curObjClass = curObjClass3;
            String str4 = fieldNamePrefix5;
            String indent5 = indent4;
            String str5 = fieldTypeStr;
            if (arrayLength <= 100) {
              indent2 = indent5;
            } else {
              indent2 = indent5;
              Log.m96e(str4, indent2 + "\t[Dumped until index 100" + str5);
            }
          } else {
            curObjClass = curObjClass3;
            String indent6 = indent4;
            if (!isPrimitiveDataType(objectTypeStr) && !objectTypeStr.contains(charSequence)) {
              while (curObjClass != null) {
                Field[] fields = curObjClass.getDeclaredFields();
                int length = fields.length;
                int i5 = 0;
                while (i5 < length) {
                  Field field = fields[i5];
                  try {
                    accessable = field.isAccessible();
                    z2 = true;
                  } catch (IllegalAccessException | IllegalArgumentException e) {
                    e = e;
                    z2 = true;
                  }
                  try {
                    field.setAccessible(true);
                    Object fieldObject2 = field.get(obj);
                    field.setAccessible(accessable);
                    fieldObject = fieldObject2;
                  } catch (IllegalAccessException | IllegalArgumentException e2) {
                    e = e2;
                    e.printStackTrace();
                    fieldObject = null;
                    boolean isFinalField = (field.getModifiers() & 16) == 0 ? z2 : false;
                    boolean isStaticField = (field.getModifiers() & 8) == 0 ? z2 : false;
                    isEnumField = field.isEnumConstant();
                    if (!isEnumField) {}
                    i5 = i + 1;
                    indent6 = indent;
                    length = i2;
                  }
                  boolean isFinalField2 = (field.getModifiers() & 16) == 0 ? z2 : false;
                  boolean isStaticField2 = (field.getModifiers() & 8) == 0 ? z2 : false;
                  isEnumField = field.isEnumConstant();
                  if (!isEnumField) {
                    i = i5;
                    i2 = length;
                    indent = indent6;
                  } else if (isStaticField2 && isFinalField2) {
                    i = i5;
                    i2 = length;
                    indent = indent6;
                  } else if (depth + 1 < maxDepth) {
                    i = i5;
                    i2 = length;
                    indent = indent6;
                    dumpObjectFields(
                        fieldObject,
                        dumpedObj,
                        null,
                        field,
                        fullPath3,
                        depth + 1,
                        maxDepth,
                        classTypeFilter,
                        valueFilter);
                  } else {
                    i = i5;
                    i2 = length;
                    indent = indent6;
                  }
                  i5 = i + 1;
                  indent6 = indent;
                  length = i2;
                }
                curObjClass = curObjClass.getSuperclass();
              }
              return;
            }
          }
          return;
        }
        return;
      }
    } else {
      charSequence = "java.lang.";
      z = true;
    }
    if (valueFilter == null || valueFilter.equals(objectValueStr2) == z) {
      boolean isPrimitive = curObjClass3.isPrimitive();
      fieldTypeStr = NavigationBarInflaterView.SIZE_MOD_END;
      if (isPrimitive || fieldTypeStr3.equals(objectTypeStr)) {
        Log.m96e(
            TAG,
            indent4
                + fieldName2
                + " = "
                + objectValueStr2
                + " ("
                + fieldTypeStr3
                + ") : "
                + fullPath2);
      } else {
        Log.m96e(
            TAG,
            indent4
                + fieldName2
                + " = "
                + objectValueStr2
                + " ("
                + fieldTypeStr3
                + " / "
                + objectTypeStr
                + ") : "
                + fullPath2);
      }
    } else {
      fieldTypeStr = NavigationBarInflaterView.SIZE_MOD_END;
    }
    if (!fullPath2.equals("")) {}
    String fullPath32 =
        fullPath2
            + fieldName2
            + NavigationBarInflaterView.KEY_CODE_START
            + extractClassNameFromFullClassPath(objectTypeStr)
            + NavigationBarInflaterView.KEY_CODE_END;
    if (alreadyDumpedObj) {}
  }

  public static void dumpObjectMethods(Object objToDump) {
    Log.m94d(TAG, "-------- Method list dump start : " + objToDump.toString() + " ----------");
    for (Class<?> curObjClass = objToDump.getClass();
        curObjClass != null;
        curObjClass = curObjClass.getSuperclass()) {
      Log.m94d(TAG, " -- Methods of " + curObjClass.getName() + " class --");
      Method[] methods = curObjClass.getDeclaredMethods();
      for (Method method : methods) {
        String curMethodName = method.toGenericString();
        Log.m94d(TAG, curMethodName);
      }
    }
    Log.m94d(TAG, "-------- Method list dump finished ----------");
  }

  public static void dumpClassHierarchy(Object objToDump) {
    Class<?> objClass = objToDump.getClass();
    Log.m94d(TAG, "-------- Class hierarchy dump start : " + objToDump.toString() + " ----------");
    for (Class<?> c = objClass; c != null; c = c.getSuperclass()) {
      Log.m94d(TAG, "-- Class name : " + c.getName());
      Class<?>[] clz = c.getInterfaces();
      for (Class<?> cls : clz) {
        Log.m94d(TAG, "   + interfaces : " + cls.getName());
      }
    }
    Log.m94d(TAG, "-------- Class hierarchy dump finished ----------");
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
                  accessable = fieldType.endsWith(MediaMetrics.SEPARATOR + fieldObjectType);
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
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                      e = e;
                      i = i3;
                      i2 = length;
                      fields = fields2;
                      Log.m96e(
                          TAG,
                          "Exception occurred in getFieldObjectByObjectType : " + e.toString());
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
                  Log.m96e(
                      TAG, "Exception occurred in getFieldObjectByObjectType : " + e.toString());
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
            Log.m96e(TAG, "Exception occurred in getFieldObjectByFieldName : " + e.toString());
          }
        }
      }
    }
    return null;
  }
}
