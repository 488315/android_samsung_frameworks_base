package com.samsung.android.server.pm;

import android.os.Bundle;

public final class MetaDataHelper {
    public static boolean isMetaDataInBundle(Bundle bundle, String str) {
        return bundle != null && bundle.getBoolean(str, false);
    }
}
