package com.android.server.wm;

import android.app.ActivityManagerInternal;
import android.app.assist.ActivityId;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import com.android.internal.util.function.OctConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda29 implements OctConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        ((ActivityManagerInternal) obj).updateActivityUsageStatsWithIntent((ComponentName) obj2, ((Integer) obj3).intValue(), ((Integer) obj4).intValue(), (IBinder) obj5, (ComponentName) obj6, (ActivityId) obj7, (Intent) obj8);
    }
}
