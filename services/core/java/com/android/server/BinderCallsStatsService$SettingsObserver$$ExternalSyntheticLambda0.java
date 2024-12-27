package com.android.server;

import android.os.Binder;

import com.android.internal.os.BinderInternal;

public final /* synthetic */
class BinderCallsStatsService$SettingsObserver$$ExternalSyntheticLambda0
        implements BinderInternal.WorkSourceProvider {
    public final int resolveWorkSourceUid(int i) {
        return Binder.getCallingUid();
    }
}
