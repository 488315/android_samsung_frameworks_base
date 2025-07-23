package com.android.systemui.audio.soundcraft.utils;

import android.content.Context;
import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
