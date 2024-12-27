package com.android.server.inputmethod;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class InputMethodSettingsRepository {
    public static final SparseArray sPerUserMap = new SparseArray();

    public static InputMethodSettings get(int i) {
        InputMethodSettings inputMethodSettings = (InputMethodSettings) sPerUserMap.get(i);
        return inputMethodSettings != null
                ? inputMethodSettings
                : new InputMethodSettings(new InputMethodMap(InputMethodMap.EMPTY_MAP), i);
    }

    public static void put(int i, InputMethodSettings inputMethodSettings) {
        sPerUserMap.put(i, inputMethodSettings);
    }
}
