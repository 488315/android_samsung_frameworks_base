package com.samsung.android.sume.core.functional;

import android.content.Context;

@FunctionalInterface
public interface ModelLoader<T> {
    T load(Context context, String str);
}
