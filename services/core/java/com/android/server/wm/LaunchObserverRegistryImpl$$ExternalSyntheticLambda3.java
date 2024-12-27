package com.android.server.wm;

import android.content.Intent;

import com.android.internal.util.function.TriConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class LaunchObserverRegistryImpl$$ExternalSyntheticLambda3
        implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public final void accept(Object obj, Object obj2, Object obj3) {
        LaunchObserverRegistryImpl launchObserverRegistryImpl = (LaunchObserverRegistryImpl) obj;
        switch (this.$r8$classId) {
            case 0:
                Intent intent = (Intent) obj2;
                long longValue = ((Long) obj3).longValue();
                for (int i = 0; i < launchObserverRegistryImpl.mList.size(); i++) {
                    ((ActivityMetricsLaunchObserver) launchObserverRegistryImpl.mList.get(i))
                            .onIntentStarted(intent, longValue);
                }
                break;
            default:
                long longValue2 = ((Long) obj2).longValue();
                long longValue3 = ((Long) obj3).longValue();
                for (int i2 = 0; i2 < launchObserverRegistryImpl.mList.size(); i2++) {
                    ((ActivityMetricsLaunchObserver) launchObserverRegistryImpl.mList.get(i2))
                            .onReportFullyDrawn(longValue2, longValue3);
                }
                break;
        }
    }
}
