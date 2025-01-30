package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.content.pm.IPackageManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PackageManagerWrapper {
    public static final PackageManagerWrapper sInstance = new PackageManagerWrapper();
    public static final IPackageManager mIPackageManager = AppGlobals.getPackageManager();

    private PackageManagerWrapper() {
    }
}
