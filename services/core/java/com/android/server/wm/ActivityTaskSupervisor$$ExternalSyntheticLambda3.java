package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.content.ComponentName;
import android.content.Intent;

import com.android.internal.util.function.QuadConsumer;

public final /* synthetic */ class ActivityTaskSupervisor$$ExternalSyntheticLambda3
        implements QuadConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        ((ActivityManagerInternal) obj)
                .cleanUpServices(((Integer) obj2).intValue(), (ComponentName) obj3, (Intent) obj4);
    }
}
