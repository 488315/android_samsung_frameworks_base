package com.samsung.android.sume.core.functional;

import android.util.Pair;

import java.util.regex.Pattern;

@FunctionalInterface
public interface ModelPathLoader {
    Pair<String, Pattern> load(String str);
}
