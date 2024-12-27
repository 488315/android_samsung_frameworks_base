package com.android.server.pm;

import android.os.Handler;
import android.os.RemoteCallbackList;

import com.android.server.Watchdog$$ExternalSyntheticOutline0;

public final class BackgroundInstallControlCallbackHelper {
    static final String FLAGGED_PACKAGE_NAME_KEY = "packageName";
    static final String FLAGGED_USER_ID_KEY = "userId";
    final RemoteCallbackList mCallbacks = new RemoteCallbackList();
    public final Handler mHandler =
            new Handler(
                    Watchdog$$ExternalSyntheticOutline0.m(
                                    10, "BackgroundInstallControlCallbackHelperBg", true)
                            .getLooper());
}
