package com.android.server.inputmethod;

import android.util.SparseArray;

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
