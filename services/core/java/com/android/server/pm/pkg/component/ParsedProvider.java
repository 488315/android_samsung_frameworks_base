package com.android.server.pm.pkg.component;

import java.util.List;

/* loaded from: classes3.dex */
public interface ParsedProvider extends ParsedMainComponent {
    String getAuthority();

    int getInitOrder();

    List getPathPermissions();

    String getReadPermission();

    List getUriPermissionPatterns();

    String getWritePermission();

    boolean isForceUriPermissions();

    boolean isGrantUriPermissions();

    boolean isMultiProcess();

    boolean isSyncable();
}
