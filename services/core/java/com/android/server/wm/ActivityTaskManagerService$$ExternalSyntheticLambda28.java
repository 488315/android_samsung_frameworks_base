package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.content.ComponentName;

import com.android.internal.util.function.QuintConsumer;

public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda28
        implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        ((ActivityManagerInternal) obj)
                .updateBatteryStats(
                        (ComponentName) obj2,
                        ((Integer) obj3).intValue(),
                        ((Integer) obj4).intValue(),
                        ((Boolean) obj5).booleanValue());
    }
}
