package com.android.internal.pm.permission;

import android.Manifest;
import android.annotation.NonNull;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes5.dex */
public class CompatibilityPermissionInfo {
    public static final CompatibilityPermissionInfo[] COMPAT_PERMS = {new CompatibilityPermissionInfo(Manifest.permission.POST_NOTIFICATIONS, 33), new CompatibilityPermissionInfo(Manifest.permission.WRITE_EXTERNAL_STORAGE, 4), new CompatibilityPermissionInfo(Manifest.permission.READ_PHONE_STATE, 4)};
    private final String mName;
    private final int mSdkVersion;

    @Deprecated
    private void __metadata() {
    }

    public CompatibilityPermissionInfo(String str, int i) {
        this.mName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mSdkVersion = i;
    }

    public String getName() {
        return this.mName;
    }

    public int getSdkVersion() {
        return this.mSdkVersion;
    }
}
