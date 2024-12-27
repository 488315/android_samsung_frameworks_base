package com.android.server.inputmethod;

import android.util.ArrayMap;

public final class AdditionalSubtypeMap {
    public static final AdditionalSubtypeMap EMPTY_MAP = new AdditionalSubtypeMap(new ArrayMap());
    public final ArrayMap mMap;

    public AdditionalSubtypeMap(ArrayMap arrayMap) {
        this.mMap = arrayMap;
    }
}
