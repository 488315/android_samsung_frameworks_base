package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.content.ComponentName;

import com.android.internal.util.function.QuintConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
