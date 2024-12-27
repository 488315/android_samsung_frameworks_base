package com.android.server;

import android.os.Bundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BundleUtils {
    public static Bundle clone(Bundle bundle) {
        return bundle != null ? new Bundle(bundle) : new Bundle();
    }

    public static boolean isEmpty(Bundle bundle) {
        return bundle == null || bundle.size() == 0;
    }
}
