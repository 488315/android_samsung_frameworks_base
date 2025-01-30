package com.android.server.pm.pkg;

import android.content.pm.SigningDetails;
import android.util.ArraySet;
import com.android.server.pm.permission.LegacyPermissionState;
import java.util.List;

/* loaded from: classes3.dex */
public interface SharedUserApi {
    String getName();

    ArraySet getPackageStates();

    List getPackages();

    int getSeInfoTargetSdkVersion();

    LegacyPermissionState getSharedUserLegacyPermissionState();

    SigningDetails getSigningDetails();

    boolean isPrivileged();
}
