package com.android.server.companion.utils;

import android.content.pm.PackageManager;
import android.util.Slog;
import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PackageUtils$$ExternalSyntheticLambda1 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ PackageManager f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ PackageManager.PackageInfoFlags f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ PackageUtils$$ExternalSyntheticLambda1(PackageManager packageManager, String str, PackageManager.PackageInfoFlags packageInfoFlags, int i) {
        this.f$0 = packageManager;
        this.f$1 = str;
        this.f$2 = packageInfoFlags;
        this.f$3 = i;
    }

    public final Object getOrThrow() {
        PackageManager packageManager = this.f$0;
        String str = this.f$1;
        try {
            return packageManager.getPackageInfoAsUser(str, this.f$2, this.f$3);
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.e("CDM_PackageUtils", "Package [" + str + "] is not found.");
            return null;
        }
    }
}
