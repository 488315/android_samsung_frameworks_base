package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;

import com.android.internal.util.function.NonaConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda25
        implements NonaConsumer {
    public final void accept(
            Object obj,
            Object obj2,
            Object obj3,
            Object obj4,
            Object obj5,
            Object obj6,
            Object obj7,
            Object obj8,
            Object obj9) {
        ((ActivityManagerInternal) obj)
                .startProcess(
                        (String) obj2,
                        (ApplicationInfo) obj3,
                        ((Boolean) obj4).booleanValue(),
                        ((Boolean) obj5).booleanValue(),
                        (String) obj6,
                        (ComponentName) obj7,
                        ((Boolean) obj8).booleanValue(),
                        ((Integer) obj9).intValue());
    }
}
