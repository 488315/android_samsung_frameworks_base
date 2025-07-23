package com.google.android.setupcompat.internal;

import android.os.BaseBundle;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import com.google.android.setupcompat.util.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class PersistableBundles {
    public static final Logger LOG = new Logger("PersistableBundles");

    private PersistableBundles() {
        throw new AssertionError("Should not be instantiated");
    }

    public static void assertIsValid(PersistableBundle persistableBundle) {
        Preconditions.checkNotNull(persistableBundle, "PersistableBundle cannot be null!");
        for (String str : persistableBundle.keySet()) {
            Object obj = persistableBundle.get(str);
            Preconditions.checkArgument(String.format("Unknown/unsupported data type [%s] for key %s", obj, str), isSupportedDataType(obj));
        }
    }

    public static boolean isSupportedDataType(Object obj) {
        return (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof Float) || (obj instanceof String) || (obj instanceof Boolean);
    }

    public static PersistableBundle mergeBundles(PersistableBundle persistableBundle, PersistableBundle persistableBundle2, PersistableBundle... persistableBundleArr) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        arrayList.addAll(Arrays.asList(persistableBundle, persistableBundle2));
        Collections.addAll(arrayList, persistableBundleArr);
        PersistableBundle persistableBundle3 = new PersistableBundle();
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            PersistableBundle persistableBundle4 = (PersistableBundle) obj;
            for (String str : persistableBundle4.keySet()) {
                Preconditions.checkArgument("Found duplicate key [" + str + "] while attempting to merge bundles.", !persistableBundle3.containsKey(str));
            }
            persistableBundle3.putAll(persistableBundle4);
        }
        return persistableBundle3;
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
                LOG.w(String.format("Unknown/unsupported data type [%s] for key %s", obj, str));
            }
        }
        return arrayMap;
    }
}
