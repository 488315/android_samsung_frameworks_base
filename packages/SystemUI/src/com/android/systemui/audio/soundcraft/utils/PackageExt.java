package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;
import kotlin.Result;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PackageExt {
    public static final PackageExt INSTANCE = new PackageExt();

    private PackageExt() {
    }

    public static String getPackageNameForUid(int i, Context context) {
        Object failure;
        try {
            int i2 = Result.$r8$clinit;
            Object[] packagesForUid = context.getPackageManager().getPackagesForUid(i);
            failure = packagesForUid != null ? packagesForUid[0] : null;
        } catch (Throwable th) {
            int i3 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        return (String) (failure instanceof Result.Failure ? null : failure);
    }
}
