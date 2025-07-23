package com.android.internal.widget;

import android.os.Bundle;
import android.os.IBinder;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class InlinePresentationStyleUtils {
    public static boolean bundleEquals(Bundle bundle, Bundle bundle2) {
        boolean equals;
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null || bundle2 == null || bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if ((obj instanceof Bundle) && (obj2 instanceof Bundle)) {
                equals = bundleEquals((Bundle) obj, (Bundle) obj2);
            } else {
                equals = Objects.equals(obj, obj2);
            }
            if (!equals) {
                return false;
            }
        }
        return true;
    }

    public static void filterContentTypes(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof Bundle) {
                filterContentTypes((Bundle) obj);
            } else if (obj instanceof IBinder) {
                bundle.remove(str);
            }
        }
    }

    private InlinePresentationStyleUtils() {
    }
}
