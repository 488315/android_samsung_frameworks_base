package com.android.systemui.utils;

import android.app.IUriGrantsManager;
import android.content.Context;
import android.os.UserHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SafeIconLoader {
    public final IUriGrantsManager iUriGrantsManager;
    public final String packageName;
    public final Context serviceContext;
    public final int serviceUid;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
