package com.android.internal.pm.pkg.component;

public interface ParsedService extends ParsedMainComponent {
    int getForegroundServiceType();

    String getPermission();
}
