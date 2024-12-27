package com.android.server.pm;

import android.os.Handler;
import android.os.RemoteCallbackList;

import com.android.server.Watchdog$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
