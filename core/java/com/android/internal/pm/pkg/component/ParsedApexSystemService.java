package com.android.internal.pm.pkg.component;

public interface ParsedApexSystemService {
    int getInitOrder();

    String getJarPath();

    String getMaxSdkVersion();

    String getMinSdkVersion();

    String getName();
}
