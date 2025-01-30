package com.google.android.setupcompat.internal;

import android.os.BaseBundle;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import com.google.android.setupcompat.util.Logger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PersistableBundles {
    public static final Logger LOG = new Logger("PersistableBundles");

    private PersistableBundles() {
        throw new AssertionError("Should not be instantiated");
    }

    public static void assertIsValid(PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            throw new NullPointerException("PersistableBundle cannot be null!");
        }
        for (String str : persistableBundle.keySet()) {
            Object obj = persistableBundle.get(str);
            Preconditions.checkArgument(String.format("Unknown/unsupported data type [%s] for key %s", obj, str), isSupportedDataType(obj));
        }
    }

    public static boolean isSupportedDataType(Object obj) {
        return (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof Float) || (obj instanceof String) || (obj instanceof Boolean);
    }

    public static ArrayMap toMap(BaseBundle baseBundle) {
        if (baseBundle == null || baseBundle.isEmpty()) {
            return new ArrayMap(0);
        }
        ArrayMap arrayMap = new ArrayMap(baseBundle.size());
        for (String str : baseBundle.keySet()) {
            Object obj = baseBundle.get(str);
            if (isSupportedDataType(obj)) {
                arrayMap.put(str, baseBundle.get(str));
            } else {
                LOG.m239w(String.format("Unknown/unsupported data type [%s] for key %s", obj, str));
            }
        }
        return arrayMap;
    }
}
