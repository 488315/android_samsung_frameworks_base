package com.android.server.inputmethod;

import android.os.ServiceManager;
import com.android.internal.compat.IPlatformCompat;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ImePlatformCompatUtils {
    public final IPlatformCompat mPlatformCompat = IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
}
