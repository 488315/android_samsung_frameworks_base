package com.android.server;

import android.os.Bundle;

public abstract class BundleUtils {
    public static Bundle clone(Bundle bundle) {
        return bundle != null ? new Bundle(bundle) : new Bundle();
    }

    public static boolean isEmpty(Bundle bundle) {
        return bundle == null || bundle.size() == 0;
    }
}
