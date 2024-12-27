package com.android.internal.pm.pkg.component;

public interface ParsedPermissionGroup extends ParsedComponent {
    int getBackgroundRequestDetailRes();

    int getBackgroundRequestRes();

    int getPriority();

    int getRequestDetailRes();

    int getRequestRes();
}
