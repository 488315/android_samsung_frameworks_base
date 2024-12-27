package com.samsung.android.server.pm;

import android.os.Bundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MetaDataHelper {
    public static boolean isMetaDataInBundle(Bundle bundle, String str) {
        return bundle != null && bundle.getBoolean(str, false);
    }
}
