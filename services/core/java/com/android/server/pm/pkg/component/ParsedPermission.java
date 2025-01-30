package com.android.server.pm.pkg.component;

import java.util.Set;

/* loaded from: classes3.dex */
public interface ParsedPermission extends ParsedComponent {
  String getBackgroundPermission();

  String getGroup();

  Set getKnownCerts();

  ParsedPermissionGroup getParsedPermissionGroup();

  int getProtectionLevel();

  int getRequestRes();

  boolean isTree();
}
