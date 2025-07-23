package com.android.internal.util;

import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* loaded from: classes4.dex */
public class MessageUtils {
    private static final boolean DBG = false;
    public static final String[] DEFAULT_PREFIXES = {"CMD_", "EVENT_"};
    private static final String TAG = "MessageUtils";

    public static class DuplicateConstantError extends Error {
        private DuplicateConstantError() {
        }

        public DuplicateConstantError(String str, String str2, int i) {
            super(String.format("Duplicate constant value: both %s and %s = %d", str, str2, Integer.valueOf(i)));
        }
    }

    public static SparseArray<String> findMessageNames(Class[] clsArr, String[] strArr) {
        SparseArray<String> sparseArray = new SparseArray<>();
        for (Class cls : clsArr) {
            try {
                for (Field field : cls.getDeclaredFields()) {
                    int modifiers = field.getModifiers();
                    if (!((!Modifier.isFinal(modifiers)) | (!Modifier.isStatic(modifiers)))) {
                        String name = field.getName();
                        for (String str : strArr) {
                            if (name.startsWith(str)) {
                                try {
                                    field.setAccessible(true);
                                    try {
                                        int i = field.getInt(null);
                                        String str2 = sparseArray.get(i);
                                        if (str2 != null && !str2.equals(name)) {
                                            throw new DuplicateConstantError(name, str2, i);
                                        }
                                        sparseArray.put(i, name);
                                    } catch (ExceptionInInitializerError | IllegalArgumentException unused) {
                                        continue;
                                    }
                                } catch (IllegalAccessException | SecurityException unused2) {
                                    continue;
                                }
                            }
                        }
                    }
                }
            } catch (SecurityException unused3) {
                Log.e(TAG, "Can't list fields of class " + cls.getName());
            }
        }
        return sparseArray;
    }

    public static SparseArray<String> findMessageNames(Class[] clsArr) {
        return findMessageNames(clsArr, DEFAULT_PREFIXES);
    }
}
