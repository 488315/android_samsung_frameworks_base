package com.android.server.pm.pkg.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public interface ParsedUsesPermission {

  @Retention(RetentionPolicy.SOURCE)
  public @interface UsesPermissionFlags {}

  String getName();

  int getUsesPermissionFlags();
}
