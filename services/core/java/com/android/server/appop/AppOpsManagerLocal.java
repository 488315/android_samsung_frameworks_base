package com.android.server.appop;

import android.annotation.SystemApi;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes.dex */
public interface AppOpsManagerLocal {
    boolean isUidInForeground(int i);
}
