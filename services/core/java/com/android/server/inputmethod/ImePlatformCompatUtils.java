package com.android.server.inputmethod;

import android.os.ServiceManager;

import com.android.internal.compat.IPlatformCompat;

public final class ImePlatformCompatUtils {
    public final IPlatformCompat mPlatformCompat =
            IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
}
