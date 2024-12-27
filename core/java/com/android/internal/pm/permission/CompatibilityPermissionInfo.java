package com.android.internal.pm.permission;

import android.Manifest;
import android.annotation.NonNull;

import com.android.internal.util.AnnotationValidations;

/* loaded from: classes5.dex */
public class CompatibilityPermissionInfo {
    public static final CompatibilityPermissionInfo[] COMPAT_PERMS = {
        new CompatibilityPermissionInfo(Manifest.permission.POST_NOTIFICATIONS, 33),
        new CompatibilityPermissionInfo(Manifest.permission.WRITE_EXTERNAL_STORAGE, 4),
        new CompatibilityPermissionInfo(Manifest.permission.READ_PHONE_STATE, 4)
    };
    private final String mName;
    private final int mSdkVersion;

    public CompatibilityPermissionInfo(String name, int sdkVersion) {
        this.mName = name;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mName);
        this.mSdkVersion = sdkVersion;
    }

    public String getName() {
        return this.mName;
    }

    public int getSdkVersion() {
        return this.mSdkVersion;
    }

    @Deprecated
    private void __metadata() {}
}
