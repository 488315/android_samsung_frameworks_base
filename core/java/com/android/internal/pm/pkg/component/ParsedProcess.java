package com.android.internal.pm.pkg.component;

import android.util.ArrayMap;

import java.util.Set;

public interface ParsedProcess {
    ArrayMap<String, String> getAppClassNamesByPackage();

    Set<String> getDeniedPermissions();

    int getGwpAsanMode();

    int getMemtagMode();

    String getName();

    int getNativeHeapZeroInitialized();

    boolean isUseEmbeddedDex();
}
