package com.android.systemui.utils;

import android.app.IUriGrantsManager;
import android.content.Context;
import android.os.UserHandle;

/* loaded from: classes3.dex */
public final class SafeIconLoader {
    public final IUriGrantsManager iUriGrantsManager;
    public final String packageName;
    public final Context serviceContext;
    public final int serviceUid;

    public interface Factory {
        SafeIconLoader create(int i, String str, int i2);
    }

    public SafeIconLoader(int i, String str, int i2, Context context, IUriGrantsManager iUriGrantsManager) {
        this.serviceUid = i;
        this.packageName = str;
        this.iUriGrantsManager = iUriGrantsManager;
        this.serviceContext = context.createPackageContextAsUser(str, 0, UserHandle.of(i2));
    }
}
