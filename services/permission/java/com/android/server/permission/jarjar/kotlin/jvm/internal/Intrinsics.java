package com.android.server.permission.jarjar.kotlin.jvm.internal;

import com.android.server.permission.jarjar.kotlin.UninitializedPropertyAccessException;
import java.util.Arrays;

/* loaded from: classes2.dex */
public abstract class Intrinsics {
  public static void checkNotNull(Object obj) {
    if (obj == null) {
      throwJavaNpe();
    }
  }

  public static void checkNotNull(Object obj, String str) {
    if (obj == null) {
      throwJavaNpe(str);
    }
  }

  public static void throwJavaNpe() {
    throw ((NullPointerException) sanitizeStackTrace(new NullPointerException()));
  }

  public static void throwJavaNpe(String str) {
    throw ((NullPointerException) sanitizeStackTrace(new NullPointerException(str)));
  }

  public static void throwUninitializedProperty(String str) {
    throw ((UninitializedPropertyAccessException)
        sanitizeStackTrace(new UninitializedPropertyAccessException(str)));
  }

  public static void throwUninitializedPropertyAccessException(String str) {
    throwUninitializedProperty("lateinit property " + str + " has not been initialized");
  }

  public static void checkNotNullExpressionValue(Object obj, String str) {
    if (obj != null) {
      return;
    }
    throw ((NullPointerException)
        sanitizeStackTrace(new NullPointerException(str + " must not be null")));
  }

  public static void checkNotNullParameter(Object obj, String str) {
    if (obj == null) {
      throwParameterIsNullNPE(str);
    }
  }

  public static void throwParameterIsNullNPE(String str) {
    throw ((NullPointerException)
        sanitizeStackTrace(new NullPointerException(createParameterIsNullExceptionMessage(str))));
  }

  public static String createParameterIsNullExceptionMessage(String str) {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    String name = Intrinsics.class.getName();
    int i = 0;
    while (!stackTrace[i].getClassName().equals(name)) {
      i++;
    }
    while (stackTrace[i].getClassName().equals(name)) {
      i++;
    }
    StackTraceElement stackTraceElement = stackTrace[i];
    return "Parameter specified as non-null is null: method "
        + stackTraceElement.getClassName()
        + "."
        + stackTraceElement.getMethodName()
        + ", parameter "
        + str;
  }

  public static boolean areEqual(Object obj, Object obj2) {
    if (obj == null) {
      return obj2 == null;
    }
    return obj.equals(obj2);
  }

  public static Throwable sanitizeStackTrace(Throwable th) {
    return sanitizeStackTrace(th, Intrinsics.class.getName());
  }

  public static Throwable sanitizeStackTrace(Throwable th, String str) {
    StackTraceElement[] stackTrace = th.getStackTrace();
    int length = stackTrace.length;
    int i = -1;
    for (int i2 = 0; i2 < length; i2++) {
      if (str.equals(stackTrace[i2].getClassName())) {
        i = i2;
      }
    }
    th.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i + 1, length));
    return th;
  }
}
