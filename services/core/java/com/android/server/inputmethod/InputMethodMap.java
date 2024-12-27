package com.android.server.inputmethod;

import android.util.ArrayMap;
import android.view.inputmethod.InputMethodInfo;

import java.util.List;

public final class InputMethodMap {
    public static final ArrayMap EMPTY_MAP = new ArrayMap();
    public final ArrayMap mMap;

    public InputMethodMap(ArrayMap arrayMap) {
        this.mMap = arrayMap.isEmpty() ? EMPTY_MAP : new ArrayMap(arrayMap);
    }

    public final InputMethodMap applyAdditionalSubtypes(AdditionalSubtypeMap additionalSubtypeMap) {
        if (additionalSubtypeMap.mMap.isEmpty()) {
            return this;
        }
        int size = this.mMap.size();
        ArrayMap arrayMap = new ArrayMap(size);
        boolean z = false;
        for (int i = 0; i < size; i++) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) this.mMap.valueAt(i);
            List list = (List) additionalSubtypeMap.mMap.get(inputMethodInfo.getId());
            if (list == null || list.isEmpty()) {
                arrayMap.put(inputMethodInfo.getId(), inputMethodInfo);
            } else {
                arrayMap.put(inputMethodInfo.getId(), new InputMethodInfo(inputMethodInfo, list));
                z = true;
            }
        }
        return z ? new InputMethodMap(arrayMap) : this;
    }

    public final InputMethodInfo get(String str) {
        return (InputMethodInfo) this.mMap.get(str);
    }
}
