package com.android.internal.pm.pkg.component;

import java.util.Set;

public interface ParsedPermission extends ParsedComponent {
    String getBackgroundPermission();

    String getGroup();

    Set<String> getKnownCerts();

    ParsedPermissionGroup getParsedPermissionGroup();

    int getProtectionLevel();

    int getRequestRes();

    boolean isTree();
}
