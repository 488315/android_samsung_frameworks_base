package com.android.server.inputmethod;

import android.util.ArrayMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AdditionalSubtypeMap {
    public static final AdditionalSubtypeMap EMPTY_MAP = new AdditionalSubtypeMap(new ArrayMap());
    public final ArrayMap mMap;

    public AdditionalSubtypeMap(ArrayMap arrayMap) {
        this.mMap = arrayMap;
    }
}
