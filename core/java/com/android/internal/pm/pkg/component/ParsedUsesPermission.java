package com.android.internal.pm.pkg.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public interface ParsedUsesPermission {
    public static final int FLAG_NEVER_FOR_LOCATION = 65536;

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsesPermissionFlags {}

    String getName();

    int getUsesPermissionFlags();
}
